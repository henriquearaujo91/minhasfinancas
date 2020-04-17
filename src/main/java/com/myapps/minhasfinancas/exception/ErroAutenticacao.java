package com.myapps.minhasfinancas.exception;

public class ErroAutenticacao extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6059466154505772468L;

	public ErroAutenticacao(String mensagem) {
		super(mensagem);
	}
}
