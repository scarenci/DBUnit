package br.com.DBUnit.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Terceiro")
@SequenceGenerator(name = "SEQ_TERCEIRO", sequenceName = "SEQ_TERCEIRO") 
public class Terceiro {

	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TERCEIRO")
	private Long id;
	
	private String nome;
	
	private Long cpf;
	
	private Long rg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Long getRg() {
		return rg;
	}

	public void setRg(Long rg) {
		this.rg = rg;
	}
}
