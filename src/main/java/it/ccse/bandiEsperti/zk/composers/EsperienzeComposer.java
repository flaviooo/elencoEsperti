package it.ccse.bandiEsperti.zk.composers;

import it.ccse.bandiEsperti.business.command.AdminCommand;
import it.ccse.bandiEsperti.business.command.AggiornaListaEsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.CancellaEsperienzaLavorativaCommand;
import it.ccse.bandiEsperti.business.command.DatoreDiLavoroCommand;
import it.ccse.bandiEsperti.business.command.EsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.ProfessioneCommand;
import it.ccse.bandiEsperti.business.command.SalvaEsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.SpecializzazioniCommand;
import it.ccse.bandiEsperti.business.command.TipoProfessioneCommand;
import it.ccse.bandiEsperti.business.command.interfaces.ICommand;
import it.ccse.bandiEsperti.business.model.DatoreDiLavoro;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Professione;
import it.ccse.bandiEsperti.business.model.Specializzazione;
import it.ccse.bandiEsperti.business.model.TipoProfessione;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;

import java.util.ArrayList;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import java.util.Collections;


public class EsperienzeComposer extends GenericForwardComposer {
	private static final long serialVersionUID = -3011711889760264355L;

	
	private Textbox txt_mansione;
	private Textbox txt_ruolo;
	private Combobox cmb_specializzazioni_1;
	private Combobox cmb_specializzazioni_2;
	private Combobox cmb_specializzazioni_3;
	private Set<Specializzazione> listaSpecializzazioni = new LinkedHashSet<Specializzazione>();
	private Set<TipoProfessione> listaTipoProfessione = new LinkedHashSet<TipoProfessione>();
	private Set<DatoreDiLavoro> listaDatoriDiLavoro = new LinkedHashSet<DatoreDiLavoro>();
	private Set<Professione> listaProfessione = new LinkedHashSet<Professione>();
	private Set<EsperienzaLavorativa> listaEsperienze = new LinkedHashSet<EsperienzaLavorativa>();;
	private EsperienzaLavorativa esperienzaSelezionata;

	private Label lbl_datore;
	private Label lbl_datore_mand;
	private Label lbl_ultimo_datore;
	private Label  lbl_mansione;
	private Label  lbl_nominativo;
	private Label  lbl_ruolo;
	private Label  lbl_ruolo_mand;
	private Label  lbl_professione;
	private Label lbl_data_fine;
	private Combobox cmb_tipo;
	private Combobox cmb_datori;
	private Combobox cmb_professione;
	private Button bnt_aggiungi_esperienza;
	private Button bnt_aggiorna_esperienza;
	private Combobox dta_data_inizio;
	private Combobox dta_data_fine;
	private List<String> listaAnniInizio = new ArrayList<String>();
	private List<String> listaAnniFine = new ArrayList<String>();
	private Checkbox in_corso;
	private Textbox txt_nominativo;


	private Esperto esperto;
	
