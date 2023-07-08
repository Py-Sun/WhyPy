package com.example.whypyprojdect.service;

import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + postId));
    }

    public Post savePostData(Post post) {
        Post postEntity = postRepository.save(post);
        return postEntity;
    }

    public void deletePostData(Integer postId) {
        postRepository.deleteById(postId);
    }
}
