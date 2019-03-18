package it.ccse.bandiEsperti.business.model;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "pubblicazione")
public class Pubblicazione implements Comparable<Pubblicazione>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Date data;
	private String descrizione;
	private Esperto esperto;
	private Integer id_esperti;
	private Specializzazione specializzazione1;
    private Specializzazione specializzazione2;
    private Specializzazione specializzazione3;
	@Transient
	public String getDataString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		if(this.data!=null)
			return sdf.format(this.data);
		return "";
	
	}
	
	@Transient
	public void setDataString(String dataString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		if(dataString!=null)
			try {
				data=sdf.parse(dataString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_esperti")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	public Esperto getEsperto() {
		return esperto;
	}

	public void setEsperto(Esperto esperto) {
		this.esperto = esperto;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_specializzazioni_1", nullable = true)
	public Specializzazione getSpecializzazione1() {
		return this.specializzazione1;
	}
	public void setSpecializzazione1(Specializzazione specializzazione1) {
		this.specializzazione1 = specializzazione1;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_specializzazioni_2", nullable = true)
	public Specializzazione getSpecializzazione2() {
		return this.specializzazione2;
	}
	public void setSpecializzazione2(Specializzazione specializzazione2) {
		this.specializzazione2 = specializzazione2;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_specializzazioni_3", nullable = true)
	public Specializzazione getSpecializzazione3() {
		return this.specializzazione3;
	}
	public void setSpecializzazione3(Specializzazione specializzazione3) {
		this.specializzazione3 = specializzazione3;
	}
	

	@Override
	public int compareTo(Pubblicazione arg0) {
		
		// COMPARAZIONE DAL PIU' VECCHIO AL PIU' RECENTE
		// **********************************************
		//		int res = data.compareTo(i.getData());
		
		// COMPARAZIONE DAL PIU' RECENTE AL PIU' VECCHIO
		// **********************************************
		int res = arg0.getData().compareTo(data);
		
		return res;
	}
}
