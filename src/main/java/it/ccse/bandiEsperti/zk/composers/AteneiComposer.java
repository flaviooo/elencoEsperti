package it.ccse.bandiEsperti.zk.composers;


import it.ccse.bandiEsperti.business.command.AdminCommand;

import it.ccse.bandiEsperti.business.command.AteneiCommand;
import it.ccse.bandiEsperti.business.command.CittaCommand;
import it.ccse.bandiEsperti.business.command.ProvincieCommand;
import it.ccse.bandiEsperti.business.model.Atenei;
import it.ccse.bandiEsperti.business.model.Citta;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Provincie;

import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;


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
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;


public class AteneiComposer extends GenericForwardComposer implements AdministrationComposer {
	private static final long serialVersionUID = -3011711889760264355L;

	private Textbox txt_nome;
	private Set<Citta> listaCitta = new LinkedHashSet<Citta>();
	private Set<Provincie> listaProvincie = new LinkedHashSet<Provincie>();
	private Set<Serializable> lista = new LinkedHashSet<Serializable>();
	private static final String  winId="win_atenei";
	private Button bnt_aggiungi;
	private Button bnt_aggiorna;
	private Combobox cmb_citta;
	private Combobox cmb_provincie;
	private Atenei itemSelezionato;

	public Set<Provincie> getListaProvincie() {
		return listaProvincie;
	}

	private Esperto esperto;
	
	

	public Set<Citta> getListaCitta() {
		return listaCitta;
	}
	
	@Override
	public Set<Serializable> getLista() {
		return lista;
	}

	@Override
	public Atenei getItemSelezionato() {
		return itemSelezionato;
	}
	
	/** Evento per la modifica del dato */
	@Override
	public void onClick$bnt_modifica(Event event) {
		if (this.itemSelezionato==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		 //Controllo Dati obbligatori
		check();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onModifica",event.getTarget(),null);
		
	}
	
	
	@Override
	public Serializable populateSelectItem(Serializable serializable){
		Atenei atenei= (Atenei)serializable;
		Comboitem comboCitta = cmb_citta.getSelectedItem();
		AdminCommand cittaCommand = new CittaCommand();
		Citta citta = (Citta) cittaCommand.findById(Integer.parseInt(comboCitta.getValue().toString()));
		atenei.setCitta(citta);
		atenei.setDenominazione(txt_nome.getValue());
		return atenei;
	}

	/** Evento per la modifica di un nuovo dato */
	@Override
	public void onClick$bnt_aggiorna(Event event) {
		if (this.itemSelezionato==null){
			throw new WrongValueException("Non sono presenti datori di lavoro da aggiornare");
		}
		
		check();
		AdminCommand command = new AteneiCommand();
		itemSelezionato=(Atenei)populateSelectItem(itemSelezionato);
		
		
		boolean res=command.checkIfExist(itemSelezionato);
		if(res){
				try {
					Messagebox.show("La modifica effettuata a questo Ateneo avrà impatto anche su tutti i Curriculum aventi tale riferimento. Si vuole procedere?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
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
			Atenei atenei = (Atenei) event.getData();
			AdminCommand ateneiCommand = new AteneiCommand();
			esito=ateneiCommand.aggiorna(atenei);
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
			itemSelezionato = (Atenei) rigaAttiva.getValue();
			selectCitta(itemSelezionato.getCitta().getProvincie().getCodiceProvincia());
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

	public void onSelect$cmb_provincie(SelectEvent evt) throws Exception {
		Comboitem item = new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		listaCitta.clear();
		this.cmb_citta.setSelectedIndex(-1);
		this.cmb_citta.getChildren().clear();
		selectCitta(item.getValue().toString());
		
	}
	
	private void selectCitta(String codiceProvincia){
		CittaCommand cittaCommand = new CittaCommand();
		List<Citta> lista = new ArrayList<Citta>(cittaCommand.findByCodiceProvincia(codiceProvincia));
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
		listaCitta.addAll(lista);
		for (Citta citta : listaCitta){
			Comboitem itemCitta = new Comboitem();	
			itemCitta.setValue(citta.getId()); 
			itemCitta.setLabel(citta.getDenominazione());
			this.cmb_citta.appendChild(itemCitta);
		}
	}
	
	@Override
	public void loadValoriDefault(){
		AdminCommand provincieCommand = new ProvincieCommand();
		List<Provincie> lista = new ArrayList<Provincie>(provincieCommand.list());
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
		this.listaProvincie.addAll(lista);
		
	}
	
	private void clearListaProvincie(){
		listaProvincie.clear();
		this.cmb_provincie.setText("");
		this.cmb_provincie.setSelectedIndex(-1);
		this.cmb_provincie.getChildren().clear();
	}
	
	private void clearListaCitta(){
		listaCitta.clear();
		this.cmb_citta.setText("");
		this.cmb_citta.setSelectedIndex(-1);
		this.cmb_citta.getChildren().clear();
	}
	
	@Override
	public void initModel(boolean isUpdate) throws Exception{
		try{
			lista.clear();
			AdminCommand ateneiCommand = new AteneiCommand();
			List<Atenei> lista = new ArrayList<Atenei>(ateneiCommand.list());
			Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
		
			this.lista.addAll(lista);
			
			if (isUpdate){
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
				clearListaProvincie();
				clearListaCitta();
				this.txt_nome.setValue("");
				loadValoriDefault();
			}else{
				this.esperto = (Esperto) session.getAttribute("login");
				loadValoriDefault();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	private void check() {
        if(this.cmb_provincie.getSelectedItem() ==null){
        	throw new WrongValueException("Provincia obbligatoria");
		}
        
        if(this.cmb_citta.getSelectedItem() ==null){
        	throw new WrongValueException("Città obbligatoria");
		}
        
        if(this.txt_nome.getValue().isEmpty()){
        	throw new WrongValueException("Nome obbligatorio");
		}
	}
    


	/** Evento per l'aggiunta di un nuovo dato */
	@Override
	public void onClick$bnt_aggiungi(Event event) {
		 //Controllo Dati obbligatori
		check();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
    	Events.echoEvent("onAggiungi",event.getTarget(),null);
		
	}
	@Override
	public void onAggiungi(Event event){
		
		try {
			Integer id = null;
			Atenei atenei = new Atenei();
			atenei=(Atenei)populateSelectItem(atenei);
			AdminCommand ateneiCommand = new AteneiCommand();
			id=ateneiCommand.inserisci(atenei);
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
	
	
	
	//primo evento generato al clic su un pulsante di rimozione
		@Override
		public void onVerificaRimuovi(ForwardEvent event){
			
			try{
				Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
				Atenei atenei = (Atenei) rigaAttiva.getValue();
				AdminCommand command = new AteneiCommand();
				boolean res=command.checkIfExist(atenei);
				if(res){
					Messagebox.show("La cancellazione di questo Ateneo non può essere effettuata, la voce è presente all'interno di uno o più Curriculum Vitae"); 
					return;		
				}else{
					Clients.showBusy("Cancellazione in corso... ");
			    	Events.echoEvent("onRimuovi",event.getTarget(),atenei);
				
				}
				
			}catch(Exception ex){
				throw new WrongValueException("Errore nella cancellazione dei Dati: " + ex.getMessage());
			}
		}
			


	@Override
	public void onRimuovi(Event event){	
		boolean esito;
		try{
			Atenei atenei = (Atenei) event.getData();
			AdminCommand command = new AteneiCommand();
			esito = command.delete(atenei.getId());
			
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
	
	
	
	
}