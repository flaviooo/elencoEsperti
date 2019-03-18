package it.ccse.bandiEsperti.business.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "competenze")
public class Competenza {

	private int id;
	private Esperto esperto;
	private Specializzazione specializzazione;
	private String altro;
	private Boolean principale;
	private Integer id_esperti;

	@Column(name="id_esperti", insertable =false, updatable=false)
	public Integer getId_esperti() {
		return id_esperti;
	}

	public void setId_esperti(Integer id_esperti) {
		this.id_esperti = id_esperti;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_esperti")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	public Esperto getEsperto() {
		return esperto;
	}

	public void setEsperto(Esperto esperti) {
		this.esperto = esperti;
	}

	public String getAltro() {
		return altro;
	}

	public void setAltro(String altro) {
		this.altro = altro;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_specializzazioni")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	public Specializzazione getSpecializzazione() {
		return specializzazione;
	}

	public void setSpecializzazione(Specializzazione specializzazione) {
		this.specializzazione = specializzazione;
	}
	
	@Column(name = "principale", nullable = false)
	public Boolean isPrincipale() {
		return this.principale;
	}

	public void setPrincipale(Boolean principale) {
		this.principale = principale;
	}
}
