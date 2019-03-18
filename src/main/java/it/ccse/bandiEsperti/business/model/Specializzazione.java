package it.ccse.bandiEsperti.business.model;


import it.ccse.bandiEsperti.business.command.interfaces.IRicerca;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "specializzazioni")
public class Specializzazione implements IRicerca{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Tema tema;
	private String nome;
	private Set<Competenza> competenze;
	//private Set<Pubblicazione> pubblicaziones = new HashSet<Pubblicazione>(0);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinColumn(name = "id_temi")
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	@Column(name = "nome", nullable = false, length = 80)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(mappedBy = "specializzazione")
	public Set<Competenza> getCompetenze() {
		return competenze;
	}

	public void setCompetenze(Set<Competenza> competenze) {
		this.competenze = competenze;
	}
	
	/**
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "specializzazione")
	public Set<Pubblicazione> getPubblicaziones() {
		return this.pubblicaziones;
	}

	public void setPubblicaziones(Set<Pubblicazione> pubblicaziones) {
		this.pubblicaziones = pubblicaziones;
	}
	**/
	
	@Transient
	public String getDenominazione() {
		return nome;
	}

}
