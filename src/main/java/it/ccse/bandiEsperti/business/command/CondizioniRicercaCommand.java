/**
 * 
 */
package it.ccse.bandiEsperti.business.command;


import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.CondizioniRicerca;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla selezione del tipo laurea
 */
public class CondizioniRicercaCommand  implements AdminCommand {

	
	Logger logger = Logger.getLogger(CondizioniRicercaCommand.class);
	
	public Set<CondizioniRicerca> list(){
	
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<CondizioniRicerca> set = null;
		try {
			set=es.listaCondizioniRicerca();
		
		} catch (Exception ex) {
			logger.debug(" errore: " + ex.getMessage());
		}
		return set;
	}
	
	@Override
	public  boolean checkIfExist(Serializable serializable){
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public CondizioniRicerca findById(Integer id){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findCondizioniRicercaById(id);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}

	@Override
	public Integer inserisci(Serializable serializable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean aggiorna(Serializable serializable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	

	
}
