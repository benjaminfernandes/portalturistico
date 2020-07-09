package br.gov.pr.guaira.portalturistico.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Endereco implements Serializable{
	private static final long serialVersionUID = 1L;

	private String logradouro;
	private String numero;
	private String bairro;
	private String complemento;
	private String cep;
	
}
