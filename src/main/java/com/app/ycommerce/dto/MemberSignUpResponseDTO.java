package com.app.ycommerce.dto;

import com.app.ycommerce.entity.Member;

import lombok.Getter;

@Getter
public class MemberSignUpResponseDTO {
	private int id;
	private String email;
	private String name;

	public MemberSignUpResponseDTO(Member member) {
		this.id = member.getId();
		this.email = member.getEmail();
		this.name = member.getName();
	}

}
