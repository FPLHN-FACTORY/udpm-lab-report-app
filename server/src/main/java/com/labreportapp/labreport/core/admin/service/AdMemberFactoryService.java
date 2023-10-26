package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdFindMemberFactoryRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMemberFactoryRequest;
import com.labreportapp.labreport.core.admin.model.response.AdDetailMemberFactoryResponse;
import com.labreportapp.labreport.core.admin.model.response.AdMemberFactoryCustom;
import com.labreportapp.labreport.core.common.base.ImportExcelResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdMemberFactoryService {

    PageableObject<AdMemberFactoryCustom> getPage(final AdFindMemberFactoryRequest request);

    List<SimpleEntityProjection> getRoles();

    List<SimpleEntityProjection> getTeams();

    Integer getNumberMemberFactory();

    AdMemberFactoryCustom addMemberFactory(String email);

    AdMemberFactoryCustom updateMemberFactory(@Valid AdUpdateMemberFactoryRequest request);

    AdDetailMemberFactoryResponse detailMemberFactory(String id);

    ByteArrayOutputStream exportTemplateExcel(HttpServletResponse response);

    ByteArrayOutputStream exportExcel(HttpServletResponse response, final AdFindMemberFactoryRequest request);

    ImportExcelResponse importExcel(MultipartFile multipartFile);
}
