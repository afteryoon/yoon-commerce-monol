package com.app.ycommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ycommerce.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Boolean existsByEmail(String email);

	Member findByEmail(String email);

}