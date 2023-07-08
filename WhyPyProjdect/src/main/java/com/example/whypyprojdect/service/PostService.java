package com.example.whypyprojdect.service;

import com.example.whypyprojdect.entity.Lecture;
import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.exception.NotFoundException;
import com.example.whypyprojdect.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public void updatePostData(Integer postId, String title, String contents) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = postOptional.orElseThrow(() -> new NotFoundException("Post not found"));
        post.setTitle(title);
        post.setContents(contents);
        postRepository.save(post);
    }
}
