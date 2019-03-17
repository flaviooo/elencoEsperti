/**
 * 
 */
package it.ccse.bandiEsperti.business.command;


import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.DatoreDiLavoro;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla selezione del tipo laurea
 */
public class DatoreDiLavoroCommand  implements AdminCommand {

	
	Logger logger = Logger.getLogger(DatoreDiLavoroCommand.class);


	public DatoreDiLavoro findById(Integer id){
		
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findDatoreDiLavoroById(id);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}
	@Override
	public Integer inserisci(Serializable datoreDiLavoro){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Integer id = null;
		try {
			id = es.inserisciDatoreDiLavoro((DatoreDiLavoro)datoreDiLavoro);
			
		} catch (Exception e) {
			logger.debug(" errore");
		}
		return id;
	}
	
	@Override
	public boolean aggiorna(Serializable datoreDiLavoro){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		boolean res = false;
		try {
		res = es.aggiornaDatoreDiLavoro((DatoreDiLavoro)datoreDiLavoro);
			
		} catch (Exception e) {
			logger.debug(" errore");
			res=false;
		}
		return res;
	}
	
	public Set<DatoreDiLavoro> list(){
	
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<DatoreDiLavoro> set = null;
		try {
			set=es.listaDatoreDiLavoro();
		
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
	public boolean delete(Integer id) {
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			es.cancellaDatorediLavoro(id);
			res = true;
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
	
		return res;
	}



	
}
