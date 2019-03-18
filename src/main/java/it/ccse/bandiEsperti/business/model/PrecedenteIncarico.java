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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "precedenti_incarichi")
public class PrecedenteIncarico implements Comparable<PrecedenteIncarico> {
	
	private int id;
	private Delibera delibera;
	private Boolean internazionale;
	private String nomeProgetto;
	private String abstractProgetto;
	private String amministrazione;
	private Boolean europeo;
	private Boolean miur;
	
	private Boolean rds;
	private Esperto esperto;
	private Integer id_esperti;
	private String programmaFinanziamento;
	private String titoloProgetto;
	private Date anno;
	private Boolean inCorso;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	@Transient
	private Date dataFineString;
	private Date dataFine;
	@Temporal(TemporalType.DATE)
	@Column(name = "data_fine", length = 10)
	public Date getDataFine() {
		return this.dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
	@Transient
	public void setAnnoString(String annoString) {
	
		if(annoString!=null)
			try {
				anno=sdf.parse(annoString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
	@Transient
	public void setDataFineString(String dataFineString) {
		
		if(dataFineString!=null)
			try {
				dataFine=sdf.parse(dataFineString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	@Transient
	public String getDataFineString() {

		if(this.dataFine!=null)
			return sdf.format(this.dataFine);
		return "";
	
	}
	@Transient
	public String getAnnoString() {
		
		if(this.anno!=null){
			return sdf.format(this.anno);
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
	
	public void setInternazionale(Boolean internazionale) {
		this.internazionale = internazionale;
	}
	
	public Boolean isInternazionale() {
		return internazionale;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_delibera")
	public Delibera getDelibera() {
		return this.delibera;
	}

	public void setDelibera(Delibera delibera) {
		this.delibera = delibera;
	}

	
	@Column(name = "nome_progetto")
	public String getNomeProgetto() {
		return nomeProgetto;
	}
	
	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}
	
	@Column(name = "abstract")
	public String getAbstractProgetto() {
		return abstractProgetto;
	}
	
	public void setAbstractProgetto(String abstractProgetto) {
		this.abstractProgetto = abstractProgetto;
	}
	
	public String getAmministrazione() {
		return amministrazione;
	}
	
	public void setAmministrazione(String amministrazione) {
		this.amministrazione = amministrazione;
	}
	
	public Boolean isEuropeo() {
		return europeo;
	}
	
	public void setEuropeo(Boolean europeo) {
		this.europeo = europeo;
	}
	public Boolean isMiur() {
		return miur;
	}
	public void setMiur(Boolean miur) {
		this.miur = miur;
	}
	public Boolean isRds() {
		return rds;
	}
	public void setRds(Boolean rds) {
		this.rds = rds;
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

	@Column(name = "titolo_progetto", length = 300)
	public String getTitoloProgetto() {
		return this.titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "anno", length = 10)
	public Date getAnno() {
		return this.anno;
	}

	public void setAnno(Date anno) {
		this.anno = anno;
	}

	@Column(name = "in_corso", nullable = false)
	public Boolean isInCorso() {
		return this.inCorso;
	}

	public void setInCorso(Boolean inCorso) {
		this.inCorso = inCorso;
	}
	
	@Column(name = "programma_finanziamento")
	public String getProgrammaFinanziamento() {
		return this.programmaFinanziamento;
	}

	public void setProgrammaFinanziamento(String programmaFinanziamento) {
		this.programmaFinanziamento = programmaFinanziamento;
	}
	
	@Override
	public int compareTo(PrecedenteIncarico arg0) {
		
		// COMPARAZIONE DAL PIU' VECCHIO AL PIU' RECENTE
		// **********************************************
		//		int res = data.compareTo(i.getData());
		
		// COMPARAZIONE DAL PIU' RECENTE AL PIU' VECCHIO
		// **********************************************
		int res = arg0.getNomeProgetto().compareTo(nomeProgetto);
		
		return res;
	}	
	
	
}
