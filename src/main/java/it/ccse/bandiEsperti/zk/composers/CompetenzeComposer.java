package it.ccse.bandiEsperti.zk.composers;

import it.ccse.bandiEsperti.business.command.AdminCommand;
import it.ccse.bandiEsperti.business.command.CancellaCompetenzaCommand;
import it.ccse.bandiEsperti.business.command.LoginCommand;
import it.ccse.bandiEsperti.business.command.ModificaEspertoCommand;
import it.ccse.bandiEsperti.business.command.RicercaCompetenzeCommand;
import it.ccse.bandiEsperti.business.command.SalvaCompetenzeCommand;
import it.ccse.bandiEsperti.business.command.TemiCommand;
import it.ccse.bandiEsperti.business.command.interfaces.ICommand;
import it.ccse.bandiEsperti.business.dao.ITemiSpecializzazioniDAO;
import it.ccse.bandiEsperti.business.dao.TemiSpecializzazioniDAO;
import it.ccse.bandiEsperti.business.model.Competenza;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Specializzazione;
import it.ccse.bandiEsperti.business.model.Tema;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;

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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;


public class CompetenzeComposer extends GenericForwardComposer {
	private static final long serialVersionUID = 7968674663788734966L;
	
	private Esperto esperto;
	private Set<Competenza> listaCompetenze;
	private List<Tema> listaTemi; //i temi sono i settori
	private Tema temaSelezionato;
	private Specializzazione specializzazioneSelezionata;
	private String competenza;
	private Combobox cmb_specializzazioni;
	private Textbox txt_altro;
	private Textbox txt_competenza;
	private Label lblAltro;
	private Checkbox chk_principale;
	private Checkbox chk_secondario;
	
	//****************************
	// richiamati dal binder
	//****************************
	public Set<Competenza> getListaCompetenze() {
		return this.listaCompetenze;
	}
	public List<Tema> getListaTemi() {
		return this.listaTemi;
	}
	public void setTemaSelezionato(Tema tema){
		this.temaSelezionato = tema;
		this.initSpecializzazioni();
	}
	public Tema getTemaSelezionato(){
		return this.temaSelezionato;
	}
	public Specializzazione getSpecializzazioneSelezionata() {
		return specializzazioneSelezionata;
	}
	public void setSpecializzazioneSelezionata(Specializzazione specializzazioneSelezionata) {
		this.specializzazioneSelezionata = specializzazioneSelezionata;
		
		boolean visibile = false;
		if (specializzazioneSelezionata!=null){
			visibile = specializzazioneSelezionata.getNome().equals("Altro");
		}
		this.txt_altro.setText(null);
		this.txt_altro.setVisible(visibile);
		this.lblAltro.setVisible(visibile);
	}
	//****************************
	
	public String getCompetenza() {
		return competenza;
	}
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		this.esperto = (Esperto) session.getAttribute("login");
		
