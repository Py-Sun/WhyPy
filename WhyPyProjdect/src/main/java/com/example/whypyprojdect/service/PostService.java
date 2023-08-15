package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.exception.NotFoundException;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.repository.PostRepository;
import com.example.whypyprojdect.repository.RecmdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final RecmdRepository recmdRepository;

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

    public void setWriterID(Post post, Object writerName) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) writerName);
        if(memberEntity.isPresent()) {
            MemberDto member = MemberDto.toMemberDto(memberEntity.get());
            post.setWriterID(member.getId());
        }
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

    public int getRecmdCountForOneDay(Integer postId) {
        LocalDateTime beforeDay = LocalDateTime.now().minusDays(7); //테스트
        String beforeDayToString = beforeDay.toString();
        return recmdRepository.countByPostIdAndRecmdDateAfter(postId, beforeDayToString);
    }

    public List<Post> searchPost(String keyword) {
        return postRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
