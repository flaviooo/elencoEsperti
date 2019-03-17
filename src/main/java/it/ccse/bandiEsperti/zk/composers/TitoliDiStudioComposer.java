package it.ccse.bandiEsperti.zk.composers;

import it.ccse.bandiEsperti.business.command.AdminCommand;
import it.ccse.bandiEsperti.business.command.TipoLaureaCommand;
import it.ccse.bandiEsperti.business.command.TitoloLaureaCommand;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.TipoLaurea;
import it.ccse.bandiEsperti.business.model.TitoloLaurea;
import it.ccse.bandiEsperti.zk.comparators.TipoLaureaComparator;
import it.ccse.bandiEsperti.zk.comparators.TitoloLaureaComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;


public class TitoliDiStudioComposer extends GenericForwardComposer implements AdministrationComposer {
	private static final long serialVersionUID = -3011711889760264355L;

	private Textbox txt_titolo;
	
	private Set<TipoLaurea> listaTipoLaurea = new LinkedHashSet<TipoLaurea>();
	private Set<Serializable> lista = new LinkedHashSet<Serializable>();
	
	private static final String  winId="win_titoliDiStudio";

	private Combobox cmb_tipo;
	
	private Button bnt_aggiungi;
	private Button bnt_aggiorna;

	private TitoloLaurea itemSelezionato;

	private Esperto esperto;

