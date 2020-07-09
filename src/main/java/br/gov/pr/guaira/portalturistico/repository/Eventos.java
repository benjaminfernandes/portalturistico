package br.gov.pr.guaira.portalturistico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pr.guaira.portalturistico.model.Evento;

public interface Eventos extends JpaRepository<Evento, Long> {

}
