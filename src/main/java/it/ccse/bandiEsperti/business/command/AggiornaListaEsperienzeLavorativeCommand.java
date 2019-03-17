/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

import java.util.List;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class AggiornaListaEsperienzeLavorativeCommand extends AbstractCommand {

	/**
	 * Deve esistere un esperto all'atto del salvataggio delle relative pubblicazioni
	 * */
	public AggiornaListaEsperienzeLavorativeCommand(List<?> list) {
		super(null);
		super.setList(list);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute() {
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		IEspertiService es = new EspertiService();
		
		// controlli per rendere "safe" il metodo
		if(getList() != null){
			try {
				es.aggiornaEsperienzeLavorativeEsperto(getList());
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
		}	
		
		return res;
	}



	
}
