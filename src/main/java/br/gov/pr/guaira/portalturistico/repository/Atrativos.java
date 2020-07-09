package br.gov.pr.guaira.portalturistico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.portalturistico.model.Atrativo;
import br.gov.pr.guaira.portalturistico.repository.helper.AtrativosQueries;

public interface Atrativos extends JpaRepository<Atrativo, Long>, AtrativosQueries {

	public Optional<Atrativo> findByNomeIgnoreCase(String nome);
}
