package it.ccse.bandiEsperti.business.model;


import it.ccse.bandiEsperti.business.command.interfaces.IRicerca;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "temi")
public class Tema implements IRicerca{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private Set<Specializzazione> specializzazioni;
	private Set<Associazioni> associazionis = new HashSet<Associazioni>(0);
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false, length = 80)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSpecializzazioni(Set<Specializzazione> specializzazioni) {
		this.specializzazioni = specializzazioni;
	}

	@OneToMany(mappedBy = "tema")
	public Set<Specializzazione> getSpecializzazioni() {
		return specializzazioni;
	}
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tema")
	public Set<Associazioni> getAssociazionis() {
		return this.associazionis;
	}
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tema")
	public void setAssociazionis(Set<Associazioni> associazionis) {
		this.associazionis = associazionis;
	}

	
	@Transient
	public String getDenominazione() {
		return nome;
	}
}
