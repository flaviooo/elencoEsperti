/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

import java.util.Set;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class SalvaCarrieraCommand extends AbstractCommand {

	/**
	 * Deve esistere un esperto all'atto del salvataggio delle relative pubblicazioni
	 * */
	public SalvaCarrieraCommand(Integer idEsperto, Set<EsperienzaLavorativa> set) {
		super(null);

		setIdEsperto(idEsperto); 
		setSet(set);		
	}
	
	@Override
	public boolean execute() {
		
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		IEspertiService es = new EspertiService();
		
		// controlli per rendere "safe" il metodo
		if(getIdEsperto() != null){
			try {
				es.salvaListaEsperienzeLavorativePerEsperto(getIdEsperto(), getSet());
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
		}	
		
		return res;
	}


		
}
