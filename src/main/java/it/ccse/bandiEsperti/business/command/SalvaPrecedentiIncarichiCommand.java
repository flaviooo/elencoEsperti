/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.model.PrecedenteIncarico;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

import java.util.Set;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class SalvaPrecedentiIncarichiCommand extends AbstractCommand {

	/**
	 * Deve esistere un esperto all'atto del salvataggio delle relative pubblicazioni
	 * */
	public SalvaPrecedentiIncarichiCommand(Integer idEsperto, Set<PrecedenteIncarico> set) {
		super(null);
//		EspertiDAO eDao = new EspertiDAO();
//		Esperto e = eDao.findById(idEsperto);
//		e.getCognome();
//		e.setPrecedentiIncarichi(set);
//		setE(e);
		
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
				es.salvaListaPrecedentiIncarichiPerEsperto(getIdEsperto(), getSet());
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
		}	
		
		return res;
	}

	
		
}
