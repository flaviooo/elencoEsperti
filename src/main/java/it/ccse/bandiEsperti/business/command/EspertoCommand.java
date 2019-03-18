/**
 * 
 */
package it.ccse.bandiEsperti.business.command;


import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla selezione del tipo laurea
 */
public class EspertoCommand implements AdminCommand {

	
	Logger logger = Logger.getLogger(EspertoCommand.class);

	
	@Override
	public Esperto findById(Integer id){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findEspertoById(id);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}
	
	

	@Override
	public Set<Esperto> list(){
		
	
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<Esperto> set = null;
		try {
			set=es.listaEsperti();
		
		} catch (Exception ex) {
			logger.debug(" errore" + ex.getMessage());
		
		}
	
	
		return set;
	}

	@Override
	public Integer inserisci(Serializable esperto){
		return null;
	}
	

	public boolean aggiornaEsperto(int idEsperto, String email, String tel){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		boolean res = false;
		try {
			res=es.aggiornaEsperto(idEsperto, email,tel);
			
		} catch (Exception e) {
			logger.debug(" errore : " + e.getMessage());
			e.printStackTrace();
			res=false;
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



	@Override
	public boolean aggiorna(Serializable serializable) {
		// TODO Auto-generated method stub
		return false;
	}


	
}
