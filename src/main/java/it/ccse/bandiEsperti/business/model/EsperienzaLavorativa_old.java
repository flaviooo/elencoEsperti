package it.ccse.bandiEsperti.business.model;

import java.util.Date;

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
@Table(name = "esperienze_lavorative")
public class EsperienzaLavorativa_old implements Comparable<EsperienzaLavorativa_old> {
	private int id;
	private Date dataInizio;
	private Date dataFine;
	private Boolean inCorso;
	private String ruolo;
	private String attivitaLavorative;
	private Esperto esperto;

	private String tipoEsperienza;
	private String datoreLavoro;
	
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

	@Column(name = "data_inizio")
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	@Column(name = "data_fine")	
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	@Column(name = "in_corso")
	public Boolean isInCorso() {
		return inCorso;
	}
	public void setInCorso(Boolean inCorso) {
		this.inCorso = inCorso;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	@Column(name = "attivita_lavorative")
	public String getAttivitaLavorative() {
		return attivitaLavorative;
	}
	public void setAttivitaLavorative(String attivitaLavorative) {
		this.attivitaLavorative = attivitaLavorative;
	}
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="id_esperti")
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public Esperto getEsperto() {
		return esperto;
	}
	public void setEsperto(Esperto esperto) {
		this.esperto = esperto;
	}
	@Column(name = "tipo_esperienza")
	public String getTipoEsperienza() {
		return tipoEsperienza;
	}
	public void setTipoEsperienza(String tipoEsperienza) {
		this.tipoEsperienza = tipoEsperienza;
	}
	@Column(name = "datore_lavoro")
	public String getDatoreLavoro() {
		return datoreLavoro;
	}
	public void setDatoreLavoro(String datoreLavoro) {
		this.datoreLavoro = datoreLavoro;
	}

	@Override
	public int compareTo(EsperienzaLavorativa_old arg0) {
		EsperienzaLavorativa_old i = (EsperienzaLavorativa_old)arg0;
		
		// COMPARAZIONE DAL PIU' VECCHIO AL PIU' RECENTE
		// **********************************************
		//		int res = data.compareTo(i.getData());
		
		// COMPARAZIONE DAL PIU' RECENTE AL PIU' VECCHIO
		// **********************************************
		int res = i.getDataInizio().compareTo(dataInizio);
		
		return res;
	}	
}
