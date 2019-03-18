/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.DatoreDiLavoro;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class EsperienzeLavorativeCommand  implements AdminCommand {
	
	
	private Logger logger = Logger.getLogger(DeliberaCommand.class);
	private Integer idEsperto;
		
	public EsperienzeLavorativeCommand(Integer idEsperto) {
		this.idEsperto=idEsperto;
	}
	


	public EsperienzeLavorativeCommand() {}



	@Override
	public boolean checkIfExist(Serializable serializable) {
		
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		IEspertiService es = new EspertiService();
		try {
			//set = es.ricercaTutteEsperienzeLavorative(getE().getId(), filtroRicerca);
			res = es.checkIfExist((DatoreDiLavoro)serializable);
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
		
		
		return res;
	}
	
	@Override
	public Set<EsperienzaLavorativa> list(){
		
	
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		Set<EsperienzaLavorativa> set = null;
		try {
			set=es.findEsperienzeLavorativeByIdEsperto(this.idEsperto);
		
		} catch (Exception ex) {
			logger.debug(" errore" + ex.getMessage());
		
		}
	
	
		return set;
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
