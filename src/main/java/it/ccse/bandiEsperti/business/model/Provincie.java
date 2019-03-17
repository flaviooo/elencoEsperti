package it.ccse.bandiEsperti.business.model;
// Generated 5-giu-2015 13.46.42 by Hibernate Tools 3.4.0.CR1

import it.ccse.bandiEsperti.business.command.interfaces.IRicerca;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Provincie generated by hbm2java
 */
@Entity
@Table(name = "provincie", uniqueConstraints = @UniqueConstraint(columnNames = "codice_provincia"))
public class Provincie implements IRicerca {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Regioni regioni;
	private String codiceProvincia;
	private String denominazione;
	private Set<Esperto> espertis = new HashSet<Esperto>();
	private Set<Citta> cittas = new HashSet<Citta>();

	public Provincie() {
	}

	public Provincie(Regioni regioni, String codiceProvincia,
			String denominazione) {
		this.regioni = regioni;
		this.codiceProvincia = codiceProvincia;
		this.denominazione = denominazione;
	}

	public Provincie(Regioni regioni, String codiceProvincia,
			String denominazione, Set<Esperto> espertis, Set<Citta> cittas) {
		this.regioni = regioni;
		this.codiceProvincia = codiceProvincia;
		this.denominazione = denominazione;
		this.espertis = espertis;
		this.cittas = cittas;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codice_regione", nullable = false, referencedColumnName="codice_regione")
	public Regioni getRegioni() {
		return this.regioni;
	}

	public void setRegioni(Regioni regioni) {
		this.regioni = regioni;
	}

	@Column(name = "codice_provincia", unique = true, nullable = false, length = 3)
	public String getCodiceProvincia() {
		return this.codiceProvincia;
	}

	public void setCodiceProvincia(String codiceProvincia) {
		this.codiceProvincia = codiceProvincia;
	}

	@Column(name = "denominazione", nullable = false, length = 45)
	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "provincie")
	public Set<Esperto> getEspertis() {
		return this.espertis;
	}

	public void setEspertis(Set<Esperto> espertis) {
		this.espertis = espertis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "provincie")
	public Set<Citta> getCittas() {
		return this.cittas;
	}

	public void setCittas(Set<Citta> cittas) {
		this.cittas = cittas;
	}

}
