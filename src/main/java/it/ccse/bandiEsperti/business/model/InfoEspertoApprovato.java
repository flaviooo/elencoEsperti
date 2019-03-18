package it.ccse.bandiEsperti.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name="esperti_approvati_info")
public class InfoEspertoApprovato {
	
	private String tipoIncarico;
	private String giudizio;
	private int idEsperto;
	private Esperto esperto;
	
	@Id
	@Column(name="id_esperto")
	public int getIdEsperto() {
		return idEsperto;
	}
	
	public void setIdEsperto(int idEsperto) {
		this.idEsperto = idEsperto;
	}
	
	@OneToOne(optional = false)
	@PrimaryKeyJoinColumn(name="id_esperto")
	public Esperto getEsperto() {
		return esperto;
	}
	
	public void setEsperto(Esperto esperto) {
		this.esperto = esperto;
	}
	

	@Column(name="tipo_incarico_ccse")
	public String getTipoIncarico() {
		return tipoIncarico;
	}
	public void setTipoIncarico(String tipoIncarico) {
		this.tipoIncarico = tipoIncarico;
	}
	public String getGiudizio() {
		return giudizio;
	}
	public void setGiudizio(String giudizio) {
		this.giudizio = giudizio;
	}
	
	

}
