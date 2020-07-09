package br.gov.pr.guaira.portalturistico.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="tipo_gastronomia")
@DynamicUpdate
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoGastronomia implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long codigo;
	@NotBlank(message = "Informe o nome!")
	@Column
	private String nome;
	
	@PrePersist
	@PreUpdate
	private void toUpperCase() {
		if(this.nome != null) {
			this.nome = this.nome.toUpperCase();
		}
	}
	
	public boolean isNovo() {
		return this.codigo == null;
	}
}
