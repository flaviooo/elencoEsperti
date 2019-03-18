/**
 * 
 */
package it.ccse.bandiEsperti.business.command;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.Associazioni;
import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla selezione del tipo laurea
 */
public class AssociazioniCommand  implements AdminCommand {

	
	Logger logger = Logger.getLogger(AssociazioniCommand.class);

	public Associazioni findById(Integer id){
		
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findAssociazioniById(id);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}
	@Override
	public Integer inserisci(Serializable associazioni){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Integer id = null;
		try {
			id = es.inserisciAssociazioni((Associazioni)associazioni);
			
		} catch (Exception e) {
			logger.debug(" errore");
		}
		return id;
	}
	
	@Override
	public boolean aggiorna(Serializable associazioni){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		boolean res = false;
		try {
		res = es.aggiornaAssociazioni((Associazioni)associazioni);
			
		} catch (Exception e) {
			logger.debug(" errore");
			res=false;
		}
		return res;
	}
	
	public boolean aggiorna(List<Associazioni> associazioniNew){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		boolean res = false;
		try {
		res = es.aggiornaAssociazioni(associazioniNew);
			
		} catch (Exception e) {
			logger.debug(" errore");
			res=false;
		}
		return res;
	}
	
	public Set<Associazioni> list(){
	
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<Associazioni> set = null;
		try {
			set=es.listaAssociazioni();
		
		} catch (Exception ex) {
			logger.debug(" errore: " + ex.getMessage());
		}
		return set;
	}
	
	
	
	
	
	public Set<Esperto> listEsperti(){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<Esperto> set = null;
		try {
			set=es.listaEsperti();
		
		} catch (Exception ex) {
			logger.debug(" errore: " + ex.getMessage());
		}
		return set;
	}
	
	public Esperto findEspertoById(Integer id){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Esperto esperto = null;
		try {
			esperto=es.findEspertoById(id);
		
		} catch (Exception ex) {
			logger.debug(" errore: " + ex.getMessage());
		}
		return esperto;
	}
	
	
	
	public boolean delete(Esperto esperto, Delibera delibera) {
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			es.cancellaAssociazioni(esperto, delibera);
			res = true;
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
	
		return res;
	}
	@Override
	public boolean checkIfExist(Serializable serializable) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}
	

	
}
