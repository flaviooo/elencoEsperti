package it.ccse.bandiEsperti.business.model;



import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "esperti")
public class Esperto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	private int id;
	
	private Paesi paesi;
	private Provincie provincie;
	private Citta citta;
	private Regioni regioni;
	
	private String cittaEstera;
	private String cittaNascitaEstera;
	
	private Paesi paesiNascita;
	private Provincie provincieNascita;
	private Citta cittaNascita;
	private Regioni regioniNascita;
	
	private String username;
	private String password;
	private String email;
	private String nome;
	private String cognome;
	private String cf;
	private String tel;
	private Date dataNascita;
	private String domicilio;
	private String residenza;
	private Date timestamp;
	private String fax;
	private String cel;
	private String piva;
	private String codiceDomanda;
	private Boolean inviato;
	private Boolean candTrasmessa;
	private Boolean privacy;
	private Boolean admin;
	private Set<Competenza> competenze;
	private Set<Istruzione> istruzioni;
	private Set<Pubblicazione> pubblicazioni;
	private Set<EsperienzaLavorativa> esperienzeLavorative;
	private Set<PrecedenteIncarico> precedentiIncarichi;
	private int stato;
	private InfoEspertoApprovato dettaglio;
	private Date dataInvio;
	private AllegatoPubblEsperto allegato;
	private String cap;
	private String competenza;
	private String presentazione;
	private Set<Associazioni> associazionis = new HashSet<Associazioni>(0);
	
	private DocumentoAllegatoEsperto documentoAllegato;
	
	private CartaIdentitaEsperto cartaIdentita;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nascita_paese", referencedColumnName="codice_paese")
	public Paesi getPaesiNascita() {
		return paesiNascita;
	}

	public void setPaesiNascita(Paesi paesiNascita) {
		this.paesiNascita = paesiNascita;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "nascita_provincia",  referencedColumnName="codice_provincia")
	public Provincie getProvincieNascita() {
		return provincieNascita;
	}

	public void setProvincieNascita(Provincie provincieNascita) {
		this.provincieNascita = provincieNascita;
	}


	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "nascita_citta", referencedColumnName="id")
	public Citta getCittaNascita() {
		return cittaNascita;
	}

	public void setCittaNascita(Citta cittaNascita) {
		this.cittaNascita = cittaNascita;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "nascita_regione", referencedColumnName="codice_regione")
	public Regioni getRegioniNascita() {
		return regioniNascita;
	}

	public void setRegioniNascita(Regioni regioniNascita) {
		this.regioniNascita = regioniNascita;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "residenza_paese", referencedColumnName="codice_paese")
	public Paesi getPaesi() {
		return this.paesi;
	}

	public void setPaesi(Paesi paesi) {
		this.paesi = paesi;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "residenza_provincia",  referencedColumnName="codice_provincia")
	public Provincie getProvincie() {
		return this.provincie;
	}

	public void setProvincie(Provincie provincie) {
		this.provincie = provincie;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "residenza_citta", referencedColumnName="id")
	public Citta getCitta() {
		return this.citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "residenza_regione", referencedColumnName="codice_regione")
	public Regioni getRegioni() {
		return this.regioni;
	}
	
	

	public void setRegioni(Regioni regioni) {
		this.regioni = regioni;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}
	
	@Column(name = "tel")
	public String getTel() {
		return tel;
	}
	
	
	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getResidenza() {
		return residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	@Column(name = "data_modifica")
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	@Column(name="data_invio")
	public Date getDataInvio()
	{
		return dataInvio;
	}
	
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "cellulare")	
	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	@OneToMany(mappedBy = "esperto")
	public Set<Competenza> getCompetenze() {
		return competenze;
	}

	public void setCompetenze(Set<Competenza> competenze) {
		this.competenze = competenze;
	}

	public void setIstruzioni(Set<Istruzione> istruzioni) {
		this.istruzioni = istruzioni;
	}

	@OneToMany(mappedBy = "esperto")
	public Set<Istruzione> getIstruzioni() {
		return istruzioni;
	}

	@OneToMany(mappedBy = "esperto", fetch=FetchType.EAGER)
	public Set<Pubblicazione> getPubblicazioni() {
		return pubblicazioni;
	}

	public void setPubblicazioni(Set<Pubblicazione> pubblicazioni) {
		this.pubblicazioni = pubblicazioni;
	}

	@OneToMany(mappedBy = "esperto")
	public Set<EsperienzaLavorativa> getEsperienzeLavorative() {
		return esperienzeLavorative;
	}

	public void setEsperienzeLavorative(
			Set<EsperienzaLavorativa> esperienzeLavorative) {
		this.esperienzeLavorative = esperienzeLavorative;
	}

	@OneToMany(mappedBy = "esperto")
	public Set<PrecedenteIncarico> getPrecedentiIncarichi() {
		return precedentiIncarichi;
	}

	public void setPrecedentiIncarichi(
			Set<PrecedenteIncarico> precedentiIncarichi) {
		this.precedentiIncarichi = precedentiIncarichi;
	}
	@Column(name="codice_domanda")
	public String getCodiceDomanda() {
		return codiceDomanda;
	}

	public void setCodiceDomanda(String codiceDomanda) {
		this.codiceDomanda = codiceDomanda;
	}

	
	@Column(name="inviato")
	public Boolean getInviato() {
		return inviato;
	}

	public void setInviato(Boolean inviato) {
		this.inviato = inviato;
	}

	public Boolean getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Boolean privacy) {
		this.privacy = privacy;
	}
	
	@Column(name="admin")
	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	public int getStato() {
		return stato;
	}
	
	public void setStato(int stato) {
		this.stato = stato;
	}
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(mappedBy="esperto", optional=true)
	public InfoEspertoApprovato getDettaglio() {
		return dettaglio;
	}
	
	public void setDettaglio(InfoEspertoApprovato dettaglio) {
		this.dettaglio = dettaglio;
	}
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(mappedBy="esperto", optional = true, fetch=FetchType.LAZY)
	public AllegatoPubblEsperto getAllegato() {
		return allegato;
	}
	
	public void setAllegato(AllegatoPubblEsperto allegato) {
		this.allegato = allegato;
	}
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(mappedBy="esperto", optional = true)
	public DocumentoAllegatoEsperto getDocumentoAllegato() {
		return documentoAllegato;
	}
	
	public void setDocumentoAllegato(DocumentoAllegatoEsperto documentoAllegato) {
		this.documentoAllegato = documentoAllegato;
	}
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(mappedBy="esperto", optional = true)
	public CartaIdentitaEsperto getCartaIdentita() {
		return cartaIdentita;
	}
	
	public void setCartaIdentita(CartaIdentitaEsperto cartaIdentita) {
		this.cartaIdentita = cartaIdentita;
	}
	
	@Column(name = "cap", length = 5)
	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}
	
	@Column(name = "competenza", length = 4000)
	public String getCompetenza() {
		return this.competenza;
	}

	public void setCompetenza(String competenza) {
		this.competenza = competenza;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "esperto")
	public Set<Associazioni> getAssociazionis() {
		return this.associazionis;
	}

	public void setAssociazionis(Set<Associazioni> associazionis) {
		this.associazionis = associazionis;
	}
	
	@Column(name = "presentazione", length = 3000)
	public String getPresentazione() {
		return this.presentazione;
	}

	public void setPresentazione(String presentazione) {
		this.presentazione = presentazione;
	}

	@Column(name="candTrasmessa")
	public Boolean getCandTrasmessa() {
		return candTrasmessa;
	}

	public void setCandTrasmessa(Boolean candTrasmessa) {
		this.candTrasmessa = candTrasmessa;
	}

	@Column(name="citta")
	public String getCittaEstera() {
		return cittaEstera;
	}

	public void setCittaEstera(String cittaEstera) {
		this.cittaEstera = cittaEstera;
	}

	@Column(name="citta_nascita")
	public String getCittaNascitaEstera() {
		return cittaNascitaEstera;
	}

	public void setCittaNascitaEstera(String cittaNascitaEstera) {
		this.cittaNascitaEstera = cittaNascitaEstera;
	}
	
}
