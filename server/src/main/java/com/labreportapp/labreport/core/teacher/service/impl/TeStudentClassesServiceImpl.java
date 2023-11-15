package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentApiRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.request.TeSentStudentClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TePointImportResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentClassesResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentStatusApiResponse;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.infrastructure.apiconstant.ApiConstants;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.labreport.infrastructure.constant.StatusTeam;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TeStudentClassesServiceImpl implements TeStudentClassesService {

    @Autowired
    private TeStudentClassesRepository teStudentClassesRepository;

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public List<TeStudentCallApiResponse> searchApiStudentClassesByIdClass(String idClass) {
        List<TeStudentClassesResponse> listRepository = teStudentClassesRepository
                .findStudentClassByIdClass(idClass);
        if (listRepository.size() == 0) {
            return null;
        }
        List<String> idStudentList = listRepository.stream()
                .map(TeStudentClassesResponse::getIdStudent)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = callApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        List<TeStudentCallApiResponse> listReturn = new ArrayList<>();
        if (listRepository.size() == 0 && listRespone.size() == 0) {
            return null;
        }
        listRepository.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdStudent().equals(respone.getId())) {
                    TeStudentCallApiResponse obj = new TeStudentCallApiResponse();
                    obj.setId(respone.getId());
                    obj.setName(respone.getName());
                    obj.setUsername(respone.getUserName());
                    obj.setEmail(reposi.getEmailStudentClass());
                    obj.setIdStudent(respone.getId());
                    obj.setIdStudentClass(reposi.getIdStudentClass());
                    obj.setRole(reposi.getRole());
                    obj.setStatusStudent(reposi.getStatusStudent());
                    obj.setIdTeam(reposi.getIdTeam());
                    obj.setNameTeam(reposi.getNameTeam());
                    obj.setSubjectName(reposi.getSubjectName());
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }

    @Override
    public List<TeStudentStatusApiResponse> searchApiStudentClassesStatusByIdClass(String idClass) {
        List<StudentClasses> listStudentClass = teStudentClassesRepository.findStudentClassesByIdClass(idClass);
        if (listStudentClass.size() == 0) {
            return null;
        }
        List<String> idStudentList = listStudentClass.stream()
                .map(StudentClasses::getStudentId)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = callApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        List<TeStudentStatusApiResponse> listReturn = new ArrayList<>();
        if (listStudentClass.size() == 0 && listRespone.size() == 0) {
            return null;
        }
        listStudentClass.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getStudentId().equals(respone.getId())) {
                    TeStudentStatusApiResponse obj = new TeStudentStatusApiResponse();
                    obj.setIdStudentClass(reposi.getId());
                    obj.setIdStudent(reposi.getStudentId());
                    obj.setName(respone.getName());
                    obj.setEmail(reposi.getEmail());
                    obj.setUsername(respone.getUserName());
                    obj.setStatusTeam(reposi.getStatus().equals(StatusTeam.ACTIVE) ? 0 : 1);
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }

    @Override
    public List<SimpleResponse> searchAllStudentByIdClass(String idClass) {
        List<TePointImportResponse> listRepository = teStudentClassesRepository
                .findAllStudentClassForPointByIdClass(idClass);
        if (listRepository.size() == 0) {
            return null;
        }
        List<String> idStudentList = listRepository.stream()
                .map(TePointImportResponse::getIdStudent)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = callApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        return listRespone;
    }

    @Override
    public List<TeStudentCallApiResponse> searchStudentClassesByIdClassAndIdTeam(TeFindStudentClasses teFindStudentClasses) {
        List<TeStudentClassesResponse> listRepository = teStudentClassesRepository
                .findStudentClassByIdClassAndIdTeam(teFindStudentClasses);
        if (listRepository.size() == 0) {
            return null;
        }
        List<String> idStudentList = listRepository.stream()
                .map(TeStudentClassesResponse::getIdStudent)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = callApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        List<TeStudentCallApiResponse> listReturn = new ArrayList<>();
        listRepository.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdStudent().equals(respone.getId())) {
                    TeStudentCallApiResponse obj = new TeStudentCallApiResponse();
                    obj.setId(respone.getId());
                    obj.setName(respone.getName());
                    obj.setUsername(respone.getUserName());
                    obj.setEmail(reposi.getEmailStudentClass());
                    obj.setIdStudent(respone.getId());
                    obj.setIdStudentClass(reposi.getIdStudentClass());
                    obj.setRole(reposi.getRole());
                    obj.setStatusStudent(reposi.getStatusStudent());
                    obj.setIdTeam(reposi.getIdTeam());
                    obj.setNameTeam(reposi.getNameTeam());
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }

    @Override
    @Transactional
    @Synchronized
    public Boolean updateKickStudentClasses(TeSentStudentClassRequest request) {
        Optional<Class> classCurrent = teClassRepository.findById(request.getIdClassOld());
        if (!classCurrent.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        List<StudentClasses> studentClasses = teStudentClassesRepository.findStudentClassesByIdClass(request.getIdClassOld());
        List<String> idStudents = request.getListIdStudent();
        List<StudentClasses> studentClassesFinal = new ArrayList<>();
        if (request.getListIdStudent().size() == 0 || studentClasses.size() == 0) {
            return false;
        }
        studentClasses.forEach(item -> {
            idStudents.forEach(id -> {
                if (id.equals(item.getStudentId())) {
                    StudentClasses studentKich = new StudentClasses();
                    studentKich.setId(item.getId());
                    studentClassesFinal.add(studentKich);
                }
            });
        });
        classCurrent.get().setClassSize(classCurrent.get().getClassSize() - idStudents.size());
        teClassRepository.save(classCurrent.get());
        teStudentClassesRepository.deleteAll(studentClassesFinal);
        return true;
    }

    @Override
    @Transactional
    @Synchronized
    public List<StudentClasses> updateSentStudentClassesToClass(TeSentStudentClassRequest request) {
        StringBuilder message = new StringBuilder();
        message.append("Đã chuyển sinh viên: ");
        Optional<Class> classSent = teClassRepository.findById(request.getIdClassSent());
        Optional<Class> classOld = teClassRepository.findById(request.getIdClassOld());
        if (!classSent.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        List<StudentClasses> studentClassesOld = teStudentClassesRepository.findStudentClassesByIdClass(request.getIdClassOld());
        List<SimpleResponse> listInforStudentClassesOld = searchAllStudentByIdClass(request.getIdClassOld());
        List<String> idStudents = request.getListIdStudent();
        List<StudentClasses> studentClassesUp = new ArrayList<>();
        List<Class> classUp = new ArrayList<>();
        if (request.getListIdStudent().size() == 0 || studentClassesOld.size() == 0) {
            return null;
        }
        studentClassesOld.forEach(item -> {
            idStudents.forEach(id -> {
                if (id.equals(item.getStudentId())) {
                    StudentClasses studentSent = new StudentClasses();
                    studentSent.setId(item.getId());
                    studentSent.setEmail(item.getEmail());
                    studentSent.setStudentId(item.getStudentId());
                    studentSent.setStatus(item.getStatus());
                    studentSent.setStatusStudentFeedBack(item.getStatusStudentFeedBack());
                    studentSent.setRole(RoleTeam.MEMBER);
                    studentSent.setTeamId(null);
                    studentSent.setClassId(request.getIdClassSent());
                    studentClassesUp.add(studentSent);
                    listInforStudentClassesOld.forEach(infor -> {
                        if (id.equals(item.getStudentId()) && studentSent.getStudentId().equals(infor.getId())) {
                            message.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName()).append(",");
                        }
                    });
                }
            });
        });
        if (message.length() > 0 && message.charAt(message.length() - 1) == ',') {
            message.deleteCharAt(message.length() - 1);
            message.append(".");
        }
        String nameSemester = loggerUtil.getNameSemesterByIdClass(classOld.get().getId());
        message.append(" Từ lớp " + classOld.get().getCode() + " qua lớp " + classSent.get().getCode());
        loggerUtil.sendLogStreamClass(message.toString() + " và cập nhật sĩ số từ "
                + classSent.get().getClassSize() + " thành " + (classSent.get().getClassSize() + idStudents.size()) + ".", classSent.get().getCode(), nameSemester);
        loggerUtil.sendLogStreamClass(message.toString() + " và cập nhật sĩ số từ "
                + classOld.get().getClassSize() + " thành " + (classOld.get().getClassSize() - idStudents.size()) + ".", classOld.get().getCode(), nameSemester);
        Class sent = classSent.get();
        sent.setClassSize(sent.getClassSize() + idStudents.size());
        classUp.add(sent);
        Class old = classOld.get();
        old.setClassSize(old.getClassSize() - idStudents.size());
        classUp.add(old);
        teClassRepository.saveAll(classUp);
        return teStudentClassesRepository.saveAll(studentClassesUp);
    }

    @Override
    public List<TeStudentCallApiResponse> callApiStudent(TeFindStudentApiRequest teFindStudentApiRequest) {
        String apiUrl = ApiConstants.API_GET_USER_BY_LIST_ID;

        ResponseEntity<List<TeStudentCallApiResponse>> responseEntity =
                restTemplate.exchange(apiUrl + "?id=" + teFindStudentApiRequest.getListId(), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<TeStudentCallApiResponse>>() {
                        });
        List<TeStudentCallApiResponse> response = responseEntity.getBody();
        return response;
    }

}
