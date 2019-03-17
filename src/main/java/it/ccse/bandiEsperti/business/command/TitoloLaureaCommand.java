/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

import it.ccse.bandiEsperti.business.model.TipoLaurea;
import it.ccse.bandiEsperti.business.model.TitoloLaurea;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla selezione del titolo di studio
 */
public class TitoloLaureaCommand implements AdminCommand {
	
	Logger logger = Logger.getLogger(TitoloLaureaCommand.class);
	
	public Set<TitoloLaurea> listaTitoloLaureaByTipoLaurea(TipoLaurea tipoLaurea){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.listaTitoloLaureaByTipoLaurea(tipoLaurea);
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}
	
	@Override
	public TitoloLaurea findById(Integer id){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findTitoloLaureaById(id);
		
		} catch (Exception e) {
			logger.debug(" errore");
		}
		return null;
	}

	@Override
	public Set<TitoloLaurea> list(){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<TitoloLaurea> set = null;
		try {
			set=es.listaTitoloLaurea();
		} catch (Exception ex) {
			logger.debug(" errore" + ex.getMessage());
		}
		return set;
	}
	
	@Override
	public Integer inserisci(Serializable titoloLaurea){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Integer id = null;
		try {
			id = es.inserisciTitoloLaurea((TitoloLaurea)titoloLaurea);
			
		} catch (Exception e) {
			logger.debug(" errore");
		}
		return id;
	}
	
	@Override
	public boolean aggiorna(Serializable titoloLaurea){
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		boolean res = false;
		try {
		res = es.aggiornaTitoloLaurea((TitoloLaurea)titoloLaurea);
			
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
			res = es.checkIfExist((TitoloLaurea)serializable);
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
			es.cancellaTitoloLaurea(id);
			res = true;
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
	
		return res;
	}

	
}
