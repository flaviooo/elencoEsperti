package it.ccse.bandiEsperti.business.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="carta_identita_esperto")
public class CartaIdentitaEsperto implements Serializable{
	
	private int idEsperto;
	private Esperto esperto;
	private byte[] allegato;
	private String contentType;
	private String estensione;
	
	@Id
	@Column(name="id_esperto")
	public int getIdEsperto() {
		return idEsperto;
	}
	
	public void setIdEsperto(int idEsperto) {
		this.idEsperto = idEsperto;
	}
	
	@OneToOne(optional=false)
	@PrimaryKeyJoinColumn(name="id_esperto")
	public Esperto getEsperto() {
		return esperto;
	}
	public void setEsperto(Esperto esperto) {
		this.esperto = esperto;
	}
	
	@Column(name="allegato")
	public byte[] getAllegato() {
		return allegato;
	}
	public void setAllegato(byte[] allegato) {
		this.allegato = allegato;
	}
	
	@Column(name="content_type")
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Column(name="estensione")
	public String getEstensione() {
		return estensione;
	}
	
	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}

}
