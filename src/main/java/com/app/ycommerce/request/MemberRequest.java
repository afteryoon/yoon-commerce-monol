package com.app.ycommerce.request;

import com.app.ycommerce.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequest {

	private String name;
	private String password;
	private String email;
	private String phone;
	private String address;

	public Member toUserEntity(String password, String username, String email, String address, String phoneNumber,
		String roles) {
		return Member.builder()
			.name(username)
			.email(email)
			.role(roles)
			.address(address)
			.phone(phoneNumber)
			.password(password)
			.build();
	}

	public MemberRequest(String username, String password, String email, String address, String phoneNumber) {
		this.name = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.phone = phoneNumber;
	}

}
