package com.app.ycommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.ycommerce.dto.MemberSignUpDTO;
import com.app.ycommerce.dto.MemberSignUpResponseDTO;
import com.app.ycommerce.dto.VerificationEmailDto;
import com.app.ycommerce.entity.Member;
import com.app.ycommerce.request.MemberRequest;
import com.app.ycommerce.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@ResponseBody
@RequestMapping("/api/member")

public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	// @PostMapping("/login") //로그인
	// public ResponseEntity<Member> findByEmail(@RequestBody MemberRequest memberRequest) {
	//
	// 	Member member = memberService.login(memberRequest);
	//
	// 	return ResponseEntity.status(HttpStatus.CREATED)
	// 		.contentType(MediaType.APPLICATION_JSON)
	// 		.body(member);
	// }
	@PostMapping("/signup") //회원가입
	public ResponseEntity<MemberSignUpResponseDTO> singUp(@RequestBody MemberSignUpDTO memberSignUpDTO) {
		Member member = memberService.signUp(memberSignUpDTO);
		MemberSignUpResponseDTO responseDto = new MemberSignUpResponseDTO(member);

		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(responseDto);
	}

	@PostMapping("/verificationEmail") //이메일 인증
	public ResponseEntity<VerificationEmailDto> verificationEmail(@RequestBody MemberRequest memberRequest) {
		System.out.println(memberRequest.getEmail());
		VerificationEmailDto verificationEmailDto = memberService.verificationEmail(memberRequest.getEmail());

		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(verificationEmailDto);
	}

}