	@Override
	public TitoloLaurea getItemSelezionato() {
		return itemSelezionato;
	}
	
	
	
	
	/** Evento per la modifica del dato */
	@Override
	public void onClick$bnt_modifica(Event event) {
		if (this.itemSelezionato==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		check();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onModifica",event.getTarget(),null);
		
	}
	
	private Serializable setTipoLaurea(Serializable serializable){
		TitoloLaurea titoloLaurea= (TitoloLaurea)serializable;
		//sono arrivato qui
		AdminCommand tipoLaureaCommand = new TipoLaureaCommand();
		TipoLaurea tipoLaurea = (TipoLaurea)tipoLaureaCommand.findById((Integer)(this.cmb_tipo.getSelectedItem().getValue()));
		
		if(tipoLaurea!=null){
			titoloLaurea.setTipoLaurea(tipoLaurea);
		}
		return titoloLaurea;
	}
	
	
	@Override
	public Serializable populateSelectItem(Serializable serializable){
		TitoloLaurea titoloLaurea= (TitoloLaurea)serializable;
		titoloLaurea.setDenominazione(txt_titolo.getValue());
		titoloLaurea=(TitoloLaurea)setTipoLaurea(titoloLaurea);
		return titoloLaurea;
	}

	/** Evento per la modifica di un nuovo dato */
	@Override
	public void onClick$bnt_aggiorna(Event event) {
		if (this.itemSelezionato==null){
			throw new WrongValueException("Non sono presenti datori di lavoro da aggiornare");
		}
		check();
		AdminCommand command = new TitoloLaureaCommand();
		itemSelezionato=(TitoloLaurea)populateSelectItem(itemSelezionato);
		boolean res=command.checkIfExist(itemSelezionato);
		if(res){
				try {
					Messagebox.show("La modifica effettuata a questo Titolo di Studio avrà impatto anche su tutti i Curriculum aventi tale riferimento. Si vuole procedere?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
					    new org.zkoss.zk.ui.event.EventListener() {
					        public void onEvent(Event event) throws InterruptedException {
					            if (event.getName().equals("onOK")) {	                	
					            	Clients.showBusy("validazione in corso... ");
					        		Events.echoEvent("onMod",Path.getComponent("/"+winId),itemSelezionato);
					            } else {
					                return;
					            }
					        }
						});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else{
			Clients.showBusy("Validazione e Salvataggio in corso... ");
	    	Events.echoEvent("onMod",event.getTarget(),itemSelezionato);
		
		}
	}
	
	@Override
	public void onMod(Event event){
		boolean esito = false;
		try {
			TitoloLaurea titoloLaurea = (TitoloLaurea) event.getData();
			AdminCommand titoloLaureaCommand = new TitoloLaureaCommand();
			esito=titoloLaureaCommand.aggiorna(titoloLaurea);
			if (!esito){
				throw new Exception("non è possibile eseguire l'operazione di aggiornamento dei dati");
			}else{
				Messagebox.show("Salvataggio completato.","Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Clients.clearBusy();
			this.bnt_aggiorna.setVisible(false);
			this.bnt_aggiungi.setVisible(true);
		
			try {
				this.initModel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onModifica(ForwardEvent event){	
		
		try{
			this.bnt_aggiorna.setVisible(true);
			this.bnt_aggiungi.setVisible(false);
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			itemSelezionato = (TitoloLaurea)rigaAttiva.getValue();
			((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();

		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
		
	}
	
	

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.esperto = (Esperto) session.getAttribute("login");
		if(esperto.getInviato()) {
			String redirectUrlInviato = "/pages/BandiEspertiPages/homeEsperto.zul";
			Executions.sendRedirect(redirectUrlInviato);
		}
		this.initModel(false);
	}

	@Override
	public void loadValoriDefault(){
		TipoLaureaCommand tipoLaureaCommand = new TipoLaureaCommand();
		List<TipoLaurea> lista = new ArrayList<TipoLaurea>(tipoLaureaCommand.list());
		Collections.sort(lista, new TipoLaureaComparator());
		listaTipoLaurea.addAll(lista);
		itemSelezionato=new TitoloLaurea();
		itemSelezionato.setTipoLaurea(lista.get(0));
	}
	
	private void clearListaTipoLaurea(){
		listaTipoLaurea.clear();
		this.cmb_tipo.setText("");
		this.cmb_tipo.setSelectedIndex(-1);
		this.cmb_tipo.getChildren().clear();
	}
	
	@Override
	public void initModel(boolean isUpdate) throws Exception{
		try{
			lista.clear();
			AdminCommand Command = new TitoloLaureaCommand();
		
			List<TitoloLaurea> lista = new ArrayList<TitoloLaurea>(Command.list());
			Collections.sort(lista, new TitoloLaureaComparator());
			this.lista.addAll(lista);
			
			if (isUpdate){
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
				this.txt_titolo.setValue("");
			}else{
				this.esperto = (Esperto) session.getAttribute("login");
				loadValoriDefault();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	private void check() {
		if(this.cmb_tipo.getSelectedItem()== null){
        	throw new WrongValueException("Tipo obbligatorio");
		}
		
        if(this.txt_titolo.getValue().isEmpty()){
        	throw new WrongValueException("Titolo obbligatorio");
		}
        
	}
	
	/** Evento per l'aggiunta di un nuovo dato */
	@Override
	public void onClick$bnt_aggiungi(Event event) {
		check();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
    	Events.echoEvent("onAggiungi",event.getTarget(),null);
		
	}
	@Override
	public void onAggiungi(Event event){
		
		try {
			Integer id = null;
			TitoloLaurea titoloLaurea = new TitoloLaurea();
			titoloLaurea=(TitoloLaurea)populateSelectItem(titoloLaurea);
			AdminCommand titoloLaureaCommand = new TitoloLaureaCommand();
			id=titoloLaureaCommand.inserisci(titoloLaurea);
			if (id==null){
				throw new Exception("non è possibile eseguire l'operazione di aggiornamento dei dati");
			}else{
				Messagebox.show("Salvataggio completato.","Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WrongValueException("Errore nel salvataggio dei dati: " + e.getMessage());
		}finally{
			Clients.clearBusy();
			try {
				this.initModel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//primo evento generato al clic su un pulsante di rimozione istruzione
	@Override
	public void onVerificaRimuovi(ForwardEvent event){
		
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			TitoloLaurea titoloLaurea = (TitoloLaurea) rigaAttiva.getValue();
			AdminCommand command = new TitoloLaureaCommand();
			boolean res=command.checkIfExist(titoloLaurea);
			if(res){
				Messagebox.show("La cancellazione del Titolo di Studio non può essere effettuata, la voce è presente all'interno di uno o più Curriculum Vitae"); 
				return;		
			}else{
				Clients.showBusy("Cancellazione in corso... ");
		    	Events.echoEvent("onRimuovi",event.getTarget(),titoloLaurea);
			
			}
			
		}catch(Exception ex){
			throw new WrongValueException("Errore nella cancellazione dei Dati: " + ex.getMessage());
		}
	}
	

	@Override
	public void onRimuovi(Event event){	
		boolean esito;
		try{
			TitoloLaurea titoloLaurea = (TitoloLaurea) event.getData();
			AdminCommand command = new TitoloLaureaCommand();
			esito = command.delete(titoloLaurea.getId());
			if (!esito){
				throw new Exception("non è possibile eseguire l'operazione di rimozione del record selezionato");
			}
		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
			try {
				this.initModel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public Set<TipoLaurea> getListaTipoLaurea() {
		return listaTipoLaurea;
	}

	@Override
	public Set<Serializable> getLista() {
		return lista;
	}
	
	
	
}