package com.example.practicaljava.api.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse {
	private String errMsg;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime errTime;
	
	
	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String errMsg, LocalDateTime errTime) {
		super();
		this.errMsg = errMsg;
		this.errTime = errTime;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public LocalDateTime getErrTime() {
		return errTime;
	}

	public void setErrTime(LocalDateTime errTime) {
		this.errTime = errTime;
	}
}
