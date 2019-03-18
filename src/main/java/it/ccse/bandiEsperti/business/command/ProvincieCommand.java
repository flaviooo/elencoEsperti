/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.Provincie;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class ProvincieCommand implements AdminCommand {
	
	
	Logger logger = Logger.getLogger(ProvincieCommand.class);
	
	@Override
	public Set<Provincie> list(){
	
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<Provincie> set = null;
		try {
			set=es.listaProvincie();
		
		} catch (Exception e) {
			logger.debug(" errore:" + e.getMessage());
		}
		return set;
	}
	
	
	
	public Set<Provincie>  findByCodiceRegione(String codiceRegione){
		
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<Provincie> set = null;
		try {
			set=es.findByCodiceRegione(codiceRegione);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}

		
		return set;
	}
	
	public Provincie findCodiceProvincia(String codiceProvincia){
		
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findCodiceProvincia(codiceProvincia);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}



	@Override
	public boolean checkIfExist(Serializable serializable) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public Serializable findById(Integer id) {
		// TODO Auto-generated method stub
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
