package org.bwyou.springboot2.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.bwyou.springboot2.viewmodels.WebStatusMessageBody;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public class WebException extends RuntimeException {

	private static final long serialVersionUID = 3584021599514020963L;

	WebStatusMessageBody body;
	BindingResult bindingResult;

	public WebStatusMessageBody getBody() {
		return body;
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public WebException(HttpStatus status, WebStatusMessageBody body) {
		super(body.getMessage());
		body.setStatus(status.value());
		this.body = body;
	}
	
	public WebException(HttpStatus status, Exception ex) {
		super(ex);
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));	//���� �� Ʈ���̽� ���� �������� ����. ����� ��忡���� ���� �ְ� �ϴ� �͵� ���� ��
		body = new WebStatusMessageBody("E" + String.format("%03d", status.value())
							, ex.getMessage() != null ? ex.getMessage() : ex.toString(), /*errors.toString()*/"", "");
		body.setStatus(status.value());
	}
	
	public WebException(HttpStatus status, Exception ex, BindingResult bindingResult) {
		super(ex);
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		body = new WebStatusMessageBody("E" + String.format("%03d", status.value())
							, ex.getMessage() != null ? ex.getMessage() : ex.toString(), /*errors.toString()*/"", "");
		body.setStatus(status.value());
		this.bindingResult = bindingResult;
	}

	public WebException(WebStatusMessageBody body) {
		super(body.getMessage());
		this.body = body;
	}
	
	public WebException(Exception ex) {
		super(ex);
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		body = new WebStatusMessageBody("E" + String.format("%03d", HttpStatus.INTERNAL_SERVER_ERROR.value())
							, ex.getMessage() != null ? ex.getMessage() : ex.toString(), /*errors.toString()*/"", "");
		body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	public WebException() {
		super();
		body = new WebStatusMessageBody("E" + String.format("%03d", HttpStatus.INTERNAL_SERVER_ERROR.value())
							, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "", "");
		body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

}
