package it.ccse.bandiEsperti.zk.composers;


import it.ccse.bandiEsperti.business.command.AdminCommand;
import it.ccse.bandiEsperti.business.command.AggiornaListaEsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.CancellaEsperienzaLavorativaCommand;
import it.ccse.bandiEsperti.business.command.EsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.SalvaEsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.interfaces.ICommand;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.zk.comparators.EsperienzeProfessionaliComparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

public class EsperienzeComposer_old extends GenericForwardComposer {
	private static final long serialVersionUID = -4723577556002282085L;
	
	
	
	private Datebox dta_data_inizio;
	private Datebox dta_data_fine;
	private Checkbox in_corso;
	private Textbox txt_attivita_lavorativa;
	private Textbox txt_tipo_esperienza;
	private Textbox txt_datore_lavoro;
	private Textbox txt_ruolo;
	
	private Button btn_aggiorna_esperienze;
	
	private Esperto user;
	private Set<EsperienzaLavorativa> listaEsperienze;
	
	
	//****************************
	// richiamati dal binder
	//****************************
	public Set<EsperienzaLavorativa> getListaEsperienze() {
		return this.listaEsperienze;
	}
	//****************************
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		this.user = (Esperto) session.getAttribute("login");
		
		if(user.getInviato() && user.getStato() != 1) {
    		String redirectUrlInviato = "/pages/BandiEspertiPages/homeEsperto.zul";
    		Executions.sendRedirect(redirectUrlInviato);
    	}
		
		this.initModel(false);
	}
	
//	@Override
//	public void doFinally(){
//		try {
//			super.doFinally();
//			if (this.listaEsperienze!=null){
//				this.listaEsperienze = null;
//			}
//		} catch (Exception e) {
//			
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		try{
			AdminCommand command = new EsperienzeLavorativeCommand(this.user.getId());
			this.listaEsperienze = new TreeSet<EsperienzaLavorativa>(new EsperienzeProfessionaliComparator());
			this.listaEsperienze.addAll(command.list());
			
			if (this.listaEsperienze!=null){
				this.btn_aggiorna_esperienze.setDisabled(!(this.listaEsperienze.size()>0));
			}
			
			if (isUpdate){
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/** Evento per il salvataggio dei dati modificati */
	public void onClick$btn_aggiorna_esperienze(Event event) {
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onAggiornaEsperienze",event.getTarget(),null);
	}
	
	public void onAggiornaEsperienze(Event event){			
		try{
			this.aggiornaEsperienze();
		}catch(Exception ex){
			throw new WrongValueException("Errore nel salvataggio dei dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
		
	}
	
	private void aggiornaEsperienze() throws Exception{
		boolean esito = false;
		
		List<EsperienzaLavorativa> list = new ArrayList<EsperienzaLavorativa>(this.listaEsperienze); 
		ICommand command = new AggiornaListaEsperienzeLavorativeCommand(list);
		esito = command.execute();
		
		if (!esito){
			throw new Exception("non è possibile eseguire l'operazione di aggiornamento dei dati");
		}
		
		this.initModel(true);
	}
	
	/** Evento per l'inserimento di un nuovo dato */
	public void onClick$bnt_aggiungi_esperienze(Event event) {
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onAggiungiEsperienze",event.getTarget(),null);
	}
	
	public void onAggiungiEsperienze(Event event){
		EsperienzaLavorativa esperienzaRif = null;
		try{
			EsperienzaLavorativa esperienza = new EsperienzaLavorativa();
			esperienza.setDataInizio(dta_data_inizio.getValue());
			esperienza.setDataFine(dta_data_fine.getValue());
			esperienza.setInCorso(in_corso.isChecked());
			esperienza.setAttivitaLavorative(txt_attivita_lavorativa.getValue());
			esperienza.setTipoEsperienza(txt_tipo_esperienza.getValue());
			esperienza.setDatoreLavoro(txt_datore_lavoro.getValue());
			esperienza.setRuolo(txt_ruolo.getValue());
			esperienzaRif = esperienza;
			
			System.out.println(dta_data_fine.getValue());
			System.out.println(in_corso.isChecked());
			if (dta_data_fine.getValue()==null && !in_corso.isChecked()){
			throw new WrongValueException("In assensa di 'Anno di fine' è obbligatorio selezionare 'In corso'" );
		}			
			this.listaEsperienze.add(esperienza);
			
			this.aggiungiEsperienze();
		}catch(Exception ex){
			if (this.listaEsperienze!=null && esperienzaRif!=null){
				this.listaEsperienze.remove(esperienzaRif);
			}
			throw new WrongValueException("Errore nel salvataggio dei dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
			//Clients.alert("Salvataggio preso in consegna dal sistema. Al più presto verrà aggiornato.");
			try {
				Messagebox.show("Salvataggio preso in consegna dal sistema. Al più presto verrà aggiornato.","Info", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// reset dei campi a video
			txt_datore_lavoro.setValue(" ");
			txt_attivita_lavorativa.setValue(" ");
			txt_ruolo.setValue(" ");
			txt_tipo_esperienza.setValue(" ");
			dta_data_fine.setValue(null);
			dta_data_inizio.setValue(null);
			in_corso.setChecked(false);
		}
		
	}
	
	private void aggiungiEsperienze() throws Exception{
		boolean esito = false;
		
		ICommand command = new SalvaEsperienzeLavorativeCommand(this.user.getId(), this.listaEsperienze);
		esito = command.execute();
		
		if (!esito){
			throw new Exception("non è possibile eseguire l'operazione di inserimento dei nuovi dati");
		}
		
		this.initModel(true);
	}
	
	//primo evento generato al clic su un pulsante di rimozione istruzione
	public void onVerificaRimuoviEsperienze(ForwardEvent event){
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			
			final EsperienzaLavorativa esperienza = (EsperienzaLavorativa)rigaAttiva.getValue();
			
			Messagebox.show("Procedere con l'eliminazione dell'Esperienza selezionata?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
			        new org.zkoss.zk.ui.event.EventListener() {
			            public void onEvent(Event event) throws InterruptedException {
			                if (event.getName().equals("onOK")) {	                	
			                	Clients.showBusy("validazione in corso... ");
			            		Events.echoEvent("onRimuoviEsperienze",Path.getComponent("/win_esperienze"),esperienza);
			                } else {
			                    
			                }
			            }
			        });
		}catch(WrongValueException ex){
			throw ex;
		}catch(Exception ex){
			
		}
	}
	
	public void onRimuoviEsperienze(Event event){	
		boolean esito = false;

		try{
			EsperienzaLavorativa esperienzaSelezionata = (EsperienzaLavorativa) event.getData();
			ICommand command = new CancellaEsperienzaLavorativaCommand(esperienzaSelezionata.getId());
			esito = command.execute();
			
			if (!esito){
				throw new Exception("non è possibile eseguire l'operazione di rimozione del record selezionato");
			}
			
			this.initModel(true);
		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
		
	}
	
}
	