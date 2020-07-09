package br.gov.pr.guaira.portalturistico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.portalturistico.model.TipoGastronomia;
import br.gov.pr.guaira.portalturistico.repository.helper.TiposGastronomiasQueries;

public interface TiposGastronomias extends JpaRepository<TipoGastronomia, Long>, TiposGastronomiasQueries {

	public Optional<TipoGastronomia> findByNomeIgnoreCase(String nome);
	
}
