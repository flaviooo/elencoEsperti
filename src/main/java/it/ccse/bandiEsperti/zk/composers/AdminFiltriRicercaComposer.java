package it.ccse.bandiEsperti.zk.composers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.ccse.bandiEsperti.business.dao.ITemiSpecializzazioniDAO;
import it.ccse.bandiEsperti.business.dao.TemiSpecializzazioniDAO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO.TipoFiltro;
import it.ccse.bandiEsperti.business.model.Specializzazione;
import it.ccse.bandiEsperti.business.model.Tema;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

public class AdminFiltriRicercaComposer extends GenericForwardComposer {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(AdminFiltriRicercaComposer.class);
	
	private FiltroRicercaEspertiDTO filtroRicerca;
	
	private Map<Tema, List<Specializzazione>> temiSpecializzazioni;
	
	private Checkbox chkPrecedentiIncarichiRds;
	private Checkbox chkPrecedentiIncarichiAltro;
	private Checkbox chkPubblicazioni;
	private Textbox txtCognome;
	private Datebox dtNatiEntro;
	private Checkbox chkInCorso;
	private Datebox dtAPartireDa;
	private Textbox txtDatoreAltro;
	private Tabbox tbbCompetenze;
	private Radiogroup rdgEsperienze;
	private Radio r1;
	private Radio r2;
	private Checkbox chkFiltraDettagliCompetenza;
	private Checkbox chkFiltraDettagliEsperienze;
	
	public void doAfterCompose(Component win) throws Exception {
		super.doAfterCompose(win);
		
		try{
			this.filtroRicerca = (FiltroRicercaEspertiDTO)arg.get("filtroRicerca");
			this.initModel(false);
			this.initWidgets(false);
		} catch (Exception e) {
			log.error(e.getMessage());
			this.self.detach();
		}
	}

	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		try{
			this.temiSpecializzazioni = new LinkedHashMap<Tema, List<Specializzazione>>();
			
			ITemiSpecializzazioniDAO temiSpecializzazioniDao = new TemiSpecializzazioniDAO();
			List<Tema> listaTemi = temiSpecializzazioniDao.getListaTemi();
			
			for(Tema tema : listaTemi){
				List<Specializzazione> specializzazioni = temiSpecializzazioniDao.getSpecializzazioniByTema(tema.getId());
				
				this.temiSpecializzazioni.put(tema, specializzazioni);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new WrongValueException("Si è verificato un errore nella inizializzazione della pagina");
		}
	}
	
