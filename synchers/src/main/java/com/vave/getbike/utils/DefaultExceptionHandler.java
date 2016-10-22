package com.vave.getbike.utils;


import com.vave.getbike.exception.AdzShopException;

public class DefaultExceptionHandler implements ExceptionHandler {

	@Override
	public void handleException(Exception rootCause) {
		rootCause.printStackTrace();
		throw new AdzShopException(rootCause);
	}
}
