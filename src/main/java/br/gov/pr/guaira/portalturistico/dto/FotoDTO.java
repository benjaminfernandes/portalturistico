package br.gov.pr.guaira.portalturistico.dto;

public class FotoDTO {

	private String nome;
	private String contentType;
	private boolean fotoNova;
	
	public FotoDTO(String nome, String contentType, boolean fotoNova) {
		this.nome = nome;
		this.contentType = contentType;
		this.fotoNova = fotoNova;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public boolean isFotoNova() {
		return fotoNova;
	}
	public void setFotoNova(boolean fotoNova) {
		this.fotoNova = fotoNova;
	}
	
}
