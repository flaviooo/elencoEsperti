/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.model.Tema;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

import java.util.List;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class RicercaTuttiTemiCommand extends AbstractCommand {

	public List<Tema> getTemi() {
		return temi;
	}

	public List<Tema> temi;
	
	/**
	 * Deve esistere un esperto all'atto del salvataggio delle relative pubblicazioni
	 * */
	public RicercaTuttiTemiCommand() {
		super(null);
	}
	
	@Override
	public boolean execute() {
		
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService service = new EspertiService();
		temi = service.ricercaTuttiTemi();
		res = true;
		return res;
	}
		
}
