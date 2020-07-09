package br.gov.pr.guaira.portalturistico.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.portalturistico.model.Gastronomia;
import br.gov.pr.guaira.portalturistico.repository.filter.GastronomiaFilter;

public interface GastronomiasQueries {

	public Page<Gastronomia> filtrar(GastronomiaFilter gastronomiaFilter, Pageable pageable);
	
	public Gastronomia buscaComTipos(Long codigo);
}
