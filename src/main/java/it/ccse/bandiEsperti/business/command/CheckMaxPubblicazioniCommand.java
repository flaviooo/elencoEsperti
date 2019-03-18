/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla verifica del numero massimo delle pubblicazioni
 */
public class CheckMaxPubblicazioniCommand extends AbstractCommand {

	private boolean overMax;
	public boolean isOverMax() {
		return overMax;
	}

	/**
	 * Costruttore Command
	 * */
	public CheckMaxPubblicazioniCommand(Esperto e) {
		super(e);
	}

	@Override
	public boolean execute() {
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		overMax = false;
		IEspertiService es = new EspertiService();
		
		// controlli per rendere "safe" il metodo
		if(getE() != null){
			try {
				overMax = es.overMaxNumPubblication(getE().getId());
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
				return false;
			}
		}	
		
		return true;
	}

	
		
}
