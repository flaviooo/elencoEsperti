package it.ccse.bandiEsperti.zk.view;

import it.ccse.bandiEsperti.business.command.AdminCommand;

import it.ccse.bandiEsperti.business.command.CampiRicercaCommand;
import it.ccse.bandiEsperti.business.command.CondizioniRicercaCommand;
import it.ccse.bandiEsperti.business.command.interfaces.IRicerca;
import it.ccse.bandiEsperti.business.model.AssCampiOperatori;
import it.ccse.bandiEsperti.business.model.CampiRicerca;
import it.ccse.bandiEsperti.business.model.CondizioniRicerca;

import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;

import org.zkoss.zul.Textbox;

public class BeRow extends Row{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Combobox campi;
	private Combobox operatori;
	private Combobox condizioni;
	private Listbox valoriC300;
	private Bandbox bandbox;
	private Bandpopup bandpopup;
	private Listbox cmb_listaLocalita;
	
	private Combobox valoriC100;
	private Textbox valoriT;
	private Button aggiungi;
	private Button rimuovi;
	private CampiRicerca campiRicercaSelezionato;
	private String operatoreRicercaSelezionato;
	private Integer localitaSelezionata;
	private CondizioniRicerca condizioneSelezionata;
	private String testoSelezionato;
	List<IRicerca>  lista = null;
	private Comboitem selectComboItemOperatore;
	private String testo; 
	private  int index;
	
	
	public void checkInsertAggiungi(){
		if(campi.getSelectedItem()!=null && operatori.getSelectedItem()!=null && condizioni.getSelectedItem()!=null && ( (valoriC300!=null && valoriC300.getSelectedItem()!=null) || (valoriC100!=null && valoriC100.getSelectedItem()!=null) || (valoriT!=null && valoriT.getValue()!=null &&  valoriT.getValue().trim().length()>0))){
			Button buttonAggiungi = new Button();
			setAggiungi(buttonAggiungi);
			if(!((BeRows)this.getParent()).isLast(this)){
				this.aggiungi.setDisabled(true);
			}
		}
		
	}
	
