package br.gov.pr.guaira.portalturistico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.portalturistico.model.Gastronomia;
import br.gov.pr.guaira.portalturistico.repository.helper.GastronomiasQueries;

public interface Gastronomias extends JpaRepository<Gastronomia, Long>, GastronomiasQueries{

	public Optional<Gastronomia> findByNomeIgnoreCase(String nome);
}