	public Set<Specializzazione> getListaSpecializzazioni() {
		return listaSpecializzazioni;
	}

	
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

	
	public EsperienzaLavorativa getEsperienzaSelezionata() {
		return esperienzaSelezionata;
	}
	
	
	private void checkDate() throws WrongValueException{
		
		//Controllo dataInizio
		if(this.dta_data_inizio.getSelectedItem()==null){
			throw new WrongValueException("Data Inizio obbligatoria" );
		}

		
		if(this.dta_data_inizio.getSelectedItem()!= null){
			String dataInizio = (String)this.dta_data_inizio.getSelectedItem().getValue();
			String dataFine = null;
			
			Esperto user = (Esperto) session.getAttribute("login");
			
			String dataNascita = user.getDataNascita().toString();
			
			
			if(dataInizio!=null && dataNascita!= null){
				if(dataInizio.compareTo(dataNascita)< 0){
					throw new WrongValueException("Data Inizio non può essere precedente alla data di nascita" );
				}
			}
				
				
			if (this.dta_data_fine.getSelectedItem()==null && !in_corso.isChecked()){
				throw new WrongValueException("In assensa di 'Anno di fine' è obbligatorio selezionare 'In corso'" );
			}else{
				if(!in_corso.isChecked()){
					if(this.cmb_tipo.getSelectedItem()== null){
						throw new WrongValueException("Tipo obbligatorio" );
					}
						
					if(this.dta_data_fine.getSelectedItem()!= null){
						dataFine = (String)this.dta_data_fine.getSelectedItem().getValue();
						if(dataFine.compareTo(dataInizio)<0){
							throw new WrongValueException(" 'Anno di fine' non può essere maggiore di 'Anno di inizio'");
						}
					}
					
				}
			}
		}
		
		if(this.cmb_tipo.getSelectedItem()== null){
			throw new WrongValueException("Tipo obbligatorio" );
		}
		
		if(this.cmb_tipo.getSelectedItem()!= null &&  this.cmb_tipo.getValue().equals("Dipendente") && this.cmb_datori.getSelectedItem()== null){
			throw new WrongValueException("Datore di Lavoro obbligatorio" );
		}
		
		if(this.txt_mansione.getValue().isEmpty()){
			throw new WrongValueException("Descrizione Attività obbligatoria" );
		}
		
		
		if(this.cmb_professione.getSelectedItem()== null){
			throw new WrongValueException("Professione obbligatoria" );
		}
		
		if(this.cmb_professione.getSelectedItem()!= null && this.cmb_professione.getValue().equals("Altro") && this.txt_ruolo.getValue().isEmpty()){
			throw new WrongValueException("Ruolo obbligatorio" );
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
	
	/** Evento per la modifica del dato */
	
	public void onClick$bnt_modifica_esperienza(Event event) {
		
			if (this.esperienzaSelezionata==null){
				throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
			}
			checkDate();
			Clients.showBusy("Validazione e Salvataggio in corso... ");
			Events.echoEvent("onModificaEsperienza",event.getTarget(),null);
		
		
	}
	/** Evento per l'inserimento di un nuovo dato */
	public void onClick$bnt_aggiorna_esperienza(Event event) {
	
		if (this.esperienzaSelezionata==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		checkDate();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
    	Events.echoEvent("onModEsperienza",event.getTarget(),null);
		
	}
	private EsperienzaLavorativa setTipoProfessione(EsperienzaLavorativa esperienzaLavorativa){
		AdminCommand tipoProfessioneCommand = new TipoProfessioneCommand();
		if(this.cmb_tipo!= null && this.cmb_tipo.getSelectedItem()!= null){
			TipoProfessione tipoProfessione = (TipoProfessione)tipoProfessioneCommand.findById((Integer)(this.cmb_tipo.getSelectedItem().getValue()));
			
			if(tipoProfessione!=null){
				esperienzaLavorativa.setTipoProfessione(tipoProfessione);
			}
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
		}else{
			
		}
		
		return esperienzaLavorativa;
	}
	private EsperienzaLavorativa populateSelectItem(EsperienzaLavorativa esperienzaLavorativa){
	
		if(this.dta_data_inizio.getSelectedItem()!=null){
			String datainizio=(String)this.dta_data_inizio.getSelectedItem().getValue();
			esperienzaLavorativa.setDataInizioString(datainizio);
		}
		esperienzaLavorativa.setFlagCarriera(false);
		esperienzaLavorativa.setFlagPrincipale(false);
		esperienzaLavorativa.setInCorso(this.in_corso.isChecked());
		if(this.in_corso.isChecked()){
			esperienzaLavorativa.setDataFine(null);
		}else{
		
			if(this.dta_data_fine.getSelectedItem()!=null){
			
				esperienzaLavorativa.setDataFineString((String)this.dta_data_fine.getSelectedItem().getValue());
			}
		}
		esperienzaLavorativa=setTipoProfessione(esperienzaLavorativa);
		esperienzaLavorativa=setProfessione(esperienzaLavorativa);
		esperienzaLavorativa=setAttivitaLavorativa(esperienzaLavorativa);
		
		if(cmb_specializzazioni_1.getSelectedItem()!=null){
			AdminCommand specializzazioniCommand = new SpecializzazioniCommand();
			Specializzazione s1 = (Specializzazione) specializzazioniCommand.findById((Integer) this.cmb_specializzazioni_1.getSelectedItem().getValue());
			esperienzaLavorativa.setSpecializzazione1(s1);
		}
		
		if(cmb_specializzazioni_2.getSelectedItem()!=null){
			AdminCommand specializzazioniCommand = new SpecializzazioniCommand();
			Specializzazione s2 = (Specializzazione) specializzazioniCommand.findById((Integer) this.cmb_specializzazioni_2.getSelectedItem().getValue());
			esperienzaLavorativa.setSpecializzazione2(s2);
		}
		
		if(cmb_specializzazioni_3.getSelectedItem()!=null){
			AdminCommand specializzazioniCommand = new SpecializzazioniCommand();
			Specializzazione s3 = (Specializzazione) specializzazioniCommand.findById((Integer) this.cmb_specializzazioni_3.getSelectedItem().getValue());
			esperienzaLavorativa.setSpecializzazione3(s3);
		}
		
		setDatoreDiLavoro(esperienzaLavorativa);
		
		return esperienzaLavorativa;
		
	}

	public void onModEsperienza(Event event){
		boolean esito = false;
		try {
			
			this.esperienzaSelezionata=populateSelectItem(this.esperienzaSelezionata);
			List<EsperienzaLavorativa> list = new ArrayList<EsperienzaLavorativa>(this.listaEsperienze); 
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
	
	public void onModificaEsperienza(ForwardEvent event){	
		
		try{
			this.bnt_aggiorna_esperienza.setVisible(true);
			this.bnt_aggiungi_esperienza.setVisible(false);
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			esperienzaSelezionata = (EsperienzaLavorativa) rigaAttiva.getValue();
			if(esperienzaSelezionata.getTipoProfessione()!=null){
				setVisibleFields(esperienzaSelezionata.getTipoProfessione().getDenominazione());
			}
		
			
			setVisibleDate(esperienzaSelezionata.isInCorso());
			if(esperienzaSelezionata.getProfessione()!=null){
				setVisibleRole(esperienzaSelezionata.getProfessione().getDenominazione());
			}
			if(esperienzaSelezionata.getDatoreDiLavoro()!=null){
				setVisibleNominativo(esperienzaSelezionata.getDatoreDiLavoro().getDenominazione());
			}
			if(esperienzaSelezionata.getTipoProfessione()!=null && (esperienzaSelezionata.getTipoProfessione().getDenominazione().equals("Dipendente")    || 
																	esperienzaSelezionata.getTipoProfessione().getDenominazione().equals("Pensionato"))
			){
				selectDatoriDiLavoro(Integer.valueOf(String.valueOf(esperienzaSelezionata.getTipoProfessione().getId())));
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
		//AdminCommand  tipoProfessioneCommand = new TipoProfessioneCommand();
		//TipoProfessione tipoProfessione = (TipoProfessione)tipoProfessioneCommand.findById(id);
		//List<DatoreDiLavoro> lista = new ArrayList<DatoreDiLavoro>(tipoProfessione.getDatoreDiLavoros());
		AdminCommand datoreDiLavoroCommand = new DatoreDiLavoroCommand();
		List<DatoreDiLavoro> lista = new ArrayList<DatoreDiLavoro>(datoreDiLavoroCommand.list());
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
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
		initSpecializzazioni();
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
	private void setDefaultFields(){
		
		this.bnt_aggiorna_esperienza.setVisible(false);
		this.bnt_aggiungi_esperienza.setVisible(true);
		this.cmb_datori.setVisible(false);
		this.lbl_datore.setVisible(false);
		this.lbl_datore_mand.setVisible(false);
		this.lbl_ultimo_datore.setVisible(false);
		this.lbl_mansione.setVisible(true);
		this.txt_mansione.setVisible(true);
		this.lbl_nominativo.setVisible(false);
		this.txt_nominativo.setVisible(false);
		this.lbl_professione.setVisible(true);
		this.cmb_professione.setVisible(true);
		this.lbl_ruolo.setVisible(false);
		this.lbl_ruolo_mand.setVisible(false);
		this.txt_ruolo.setVisible(false);
		this.lbl_data_fine.setVisible(true);
		this.dta_data_fine.setVisible(true);
		in_corso.setChecked(false);
	}
	private void setVisibleFields(String tipoProfessione){
		
		if(tipoProfessione.equals("Dipendente")){
			this.cmb_datori.setVisible(true);
			this.lbl_datore.setVisible(true);
			this.lbl_datore_mand.setVisible(true);
			this.lbl_ultimo_datore.setVisible(false);
			
		}else if(tipoProfessione.equals("Libero Professionista")){
			this.cmb_datori.setVisible(false);
			this.lbl_datore.setVisible(false);
			this.lbl_datore_mand.setVisible(false);
			this.lbl_ultimo_datore.setVisible(false);
		
		}else if(tipoProfessione.equals("Pensionato")){
			this.cmb_datori.setVisible(true);
			this.lbl_datore.setVisible(false);
			this.lbl_datore_mand.setVisible(false);
			this.lbl_ultimo_datore.setVisible(true);
		
		}else if(tipoProfessione.equals("Altro")){
			this.cmb_datori.setVisible(false);
			this.lbl_datore.setVisible(false);
			this.lbl_datore_mand.setVisible(false);
			this.lbl_ultimo_datore.setVisible(false);
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
			this.lbl_ruolo_mand.setVisible(true);
			this.txt_ruolo.setVisible(true);
		}else{
			this.lbl_ruolo.setVisible(false);
			this.lbl_ruolo_mand.setVisible(false);
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
	
	public void onCheck$in_corso( CheckEvent evt) throws Exception {
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
		TipoProfessione tipoProfessioneToRemove=null;
		
		for(TipoProfessione tipoProfessione: lista){
			if(tipoProfessione.getDenominazione().equalsIgnoreCase("Pensionato")){
				tipoProfessioneToRemove=tipoProfessione;
			}
		
					
		}
		if(tipoProfessioneToRemove!=null){
			lista.remove(tipoProfessioneToRemove);
		}
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
		
		listaTipoProfessione.addAll(lista);
	}
	
	private void loadListaProfessione(){
		AdminCommand professioneCommand = new ProfessioneCommand();
		List<Professione> lista = new ArrayList<Professione>(professioneCommand.list());
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
		listaProfessione.addAll(lista);
	}
	
	
	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		try{
			esperienzaSelezionata = null;
			if(isUpdate){
				setDefaultFields();
			}else{
				for(int i=1940; i<=2020; i++){
					listaAnniInizio.add(String.valueOf(i));
					listaAnniFine.add(String.valueOf(i));
				}
				loadListaTipoProfessione();
				loadListaProfessione();
				
			
			
			}
			EsperienzeLavorativeCommand command = new EsperienzeLavorativeCommand(this.esperto.getId());
			List<EsperienzaLavorativa> lista = new ArrayList<EsperienzaLavorativa>(command.list());
			Collections.sort(lista);
			this.listaEsperienze.clear();
			this.listaEsperienze.addAll(lista);
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
	public void onClick$bnt_aggiungi_esperienza(Event event) {
		
		checkDate();
	
		if(this.txt_nominativo.isVisible() && (this.txt_nominativo.getValue()==null || this.txt_nominativo.getValue().trim().length()==0)){
			throw new WrongValueException("Inserire il nominativo del Datore Di Lavoro" );
		
		}
		Clients.showBusy("Validazione e Salvataggio in corso... ");
    	Events.echoEvent("onAggiungiEsperienza",event.getTarget(),new Boolean(false));
	}
	
	public void onAggiungiEsperienza(Event event){
		EsperienzaLavorativa esperienzaRif = null;
		try{
			EsperienzaLavorativa esperienzaLavorativa = new EsperienzaLavorativa();
			esperienzaLavorativa=populateSelectItem(esperienzaLavorativa);
			esperienzaRif = esperienzaLavorativa;
			
			if( cmb_specializzazioni_1.getSelectedItem()!=null){
				SpecializzazioniCommand specializzazioniCommand = new SpecializzazioniCommand();
				Integer idSpecializzazione=Integer.valueOf(String.valueOf(cmb_specializzazioni_1.getSelectedItem().getValue()));
				Specializzazione specializzazione = specializzazioniCommand.findById(idSpecializzazione);
				esperienzaLavorativa.setSpecializzazione1(specializzazione);
		
			}
			if(cmb_specializzazioni_2.getSelectedItem()!=null){
				SpecializzazioniCommand specializzazioniCommand = new SpecializzazioniCommand();
				Integer idSpecializzazione=Integer.valueOf(String.valueOf(cmb_specializzazioni_2.getSelectedItem().getValue()));
				Specializzazione specializzazione = specializzazioniCommand.findById(idSpecializzazione);
				esperienzaLavorativa.setSpecializzazione2(specializzazione);
		
			}
			if(cmb_specializzazioni_3.getSelectedItem()!=null){
				SpecializzazioniCommand specializzazioniCommand = new SpecializzazioniCommand();
				Integer idSpecializzazione=Integer.valueOf(String.valueOf(cmb_specializzazioni_3.getSelectedItem().getValue()));
				Specializzazione specializzazione = specializzazioniCommand.findById(idSpecializzazione);
				esperienzaLavorativa.setSpecializzazione3(specializzazione);
		
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
	
	public Set<TipoProfessione> getListaTipoProfessione() {
		return listaTipoProfessione;
	}
	
	public Set<DatoreDiLavoro> getListaDatoriDiLavoro() {
		return listaDatoriDiLavoro;
	}
	public Set<Professione> getListaProfessione() {
		return listaProfessione;
	}
}