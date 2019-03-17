/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.dao.EspertiDAO;
import it.ccse.bandiEsperti.business.dao.IEspertiDAO;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class RicercaPrecedentiIncarichiCommand extends AbstractCommand {

	/**
	 * Deve esistere un esperto all'atto del salvataggio delle relative pubblicazioni
	 * */
	public RicercaPrecedentiIncarichiCommand(Integer idEsperto) {
		super(null);
		IEspertiDAO eDao = new EspertiDAO();
		Esperto e = eDao.findById(idEsperto);
		setE(e);
	}
	
	@Override
	public boolean execute() {
		
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		IEspertiService es = new EspertiService();
		
		// controlli per rendere "safe" il metodo
		if(getE() != null){
			try {
				set = es.ricercaTuttePrecedentiIncarichi(getE().getId());
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
		}	
		
		return res;
	}

	
		
}
