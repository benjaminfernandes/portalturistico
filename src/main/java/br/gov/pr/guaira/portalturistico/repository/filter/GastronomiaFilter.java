package br.gov.pr.guaira.portalturistico.repository.filter;

import java.util.List;

import br.gov.pr.guaira.portalturistico.model.TipoGastronomia;

public class GastronomiaFilter {

	private String nome;
	private List<TipoGastronomia> tiposGastronomias;
	private TipoGastronomia tipoGastronomia;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<TipoGastronomia> getTiposGastronomias() {
		return tiposGastronomias;
	}
	public void setTiposGastronomias(List<TipoGastronomia> tiposGastronomias) {
		this.tiposGastronomias = tiposGastronomias;
	}
	public TipoGastronomia getTipoGastronomia() {
		return tipoGastronomia;
	}
	public void setTipoGastronomia(TipoGastronomia tipoGastronomia) {
		this.tipoGastronomia = tipoGastronomia;
	}
	
}
