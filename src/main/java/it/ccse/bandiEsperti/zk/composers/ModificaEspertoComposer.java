package it.ccse.bandiEsperti.zk.composers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

import it.ccse.bandiEsperti.business.command.AdminCommand;
import it.ccse.bandiEsperti.business.command.CittaCommand;
import it.ccse.bandiEsperti.business.command.EspertoCommand;
import it.ccse.bandiEsperti.business.command.LoginCommand;
import it.ccse.bandiEsperti.business.command.ModificaEspertoCommand;
import it.ccse.bandiEsperti.business.command.PaesiCommand;
import it.ccse.bandiEsperti.business.command.ProvincieCommand;
import it.ccse.bandiEsperti.business.command.RegioniCommand;
import it.ccse.bandiEsperti.business.model.Citta;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Paesi;
import it.ccse.bandiEsperti.business.model.Provincie;
import it.ccse.bandiEsperti.business.model.Regioni;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;
import it.ccse.bandiEsperti.zk.comparators.PaesiComparator;
import it.ccse.bandiEsperti.zk.comparators.RegioniComparator;

public class ModificaEspertoComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -7732338175158044791L;
	private static final Logger log = Logger.getLogger(ModificaEspertoComposer.class);

	private Textbox txt_email;
	private Textbox txt_nome;
	private Textbox txt_cognome;
	private Textbox txt_cf;
	private Textbox txt_pi;
	private Textbox txt_telefono;
	private Textbox txt_cellulare;
	private Textbox txt_domicilio;
	private Textbox txt_indirizzo;
	private Textbox txt_cap;	
	private Datebox dta_data_nascita;
	private Combobox cmb_paesi;
	private Combobox cmb_regioni;
	private Combobox cmb_provincie;
	private Combobox cmb_citta;
	
	private Combobox cmb_paesi_nascita;
	private Combobox cmb_regioni_nascita;
	private Combobox cmb_provincie_nascita;
	private Combobox cmb_citta_nascita;
	
	private Label lbl_regione;
	private Label lbl_regione_mand;
	private Label lbl_provincia;
	private Label lbl_provincia_mand;
	private Label lbl_citta;
	private Label lbl_citta_mand;
	
	private Label lbl_regione_res;
	private Label lbl_regione_res_mand;
	private Label lbl_provincia_res;
	private Label lbl_provincia_res_mand;
	private Label lbl_citta_res;
	private Label lbl_citta_res_mand;
	
	private Label lbl_citta_nascita_estera;
	private Label lbl_citta_nascita_estera_mand;
	private Textbox txt_citta_nascita_estera;
	
	private Label lbl_citta_res_estera;
	private Label lbl_citta_res_estera_mand;
	private Textbox txt_citta_res_estera;
	
	
	private Esperto esperto;
	
	private Set<Paesi> listaPaesi = new LinkedHashSet<Paesi>();
	private Set<Regioni> listaRegioni = new LinkedHashSet<Regioni>();
	private Set<Provincie> listaProvincie = new LinkedHashSet<Provincie>();
	private Set<Citta> listaCitta = new LinkedHashSet<Citta>();
	
	private Set<Paesi> listaPaesiNascita = new LinkedHashSet<Paesi>();
	private Set<Regioni> listaRegioniNascita = new LinkedHashSet<Regioni>();
	private Set<Provincie> listaProvincieNascita = new LinkedHashSet<Provincie>();
	private Set<Citta> listaCittaNascita = new LinkedHashSet<Citta>();
	private Window win_modifica;
	
	// ********************************
	// ad utilizzo del binder
	// ********************************
	public Esperto getEsperto() {
		return esperto;
	}
	// ********************************

	
	public void doAfterCompose(Component win) throws Exception {
		super.doAfterCompose(win);
		Esperto user = (Esperto) session.getAttribute("login");
		Execution execution = Executions.getCurrent();
		if (user != null)
		{
			if (!user.getAdmin())
			{	
				if(user.getInviato()) {
					String redirectUrlInviato = "/pages/BandiEspertiPages/homeEspertoInviato.zul";
					execution.sendRedirect(redirectUrlInviato);
				}
			}
			else //Utente ammministratore
			{
				String redirectUrlAdmin = "/pages/BandiEspertiPages/welcomePageAdmin.zul";
				execution.sendRedirect(redirectUrlAdmin);
			}
		}
		else //Utente non loggato
		{
			String redirectUrlLogin = "/pages/BandiEspertiPages/login.zul";
			execution.sendRedirect(redirectUrlLogin);
		}
		this.initModel(false);
	}
	
	private void selectRegioni(Paesi paesi, boolean nascita){
		this.esperto = (Esperto) session.getAttribute("login");
		RegioniCommand regioniCommand = new RegioniCommand();
		
		List<Regioni> lista = new ArrayList<Regioni>(regioniCommand.listRegioni(paesi));
		Collections.sort(lista, new RegioniComparator());
		if(!nascita){
			listaRegioni.addAll(lista);
		}else{
			listaRegioniNascita.addAll(lista);
		}
		
		for (Regioni regione : lista){
			Comboitem itemRegione = new Comboitem();
			itemRegione.setValue(regione.getCodiceRegione().toString()); 
			itemRegione.setLabel(regione.getDenominazione());
			if(!nascita){
				this.cmb_regioni.appendChild(itemRegione);
			}else{
				this.cmb_regioni_nascita.appendChild(itemRegione);
			}
		}
		
	}
	
	
	
	
	private void selectProvincie(String codiceRegione, boolean nascita){
		
		ProvincieCommand provincieCommand = new ProvincieCommand();
		List<Provincie> lista = new ArrayList<Provincie>(provincieCommand.findByCodiceRegione(codiceRegione));
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
		
		if(!nascita){
			listaProvincie.addAll(lista);
		}else{
			listaProvincieNascita.addAll(lista);
		}
		
		for (Provincie provincie : lista){
			Comboitem itemProvincie = new Comboitem();
			itemProvincie.setValue(provincie.getCodiceProvincia().toString()); 
			itemProvincie.setLabel(provincie.getDenominazione());
			itemProvincie.setStyle("tahoma,arial,sans-serif;font-size:8pt;");
			if(!nascita){
				this.cmb_provincie.appendChild(itemProvincie);
			}else{
				this.cmb_provincie_nascita.appendChild(itemProvincie);
			}
		}
	}
	
	private void selectCitta(String codiceProvincia, boolean nascita){
	
		CittaCommand cittaCommand = new CittaCommand();
		List<Citta> lista = new ArrayList<Citta>(cittaCommand.findByCodiceProvincia(codiceProvincia));
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
		listaCitta.addAll(lista);
		if(!nascita){
			listaCitta.addAll(lista);
		}else{
			listaCittaNascita.addAll(lista);
		}
		for (Citta citta : lista){
			Comboitem itemCitta = new Comboitem();	
			itemCitta.setValue(citta.getId()); 
			itemCitta.setLabel(citta.getDenominazione());
			if(!nascita){
				this.cmb_citta.appendChild(itemCitta);
			}else{
				this.cmb_citta_nascita.appendChild(itemCitta);
			}
		}
	}
	
	private void setVisibleFieldsStato(String stato){
		
		if(stato.equals("Italia")){
			this.lbl_regione.setVisible(true);
			this.lbl_regione_mand.setVisible(true);
			this.cmb_regioni_nascita.setVisible(true);
			this.lbl_provincia.setVisible(true);
			this.lbl_provincia_mand.setVisible(true);
			this.cmb_provincie_nascita.setVisible(true);
			this.lbl_citta.setVisible(true);
			this.lbl_citta_mand.setVisible(true);
			this.cmb_citta_nascita.setVisible(true);
			this.lbl_citta_nascita_estera.setVisible(false);
			this.lbl_citta_nascita_estera_mand.setVisible(false);
			this.txt_citta_nascita_estera.setVisible(false);
		}else{
			this.lbl_regione.setVisible(false);
			this.lbl_regione_mand.setVisible(false);
			this.cmb_regioni_nascita.setVisible(false);
			this.lbl_provincia.setVisible(false);
			this.lbl_provincia_mand.setVisible(false);
			this.cmb_provincie_nascita.setVisible(false);
			this.lbl_citta.setVisible(false);
			this.lbl_citta_mand.setVisible(false);
			this.cmb_citta_nascita.setVisible(false);
			this.lbl_citta_nascita_estera.setVisible(true);
			this.lbl_citta_nascita_estera_mand.setVisible(true);
			this.txt_citta_nascita_estera.setVisible(true);
		}
	}
	
	private void setVisibleFieldsStatoRes(String stato){
		
		if(stato.equals("Italia")){
			this.lbl_regione_res.setVisible(true);
			this.lbl_regione_res_mand.setVisible(true);
			this.cmb_regioni.setVisible(true);
			this.lbl_provincia_res.setVisible(true);
			this.lbl_provincia_res_mand.setVisible(true);
			this.cmb_provincie.setVisible(true);
			this.lbl_citta_res.setVisible(true);
			this.lbl_citta_res_mand.setVisible(true);
			this.cmb_citta.setVisible(true);
			this.lbl_citta_res_estera.setVisible(false);
			this.lbl_citta_res_estera_mand.setVisible(false);
			this.txt_citta_res_estera.setVisible(false);
		}else{
			this.lbl_regione_res.setVisible(false);
			this.lbl_regione_res_mand.setVisible(false);
			this.cmb_regioni.setVisible(false);
			this.lbl_provincia_res.setVisible(false);
			this.lbl_provincia_res_mand.setVisible(false);
			this.cmb_provincie.setVisible(false);
			this.lbl_citta_res.setVisible(false);
			this.lbl_citta_res_mand.setVisible(false);
			this.cmb_citta.setVisible(false);
			this.lbl_citta_res_estera.setVisible(true);
			this.lbl_citta_res_estera_mand.setVisible(true);
			this.txt_citta_res_estera.setVisible(true);
		}
	}
	
	
	
	public void onSelect$cmb_paesi(SelectEvent evt) throws Exception {
		Comboitem item = new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		listaRegioni.clear();
		this.cmb_regioni.setText("");
		this.cmb_regioni.setSelectedIndex(-1);
		this.cmb_regioni.getChildren().clear();
		
		this.cmb_provincie.setText("");
		this.cmb_provincie.setSelectedIndex(-1);
		this.cmb_provincie.getChildren().clear();
		
		this.cmb_citta.setText("");
		this.cmb_citta.setSelectedIndex(-1);
		this.cmb_citta.getChildren().clear();
		PaesiCommand paesiCommand = new PaesiCommand(this.esperto);
		Paesi paesi = (Paesi)paesiCommand.findCodicePaese((String)item.getValue());
		
		setVisibleFieldsStatoRes(paesi.getDenominazione());
		
		selectRegioni(paesi, false);
	}	
	
	public void onSelect$cmb_paesi_nascita(SelectEvent evt) throws Exception {
		Comboitem item = new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		listaRegioniNascita.clear();
		this.cmb_regioni_nascita.setText("");
		this.cmb_regioni_nascita.setSelectedIndex(-1);
		this.cmb_regioni_nascita.getChildren().clear();
		
		this.cmb_provincie_nascita.setText("");
		this.cmb_provincie_nascita.setSelectedIndex(-1);
		this.cmb_provincie_nascita.getChildren().clear();
	
		this.cmb_citta_nascita.setText("");
		this.cmb_citta_nascita.setSelectedIndex(-1);
		this.cmb_citta_nascita.getChildren().clear();
		PaesiCommand paesiCommand = new PaesiCommand(this.esperto);
		Paesi paesi = (Paesi)paesiCommand.findCodicePaese((String)item.getValue());
		
		setVisibleFieldsStato(paesi.getDenominazione());
		
		selectRegioni(paesi, true);
		
		
		
	}	

	public void onSelect$cmb_regioni(SelectEvent evt) throws Exception {
		Comboitem item = (Comboitem) new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		listaProvincie.clear();
		this.cmb_provincie.setText("");
		this.cmb_provincie.setSelectedIndex(-1);
		this.cmb_provincie.getChildren().clear();
		this.cmb_citta.setText("");
		this.cmb_citta.setSelectedIndex(-1);
		this.cmb_citta.getChildren().clear();
		selectProvincie(item.getValue().toString(), false);
	
	}
	
	public void onSelect$cmb_regioni_nascita(SelectEvent evt) throws Exception {
		Comboitem item = (Comboitem) new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		listaProvincieNascita.clear();
		this.cmb_provincie_nascita.setText("");
		this.cmb_provincie_nascita.setSelectedIndex(-1);
		this.cmb_provincie_nascita.getChildren().clear();
		this.cmb_citta_nascita.setText("");
		this.cmb_citta_nascita.setSelectedIndex(-1);
		this.cmb_citta_nascita.getChildren().clear();
		selectProvincie(item.getValue().toString(), true);
	
	}
	
	public void onSelect$cmb_provincie(SelectEvent evt) throws Exception {
		Comboitem item = new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		listaCitta.clear();
		this.cmb_citta.setSelectedIndex(-1);
		this.cmb_citta.getChildren().clear();
		selectCitta(item.getValue().toString(), false);
		
	}
	
	public void onSelect$cmb_provincie_nascita(SelectEvent evt) throws Exception {
		Comboitem item = new ArrayList<Comboitem>(evt.getSelectedItems()).get(0);
		listaCittaNascita.clear();
		this.cmb_citta_nascita.setSelectedIndex(-1);
		this.cmb_citta_nascita.getChildren().clear();
		selectCitta(item.getValue().toString(), true);
		
	}
	
	
	public void initModel(boolean isUpdate) throws Exception{
		try{
			this.esperto = (Esperto) session.getAttribute("login");
		
			Esperto espertoDb  = getEsperto();
			
			if(this.cmb_paesi!=null){
				PaesiCommand paesiCommand = new PaesiCommand(this.esperto);
				paesiCommand.execute();
			
				List<Paesi> lista = new ArrayList<Paesi>(paesiCommand.getSet());
				Collections.sort(lista, new PaesiComparator());
				
				listaPaesi.addAll(lista);
				listaPaesiNascita.addAll(lista);
				
				
				if(espertoDb.getPaesi()!=null){
					
					selectRegioni(espertoDb.getPaesi(), false);
				}
				if(espertoDb.getRegioni()!=null){
					selectProvincie(espertoDb.getRegioni().getCodiceRegione(), false);
				}
				if(this.esperto.getProvincie()!=null){
					selectCitta(this.esperto.getProvincie().getCodiceProvincia(), false);
				}
				
				if(espertoDb.getPaesiNascita()!=null){
					selectRegioni(espertoDb.getPaesiNascita(), true);
				}
				if(espertoDb.getRegioniNascita()!=null){
					selectProvincie(espertoDb.getRegioniNascita().getCodiceRegione(), true);
				}
				if(espertoDb.getProvincieNascita()!=null){
					selectCitta(espertoDb.getProvincieNascita().getCodiceProvincia(), true);
				}
				
			}
			
			
			if(this.cmb_paesi_nascita!=null){
				if(espertoDb!=null && espertoDb.getPaesiNascita()!=null){
					  setVisibleFieldsStato(espertoDb.getPaesiNascita().getDenominazione());
				  }
				
			}
			
			if(this.cmb_paesi!=null){
				if(espertoDb!=null && espertoDb.getPaesi()!=null){
					  setVisibleFieldsStatoRes(espertoDb.getPaesi().getDenominazione());
				  }
				
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Set<Paesi> getListaPaesi() {
		return listaPaesi;
	}
	public Set<Regioni> getListaRegioni() {
		return listaRegioni;
	}
	public Set<Provincie> getListaProvincie() {
		return listaProvincie;
	}
	
	public Set<Citta> getListaCitta() {
		return listaCitta;
	}

	public Set<Paesi> getListaPaesiNascita() {
		return listaPaesiNascita;
	}
	public Set<Regioni> getListaRegioniNascita() {
		return listaRegioniNascita;
	}
	public Set<Provincie> getListaProvincieNascita() {
		return listaProvincieNascita;
	}
	
	public Set<Citta> getListaCittaNascita() {
		return listaCittaNascita;
	}
	private void checkIsValid(Component component) {
		if (component instanceof InputElement) {
                if (!((InputElement) component).isValid()) {
                  // Force show errorMessage
                  ((InputElement) component).getText();
                }
		}
	}
	
	private void check(Component component) {
		checkIsValid(component);
        List<Component> children = component.getChildren();
        for (Component each: children) {
        	check(each);
        }
        
        //Controllo Dati obbligatori
        if(cmb_paesi.getValue()!=null){
			
			//STATO ITALIA REGIONE, PROVINCIA E CITTA OBBLIGATORIA
			if(cmb_paesi.getValue().toString().equals("Italia")){
				
				Comboitem comboItemRegione = cmb_regioni.getSelectedItem();
				if(comboItemRegione == null){
					throw new WrongValueException("Regione di Residenza obbligatoria");
				}
				
				Comboitem comboItemProvincia = cmb_provincie.getSelectedItem();
				
				if(comboItemProvincia == null){
					throw new WrongValueException("Provincia di Residenza obbligatoria");
				}
				
				Comboitem comboItemCitta = cmb_citta.getSelectedItem();
				
				if(comboItemCitta == null){
					throw new WrongValueException("Città di Residenza obbligatoria");
				}
			}else{
				if(txt_citta_res_estera.getValue().isEmpty()){
					throw new WrongValueException("Città di Residenza obbligatoria");
				}
			}
		}
        
        if(cmb_paesi_nascita.getValue()!=null){
			
			//STATO DI NASCITA ITALIA REGIONE, PROVINCIA E CITTA OBBLIGATORIA
			if(cmb_paesi_nascita.getValue().toString().equals("Italia")){
				
				Comboitem comboItemRegioneNascita = cmb_regioni_nascita.getSelectedItem();
				
				if(comboItemRegioneNascita == null){
					throw new WrongValueException("Regione di Nascita obbligatoria");
					
				}
				
				Comboitem comboItemProvinciaNascita = cmb_provincie_nascita.getSelectedItem();
				
				if(comboItemProvinciaNascita == null){
					throw new WrongValueException("Provincia di Nascita obbligatoria");
					
				}
				
				Comboitem comboItemCittaNascita = cmb_citta_nascita.getSelectedItem();
				
				if(comboItemCittaNascita == null){
					throw new WrongValueException("Città di Nascita obbligatoria");
					
				}
			}else{
				if(txt_citta_nascita_estera.getValue().isEmpty()){
					throw new WrongValueException("Città di Nascita obbligatoria");
				}
			}
				
		}
	}
    
	
	public void onClick$btn_salva(Event event) throws Exception {
		check(win_modifica);
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onSalvaEsperto",event.getTarget(),null);
	}

	public void onSalvaEsperto(Event evt) throws Exception {
		try{
			Esperto e = new Esperto();
			
			//recupera i campi chiave dall'utente in sessione
			e.setUsername(this.esperto.getUsername());
			e.setPassword(this.esperto.getPassword());
			//e.setId(this.esperto.getId());
			e.setCodiceDomanda(this.esperto.getCodiceDomanda());
			e.setInviato(this.esperto.getInviato());
			e.setPrivacy(this.esperto.getPrivacy());

			//recupera le info dalla form di modifica
			
			
			Comboitem comboItemPaese = cmb_paesi.getSelectedItem();
			if(comboItemPaese!=null && cmb_paesi.getValue()!=null){
				PaesiCommand paesiCommand = new PaesiCommand(this.esperto);
				Paesi paese = paesiCommand.findCodicePaese(comboItemPaese.getValue().toString());
				e.setPaesi(paese);
			}
			
			Comboitem comboItemPaeseNascita = cmb_paesi_nascita.getSelectedItem();
			if(comboItemPaeseNascita!=null && cmb_paesi_nascita.getValue()!=null){
				PaesiCommand paesiCommand = new PaesiCommand(this.esperto);
				Paesi paese = paesiCommand.findCodicePaese(comboItemPaeseNascita.getValue().toString());
				e.setPaesiNascita(paese);
				
				
			}
			
			Comboitem comboItemRegione = cmb_regioni.getSelectedItem();
			
			
				
			if(comboItemRegione!=null && comboItemRegione.getValue()!=null){
				RegioniCommand regionCommand = new RegioniCommand();
				Regioni regione = regionCommand.findCodiceRegione(comboItemRegione.getValue().toString());
				
				e.setRegioni(regione);
			}
			
			Comboitem comboItemRegioneNascita = cmb_regioni_nascita.getSelectedItem();
			if(comboItemRegioneNascita!=null && comboItemRegioneNascita.getValue()!=null){
				RegioniCommand regionCommand = new RegioniCommand();
				Regioni regione = regionCommand.findCodiceRegione(comboItemRegioneNascita.getValue().toString());
				e.setRegioniNascita(regione);
			}
			
			Comboitem comboItemProvincia = cmb_provincie.getSelectedItem();
			
			if(comboItemProvincia!=null && comboItemProvincia.getValue()!=null){
				ProvincieCommand provincieCommand = new ProvincieCommand();
				Provincie provincie = provincieCommand.findCodiceProvincia(comboItemProvincia.getValue().toString());
			
				e.setProvincie(provincie);
			}
			
			Comboitem comboItemProvinciaNascita = cmb_provincie_nascita.getSelectedItem();
			
			if(comboItemProvinciaNascita!=null && comboItemProvinciaNascita.getValue()!=null){
				ProvincieCommand provincieCommand = new ProvincieCommand();
				Provincie provincie = provincieCommand.findCodiceProvincia(comboItemProvinciaNascita.getValue().toString());
			
				e.setProvincieNascita(provincie);
			}
			
			Comboitem comboItemCitta = cmb_citta.getSelectedItem();
			
			if(comboItemCitta!=null &&  comboItemCitta.getValue()!=null){
				AdminCommand cittaCommand = new CittaCommand();
				Citta citta = (Citta) cittaCommand.findById(Integer.parseInt(comboItemCitta.getValue().toString()));
				e.setCitta(citta);
			}
			
			Comboitem comboItemCittaNascita = cmb_citta_nascita.getSelectedItem();
			
			if(comboItemCittaNascita!=null &&  comboItemCittaNascita.getValue()!=null){
				AdminCommand cittaCommand = new CittaCommand();
				Citta citta = (Citta) cittaCommand.findById(Integer.parseInt(comboItemCittaNascita.getValue().toString()));
				e.setCittaNascita(citta);
			}
			
			
			
			e.setEmail(txt_email.getValue());
			e.setNome(txt_nome.getValue());
			e.setCognome(txt_cognome.getValue());
			e.setCf(txt_cf.getValue());
			e.setPiva(txt_pi.getValue());
			e.setTel(txt_telefono.getValue());
			e.setCel(txt_cellulare.getValue());

			//e.setFax(txt_fax.getValue()!=null?txt_fax.getValue():null);
			e.setDomicilio(txt_domicilio.getValue()!=null?txt_domicilio.getValue():null);
			e.setResidenza(txt_indirizzo.getValue()!=null?txt_indirizzo.getValue():null);
			e.setDataNascita(dta_data_nascita.getValue()!=null?dta_data_nascita.getValue():null);
			e.setCap(txt_cap.getValue()!=null?txt_cap.getValue():null);
			e.setCittaEstera(txt_citta_res_estera.getValue()!=null?txt_citta_res_estera.getValue():null);
			e.setCittaNascitaEstera(txt_citta_nascita_estera.getValue()!=null?txt_citta_nascita_estera.getValue():null);
			
			ModificaEspertoCommand mc = new ModificaEspertoCommand(e);
			boolean esitoModifica = mc.execute();
		
			log.debug("Esito Modifica Esperto: "+esitoModifica);

			if(!esitoModifica){
				throw new WrongValueException("Errore durante il salvataggio: I dati NON sono stati inseriti correttamente");
			}else{
				//aggiornare i dati dell'oggetto in sessione
				LoginCommand lc = new LoginCommand(this.esperto.getCf(), this.esperto.getPassword());
				lc.execute();
				session.setAttribute("login", lc.getE());
				//forza il refresh del layout
			
			}

		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
			try {

				//aggiorna il model (rilegge da db)
				this.initModel(true);
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
				//Messagebox.show("Salvataggio preso in consegna dal sistema. Al più presto verrà aggiornato.","Info", Messagebox.OK, Messagebox.INFORMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
