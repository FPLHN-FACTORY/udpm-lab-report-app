package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.portalprojects.core.common.base.TodoObject;
import com.labreportapp.portalprojects.core.member.model.request.MeChangeCoverTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateImageRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteImageRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateNameImageRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeImageResponse;
import com.labreportapp.portalprojects.core.member.repository.MeActivityRepository;
import com.labreportapp.portalprojects.core.member.repository.MeImageRepository;
import com.labreportapp.portalprojects.core.member.repository.MeTodoRepository;
import com.labreportapp.portalprojects.core.member.service.MeImageService;
import com.labreportapp.portalprojects.entity.Activity;
import com.labreportapp.portalprojects.entity.Image;
import com.labreportapp.portalprojects.entity.Todo;
import com.labreportapp.portalprojects.infrastructure.cloudinary.CloudinaryUploadImages;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.StatusImage;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import com.labreportapp.portalprojects.infrastructure.successnotification.ConstantMessageSuccess;
import com.labreportapp.portalprojects.infrastructure.successnotification.SuccessNotificationSender;
import com.labreportapp.portalprojects.util.CloundinaryUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeImageServiceImpl implements MeImageService {

    @Autowired
    private MeImageRepository meImageRepository;

    @Autowired
    private MeTodoRepository meTodoRepository;

    @Autowired
    private MeActivityRepository meActivityRepository;

    @Autowired
    private SuccessNotificationSender successNotificationSender;

    @Autowired
    private CloudinaryUploadImages cloudinaryUploadImages;

    @Override
    @Synchronized
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    public TodoObject add(@Valid MeCreateImageRequest request, StompHeaderAccessor headerAccessor) {

        Image image = new Image();
        image.setTodoId(request.getIdTodo());
        image.setNameImage(request.getNameFileOld());
        Integer countImage = meImageRepository.countImageByIdTodo(request.getIdTodo());
        if (countImage == 0) {
            image.setStatusImage(StatusImage.COVER);
        } else {
            image.setStatusImage(StatusImage.NO_COVER);
        }
        image.setNameFile(request.getUrlImage());
        Image newImage = meImageRepository.save(image);
        if (countImage == 0) {
            Optional<Todo> todoFind = meTodoRepository.findById(request.getIdTodo());
            if (!todoFind.isPresent()) {
                throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
            }
            todoFind.get().setImageId(newImage.getId());
            todoFind.get().setNameFile(request.getUrlImage());
            meTodoRepository.save(todoFind.get());
        }
        Activity activity = new Activity();
        activity.setProjectId(request.getProjectId());
        activity.setTodoListId(request.getIdTodoList());
        activity.setTodoId(request.getIdTodo());
        activity.setImageId(newImage.getId());
        activity.setMemberCreatedId(request.getIdUser());
        activity.setContentAction("đã thêm " + request.getNameFileOld() + " vào thẻ này");
        activity.setUrlImage(request.getUrlImage());

        successNotificationSender.senderNotification(ConstantMessageSuccess.TAI_ANH_LEN_THANH_CONG, headerAccessor);
        TodoObject todoObject = TodoObject.builder().data(newImage)
                .dataActivity(meActivityRepository.save(activity))
                .idTodoList(request.getIdTodoList())
                .idTodo(request.getIdTodo()).build();
        return todoObject;
    }

    @Override
    @Synchronized
    public String uploadFile(MultipartFile file) {
        try {
            return cloudinaryUploadImages.uploadImage(file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Image findById(String id) {
        return meImageRepository.findById(id).get();
    }

    @Override
    public List<MeImageResponse> getAllByIdTodo(String idTodo) {
        return meImageRepository.getAllByIdTodo(idTodo);
    }

    @Override
    @Synchronized
    @Transactional
    public Image updateNameImage(@Valid MeUpdateNameImageRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Image> imageFind = meImageRepository.findById(request.getId());
        if (!imageFind.isPresent()) {
            throw new MessageHandlingException(Message.IMAGE_NOT_EXISTS);
        }
        imageFind.get().setNameImage(request.getNameImage());
        successNotificationSender.senderNotification(ConstantMessageSuccess.CAP_NHAT_THANH_CONG, headerAccessor);
        return meImageRepository.save(imageFind.get());
    }

    @Override
    @Synchronized
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Transactional
    public TodoObject deleteImage(@Valid MeDeleteImageRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Todo> todoFind = meTodoRepository.findById(request.getIdTodo());
        if (request.getStatusImage().equals("0")) {
            if (!todoFind.isPresent()) {
                throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
            }
            todoFind.get().setImageId(null);
            todoFind.get().setNameFile(null);
            meTodoRepository.save(todoFind.get());
        }

        String publicId = CloundinaryUtils.extractPublicId(request.getNameFile());
        cloudinaryUploadImages.deleteImage(publicId);

        Activity activityFind = meActivityRepository.findActivityByIdImage(request.getId());
        activityFind.setUrlImage(null);
        activityFind.setImageId(null);
        meActivityRepository.save(activityFind);

        Activity activity = new Activity();
        activity.setProjectId(request.getProjectId());
        activity.setTodoListId(request.getIdTodoList());
        activity.setTodoId(request.getIdTodo());
        activity.setMemberCreatedId(request.getIdUser());
        activity.setContentAction("đã xóa " + request.getNameImage() + " khỏi thẻ này");
        Activity newActivity = meActivityRepository.save(activity);

        meImageRepository.deleteById(request.getId());
        successNotificationSender.senderNotification(ConstantMessageSuccess.XOA_THANH_CONG, headerAccessor);
        TodoObject todoObject = TodoObject.builder().data(meTodoRepository.save(todoFind.get()))
                .dataImage(request.getId())
                .dataActivity(newActivity)
                .idTodoList(request.getIdTodoList()).
                        idTodo(request.getIdTodo()).build();
        return todoObject;
    }

    @Override
    @Synchronized
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todoById", "detailTodosById", "todosByFilter"}, allEntries = true)
    @Transactional
    public TodoObject changeCoverTodo(@Valid MeChangeCoverTodoRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Todo> todoFind = meTodoRepository.findById(request.getIdTodo());
        if (!todoFind.isPresent()) {
            throw new MessageHandlingException(Message.TO_DO_NOT_EXISTS);
        }
        Optional<Image> imageFind = meImageRepository.findById(request.getIdImage());
        if (!imageFind.isPresent()) {
            throw new MessageHandlingException(Message.IMAGE_NOT_EXISTS);
        }
        if (request.getStatus().equals("0")) { // xóa khỏi cover
            todoFind.get().setImageId(null);
            todoFind.get().setNameFile(null);
            successNotificationSender.senderNotification(ConstantMessageSuccess.XOA_THANH_CONG, headerAccessor);
        } else {
            todoFind.get().setImageId(request.getIdImage());
            todoFind.get().setNameFile(request.getNameFile());
            meImageRepository.updateCoverOld(request.getIdTodo());
            successNotificationSender.senderNotification(ConstantMessageSuccess.CAP_NHAT_THANH_CONG, headerAccessor);
        }
        if (imageFind.get().getStatusImage().equals(StatusImage.COVER)) {
            imageFind.get().setStatusImage(StatusImage.NO_COVER);
        } else {
            imageFind.get().setStatusImage(StatusImage.COVER);
        }
        Image newImage = meImageRepository.save(imageFind.get());
        TodoObject todoObject = TodoObject.builder().data(meTodoRepository.save(todoFind.get()))
                .dataImage(newImage)
                .idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).build();
        return todoObject;
    }
}
