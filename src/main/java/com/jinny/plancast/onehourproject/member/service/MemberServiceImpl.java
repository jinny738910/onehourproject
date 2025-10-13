package com.jinny.plancast.onehourproject.member.servicce;


import com.jinny.plancast.onehourproject.member.repository.MemberRepository;
import com.jinny.plancast.onehourproject.member.repository.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public String join(String id, String name, String phoneNumber) {
        Member member = Member.builder()
                .id(id)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
        memberRepository.save(member);

        return "success";
    }

}
