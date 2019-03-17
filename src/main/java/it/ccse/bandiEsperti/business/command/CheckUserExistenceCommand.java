/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class CheckUserExistenceCommand extends AbstractCommand {

	private boolean flagExistence;
	public boolean isFlagExistence() {
		return flagExistence;
	}

	/**
	 * Costruttore Command
	 * */
	public CheckUserExistenceCommand(Esperto e) {
		super(e);
	}

	@Override
	public boolean execute() {
		
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		IEspertiService es = new EspertiService();
		
		// controlli per rendere "safe" il metodo
		if(getE() != null){
			try {
				flagExistence = es.existentUser(getE().getCf());
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
		}	
		
		return res;
	}

	
		
}
