package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.dto.QuestionDto;
import com.example.whypyprojdect.entity.Follow;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.entity.Question;
import com.example.whypyprojdect.exception.NotFoundException;
import com.example.whypyprojdect.repository.MemberImageRepository;
import com.example.whypyprojdect.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.whypyprojdect.repository.FollowRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service //스프링이 관리하는 스프링 빈으로 등록
@RequiredArgsConstructor
public class MemberService {
    //생성자 주입
    private final MemberRepository memberRepository;

    private final FollowRepository followRepository;

    private final MemberImageRepository memberImageRepository;

    public void save(MultipartFile image, MemberDto memberDto) throws Exception {
        //1. dto > entity 변환
        //2. repository의 save 메서드 호출

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);

        //memberEntity의 toMemberEntity메소드를 매개변수 memberDto 이용해서 호출
        //그리고 변환된 entity를 가져와야 하므로 Member Entity memberEntity= ~~

        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\memberImages";
        UUID uuid = UUID.randomUUID();
        String memberImage = uuid + "_" + image.getOriginalFilename();
        File saveFile = new File(filePath, memberImage);
        image.transferTo(saveFile);
        memberEntity.setMemberImage(memberImage);
        memberEntity.setImagePath("/memberImages/"+memberImage);

        memberImageRepository.save(memberEntity);
        //(jpa 제공하는) 래파지토리 save 메소드 호출
        //memberRepository의 save 메소드 호출 (조건: entity 객체를 넘겨줘야 함)
    }

    public void saveWithNoImage(MemberDto memberDto) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
        memberRepository.save(memberEntity);
    }

    public MemberDto login(MemberDto memberDto) {
        /*
          1.회원이 입력한 아아디로 db에서 조회를 함
          2.db에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byMemberName = memberRepository.findByMemberName(memberDto.getMemberName());
        if (byMemberName.isPresent()) {
            // 조회 결과가 있다 (해당 아이디를 가진 회원 정보가 있다)
            MemberEntity memberEntity=byMemberName.get();
            if (memberEntity.getMemberPassword().equals(memberDto.getMemberPassword())) {
                //login 성공
                //entity ->dto 변환 후 리턴
                MemberDto dto=MemberDto.toMemberDto(memberEntity);
                return dto;
            } else {
                //login 실패
                return null;
            }
        } else {
            //조회 결과가 없다(해당 아이디를 가진 회원이 없다)
            return null;
        }
    }

    public MemberDto updateForm(String myName) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberName(myName);
        if (optionalMemberEntity.isPresent()) {
            return MemberDto.toMemberDto((optionalMemberEntity.get()));
        } else {
            return null;
        }
    }

    public void update(MemberDto memberDto) {
        memberRepository.save(MemberEntity.toUpateMemberEntity(memberDto));
    }



//     // @param leaderNickname : 팔로우할 유저의 닉네임
//     // @param follower : 팔로우 유저
//    @Transactional
//    public void followUser(String leaderNickname, MemberEntity follower){
//        // 자기 자신을 팔로우 하는 경우 에러
////        if (follower.getNickname().equals(leaderNickname)){
////            throw CustomException.of(CustomErrorCode.INTERNAL_SERVER_ERROR, "leader and follower are same...?");
////        }
//        // 팔로잉 당하는 유저가 존재하는지 확인
//        MemberEntity leader = findByNicknameOrElseThrow(leaderNickname);
//        // 이미 팔로우가 되어 있는지 확인
//        followRepository.findByLeaderAndFollower(leader, follower).ifPresent(it->{
//            throw CustomException.of(CustomErrorCode.ALREADY_FOLLOWING);
//        });
//        // 팔로우 관계 저장
//        followRepository.save(Follow.of(leader, follower));
//    }
//    /**
//     * 특정 유저의 팔로잉 관계 조회하기
//     * @param targetNickname : 특정 유저의 닉네임
//     * @param followingType
//     * - LEADER : 특정 유저를 팔로잉 하는 팔로워들
//     * - FOLLOWER : 특정 유저가 팔로우하는 리더들
//     * @return User Dto
//     */
//    @Transactional(readOnly = true)
//    public Set<MemberDto> getUsersFollow(String targetNickname, FollowingType followingType){
//        if (followingType.equals(FollowingType.LEADER)){
//            return followRepository.findByLeader_Nickname(targetNickname)
//                    .stream().map(Follow::getFollower).map(MemberEntity::dto).collect(Collectors.toSet());
//        }
//        if (followingType.equals(FollowingType.FOLLOWER)){
//            return followRepository.findByFollower_Nickname(targetNickname)
//                    .stream().map(Follow::getLeader).map(MemberEntity::dto).collect(Collectors.toSet());
//        }
//        throw CustomException.of(CustomErrorCode.INTERNAL_SERVER_ERROR);
//    }
//    /**
//     * 언팔로우 기능
//     * @param leaderNickname : 팔로우된 사람의 닉네임
//     * @param follower : 팔로워
//     */
//    @Transactional
//    public void unFollow(String leaderNickname, Follow follower){
//        // 자기 자신을 언팔 하는 경우 에러
//        if (follower.getNickname().equals(leaderNickname)){
//            throw CustomException.of(CustomErrorCode.INTERNAL_SERVER_ERROR, "leader and follower are same...?");
//        }
//        Follow follow = followRepository
//                .findByLeaderAndFollower(findByNicknameOrElseThrow(leaderNickname), follower)
//                .orElseThrow(()->{throw CustomException.of(CustomErrorCode.FOLLOWING_NOT_FOUND);});
//        followRepository.delete(follow);
//    }
//}

}