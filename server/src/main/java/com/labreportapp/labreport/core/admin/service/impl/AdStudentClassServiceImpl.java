package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.excel.AdExcelImportService;
import com.labreportapp.labreport.core.admin.excel.AdExportExcelStudentClasses;
import com.labreportapp.labreport.core.admin.excel.AdImportExcelStudentClasses;
import com.labreportapp.labreport.core.admin.model.response.AdExportExcelStudentsClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdStudentCallApiRespone;
import com.labreportapp.labreport.core.admin.model.response.AdStudentClassesRespone;
import com.labreportapp.labreport.core.admin.repository.AdStudentClassRepository;
import com.labreportapp.labreport.core.admin.service.AdStudentClassService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.response.StClassConfigurationResponse;
import com.labreportapp.labreport.core.student.model.response.StClassResponse;
import com.labreportapp.labreport.core.student.repository.StClassConfigurationRepository;
import com.labreportapp.labreport.core.student.repository.StClassRepository;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.infrastructure.apiconstant.ActorConstants;
import com.labreportapp.labreport.infrastructure.constant.StatusStudentFeedBack;
import com.labreportapp.labreport.infrastructure.constant.StatusTeam;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdStudentClassServiceImpl implements AdStudentClassService {

    @Autowired
    private AdStudentClassRepository adStudentClassRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    private AdExportExcelStudentClasses adExportExcelStudentClasses;

    @Autowired
    private AdExcelImportService adExcelImportService;

    @Autowired
    private StClassConfigurationRepository stClassConfigurationRepository;

    @Autowired
    private StClassRepository stClassRepository;

    @Override
    public List<AdStudentCallApiRespone> findStudentClassByIdClass(String idClass) {
        List<AdStudentClassesRespone> listRepository = adStudentClassRepository
                .findStudentClassByIdClass(idClass);

        List<String> idStudentList = listRepository.stream()
                .map(AdStudentClassesRespone::getIdStudent)
                .collect(Collectors.toList());

        List<SimpleResponse> listRespone = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        List<AdStudentCallApiRespone> listReturn = new ArrayList<>();
        listRepository.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdStudent().equals(respone.getId())) {
                    AdStudentCallApiRespone obj = new AdStudentCallApiRespone();
                    obj.setId(respone.getId());
                    obj.setName(respone.getName());
                    obj.setUsername(respone.getUserName());
                    obj.setEmail(reposi.getEmailStudentClass());
                    obj.setIdStudent(respone.getId());
                    obj.setIdStudentClass(reposi.getIdStudentClass());
                    obj.setRole(reposi.getRole());
                    obj.setStatusStudent(reposi.getStatusStudent());
                    obj.setIdTeam(reposi.getIdTeam());
                    obj.setCodeTeam(reposi.getCodeTeam());
                    obj.setNameTeam(reposi.getNameTeam());
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }

    @Override
    public ByteArrayOutputStream exportStudentsInClassExcel(HttpServletResponse response, String idClass, Boolean isSample) {
        List<AdStudentClassesRespone> studentsInClass = adStudentClassRepository
                .findStudentClassByIdClass(idClass);

        List<String> idStudentList = studentsInClass.stream()
                .map(AdStudentClassesRespone::getIdStudent)
                .collect(Collectors.toList());

        List<SimpleResponse> simpleResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        List<AdExportExcelStudentsClassCustomResponse> studentsInClassResponse = new ArrayList<>();

        for (AdStudentClassesRespone st : studentsInClass) {
            simpleResponse.forEach((s -> {
                if (st.getIdStudent().equals(s.getId())) {
                    AdExportExcelStudentsClassCustomResponse export = new AdExportExcelStudentsClassCustomResponse();
                    export.setStatusStudent(st.getStatusStudent());
                    export.setNameTeam(st.getNameTeam());
                    export.setEmail(st.getEmailStudentClass());
                    export.setName(st.getIdStudent().equals(s.getId()) ? s.getName() : "");
                    export.setUsername(st.getIdStudent().equals(s.getId()) ? s.getUserName() : "");
                    studentsInClassResponse.add(export);
                }
            }));
        }

        return adExportExcelStudentClasses.export(response, studentsInClassResponse, isSample);
    }

    @Override
    @Synchronized
    public AdImportExcelStudentClasses importExcelStudentsInClass(MultipartFile multipartFile, String idClass) {
        AdImportExcelStudentClasses response = new AdImportExcelStudentClasses();
        ConcurrentHashMap<String, StudentClasses> mapStudents = new ConcurrentHashMap<>();
        StClassRequest stClassRequest = new StClassRequest();
        stClassRequest.setIdClass(idClass);
        Optional<StClassResponse> conditionClass = stClassRepository.checkConditionCouldJoinOrLeaveClass(stClassRequest);

        List<SimpleResponse> simpleResponse = convertRequestCallApiIdentity.
                handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_STUDENT);
        Map<String, SimpleResponse> simpleMap = simpleResponse.stream()
                .collect(Collectors.toMap(SimpleResponse::getEmail, Function.identity()));
        StClassConfigurationResponse configuration = stClassConfigurationRepository.getClassConfiguration();
        response.setStatus(true);
        ConcurrentHashMap<String, Boolean> emailMap = new ConcurrentHashMap<>();

        try {
            List<AdImportExcelStudentClasses> listImport = adExcelImportService.importDataStudentClasses(multipartFile);
            if (listImport.isEmpty()) {
                response.setStatus(false);
                response.setMessage("File excel không có dữ liệu!");
                return response;
            }

            if (listImport.size() > configuration.getClassSizeMax()) {
                response.setStatus(false);
                response.setMessage("Chỉ được phép import tối đa " + configuration.getClassSizeMax() + " sinh viên");
                return response;
            }

            if (conditionClass.isPresent()) {
                response.setStatus(false);
                response.setMessage("Không thể import vì thời hạn của sinh viên đã bắt đầu!");
                return response;
            }

            listImport.parallelStream().forEach(imp -> {
                String regexName = "^[^!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/\\\\]*$";
                String regexEmailExactly = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                if (imp.getName().isBlank()) {
                    response.setStatus(false);
                    response.setMessage("Tên sinh viên không được để trống");
                    return;
                }
                if (!imp.getName().matches(regexName)) {
                    response.setStatus(false);
                    response.setMessage("Tên sinh viên sai định dạng");
                    return;
                }
                if (imp.getEmail().isBlank()) {
                    response.setStatus(false);
                    response.setMessage("Email không được để trống");
                    return;
                }
                if (!imp.getEmail().matches(regexEmailExactly)) {
                    response.setStatus(false);
                    response.setMessage("Email sai định dạng");
                    return;
                }

                SimpleResponse getSimple = simpleMap.get(imp.getEmail().trim());
                if (getSimple == null) {
                    response.setStatus(false);
                    response.setMessage("Email sinh viên không tồn tại trong hệ thống!");
                    return;
                }

                if (emailMap.putIfAbsent(imp.getEmail(), true) != null) {
                    response.setStatus(false);
                    response.setMessage("Trùng email sinh viên trong sheet!");
                    return;
                }

                StudentClasses st = new StudentClasses();
                st.setClassId(idClass);
                st.setStudentId(getSimple.getId());
                st.setEmail(getSimple.getEmail());
                st.setStatus(StatusTeam.INACTIVE);
                st.setStatusStudentFeedBack(StatusStudentFeedBack.CHUA_FEEDBACK);
                st.setCreatedDate(new Date().getTime());
                mapStudents.put(getSimple.getEmail(), st);

            });
            if (response.getStatus()) {
                List<StudentClasses> students = new ArrayList<>(mapStudents.values());
                adStudentClassRepository.deleteAll();
                adStudentClassRepository.saveAll(students);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(false);
            response.setMessage("Lỗi không xác định!");
            return response;
        }
        return response;
    }
}
