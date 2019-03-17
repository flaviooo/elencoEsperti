/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.Paesi;
import it.ccse.bandiEsperti.business.model.Regioni;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class RegioniCommand implements AdminCommand {

	Logger logger = Logger.getLogger(RegioniCommand.class);

	
	@Override
	public  Set<Regioni> list(){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<Regioni> set = null;
		try {
			set=es.listaRegioni();
		
		} catch (Exception e) {
			logger.debug(" errore:" + e.getMessage());
		}
		return set;
	}
	
	public  Set<Regioni> listRegioni(Paesi paesi){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<Regioni> set = null;
		try {
			return es.listaRegioni(paesi);
		
		} catch (Exception e) {
			logger.debug(" errore:" + e.getMessage());
		}
		return set;
	}
	
	public Regioni findCodiceRegione(String codiceRegione){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findCodiceRegione(codiceRegione);
		
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