	public void initWidgets(boolean isResetMode){
		this.chkPrecedentiIncarichiRds.setChecked((Boolean)this.filtroRicerca.getPrecedentiIncarichi().get(TipoFiltro.INCARICHIPRESENTIRDS));
		this.chkPrecedentiIncarichiAltro.setChecked((Boolean)this.filtroRicerca.getPrecedentiIncarichi().get(TipoFiltro.INCARICHIPRESENTIALTRO));
		
		this.chkPubblicazioni.setChecked((Boolean)this.filtroRicerca.getPubblicazioni().get(TipoFiltro.PUBBLICAZIONIPRESENTI));

		this.txtCognome.setText("");
		if(this.filtroRicerca.getDatiAnagrafici().get(TipoFiltro.COGNOME) != null){
			this.txtCognome.setText((String)this.filtroRicerca.getDatiAnagrafici().get(TipoFiltro.COGNOME));
		}
		
		this.dtNatiEntro.setValue(null);
		if(this.filtroRicerca.getDatiAnagrafici().get(TipoFiltro.DATALIMITE) != null){
			this.dtNatiEntro.setValue((Date)this.filtroRicerca.getDatiAnagrafici().get(TipoFiltro.DATALIMITE));
		}
		
		this.r1.setChecked(true);
		this.chkInCorso.setChecked((Boolean)this.filtroRicerca.getEsperienze().get(TipoFiltro.INCORSO));
		
		if(this.filtroRicerca.getEsperienze().get(TipoFiltro.APARTIREDA) != null){
			this.dtAPartireDa.setValue((Date)this.filtroRicerca.getEsperienze().get(TipoFiltro.APARTIREDA));
			this.r2.setChecked(true);
		}
		
		this.onCheckEsperienza();
		
		if(this.filtroRicerca.getEsperienze().get(TipoFiltro.DATORELAVORO)!= null){
			List<String> datori = (List<String>)this.filtroRicerca.getEsperienze().get(TipoFiltro.DATORELAVORO);
			for(String datore : datori){
				if(datore.length() > 0){
					this.txtDatoreAltro.setText(datore);
				}
			}
		}
		this.chkFiltraDettagliEsperienze.setChecked((Boolean)this.filtroRicerca.getEsperienze().get(TipoFiltro.FILTRADETTAGLIESPERIENZE));
		
		if(!isResetMode){
			for(Entry<Tema, List<Specializzazione>> entry : this.temiSpecializzazioni.entrySet()){
				String nomeTema = ((Tema)entry.getKey()).getNome();
	
				Tab tb = new Tab();
				tb.setLabel(nomeTema);
				
				Vlayout vlayout = new Vlayout();
				vlayout.setWidth("100%");
				vlayout.setStyle("text-align:left;");
				
				Tabpanel tabpanel = new Tabpanel();
				tabpanel.appendChild(vlayout);
				tabpanel.setId("Tema_"+((Tema)entry.getKey()).getId());
				
				for(Specializzazione specializzazione : (List<Specializzazione>)entry.getValue()){
					Checkbox chk = new Checkbox();
					chk.setId("Spec_"+specializzazione.getId());
					chk.setLabel(specializzazione.getNome());
					
					vlayout.appendChild(chk);
				}
				
				this.tbbCompetenze.getTabs().appendChild(tb);
				this.tbbCompetenze.getTabpanels().appendChild(tabpanel);
			}
		}else{
			for(Entry<Tema, List<Specializzazione>> entry : this.temiSpecializzazioni.entrySet()){
				for(Specializzazione specializzazione : (List<Specializzazione>)entry.getValue()){
					String chiave = "Spec_"+specializzazione.getId();
					((Checkbox)Path.getComponent("/win_welcome_admin/win_admin_filtri_ricerca/"+chiave)).setChecked(false);
//					((Tabpanel)((Checkbox)Path.getComponent("/win_welcome_admin/win_admin_filtri_ricerca/"+chiave)).getParent().getParent()).getLinkedTab().setSclass("z-tab;");
//					((Tabpanel)((Checkbox)Path.getComponent("/win_welcome_admin/win_admin_filtri_ricerca/"+chiave)).getParent().getParent()).getLinkedTab().invalidate();
				}
			}
		}
		
		if(this.filtroRicerca.getCompetenze().get(TipoFiltro.LISTACOMPETENZE) != null){
			List<Specializzazione> specializzazioni = (List<Specializzazione>)this.filtroRicerca.getCompetenze().get(TipoFiltro.LISTACOMPETENZE);
			
			for(Specializzazione specializzazione : specializzazioni){
				String chiave = "Spec_"+specializzazione.getId();
				((Checkbox)Path.getComponent("/win_welcome_admin/win_admin_filtri_ricerca/"+chiave)).setChecked(true);
//				((Tabpanel)((Checkbox)Path.getComponent("/win_welcome_admin/win_admin_filtri_ricerca/"+chiave)).getParent().getParent()).getLinkedTab().setSclass("z-tab;");
//				((Tabpanel)((Checkbox)Path.getComponent("/win_welcome_admin/win_admin_filtri_ricerca/"+chiave)).getParent().getParent()).getLinkedTab().invalidate();
			}
		}
		
		chkFiltraDettagliCompetenza.setChecked((Boolean)this.filtroRicerca.getCompetenze().get(TipoFiltro.FILTRADETTAGLICOMPETENZE));
	}
	
	public FiltroRicercaEspertiDTO getFiltroRicerca() {
		return filtroRicerca;
	}

	public void setFiltroRicerca(FiltroRicercaEspertiDTO filtroRicerca) {
		this.filtroRicerca = filtroRicerca;
	}
	
	/** Evento per l'aggiornamento dei dati modificati */
	public void onClick$btnApplicaFiltri(Event event) {
		Clients.showBusy("Applicazione Filtri in corso... ");
		Events.echoEvent("onApplicaFiltri",event.getTarget(),null);
	}
	
