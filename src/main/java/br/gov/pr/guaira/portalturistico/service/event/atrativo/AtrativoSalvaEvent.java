package br.gov.pr.guaira.portalturistico.service.event.atrativo;

import org.springframework.util.StringUtils;

import br.gov.pr.guaira.portalturistico.model.Atrativo;

public class AtrativoSalvaEvent {

	public AtrativoSalvaEvent(Atrativo atrativo) {
		this.atrativo = atrativo;
	}
	
	private Atrativo atrativo;

	public Atrativo getAtrativo() {
		return atrativo;
	}

	public void setAtrativo(Atrativo atrativo) {
		this.atrativo = atrativo;
	}
	
	public boolean temFotoChamada() {
		return !StringUtils.isEmpty(atrativo.getFotoChamada());
	}
	
	public boolean isNovaFotoChamada() {
		return atrativo.isNovaFotoChamada();
	}
	
	public boolean temFotoCapa() {
		return !StringUtils.isEmpty(atrativo.getFotoCapa());
	}
	
	public boolean isNovaFotoCapa() {
		return atrativo.isNovaFotoCapa();
	}
	
	public boolean temFotoGaleria() {
		return !StringUtils.isEmpty(atrativo.getFotosGaleria());
	}
}
