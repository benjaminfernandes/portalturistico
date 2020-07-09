package br.gov.pr.guaira.portalturistico.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

import lombok.Data;

@Data
@Embeddable
public class Contato implements Serializable{

	private static final long serialVersionUID = 1L;

	private String celular;
	private String telefone;
	@Email
	private String email;
	
}
