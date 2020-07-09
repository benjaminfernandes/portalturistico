package br.gov.pr.guaira.portalturistico.model;

public class Galeria {
	
	public Galeria() {
	}
	
	public Galeria(String nome, String contentType) {
		this.nome = nome;
		this.contentType = contentType;
	}
	
	private String nome;
	private String contentType;
	private boolean fotoNova;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentTypeGaleria) {
		this.contentType = contentTypeGaleria;
	}

	public boolean isFotoNova() {
		return fotoNova;
	}

	public void setFotoNova(boolean fotoNova) {
		this.fotoNova = fotoNova;
	}
	
}
