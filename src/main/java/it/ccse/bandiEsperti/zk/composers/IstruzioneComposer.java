package it.ccse.bandiEsperti.zk.composers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
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
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import it.ccse.bandiEsperti.business.command.AdminCommand;
import it.ccse.bandiEsperti.business.command.AggiornaListaIstruzioneCommand;
import it.ccse.bandiEsperti.business.command.AteneiCommand;
import it.ccse.bandiEsperti.business.command.CancellaIstruzioneCommand;
import it.ccse.bandiEsperti.business.command.CittaCommand;
import it.ccse.bandiEsperti.business.command.RicercaIstruzioneCommand;
import it.ccse.bandiEsperti.business.command.SalvaIstruzioneCommand;
import it.ccse.bandiEsperti.business.command.TipoLaureaCommand;
import it.ccse.bandiEsperti.business.command.TitoloLaureaCommand;
import it.ccse.bandiEsperti.business.command.interfaces.ICommand;
import it.ccse.bandiEsperti.business.model.Atenei;
import it.ccse.bandiEsperti.business.model.Citta;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Istruzione;
import it.ccse.bandiEsperti.business.model.TipoLaurea;
import it.ccse.bandiEsperti.business.model.TitoloLaurea;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;
import it.ccse.bandiEsperti.zk.comparators.IstruzioneComparator;

public class IstruzioneComposer extends GenericForwardComposer {
	private static final long serialVersionUID = -3011711889760264355L;

	private Textbox txt_titoloStudio;
	private Combobox dta_data_conseguimento;
	
	private Checkbox chk_estero;
	private TreeSet<Istruzione> listaIstruzione;
	private Esperto user;
	
	private Set<TipoLaurea> listaTipoLaurea = new LinkedHashSet<TipoLaurea>();
	private Set<TitoloLaurea> listaTitoloLaurea = new LinkedHashSet<TitoloLaurea>();
	private Set<Citta> listaCitta = new LinkedHashSet<Citta>();
	private Set<Atenei> listaAtenei = new LinkedHashSet<Atenei>();
	
	private Combobox cmb_tipo;
	private Combobox cmb_titolo;
	private Combobox cmb_ateneo;
	private Combobox cmb_citta;
	
	private Label lbl_citta;
	private Label lbl_citta_mand;
	
	private Label lbl_ateneo;
	private Label lbl_ateneo_mand;
	
	private Label lbl_titoloStudio;
	private Label lbl_titoloStudio_mand;
	
	private Label lbl_desc;
	private Label lbl_desc_mand;
	private Label lbl_ind_desc;
	private Label lbl_ind_desc2;
	
	private Button bnt_aggiungi_istruzione;
	private Button bnt_aggiorna_istruzione;
	private Istruzione istruzioneSelezionata;
	private List<String> listaAnni = new ArrayList<String>();
	
	
	
	public List<String> getListaAnni() {
		return listaAnni;
	}

	public void setListaAnni(List<String> listaAnni) {
		this.listaAnni = listaAnni;
	}

	public Set<Istruzione> getIstruzione() {
		return this.listaIstruzione;
	}
	
	private void selectTitoli(Integer id){
		//this.user = (Esperto) session.getAttribute("login");
		AdminCommand  tipoLaureaCommand = new TipoLaureaCommand();
		TipoLaurea tipoLaurea = (TipoLaurea) tipoLaureaCommand.findById(id);
		List<TitoloLaurea> lista = new ArrayList<TitoloLaurea>(tipoLaurea.getTitoloLaureas());
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
		listaTitoloLaurea.addAll(lista);
		for (TitoloLaurea titoloLaurea : listaTitoloLaurea){
			Comboitem itemTitoloLaurea = new Comboitem();
			itemTitoloLaurea.setValue(titoloLaurea.getId()); 
			itemTitoloLaurea.setLabel(titoloLaurea.getDenominazione());
			this.cmb_titolo.appendChild(itemTitoloLaurea);
		}
	}
	
