package it.ccse.bandiEsperti.business.model;

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
@Table(name = "Istruzione")
public class Istruzione implements Comparable<Istruzione> {

	private int id;
	private String titoloStudio;
	private Date data;
	private Esperto esperto;
	private Integer id_esperti;
	private TitoloLaurea titoloLaurea;
	private TipoLaurea tipoLaurea;
	private Atenei atenei;
	private Citta citta;
	private Boolean estero;
	
	
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
	
	@Transient
	public String getDataString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		if(this.data!=null){
			return sdf.format(this.data);
		}
		return "";
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

	@Column(name = "titolo_studio")
	public String getTitoloStudi() {
		return titoloStudio;
	}

	public void setTitoloStudi(String titoloStudo) {
		this.titoloStudio = titoloStudo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

	@Override
	public int compareTo(Istruzione arg0) {
		
		
		// COMPARAZIONE DAL PIU' VECCHIO AL PIU' RECENTE
		// **********************************************
		//		int res = data.compareTo(i.getData());
		
		// COMPARAZIONE DAL PIU' RECENTE AL PIU' VECCHIO
		// **********************************************
		int res = arg0.getData().compareTo(data);
		
		return res;
	}

	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_titolo_laurea")
	public TitoloLaurea getTitoloLaurea() {
		return titoloLaurea;
	}

	public void setTitoloLaurea(TitoloLaurea titoloLaurea) {
		this.titoloLaurea = titoloLaurea;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_laurea")
	public TipoLaurea getTipoLaurea() {
		return tipoLaurea;
	}

	
	public void setTipoLaurea(TipoLaurea tipoLaurea) {
		this.tipoLaurea = tipoLaurea;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ateneo")
	public Atenei getAtenei() {
		return atenei;
	}

	public void setAtenei(Atenei atenei) {
		this.atenei = atenei;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_citta")
	public Citta getCitta() {
		return this.citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
	}

	@Column(name = "estero", nullable = false)
	public Boolean isEstero() {
		return this.estero;
	}

	public void setEstero(Boolean estero) {
		this.estero = estero;
	}
}
