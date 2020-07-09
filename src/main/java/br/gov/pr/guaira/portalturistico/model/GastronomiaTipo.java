package br.gov.pr.guaira.portalturistico.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "gastronomia_tipo")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GastronomiaTipo {

	@EmbeddedId
	@EqualsAndHashCode.Include
	private GastronomiaTipoId id;
	
}
