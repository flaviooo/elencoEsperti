package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.dto.EspertoDTO;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;
import it.ccse.bandiEsperti.enums.OperazioniAmministratore;
import it.ccse.bandiEsperti.utils.CVHelper;
import it.ccse.bandiEsperti.utils.Constants;

public class ModificaStatoEspertoCommand  extends AbstractCommand {

	private OperazioniAmministratore operazione;
	private EspertoDTO espertoDTO;


	public ModificaStatoEspertoCommand(EspertoDTO espertoDTO, OperazioniAmministratore operazione) {
		super(null);
		this.espertoDTO = espertoDTO;
		this.operazione = operazione;
	}

	@Override
	public boolean execute() {
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		if (operazione == null)
			return true;

		IEspertiService es = new EspertiService();

		// controlli per rendere "safe" il metodo

		try {
			
			int tmpStato=-1;
			boolean statoBloccato=false;
			
			switch (operazione) {
			case APPROVA:
				tmpStato = Constants.STATO_ESPERTO_APPROVATO;
				statoBloccato = espertoDTO.isBloccato();
				break;
			case RIFIUTA:
				tmpStato = Constants.STATO_ESPERTO_RIFIUTATO;
				statoBloccato = espertoDTO.isBloccato();
				break;
			case SBLOCCA:
				tmpStato = espertoDTO.getStato();
				statoBloccato = false;
				break;
			case BLOCCA:
				tmpStato = espertoDTO.getStato();
				statoBloccato = true;
				break;
			case REVOCA:
				tmpStato = Constants.STATO_ESPERTO_SOSPESO;
				statoBloccato = espertoDTO.isBloccato();
				break;
			}
			es.modificaStatoEsperto(espertoDTO.getId(), tmpStato, statoBloccato);
			
			if (operazione == OperazioniAmministratore.APPROVA || operazione == OperazioniAmministratore.REVOCA)
			{
				CVHelper helper = new CVHelper();
				helper.spostaCVEsperto(espertoDTO, (operazione == OperazioniAmministratore.APPROVA));
			}
			
			res = true;
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}

		return res;
	}

	

}
