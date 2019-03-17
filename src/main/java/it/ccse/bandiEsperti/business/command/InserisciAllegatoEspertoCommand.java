package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.model.AllegatoPubblEsperto;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

public class InserisciAllegatoEspertoCommand extends AbstractCommand {

	private AllegatoPubblEsperto allegato;

	public InserisciAllegatoEspertoCommand(Esperto esperto, AllegatoPubblEsperto allegato) {
		super(esperto);
		this.allegato = allegato;
	}

	@Override
	public boolean execute() {
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");

		IEspertiService es = new EspertiService();

		// controlli per rendere "safe" il metodo

		if (getE() != null)
		{
			try {
				es.inserisciAllegatoPubbl(getE(), allegato);
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
		}


		return res;
	}

	

}
