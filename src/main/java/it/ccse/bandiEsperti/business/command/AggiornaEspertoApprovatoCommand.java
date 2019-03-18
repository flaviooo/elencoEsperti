package it.ccse.bandiEsperti.business.command;

import java.io.Serializable;

import it.ccse.bandiEsperti.business.model.InfoEspertoApprovato;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

import org.apache.log4j.Logger;

public class AggiornaEspertoApprovatoCommand extends AbstractCommand {

	private Logger logger = Logger.getLogger(AbstractCommand.class);
	private InfoEspertoApprovato dettaglio;

	public AggiornaEspertoApprovatoCommand(int idEsperto, InfoEspertoApprovato dettaglio) {
		super(null);
		setId(idEsperto);
		this.dettaglio = dettaglio;
	}
	
	@Override
	public boolean execute() {
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		IEspertiService es = new EspertiService();
		
		// controlli per rendere "safe" il metodo
		
			try {
				es.aggiornaDettaglio(getId(), dettaglio);
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
			
		
		return res;
	}

	

	@Override
	public Serializable findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
