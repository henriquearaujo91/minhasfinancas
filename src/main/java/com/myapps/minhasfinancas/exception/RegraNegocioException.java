package com.myapps.minhasfinancas.exception;

public class RegraNegocioException extends RuntimeException {

	private static final long serialVersionUID = -7298419257488787576L;

	public RegraNegocioException(String mensagem) {
		super(mensagem);
	}
}
