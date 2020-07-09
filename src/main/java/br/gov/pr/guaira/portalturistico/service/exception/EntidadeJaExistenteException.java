package br.gov.pr.guaira.portalturistico.service.exception;

public class EntidadeJaExistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadeJaExistenteException(String message) {
		super(message);
	}
}
