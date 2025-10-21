package com.jinny.plancast.onehourproject.member.service;


import com.jinny.plancast.onehourproject.member.controller.dto.JoinRequest;
import com.jinny.plancast.onehourproject.member.repository.MemberRepository;
import com.jinny.plancast.onehourproject.member.repository.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public String join(JoinRequest joinRequest) {
        Member member = Member.builder()
                .id(joinRequest.getId())
                .name(joinRequest.getName())
                .phoneNumber(joinRequest.getPhoneNumber())
                .build();
        memberRepository.save(member);

        return "success";
    }

}
