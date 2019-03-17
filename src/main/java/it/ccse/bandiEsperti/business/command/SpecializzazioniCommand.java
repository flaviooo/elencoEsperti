/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.Specializzazione;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class SpecializzazioniCommand implements AdminCommand  {

	Logger logger = Logger.getLogger(TemiCommand.class);
	
	@Override
	public Set<Specializzazione> list() {
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService service = new EspertiService();
		Set<Specializzazione> set = null;
		try {
			set=service.listaSpecializzazioni();
			
		} catch (Exception ex) {
			logger.debug(" errore" + ex.getMessage());
		
		}
		return set;
	}
	
	@Override
	public Specializzazione findById(Integer id){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findSpecializzazioneById(id);
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}
	
	@Override
	public Integer inserisci(Serializable specializzazione){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Integer id = null;
		try {
			id = es.inserisciSpecializzazione((Specializzazione)specializzazione);
		} catch (Exception e) {
			logger.debug(" errore");
		}
		return id;
	}
	
	@Override
	public boolean aggiorna(Serializable specializzazione){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		boolean res = false;
		try {
		res = es.aggiornaSpecializzazione((Specializzazione)specializzazione);
		} catch (Exception e) {
			logger.debug(" errore");
			res=false;
		}
		return res;
	}

	@Override
	public boolean checkIfExist(Serializable serializable) {
		
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			res = es.checkIfExist((Specializzazione)serializable);
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
		return res;
	}
	
	@Override
	public boolean delete(Integer id) {
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			es.cancellaSpecializzazione(id);
			res = true;
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
	
		return res;
	}


}
