package it.ccse.bandiEsperti.utils;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import it.ccse.bandiEsperti.business.dto.EspertoRicercaDinamicaDTO;


public class TrasformListToDTOObject {
	
	private static TrasformListToDTOObject instance;
	
	private TrasformListToDTOObject(){}
	
	public static TrasformListToDTOObject getInstance(){
	    if(instance == null){
	        instance = new TrasformListToDTOObject();
	    }
	    return instance;
	}
	
	public Set<EspertoRicercaDinamicaDTO> execute(List<Object> objects) throws Exception {
		
		List<EspertoRicercaDinamicaDTO> listEspertoRicercaDTO = new ArrayList<EspertoRicercaDinamicaDTO>();
		Iterator<Object> iterator = objects.iterator();
		
		while (iterator.hasNext() ) {
			EspertoRicercaDinamicaDTO espertoRicercaDinamicaDTO = new EspertoRicercaDinamicaDTO();
		    Object[] tuple = (Object[]) iterator.next();
		    espertoRicercaDinamicaDTO.setNumPubblicazioni(((BigInteger)tuple[0]).intValue());
		    espertoRicercaDinamicaDTO.setId(((Integer) tuple[1]));
		    espertoRicercaDinamicaDTO.setCognome(((String) tuple[2]));
		    Date dataInvio = null;
		    Date dataNascita  = null;
		    if(tuple[3]!=null){
		    	dataInvio=(java.util.Date)tuple[3]; 
		    	espertoRicercaDinamicaDTO.setDataInvio(dataInvio);
		    }
		    if(tuple[4]!=null){
		    	dataNascita=(java.util.Date)tuple[4]; 
		    	espertoRicercaDinamicaDTO.setDataNascita(dataNascita);
		    }
		   
		    espertoRicercaDinamicaDTO.setNome(((String) tuple[5]));
		    espertoRicercaDinamicaDTO.setCitta(((String) tuple[6]));
		    espertoRicercaDinamicaDTO.setTelefono(((String) tuple[7]));
		    espertoRicercaDinamicaDTO.setEmail(((String) tuple[8]));
		    espertoRicercaDinamicaDTO.setDatoreDiLavoro(((String) tuple[9]));
		    espertoRicercaDinamicaDTO.setTipoProfessione(((String) tuple[10]));
		    espertoRicercaDinamicaDTO.setProfessione(((String) tuple[11]));
		    espertoRicercaDinamicaDTO.setInviato(((Boolean) tuple[12]));
		    espertoRicercaDinamicaDTO.setStato(((Integer) tuple[13]));
		    espertoRicercaDinamicaDTO.setCodiceDomanda(((String) tuple[14]));
		    espertoRicercaDinamicaDTO.setTelefono(((String) tuple[15]));
		    espertoRicercaDinamicaDTO.setTipoIncarico(((String) tuple[16]));
		    espertoRicercaDinamicaDTO.setGiudizio(((String) tuple[17]));
		    listEspertoRicercaDTO.add(espertoRicercaDinamicaDTO);
		    
		}
		return new HashSet<EspertoRicercaDinamicaDTO>(listEspertoRicercaDTO);
	}
	
}
