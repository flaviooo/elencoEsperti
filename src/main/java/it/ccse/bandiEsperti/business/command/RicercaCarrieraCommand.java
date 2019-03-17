/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.dao.EspertiDAO;
import it.ccse.bandiEsperti.business.dao.IEspertiDAO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class RicercaCarrieraCommand extends AbstractCommand {
	FiltroRicercaEspertiDTO filtroRicerca = null;

	/**
	 * Deve esistere un esperto all'atto del salvataggio delle relative pubblicazioni
	 * */
	public RicercaCarrieraCommand(Integer idEsperto) {
		super(null);
		IEspertiDAO eDao = new EspertiDAO();
		Esperto e = eDao.findById(idEsperto);
		setE(e);
	}
	
	public void setFiltriApplicati(FiltroRicercaEspertiDTO filtroRicerca){
		this.filtroRicerca = filtroRicerca;
	}
	
	public EsperienzaLavorativa findCarrieraPrincipaleByIdEsperto(Integer idEsperto){
		
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		
		try {
			return es.findCarrieraPrincipaleByIdEsperto(getE().getId());
			
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
		
		return null;
	}
	
	@Override
	public boolean execute() {
		
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		IEspertiService es = new EspertiService();
		
		// controlli per rendere "safe" il metodo
		if(getE() != null){
			try {
				set = es.findCarriereByIdEsperto(getE().getId());
				
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
		}	
		
		return res;
	}


		
}
