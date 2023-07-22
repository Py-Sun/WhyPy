package com.example.whypyprojdect.service;

import com.example.whypyprojdect.entity.Lecture;
import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.exception.NotFoundException;
import com.example.whypyprojdect.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

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

//    public Post savePostData(Post post) {
//        Post postEntity = postRepository.save(post);
//        return postEntity;
//    }

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

    public void createPostData(Post post , MultipartFile image) throws Exception{
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\postImages";
        UUID uuid = UUID.randomUUID();
        String imageName = uuid + "_" + image.getOriginalFilename();
        File saveFile = new File(projectPath, imageName);
        image.transferTo(saveFile);

        post.setImageName(imageName);
        post.setImagePath("/postImages/" + imageName);
        postRepository.save(post);
    }
}
