package it.ccse.bandiEsperti.business.dto;

import it.ccse.bandiEsperti.business.model.Specializzazione;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FiltroRicercaEspertiDTO {
	 public enum TipoFiltro {
		 INCARICHIPRESENTIRDS,
		 INCARICHIPRESENTIALTRO,
		 PUBBLICAZIONIPRESENTI,
		 COGNOME,
		 DATALIMITE,
		 INCORSO,
		 FLAGCARRIERA,
		 FLAGPRINCIPALE,
		 APARTIREDA,
		 DATORELAVORO,
		 FILTRADETTAGLIESPERIENZE,
		 LISTACOMPETENZE,
		 FILTRADETTAGLICOMPETENZE
	 }
	 
	 public enum MacroFiltro {
		 PRECEDENTIINCARICHI,
		 PUBBLICAZIONI,
		 DATIANAGRAFICI,
		 ESPERIENZE,
		 COMPETENZE
	 }
	
	private Map<TipoFiltro, Object> precedentiIncarichi;
	private Map<TipoFiltro, Object> pubblicazioni;
	private Map<TipoFiltro, Object> datiAnagrafici;
	private Map<TipoFiltro, Object> esperienze;
	private Map<TipoFiltro, Object> competenze;
	
	public FiltroRicercaEspertiDTO (){
		this.initAll();
	} 
	
	public void initAll(){
		this.precedentiIncarichi = new LinkedHashMap<TipoFiltro, Object>();
		this.pubblicazioni = new LinkedHashMap<TipoFiltro, Object>();
		this.datiAnagrafici = new LinkedHashMap<TipoFiltro, Object>();
		this.esperienze = new LinkedHashMap<TipoFiltro, Object>();
		this.competenze = new LinkedHashMap<TipoFiltro, Object>();
		
		//inizializzazione
		this.precedentiIncarichi.put(TipoFiltro.INCARICHIPRESENTIRDS, new Boolean(false));
		this.precedentiIncarichi.put(TipoFiltro.INCARICHIPRESENTIALTRO, new Boolean(false));
		
		this.pubblicazioni.put(TipoFiltro.PUBBLICAZIONIPRESENTI, new Boolean(false));
		
		//String
		this.datiAnagrafici.put(TipoFiltro.COGNOME, null);
		//Date
		this.datiAnagrafici.put(TipoFiltro.DATALIMITE, null);
		
		this.esperienze.put(TipoFiltro.INCORSO, new Boolean(false));
		
		this.esperienze.put(TipoFiltro.FLAGCARRIERA, new Boolean(false));
		this.esperienze.put(TipoFiltro.FLAGPRINCIPALE, new Boolean(false));
		//Date
		this.esperienze.put(TipoFiltro.APARTIREDA, null);
		//List<String>
		this.esperienze.put(TipoFiltro.DATORELAVORO, null);
		this.esperienze.put(TipoFiltro.FILTRADETTAGLIESPERIENZE, new Boolean(true));
		
		//List<Specializzazioni>
		this.competenze.put(TipoFiltro.LISTACOMPETENZE, null);
		this.competenze.put(TipoFiltro.FILTRADETTAGLICOMPETENZE, new Boolean(true));
	}

	public Map<TipoFiltro, Object> getPrecedentiIncarichi() {
		return precedentiIncarichi;
	}

	public void setPrecedentiIncarichi(Map<TipoFiltro, Object> precedentiIncarichi) {
		this.precedentiIncarichi = precedentiIncarichi;
	}

	public Map<TipoFiltro, Object> getPubblicazioni() {
		return pubblicazioni;
	}

	public void setPubblicazioni(Map<TipoFiltro, Object> pubblicazioni) {
		this.pubblicazioni = pubblicazioni;
	}

	public Map<TipoFiltro, Object> getDatiAnagrafici() {
		return datiAnagrafici;
	}

	public void setDatiAnagrafici(Map<TipoFiltro, Object> datiAnagrafici) {
		this.datiAnagrafici = datiAnagrafici;
	}

	public Map<TipoFiltro, Object> getEsperienze() {
		return esperienze;
	}

	public void setEsperienze(Map<TipoFiltro, Object> esperienze) {
		this.esperienze = esperienze;
	}

	public Map<TipoFiltro, Object> getCompetenze() {
		return competenze;
	}

	public void setCompetenze(Map<TipoFiltro, Object> competenze) {
		this.competenze = competenze;
	}
	
	public boolean filtriApplicatiDefault(){
		boolean res = true;
		
		//verifica PrecedentiIncarichi
		if((Boolean)this.precedentiIncarichi.get(TipoFiltro.INCARICHIPRESENTIRDS) != false){
			res = false;
		}
		if((Boolean)this.precedentiIncarichi.get(TipoFiltro.INCARICHIPRESENTIALTRO) != false){
			res = false;
		}
		
		//verifica Pubblicazioni
		if((Boolean)this.pubblicazioni.get(TipoFiltro.PUBBLICAZIONIPRESENTI) != false){
			res = false;
		}
		
		//verifica DatiAnagrafici
		if(this.datiAnagrafici.get(TipoFiltro.COGNOME) != null){
			res = false;
		}
		if(this.datiAnagrafici.get(TipoFiltro.DATALIMITE) != null){
			res = false;
		}
		
		//verifica Esperienze
		if((Boolean)this.esperienze.get(TipoFiltro.INCORSO) != false){
			res = false;
		}
		if(this.esperienze.get(TipoFiltro.APARTIREDA) != null){
			res = false;
		}
		if(this.esperienze.get(TipoFiltro.DATORELAVORO) != null){
			res = false;
		}
		if((Boolean)this.esperienze.get(TipoFiltro.FILTRADETTAGLIESPERIENZE) != false){
			res = false;
		}
		
		//verifica Specializzazioni
		if(this.competenze.get(TipoFiltro.LISTACOMPETENZE) != null){
			res = false;
		}
		if((Boolean)this.competenze.get(TipoFiltro.FILTRADETTAGLICOMPETENZE) != false){
			res = false;
		}
		
		return res;
	}
	
	public Map<MacroFiltro,  Map<TipoFiltro, List<String>>> getFiltriApplicati(){
		Map<MacroFiltro, Map<TipoFiltro, List<String>>> macroFiltri = new HashMap<MacroFiltro,  Map<TipoFiltro, List<String>>>();
		List<String> filtriApplicati = null;
		
		Map<TipoFiltro, List<String>> filtriApplicatiPrecedentiIncarichi = new HashMap<TipoFiltro, List<String>>();
		for(Entry<TipoFiltro, Object> singleEntry : this.precedentiIncarichi.entrySet()){
			if((filtriApplicati = this.isFiltroApplicatoToString(singleEntry.getKey(), singleEntry.getValue())) != null){
				filtriApplicatiPrecedentiIncarichi.put(singleEntry.getKey(), filtriApplicati);
			}
		}
		filtriApplicati = null;
		
		Map<TipoFiltro, List<String>> filtriApplicatiPubblicazioni = new HashMap<TipoFiltro, List<String>>();
		for(Entry<TipoFiltro, Object> singleEntry : this.pubblicazioni.entrySet()){
			if((filtriApplicati = this.isFiltroApplicatoToString(singleEntry.getKey(), singleEntry.getValue())) != null){
				filtriApplicatiPubblicazioni.put(singleEntry.getKey(), filtriApplicati);
			}
		}
		filtriApplicati = null;
		
		Map<TipoFiltro, List<String>> filtriApplicatiDatiAnagrafici = new HashMap<TipoFiltro, List<String>>();
		for(Entry<TipoFiltro, Object> singleEntry : this.datiAnagrafici.entrySet()){
			if((filtriApplicati = this.isFiltroApplicatoToString(singleEntry.getKey(), singleEntry.getValue())) != null){
				filtriApplicatiDatiAnagrafici.put(singleEntry.getKey(), filtriApplicati);
			}
		}
		filtriApplicati = null;
		
		Map<TipoFiltro, List<String>> filtriApplicatiEsperienze = new HashMap<TipoFiltro, List<String>>();
		for(Entry<TipoFiltro, Object> singleEntry : this.esperienze.entrySet()){
			if((filtriApplicati = this.isFiltroApplicatoToString(singleEntry.getKey(), singleEntry.getValue())) != null){
				filtriApplicatiEsperienze.put(singleEntry.getKey(), filtriApplicati);
			}
		}
		filtriApplicati = null;
		
		Map<TipoFiltro, List<String>> filtriApplicatiCompetenze = new HashMap<TipoFiltro, List<String>>();
		for(Entry<TipoFiltro, Object> singleEntry : this.competenze.entrySet()){
			if((filtriApplicati = this.isFiltroApplicatoToString(singleEntry.getKey(), singleEntry.getValue())) != null){
				filtriApplicatiCompetenze.put(singleEntry.getKey(), filtriApplicati);
			}
		}
		filtriApplicati = null;
		
		macroFiltri.put(MacroFiltro.PRECEDENTIINCARICHI, filtriApplicatiPrecedentiIncarichi);
		macroFiltri.put(MacroFiltro.PUBBLICAZIONI, filtriApplicatiPubblicazioni);
		macroFiltri.put(MacroFiltro.DATIANAGRAFICI, filtriApplicatiDatiAnagrafici);
		macroFiltri.put(MacroFiltro.ESPERIENZE, filtriApplicatiEsperienze);
		macroFiltri.put(MacroFiltro.COMPETENZE, filtriApplicatiCompetenze);
		
		return macroFiltri;
	}
	
	private List<String> isFiltroApplicatoToString(TipoFiltro tipoFiltro, Object filtro){
		List<String> response = null;
		
		if(tipoFiltro.equals(TipoFiltro.INCARICHIPRESENTIRDS)){
			if(((Boolean)filtro).booleanValue()){
				response = new ArrayList<String>();
				response.add(new String("- Incarico RDS"));
			}
		}
		else if(tipoFiltro.equals(TipoFiltro.INCARICHIPRESENTIALTRO)){
			if(((Boolean)filtro).booleanValue()){
				response = new ArrayList<String>();
				response.add(new String("- Altri Incarichi"));
			}
		}
		else if(tipoFiltro.equals(TipoFiltro.PUBBLICAZIONIPRESENTI)){
			if(((Boolean)filtro).booleanValue()){
				response = new ArrayList<String>();
				response.add(new String("- Pubblicazioni Presenti"));
			}
		}
		else if(tipoFiltro.equals(TipoFiltro.COGNOME)){
			if(filtro != null){
				response = new ArrayList<String>();
				response.add(new String("- Cognome: "+(String)filtro));
			}
		}
		else if(tipoFiltro.equals(TipoFiltro.DATALIMITE)){
			if(filtro != null){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				response = new ArrayList<String>();
				response.add(new String("- Nati Dal: "+sdf.format(((Date)filtro).getTime())));
			}
		}
		else if(tipoFiltro.equals(TipoFiltro.INCORSO)){
			if(((Boolean)filtro).booleanValue()){
				response = new ArrayList<String>();
				response.add(new String("- In Corso"));
			}
		}
		else if(tipoFiltro.equals(TipoFiltro.APARTIREDA)){
			if(filtro != null){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				response = new ArrayList<String>();
				response.add(new String("- A Partire Da: "+sdf.format(((Date)filtro).getTime())));
			}
		}
		else if(tipoFiltro.equals(TipoFiltro.DATORELAVORO)){
			if(filtro != null && ((List<String>)filtro).size() > 0){
				response = new ArrayList<String>();
				for(String datore : (List<String>)filtro){
					response.add("- "+datore);
				}
			}
		}
		else if(tipoFiltro.equals(TipoFiltro.FILTRADETTAGLIESPERIENZE)){
			if(((Boolean)filtro).booleanValue()){
				response = new ArrayList<String>();
				response.add(new String("- Filtra risultati nella finestra di dettaglio (se altri criteri selezionati)"));
			}
		}
		else if(tipoFiltro.equals(TipoFiltro.LISTACOMPETENZE)){
			if(filtro != null && ((List<Specializzazione>)filtro).size() > 0){
				response = new ArrayList<String>();
				for(Specializzazione specializzazione : (List<Specializzazione>)filtro){
					response.add("- [" + specializzazione.getTema().getNome() + "] : "+specializzazione.getNome()); 
				}
			}
		}
		else if(tipoFiltro.equals(TipoFiltro.FILTRADETTAGLICOMPETENZE)){
			if(((Boolean)filtro).booleanValue()){
				response = new ArrayList<String>();
				response.add(new String("- Filtra risultati nella finestra di dettaglio (se competenze selezionate)"));
			}
		}
		
		return response;
	}
}
