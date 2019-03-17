package it.ccse.bandiEsperti.zk.composers;

import it.ccse.bandiEsperti.business.command.AdminCommand;
import it.ccse.bandiEsperti.business.command.TemiCommand;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Tema;
import it.ccse.bandiEsperti.zk.comparators.TemiComparator;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;


public class SettoriComposer extends GenericForwardComposer implements AdministrationComposer {
	private static final long serialVersionUID = -3011711889760264355L;

	private Textbox txt_nome;
	
	private Set<Serializable> lista = new LinkedHashSet<Serializable>();
	private static final String  winId="win_settori";
	private Button bnt_aggiungi;
	private Button bnt_aggiorna;

	private Tema itemSelezionato;
	private Esperto esperto;

	@Override
	public Set<Serializable> getLista() {
		return lista;
	}

	@Override
	public Tema getItemSelezionato() {
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
	
	
	
	
	@Override
	public Serializable populateSelectItem(Serializable serializable){
		Tema tema = (Tema)serializable;
		tema.setNome(txt_nome.getValue());
		return tema;
	}

	/** Evento per la modifica di un nuovo dato */
	@Override
	public void onClick$bnt_aggiorna(Event event) {
		if (this.itemSelezionato==null){
			throw new WrongValueException("Non sono presenti datori di lavoro da aggiornare");
		}
		check();
		AdminCommand command = new TemiCommand();
		itemSelezionato=(Tema)populateSelectItem(itemSelezionato);
		boolean res=command.checkIfExist(itemSelezionato);
		if(res){
				try {
					Messagebox.show("La modifica effettuata alla Settore avr� impatto su tutti i Curriculum aventi tale riferimento. Si vuole procedere?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
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
			Tema tema = (Tema) event.getData();
			AdminCommand temiCommand = new TemiCommand();
			esito=temiCommand.aggiorna(tema);
			if (!esito){
				throw new Exception("non � possibile eseguire l'operazione di aggiornamento dei dati");
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
			itemSelezionato = (Tema) rigaAttiva.getValue();
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
	}
	
	@Override
	public void initModel(boolean isUpdate) throws Exception{
		try{
			lista.clear();
			AdminCommand temiCommand = new TemiCommand();
			List<Tema> lista = new ArrayList<Tema>(temiCommand.list());
			Collections.sort(lista, new TemiComparator());
			this.lista.addAll(lista);
			
			if (isUpdate){
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
				this.txt_nome.setValue("");
			}else{
				this.esperto = (Esperto) session.getAttribute("login");
				loadValoriDefault();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private void check() {
        if(this.txt_nome.getValue().isEmpty()){
        	throw new WrongValueException("Nome obbligatorio");
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
			Tema tema = new Tema();
			tema=(Tema)populateSelectItem(tema);
			AdminCommand temiCommand = new TemiCommand();
			id=temiCommand.inserisci(tema);
			if (id==null){
				throw new Exception("non � possibile eseguire l'operazione di aggiornamento dei dati");
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
	
	
	   //primo evento generato al clic su un pulsante di rimozione
		@Override
		public void onVerificaRimuovi(ForwardEvent event){
			
			try{
				Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
				Tema tema = (Tema) rigaAttiva.getValue();
				AdminCommand command = new TemiCommand();
				boolean res=command.checkIfExist(tema);
				if(res){
					Messagebox.show("La cancellazione del Settore non pu� essere effettuata, la voce � presente all'interno di uno o pi� Curriculum Vitae"); 
					return;		
				}else{
					Clients.showBusy("Cancellazione in corso... ");
			    	Events.echoEvent("onRimuovi",event.getTarget(),tema);
				
				}
				
			}catch(Exception ex){
				throw new WrongValueException("Errore nella cancellazione dei Dati: " + ex.getMessage());
			}
		}
		
	
	

	@Override
	public void onRimuovi(Event event){	
		boolean esito;
		try{
			Tema tema = (Tema) event.getData();
			AdminCommand command = new TemiCommand();
			esito = command.delete(tema.getId());
			
			if (!esito){
				throw new Exception("non � possibile eseguire l'operazione di rimozione del record selezionato");
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
	
	
	
	
}