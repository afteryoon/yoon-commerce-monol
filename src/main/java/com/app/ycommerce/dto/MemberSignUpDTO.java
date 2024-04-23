package com.app.ycommerce.dto;

import com.app.ycommerce.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSignUpDTO {

	private Long id;
	private String name;
	private String password;
	private String email;
	private String phone;
	private Address address;
	private String verificationCode;

}
