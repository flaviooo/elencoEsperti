package it.ccse.bandiEsperti.zk.composers;

import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;
import it.ccse.bandiEsperti.utils.invioMail.InvioMailHelper;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class RecuperoUtenteComposer extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Textbox email;
	
	public void onClick$btn_invio(Event event) throws Exception
	{
		IEspertiService service = new EspertiService();
		
		Esperto esperto = service.recuperoByEmail(email.getValue());
		if (esperto != null) //email esistente
		{
			InvioMailHelper helper = new InvioMailHelper();
			helper.invioMailRecupero(email.getValue(), esperto.getUsername(),
					esperto.getPassword());
			Messagebox.show("Recupero effettuato con successo. E' stata inviata una mail all'indirizzo specificato.", null,
					Messagebox.OK, Messagebox.NONE, new EventListener() {
						
						@Override
						public void onEvent(Event arg0) throws InterruptedException {
							Executions.sendRedirect("login.zul");
						}
					});
		}
		else
			Messagebox.show("Indirizzo mail non esistente.", null,
					Messagebox.OK, Messagebox.NONE, new EventListener() {
						
						@Override
						public void onEvent(Event arg0) throws InterruptedException {
							Executions.sendRedirect("login.zul");
						}
					});

	}

}
