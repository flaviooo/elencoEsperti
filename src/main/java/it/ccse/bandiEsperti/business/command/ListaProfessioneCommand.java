/**
 * 
 */
package it.ccse.bandiEsperti.business.command;


import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Professione;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla selezione del tipo laurea
 */
public class ListaProfessioneCommand extends AbstractCommand {

	


	public ListaProfessioneCommand(Esperto e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	
	public Professione findById(Integer id){
		
		
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			return es.findProfessioneById(id);
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}
		return null;
	}

	public boolean execute(){
		
	
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		IEspertiService es = new EspertiService();
		try {
			set=es.listaProfessione();
		
		} catch (Exception e) {
			logger.debug(" errore");
		
		}

		
		return true;
	}

	
}
