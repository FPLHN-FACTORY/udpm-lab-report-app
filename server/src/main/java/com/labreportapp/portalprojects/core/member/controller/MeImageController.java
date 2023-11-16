package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.DesVarProjectIdAndPeriodIdRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeChangeCoverTodoRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateImageRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteImageRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateNameImageRequest;
import com.labreportapp.portalprojects.core.member.service.MeImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/member/image")
public class MeImageController {

    @Autowired
    private MeImageService meImageService;

    @MessageMapping("/create-image/{projectId}/{periodId}")
    @SendTo("/portal-projects/create-image/{projectId}/{periodId}")
    public ResponseObject create(@RequestBody MeCreateImageRequest request,
                                 @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                 StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meImageService.add(request, headerAccessor));
    }

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestBody MultipartFile file) {
        return new ResponseEntity(meImageService.uploadFile(file), HttpStatus.CREATED);
    }

    @GetMapping("/detail/{id}")
    public ResponseObject findById(@PathVariable("id") String id) {
        return new ResponseObject(meImageService.findById(id));
    }

    @GetMapping("/{idTodo}")
    public ResponseObject getAllByIdTodo(@PathVariable("idTodo") String idTodo) {
        return new ResponseObject(meImageService.getAllByIdTodo(idTodo));
    }

    @MessageMapping("/update-name-image/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-name-image/{projectId}/{periodId}")
    public ResponseObject updateNameImage(@RequestBody MeUpdateNameImageRequest request,
                                          @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                          StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meImageService.updateNameImage(request, headerAccessor));
    }

    @MessageMapping("/delete-image/{projectId}/{periodId}")
    @SendTo("/portal-projects/delete-image/{projectId}/{periodId}")
    public ResponseObject deleteImage(@RequestBody MeDeleteImageRequest request,
                                      @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                      StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meImageService.deleteImage(request, headerAccessor));
    }

    @MessageMapping("/change-cover-image/{projectId}/{periodId}")
    @SendTo("/portal-projects/change-cover-image/{projectId}/{periodId}")
    public ResponseObject changeCoverTodo(@RequestBody MeChangeCoverTodoRequest request,
                                          @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                          StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meImageService.changeCoverTodo(request, headerAccessor));
    }
}
