package it.ccse.bandiEsperti.business.dto;

import java.util.Map;
public class FiltriRicercaDTO {

	Map<String, Boolean> filtroSettore;
	Map<String, Boolean> filtroSpecializzazione;

	String filtroCognome;
	String filtroDataNascita;
	
	public Map<String, Boolean> getFiltroSettore() {
		return filtroSettore;
	}
	public void setFiltroSettore(Map<String, Boolean> filtroSettore) {
		this.filtroSettore = filtroSettore;
	}
	public Map<String, Boolean> getFiltroSpecializzazione() {
		return filtroSpecializzazione;
	}
	public void setFiltroSpecializzazione(Map<String, Boolean> filtroSpecializzazione) {
		this.filtroSpecializzazione = filtroSpecializzazione;
	}

	public String getFiltroCognome() {
		return filtroCognome;
	}
	public void setFiltroCognome(String filtroCognome) {
		this.filtroCognome = filtroCognome;
	}
	public String getFiltroDataNascita() {
		return filtroDataNascita;
	}
	public void setFiltroDataNascita(String filtroDataNascita) {
		this.filtroDataNascita = filtroDataNascita;
	}	
	
	
}
