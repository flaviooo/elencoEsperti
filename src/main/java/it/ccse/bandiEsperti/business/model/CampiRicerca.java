package it.ccse.bandiEsperti.business.model;

// Generated 6-lug-2015 16.18.03 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CampiRicerca generated by hbm2java
 */
@Entity
@Table(name = "campi_ricerca")
public class CampiRicerca implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String etichetta;
	private String nomeTabella;
	private String nomeColonna;
	private String tipo;
	private String command;
	
	private Set<AssCampiOperatori> assCampiOperatoris = new HashSet<AssCampiOperatori>(
			0);
	private Boolean lista;
	
	
	public CampiRicerca() {
	}

	public CampiRicerca(String etichetta, String nomeTabella,
			String nomeColonna, String tipo, String command, Boolean lista) {
		this.etichetta = etichetta;
		this.nomeTabella = nomeTabella;
		this.nomeColonna = nomeColonna;
		this.tipo = tipo;
		this.command=command;
		this.lista=lista;
	}

	public CampiRicerca(String etichetta, String nomeTabella,
			String nomeColonna, String tipo, String command, Boolean lista,
			Set<AssCampiOperatori> assCampiOperatoris) {
		this.etichetta = etichetta;
		this.nomeTabella = nomeTabella;
		this.nomeColonna = nomeColonna;
		this.tipo = tipo;
		this.assCampiOperatoris = assCampiOperatoris;
		this.command=command;
		this.lista=lista;
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

	@Column(name = "nome_tabella", nullable = false, length = 45)
	public String getNomeTabella() {
		return this.nomeTabella;
	}

	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}

	@Column(name = "nome_colonna", nullable = false, length = 45)
	public String getNomeColonna() {
		return this.nomeColonna;
	}

	public void setNomeColonna(String nomeColonna) {
		this.nomeColonna = nomeColonna;
	}

	@Column(name = "tipo", nullable = false, length = 45)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Column(name = "command", length = 45)
	public String getCommand() {
		return this.command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	@Column(name = "lista", nullable = false)
	public Boolean getLista() {
		return this.lista;
	}

	public void setLista(Boolean lista) {
		this.lista = lista;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "campiRicerca")
	public Set<AssCampiOperatori> getAssCampiOperatoris() {
		return this.assCampiOperatoris;
	}

	public void setAssCampiOperatoris(Set<AssCampiOperatori> assCampiOperatoris) {
		this.assCampiOperatoris = assCampiOperatoris;
	}

	
}