/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.Atenei;
import it.ccse.bandiEsperti.business.model.Citta;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class AteneiCommand implements AdminCommand {

	
	Logger logger = Logger.getLogger(AteneiCommand.class);
	

	@Override
	public Set<Atenei> list(){
		
	
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<Atenei> set = null;
		try {
			set=es.listaAtenei();
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return set;
	}
	
	@Override
	public Atenei findById(Integer id){
		
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findAteneoById(id);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}

	public Set<Atenei> listaAteneiByCitta(Citta citta){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.listaAteneiByCitta(citta);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}

	@Override
	public boolean checkIfExist(Serializable serializable) {
		
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
		
			res = es.checkIfExist((Atenei)serializable);
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
		
		
		return res;
	}
	
	

	@Override
	public Integer inserisci(Serializable ateneo) {
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Integer id = null;
		try {
			id = es.inserisciAteneo((Atenei)ateneo);
			
		} catch (Exception e) {
			logger.debug(" errore");
		}
		return id;
	}

	@Override
	public boolean aggiorna(Serializable ateneo){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		boolean res = false;
		try {
		res = es.aggiornaAtenei((Atenei)ateneo);
			
		} catch (Exception e) {
			logger.debug(" errore");
			res=false;
		}
		return res;
	}
	
	@Override
	public boolean delete(Integer id) {
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			es.cancellaAteneo(id);
			res = true;
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
	
		return res;
	}


}