	public String getTesto() {
		return testo;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	
	public int getIndex() {
		return this.index;
	}
	

	public Listbox getValoriC300() {
		return valoriC300;
	}


	public Combobox getValoriC100() {
		return valoriC100;
	}


	public CampiRicerca getCampiRicercaSelezionato() {
		return campiRicercaSelezionato;
	}


	public void setCampiRicercaSelezionato(CampiRicerca campiRicercaSelezionato) {
		this.campiRicercaSelezionato = campiRicercaSelezionato;
	}


	public String getOperatoreRicercaSelezionato() {
		return operatoreRicercaSelezionato;
	}


	public void setOperatoreRicercaSelezionato(String operatoreRicercaSelezionato) {
		this.operatoreRicercaSelezionato = operatoreRicercaSelezionato;
	}


	public Integer getLocalitaSelezionata() {
		return localitaSelezionata;
	}


	public void setLocalitaSelezionata(Integer localitaSelezionata) {
		this.localitaSelezionata = localitaSelezionata;
	}


	public CondizioniRicerca getCondizioneSelezionata() {
		return condizioneSelezionata;
	}


	public void setCondizioneSelezionata(CondizioniRicerca condizioneSelezionata) {
		this.condizioneSelezionata = condizioneSelezionata;
	}


	public String getTestoSelezionato() {
		return testoSelezionato;
	}


	public void setTestoSelezionato(String testoSelezionato) {
		this.testoSelezionato = testoSelezionato;
	}

	public Combobox getCampi() {
		return campi;
	}
	
	
	
	private Listbox populateListaLocalitaMaggioreDi100(List<IRicerca>  listLocalita){
		cmb_listaLocalita= new Listbox();
		
		cmb_listaLocalita.setHeight("250px");
		cmb_listaLocalita.setWidth("300px");
		cmb_listaLocalita.setMold("paging");
		cmb_listaLocalita.setAutopaging(true);
		
		
		bandbox = new Bandbox();
		bandpopup = new Bandpopup();
		//bandbox.setId("bd");
		bandbox.setMold("rounded");
		bandbox.setAutodrop(true);
	
		bandbox.appendChild(bandpopup);
		
	
		bandpopup.appendChild(cmb_listaLocalita);
		for (IRicerca localita : listLocalita){
			Listitem itemLocalita = new Listitem();
			itemLocalita.setValue(localita.getId()); 
			itemLocalita.setLabel(localita.getDenominazione());
			cmb_listaLocalita.appendChild(itemLocalita);
		}
		bandbox.setParent(this);
		return cmb_listaLocalita;
		
	}
	
	
	private Combobox populateListaLocalita(List<IRicerca>  listLocalita){
		Combobox  cmb_listaLocalita= new Combobox();
	
		for (IRicerca localita : listLocalita){
			Comboitem itemLocalita = new Comboitem();
			itemLocalita.setValue(localita.getId()); 
			itemLocalita.setLabel(localita.getDenominazione());
			cmb_listaLocalita.appendChild(itemLocalita);
		}
		return cmb_listaLocalita;
		
	}
	
	private Combobox populateListaCondizioni(Set<CondizioniRicerca>  listCondizioniRicerca){
		Combobox  cmb_listaCondizioni= new Combobox();
		cmb_listaCondizioni.setWidth("55px");
		for (CondizioniRicerca condizioniRicerca : listCondizioniRicerca){
			Comboitem itemCondizioniRicerca = new Comboitem();
			itemCondizioniRicerca.setValue(condizioniRicerca.getId()); 
			itemCondizioniRicerca.setLabel(condizioniRicerca.getNome());
			cmb_listaCondizioni.appendChild(itemCondizioniRicerca);
		}
		return cmb_listaCondizioni;
		
	}
	
	
	private Combobox populateCampiOperatori(CampiRicerca campiRicerca){
	
		Combobox  cmb_listaOperatori= new Combobox();
		cmb_listaOperatori.setWidth("120px");
		Set<AssCampiOperatori> listaAssCampiOperatori = campiRicerca.getAssCampiOperatoris();
		for (AssCampiOperatori assCampiOperatori : listaAssCampiOperatori){
			Comboitem itemOperatoreRicerca = new Comboitem();
			itemOperatoreRicerca.setValue(assCampiOperatori.getOperatoriRicerca().getNome()); 
			itemOperatoreRicerca.setLabel(assCampiOperatori.getOperatoriRicerca().getEtichetta());
			cmb_listaOperatori.appendChild(itemOperatoreRicerca);
			//listaOperatori.add(assCampiOperatori.getOperatoriRicerca());
		}
		cmb_listaOperatori.setSelectedIndex(-1);
	
	
		return cmb_listaOperatori;
	}
	
	public void checkInsertRimuovi(){
			Button buttonRimuovi = new Button();
			if(index>0){
				setRimuovi(buttonRimuovi);
			}
	}
	
	
	
	private void clearSelectObject(){
		campiRicercaSelezionato = null;
		operatoreRicercaSelezionato = null;
		localitaSelezionata = null;
		condizioneSelezionata = null;
		testoSelezionato = null;

	}
	
	public void setCampi(Combobox campi) {
		this.campi = campi;
		this.campi.setParent(this);
		
		this.campi.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener() {
        	public void onEvent(Event event) throws Exception {
        		removeOperatori();
        		removeValoriT();
        		removeValoriC();
        		removeCondizioni();
        		removeAggiungi();
        		removeRimuovi();
        		clearSelectObject();
        		Combobox combobox = (Combobox)event.getTarget();
        		Comboitem item = combobox.getSelectedItem();
        		AdminCommand campiRicercaCommand = new CampiRicercaCommand();
        		campiRicercaSelezionato = (CampiRicerca) campiRicercaCommand.findById(Integer.valueOf(String.valueOf(item.getValue())));
        		setOperatori(populateCampiOperatori(campiRicercaSelezionato));
        		if(!campiRicercaSelezionato.getLista()){
        			Textbox textbox = new Textbox();
        			textbox.setWidth("120px");
        			setValoriT(new Textbox());
        		}else{
        			Class className=null;
        			className = Class.forName("it.ccse.bandiEsperti.business.command."+campiRicercaSelezionato.getCommand());
    				AdminCommand adminCommand = (AdminCommand)className.newInstance();
    				lista = new ArrayList<IRicerca> (adminCommand.list());
    				Collections.sort(lista, ComparatorFactory.getInstance().retrieve("IRicerca"));
    			if(lista.size()>300){
    					setValoriCListaMaggioreDi300(populateListaLocalitaMaggioreDi100(lista));
    			  }else{
    				  setValoriC(populateListaLocalita(lista));
    			  }
    			}
        		AdminCommand condizioniRicercaCommand = new CondizioniRicercaCommand();
        		Set<CondizioniRicerca> listaCondizioniRicerca = condizioniRicercaCommand.list();
        		setCondizioni(populateListaCondizioni(listaCondizioniRicerca));
        		checkInsertRimuovi();
        		checkInsertAggiungi();
        	
        	}
   		});
   		
	}
	
