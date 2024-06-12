package com.app.ycommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ycommerce.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Boolean existsByEmail(String email);

	Optional<Member> findByEmail(String email);

	Optional<Member> findById(Long memberId);

}