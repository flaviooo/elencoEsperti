/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Paesi;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class PaesiCommand extends AbstractCommand {

	

	public PaesiCommand(Esperto e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	
	
	public Paesi findCodicePaese(String codicePaese){
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findByCodicePaese(codicePaese);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}

		
		return null;
	}

	public boolean execute(){
		
	
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			set=es.listaPaesi();
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}

		
		return true;
	}


	
}
