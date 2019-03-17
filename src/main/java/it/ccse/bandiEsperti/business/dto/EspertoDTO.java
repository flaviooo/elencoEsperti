package it.ccse.bandiEsperti.business.dto;

import it.ccse.bandiEsperti.utils.Constants;

import java.util.Date;

public class EspertoDTO implements Comparable<EspertoDTO>{
	private int id;
	private String cognome="";
	protected String nome="";
	protected Date dataNascita;
	protected int stato;
	protected AllegatoDTO allegatoPubblicazioni = null;
	protected String giudizio="";
	protected String tipoIncarico="";
	protected String username="";
	protected String password;
	protected String email;
	protected String telefono;
	protected String codiceDomanda="";
	protected int numPubblicazioni;
	protected boolean inviato=false;
	protected Date dataInvio = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	
	public AllegatoDTO getAllegatoPubblicazioni() {
		return allegatoPubblicazioni;
	}
	
	public void setAllegatoPubblicazioni(AllegatoDTO allegatoPubblicazioni) {
		this.allegatoPubblicazioni = allegatoPubblicazioni;
	}
	
	
	
	public void setGiudizio(String giudizio) {
		this.giudizio = giudizio;
	}
	
	public String getGiudizio() {
		return giudizio;
	}
	
	public void setTipoIncarico(String tipoIncarico) {
		this.tipoIncarico = tipoIncarico;
	}
	
	public String getTipoIncarico() {
		return tipoIncarico;
	}
	
	public String getCodiceDomanda() {
		return codiceDomanda;
	}
	
	public void setCodiceDomanda(String codiceDomanda) {
		this.codiceDomanda = codiceDomanda;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getNumPubblicazioni() {
		return numPubblicazioni;
	}
	
	public void setNumPubblicazioni(int numPubblicazioni) {
		this.numPubblicazioni = numPubblicazioni;
	}
	
	public void setInviato(boolean inviato) {
		this.inviato = inviato;
	}
	
	public boolean isInviato() {
		return inviato;
	}
	
	public Date getDataInvio() {
		return dataInvio;
	}
	
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isSospeso()
	{
		return (stato == Constants.STATO_ESPERTO_SOSPESO);
	}

	public boolean isRifiutato()
	{
		return (stato == Constants.STATO_ESPERTO_RIFIUTATO);
	}
	
	public boolean isBloccato()
	{
		return inviato;
	}
	
	public boolean isApprovato()
	{
		return (stato == Constants.STATO_ESPERTO_APPROVATO);
	}

	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
	@Override
	public int compareTo(EspertoDTO arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
