package com.example.SecurityApp.SecurityApplication.services;



import com.example.SecurityApp.SecurityApplication.dto.PostDto;

import java.util.List;


public interface PostService {

    List<PostDto> getAllPosts();

    PostDto createNewPost(PostDto inputpost);

    PostDto getPostById(Long postId);

    PostDto updatePost(PostDto inputpost, Long postId);
}
