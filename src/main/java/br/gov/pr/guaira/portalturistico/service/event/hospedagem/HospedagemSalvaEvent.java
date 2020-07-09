package br.gov.pr.guaira.portalturistico.service.event.hospedagem;

import org.springframework.util.StringUtils;

import br.gov.pr.guaira.portalturistico.model.Hospedagem;

public class HospedagemSalvaEvent {

	
	public HospedagemSalvaEvent(Hospedagem hospedagem) {
		this.hospedagem = hospedagem;
	}

	private Hospedagem hospedagem;

	public Hospedagem getHospedagem() {
		return hospedagem;
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(hospedagem.getFoto());
	}
	
	public boolean isNovaFoto() {
		return hospedagem.isNovaFoto();
	}
	
}
