package br.gov.pr.guaira.portalturistico.service.event.gastronomia;

import org.springframework.util.StringUtils;

import br.gov.pr.guaira.portalturistico.model.Gastronomia;

public class GastronomiaSalvaEvent {

	public GastronomiaSalvaEvent(Gastronomia gastronomia) {
		this.gastronomia = gastronomia;
	}
	
	private Gastronomia gastronomia;
	
	public Gastronomia getGastronomia() {
		return gastronomia;
	}

	public void setGastronomia(Gastronomia gastronomia) {
		this.gastronomia = gastronomia;
	}

	public boolean temFoto() {
		return !StringUtils.isEmpty(gastronomia.getFoto());
	}
	
	public boolean isNovaFoto() {
		return gastronomia.isNovaFoto();
	}
}
