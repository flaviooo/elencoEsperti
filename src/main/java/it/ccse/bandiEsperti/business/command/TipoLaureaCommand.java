/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.TipoLaurea;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla selezione del tipo laurea
 */
public class TipoLaureaCommand implements AdminCommand {

	Logger logger = Logger.getLogger(TipoLaureaCommand.class);
	
	@Override
	public Set<TipoLaurea> list(){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<TipoLaurea> set = null;
		try {
			set=es.listaTipoLaurea();
		
		} catch (Exception ex) {
			logger.debug(" errore" + ex.getMessage());
		
		}
		return set;
	}
	
	public TipoLaurea findById(Integer id){
		
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findTipoLaureaById(id);
		
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