	public void onApplicaFiltri(Event event){
		try{
			//Incarichi
			Map<TipoFiltro, Object> filterIncarichi = this.filtroRicerca.getPrecedentiIncarichi();
			filterIncarichi.put(TipoFiltro.INCARICHIPRESENTIRDS, new Boolean(this.chkPrecedentiIncarichiRds.isChecked()));
			filterIncarichi.put(TipoFiltro.INCARICHIPRESENTIALTRO, new Boolean(this.chkPrecedentiIncarichiAltro.isChecked()));
			
			//Pubblicazioni
			Map<TipoFiltro, Object> filterPubblicazioni = this.filtroRicerca.getPubblicazioni();
			filterPubblicazioni.put(TipoFiltro.PUBBLICAZIONIPRESENTI, new Boolean(this.chkPubblicazioni.isChecked()));
	
			//Dati Anagrafici
			Map<TipoFiltro, Object> filterDatiAnagrafici = this.filtroRicerca.getDatiAnagrafici();
			filterDatiAnagrafici.put(TipoFiltro.COGNOME, null);
			if(!this.txtCognome.getText().trim().equals("")){
				filterDatiAnagrafici.put(TipoFiltro.COGNOME, this.txtCognome.getText().trim());
			}
			
			filterDatiAnagrafici.put(TipoFiltro.DATALIMITE, null);			
			if(this.dtNatiEntro.getValue() != null){
				filterDatiAnagrafici.put(TipoFiltro.DATALIMITE, this.dtNatiEntro.getValue());
			}
	
			//Esperienze
			Map<TipoFiltro, Object> filterEsperienze = this.filtroRicerca.getEsperienze();
			filterEsperienze.put(TipoFiltro.INCORSO, new Boolean(this.chkInCorso.isChecked()));
			
			filterEsperienze.put(TipoFiltro.APARTIREDA, null);
			if(this.dtAPartireDa.getValue() != null){
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
				Calendar dataInizio = new GregorianCalendar(
						Integer.parseInt(simpleDateformat.format(this.dtAPartireDa.getValue())), 
						Calendar.JANUARY,
						1);
				filterEsperienze.put(TipoFiltro.APARTIREDA, dataInizio.getTime());
			}
			
			List<String> datori = new ArrayList<String>();
			
			if(!this.txtDatoreAltro.getText().trim().equals("")){
				String strDatore = this.txtDatoreAltro.getText().trim();
				if (strDatore.equalsIgnoreCase("rse")){
					strDatore = strDatore.toUpperCase();
				};
				datori.add(strDatore);
				this.txtDatoreAltro.setText(strDatore);
			}
			
			filterEsperienze.put(TipoFiltro.DATORELAVORO, null);
			if(datori.size() >0){
				filterEsperienze.put(TipoFiltro.DATORELAVORO, datori);
			}
			
			filterEsperienze.put(TipoFiltro.FILTRADETTAGLIESPERIENZE, null);
			filterEsperienze.put(TipoFiltro.FILTRADETTAGLIESPERIENZE, new Boolean(this.chkFiltraDettagliEsperienze.isChecked()));
			
			//Competenze
			Map<TipoFiltro, Object> filterCompetenze = this.filtroRicerca.getCompetenze();
			filterCompetenze.put(TipoFiltro.LISTACOMPETENZE, null);
			List<Specializzazione> specializzazioniSelezionate = new ArrayList<Specializzazione>();
			
			for(Entry<Tema, List<Specializzazione>> entry : this.temiSpecializzazioni.entrySet()){				
				for(Specializzazione specializzazione : (List<Specializzazione>)entry.getValue()){
					String chiave = "Spec_"+specializzazione.getId();
					
					if(((Checkbox)Path.getComponent("/win_welcome_admin/win_admin_filtri_ricerca/"+chiave)).isChecked()){
						specializzazioniSelezionate.add(specializzazione);
					}
				}
			}
			
			if(specializzazioniSelezionate.size() > 0){
				filterCompetenze.put(TipoFiltro.LISTACOMPETENZE, specializzazioniSelezionate);
			}
			
			filterCompetenze.put(TipoFiltro.FILTRADETTAGLICOMPETENZE, null);
			filterCompetenze.put(TipoFiltro.FILTRADETTAGLICOMPETENZE, new Boolean(this.chkFiltraDettagliCompetenza.isChecked()));
			
			//rinfresco dei filtri
			this.filtroRicerca.setPrecedentiIncarichi(filterIncarichi);
			this.filtroRicerca.setPubblicazioni(filterPubblicazioni);
			this.filtroRicerca.setDatiAnagrafici(filterDatiAnagrafici);
			this.filtroRicerca.setEsperienze(filterEsperienze);
			this.filtroRicerca.setCompetenze(filterCompetenze);
	
			//aggiornamento UI
			AdminComposer ctrl = (AdminComposer) arg.get("parentController");
			ctrl.initModel(true);
			ctrl.initWidgets();
			
			AnnotateDataBinder binder = (AnnotateDataBinder) arg.get("binder");
			binder.loadAll();
			
			this.self.detach();
		}catch(Exception ex){
			log.error(ex.getMessage());
			throw new WrongValueException("Si è verificato un errore nella inizializzazione della pagina");
		}
		finally{
			Clients.clearBusy();
		}
	}
	
	public void onChiudiFinestra(ForwardEvent event){
		this.self.detach();
	}
	
	public void onResetFiltri(ForwardEvent event){
		this.filtroRicerca.initAll();
		this.initWidgets(true);
	}
	
	public void onCheckEsperienza(){
		int opzione = Integer.parseInt(rdgEsperienze.getSelectedItem().getValue());
		
		if (opzione==1){
			this.chkInCorso.setDisabled(false);
			this.dtAPartireDa.setDisabled(true);
			this.dtAPartireDa.setText(null);
		}else{
			this.chkInCorso.setDisabled(true);
			this.chkInCorso.setChecked(false);
			this.dtAPartireDa.setDisabled(false);
		} 
	}
}
