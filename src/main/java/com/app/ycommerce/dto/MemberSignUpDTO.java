package com.app.ycommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSignUpDTO {

	private int id;
	private String name;
	private String password;
	private String email;
	private String phone;
	private String address;
	private String verificationCode;

}
