package it.ccse.bandiEsperti.zk.composers;

import java.util.HashMap;
import java.util.Map;

import it.ccse.bandiEsperti.business.command.LoginCommand;
import it.ccse.bandiEsperti.business.model.Esperto;

import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.sun.mail.imap.protocol.ENVELOPE;


public class Header extends GenericForwardComposer {

	private static final long serialVersionUID = 862041909319557464L;
	private Label lbl_logged;
	private Input entraa;
	private Input escia;
	private Textbox username;
	private Textbox password;

	
	public void onCreate$win_header(Event evt) throws Exception {
		Esperto user = (Esperto) session.getAttribute("login");
		if (user != null) {
			lbl_logged.setValue("Logged as: " + user.getUsername());
			entraa.setVisible(false);
			escia.setVisible(true);
		}else {
			entraa.setVisible(true);
			escia.setVisible(false);
		}
		
	}

	public void onClick$escia(Event evt) throws Exception {
		session.setAttribute("login", null);
		alert("Logout effettuato");
		lbl_logged.setValue("Not logged");
		entraa.setVisible(true);
		escia.setVisible(false);
		Executions.sendRedirect("login.zul");
	}

	public void onClick$entraa(Event evt) throws Exception {
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
				entraa.setVisible(false);
				escia.setVisible(true);
				
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

		//Executions.sendRedirect("login.zul");
	}
	
	public void onClick$btnInfoCookie(Event event){
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			Window window = (Window) Executions.createComponents("/pages/BandiEspertiPages/infoCookie.zul", this.self, params);
			window.setClosable(true);
			window.doModal();
			
		}catch(Exception ex){
			throw new WrongValueException("Errore nella apertura della finestra di ricerca: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
	}
	
	

}
