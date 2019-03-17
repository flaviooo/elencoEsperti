package it.ccse.bandiEsperti.zk.composers;

import it.ccse.bandiEsperti.business.model.Esperto;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public class WelcomeAdminComposer extends GenericForwardComposer {
	
	private static final long serialVersionUID = -7732338175158044791L;
	
	

	private Esperto esperto;
	
	// ********************************
	// ad utilizzo del binder
	// ********************************
	public Esperto getEsperto() {
		return esperto;
	}
	// ********************************
	
	public void doAfterCompose(Component win) throws Exception {
		super.doAfterCompose(win);
		
		// Controllo di Sicurezza per accertare che l'utente sia effettivamente "admin"
		Esperto user = (Esperto) session.getAttribute("login");
		// *****************************************************
		// Se l'utente non è 'admin', torna alla pagina di login
    	if(user.getUsername().equals("admin")) {
    		String url = "/pages/BandiEspertiPages/login.zul";
    		Executions.sendRedirect(url);
    	}
		// *****************************************************    	
	}
}
