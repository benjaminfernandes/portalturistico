package br.gov.pr.guaira.portalturistico.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="hospedagem")
@DynamicUpdate
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Hospedagem implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long codigo;
	@NotNull(message = "Informe o tipo de hospedagem")
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_hospedagem")
	private TipoHospedagem tipoHospedagem;
	@NotBlank(message = "Informe o nome!")
	private String nome;
	@Column(name="content_type")
	private String contentType;
	@Embedded
	private Contato contato;
	@Embedded
	private Endereco endereco;
	@Column
	private String foto;
	@Column
	private String site;
	@Transient
	private boolean novaFoto;
	
	@PrePersist
	@PreUpdate
	private void toUpperCase() {
		this.nome = this.nome.toUpperCase();
	}
	
	public boolean isNova() {
		return codigo == null;
	}
	
	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "hotel-mock.png";
	}
	
	public String formataEndereco() {
		return this.endereco.getLogradouro() + ", " + this.endereco.getNumero() + " - " + this.endereco.getBairro();
	}	
}