	private void selectCitta(){
	
			cmb_citta.setDisabled(false);
			AteneiCommand ateneiCommand = new AteneiCommand();
			HashMap<Integer, Citta> hashListaCitta = new HashMap<Integer, Citta>();
			List<Atenei> listaA = new ArrayList<Atenei>(ateneiCommand.list());
			Collections.sort(listaA, ComparatorFactory.getInstance().retrieve("IRicerca"));
			
			for (Atenei ateneo : listaA){
				Citta citta = ateneo.getCitta();
				hashListaCitta.put(citta.getId(), citta);
			}
			List<Citta> listaC= new ArrayList<Citta>(hashListaCitta.values());
			Collections.sort(listaC, ComparatorFactory.getInstance().retrieve("IRicerca"));
			listaCitta.addAll(listaC);
			for(Citta citta:listaC){
				Comboitem itemCitta = new Comboitem();
				itemCitta.setValue(citta.getId()); 
				itemCitta.setLabel(citta.getDenominazione());
				this.cmb_citta.appendChild(itemCitta);
			}
	}
	
	
	private void selectAtenei(Integer id){
		cmb_ateneo.setDisabled(false);
		this.user = (Esperto) session.getAttribute("login");
		AdminCommand  cittaCommand = new CittaCommand();
		Citta citta = (Citta) cittaCommand.findById(id);
		
		List<Atenei> lista = new ArrayList<Atenei>(citta.getAteneis());
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
		
		listaAtenei.addAll(lista);
		for (Atenei atenei : lista){
			Comboitem itemAtenei = new Comboitem();
			itemAtenei.setValue(atenei.getId()); 
			itemAtenei.setLabel(atenei.getDenominazione());
			this.cmb_ateneo.appendChild(itemAtenei);
		}
	}
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.user = (Esperto) session.getAttribute("login");
    	if(user.getInviato()) {
    		String redirectUrlInviato = "/pages/BandiEspertiPages/homeEsperto.zul";
    		Executions.sendRedirect(redirectUrlInviato);
    	}
		this.initModel(false);
	}
	
	private void setVisibleFields(String tipoLaurea){
		
		if(tipoLaurea.equals("Altro")){
			this.cmb_titolo.setVisible(false);
			this.lbl_titoloStudio.setVisible(false);
			this.lbl_titoloStudio_mand.setVisible(false);
			this.txt_titoloStudio.setVisible(true);
			this.lbl_desc.setVisible(true);
			this.lbl_desc_mand.setVisible(true);
			this.lbl_ind_desc.setVisible(false);
			this.lbl_ind_desc2.setVisible(true);
			this.cmb_citta.setVisible(true);
			this.cmb_ateneo.setVisible(true);
			this.lbl_citta.setVisible(true);
			this.lbl_citta_mand.setVisible(true);
			this.lbl_ateneo.setVisible(true);
			this.lbl_ateneo_mand.setVisible(true);
		}else if(tipoLaurea.equals("Dottorato di Ricerca")){
			this.cmb_titolo.setVisible(false);
			this.lbl_titoloStudio.setVisible(false);
			this.lbl_titoloStudio_mand.setVisible(false);
			this.txt_titoloStudio.setVisible(true);
			this.lbl_desc.setVisible(true);
			this.lbl_desc_mand.setVisible(true);
			this.lbl_ind_desc.setVisible(false);
			this.lbl_ind_desc2.setVisible(true);
			this.cmb_citta.setVisible(true);
			this.cmb_ateneo.setVisible(true);
			this.lbl_citta.setVisible(true);
			this.lbl_citta_mand.setVisible(true);
			this.lbl_ateneo.setVisible(true);
			this.lbl_ateneo_mand.setVisible(true);
		}else{
			this.cmb_titolo.setVisible(true);
			this.lbl_titoloStudio.setVisible(true);
			this.lbl_titoloStudio_mand.setVisible(true);
			this.cmb_citta.setVisible(true);
			this.lbl_citta.setVisible(true);
			this.lbl_citta_mand.setVisible(true);
			this.cmb_ateneo.setVisible(true);
			this.lbl_ateneo.setVisible(true);
			this.lbl_ateneo_mand.setVisible(true);
			this.lbl_desc.setVisible(false);
			this.lbl_ind_desc.setVisible(false);	
			this.lbl_ind_desc2.setVisible(false);	
			this.lbl_desc_mand.setVisible(false);
			this.txt_titoloStudio.setVisible(false);
		}
	}
	
	public void onSelect$cmb_tipo(SelectEvent evt) throws Exception {
		Comboitem item = new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		setVisibleFields(item.getLabel().toString());
		clearListaTitoloLaurea();
		clearListaCitta();
		clearListaAtenei();
		selectTitoli(Integer.valueOf(String.valueOf(item.getValue())));
		if(this.cmb_titolo.getItems()==null || this.cmb_titolo.getItems().size()==0){
			selectCitta();
		}
	}	
	
	public void onClick$chk_estero(Event evt) throws Exception {
		if(chk_estero.isChecked()){
			clearListaCitta();
			clearListaAtenei();
			cmb_citta.setVisible(false);
			cmb_ateneo.setVisible(false);
			lbl_citta.setVisible(false);
			lbl_citta_mand.setVisible(false);
			lbl_ateneo.setVisible(false);
			lbl_ateneo_mand.setVisible(false);
			lbl_desc.setVisible(true);
			lbl_desc_mand.setVisible(true);
			txt_titoloStudio.setVisible(true);
			lbl_ind_desc.setVisible(true);
			lbl_ind_desc2.setVisible(false);
		}else{
			cmb_citta.setVisible(true);
			cmb_ateneo.setVisible(true);
			lbl_citta.setVisible(true);
			lbl_citta_mand.setVisible(true);
			lbl_ateneo.setVisible(true);
			lbl_ateneo_mand.setVisible(true);
			lbl_desc.setVisible(false);
			lbl_desc_mand.setVisible(false);
			txt_titoloStudio.setVisible(false);
			lbl_ind_desc.setVisible(false);
			lbl_ind_desc2.setVisible(false);
			selectCitta();
		}
	}
	
	public void onSelect$cmb_titolo(SelectEvent evt) throws Exception {
		clearListaCitta();
		clearListaAtenei();
		selectCitta();
	}	
	
	public void onSelect$cmb_citta(SelectEvent evt) throws Exception {
		Comboitem item = new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		clearListaAtenei();
		selectAtenei(Integer.valueOf(String.valueOf(item.getValue())));
	}	
	
	private void clearListaTipoLaurea(){
		listaTipoLaurea.clear();
		this.cmb_tipo.setText("");
		this.cmb_tipo.setSelectedIndex(-1);
		this.cmb_tipo.getChildren().clear();
	}
	private void clearListaTitoloLaurea(){
		listaTitoloLaurea.clear();
		this.cmb_titolo.setText("");
		this.cmb_titolo.setSelectedIndex(-1);
		this.cmb_titolo.getChildren().clear();
	}
	
	private void clearListaCitta(){
		listaCitta.clear();
		this.cmb_citta.setText("");
		this.cmb_citta.setSelectedIndex(-1);
		this.cmb_citta.getChildren().clear();
	}
	
	private void clearListaAtenei(){
		listaAtenei.clear();
		this.cmb_ateneo.setText("");
		this.cmb_ateneo.setSelectedIndex(-1);
		this.cmb_ateneo.getChildren().clear();
	}
	
	private void loadListaTipoLaurea(){
		AdminCommand tipoLaureaCommand = new TipoLaureaCommand();
		List<TipoLaurea> lista = new ArrayList<TipoLaurea>(tipoLaureaCommand.list());
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("TipoLaurea"));
		listaTipoLaurea.addAll(lista);
	}
	
	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		istruzioneSelezionata = null;
		try{
			
			if(isUpdate){
				clearListaTipoLaurea();
				clearListaTitoloLaurea();
				clearListaCitta();
				clearListaAtenei();
				loadListaTipoLaurea();
				this.txt_titoloStudio.setValue("");
				
				cmb_citta.setVisible(true);
				cmb_ateneo.setVisible(true);
				lbl_citta.setVisible(true);
				lbl_citta_mand.setVisible(true);
				lbl_ateneo.setVisible(true);
				lbl_ateneo_mand.setVisible(true);
				lbl_desc.setVisible(false);
				lbl_desc_mand.setVisible(false);
				txt_titoloStudio.setVisible(false);
				lbl_ind_desc.setVisible(false);
				lbl_ind_desc2.setVisible(false);
				
			}
			else{
				loadListaTipoLaurea();
				int annoDiNascita = 1940; 
				if(this.user.getDataNascita()!=null) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
					Date dataNascitaUser = (Date) this.user.getDataNascita();
					annoDiNascita = Integer.parseInt(formatter.format(dataNascitaUser));
				}
						for(int i=annoDiNascita; i<=2020; i++)
							listaAnni.add(String.valueOf(i));
				
				
				lbl_desc.setVisible(false);
				lbl_desc_mand.setVisible(false);
				txt_titoloStudio.setVisible(false);
				lbl_ind_desc.setVisible(false);
				lbl_ind_desc2.setVisible(false);
			}
				
			
			ICommand command = new RicercaIstruzioneCommand(this.user.getId());
			command.execute();
			
			this.listaIstruzione = new TreeSet<Istruzione>(new IstruzioneComparator());
			Set<Istruzione> lista = command.getSet();
