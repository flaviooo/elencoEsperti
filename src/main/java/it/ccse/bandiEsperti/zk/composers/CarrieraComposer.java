package it.ccse.bandiEsperti.zk.composers;

import it.ccse.bandiEsperti.business.command.AdminCommand;
import it.ccse.bandiEsperti.business.command.AggiornaListaEsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.CancellaEsperienzaLavorativaCommand;
import it.ccse.bandiEsperti.business.command.DatoreDiLavoroCommand;
import it.ccse.bandiEsperti.business.command.ListaProfessioneCommand;
import it.ccse.bandiEsperti.business.command.LoginCommand;
import it.ccse.bandiEsperti.business.command.ModificaEspertoCommand;
import it.ccse.bandiEsperti.business.command.ProfessioneCommand;
import it.ccse.bandiEsperti.business.command.RicercaCarrieraCommand;
import it.ccse.bandiEsperti.business.command.SalvaEsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.TipoProfessioneCommand;
import it.ccse.bandiEsperti.business.command.interfaces.ICommand;
import it.ccse.bandiEsperti.business.model.DatoreDiLavoro;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Professione;
import it.ccse.bandiEsperti.business.model.TipoProfessione;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CheckEvent;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

public class CarrieraComposer extends GenericForwardComposer {
	private static final long serialVersionUID = -3011711889760264355L;

	
	private Textbox txt_mansione;
	private Textbox txt_ruolo;
	private Textbox txt_presentazione;
	private String presentazione;
	private Set<TipoProfessione> listaTipoProfessione = new LinkedHashSet<TipoProfessione>();
	private Set<DatoreDiLavoro> listaDatoriDiLavoro = new LinkedHashSet<DatoreDiLavoro>();
	private Set<Professione> listaProfessione = new LinkedHashSet<Professione>();
	

	private Label lbl_datore;
	private Label lbl_ultimo_datore;

	private Label  lbl_mansione;
	private Label  lbl_nominativo;
	private Label  lbl_ruolo;
	private Label  lbl_professione;
	private Label lbl_data_fine;
	private Combobox cmb_tipo;
	private Combobox cmb_datori;
	private Combobox cmb_professione;
	private Button bnt_aggiungi_carriera;
	private Button bnt_aggiorna_carriera;
	
	private Combobox dta_data_inizio;
	private Combobox dta_data_fine;
	private Checkbox in_corso;
	private Checkbox principale;
	private Textbox txt_nominativo;
	private List<String> listaAnniInizio = new ArrayList<String>();
	private List<String> listaAnniFine = new ArrayList<String>();
	private Set<EsperienzaLavorativa> listaEsperienze = new java.util.LinkedHashSet<EsperienzaLavorativa>();
	private EsperienzaLavorativa carrieraSelezionata;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	private Esperto esperto;

	


	public List<String> getListaAnniInizio() {
		return listaAnniInizio;
	}


	public void setListaAnniInizio(List<String> listaAnniInizio) {
		this.listaAnniInizio = listaAnniInizio;
	}


	public List<String> getListaAnniFine() {
		return listaAnniFine;
	}


	public void setListaAnniFine(List<String> listaAnniFine) {
		this.listaAnniFine = listaAnniFine;
	}


	public EsperienzaLavorativa getCarrieraSelezionata() {
		return carrieraSelezionata;
	}
	private void checkDate() throws WrongValueException{
		String dataInizio = (String)this.dta_data_inizio.getSelectedItem().getValue();
		String dataFine = null;
		if (this.dta_data_fine.getSelectedItem()==null && 
			principale.isChecked()){
			throw new WrongValueException("In assensa di 'Anno di fine' è obbligatorio selezionare 'L'attuale Posizione Lavorativa'" );
		}else{
			if(!principale.isChecked()){
				dataFine = (String)this.dta_data_fine.getSelectedItem().getValue();
				if(dataFine.compareTo(dataInizio)<0){
					throw new WrongValueException(" 'Anno di fine' non può essere maggiore di 'Anno di inizio'");
				}
			}
		}		
	}
	
