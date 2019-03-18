/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class RicercaCompetenzeCommand extends AbstractCommand {
	FiltroRicercaEspertiDTO filtroRicerca = null;
	
	/**
	 * Deve esistere un esperto all'atto del salvataggio delle relative pubblicazioni
	 * */
	public RicercaCompetenzeCommand(Integer idEsperto) {
		super(null);
//		EspertiDAO eDao = new EspertiDAO();
//		Esperto e = eDao.findById(idEsperto);
//		setE(e);
		setIdEsperto(idEsperto); 
//		setSet(set);		
	}
	
	public void setFiltriApplicati(FiltroRicercaEspertiDTO filtroRicerca){
		this.filtroRicerca = filtroRicerca;
	}
	
	@Override
	public boolean execute() {
		
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		IEspertiService es = new EspertiService();
		
		// controlli per rendere "safe" il metodo
		if(getIdEsperto() != null){
			try {
				set = es.ricercaTutteCompetenze(getIdEsperto(), filtroRicerca);
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
		}	
		
		return res;
	}

		
}
