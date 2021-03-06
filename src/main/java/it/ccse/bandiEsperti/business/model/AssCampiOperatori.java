package it.ccse.bandiEsperti.business.model;

// Generated 6-lug-2015 16.18.03 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AssCampiOperatori generated by hbm2java
 */
@Entity
@Table(name = "ass_campi_operatori")
public class AssCampiOperatori implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private CampiRicerca campiRicerca;
	private OperatoriRicerca operatoriRicerca;

	public AssCampiOperatori() {
	}

	public AssCampiOperatori(CampiRicerca campiRicerca,
			OperatoriRicerca operatoriRicerca) {
		this.campiRicerca = campiRicerca;
		this.operatoriRicerca = operatoriRicerca;
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
	@JoinColumn(name = "id_campo_ricerca", nullable = false)
	public CampiRicerca getCampiRicerca() {
		return this.campiRicerca;
	}

	public void setCampiRicerca(CampiRicerca campiRicerca) {
		this.campiRicerca = campiRicerca;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_operatore_ricerca", nullable = false)
	public OperatoriRicerca getOperatoriRicerca() {
		return this.operatoriRicerca;
	}

	public void setOperatoriRicerca(OperatoriRicerca operatoriRicerca) {
		this.operatoriRicerca = operatoriRicerca;
	}

}
