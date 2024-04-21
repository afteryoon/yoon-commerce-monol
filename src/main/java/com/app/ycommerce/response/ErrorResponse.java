package com.app.ycommerce.response;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

	private int status; // HTTP 상태 코드
	private String message;
	private String path; // 요청 URL 경로
	private Timestamp timestamp; // 에러 발생 시간

	public ErrorResponse(int status, String message, String path) {
		this.status = status;
		this.message = message;
		this.path = path;
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}

	public ErrorResponse(String message) {
		this.message = message;
	}

}
