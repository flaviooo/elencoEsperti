package it.ccse.bandiEsperti.zk.composers;


//import it.ccse.bandiEsperti.business.command.AggiornaAllegatoEspertoCommand;
import it.ccse.bandiEsperti.business.command.AggiornaListaPubblicazioniCommand;
import it.ccse.bandiEsperti.business.command.CancellaPubblicazioneCommand;
import it.ccse.bandiEsperti.business.command.CheckMaxPubblicazioniCommand;
import it.ccse.bandiEsperti.business.command.EliminaAllegatoEspertoCommand;
import it.ccse.bandiEsperti.business.command.InserisciAllegatoEspertoCommand;
import it.ccse.bandiEsperti.business.command.RicercaPubblicazioniCommand;
import it.ccse.bandiEsperti.business.command.SalvaPubblicazioniCommand;
import it.ccse.bandiEsperti.business.command.SpecializzazioniCommand;
import it.ccse.bandiEsperti.business.command.interfaces.ICommand;
import it.ccse.bandiEsperti.business.model.AllegatoPubblEsperto;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Pubblicazione;
import it.ccse.bandiEsperti.business.model.Specializzazione;
import it.ccse.bandiEsperti.enums.FileTypes;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

public class PubblicazioneComposer extends GenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Set<Pubblicazione> listaPubblicazione = new HashSet<Pubblicazione>();
	private Textbox txt_descrizione;
	private Combobox dta_data_pubblicazione;
	private Combobox cmb_specializzazioni_1;
	private Combobox cmb_specializzazioni_2;
	private Combobox cmb_specializzazioni_3;
	private Esperto user;
	private Button btn_aggiungi_doc;
	private Button btn_vedi_doc;
	private Button btn_elimina_doc;
	private Button btn_aggiungi_pubblicazione;
	private Button btn_aggiorna_pubblicazione;
	private Pubblicazione pubblicazioneSelezionata;
	private Combobox cmb_specializzazioni;
	private List<String> listaAnni = new ArrayList<String>();
	private Set<Specializzazione> listaSpecializzazioni = new LinkedHashSet<Specializzazione>();
	
	
	public List<String> getListaAnni() {
		return listaAnni;
	}

	public void setListaAnni(List<String> listaAnni) {
		this.listaAnni = listaAnni;
	}

	
	public Set<Pubblicazione> getListaPubblicazione() {
		return listaPubblicazione;
	}

	public Set<Specializzazione> getListaSpecializzazioni() {
		return listaSpecializzazioni;
	}

	public Set<Pubblicazione> getPubblicazione() {
		return listaPubblicazione;
	}
	
	public Pubblicazione getPubblicazioneSelezionata() {
		return pubblicazioneSelezionata;
	}

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.user = (Esperto) session.getAttribute("login");

		if(user.getInviato()) {
			String redirectUrlInviato = "/pages/BandiEspertiPages/homeEsperto.zul";
			Executions.sendRedirect(redirectUrlInviato);
		}
	
		btn_aggiungi_doc.setVisible(user.getAllegato() == null);
		btn_vedi_doc.setVisible(user.getAllegato() != null);
		btn_elimina_doc.setVisible(user.getAllegato() != null);
	
		this.initModel(false);		
		initSpecializzazioni();
	}
	
	private void setDefaultFields(){
		
		this.btn_aggiungi_pubblicazione.setVisible(true);
		this.btn_aggiorna_pubblicazione.setVisible(false);
		pubblicazioneSelezionata=null;
	}

	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		try{
			ICommand command = new RicercaPubblicazioniCommand(this.user.getId());
			command.execute();
			List<Pubblicazione> lista = new ArrayList<Pubblicazione>(command.getSet());
			Collections.sort(lista, ComparatorFactory.getInstance().retrieve("Pubblicazione"));
			this.listaPubblicazione.clear();
			this.listaPubblicazione.addAll(lista);			
			
			if (isUpdate){
				 setDefaultFields();
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
			}else{
				
				for(int i=1996; i<=2020; i++){
					listaAnni.add(String.valueOf(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	/** Evento per il salvataggio dei dati modificati */
	//	public void onClick$refreshBtn(Event e) {
	//		this.getUpdatedData();
	//	}

	/** Evento per l'aggiornamento dei dati modificati */
	
	
	/**
	public void onClick$btn_aggiorna_pubblicazione(Event event) {
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onAggiornaPubblicazione",event.getTarget(),null);
	}

	public void onAggiornaPubblicazione(Event event){			
		try{
			this.aggiornaPubblicazione();
		}catch(Exception ex){
			throw new WrongValueException("Errore nel salvataggio dei dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
			txt_descrizione.setValue(" ");
			dta_data_pubblicazione.setValue(null);
		}

	}
	**/
	
	private void check() throws WrongValueException{
		
		if(this.txt_descrizione.getValue()== null){
			throw new WrongValueException("Pubblicazione obbligatoria" );
		}
		
		if(this.dta_data_pubblicazione.getSelectedItem()== null){
			throw new WrongValueException("Anno di pubblicazione obbligatoria" );
		}
		
		if(this.dta_data_pubblicazione.getSelectedItem()!= null){
			String dataPublicazione = (String)this.dta_data_pubblicazione.getSelectedItem().getValue();
			
			Esperto user = (Esperto) session.getAttribute("login");
			
			String dataNascita = user.getDataNascita().toString();
			//Controllo dataInizio
			if(dataPublicazione!=null && dataNascita!= null){
				if(dataPublicazione.compareTo(dataNascita)< 0){
					throw new WrongValueException("Anno di Pubblicazione non può essere precedente alla data di nascita" );
				}
			}
		}
			
			
		if(this.cmb_specializzazioni_1.getSelectedItem()== null){
			throw new WrongValueException("Keyword 1 obbligatoria" );
		}
		
		if(this.cmb_specializzazioni_1.getSelectedItem()!= null &&  this.cmb_specializzazioni_2.getSelectedItem()!= null){
			if(this.cmb_specializzazioni_1.getSelectedItem().getValue().equals(this.cmb_specializzazioni_2.getSelectedItem().getValue())){
				throw new WrongValueException("Keyword 2 non può essere uguale a Keyword 1" );
			}
			
		}
		
		if(this.cmb_specializzazioni_1.getSelectedItem()!= null && this.cmb_specializzazioni_2.getSelectedItem()!= null &&  this.cmb_specializzazioni_3.getSelectedItem()!= null){
			if(this.cmb_specializzazioni_1.getSelectedItem().getValue().equals(this.cmb_specializzazioni_2.getSelectedItem().getValue())){
				throw new WrongValueException("Keyword 2 non può essere uguale a Keyword 1" );
			}
			
			if(this.cmb_specializzazioni_2.getSelectedItem().getValue().equals(this.cmb_specializzazioni_3.getSelectedItem().getValue())){
				throw new WrongValueException("Keyword 3 non può essere uguale a Keyword 2" );
			}
			
			if(this.cmb_specializzazioni_1.getSelectedItem().getValue().equals(this.cmb_specializzazioni_3.getSelectedItem().getValue())){
				throw new WrongValueException("Keyword 3 non può essere uguale a Keyword 1" );
			}
		}
	}
	


	/** Evento per l'inserimento di un nuovo dato */
	public void onClick$btn_aggiungi_pubblicazione(Event event) {
		if (this.listaPubblicazione==null){
			throw new WrongValueException("Non sono presenti pubblicazioni da aggiornare");
		}
		check();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onAggiungiPubblicazione",event.getTarget(),null);
	}
	

	public void onAggiungiPubblicazione(Event event){
		Pubblicazione pubblicazione = new Pubblicazione();
		boolean saved = false;
		try{

			pubblicazione=populateSelectItem(pubblicazione);
			
			// verifico prima che non ci siano già 10 pubblicazioni
			if(checkMaxPubblicazioni())
			{
				Messagebox.show("Impossibile aggiungere un'ulteriore pubblicazione. Limite di 10 pubblicazioni raggiunto!");
				Clients.clearBusy();
				return;
			}
			
		
			this.listaPubblicazione.add(pubblicazione);
			this.aggiungiPubblicazione();
			saved = true;
		}catch(Exception ex){
			if (this.listaPubblicazione!=null && pubblicazione!=null){
				this.listaPubblicazione.remove(pubblicazione);
			}
			throw new WrongValueException("Errore nel salvataggio dei dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
			try {
				if(saved)
				{
					Messagebox.show("Salvataggio effettuato con successo.","Info", Messagebox.OK, Messagebox.INFORMATION);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			txt_descrizione.setValue(" ");
			dta_data_pubblicazione.setValue(null);
		}
	}	


	/**
	 * Metodo che provvede all'inserimento di una nuova istruzione 
	 */
	private void aggiungiPubblicazione() throws Exception{
		boolean esito = false;
		try{
		
			ICommand command = new SalvaPubblicazioniCommand(this.user.getId(), this.listaPubblicazione);
			esito = command.execute();
			if (!esito){
				throw new Exception("non è possibile eseguire l'operazione di inserimento dei nuovi dati");
			}
			
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}finally{
				Clients.clearBusy();
				try {
					this.initModel(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * Metodo che verifica se si è raggiunto il massimo numero di pubblicazioni
	 */
	private boolean checkMaxPubblicazioni() throws Exception{
		boolean res = true;
		try{
		
			CheckMaxPubblicazioniCommand command = new CheckMaxPubblicazioniCommand(this.user);
			if(command.execute())
			{
				res = command.isOverMax();
			}
			else
			{
				res = true;
			}
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}finally{
				Clients.clearBusy();
				try {
					this.initModel(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return res;
	}


	//primo evento generato al clic su un pulsante di rimozione pubblicazione
	public void onVerificaRimuovi(ForwardEvent event){
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();

			final Pubblicazione selezionato = (Pubblicazione) rigaAttiva.getValue();
			String descr = selezionato.getDescrizione();

			Messagebox.show("Procedere con l'eliminazione di '"+descr+"' ?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
					new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event event) throws InterruptedException {
					if (event.getName().equals("onOK")) {	                	
						Clients.showBusy("validazione in corso... ");
						Events.echoEvent("onRimuoviPubblicazione",Path.getComponent("/win_pubblicazione"),selezionato);
					} else {

					}
				}
			});
		}catch(WrongValueException ex){
			throw ex;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	public void onRimuoviPubblicazione(Event event){	
		boolean esito = false;
		try{
			Pubblicazione selezionato = (Pubblicazione) event.getData();

			ICommand command = new CancellaPubblicazioneCommand(selezionato.getId());
			esito = command.execute();
			if (!esito){
				throw new Exception("non è possibile eseguire l'operazione di rimozione del record selezionato");
			}else{
				Messagebox.show("Rimozione completata.","Info", Messagebox.OK, Messagebox.INFORMATION);
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Clients.clearBusy();
		
			try {
				this.initModel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void onUpload$btn_aggiungi_doc(UploadEvent event) throws Exception {
		Media media = event.getMedia();
		String estensione = getFileExtension(media.getName());
		
		boolean flagFormat = FileTypes.searchTypeByFormat(estensione);

		if (flagFormat)
		{
			byte [] arrData = getBytesFromStream(media.getStreamData());

			AllegatoPubblEsperto allegato = new AllegatoPubblEsperto();
			allegato.setAllegato(arrData);
			allegato.setEstensione(estensione);
			allegato.setContentType(media.getContentType());
			InserisciAllegatoEspertoCommand cmd = new InserisciAllegatoEspertoCommand(user, allegato);
			cmd.execute();
			Messagebox.show("L'Allegato è stato caricato con successo.", "Inserimento Allegato",
					Messagebox.OK, Messagebox.INFORMATION);
			btn_aggiungi_doc.setVisible(false);
			btn_vedi_doc.setVisible(true);
			btn_elimina_doc.setVisible(true);

		}
		else
		{
			Messagebox.show("Tipo file non valido. Caricare un file con estensione PDF.", "Info", Messagebox.OK, Messagebox.EXCLAMATION);
		}

	}
	
	public void onClick$btn_elimina_doc(Event event) throws Exception
	{
		EliminaAllegatoEspertoCommand cmd = new EliminaAllegatoEspertoCommand(user);
		cmd.execute();
		Messagebox.show("L'Allegato è stato rimosso con successo.", "Eliminazione Allegato",
				Messagebox.OK, Messagebox.INFORMATION);
		btn_elimina_doc.setVisible(false);
		btn_vedi_doc.setVisible(false);
		btn_aggiungi_doc.setVisible(true);
	}

	private byte[] getBytesFromStream(InputStream is)
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int abyte = is.read();
			while (abyte != -1)
			{
				baos.write(abyte);
				abyte = is.read();
			}
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	private String getFileExtension(String fileName)
	{
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos+1);
	}

	public void onClick$btn_vedi_doc(Event event) throws Exception
	{
		AllegatoPubblEsperto allegato = user.getAllegato();
		if (allegato != null)
		{
			String fileName="Pubblicazione_"+user.getUsername()+"."+allegato.getEstensione();
			Filedownload.save(allegato.getAllegato(), allegato.getContentType(), fileName);
		}
		else
			Messagebox.show("Non è stato inserito nessun file come allegato.", "CCSE Bandi Esperti", Messagebox.OK, Messagebox.INFORMATION);
	}
	
	
/** Evento per la modifica del dato */
	
	
	public void onModificaPubblicazione(ForwardEvent event){	
		
		try{
			this.btn_aggiungi_pubblicazione.setVisible(false);
			this.btn_aggiorna_pubblicazione.setVisible(true);
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			pubblicazioneSelezionata = (Pubblicazione) rigaAttiva.getValue();
			((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();

		}catch(Exception ex){
			ex.printStackTrace();
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
			
		}finally{
			Clients.clearBusy();
		}
		
	}
	

	
	private void initSpecializzazioni(){
		this.cmb_specializzazioni_1.getChildren().clear();
		this.cmb_specializzazioni_2.getChildren().clear();
		this.cmb_specializzazioni_3.getChildren().clear();
		SpecializzazioniCommand specializzazioniCommand = new SpecializzazioniCommand();
		List<Specializzazione> lista = new ArrayList<Specializzazione>(specializzazioniCommand.list());
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("Specializzazione"));
		listaSpecializzazioni.addAll(lista);
		for (Specializzazione specializzazione : lista){
			Comboitem itemSpecializzazione = new Comboitem();
			itemSpecializzazione.setValue(specializzazione.getId()); 
			itemSpecializzazione.setLabel(specializzazione.getNome());
			this.cmb_specializzazioni_1.appendChild(itemSpecializzazione);
		}
		for (Specializzazione specializzazione : lista){
			Comboitem itemSpecializzazione = new Comboitem();
			itemSpecializzazione.setValue(specializzazione.getId()); 
			itemSpecializzazione.setLabel(specializzazione.getNome());
			this.cmb_specializzazioni_2.appendChild(itemSpecializzazione);
		}
		for (Specializzazione specializzazione : lista){
			Comboitem itemSpecializzazione = new Comboitem();
			itemSpecializzazione.setValue(specializzazione.getId()); 
			itemSpecializzazione.setLabel(specializzazione.getNome());
			this.cmb_specializzazioni_3.appendChild(itemSpecializzazione);
		}
	}
	
	private Pubblicazione populateSelectItem(Pubblicazione pubblicazione){
		
		if(this.dta_data_pubblicazione.getSelectedItem()!=null){
			pubblicazione.setDataString((String)this.dta_data_pubblicazione.getSelectedItem().getValue());
		}
		pubblicazione.setDescrizione(txt_descrizione.getValue());
		
		if( cmb_specializzazioni_1.getSelectedItem()!=null){
			SpecializzazioniCommand specializzazioniCommand = new SpecializzazioniCommand();
			Integer idSpecializzazione=Integer.valueOf(String.valueOf(cmb_specializzazioni_1.getSelectedItem().getValue()));
			Specializzazione specializzazione = specializzazioniCommand.findById(idSpecializzazione);
			pubblicazione.setSpecializzazione1(specializzazione);
	
		}
		if(cmb_specializzazioni_2.getSelectedItem()!=null){
			SpecializzazioniCommand specializzazioniCommand = new SpecializzazioniCommand();
			Integer idSpecializzazione=Integer.valueOf(String.valueOf(cmb_specializzazioni_2.getSelectedItem().getValue()));
			Specializzazione specializzazione = specializzazioniCommand.findById(idSpecializzazione);
			pubblicazione.setSpecializzazione2(specializzazione);
	
		}
		if(cmb_specializzazioni_3.getSelectedItem()!=null){
			SpecializzazioniCommand specializzazioniCommand = new SpecializzazioniCommand();
			Integer idSpecializzazione=Integer.valueOf(String.valueOf(cmb_specializzazioni_3.getSelectedItem().getValue()));
			Specializzazione specializzazione = specializzazioniCommand.findById(idSpecializzazione);
			pubblicazione.setSpecializzazione3(specializzazione);
	
		}
		
		
			return pubblicazione;
			
	}
	
	/** Evento per la modifica di un nuovo dato */
	public void onClick$btn_aggiorna_pubblicazione(Event event) {
		if (this.pubblicazioneSelezionata==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		check();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
    	Events.echoEvent("onModPubblicazione",event.getTarget(),null);
	}
	
	public void onModPubblicazione(Event event){
		boolean esito = false;
		try {
			pubblicazioneSelezionata = populateSelectItem(this.pubblicazioneSelezionata);
			List<Pubblicazione> list = new ArrayList<Pubblicazione>(this.listaPubblicazione);
			ICommand command = new AggiornaListaPubblicazioniCommand(list);
			esito = command.execute();
		
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
		
			try {
				this.initModel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	

	//	/** Evento per l'inserimento di un nuovo dato */
	//	public void onClick$bnt_aggiungi_pubblicazione(Event e) {
	//		Esperto user = (Esperto) session.getAttribute("login");
	//		Pubblicazione i = new Pubblicazione();
	//		i.setDescrizione(txt_descrizione.getValue());
	//		i.setData(dta_data_pubblicazione.getValue());
	//		pubblicazione.add(i);
	//		SalvaPubblicazioniCommand spc = new SalvaPubblicazioniCommand(user.getId(), pubblicazione);
	//		spc.execute();
	//		inboxGrid.renderAll();
	//	}
	//	
	//	/**
	//	 * Metodo che provvede al salvataggio della lista delle istruzioni
	//	 * modificate
	//	 */
	//	private List<String[]> getUpdatedData() {
	//
	//		// ***********************************************************
	//		// Test Salvataggio del command
	//		Esperto user = (Esperto) session.getAttribute("login");
	//		if (user != null) {
	//			// FORZA IL LOGOUT E LA RIDIREZIONE ALLA PAGINA DI ERRORE
	//		}
	//		ICommand command = new SalvaPubblicazioniCommand(user.getId(), fromArrayListToSet(list));
	//		command.execute();
	//
	//		inboxGrid.getFellows();
	//		return list;
	//	}
	//
	//	private Set<Pubblicazione> buildSetPubblicazione() {
	//		
	//		Set<Pubblicazione> set = new HashSet<Pubblicazione>();
	//		
	//		return set;
	//	}

	//	private ArrayList<String[]> fromSetToArrayList(Set<Pubblicazione> set) {
	//		ArrayList<String[]> list = new ArrayList<String[]>();
	//		Iterator<Pubblicazione> it = set.iterator();
	//		while (it.hasNext()) {
	//			// Get element
	//			Pubblicazione element = (Pubblicazione) it.next();
	//			System.out.println(element.getData().toString());
	//
	//			list.add(new String[] { element.getDescrizione(), element.getData().toString() });
	//		}
	//		return list;
	//	}
	//
	//	@SuppressWarnings("rawtypes")
	//	private Set<Pubblicazione> fromArrayListToSet(ArrayList<String[]> list) {
	//		Set<Pubblicazione> set = new HashSet<Pubblicazione>();
	//
	//		Iterator it = list.iterator();
	//		while (it.hasNext()) {
	//			String[] strArray = (String[]) it.next();
	//			Pubblicazione element = new Pubblicazione();
	//
	//			// Valorizzazione dell'elemento specifico che si sta salvando
	//			element.setDescrizione(strArray[0]);
	//
	//			// ********************************************************************
	//			// * DATA
	//			Date date = null;
	//			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
	//			try {
	//				date = dateformat.parse(strArray[1]);
	//			} catch (Exception e) {
	//				System.out.println(e.getMessage());
	//			}
	//			element.setData(new Date());
	//			// ********************************************************************			
	//
	//			set.add(element);
	//		}
	//		return set;
	//	}

}