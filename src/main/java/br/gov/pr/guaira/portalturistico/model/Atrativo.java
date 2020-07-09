package br.gov.pr.guaira.portalturistico.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "atrativo")
@DynamicUpdate
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Atrativo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long codigo;
	@NotBlank(message = "Informe o nome do atrativo!")
	@Column
	private String nome;
	@Embedded
	private Endereco endereco;
	@Embedded
	private Contato contato;
	@Column(name="horario", length = 1000)
	private String horario;
	@Column(name="horario_html", length = 1000)
	private String horarioHtml;
	@Column(length = 10000)
	private String descricao;
	@Column(name="descricao_html", length = 15000)
	private String descricaoHtml;
	@Column(name="content_type_capa")
	private String contentTypeCapa;
	@Column
	private String fotoCapa;
	@Column(name="content_type_chamada")
	private String contentTypeChamada;
	@NotBlank(message = "Informe a foto de chamada!")
	@Column
	private String fotoChamada;
	@Column(name = "fotos_galeria", length = 10000)
	private String fotosGaleria; //usado para receber o JSON com o nome e contentType
	@Transient
	private boolean novaFotoChamada;
	@Transient
	private boolean novaFotoCapa;
	
	public boolean isNovo() {
		return this.codigo == null;
	}
}
