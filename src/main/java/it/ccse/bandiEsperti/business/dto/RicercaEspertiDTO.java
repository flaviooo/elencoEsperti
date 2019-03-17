package it.ccse.bandiEsperti.business.dto;

import it.ccse.bandiEsperti.business.dto.CompetenzaDTO;
import it.ccse.bandiEsperti.business.dto.EspertoRicercaDTO;
import it.ccse.bandiEsperti.business.dto.IncaricoDTO;

import java.util.ArrayList;
import java.util.List;

public class RicercaEspertiDTO {

	private List<EspertoRicercaDTO> espertiList = new ArrayList<EspertoRicercaDTO>();
	private List<CompetenzaDTO> competenzeList = new ArrayList<CompetenzaDTO>();
	private List<IncaricoDTO> incarichiList = new ArrayList<IncaricoDTO>();
	private String filtroCognome="";
	private String filtroDataNascitaEntro="";
	private boolean conPubblicazioni = false;
	private boolean esperienzaInCorso = false;
	private String esperienzaDa="";
	private String datoreLavoro="";
	
	public List<CompetenzaDTO> getCompetenzeList() {
		return competenzeList;
	}
	
	public List<EspertoRicercaDTO> getEspertiList() {
		return espertiList;
	}
	
	public List<IncaricoDTO> getIncarichiList() {
		return incarichiList;
	}
	
	public String getDatoreLavoro() {
		return datoreLavoro;
	}
	
	public void setDatoreLavoro(String datoreLavoro) {
		this.datoreLavoro = datoreLavoro;
	}
	
	public String getFiltroCognome() {
		return filtroCognome;
	}
	
	public void setFiltroCognome(String filtroCognome) {
		this.filtroCognome = filtroCognome;
	}
	
	public String getFiltroDataNascitaLimite() {
		return filtroDataNascitaEntro;
	}
	
	public void setFiltroDataNascitaLimite(String filtroDataNascitaEntro) {
		this.filtroDataNascitaEntro = filtroDataNascitaEntro;
	}
	
	public boolean isConPubblicazioni() {
		return conPubblicazioni;
	}
	
	public void setConPubblicazioni(boolean conPubblicazioni) {
		this.conPubblicazioni = conPubblicazioni;
	}
	
	public boolean isEsperienzaInCorso() {
		return esperienzaInCorso;
	}
	
	public void setEsperienzaInCorso(boolean esperienzaInCorso) {
		this.esperienzaInCorso = esperienzaInCorso;
	}
	
	
	public String getEsperienzaDa() {
		return esperienzaDa;
	}
	
	public void setEsperienzaDa(String esperienzaDa) {
		this.esperienzaDa = esperienzaDa;
	}

	
	
}