		if(esperto.getInviato()) {
    		String redirectUrlInviato = "/pages/BandiEspertiPages/homeEsperto.zul";
    		Executions.sendRedirect(redirectUrlInviato);
    	}
		this.initModel(false);
	}
	
	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		try{
			//lista delle competenze
			ICommand commandCompetenze = new RicercaCompetenzeCommand(this.esperto.getId());
			competenza = this.esperto.getCompetenza();
			commandCompetenze.execute();
			List<Competenza> listaComp = new ArrayList<Competenza>(commandCompetenze.getSet());
			Collections.sort(listaComp, ComparatorFactory.getInstance().retrieve("Competenza"));
		
			this.listaCompetenze = new LinkedHashSet<Competenza>(listaComp);
			txt_competenza.setValue(competenza);
			
			//lista dei temi ossia dei settori
			AdminCommand commandTemi = new TemiCommand();
		
			List<Tema> tmpList = new ArrayList<Tema>(commandTemi.list());
			Collections.sort(tmpList, ComparatorFactory.getInstance().retrieve("Tema"));
			this.listaTemi = tmpList;
			this.temaSelezionato = null;
			this.specializzazioneSelezionata = null;
			this.txt_altro.setText(null);
			this.txt_altro.setVisible(false);
			this.lblAltro.setVisible(false);
			this.chk_principale.setChecked(true);
			this.chk_secondario.setChecked(false);
			
			if (isUpdate){
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initSpecializzazioni(){
		this.cmb_specializzazioni.getChildren().clear();
		
		if (this.temaSelezionato!=null){
			ITemiSpecializzazioniDAO tDAO = new TemiSpecializzazioniDAO();
			List<Specializzazione> list = tDAO.getSpecializzazioniByTema(this.temaSelezionato.getId());
			for (Specializzazione spec : list){
				Comboitem item = new Comboitem();
				item.setValue(spec); 
				item.setLabel(spec.getNome());
				this.cmb_specializzazioni.appendChild(item);
			}
		}
		
		Specializzazione specSelezionata = null;
		if(this.cmb_specializzazioni.getItemCount()>0){
			this.cmb_specializzazioni.setSelectedIndex(0);
			if (this.cmb_specializzazioni.getSelectedItem().getValue()!=null){
				specSelezionata = (Specializzazione) this.cmb_specializzazioni.getSelectedItem().getValue();
			}
		}
		
		this.setSpecializzazioneSelezionata(specSelezionata);
	}
	
	public void onClick$bnt_aggiungi_competenza(Event event){
		if (this.temaSelezionato==null || this.specializzazioneSelezionata==null){
			throw new WrongValueException("Selezionare un Settore ed una Specializzazione");
		}
		
		String altro =this.txt_altro.getText().trim();
		if (this.specializzazioneSelezionata.getNome().equals("Altro") && altro.equals("")){
			throw new WrongValueException("Per la Specializzazione 'Altro' è necessario valorizzare l'apposito campo");
		}
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onAggiungiCompetenze",event.getTarget(),null);
	}
	
	public void onAggiungiCompetenze(Event event){
		Competenza competenza = new Competenza();
		try{
			String altro = this.txt_altro.getText().trim();
			competenza.setEsperto(this.esperto);
			competenza.setPrincipale(this.chk_principale.isChecked());
			competenza.setSpecializzazione(this.specializzazioneSelezionata);
			if (this.specializzazioneSelezionata.getNome().equals("Altro")){
				competenza.setAltro(altro);
			}
			this.listaCompetenze.add(competenza);
			SalvaCompetenzeCommand scc = new SalvaCompetenzeCommand(this.esperto.getId(),this.listaCompetenze);
			scc.execute();
			Messagebox.show("Salvataggio effettuato.","Info", Messagebox.OK, Messagebox.INFORMATION);
		}catch(Exception ex){
			if (this.listaCompetenze.contains(competenza)){
				this.listaCompetenze.remove(competenza);
			}
			throw new WrongValueException("Errore nel salvataggio dei dati: " + ex.getMessage());
		}finally{
			try {
				Clients.clearBusy();
				// reset dei campi a video
				txt_altro.setValue(" ");
				this.initModel(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void onVerificaRimuoviCompetenze(ForwardEvent event){
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			final Competenza competenza = (Competenza)rigaAttiva.getValue();
			Messagebox.show("Procedere con l'eliminazione della competenza selezionata?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
			        new org.zkoss.zk.ui.event.EventListener() {
			            public void onEvent(Event event) throws InterruptedException {
			                if (event.getName().equals("onOK")) {	                	
			                	Clients.showBusy("validazione in corso... ");
			            		Events.echoEvent("onRimuoviCompetenze",Path.getComponent("/win_competenze"),competenza);
			                } else {
			                    
			                }
			            }
			        });
		}catch(WrongValueException ex){
			throw ex;
		}catch(Exception ex){
			
		}
	}
	
	public void onRimuoviCompetenze(Event event){	
		boolean esito = false;
		try{
			Competenza competenzaSelezionata = (Competenza) event.getData();
			ICommand command = new CancellaCompetenzaCommand(competenzaSelezionata.getId());
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
	
	private void check() throws WrongValueException{
		if(this.txt_competenza.getValue().isEmpty()){
			throw new WrongValueException("Competenze principali obbligatorio" );
		}
	}
	
	
	public void onClick$bnt_salva_competenza(Event event) {
		check();
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onSalvaCompetenza",event.getTarget(),null);
	}
	
	public void onSalvaCompetenza(Event event){
		boolean esito = false;
		try {
			ModificaEspertoCommand mc = new ModificaEspertoCommand(this.esperto);
			esito = mc.salvaCompetenzaEsperto(txt_competenza.getValue());
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
			competenza = this.esperto.getCompetenza();
		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
	}
}