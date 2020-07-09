package br.gov.pr.guaira.portalturistico.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Embeddable
@Data
public class GastronomiaTipoId implements Serializable{

	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="codigo_gastronomia")
	private Gastronomia gastronomia;
	@ManyToOne
	@JoinColumn(name="codigo_tipo_gastronomia")
	private TipoGastronomia tipoGastronomia;
	
}