	public void removeAggiungi(){
		if(this.aggiungi!=null)
			this.removeChild(this.aggiungi);
	}
	public void removeRimuovi(){
		if(this.rimuovi!=null)
			this.removeChild(this.rimuovi);
	}
	
	public Combobox getOperatori() {
		return operatori;
	}
	public void removeCondizioni(){
		if(this.condizioni!=null)
			this.removeChild(this.condizioni);
	}
	
	public void removeOperatori(){
		if(this.operatori!=null)
			this.removeChild(this.operatori);
	}
	public void removeValoriC(){
		if(this.valoriC300!=null){
			this.cmb_listaLocalita.detach();
			this.bandpopup.detach();
			this.bandbox.detach();
			this.bandpopup.removeChild(this.cmb_listaLocalita);
			this.bandbox.removeChild(this.bandpopup);
			this.removeChild(this.bandbox);
			this.valoriC300=null;
			
		}
		if(this.valoriC100!=null){
			this.removeChild(this.valoriC100);
			this.valoriC100=null;
		}
	}
	
	public void removeValoriT(){
		if(this.valoriT!=null){
			this.removeChild(this.valoriT);
			this.valoriT=null;
		}
	}
	
	public void setOperatori(Combobox operatori) {
		this.operatori = operatori;
		this.operatori.setParent(this);
//		if(selectComboItemOperatore!=null){
//			for(int i=0; i< this.operatori.getChildren().size(); i++){
//				Comboitem combo = (Comboitem) this.operatori.getChildren().get(i);
//				if(combo.getValue().equals(selectComboItemOperatore.getValue())){
//					this.operatori.setSelectedItem(combo);
//				}
//			}
//		}
			
		
		this.operatori.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener() {
        	public void onEvent(Event event) throws Exception {
        		removeAggiungi();
        		removeRimuovi();
        		Combobox combobox = (Combobox)event.getTarget();
        	
        		Comboitem item = combobox.getSelectedItem();
        		selectComboItemOperatore=item;
        		operatoreRicercaSelezionato=(String)item.getValue();
        		checkInsertRimuovi();
        		checkInsertAggiungi();
        	
				
        	}
   		});
		
	}
	
	
	public void setValoriCListaMaggioreDi300(Listbox valoriC) {
		this.valoriC300 = valoriC;
		this.bandbox.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event event) throws Exception {
			
				Bandbox bandbox = (Bandbox)event.getTarget();
				testo = bandbox.getText();
				
				List<IRicerca> subList = new ArrayList<IRicerca>(); 
				for(IRicerca localita: lista){
					if(localita.getDenominazione().toLowerCase().startsWith(testo.toLowerCase())){
						subList.add(localita);
					}
				}
			
				Collections.sort(subList, ComparatorFactory.getInstance().retrieve("IRicerca"));
				if(lista.size()>300){
					removeOperatori();
	        		removeValoriT();
	        		removeValoriC();
	        		removeCondizioni();
	        		removeAggiungi();
	        		removeRimuovi();
					AdminCommand condizioniRicercaCommand = new CondizioniRicercaCommand();
	        		Set<CondizioniRicerca> listaCondizioniRicerca = condizioniRicercaCommand.list();
	        		setOperatori(populateCampiOperatori(campiRicercaSelezionato));
	        		bandbox.setOpen(false);	
		       		setValoriCListaMaggioreDi300(populateListaLocalitaMaggioreDi100(subList));
		       		bandbox.setText(testo);
		       		bandbox.setValue(testo);
					setCondizioni(populateListaCondizioni(listaCondizioniRicerca));
					checkInsertRimuovi();
					checkInsertAggiungi();
			  }
        	}
   		});
	
		
		
	
		this.bandbox.addEventListener(Events.ON_BLUR, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event event) throws Exception {
			
	       		bandbox.setText(testo);
	       		bandbox.setValue(testo);
			
			  }
        	
   		});
			
		this.valoriC300.addEventListener(Events.ON_SELECT, new org.zkoss.zk.ui.event.EventListener() {
		
        	public void onEvent(Event event) throws Exception {
        		removeAggiungi();
        		removeRimuovi();
        		Listbox listbox = (Listbox)event.getTarget();
        		Bandbox bandbox = (Bandbox)listbox.getParent().getParent();
        	
        		Listitem item = listbox.getSelectedItem();
        		bandbox.setValue(String.valueOf(item.getLabel()));
        		testo=String.valueOf(item.getLabel());
        		bandbox.close();
        		localitaSelezionata=Integer.valueOf(String.valueOf(item.getValue()));
        		checkInsertRimuovi();
        		checkInsertAggiungi();
        		testo = bandbox.getText();
        		
        	
        	}
   		});
	}
	
	
	public void setValoriC(Combobox valoriC) {
		this.valoriC100 = valoriC;
		valoriC100.setParent(this);
		valoriC100.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener() {
		
        	public void onEvent(Event event) throws Exception {
        		removeAggiungi();
        		removeRimuovi();
        		Combobox combobox = (Combobox)event.getTarget();
        		Comboitem item = combobox.getSelectedItem();
        		testo = item.getLabel();
        		localitaSelezionata=Integer.valueOf(String.valueOf(item.getValue()));
        		
        		checkInsertRimuovi();
        		checkInsertAggiungi();
        		
        		
        	}
   		});
	}
	
	public Combobox getCondizioni() {
		return condizioni;
	}
	public void setCondizioni(Combobox condizioni) {
		this.condizioni = condizioni;
		this.condizioni.setParent(this);
		
		this.condizioni.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener() {
        	public void onEvent(Event event) throws Exception {
        	
        		removeAggiungi();
        		removeRimuovi();
        		Combobox combobox = (Combobox)event.getTarget();
        		Comboitem item = combobox.getSelectedItem();
        		AdminCommand condizioniRicercaCommand = new CondizioniRicercaCommand();
        		condizioneSelezionata = (CondizioniRicerca) condizioniRicercaCommand.findById(Integer.valueOf(String.valueOf(item.getValue())));
        		checkInsertRimuovi();
        		checkInsertAggiungi();
        	
        	}
   		});
	}
	
	public Textbox getValoriT() {
		return valoriT;
	}
	public void setValoriT(Textbox valoriT) {
		this.valoriT = valoriT;
		this.valoriT.setParent(this);
		this.valoriT.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener() {
        	public void onEvent(Event event) throws Exception {
        		removeRimuovi();
        		removeAggiungi();
        		Textbox textbox = (Textbox)event.getTarget();
        		testoSelezionato=textbox.getValue();
        		testo=textbox.getValue();
        		checkInsertRimuovi();
        		checkInsertAggiungi();
        		
        	
        		
        	}
   		});
		
	}
	
	public Button getAggiungi() {
		return aggiungi;
	}
	public void setAggiungi(Button aggiungi) {
		this.aggiungi = aggiungi;
		aggiungi.setLabel("+");
		aggiungi.setWidth("15px");
    	
		this.aggiungi.setParent(this);
		
		this.aggiungi.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
        	public void onEvent(Event event) throws Exception {
        
        		AdminCommand campiRicercaCommand = new CampiRicercaCommand();
        		Set listaCampiRicerca = campiRicercaCommand.list();
        		Button button = (Button)event.getTarget();
        		button.setDisabled(true);
        		BeRow beRow= new BeRow();
        		beRow.setHeight("35px");
        		beRow.setCampi(populateListaCampi(listaCampiRicerca));
        		getP().addRow(beRow);
        		
        		
        	}
   		});
	}
	
	
	
	public void setRimuovi(Button rimuovi) {
		this.rimuovi = rimuovi;
		rimuovi.setLabel("-");
		rimuovi.setWidth("15px");
    	
		this.rimuovi.setParent(this);
		
		this.rimuovi.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
        	public void onEvent(Event event) throws Exception {
        		getP().removeRow(getThis());
        	
        	}
   		});
	}
	
	public BeRow getThis(){
		return this;
	}
	public BeRows getP(){
		BeRows beRows = (BeRows) this.getParent();
		//Grid grid = (Grid) beRows.getParent();
		return beRows;
	}
	
	private Combobox populateListaCampi(Set<CampiRicerca>  listCampiRicerca){
		Combobox  cmb_listaCampi= new Combobox();
		
		for (CampiRicerca campiRicerca : listCampiRicerca){
			Comboitem itemCampiRicerca = new Comboitem();
			itemCampiRicerca.setValue(campiRicerca.getId()); 
			itemCampiRicerca.setLabel(campiRicerca.getEtichetta());
			cmb_listaCampi.appendChild(itemCampiRicerca);
			
		}
		cmb_listaCampi.setWidth("145px");
		return cmb_listaCampi;
		
	}
	
	
}
