/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import java.util.List;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command che recupera tutti gli esperti presenti nell'applicazione
 */
public class RecuperaTuttiEspertiCommand extends AbstractCommand {

	/**
	 * Costruttore Command
	 * */
	
	private boolean approvato=false;
	
	public RecuperaTuttiEspertiCommand(Esperto e) {
		super(e);
	}
	
	public RecuperaTuttiEspertiCommand(boolean approvato) {
		super(null);
		this.approvato = approvato;
	}
	
	public List<Esperto> ricercaEsperti(FiltroRicercaEspertiDTO filtroRicerca) {
		List<Esperto> listaEsperti = null;
		try {
			logger.debug("execute del Command: [" + this.getClass().getName() + "]");
			
			if(filtroRicerca == null){
				throw new Exception("Il filtro di ricerca non può essere Null");
			}
			
			IEspertiService es = new EspertiService();
			listaEsperti = es.ricercaEsperti(filtroRicerca, approvato);
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
		
		return listaEsperti;
	}

	
		
}
