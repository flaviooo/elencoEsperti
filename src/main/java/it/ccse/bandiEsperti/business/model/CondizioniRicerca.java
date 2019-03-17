package it.ccse.bandiEsperti.business.model;

// Generated 7-lug-2015 10.30.04 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CondizioniRicerca generated by hbm2java
 */
@Entity
@Table(name = "condizioni_ricerca")
public class CondizioniRicerca implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String etichetta;
	private String nome;

	public CondizioniRicerca() {
	}

	public CondizioniRicerca(String etichetta, String nome) {
		this.etichetta = etichetta;
		this.nome = nome;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "etichetta", nullable = false, length = 45)
	public String getEtichetta() {
		return this.etichetta;
	}

	public void setEtichetta(String etichetta) {
		this.etichetta = etichetta;
	}

	@Column(name = "nome", nullable = false, length = 45)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
