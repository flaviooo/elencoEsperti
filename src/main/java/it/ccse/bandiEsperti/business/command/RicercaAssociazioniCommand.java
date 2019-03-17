/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import it.ccse.bandiEsperti.business.model.Associazioni;
import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Tema;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 */
public class RicercaAssociazioniCommand {
	
	List<Esperto> listEsperti = null;
	List<Tema> listTemi = null;
	List<Delibera> listDelibera = null;
	
	Logger logger = Logger.getLogger(RicercaAssociazioniCommand.class);
	
	

	

	public Set<Associazioni> execute() throws Exception{
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		Set<Associazioni> listAssociazioni = null;
		try {
	    	IEspertiService es = new EspertiService();
			listAssociazioni = es.ricerca(this.listEsperti, this.listTemi, this.listDelibera);
			
			
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
		return listAssociazioni;
	}




	public RicercaAssociazioniCommand(List<Esperto> listEsperti,
			List<Tema> listTemi, List<Delibera> listDelibera) {
		super();
		this.listEsperti = listEsperti;
		this.listTemi = listTemi;
		this.listDelibera = listDelibera;
	}



	


		
}
