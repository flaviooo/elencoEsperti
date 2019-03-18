/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.model.Competenza;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

import java.util.Set;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class SalvaCompetenzeCommand extends AbstractCommand {

	/**
	 * Deve esistere un esperto all'atto del salvataggio delle relative pubblicazioni
	 * */
	public SalvaCompetenzeCommand(Integer idEsperto, Set<Competenza> set) {
		super(null);
//		EspertiDAO eDao = new EspertiDAO();
//		Esperto e = eDao.findById(idEsperto);
//		e.getCognome();
//		e.setCompetenze(set);
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
				es.salvaListaCompetenzePerEsperto(getIdEsperto(), getSet());
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
		}	
		
		return res;
	}

	
		
}
