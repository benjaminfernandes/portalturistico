package br.gov.pr.guaira.portalturistico.repository.filter;

import br.gov.pr.guaira.portalturistico.model.TipoHospedagem;

public class HospedagemFilter {

	private String nome;
	private TipoHospedagem tipoHospedagem;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public TipoHospedagem getTipoHospedagem() {
		return tipoHospedagem;
	}
	public void setTipoHospedagem(TipoHospedagem tipoHospedagem) {
		this.tipoHospedagem = tipoHospedagem;
	}
	
	
}
