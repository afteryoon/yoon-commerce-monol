package com.app.ycommerce.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.ycommerce.dto.MemberSignUpDTO;
import com.app.ycommerce.entity.Member;
import com.app.ycommerce.repository.MemberRepository;

@Service
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

		this.memberRepository = memberRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public Member signUp(MemberSignUpDTO memberSignUpDTO) {

		if (memberRepository.existsByEmail(memberSignUpDTO.getEmail())) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}

		Member member = Member.builder()
			.email(memberSignUpDTO.getEmail())
			.password(bCryptPasswordEncoder.encode(memberSignUpDTO.getPassword()))
			.name(memberSignUpDTO.getName())
			.phone(memberSignUpDTO.getPhone())
			.address(memberSignUpDTO.getAddress())
			.role("ROLE_MEMBER")
			.createdAt(LocalDate.from(LocalDateTime.now()))
			.build();

		//        ModelMapper modelMapper = new ModelMapper();
		//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		//        Member member = modelMapper.map(memberSignUpDTO, Member.class);

		return memberRepository.save(member);
	}

}
