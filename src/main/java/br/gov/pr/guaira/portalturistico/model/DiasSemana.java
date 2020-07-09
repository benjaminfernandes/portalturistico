package br.gov.pr.guaira.portalturistico.model;

public enum DiasSemana {
	
	SEGUNDA("Segunda"), TERCA("Terça"), QUARTA("Quarta"), QUINTA("Quinta"), SEXTA("Sexta"), SABADO("Sábado"), DOMINGO("Domingo");
	
	private DiasSemana(String descricao) {
		this.descricao = descricao;
	}
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
