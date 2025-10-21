package com.jinny.plancast.onehourproject.member.repository;

import com.jinny.plancast.onehourproject.member.repository.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
