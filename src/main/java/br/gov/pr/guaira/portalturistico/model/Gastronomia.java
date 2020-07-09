package br.gov.pr.guaira.portalturistico.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="gastronomia")
@DynamicUpdate
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Gastronomia implements Serializable{

	private static final long serialVersionUID = 642711719084882078L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long codigo;
	@NotBlank(message = "Nome é obrigatório!")
	@Column
	private String nome;
	@Embedded
	private Endereco endereco;
	@Embedded
	private Contato contato;
	@Size(min = 1, message = "Selecione pelo menos um grupo!")
	@ManyToMany
	@JoinTable(name="gastronomia_tipo", joinColumns = @JoinColumn(name = "codigo_gastronomia"),
	inverseJoinColumns = @JoinColumn(name = "codigo_tipo_gastronomia"))
	private List<TipoGastronomia> tiposGastronomias;
	@Column(name="content_type")
	private String contentType;
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
	public boolean isNovo() {
		return this.codigo == null;
	}
	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "gastronomia-mock.png";
	}	
	public String formataEndereco() {
		return this.endereco.getLogradouro() + ", " + this.endereco.getNumero() + " - " + this.endereco.getBairro();
	}
}
