package cn.edu.whu.ProductManager.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import cn.edu.whu.ProductManager.exception.ProductException;

@ControllerAdvice
public class MyExceptionHandler {


	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ExceptionResponse> handleExceptions(ProductException exception, WebRequest webRequest) {
		ExceptionResponse response = new ExceptionResponse();
		response.setCode(exception.getStatus());
		response.setMessage("Product id: " + exception.getProductId() + " " + exception.getMessage());
		ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return entity;
	}

}

