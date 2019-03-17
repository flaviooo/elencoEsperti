package it.ccse.bandiEsperti.zk.composers;


import it.ccse.bandiEsperti.business.command.AdminCommand;
import it.ccse.bandiEsperti.business.command.AssociazioniCommand;
import it.ccse.bandiEsperti.business.command.DeliberaCommand;
import it.ccse.bandiEsperti.business.command.EspertoCommand;
import it.ccse.bandiEsperti.business.command.RicercaAssociazioniCommand;
import it.ccse.bandiEsperti.business.command.TemiCommand;
import it.ccse.bandiEsperti.business.model.Associazioni;
import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Tema;
import it.ccse.bandiEsperti.utils.PdfUtils;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;






import it.ccse.bandiEsperti.zk.comparators.DeliberaComparator;
import it.ccse.bandiEsperti.zk.comparators.EspertoComparator;
import it.ccse.bandiEsperti.zk.comparators.TemiComparator;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;


public class AssociazioniComposer extends GenericForwardComposer implements AdministrationComposer {
	private static final long serialVersionUID = -3011711889760264355L;
	
	private Set<Delibera> listaDelibere = new LinkedHashSet<Delibera>();
	private Set<Esperto> listaEsperti = new LinkedHashSet<Esperto>();
	private Listbox list_temi;
	private Listbox list_esperti;
	private List<Associazioni> lastSearchAssociazioni; 

	private Set<Tema> listaTemi = new LinkedHashSet<Tema>();
	
	Grid inboxGrid;
	private Set<Serializable> lista = new LinkedHashSet<Serializable>();
	
	private Button bnt_aggiungi;
	private Button bnt_ricerca;
	private Button bnt_aggiorna;
	private Button bnt_esporta;
	
	private Combobox cmb_delibere;
	private Associazioni itemSelezionato;

	public Set<Tema> getListaTemi() {
		return listaTemi;
	}
	
	public Set<Esperto> getListaEsperti() {
		return listaEsperti;
	}

	public Set<Delibera> getListaDelibere() {
		return listaDelibere;
	}
	

	@Override
	public Set<Serializable> getLista() {
		return lista;
	}

	@Override
	public Associazioni getItemSelezionato() {
		return itemSelezionato;
	}
	
