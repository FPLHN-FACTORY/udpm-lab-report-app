package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeCreatePostRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindPostClassRepquest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdatePostRequest;
import com.labreportapp.labreport.core.teacher.model.response.TePostTeacherResponse;
import com.labreportapp.labreport.core.teacher.service.TePostService;
import com.labreportapp.labreport.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/post")
public class TePostController {

    @Autowired
    private TePostService tePostService;

    @GetMapping("")
    public ResponseObject searchTePost(final TeFindPostClassRepquest repquest) {
        PageableObject<TePostTeacherResponse> pageList = tePostService.searchPagePost(repquest);
        return new ResponseObject(pageList);
    }

    @PostMapping("")
    public ResponseObject createPost(@RequestBody TeCreatePostRequest repquest) {
        TePostTeacherResponse post = tePostService.create(repquest);
        return new ResponseObject(post);
    }

    @PutMapping("")
    public ResponseObject updatePost(@RequestBody TeUpdatePostRequest repquest) {
        TePostTeacherResponse post = tePostService.update(repquest);
        return new ResponseObject(post);
    }

    @DeleteMapping("/{id}")
    public ResponseObject deletePostById(@PathVariable String id) {
        Post post = tePostService.deleteById(id);
        return new ResponseObject(post);
    }
}
