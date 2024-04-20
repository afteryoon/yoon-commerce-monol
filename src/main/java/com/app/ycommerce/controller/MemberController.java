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
import com.app.ycommerce.dto.MemberSignUpResponseDto;
import com.app.ycommerce.entity.Member;
import com.app.ycommerce.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Slf4j
@RestController
@ResponseBody
@RequestMapping("/api/member")
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/signup")
	public ResponseEntity<MemberSignUpResponseDto> singUp(@RequestBody MemberSignUpDTO memberSignUpDTO) {
		Member member = memberService.signUp(memberSignUpDTO);
		MemberSignUpResponseDto responseDto = new MemberSignUpResponseDto(member);
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(responseDto);
	}

}
