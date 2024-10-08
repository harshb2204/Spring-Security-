package com.example.SecurityApp.SecurityApplication.services;


import com.example.SecurityApp.SecurityApplication.dto.PostDto;
import com.example.SecurityApp.SecurityApplication.entities.PostEntity;
import com.example.SecurityApp.SecurityApplication.entities.User;
import com.example.SecurityApp.SecurityApplication.exceptions.ResourceNotFound;
import com.example.SecurityApp.SecurityApplication.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
@ToString

public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream().map(postEntity -> modelMapper.map(postEntity, PostDto.class)).toList();
    }

    @Override
    public PostDto createNewPost(PostDto inputpost) {

        PostEntity postEntity = modelMapper.map(inputpost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDto.class);
    }

    @Override
    public PostDto getPostById(Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user {}", user);
        PostEntity postEntity = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFound("Post not found with id"+ postId));
        return modelMapper.map(postEntity, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto inputpost, Long postId) {
        PostEntity olderpost = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFound("Post not found with id"+ postId));
        inputpost.setId(postId);
        modelMapper.map(inputpost, olderpost);
        PostEntity savedEntity = postRepository.save(olderpost);
        return modelMapper.map(savedEntity, PostDto.class);
    }
}
