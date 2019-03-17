/**
 * 
 */
package it.ccse.bandiEsperti.business.command;


import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla selezione del tipo laurea
 */
public class DeliberaCommand implements AdminCommand {

	
	Logger logger = Logger.getLogger(DeliberaCommand.class);

	
	@Override
	public Delibera findById(Integer id){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findDeliberaById(id);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}
	
	

	@Override
	public Set<Delibera> list(){
		
	
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<Delibera> set = null;
		try {
			set=es.listaDelibera();
		
		} catch (Exception ex) {
			logger.debug(" errore" + ex.getMessage());
		
		}
	
	
		return set;
	}

	@Override
	public Integer inserisci(Serializable delibera){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Integer id = null;
		try {
			id = es.inserisciDelibera((Delibera)delibera);
			
		} catch (Exception e) {
			logger.debug(" errore");
		}
		return id;
	}
	
	@Override
	public boolean aggiorna(Serializable delibera){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		boolean res = false;
		try {
		res = es.aggiornaDelibera((Delibera)delibera);
			
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
			//set = es.ricercaTutteEsperienzeLavorative(getE().getId(), filtroRicerca);
			res = es.checkIfExist((Delibera)serializable);
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
			es.cancellaDelibera(id);
			res = true;
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
	
		return res;
	}



	
}
