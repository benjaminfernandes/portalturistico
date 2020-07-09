package br.gov.pr.guaira.portalturistico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.portalturistico.model.Hospedagem;
import br.gov.pr.guaira.portalturistico.repository.helper.HospedagensQueries;

public interface Hospedagens extends JpaRepository<Hospedagem, Long>, HospedagensQueries{

	public Optional<Hospedagem> findByNomeIgnoreCase(String nome);
}
