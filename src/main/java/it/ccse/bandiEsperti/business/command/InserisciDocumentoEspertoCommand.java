package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.model.DocumentoAllegatoEsperto;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

public class InserisciDocumentoEspertoCommand extends AbstractCommand {

	private DocumentoAllegatoEsperto allegato;

	public InserisciDocumentoEspertoCommand(Esperto esperto, DocumentoAllegatoEsperto allegato) {
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
				es.inserisciDocumentoAllegatoEsperto(getE(), allegato);
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nell'esecuzione del service:" + e.toString());
			}
		}


		return res;
	}

	

}
