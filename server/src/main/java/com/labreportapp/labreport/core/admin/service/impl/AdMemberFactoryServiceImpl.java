package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.excel.AdExportExcelMemberFactory;
import com.labreportapp.labreport.core.admin.model.request.AdFindMemberFactoryRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMemberFactoryRequest;
import com.labreportapp.labreport.core.admin.model.response.AdDetailMemberFactoryResponse;
import com.labreportapp.labreport.core.admin.model.response.AdExcelMemberFactoryCustom;
import com.labreportapp.labreport.core.admin.model.response.AdExcelMemberFactoryResponse;
import com.labreportapp.labreport.core.admin.model.response.AdMemberFactoryCustom;
import com.labreportapp.labreport.core.admin.model.response.AdMemberFactoryResponse;
import com.labreportapp.labreport.core.admin.model.response.AdRoleFactoryDefaultResponse;
import com.labreportapp.labreport.core.admin.repository.AdMemberFactoryRepository;
import com.labreportapp.labreport.core.admin.service.AdMemberFactoryService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.MemberFactory;
import com.labreportapp.labreport.entity.MemberRoleFactory;
import com.labreportapp.labreport.entity.MemberTeamFactory;
import com.labreportapp.labreport.infrastructure.constant.StatusMemberFactory;
import com.labreportapp.labreport.repository.MemberRoleFactoryRepository;
import com.labreportapp.labreport.repository.MemberTeamFactoryRepository;
import com.labreportapp.labreport.repository.RoleFactoryRepository;
import com.labreportapp.labreport.repository.TeamFactoryRepository;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class AdMemberFactoryServiceImpl implements AdMemberFactoryService {

    @Autowired
    private AdMemberFactoryRepository adMemberFactoryRepository;

    @Autowired
    @Qualifier(TeamFactoryRepository.NAME)
    private TeamFactoryRepository teamFactoryRepository;

    @Autowired
    @Qualifier(RoleFactoryRepository.NAME)
    private RoleFactoryRepository roleFactoryRepository;

    @Autowired
    @Qualifier(MemberRoleFactoryRepository.NAME)
    private MemberRoleFactoryRepository memberRoleFactoryRepository;

    @Autowired
    @Qualifier(MemberTeamFactoryRepository.NAME)
    private MemberTeamFactoryRepository memberTeamFactoryRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    private AdExportExcelMemberFactory adExportExcelMemberFactory;

    @Override
    public PageableObject<AdMemberFactoryCustom> getPage(final AdFindMemberFactoryRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<AdMemberFactoryResponse> responsePage = adMemberFactoryRepository.getPageMemberFactory(pageable, request);
        List<String> idList = responsePage.getContent().stream()
                .map(AdMemberFactoryResponse::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = new ArrayList<>();
        if (idList != null && idList.size() > 0) {
            listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idList);
        }
        List<SimpleResponse> finalListResponse = listResponse;
        List<AdMemberFactoryCustom> listCustom = new ArrayList<>();
        responsePage.getContent().forEach(page -> {
            finalListResponse.forEach(response -> {
                if (page.getMemberId().equals(response.getId())) {
                    AdMemberFactoryCustom adMemberFactoryCustom = new AdMemberFactoryCustom();
                    adMemberFactoryCustom.setId(page.getId());
                    adMemberFactoryCustom.setMemberId(page.getMemberId());
                    adMemberFactoryCustom.setRoleMemberFactory(page.getRoleMemberFactory());
                    adMemberFactoryCustom.setNumberTeam(page.getNumberTeam());
                    adMemberFactoryCustom.setPicture(response.getPicture());
                    adMemberFactoryCustom.setStt(page.getStt());
                    adMemberFactoryCustom.setEmail(page.getEmail());
                    adMemberFactoryCustom.setStatusMemberFactory(page.getStatusMemberFactory());
                    adMemberFactoryCustom.setName(response.getName());
                    adMemberFactoryCustom.setUserName(response.getUserName());
                    listCustom.add(adMemberFactoryCustom);
                }
            });
        });
        PageableObject<AdMemberFactoryCustom> pageableObject = new PageableObject<>();
        pageableObject.setData(listCustom);
        pageableObject.setCurrentPage(responsePage.getNumber());
        pageableObject.setTotalPages(responsePage.getTotalPages());
        return pageableObject;
    }

    @Override
    public List<SimpleEntityProjection> getRoles() {
        return adMemberFactoryRepository.getRoles();
    }

    @Override
    public List<SimpleEntityProjection> getTeams() {
        return teamFactoryRepository.getTeams();
    }

    @Override
    public Integer getNumberMemberFactory() {
        return adMemberFactoryRepository.getNumberMemberFactory();
    }

    @Override
    public AdMemberFactoryCustom addMemberFactory(String email) {
        SimpleResponse simpleResponse = convertRequestCallApiIdentity.handleCallApiGetUserByEmail(email);
        if (simpleResponse == null) {
            throw new RestApiException(Message.NO_FIND_EMAIL);
        }
        String checkMemberFactory = adMemberFactoryRepository.getMemberFactoryByEmail(email);
        if (checkMemberFactory != null) {
            throw new RestApiException(Message.MEMBER_ALREADY);
        }
        MemberFactory memberFactory = new MemberFactory();
        memberFactory.setMemberId(simpleResponse.getId());
        memberFactory.setEmail(email);
        memberFactory.setStatusMemberFactory(StatusMemberFactory.HOAT_DONG);
        MemberFactory memberFactoryNew = adMemberFactoryRepository.save(memberFactory);
        MemberRoleFactory memberRoleFactory = new MemberRoleFactory();
        AdRoleFactoryDefaultResponse roleDefault = memberRoleFactoryRepository.findRoleDefault();
        memberRoleFactory.setRoleFactoryId(roleDefault.getId());
        memberRoleFactory.setMemberFactoryId(memberFactoryNew.getId());
        memberRoleFactoryRepository.save(memberRoleFactory);
        AdMemberFactoryCustom adMemberFactoryCustom = new AdMemberFactoryCustom();
        adMemberFactoryCustom.setId(memberFactoryNew.getId());
        adMemberFactoryCustom.setMemberId(simpleResponse.getId());
        adMemberFactoryCustom.setRoleMemberFactory(roleDefault.getName());
        adMemberFactoryCustom.setStatusMemberFactory(0);
        adMemberFactoryCustom.setPicture(simpleResponse.getPicture());
        adMemberFactoryCustom.setStt(1);
        adMemberFactoryCustom.setNumberTeam(0);
        adMemberFactoryCustom.setName(simpleResponse.getName());
        adMemberFactoryCustom.setUserName(simpleResponse.getUserName());
        adMemberFactoryCustom.setEmail(simpleResponse.getEmail());
        return adMemberFactoryCustom;
    }

    @Override
    @Transactional
    public AdMemberFactoryCustom updateMemberFactory(@Valid AdUpdateMemberFactoryRequest request) {
        MemberFactory memberFactoryFind = adMemberFactoryRepository.findById(request.getId()).get();
        if (Objects.isNull(memberFactoryFind)) {
            throw new RestApiException(Message.MEMBER_FACTORY_NOT_EXISTS);
        }
        memberFactoryFind.setStatusMemberFactory(StatusMemberFactory.values()[request.getStatus()]);

        adMemberFactoryRepository.deleteTeamsMemberFactory(memberFactoryFind.getId());
        adMemberFactoryRepository.deleteRolesMemberFactory(memberFactoryFind.getId());

        List<String> newRoles = new ArrayList<>(request.getRoles());
        List<String> newTeams = new ArrayList<>(request.getTeams());
        List<MemberRoleFactory> memberRoleFactoryList = new ArrayList<>();
        newRoles.forEach(role -> {
            MemberRoleFactory memberRoleFactory = new MemberRoleFactory();
            memberRoleFactory.setMemberFactoryId(memberFactoryFind.getId());
            memberRoleFactory.setRoleFactoryId(role);
            memberRoleFactoryList.add(memberRoleFactory);
        });
        List<MemberTeamFactory> memberTeamFactoryList = new ArrayList<>();
        newTeams.forEach(team -> {
            MemberTeamFactory memberTeamFactory = new MemberTeamFactory();
            memberTeamFactory.setMemberFactoryId(memberFactoryFind.getId());
            memberTeamFactory.setTeamFactoryId(team);
            memberTeamFactoryList.add(memberTeamFactory);
        });
        memberTeamFactoryRepository.saveAll(memberTeamFactoryList);
        memberRoleFactoryRepository.saveAll(memberRoleFactoryList);
        AdMemberFactoryCustom adMemberFactoryCustom = new AdMemberFactoryCustom();
        adMemberFactoryCustom.setId(memberFactoryFind.getId());
        adMemberFactoryCustom.setStatusMemberFactory(request.getStatus());
        adMemberFactoryCustom.setRoleMemberFactory(adMemberFactoryRepository.getNameRolesMemberFactory(memberFactoryFind.getId()));
        adMemberFactoryCustom.setNumberTeam(request.getTeams().size());
        return adMemberFactoryCustom;
    }

    @Override
    public AdDetailMemberFactoryResponse detailMemberFactory(String id) {
        MemberFactory memberFactoryFind = adMemberFactoryRepository.findById(id).get();
        if (Objects.isNull(memberFactoryFind)) {
            throw new RestApiException(Message.MEMBER_FACTORY_NOT_EXISTS);
        }
        SimpleResponse simpleResponse = convertRequestCallApiIdentity.handleCallApiGetUserById(memberFactoryFind.getMemberId());
        if (simpleResponse == null) {
            throw new RestApiException(Message.MEMBER_FACTORY_NOT_EXISTS);
        }
        AdDetailMemberFactoryResponse adDetailMemberFactoryResponse = new AdDetailMemberFactoryResponse();
        adDetailMemberFactoryResponse.setId(id);
        adDetailMemberFactoryResponse.setMemberId(memberFactoryFind.getMemberId());
        adDetailMemberFactoryResponse.setName(simpleResponse.getName());
        adDetailMemberFactoryResponse.setEmail(simpleResponse.getEmail());
        adDetailMemberFactoryResponse.setPicture(simpleResponse.getPicture());
        adDetailMemberFactoryResponse.setUserName(simpleResponse.getUserName());
        adDetailMemberFactoryResponse.setStatusMemberFactory(memberFactoryFind.getStatusMemberFactory() == StatusMemberFactory.HOAT_DONG ? 0 : 1);
        List<String> listRoles = adMemberFactoryRepository.getRolesMemberFactory(memberFactoryFind.getId());
        List<String> listTeams = adMemberFactoryRepository.getTeamsMemberFactory(memberFactoryFind.getId());
        adDetailMemberFactoryResponse.setRoles(listRoles);
        adDetailMemberFactoryResponse.setTeams(listTeams);
        return adDetailMemberFactoryResponse;
    }

    @Override
    public ByteArrayOutputStream exportTemplateExcel(HttpServletResponse response) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Template mẫu Import danh sách thành viên xưởng");

            CellStyle cellStyle = workbook.createCellStyle();

            XSSFFont font = (XSSFFont) workbook.createFont();
            font.setFontHeight(12);
            cellStyle.setFont(font);

            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("STT");
            cell.setCellStyle(cellStyle);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("Họ và tên");
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Email");
            cell2.setCellStyle(cellStyle);
            int rowNum = 1;

            workbook.write(outputStream);
            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ByteArrayOutputStream exportExcel(HttpServletResponse response, final AdFindMemberFactoryRequest request) {
        List<AdExcelMemberFactoryResponse> listRepo = adMemberFactoryRepository.getListMemberFactory(request);
        List<String> idList = listRepo.stream()
                .map(AdExcelMemberFactoryResponse::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = new ArrayList<>();
        if (idList != null && idList.size() > 0) {
            listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idList);
        }
        List<SimpleResponse> finalListResponse = listResponse;
        List<AdExcelMemberFactoryCustom> listCustom = new ArrayList<>();
        listRepo.forEach(repo -> {
            finalListResponse.forEach(xx -> {
                if (repo.getMemberId().equals(xx.getId())) {
                    AdExcelMemberFactoryCustom adExcelMemberFactoryCustom = new AdExcelMemberFactoryCustom();
                    adExcelMemberFactoryCustom.setStt(repo.getStt());
                    adExcelMemberFactoryCustom.setName(xx.getName());
                    adExcelMemberFactoryCustom.setRole(repo.getRoleMemberFactory());
                    adExcelMemberFactoryCustom.setTeam(repo.getMemberTeamFactory());
                    adExcelMemberFactoryCustom.setStatus(repo.getStatusMemberFactory());
                    adExcelMemberFactoryCustom.setUserName(xx.getUserName());
                    listCustom.add(adExcelMemberFactoryCustom);
                }
            });
        });
        return adExportExcelMemberFactory.exportExcel(response, listCustom);
    }
}
