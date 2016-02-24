package com.dto;
public class JsonDto {
	
	private String status;
	
	
	private Object result;
	
	
	public JsonDto(String status) {
		super();
		this.status = status;
	}

	
	public JsonDto(String status, Object result) {
		super();
		this.status = status;
		this.result = result;
	}



	public JsonDto() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
