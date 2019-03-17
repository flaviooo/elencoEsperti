/**
 * 
 */
package it.ccse.bandiEsperti.business.command;


import it.ccse.bandiEsperti.business.model.Tema;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;






import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class TemiCommand implements AdminCommand  {

	Logger logger = Logger.getLogger(TemiCommand.class);
	
	
	@Override
	public Set<Tema> list() {
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService service = new EspertiService();
		Set<Tema> set = null;
		try {
			//temi=service.ricercaTuttiTemi();
			set = new HashSet<Tema>(service.ricercaTuttiTemi());
			
		} catch (Exception ex) {
			logger.debug(" errore" + ex.getMessage());
		
		}
		return set;
	}
	


	@Override
	public Integer inserisci(Serializable tema){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Integer id = null;
		try {
			id = es.inserisciTema((Tema)tema);
			
		} catch (Exception e) {
			logger.debug(" errore");
		}
		return id;
	}
	
	@Override
	public boolean aggiorna(Serializable tema){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		boolean res = false;
		try {
		res = es.aggiornaTema((Tema)tema);
			
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
			res = es.checkIfExist((Tema)serializable);
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
			es.cancellaTema(id);
			res = true;
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
	
		return res;
	}

	@Override
	public Tema findById(Integer id){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findTemaById(id);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}



	
}


