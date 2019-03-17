package it.ccse.bandiEsperti.zk.composers;


import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import it.ccse.bandiEsperti.business.command.LoginCommand;
import it.ccse.bandiEsperti.business.model.Esperto;

public class Login extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Textbox username;
	private Textbox password;
	private Textbox search;
	private Label lbl_logged;
	private Input entra;
	private Input esci;

	
	public void onCreate$win_home(Event evt) throws Exception {
		Esperto user = (Esperto) session.getAttribute("login");
		if (user != null) {
			lbl_logged.setValue("Logged as: " + user.getUsername());
			entra.setVisible(false);
			esci.setVisible(true);
			
		} else {
			
			entra.setVisible(true);
			esci.setVisible(false);
		}
	}
	
	public void onOK$password() throws Exception{
		PerformLogin();
		}

	public void onClick$entra(Event evt) throws Exception {
		
		PerformLogin();
	}

	private void PerformLogin() throws InterruptedException {
		if (session.getAttribute("login") == null) {
			LoginCommand lc = new LoginCommand(username.getValue(), password.getValue());
			lc.execute();
			
			if (lc.getE() != null) {
				Messagebox.show("Benvenuto, " + lc.getE().getUsername(), null,
						Messagebox.OK, Messagebox.NONE);
				session.setAttribute("login", lc.getE());
				username.setValue("");
				password.setValue("");
				lbl_logged.setValue("Logged as: " + lc.getE().getUsername());
				entra.setVisible(false);
				esci.setVisible(true);
				
				// Verifica se l'utente è l'amministratore.
				/* 
				 * Ogni pagina di amministrazione deve essere controllata per vedere
				 * che solo l'Amministrazione (utente 'admin') possa accedervi.
				 *  
				 */
				if(lc.getE().getAdmin()) {
					// AMMINISTRATORE
					Executions.sendRedirect("welcomePageAdmin.zul");
				} else{
					// UTENTE NORMALE
					if(lc.getE().getInviato())
						Executions.sendRedirect("homeEspertoInviato.zul");
					else
						Executions.sendRedirect("welcomePage.zul");
				}
			} else {
				Messagebox.show("Combinazione Username/Password errata.",
						"Errore", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	public void onClick$esci(Event evt) throws Exception {
		session.setAttribute("login", null);
		alert("Logout effettuato");
		lbl_logged.setValue("Not logged");
		entra.setVisible(true);
		esci.setVisible(false);
	}

	public void onFocus$search(Event evt) throws Exception {
		search.setValue("");
	}

	

}