	/** Evento per la modifica del dato */
	@Override
	public void onClick$bnt_modifica(Event event) {
		if (this.itemSelezionato==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onModifica",event.getTarget(),null);
		
	}

	

	public List<Associazioni> populate(Serializable serializable){
		Associazioni associazioni = (Associazioni)serializable;
		List<Associazioni> associazionis = new ArrayList<Associazioni>();
		List<Listitem> itemsTema = new ArrayList<Listitem>(list_temi.getSelectedItems());
		AdminCommand temiCommand = new TemiCommand();
		for(int i=0; i<itemsTema.size(); i++){
				Associazioni associazioniNew = new Associazioni();
				associazioniNew.setDelibera(associazioni.getDelibera());
				//assegno l'esperto selezionato alle nuove associazioni
				associazioniNew.setEsperto(associazioni.getEsperto());
				Tema tema = (Tema) temiCommand.findById(Integer.parseInt(String.valueOf(itemsTema.get(i).getValue())));
				associazioniNew.setTema(tema);
				associazionis.add(associazioniNew);
		}
		return associazionis;
		
		
	}

	/** Evento per la modifica di un nuovo dato */
	@Override
	public void onClick$bnt_aggiorna(Event event) {
		if (this.itemSelezionato==null){
			throw new WrongValueException("Non sono presenti datori di lavoro da aggiornare");
		}
		List<Associazioni> associazioni=populate(itemSelezionato);
		Clients.showBusy("Validazione e Salvataggio in corso... ");
    	Events.echoEvent("onMod",event.getTarget(),associazioni);
    	
		
	}
	
	
	
	/** Evento per la ricerca delle associazioni */
	public void onClick$bnt_ricerca(Event event) {
		try {
			
			
			List<Delibera> listDelibera = new ArrayList<Delibera>();
			AdminCommand deliberaCommand = new DeliberaCommand();
			if(this.cmb_delibere.getSelectedItem()!=null){
				Delibera delibera = (Delibera) deliberaCommand.findById(Integer.parseInt(String.valueOf(this.cmb_delibere.getSelectedItem().getValue())));
				listDelibera.add(delibera);
			}
			AdminCommand temiCommand = new TemiCommand();
			AssociazioniCommand associazioniCommand = new AssociazioniCommand();
			
		
			List<Tema> listTemi = new ArrayList<Tema>();
			if(list_temi.getSelectedItems()!=null && list_temi.getSelectedItems().size()>0){
				List<Listitem> itemsTema = new ArrayList<Listitem>(list_temi.getSelectedItems());
					for(int i=0; i<itemsTema.size(); i++){
						Tema tema = (Tema) temiCommand.findById(Integer.parseInt(String.valueOf(itemsTema.get(i).getValue())));
						listTemi.add(tema);
					}
			}
			List<Esperto> listEsperti = new ArrayList<Esperto>();
			if(list_esperti.getSelectedItems()!=null && list_esperti.getSelectedItems().size()>0){
				List<Listitem> itemsEsperti = new ArrayList<Listitem>(list_esperti.getSelectedItems());
				for(int i=0; i<itemsEsperti.size(); i++){
					for(int j=0; j<itemsEsperti.size(); j++){
						List <Listcell> listcell = itemsEsperti.get(j).getChildren();
			
						Esperto esperto = associazioniCommand.findEspertoById(Integer.parseInt(String.valueOf(listcell.get(0).getValue())));
						listEsperti.add(esperto);
					}
				}
			}
			RicercaAssociazioniCommand ricercaAssociazioniCommand = new RicercaAssociazioniCommand(listEsperti, listTemi, listDelibera);
			lista.clear();
			if(lastSearchAssociazioni!=null){
				lastSearchAssociazioni.clear();
			}
			lastSearchAssociazioni = new ArrayList<Associazioni>(ricercaAssociazioniCommand.execute());
			Collections.sort(lastSearchAssociazioni, ComparatorFactory.getInstance().retrieve("Associazioni"));
			this.lista.addAll(lastSearchAssociazioni);
			((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			Clients.clearBusy();
			this.bnt_aggiorna.setVisible(false);
			this.bnt_aggiungi.setVisible(true);
			this.bnt_ricerca.setDisabled(false);
			this.bnt_esporta.setDisabled(false);
			enabledListItemEsperto();
		}
	}
	
	public void onClick$bnt_esporta(Event event) {
		try {
			//TODO: impostazione filtri
			
			List<Delibera> listDelibera = new ArrayList<Delibera>();
			AdminCommand deliberaCommand = new DeliberaCommand();
			if(this.cmb_delibere.getSelectedItem()!=null){
				Delibera delibera = (Delibera) deliberaCommand.findById(Integer.parseInt(String.valueOf(this.cmb_delibere.getSelectedItem().getValue())));
				listDelibera.add(delibera);
			}
			AdminCommand temiCommand = new TemiCommand();
			AssociazioniCommand associazioniCommand = new AssociazioniCommand();
			
		
			List<Tema> listTemi = new ArrayList<Tema>();
			if(list_temi.getSelectedItems()!=null && list_temi.getSelectedItems().size()>0){
				List<Listitem> itemsTema = new ArrayList<Listitem>(list_temi.getSelectedItems());
					for(int i=0; i<itemsTema.size(); i++){
						Tema tema = (Tema) temiCommand.findById(Integer.parseInt(String.valueOf(itemsTema.get(i).getValue())));
						listTemi.add(tema);
					}
			}
			List<Esperto> listEsperti = new ArrayList<Esperto>();
			if(list_esperti.getSelectedItems()!=null && list_esperti.getSelectedItems().size()>0){
				List<Listitem> itemsEsperti = new ArrayList<Listitem>(list_esperti.getSelectedItems());
				for(int i=0; i<itemsEsperti.size(); i++){
					for(int j=0; j<itemsEsperti.size(); j++){
						List <Listcell> listcell = itemsEsperti.get(j).getChildren();
			
						Esperto esperto = associazioniCommand.findEspertoById(Integer.parseInt(String.valueOf(listcell.get(0).getValue())));
						listEsperti.add(esperto);
					}
				}
			}
			RicercaAssociazioniCommand ricercaAssociazioniCommand = new RicercaAssociazioniCommand(listEsperti, listTemi, listDelibera);
			lista.clear();
			if(lastSearchAssociazioni!=null){
				lastSearchAssociazioni.clear();
			}
			lastSearchAssociazioni = new ArrayList<Associazioni>(ricercaAssociazioniCommand.execute());
			Collections.sort(lastSearchAssociazioni, ComparatorFactory.getInstance().retrieve("Associazioni"));
			this.lista.addAll(lastSearchAssociazioni);
			((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
			
			Date now = new Date();
			//RicercaEspertiDTO dto = helper.createReportData(listaEsperti, filtroRicerca);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
			String fileName = "ricercaEsperti_"+sdf.format(now)+".xls";
		
			byte byteArr[] = PdfUtils.generaExcelContentAssociazioniEsperti(lastSearchAssociazioni, now);
			Filedownload.save(byteArr, "application/excel", fileName);
		} catch (Exception e) {
			throw new WrongValueException("Errore durante il download della ricerca");
		}
	}
	
	@Override
	public void onMod(Event event){
		boolean esito = false;
		try {
			List<Associazioni> associazioniNew = (List<Associazioni>) event.getData();
			AssociazioniCommand associazioniCommand = new AssociazioniCommand();
			
			esito=associazioniCommand.aggiorna(associazioniNew);
			enabledListItemEsperto();
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
			this.bnt_ricerca.setDisabled(false);
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
			enabledListItemEsperto();
			this.bnt_aggiorna.setVisible(true);
			this.bnt_aggiungi.setVisible(false);
			this.bnt_ricerca.setDisabled(true);
			
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			
			itemSelezionato = (Associazioni) rigaAttiva.getValue();
			
			Iterator<Listitem> iterTemi = list_temi.getItems().listIterator();
			List <Listitem> selectListItem = new ArrayList<Listitem>();
			while(iterTemi.hasNext()){
				Listitem item = iterTemi.next();
				Iterator<Associazioni> associazionis = itemSelezionato.getDelibera().getAssociazionis().iterator();
				while(associazionis.hasNext()){
					Associazioni associazione = associazionis.next();
					if(itemSelezionato.getEsperto().getId() == associazione.getEsperto().getId() && itemSelezionato.getDelibera().getId()==associazione.getDelibera().getId()){
						if(item.getValue().equals(associazione.getTema().getId())){
							list_temi.setPage(item.getPage());
							selectListItem.add(item);
						}
					}
				}
			}	
			list_temi.setSelectedItems(new HashSet<Listitem>(selectListItem));
		
			Iterator<Listitem> iterEsperti = list_esperti.getItems().listIterator();
			while(iterEsperti.hasNext()){
				Iterator<Associazioni> associazionis = itemSelezionato.getEsperto().getAssociazionis().iterator();
				Listitem item = iterEsperti.next();
				List <Listcell> listcell = item.getChildren();
				while(associazionis.hasNext()){
					Associazioni associazione = associazionis.next();
					for(int i=0; i<listcell.size(); i++){
						if(listcell.get(i).getValue().equals(associazione.getEsperto().getId())){
							list_esperti.setPage(item.getPage());
							list_esperti.selectItem(item);
						}
						item.setDisabled(true);
					}
				}
			}	
		
			((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
			
			cmb_delibere.setDisabled(true);

		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
		
	}
	
	private void enabledListItemEsperto(){
		
		Iterator<Listitem> iterEsperti = list_esperti.getItems().listIterator();
		Iterator<Listitem> iterTemi = list_temi.getItems().listIterator();
		this.list_temi.setSelectedIndex(-1);
		this.cmb_delibere.setDisabled(false);
		this.cmb_delibere.setSelectedIndex(1);
		while(iterEsperti.hasNext()){
			Listitem item = iterEsperti.next();
			List <Listcell> listcell = item.getChildren();
				for(int i=0; i<listcell.size(); i++){
					item.setDisabled(false);
					item.setSelected(false);
				}
			}
		}
	

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		
		/**
		this.esperto = (Esperto) session.getAttribute("login");
		if(esperto.getInviato()) {
			String redirectUrlInviato = "/pages/BandiEspertiPages/homeEsperto.zul";
			Executions.sendRedirect(redirectUrlInviato);
		}
		**/
		this.initModel(false);
	}
	
	private void LoadTemi(){
		
		for (Tema tema : this.listaTemi){
			Listitem item = new Listitem();	
			item.setValue(tema.getId()); 
			item.setLabel(tema.getNome());
			this.list_temi.appendChild(item);
		}
	}
	
	private void loadEsperti(){
		
		for (Esperto esperto : this.listaEsperti){
			Listitem item = new Listitem();
			Listcell itemcell1 = new Listcell();
			Listcell itemcell2 = new Listcell();
			itemcell1.setValue(esperto.getId()); 
			itemcell1.setLabel(esperto.getNome());
			itemcell2.setValue(esperto.getId()); 
			itemcell2.setLabel(esperto.getCognome());
			item.appendChild(itemcell1);
			item.appendChild(itemcell2);
			this.list_esperti.appendChild(item);
		}
	
	}
	
	@Override
	public void loadValoriDefault(){
		AdminCommand deliberaCommand = new DeliberaCommand();
		List<Delibera> listaDelibere = new ArrayList<Delibera>(deliberaCommand.list());
		Collections.sort(listaDelibere, (DeliberaComparator)ComparatorFactory.getInstance().retrieve("Delibera"));
		this.listaDelibere.addAll(listaDelibere);
		AdminCommand temaCommand = new TemiCommand();
		List<Tema> listaTemi = new ArrayList<Tema>(temaCommand.list());
		Collections.sort(listaTemi, (TemiComparator)ComparatorFactory.getInstance().retrieve("Tema"));
		this.listaTemi.addAll(listaTemi);
		AssociazioniCommand associazioniCommand = new AssociazioniCommand();
		List<Esperto> listEsperti = new ArrayList<Esperto>(associazioniCommand.listEsperti());
		Collections.sort(listEsperti,(EspertoComparator)ComparatorFactory.getInstance().retrieve("Esperto"));
		this.listaEsperti.addAll(listEsperti);
		
		LoadTemi();
		loadEsperti();
		
	}
	
	
	@Override
	public void initModel(boolean isUpdate) throws Exception{
		try{
			lista.clear();
			AssociazioniCommand associazioniCommand = new AssociazioniCommand();
			List<Associazioni> listaAssociazioni = new ArrayList<Associazioni>(associazioniCommand.list());
			Collections.sort(listaAssociazioni, ComparatorFactory.getInstance().retrieve("Associazioni"));
			this.lista.addAll(listaAssociazioni);
			if (isUpdate){
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
			}else{
				//this.esperto = (Esperto) session.getAttribute("login");
				loadValoriDefault();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	


	/** Evento per l'aggiunta di un nuovo dato */
	@Override
	public void onClick$bnt_aggiungi(Event event) {
		
		if(this.cmb_delibere.getSelectedItem()==null || list_temi.getSelectedItems()==null || list_temi.getSelectedItems().size()==0 || list_esperti.getSelectedItems()==null || list_esperti.getSelectedItems().size()==0){
			try {
				Messagebox.show("Non sono stati selezionati tutti i valori per una corretta associazione Esperto/i, Delibera, Tema/i","Warning", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}else{
			Clients.showBusy("Validazione e Salvataggio in corso... ");
	    	Events.echoEvent("onAggiungi",event.getTarget(),null);
		}
	}
	
	@Override
	public void onAggiungi(Event event){
		
		try {
			Integer id = null;
			List<Associazioni> listAssociazioni=(List<Associazioni>)populateSelectItem();
			AdminCommand associazioniCommand = new AssociazioniCommand();
			for(Associazioni associazione: listAssociazioni){
				id=associazioniCommand.inserisci(associazione);
				if (id==null){
					throw new Exception("non è possibile eseguire l'operazione di aggiornamento dei dati");
				}
			}
			Messagebox.show("Salvataggio completato.","Info", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WrongValueException("Errore nel salvataggio dei dati: " + e.getMessage());
		}finally{
			Clients.clearBusy();
			try {
				enabledListItemEsperto();
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
			Associazioni associazioni = (Associazioni) rigaAttiva.getValue();
			Clients.showBusy("Cancellazione in corso... ");
	    	Events.echoEvent("onRimuovi",event.getTarget(),associazioni);
		
			
		}catch(Exception ex){
			throw new WrongValueException("Errore nella cancellazione dei Dati: " + ex.getMessage());
		}
	}

	@Override
	public void onRimuovi(Event event){	
		boolean esito;
		try{
			Associazioni associazioni = (Associazioni) event.getData();
			AssociazioniCommand command = new AssociazioniCommand();
			
			esito = command.delete(associazioni.getEsperto(), associazioni.getDelibera());
			
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


	public List<Associazioni> populateSelectItem() {
		
		List<Associazioni> associazionis = new ArrayList<Associazioni>();
		List<Listitem> itemsTema = new ArrayList<Listitem>(list_temi.getSelectedItems());
		List<Listitem> itemsEsperti = new ArrayList<Listitem>(list_esperti.getSelectedItems());
		AdminCommand deliberaCommand = new DeliberaCommand();
		Delibera delibera = (Delibera) deliberaCommand.findById(Integer.parseInt(String.valueOf(this.cmb_delibere.getSelectedItem().getValue())));
		AdminCommand temiCommand = new TemiCommand();
		AssociazioniCommand associazioniCommand = new AssociazioniCommand();
		EspertoCommand espertoCommand = new EspertoCommand();
		
		for(int i=0; i<itemsTema.size(); i++){
			for(int j=0; j<itemsEsperti.size(); j++){
				List <Listcell> listcell = itemsEsperti.get(j).getChildren();
				Associazioni associazioniNew = new Associazioni();
				Esperto esperto = espertoCommand.findById(Integer.parseInt(String.valueOf(listcell.get(0).getValue())));
				associazioniNew.setDelibera(delibera);
				associazioniNew.setEsperto(esperto);
				Tema tema = (Tema) temiCommand.findById(Integer.parseInt(String.valueOf(itemsTema.get(i).getValue())));
				associazioniNew.setTema(tema);
				associazionis.add(associazioniNew);
			}
		}
		return associazionis;
			
	}

	@Override
	public Serializable populateSelectItem(Serializable serializable) {
		// TODO Auto-generated method stub
		return null;
	}
	}
	
