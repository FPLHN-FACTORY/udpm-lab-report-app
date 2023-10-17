//package com.labreportapp.labreport.core.admin.controller;
//
//import com.labreportapp.labreport.core.admin.model.request.*;
//import com.labreportapp.labreport.core.admin.model.response.AdTypeProjectResponse;
//import com.labreportapp.labreport.core.admin.service.AdTypeProjectService;
//import com.labreportapp.labreport.core.common.base.PageableObject;
//import com.labreportapp.labreport.core.common.base.ResponseObject;
//import com.labreportapp.portalprojects.entity.TypeProject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @author quynhncph26201
// */
//@RestController
//@RequestMapping("/admin/type-project")
//@CrossOrigin(origins = {"*"}, maxAge = -1)
//public class AdTypeProjectController {
//    @Autowired
//    private AdTypeProjectService adTypeProjectService;
//
//    @GetMapping("/page/{page}")
//    public ResponseEntity<?> getAllTypeProject(@PathVariable int page) {
//        Pageable pageResquest = PageRequest.of(page - 1, 5);
//        List<TypeProject> TypeProjectList = adTypeProjectService.findAllTypeProject(pageResquest);
//        return ResponseEntity.ok(TypeProjectList);
//    }
//
//    @GetMapping("")
//    public ResponseObject viewTypeProject(@ModelAttribute final AdFindTypeProject request) {
//        return new ResponseObject((adTypeProjectService.searchTypeProject(request)));
//    }
//
//    @GetMapping("/search")
//    public ResponseObject searchTypeProject(final AdFindTypeProject request) {
//        PageableObject<AdTypeProjectResponse> listTypeProject = adTypeProjectService.searchTypeProject(request);
//        return new ResponseObject(listTypeProject);
//    }
//
//    @PostMapping("/add")
//    public ResponseObject addTypeProject(@RequestBody AdCreateTypeProjectRequest obj) {
//        return new ResponseObject(adTypeProjectService.createTypeProject(obj));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseObject deleteTypeProject(@PathVariable("id") String id) {
//        return new ResponseObject(adTypeProjectService.deleteTypeProject(id));
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseObject updateTypeProject(@PathVariable("id") String id,
//                                              @RequestBody AdUpdateTypeProjectRequest obj) {
//        obj.setId(id);
//        return new ResponseObject(adTypeProjectService.updateTypeProject(obj));
//    }
//
//}
