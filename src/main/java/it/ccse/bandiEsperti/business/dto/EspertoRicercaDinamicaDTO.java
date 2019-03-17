package it.ccse.bandiEsperti.business.dto;

public class EspertoRicercaDinamicaDTO extends EspertoDTO{

	private String citta = "";
	private String datoreDiLavoro = "";
	private String tipoProfessione = "";
	private String professione = "";
	private String telefono = "";
	private String cognome="";
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}	
	public String getDatoreDiLavoro() {
		return datoreDiLavoro;
	}
	public void setDatoreDiLavoro(String datoreDiLavoro) {
		this.datoreDiLavoro = datoreDiLavoro;
	}
	public String getTipoProfessione() {
		return tipoProfessione;
	}
	public void setTipoProfessione(String tipoProfessione) {
		this.tipoProfessione = tipoProfessione;
	}
	public String getProfessione() {
		return professione;
	}
	public void setProfessione(String professione) {
		this.professione = professione;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	
}