//			Collections.sort(lista, ComparatorFactory.getInstance().retrieve("Istruzione"));
//			Collections.sort(lista,Collections.reverseOrder());
			this.listaIstruzione.addAll(lista);
			
			if (isUpdate){
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Evento per l'aggiornamento dei dati modificati */
	public void onClick$btn_aggiorna_istruzione(Event event) {
		check();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onAggiornaIstruzione",event.getTarget(),null);
	}
	
	public void onAggiornaIstruzione(Event event){			
		try{
			this.aggiornaIstruzione();
		}catch(Exception ex){
			throw new WrongValueException("Errore nel salvataggio dei dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
		
	}
	
	/**
	 * Metodo che provvede all'inserimento di una nuova istruzione 
	 */
	private void aggiornaIstruzione() throws Exception{
		boolean esito = false;
		List<Istruzione> list = new ArrayList<Istruzione>(this.listaIstruzione);
		ICommand command = new AggiornaListaIstruzioneCommand(list);
		esito = command.execute();
		if (!esito){
			throw new Exception("non è possibile eseguire l'operazione di aggiornamento dei dati");
		}
		
		this.initModel(true);
	}
	
	private void check() throws WrongValueException{ 
        
        //Controllo Dati obbligatori
		
		if(this.dta_data_conseguimento.getSelectedItem()!= null){
			String dataConseguimento = (String)this.dta_data_conseguimento.getSelectedItem().getValue();
			
			Esperto user = (Esperto) session.getAttribute("login");
			
			String dataNascita = user.getDataNascita().toString();
			//Controllo dataInizio
			if(dataConseguimento!=null && dataNascita!= null){
				if(dataConseguimento.compareTo(dataNascita)< 0){
					throw new WrongValueException("Anno di conseguimento non può essere precedente alla data di nascita" );
				}
			}
		}
			
		if(cmb_tipo.getSelectedItem()==null){
        	throw new WrongValueException("Tipo obbligatorio");
		}
		
		if(cmb_tipo.getSelectedItem()!=null && cmb_tipo.getValue()!=null && !cmb_tipo.getValue().equals("Altro") && !cmb_tipo.getValue().equals("Dottorato di Ricerca") && cmb_titolo.getSelectedItem()==null){
        	throw new WrongValueException("Titolo di studio obbligatorio");
		}
		
		
		if(!chk_estero.isChecked()){
			if(cmb_citta.getSelectedItem()==null){
	        	throw new WrongValueException("Città obbligatoria");
			}
			
			if(cmb_ateneo.getSelectedItem()==null){
	        	throw new WrongValueException("Ateneo obbligatorio");
			}
		}else{
			if(txt_titoloStudio.getValue().isEmpty()){
	        	throw new WrongValueException("Descrizione obbligatoria");
			}
		}
			
		
		if(dta_data_conseguimento.getSelectedItem()==null){
        	throw new WrongValueException("Anno di conseguimento obbligatorio");
		}
		
		if(cmb_tipo.getSelectedItem()!=null && cmb_tipo.getValue()!=null && (cmb_tipo.getValue().equals("Altro") || cmb_tipo.getValue().equals("Dottorato di Ricerca")) && txt_titoloStudio.getValue().isEmpty()){
	        throw new WrongValueException("Descrizione obbligatoria");
		}
	}
	
	/** Evento per l'inserimento di un nuovo dato */
	public void onClick$bnt_aggiungi_istruzione(Event event) {
		//controlli
		check();
		if (this.listaIstruzione==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onAggiungiIstruzione",event.getTarget(),null);
		
	}
	
	/** Evento per la modifica del dato */
	public void onClick$bnt_modifica_istruzione(Event event) {
		if (this.istruzioneSelezionata==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onModificaIstruzione",event.getTarget(),null);
		
	}
	
	/** Evento per l'inserimento di un nuovo dato */
	public void onClick$bnt_aggiorna_istruzione(Event event) {
		if (this.istruzioneSelezionata==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		check();
		
		this.bnt_aggiorna_istruzione.setVisible(false);
		this.bnt_aggiungi_istruzione.setVisible(true);
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onModIstruzione",event.getTarget(),null);
	 }
	
	private Istruzione populateSelectItem(Istruzione istruzione){
		TitoloLaurea titoloLaurea = null;
		Citta citta  = null;
		Atenei atenei = null;
		if(this.dta_data_conseguimento.getSelectedItem()!=null){
			istruzione.setDataString((String)this.dta_data_conseguimento.getValue());
		}
		
		TipoLaureaCommand  tipoLaureaCommand = new TipoLaureaCommand();
		TitoloLaureaCommand titoloLaureaCommand = new TitoloLaureaCommand();
		AdminCommand cittaCommand = new CittaCommand();
		AdminCommand ateneiCommand = new AteneiCommand();
		
		TipoLaurea tipoLaurea = tipoLaureaCommand.findById((Integer)(this.cmb_tipo.getSelectedItem().getValue()));
		
		if(this.cmb_titolo.getSelectedItem()!=null && this.cmb_titolo.getSelectedItem().getValue()!=null){
			titoloLaurea = titoloLaureaCommand.findById((Integer)(this.cmb_titolo.getSelectedItem().getValue()));
			istruzione.setTitoloLaurea(titoloLaurea);

		}
		if(this.dta_data_conseguimento.getSelectedItem()!=null){
			istruzione.setDataString((String)this.dta_data_conseguimento.getSelectedItem().getValue());
		}
		if(this.cmb_citta.isDisabled()){
			istruzione.setCitta(null);
		}else if(this.cmb_citta.getSelectedItem()!=null && this.cmb_citta.getSelectedItem().getValue()!=null){
			citta = (Citta)cittaCommand.findById((Integer)(this.cmb_citta.getSelectedItem().getValue()));
			istruzione.setCitta(citta);
		}if(this.cmb_ateneo.isDisabled()){
			istruzione.setAtenei(null);
		}else if(this.cmb_ateneo.getSelectedItem()!=null && this.cmb_ateneo.getSelectedItem().getValue()!=null && !this.cmb_ateneo.isDisabled()){
			atenei = (Atenei)ateneiCommand.findById((Integer)(this.cmb_ateneo.getSelectedItem().getValue()));
			istruzione.setAtenei(atenei);
		}
		istruzione.setEstero(this.chk_estero.isChecked());
		istruzione.setTipoLaurea(tipoLaurea);
		istruzione.setTitoloStudi(this.txt_titoloStudio.getValue()!=null?this.txt_titoloStudio.getValue():null);
		
		return istruzione;
	}
	
	public void onModIstruzione(Event event){
		boolean esito = false;
		try {
			populateSelectItem(this.istruzioneSelezionata);
			ICommand command = new SalvaIstruzioneCommand(this.user.getId(), this.listaIstruzione);
			esito = command.execute();
			if (!esito){
				throw new Exception("non è possibile eseguire l'operazione di inserimento dei nuovi dati");
			}else{
				Messagebox.show("Salvataggio effettuato.","Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Clients.clearBusy();
			//Clients.alert("Salvataggio preso in consegna dal sistema. Al più presto verrà aggiornato.");
			try {
				//Messagebox.show("Salvataggio preso in consegna dal sistema. Al più presto verrà aggiornato.","Info", Messagebox.OK, Messagebox.INFORMATION);
				this.initModel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void onAggiungiIstruzione(Event event){
		Istruzione istruzioneRef = null;
		try{
			Istruzione istruzione = new Istruzione();
			istruzione=populateSelectItem(istruzione);
			this.listaIstruzione.add(istruzione);
			istruzioneRef = istruzione;
			this.aggiungiIstruzione();
			Messagebox.show("Salvataggio effettuato.","Info", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception ex){
			ex.printStackTrace();
			if (this.listaIstruzione!=null && istruzioneRef!=null){
				this.listaIstruzione.remove(istruzioneRef);
			}
			throw new WrongValueException("Errore nel salvataggio dei dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		
			try {
				dta_data_conseguimento.setValue(null);
				this.initModel(true);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
	}
	
	/**
	 * Metodo che provvede all'inserimento di una nuova istruzione 
	 */
	private void aggiungiIstruzione() throws Exception{
		boolean esito = false;
		ICommand command = new SalvaIstruzioneCommand(this.user.getId(), this.listaIstruzione);
		esito = command.execute();
		if (!esito){
			throw new Exception("non è possibile eseguire l'operazione di inserimento dei nuovi dati");
		}
	}
	
	//primo evento generato al clic su un pulsante di rimozione istruzione
	public void onVerificaRimuoviIstruzione(ForwardEvent event){
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			final Istruzione istruzioneSelezionata = (Istruzione) rigaAttiva.getValue();
			//String titolo = istruzioneSelezionata.getTitoloStudi();
	        StringBuilder sb = new StringBuilder();
	        
	        GregorianCalendar gc = new GregorianCalendar();
	        gc.setTime(istruzioneSelezionata.getData());
	        int anno = Calendar.getInstance().get(Calendar.YEAR);
	        
	        if(istruzioneSelezionata!= null && istruzioneSelezionata.getTipoLaurea()!= null && !istruzioneSelezionata.getTipoLaurea().getDenominazione().isEmpty() && istruzioneSelezionata.getTitoloLaurea()!=null){
	        	sb.append("Procedere con l'eliminazione di '").append(anno).append(": ").append(istruzioneSelezionata.getTipoLaurea().getDenominazione()).append(" - ").append(istruzioneSelezionata.getTitoloLaurea().getDenominazione()).append("' ?");
	        }else if(istruzioneSelezionata!= null && istruzioneSelezionata.getTipoLaurea()!=null && istruzioneSelezionata.getTitoloLaurea()==null && !istruzioneSelezionata.getTipoLaurea().getDenominazione().isEmpty() && istruzioneSelezionata.getCitta()!=null && !istruzioneSelezionata.getCitta().getDenominazione().isEmpty()){
	        	sb.append("Procedere con l'eliminazione di '").append(anno).append(": ").append(istruzioneSelezionata.getTipoLaurea().getDenominazione()).append(" - ").append(istruzioneSelezionata.getCitta().getDenominazione()).append("' ?");
	        }else if(istruzioneSelezionata!= null && istruzioneSelezionata.getTipoLaurea()!=null && istruzioneSelezionata.getTitoloLaurea()==null && istruzioneSelezionata.getData()!=null){
	        	sb.append("Procedere con l'eliminazione di '").append(anno).append(" - ").append(istruzioneSelezionata.getTipoLaurea().getDenominazione()).append("' ?");
	        }
	        
	        Messagebox.show(sb.toString(), "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
	        new org.zkoss.zk.ui.event.EventListener() {
	            public void onEvent(Event event) throws InterruptedException {
	                if (event.getName().equals("onOK")) {	                	
	                	Clients.showBusy("validazione in corso... ");
	            		Events.echoEvent("onRimuoviIstruzione",Path.getComponent("/win_istruzione"),istruzioneSelezionata);
	                } else {
	                    
	                }
	            }
	        });
		}catch(WrongValueException ex){
			throw ex;
		}catch(Exception ex){
			
		}
	}
	public Istruzione getIstruzioneSelezionata() {
		return istruzioneSelezionata;
	}

	public void onModificaIstruzione(ForwardEvent event){	
		
		try{
			this.bnt_aggiorna_istruzione.setVisible(true);
			this.bnt_aggiungi_istruzione.setVisible(false);
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			clearListaTipoLaurea();
			clearListaTitoloLaurea();
			clearListaCitta();
			clearListaAtenei();
			loadListaTipoLaurea();
			istruzioneSelezionata = (Istruzione) rigaAttiva.getValue();
			selectTitoli(istruzioneSelezionata.getTipoLaurea().getId());
			if(!istruzioneSelezionata.isEstero()){
				selectCitta();
				selectAtenei(istruzioneSelezionata.getCitta().getId());
				
				if(istruzioneSelezionata!=null && istruzioneSelezionata.getTipoLaurea()!= null){	
					String tipoLaurea = istruzioneSelezionata.getTipoLaurea().getDenominazione();
					
					setVisibleFields(tipoLaurea);
					
					if(tipoLaurea.equals("Altro")){
						txt_titoloStudio.setValue(istruzioneSelezionata.getTitoloStudi());
					}else if(tipoLaurea.equals("Dottorato di Ricerca")){
						txt_titoloStudio.setValue(istruzioneSelezionata.getTitoloStudi());
					}
				}
					
				
				
			}else{
				clearListaCitta();
				clearListaAtenei();
				
				
				if(istruzioneSelezionata!=null && istruzioneSelezionata.getTipoLaurea()!= null){	
					String tipoLaurea = istruzioneSelezionata.getTipoLaurea().getDenominazione();
					
					if(tipoLaurea.equals("Altro")){
						this.cmb_titolo.setVisible(false);
						this.lbl_titoloStudio.setVisible(false);
						this.lbl_titoloStudio_mand.setVisible(false);
					}else if(tipoLaurea.equals("Dottorato di Ricerca")){
						this.cmb_titolo.setVisible(false);
						this.lbl_titoloStudio.setVisible(false);
						this.lbl_titoloStudio_mand.setVisible(false);
					}else{
						this.cmb_titolo.setVisible(true);
						this.lbl_titoloStudio.setVisible(true);
						this.lbl_titoloStudio_mand.setVisible(true);
					}
				}		
				
				this.cmb_citta.setVisible(false);
				this.cmb_ateneo.setVisible(false);
				this.lbl_citta.setVisible(false);
				this.lbl_citta_mand.setVisible(false);
				this.lbl_ateneo.setVisible(false);
				this.lbl_ateneo_mand.setVisible(false);
				this.lbl_desc.setVisible(true);
				this.lbl_desc_mand.setVisible(true);
				this.txt_titoloStudio.setVisible(true);
				this.lbl_ind_desc.setVisible(true);
				
				txt_titoloStudio.setValue(istruzioneSelezionata.getTitoloStudi());
			}
			((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();

		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
		
	}
	
	public void onRimuoviIstruzione(Event event){
		boolean esito = false;
		try{
			Istruzione istruzioneSelezionata = (Istruzione) event.getData();
			ICommand command = new CancellaIstruzioneCommand(istruzioneSelezionata.getId());
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

	public TreeSet<Istruzione> getListaIstruzione() {
		return listaIstruzione;
	}


	public Set<TipoLaurea> getListaTipoLaurea() {
		return listaTipoLaurea;
	}


	public Set<Citta> getListaCitta() {
		return listaCitta;
	}


	public Set<Atenei> getListaAtenei() {
		return listaAtenei;
	}
	
	public Set<TitoloLaurea> getListaTitoloLaurea() {
		return listaTitoloLaurea;
	}

	
	
}