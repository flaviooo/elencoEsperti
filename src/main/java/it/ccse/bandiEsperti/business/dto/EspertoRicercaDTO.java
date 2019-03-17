package it.ccse.bandiEsperti.business.dto;



import java.math.BigInteger;
import java.util.Date;

public class EspertoRicercaDTO{

	private Integer id;
	private String nome;
	private String cognome;
	private Date dataNascita;
	private String citta;
	private java.math.BigInteger NumeroPubblicazioni;
	private Date dataInvio;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public BigInteger getNumeroPubblicazioni() {
		return NumeroPubblicazioni;
	}
	public void setNumeroPubblicazioni(BigInteger tuple) {
		NumeroPubblicazioni = tuple;
	}
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
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
	
	public Date getDataNascita() {
		return dataNascita;
	}
	
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	
}
