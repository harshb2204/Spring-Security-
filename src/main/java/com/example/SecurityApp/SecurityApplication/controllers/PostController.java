package com.example.SecurityApp.SecurityApplication.controllers;


import com.example.SecurityApp.SecurityApplication.dto.PostDto;
import com.example.SecurityApp.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/posts")
public class PostController {
    private final PostService postService; //use interface


    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }


    @GetMapping(path = "/{postId}")
    public PostDto getPostById(@PathVariable Long postId ){
        return postService.getPostById(postId);

    }

    @PostMapping
    public PostDto createNewPost(@RequestBody PostDto inputpost){
        return postService.createNewPost(inputpost);
    }

    @PutMapping(path = "/{postId}")
    public PostDto updatePost(@RequestBody PostDto inputpost, @PathVariable Long postId){
        return postService.updatePost(inputpost, postId);
    }
}
