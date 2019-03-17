package it.ccse.bandiEsperti.zk.composers;

import java.util.Date;

import it.ccse.bandiEsperti.business.command.CheckUserExistenceCommand;
import it.ccse.bandiEsperti.business.command.RegistrazioneCommand;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.utils.Constants;
import it.ccse.bandiEsperti.utils.invioMail.InvioMailHelper;



import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class Registrazione extends GenericForwardComposer {

	private static final long serialVersionUID = -1032083816674209453L;
	private static final Logger log = Logger.getLogger(Registrazione.class);
	

	
	private Textbox txt_password;
	private Textbox txt_password_confermata;
	private Textbox txt_email_confermata;
	private Textbox txt_email;
	private Textbox txt_nome;
	private Textbox txt_cognome;
	private Textbox txt_cf;
	private Textbox txt_pi;
	
	
	public void doAfterCompose(Component win) throws Exception {
		super.doAfterCompose(win);
	}
	
	public void onClick$btn_salva(Event event) throws Exception {
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onSalvaEsperto",event.getTarget(),null);
	}
	
	public void onSalvaEsperto(Event evt) throws Exception {
		boolean esitoRegistrazione = false;
		
		try{
			
			// CHECK SULLA PASSWORD
			if (!txt_password.getValue().equals(txt_password_confermata.getValue())){
				throw new WrongValueException("Verificare che la password sia digitata correttamente!");
			}
			// CHECK SULLA mail
			if (!txt_email.getValue().equals(txt_email_confermata.getValue())){
				throw new WrongValueException("Verificare che la mail sia digitata correttamente!");
			}
			
			// CHECK SUL NOME UTENTE, CHE NON DEVE ESSERE 'admin'
			if (txt_cf.getValue().equals("admin")){
				throw new WrongValueException("Attenzione! Username non valido!");
			}
			
			Esperto e = new Esperto();
			e.setUsername(txt_cf.getValue());
			e.setPassword(txt_password.getValue());
			e.setEmail(txt_email.getValue());
			e.setNome(txt_nome.getValue());
			e.setCognome(txt_cognome.getValue()); 
			e.setCf(txt_cf.getValue());
			e.setTimestamp(new Date());
			e.setPiva(txt_pi.getValue());
			/**
			e.setTel(txt_telefono.getValue());
			e.setCel(txt_cell.getValue());
			e.setFax(txt_fax.getValue());
			e.setDomicilio(txt_domicilio.getValue());
			e.setResidenza(txt_residenza.getValue());
			e.setDataNascita(dta_data_nascita.getValue());
			**/
			e.setAdmin(false);
			e.setStato(Constants.STATO_ESPERTO_SOSPESO); //stato sospeso

			// TODO sostituire con codice StringBuilder
			String codiceDomanda = "NONINVIATO";
						
			e.setCodiceDomanda(codiceDomanda);
			e.setInviato(false);
			e.setPrivacy(false);

			// ***********************************************
			// VERIFICA PRE-ESISTENZA ESPERTO
			CheckUserExistenceCommand cuec = new CheckUserExistenceCommand(e);
			cuec.execute();						
			if(cuec.isFlagExistence()){
				
				Messagebox.show("Errore: utente già presente nel sistema!", "Registrazione utente", Messagebox.OK , Messagebox.INFORMATION, 
				        new org.zkoss.zk.ui.event.EventListener() {
				            public void onEvent(Event event) throws InterruptedException {
				            	Executions.sendRedirect("login.zul");
				            }
				        });		
				throw new WrongValueException("utente già presente nella base dati!");
			}

			// ***********************************************
			// REGISTRAZIONE
			RegistrazioneCommand rc = new RegistrazioneCommand(e);
			esitoRegistrazione = rc.execute();
			log.debug("Esito Registrazione: "+esitoRegistrazione);
			if(!esitoRegistrazione){
				throw new WrongValueException("Errore durante il salvataggio: I dati NON sono stati inseriti correttamente");
			}else{
				InvioMailHelper mailHelper = new InvioMailHelper();
				mailHelper.invioMailRegistrazione(e.getEmail(), e.getNome(), e.getCognome(),
						e.getCf(), e.getPassword());
				mailHelper.invioNotificaRegistrazione(e.getCognome(), e.getNome(), e.getTimestamp());
				Messagebox.show("A breve sarà inviata una e-mail all’indirizzo indicato a conferma dell’avvenuta registrazione", "Registrazione utente", Messagebox.OK , Messagebox.INFORMATION, 
			        new org.zkoss.zk.ui.event.EventListener() {
			            public void onEvent(Event event) throws InterruptedException {
			            	Executions.sendRedirect("login.zul");
			            }
			        });
			}
		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
	}
}