	/** Evento per la modifica del dato */
	public void onClick$bnt_modifica_carriera(Event event) {
		if (this.carrieraSelezionata==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		checkDate();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onModificaCarriera",event.getTarget(),null);
		
	}
	/** Evento per l'inserimento di un nuovo dato */
	public void onClick$bnt_aggiorna_carriera(Event event) {
		if (this.carrieraSelezionata==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		checkDate();
		if(this.principale.isChecked()){
			RicercaCarrieraCommand ricercaCarrieraCommand = new RicercaCarrieraCommand(esperto.getId());
			EsperienzaLavorativa esperienzaLavorativaPrincipale = ricercaCarrieraCommand.findCarrieraPrincipaleByIdEsperto(esperto.getId());
			
			if(esperienzaLavorativaPrincipale!=null && (carrieraSelezionata.getId()!=esperienzaLavorativaPrincipale.getId())){
				try {
					Messagebox.show("Procedere con la sostituzione della carriera principale?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
					        new org.zkoss.zk.ui.event.EventListener() {
					            public void onEvent(Event event) throws InterruptedException {
					                if (event.getName().equals("onOK")) {	                	
					                	Clients.showBusy("Validazione e Salvataggio in corso... ");
					                	Events.echoEvent("onModCarriera",Path.getComponent("/win_carriera"),new Boolean(true));
					                
					                	
					                } else {
					                	try {
											//initModel(true);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
					                }
					            }
							});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				Clients.showBusy("Validazione e Salvataggio in corso... ");
		    	Events.echoEvent("onModCarriera",event.getTarget(),new Boolean(false));
			}
		}else{
			Clients.showBusy("Validazione e Salvataggio in corso... ");
	    	Events.echoEvent("onModCarriera",event.getTarget(),new Boolean(false));
		}
	}
	private EsperienzaLavorativa setTipoProfessione(EsperienzaLavorativa esperienzaLavorativa){
		AdminCommand tipoProfessioneCommand = new TipoProfessioneCommand();
		TipoProfessione tipoProfessione = (TipoProfessione)tipoProfessioneCommand.findById((Integer)(this.cmb_tipo.getSelectedItem().getValue()));
		
		if(tipoProfessione!=null){
			esperienzaLavorativa.setTipoProfessione(tipoProfessione);
		}
		return esperienzaLavorativa;
	}
	
	private EsperienzaLavorativa setProfessione(EsperienzaLavorativa esperienzaLavorativa){
		if(this.cmb_professione.isVisible()){
			AdminCommand professioneCommand = new ProfessioneCommand();
			Professione professione = (Professione) professioneCommand.findById((Integer)(this.cmb_professione.getSelectedItem().getValue()));
			if(professione!=null){
				esperienzaLavorativa.setProfessione(professione);
			}
			if(this.cmb_professione.getSelectedItem().getLabel().equals("Altro") && this.txt_ruolo.getValue()!=null){
				esperienzaLavorativa.setRuolo(this.txt_ruolo.getValue());
			}else{
				esperienzaLavorativa.setRuolo(null);
			}
		}
		return esperienzaLavorativa;
	}
	private EsperienzaLavorativa setAttivitaLavorativa(EsperienzaLavorativa esperienzaLavorativa){
		if(txt_mansione.isVisible() && txt_mansione.getValue()!=null){
			esperienzaLavorativa.setAttivitaLavorative(txt_mansione.getValue());
		}
		return esperienzaLavorativa;
	}
	
	private EsperienzaLavorativa setDatoreDiLavoro(EsperienzaLavorativa esperienzaLavorativa){
		if(this.cmb_datori.isVisible()){
			AdminCommand datoreDiLavoroCommand = new DatoreDiLavoroCommand();
			DatoreDiLavoro datoreDiLavoro = null;
			if(!this.cmb_datori.getSelectedItem().getLabel().equals("Altro")){
				datoreDiLavoro = (DatoreDiLavoro) datoreDiLavoroCommand.findById((Integer)(this.cmb_datori.getSelectedItem().getValue()));
				
			}else if(this.txt_nominativo.isVisible() && this.txt_nominativo.getValue()!=null){
				DatoreDiLavoro datoreDiLavoroNew = new DatoreDiLavoro();
				datoreDiLavoroNew.setDenominazione(this.txt_nominativo.getValue());
				//datoreDiLavoroNew.setTipoProfessione(esperienzaLavorativa.getTipoProfessione());
				Integer id = datoreDiLavoroCommand.inserisci(datoreDiLavoroNew);
				datoreDiLavoro = (DatoreDiLavoro) datoreDiLavoroCommand.findById(id);
				
			}
			if(datoreDiLavoro!=null){
				esperienzaLavorativa.setDatoreDiLavoro(datoreDiLavoro);
			}
		}
		
		return esperienzaLavorativa;
	}
	private EsperienzaLavorativa populateSelectItem(EsperienzaLavorativa esperienzaLavorativa){
		
		
		if(this.dta_data_inizio.getSelectedItem()!=null){
			esperienzaLavorativa.setDataInizioString((String)this.dta_data_inizio.getSelectedItem().getValue());
		}
		esperienzaLavorativa.setFlagCarriera(true);
		esperienzaLavorativa.setFlagPrincipale(this.principale.isChecked());
		//esperienzaLavorativa.setInCorso(this.in_corso.isChecked());
		if(this.principale.isChecked()){
			esperienzaLavorativa.setDataFine(null);
		}else{
			if(this.dta_data_fine.getSelectedItem()!=null){
				esperienzaLavorativa.setDataFineString((String)this.dta_data_fine.getSelectedItem().getValue());
				}
		}
		esperienzaLavorativa=setTipoProfessione(esperienzaLavorativa);
		esperienzaLavorativa=setProfessione(esperienzaLavorativa);
		esperienzaLavorativa=setAttivitaLavorativa(esperienzaLavorativa);
		esperienzaLavorativa=setDatoreDiLavoro(esperienzaLavorativa);
		
		return esperienzaLavorativa;
		
	}

	public void onModCarriera(Event event){
		boolean esito = false;
		try {
			Boolean isFlagPrincipale = (Boolean) event.getData();
			if(isFlagPrincipale){
				for(EsperienzaLavorativa esperienzaLavorativa: this.listaEsperienze){
					esperienzaLavorativa.setFlagPrincipale(false);
				}
			}
			
			
			this.carrieraSelezionata=populateSelectItem(this.carrieraSelezionata);
			
			List<EsperienzaLavorativa> list = new ArrayList<EsperienzaLavorativa>(this.listaEsperienze);
			for(EsperienzaLavorativa e: list){
				this.listaEsperienze.add(e);
			}
			
			ICommand command = new AggiornaListaEsperienzeLavorativeCommand(list);
			esito = command.execute();
			
			if (!esito){
				throw new Exception("non è possibile eseguire l'operazione di aggiornamento dei dati");
			}else{
				Messagebox.show("Salvataggio completato.","Info", Messagebox.OK, Messagebox.INFORMATION);
			}
			
			this.initModel(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Clients.clearBusy();
			//Clients.alert("Salvataggio preso in consegna dal sistema. Al più presto verrà aggiornato.");
			try {
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void onModificaCarriera(ForwardEvent event){	
		
		try{
			this.bnt_aggiorna_carriera.setVisible(true);
			this.bnt_aggiungi_carriera.setVisible(false);
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			carrieraSelezionata = (EsperienzaLavorativa) rigaAttiva.getValue();
			if(carrieraSelezionata.getTipoProfessione()!=null){
				setVisibleFields(carrieraSelezionata.getTipoProfessione().getDenominazione());
			}
		
			//setVisibleDate(carrieraSelezionata.isInCorso());
			if(carrieraSelezionata.getProfessione()!=null){
				setVisibleRole(carrieraSelezionata.getProfessione().getDenominazione());
					
			}
			if(carrieraSelezionata.getDatoreDiLavoro()!=null){
				setVisibleNominativo(carrieraSelezionata.getDatoreDiLavoro().getDenominazione());
			}
			if(carrieraSelezionata.getTipoProfessione()!=null && (carrieraSelezionata.getTipoProfessione().getDenominazione().equals("Dipendente")    || 
																	carrieraSelezionata.getTipoProfessione().getDenominazione().equals("Pensionato"))
			){
				selectDatoriDiLavoro(Integer.valueOf(String.valueOf(carrieraSelezionata.getTipoProfessione().getId())));
			}
			((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();

		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
		
	}
	
	private void selectDatoriDiLavoro(Integer id){
		//this.esperto = (Esperto) session.getAttribute("login");
		//AdminCommand tipoProfessioneCommand = new TipoProfessioneCommand();
		AdminCommand datoreDiLavoroCommand = new DatoreDiLavoroCommand();
		
 		//TipoProfessione tipoProfessione = (TipoProfessione)tipoProfessioneCommand.findById(id);
		//List<DatoreDiLavoro> lista = new ArrayList<DatoreDiLavoro>(tipoProfessione.getDatoreDiLavoros());
		List<DatoreDiLavoro> lista = new ArrayList<DatoreDiLavoro>(datoreDiLavoroCommand.list());
		//Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
		listaDatoriDiLavoro.addAll(lista);
		for (DatoreDiLavoro datoreDiLavoro : lista){
			Comboitem itemDatoreDiLavoro = new Comboitem();
			itemDatoreDiLavoro.setValue(datoreDiLavoro.getId()); 
			itemDatoreDiLavoro.setLabel(datoreDiLavoro.getDenominazione());
			this.cmb_datori.appendChild(itemDatoreDiLavoro);
		}
	}

	
	public Set<EsperienzaLavorativa> getListaEsperienze() {
		return this.listaEsperienze;
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
	private void setDefaultFields(){
		
		this.bnt_aggiorna_carriera.setVisible(false);
		this.bnt_aggiungi_carriera.setVisible(true);
		this.cmb_datori.setVisible(false);
		this.lbl_datore.setVisible(false);
		this.lbl_ultimo_datore.setVisible(false);
		this.lbl_mansione.setVisible(false);
		this.txt_mansione.setVisible(false);
		this.lbl_nominativo.setVisible(false);
		this.txt_nominativo.setVisible(false);
		this.lbl_professione.setVisible(false);
		this.cmb_professione.setVisible(false);
		this.lbl_ruolo.setVisible(false);
		this.txt_ruolo.setVisible(false);
		this.lbl_data_fine.setVisible(true);
		this.dta_data_fine.setVisible(true);
		this.principale.setChecked(false);
	}
	
	private void setVisibleFields(String tipoProfessione){
		
		if(tipoProfessione.equals("Dipendente")){
			this.cmb_datori.setVisible(true);
			this.lbl_datore.setVisible(true);
			this.lbl_professione.setVisible(true);
			this.cmb_professione.setVisible(true);
			this.lbl_ultimo_datore.setVisible(false);
			this.lbl_mansione.setVisible(false);
			this.txt_mansione.setVisible(false);
			setVisibleDate(false);
			
		}else if(tipoProfessione.equals("Libero Professionista")){
			this.lbl_professione.setVisible(false);
			this.cmb_professione.setVisible(false);
			this.cmb_datori.setVisible(false);
			this.lbl_datore.setVisible(false);
			this.lbl_ultimo_datore.setVisible(false);
			this.lbl_mansione.setVisible(true);
			this.txt_mansione.setVisible(true);
			this.lbl_ultimo_datore.setVisible(false);
			setVisibleDate(false);
			
		}else if(tipoProfessione.equals("Pensionato")){
			this.cmb_datori.setVisible(false);
			this.lbl_datore.setVisible(false);
			this.lbl_ultimo_datore.setVisible(false);
			this.lbl_professione.setVisible(false);
			this.cmb_professione.setVisible(false);
			this.lbl_mansione.setVisible(false);
			this.txt_mansione.setVisible(false);
			setVisibleDate(true);
			
		}
	}
	public void onSelect$cmb_tipo(SelectEvent evt) throws Exception {
		Comboitem item = new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		clearDatoriDiLavoro();
		setVisibleFields(item.getLabel());
		if(item.getLabel().equals("Dipendente") || item.getLabel().equals("Pensionato")){
			selectDatoriDiLavoro(Integer.valueOf(String.valueOf(item.getValue())));
		}
	}	
	
	private void setVisibleRole(String professione){
		if(professione.equals("Altro")){
			this.lbl_ruolo.setVisible(true);
			this.txt_ruolo.setVisible(true);
		}else{
			this.lbl_ruolo.setVisible(false);
			this.txt_ruolo.setVisible(false);
		}
	}
	private void setVisibleDate(boolean inCorso){
		if(inCorso){
			this.lbl_data_fine.setVisible(false);
			this.dta_data_fine.setVisible(false);
		}else{
			this.lbl_data_fine.setVisible(true);
			this.dta_data_fine.setVisible(true);
		}
	}
	private void setVisibleNominativo(String datore){
		if(datore.equals("Altro")){
			this.lbl_nominativo.setVisible(true);
			this.txt_nominativo.setVisible(true);
		}else{
			this.lbl_nominativo.setVisible(false);
			this.txt_nominativo.setVisible(false);
		}
	}
	/**
	public void onCheck$in_corso( CheckEvent evt) throws Exception {
		Checkbox checkbox = (Checkbox)evt.getTarget();
		setVisibleDate(checkbox.isChecked());
	}
	**/
	
	
	public void onCheck$principale( CheckEvent evt) throws Exception {
		Checkbox checkbox = (Checkbox)evt.getTarget();
		setVisibleDate(checkbox.isChecked());
	}
	
	public void onSelect$cmb_professione(SelectEvent evt) throws Exception {
		Comboitem item = new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		setVisibleRole(item.getLabel());
	}	
	
	public void onSelect$cmb_datori(SelectEvent evt) throws Exception {
		Comboitem item = new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		setVisibleNominativo(item.getLabel());
		
	}	
	
	private void clearDatoriDiLavoro(){
		listaDatoriDiLavoro.clear();
		this.cmb_datori.setText("");
		this.cmb_datori.setSelectedIndex(-1);
		this.cmb_datori.getChildren().clear();
		
	}
	
	
	private void loadListaTipoProfessione(){
		AdminCommand tipoProfessioneCommand = new TipoProfessioneCommand();
		List<TipoProfessione> lista = new ArrayList<TipoProfessione>(tipoProfessioneCommand.list());
		listaTipoProfessione.addAll(lista);
	}
	
	private void loadListaProfessione(){
		ListaProfessioneCommand listaProfessioneCommand = new ListaProfessioneCommand(esperto);
		listaProfessioneCommand.execute();
		List<Professione> lista = new ArrayList<Professione>(listaProfessioneCommand.getSet());
		listaProfessione.addAll(lista);
	}
	
	
	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		try{
			carrieraSelezionata = null;
			if(isUpdate){
				setDefaultFields();
			}else{
				for(int i=1960; i<=2020; i++){
					listaAnniInizio.add(String.valueOf(i));
					listaAnniFine.add(String.valueOf(i));
				}
				loadListaTipoProfessione();
				loadListaProfessione();
			
			}
			presentazione = this.esperto.getPresentazione();
			txt_presentazione.setValue(presentazione);
			ICommand command = new RicercaCarrieraCommand(esperto.getId());
			command.execute();
			List<EsperienzaLavorativa> lista = new ArrayList<EsperienzaLavorativa>(command.getSet());
			Collections.sort(lista);
			this.listaEsperienze.clear();
		
			for(EsperienzaLavorativa e: lista){
				this.listaEsperienze.add(e);
			}
			//this.listaEsperienze.addAll(lista);
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
	
	
	
	/** Evento per l'inserimento di un nuovo dato */
	public void onClick$bnt_aggiungi_carriera(Event event) {
		checkDate();
		
		if(this.txt_nominativo.isVisible() && (this.txt_nominativo.getValue()==null || this.txt_nominativo.getValue().trim().length()==0)){
			throw new WrongValueException("Inserire il nominativo del Datore Di Lavoro" );
		
		}
		if(this.principale.isChecked()){
			RicercaCarrieraCommand ricercaCarrieraCommand = new RicercaCarrieraCommand(esperto.getId());
			EsperienzaLavorativa esperienzaLavorativaPrincipale = ricercaCarrieraCommand.findCarrieraPrincipaleByIdEsperto(esperto.getId());
			
			if(esperienzaLavorativaPrincipale!=null){
				try {
					Messagebox.show("Procedere con la sostituzione della carriera principale?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
					        new org.zkoss.zk.ui.event.EventListener() {
					            public void onEvent(Event event) throws InterruptedException {
					                if (event.getName().equals("onOK")) {	                	
					                	Clients.showBusy("Validazione e Salvataggio in corso... ");
					                	Events.echoEvent("onAggiungiCarriera",Path.getComponent("/win_carriera"),new Boolean(true));
					                
					                	
					                } else {
					                	try {
											//initModel(true);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
					                }
					            }
							});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				Clients.showBusy("Validazione e Salvataggio in corso... ");
		    	Events.echoEvent("onAggiungiCarriera",event.getTarget(),new Boolean(false));
			}
		}else{
			Clients.showBusy("Validazione e Salvataggio in corso... ");
	    	Events.echoEvent("onAggiungiCarriera",event.getTarget(),new Boolean(false));
		}
		
	
	}
	
	public void onAggiungiCarriera(Event event){
		EsperienzaLavorativa esperienzaRif = null;
		try{
			List<Integer> anniEsperienzeLavorative = new ArrayList<Integer>();
			Boolean isFlagPrincipale = (Boolean) event.getData();
			if(isFlagPrincipale){
				for(EsperienzaLavorativa esperienzaLavorativa: this.listaEsperienze){
					esperienzaLavorativa.setFlagPrincipale(false);
				}
			}
			EsperienzaLavorativa esperienzaLavorativa = new EsperienzaLavorativa();
			esperienzaLavorativa=populateSelectItem(esperienzaLavorativa);
			esperienzaRif = esperienzaLavorativa;
			//qui
			for (EsperienzaLavorativa e : listaEsperienze){
				if(e.getDataInizio()!=null && e.getDataFine()!=null){
					int diff=e.getDiffInizioFine();
					for(Integer anno:e.getDataInizioIncrementList(diff)){
						if(!anniEsperienzeLavorative.contains(anno)){
							anniEsperienzeLavorative.add(anno);
						}
					}
				
				}
			}
			int diffNew=esperienzaLavorativa.getDiffInizioFine();
			if(diffNew>0){
				for(Integer annoNew:esperienzaLavorativa.getDataInizioIncrementList(diffNew)){
					if(anniEsperienzeLavorative.contains(annoNew)){
						throw new Exception("non è possibile aggiungere la nuova esperienza lavorativa in quanto esistente"
							+ " una sovrapposizione di date con una esperienza già inserita");
					}
				}
			}
		
			this.listaEsperienze.add(esperienzaLavorativa);
			this.aggiungiEsperienze();
			Messagebox.show("Salvataggio completato.","Info", Messagebox.OK, Messagebox.INFORMATION);
		
				
		}catch(Exception ex){
			if (this.listaEsperienze!=null && esperienzaRif!=null){
				this.listaEsperienze.remove(esperienzaRif);
			}
			throw new WrongValueException("Errore nel salvataggio dei dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
			try {
				
				initModel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
	}
	
	private void aggiungiEsperienze() throws Exception{
		boolean esito = false;
		
		ICommand command = new SalvaEsperienzeLavorativeCommand(this.esperto.getId(), this.listaEsperienze);
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
			            		Events.echoEvent("onRimuoviEsperienze",Path.getComponent("/win_carriera"),esperienza);
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
	public void onClick$bnt_salva_presentazione(Event event) {
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onSalvaPresentazione",event.getTarget(),null);
	}
	
	public void onSalvaPresentazione(Event event){
		boolean esito = false;
		try {
			ModificaEspertoCommand mc = new ModificaEspertoCommand(this.esperto);
			esito = mc.salvaPresentazioneEsperto(txt_presentazione.getValue());
			if (!esito){
				throw new Exception("non è possibile eseguire l'operazione di salvataggio dell'informazione inserita");
			}else{
				Messagebox.show("Salvataggio effettuato.","Info", Messagebox.OK, Messagebox.INFORMATION);
				
			}
			//aggiornare i dati dell'oggetto in sessione
			LoginCommand lc = new LoginCommand(this.esperto.getCf(), this.esperto.getPassword());
			lc.execute();
			session.setAttribute("login", lc.getE());
			this.esperto = (Esperto) session.getAttribute("login");
			presentazione = this.esperto.getPresentazione();
		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
	}
	
	public Set<TipoProfessione> getListaTipoProfessione() {
		return listaTipoProfessione;
	}
	
	public Set<DatoreDiLavoro> getListaDatoriDiLavoro() {
		return listaDatoriDiLavoro;
	}
	public Set<Professione> getListaProfessione() {
		return listaProfessione;
	}


	public String getPresentazione() {
		return presentazione;
	}
	
	
}