package it.ccse.bandiEsperti.zk.composers;

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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import it.ccse.bandiEsperti.business.command.CancellaPrecedentiIncarichiCommand;
import it.ccse.bandiEsperti.business.command.DeliberaCommand;
import it.ccse.bandiEsperti.business.command.RicercaPrecedentiIncarichiCommand;
import it.ccse.bandiEsperti.business.command.SalvaPrecedentiIncarichiCommand;
import it.ccse.bandiEsperti.business.command.interfaces.ICommand;
import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.PrecedenteIncarico;

public class IncarichiComposer extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Grid inboxGrid;

	Set<PrecedenteIncarico> precedenteIncarico;
	private Textbox txt_nome_progetto;
	private Textbox txt_finanziamento;
	private Textbox txt_titolo_progetto; 
	private Textbox txt_abstract;
	private Checkbox chk_internazionale;
	private Checkbox chk_europeo;
	private Checkbox chk_miur;
	private Checkbox chk_rds;
	private Checkbox chk_incorso;
	private Combobox cmb_delibere;
	private Label lbl_delibere;
	private Label lbl_data_fine;
	private Combobox dta_anno;
	private Combobox dta_data_fine;
	private List<String> listaAnni = new ArrayList<String>();
	private List<String> listaAnniFine = new ArrayList<String>();
	private Button bnt_aggiungi_precedente_incarico;
	private Button bnt_aggiorna_precedente_incarico;
	private PrecedenteIncarico precedenteIncaricoSelezionato;
	
	
	private Set<Delibera> listaDelibera = new LinkedHashSet<Delibera>();
	private Esperto esperto;

	public List<String> getListaAnniFine() {
		return listaAnniFine;
	}

	public void setListaAnniFine(List<String> listaAnniFine) {
		this.listaAnniFine = listaAnniFine;
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
	
	private void checkDate() throws WrongValueException{
		if(this.dta_anno.getSelectedItem()!= null){
			String dataInizio = (String)this.dta_anno.getSelectedItem().getValue();
			
			Esperto user = (Esperto) session.getAttribute("login");
			
			String dataNascita = user.getDataNascita().toString();
			//Controllo dataInizio
			if(dataInizio!=null && dataNascita!= null){
				if(dataInizio.compareTo(dataNascita)< 0){
					throw new WrongValueException("Data Inizio non può essere precedente alla data di nascita" );
				}
			}
			
			String dataFine = null;
			if (this.dta_data_fine.getSelectedItem()==null && !chk_incorso.isChecked()){
				throw new WrongValueException("In assensa di 'Anno di fine' è obbligatorio selezionare 'In corso'" );
			}else{
				if(!chk_incorso.isChecked()){
					dataFine = (String)this.dta_data_fine.getSelectedItem().getValue();
					if(dataFine.compareTo(dataInizio)<0){
						throw new WrongValueException(" 'Anno di fine' non può essere maggiore di 'Anno di inizio'");
					}
				}
			}		
		}
		
		//Controllo dati obbligatori
        if(txt_nome_progetto.getValue()==null){
        	throw new WrongValueException("Soggetto che ha conferito l'incarico obbligatorio");
		}
        
        if(txt_titolo_progetto.getValue()==null){
        	throw new WrongValueException("Titolo progetto obbligatorio");
		}
        
        if(txt_abstract.getValue()==null){
        	throw new WrongValueException("Descrizione sintetica incarico/progetto obbligatorio");
		}
	}
	
	public List<String> getListaAnni() {
		return listaAnni;
	}

	public void setListaAnni(List<String> listaAnni) {
		this.listaAnni = listaAnni;
	}

	public Set<Delibera> getListaDelibera() {
		return listaDelibera;
	}

	public PrecedenteIncarico getPrecedenteIncaricoSelezionato() {
		return precedenteIncaricoSelezionato;
	}



	public Set<PrecedenteIncarico> getPrecedenteIncarico() {
		return precedenteIncarico;
	}

	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		esperto = (Esperto) session.getAttribute("login");
		if (esperto != null);
	
		// FORZA IL LOGOUT E LA RIDIREZIONE ALLA PAGINA CORRETTA
		if(esperto.getInviato()) {
    		String redirectUrlInviato = "/pages/BandiEspertiPages/homeEsperto.zul";
    		Executions.sendRedirect(redirectUrlInviato);
    	}		
		
		initModel(false);
	}
	
	

	private void loadListaDelibera(){
		DeliberaCommand deliberaCommand = new DeliberaCommand();
		
		List<Delibera> lista = new ArrayList<Delibera>(deliberaCommand.list());
		listaDelibera.addAll(lista);
		for (Delibera delibera : lista){
			Comboitem itemDelibera = new Comboitem();
			itemDelibera.setValue(delibera.getId()); 
			itemDelibera.setLabel(delibera.getDescrizioneDelibera());
			this.cmb_delibere.appendChild(itemDelibera);
		}
	}
	
	private void cleraListaDelibera(){
		listaDelibera.clear();
		this.cmb_delibere.setText("");
		this.cmb_delibere.setSelectedIndex(-1);
		this.cmb_delibere.getChildren().clear();
	}
	
	
	public void onClick$refreshBtn(Event e) {
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onRefreshBtn", e.getTarget(), null);
	}
	
	
	private void checkRds(Boolean check){
		if(check){
			cmb_delibere.setVisible(true);
			lbl_delibere.setVisible(true);
			loadListaDelibera();
		}else{
			cleraListaDelibera();
			cmb_delibere.setVisible(false);
			lbl_delibere.setVisible(false);
		}
	}
	
	public void onCheck$chk_rds( CheckEvent evt) throws Exception {
		Checkbox checkbox = (Checkbox)evt.getTarget();
		CheckDisableInternazionaleEuropeoNazionale();
		//checkRds(checkbox.isChecked());

	}

	/** Evento per il salvataggio dei dati modificati */
	public void onRefreshBtn(Event e) {
		try {
			this.getUpdatedData();
		} catch (Exception ex) {
			throw new WrongValueException("Errore nella validazione: " + ex.getMessage());
		} finally {
			Clients.clearBusy();
		}
	}
	
	/** Evento per la modifica del dato */
	
	public void onClick$bnt_modifica_incarico(Event event) {
		checkDate();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onModificaIncarico",event.getTarget(),null);
		
	}
	
	public void onModificaIncarico(ForwardEvent event){	
		
		try{
			this.bnt_aggiungi_precedente_incarico.setVisible(false);
			this.bnt_aggiorna_precedente_incarico.setVisible(true);
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			precedenteIncaricoSelezionato = (PrecedenteIncarico) rigaAttiva.getValue();
			setVisibleDate(precedenteIncaricoSelezionato.isInCorso());
			//checkRds(precedenteIncaricoSelezionato.isRds());
			((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();

		}catch(Exception ex){
			ex.printStackTrace();
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
			
		}finally{
			Clients.clearBusy();
		}
		
	}
	
	public void onCheck$chk_incorso( CheckEvent evt) throws Exception {
		Checkbox checkbox = (Checkbox)evt.getTarget();
		setVisibleDate(checkbox.isChecked());
	}
	

   private PrecedenteIncarico populateSelectItem(PrecedenteIncarico precedenteIncarico){
		
	   if(txt_nome_progetto.getValue()!=null){
		   precedenteIncarico.setNomeProgetto(txt_nome_progetto.getValue());
	   }
	   if(txt_abstract.getValue()!=null){
		   precedenteIncarico.setAbstractProgetto(txt_abstract.getValue());
	   }
		
	//	i.setAmministrazione(txt_admin.getValue());
		precedenteIncarico.setAmministrazione("EMPTY");
		
		if(txt_finanziamento.getValue()!=null){
			precedenteIncarico.setProgrammaFinanziamento(txt_finanziamento.getValue());
		}
		
		precedenteIncarico.setInternazionale(chk_internazionale.isChecked());
		precedenteIncarico.setEuropeo(chk_europeo.isChecked());
		precedenteIncarico.setRds(chk_rds.isChecked());
		precedenteIncarico.setMiur(chk_miur.isChecked());
		if(this.chk_incorso.isChecked()){
			precedenteIncarico.setDataFine(null);
		}else{
			if(this.dta_data_fine.getSelectedItem()!=null){
				precedenteIncarico.setDataFineString((String)this.dta_data_fine.getSelectedItem().getValue());
			}
		}
		precedenteIncarico.setInCorso(chk_incorso.isChecked());
		
		if(txt_titolo_progetto.getValue()!=null){
			precedenteIncarico.setTitoloProgetto(txt_titolo_progetto.getValue());
		}
		
		if(this.dta_anno.getSelectedItem()!=null){
			precedenteIncarico.setAnnoString((String)this.dta_anno.getSelectedItem().getValue());
		}
//		if(cmb_delibere.isVisible() && cmb_delibere.getSelectedItem()!=null){
//			AdminCommand deliberaCommand = new DeliberaCommand();
//			//Comboitem comboitem =  cmb_delibere.getSelectedItem();
//			Integer idDelibera=Integer.valueOf(String.valueOf(cmb_delibere.getSelectedItem().getValue()));
//			Delibera delibera = (Delibera) deliberaCommand.findById(idDelibera);
//			precedenteIncarico.setDelibera(delibera);
//	
//		}
		return precedenteIncarico;
			
	}

	public void onClick$bnt_aggiungi_precedente_incarico(Event e) {
		checkDate();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onAggiungi_precedente_incarico", e.getTarget(), null);
	}
	
	/** Evento per la modifica di un nuovo dato */
	public void onClick$bnt_aggiorna_precedente_incarico(Event event) {
		if (this.precedenteIncaricoSelezionato==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		Clients.showBusy("Validazione e Salvataggio in corso... ");
    	Events.echoEvent("onModIncarico",event.getTarget(),null);
	}
	
	public void onModIncarico(Event event){
		boolean esito = false;
		try {
			Esperto user = (Esperto) session.getAttribute("login");
			this.precedenteIncaricoSelezionato=populateSelectItem(this.precedenteIncaricoSelezionato);
			
			SalvaPrecedentiIncarichiCommand salvaPrecedentiIncarichiCommand = new SalvaPrecedentiIncarichiCommand(user.getId(), precedenteIncarico);
			esito = salvaPrecedentiIncarichiCommand.execute();
			
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
	
	/** Evento per l'inserimento di un nuovo dato */
	public void onAggiungi_precedente_incarico(Event e) throws InterruptedException {
		try {
			Esperto user = (Esperto) session.getAttribute("login");
			PrecedenteIncarico precedenteIncaricoNew = new PrecedenteIncarico();
			precedenteIncaricoNew=populateSelectItem(precedenteIncaricoNew);
			precedenteIncarico.add(precedenteIncaricoNew);
			SalvaPrecedentiIncarichiCommand sic = new SalvaPrecedentiIncarichiCommand(user.getId(), precedenteIncarico);
			sic.execute();
		
		} catch (Exception ex) {
			throw new WrongValueException("Errore nella validazione: " + ex.getMessage());
		} finally {
			Clients.clearBusy();
			//Clients.alert("Salvataggio preso in consegna dal sistema. Al più presto verrà aggiornato.");
			try {
				Messagebox.show("Salvataggio preso in consegna dal sistema. Al più presto verrà aggiornato.","Info", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			txt_abstract.setValue(" ");
			txt_nome_progetto.setValue(" ");
			chk_europeo.setChecked(false);
			chk_internazionale.setChecked(false);
			chk_miur.setChecked(false);
			chk_rds.setChecked(false);
			chk_incorso.setChecked(false);
			txt_titolo_progetto.setValue(" ");
			
		}
		inboxGrid.renderAll();
		Executions.sendRedirect("incarichi.zul");
	}

	/**
	 * Metodo che provvede al salvataggio della lista delle istruzioni
	 * modificate
	 */
	private Set<PrecedenteIncarico> getUpdatedData() {
	
		Esperto user = (Esperto) session.getAttribute("login");
		if (user != null);
		ICommand command = new SalvaPrecedentiIncarichiCommand(user.getId(), precedenteIncarico);
		command.execute();
		inboxGrid.getFellows();
		return precedenteIncarico;
	}

	
	//primo evento generato al clic su un pulsante di rimozione istruzione
	public void onVerificaRimuoviIncarichi(ForwardEvent event){
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			
			final PrecedenteIncarico selezionato = (PrecedenteIncarico) rigaAttiva.getValue();
			String titolo = selezionato.getAbstractProgetto();
	       
			Messagebox.show("Procedere con l'eliminazione di '"+titolo+"' ?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
	        new org.zkoss.zk.ui.event.EventListener() {
	            public void onEvent(Event event) throws InterruptedException {
	                if (event.getName().equals("onOK")) {	                	
	                	Clients.showBusy("validazione in corso... ");
	            		Events.echoEvent("onRimuoviIncarico",Path.getComponent("/win_incarichi"),selezionato);
	                } else {
	                    
	                }
	            }
	        });
		}catch(WrongValueException ex){
			throw ex;
		}catch(Exception ex){
			
		}
	}
	
	public void onRimuoviIncarico(Event event){	
		boolean esito = false;
		try{
			PrecedenteIncarico selezionato = (PrecedenteIncarico) event.getData();
			ICommand command = new CancellaPrecedentiIncarichiCommand(selezionato.getId());
			esito = command.execute();
			if (!esito){
				throw new Exception("non è possibile eseguire l'operazione di rimozione del record selezionato");
			}
			
			this.initModel(true);

		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
			//Executions.sendRedirect("incarichi.zul");
		}
		
	}	
	
	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		try{
			if(isUpdate){
				setDefaultFields();
			}else{
				for(int i=1940; i<=2020; i++){
					listaAnni.add(String.valueOf(i));
					listaAnniFine.add(String.valueOf(i));
				}
			}
			ICommand command = new RicercaPrecedentiIncarichiCommand(esperto.getId());
			command.execute();
			this.precedenteIncarico = command.getSet();

			if (isUpdate){
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private void setDefaultFields(){
		
		this.bnt_aggiorna_precedente_incarico.setVisible(false);
		this.bnt_aggiungi_precedente_incarico.setVisible(true);
		//checkRds(false);
		precedenteIncaricoSelezionato=null;
		chk_europeo.setChecked(false);
		chk_miur.setChecked(false);
		chk_rds.setChecked(false);
		chk_internazionale.setChecked(false);
		chk_incorso.setChecked(false);
		this.lbl_data_fine.setVisible(true);
		this.dta_data_fine.setVisible(true);
	
	}
	
	public void onClick$chk_internazionale(Event evt) throws Exception {
		CheckDisableEuropeoNazionaleRDS();
		//CheckDisableRDS();
	}

	public void onClick$chk_europeo(Event evt) throws Exception {
		CheckDisableInternazionaleNazionaleRDS();
		//CheckDisableRDS();
	}
	
	public void onClick$chk_miur(Event evt) throws Exception {
		CheckDisableInternazionaleEuropeoRDS();
		//CheckDisableRDS();
	}
	
	
	private void CheckDisableEuropeoNazionaleRDS() {
		
		if(chk_internazionale.isChecked())
		{
			chk_europeo.setChecked(false);
			chk_miur.setChecked(false);
			chk_rds.setChecked(false);
		}
	}
	
	private void CheckDisableInternazionaleNazionaleRDS() {
		
		if(chk_europeo.isChecked())
		{
			chk_internazionale.setChecked(false);
			chk_miur.setChecked(false);
			chk_rds.setChecked(false);
		}
	}
	
	private void CheckDisableInternazionaleEuropeoRDS() {
		
		if(chk_miur.isChecked())
		{
			chk_internazionale.setChecked(false);
			chk_europeo.setChecked(false);
			chk_rds.setChecked(false);
		}
	}
	
	private void CheckDisableInternazionaleEuropeoNazionale() {
		
		if(chk_rds.isChecked())
		{
			chk_internazionale.setChecked(false);
			chk_europeo.setChecked(false);
			chk_miur.setChecked(false);
		}
	}


	private void CheckDisableRDS() {
		if((chk_internazionale.isChecked() || chk_europeo.isChecked()) || chk_rds.isDisabled() == false)
		{
			chk_rds.setChecked(false);
			chk_rds.setDisabled(true);
		}
		else
		{
			chk_rds.setDisabled(false);
		}
	}

}