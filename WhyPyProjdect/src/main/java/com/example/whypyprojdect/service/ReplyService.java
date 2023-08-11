package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.entity.Recmd;
import com.example.whypyprojdect.entity.Reply;
import com.example.whypyprojdect.exception.NotFoundException;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.repository.PostRepository;
import com.example.whypyprojdect.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<Reply> getAllReplys() {
        return replyRepository.findAll();
    }

    public Reply getReplyById(Integer replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + replyId));
    }

    public Reply saveReplyData(Reply reply) {
        Reply replyEntity = replyRepository.save(reply);
        return replyEntity;
    }

    public void deleteReplyData(Integer replyId) {
        replyRepository.deleteById(replyId);
    }

    public void updateReplyData(Integer replyId, String comment) {
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        Reply reply = replyOptional.orElseThrow(() -> new NotFoundException("Reply not found"));
        reply.setComment(comment);
        replyRepository.save(reply);
    }

    public void setPostID(Reply reply, int postId) {
        reply.setPostId(postId);
    }

    public void setParentID(Reply reply, int parentId) {
        reply.setParentId(parentId);
    }

    public void setWriterId(Reply reply, Object writerName) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) writerName);
        if(memberEntity.isPresent()) {
            MemberDto member = MemberDto.toMemberDto(memberEntity.get());
            reply.setWriterId(member.getId());
        }
    }

    public List<Reply> getRepliesByPostId(int postId) {
        return replyRepository.findByPostId(postId);
    }

    public List<Reply> getRepliesByParentId(int parentId) {
        return replyRepository.findByParentId(parentId);
    }
}
