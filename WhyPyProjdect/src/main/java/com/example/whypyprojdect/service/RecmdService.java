package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.entity.Recmd;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.repository.PostRepository;
import com.example.whypyprojdect.repository.RecmdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecmdService {
    private final RecmdRepository recmdRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<Recmd> getAllRecmds() {
        return recmdRepository.findAll();
    }

    public Recmd getRecmdById(Integer recmdId) {
        return recmdRepository.findById(recmdId)
                .orElseThrow(() -> new NoSuchElementException("Recmd not found with id: " + recmdId));
    }

    public Recmd saveRecmdData(Recmd recmd) {
        Recmd recmdEntity = recmdRepository.save(recmd);
        return recmdEntity;
    }

    public void setMemberID(Recmd recmd, Object writerName) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) writerName);
        if(memberEntity.isPresent()) {
            MemberDto member = MemberDto.toMemberDto(memberEntity.get());
            recmd.setMemberId(member.getId());
        }
    }

    public void setPostID(Recmd recmd, int postId) {
        recmd.setPostId(postId);
    }

    public void deleteRecmdData(Integer recmdId) {
        recmdRepository.deleteById(recmdId);
    }
}
