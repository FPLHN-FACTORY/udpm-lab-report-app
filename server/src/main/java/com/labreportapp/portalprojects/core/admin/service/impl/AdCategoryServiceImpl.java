package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.portalprojects.core.admin.model.request.AdBaseCategoryRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindCategoryRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateCategoryRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdCategoryPesponse;
import com.labreportapp.portalprojects.core.admin.repository.AdCategoryRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectCategoryRepository;
import com.labreportapp.portalprojects.core.admin.service.AdCategoryService;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.entity.Category;
import com.labreportapp.portalprojects.entity.ProjectCategory;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import com.labreportapp.portalprojects.util.FormUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdCategoryServiceImpl implements AdCategoryService {

    @Autowired
    private AdCategoryRepository adCategoryRepository;

    @Autowired
    private AdProjectCategoryRepository adProjectCategoryRepository;

    private FormUtils formUtils = new FormUtils();

    private List<AdCategoryPesponse> adCategoryPesponseList;

    @Override
    public List<Category> getAllCategory(Pageable pageable) {
        return adCategoryRepository.getAllCategory(pageable);
    }

    @Override
    public List<Category> findAll() {
        return adCategoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category addCategory(@Valid AdBaseCategoryRequest adBaseCategoryRequest) {
        LocalDateTime time = LocalDateTime.now();
        String code = "Cate_" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
                + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();
        adBaseCategoryRequest.setCode(code);
        Category category = formUtils.convertToObject(Category.class, adBaseCategoryRequest);
        return adCategoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(@Valid AdUpdateCategoryRequest adUpdateCategoryRequest) {
        Optional<Category> findCategoryById = adCategoryRepository.findById(adUpdateCategoryRequest.getId());
        if (!findCategoryById.isPresent()) {
            throw new RestApiException(Message.CATEGORY_NOT_EXISTS);
        }
        Category category = findCategoryById.get();
        category.setCode(adUpdateCategoryRequest.getCode());
        category.setName(adUpdateCategoryRequest.getName());
        return adCategoryRepository.save(category);
    }

    @Override
    public PageableObject<AdCategoryPesponse> searchCategory(final AdFindCategoryRequest adFindCategoryRequest) {
        Pageable pageable = PageRequest.of(adFindCategoryRequest.getPage() - 1, adFindCategoryRequest.getSize());
        Page<AdCategoryPesponse> adCategoryPesponses = adCategoryRepository.searchCategory(adFindCategoryRequest, pageable);
        adCategoryPesponseList = adCategoryPesponses.stream().toList();
        return new PageableObject<>(adCategoryPesponses);
    }

    @Override
    public Category findCategoryById(String id) {
        Optional<Category> findCategory = adCategoryRepository.findById(id);
        if (!findCategory.isPresent()) {
            throw new RestApiException(Message.CATEGORY_NOT_EXISTS);
        }
        return findCategory.get();
    }

    @Override
    public List<Category> getAllByIdCate() {
        return adCategoryRepository.getAllByIdCate();
    }

    @Override
    @Transactional
    public String deleteCategory(String id) {
        Optional<Category> optional = adCategoryRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RestApiException(Message.CATEGORY_NOT_EXISTS);
        }
        List<ProjectCategory> listKey = adProjectCategoryRepository.findAllByCategoryId(id);
        if (listKey != null && listKey.size() > 0) {
            throw new RestApiException(Message.USING_CATEGORY_CAN_NOT_DELETE);
        }
        adCategoryRepository.delete(optional.get());
        return id;
    }
}
