package it.ccse.bandiEsperti.zk.composers;


import it.ccse.bandiEsperti.business.command.AdminCommand;
import it.ccse.bandiEsperti.business.command.CampiRicercaCommand;
import it.ccse.bandiEsperti.business.command.DatoreDiLavoroCommand;
import it.ccse.bandiEsperti.business.command.EsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.IRicercaCommand;
import it.ccse.bandiEsperti.business.command.RicercaCommand;
import it.ccse.bandiEsperti.business.command.interfaces.IRicerca;
import it.ccse.bandiEsperti.business.dto.EspertoRicercaDinamicaDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaDTO;
import it.ccse.bandiEsperti.business.model.Associazioni;
import it.ccse.bandiEsperti.business.model.CampiRicerca;
import it.ccse.bandiEsperti.business.model.CondizioniRicerca;
import it.ccse.bandiEsperti.business.model.DatoreDiLavoro;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.OperatoriRicerca;
import it.ccse.bandiEsperti.utils.PdfUtils;
import it.ccse.bandiEsperti.utils.TrasformListViewToFiltroRicercaDTO;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;
import it.ccse.bandiEsperti.zk.view.BeRow;
import it.ccse.bandiEsperti.zk.view.BeRows;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;


public class RicercaComposer extends CommonComposer implements AdministrationComposer {
	private static final long serialVersionUID = -3011711889760264355L;


	BeRows beRows;
	BeRow beRow;
	private Set<CampiRicerca> listaCampi = new LinkedHashSet<CampiRicerca>();
	private Set<OperatoriRicerca> listaOperatori = new LinkedHashSet<OperatoriRicerca>();
	private Set<CondizioniRicerca> listaCondizioni = new LinkedHashSet<CondizioniRicerca>();
	private Set<IRicerca> listaLocalita = new LinkedHashSet<IRicerca>();
	
	Button bnt_esport;
	
	private Set<Serializable> lista = new LinkedHashSet<Serializable>();
	
	private static final String  winId="win_ricerca";
	
	private Grid inboxGrid;


	private CampiRicerca itemSelezionato;
	private Set<EspertoRicercaDinamicaDTO> listEspertoRicercaDinamicaDTO;


	public Set<EspertoRicercaDinamicaDTO> getListEspertoRicercaDinamicaDTO() {
		return listEspertoRicercaDinamicaDTO;
	}

	@Override
	public CampiRicerca getItemSelezionato() {
		return itemSelezionato;
	}
	
	
	public Set<OperatoriRicerca> getListaOperatori() {
		return listaOperatori;
	}


	
	public Set<IRicerca> getListaLocalita() {
		return listaLocalita;
	}
	
	public Set<CampiRicerca> getListaCampi() {
		return listaCampi;
	}


	@Override
	public Set<Serializable> getLista() {
		return lista;
	}
	
	public Set<CondizioniRicerca> getListaCondizioni() {
		return listaCondizioni;
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
	
	public void onEsportaRicerca(ForwardEvent event) {
		try {
			//TODO: impostazione filtri
		
			Date now = new Date();
			//RicercaEspertiDTO dto = helper.createReportData(listaEsperti, filtroRicerca);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
			String fileName = "ricercaEsperti_"+sdf.format(now)+".xls";
			//List<FiltroRicercaDTO> listaFiltroRicercaDTO=TrasformListViewToFiltroRicercaDTO.getInstance().execute(beRows);
			StringBuilder sb = new StringBuilder();
			for(BeRow beRow: beRows.getRows()){
				if(beRow.getCampiRicercaSelezionato()!=null){
					String campoRicerca = beRow.getCampiRicercaSelezionato().getEtichetta();
					String operatore = beRow.getOperatoreRicercaSelezionato();
					String valore = "";
					if(beRow.getTesto()!=null && beRow.getTesto().trim().length()>0){
						valore=beRow.getTesto();
					}
					sb.append(campoRicerca).append(operatore).append(valore).append("\n");
				}
			}
			byte byteArr[] = PdfUtils.generaExcelContentRicercaEsperti(listEspertoRicercaDinamicaDTO, now, sb.toString());
			Filedownload.save(byteArr, "application/excel", fileName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WrongValueException("Errore durante il download della ricerca");
		}
	}
	
	@Override
	public Serializable populateSelectItem(Serializable serializable){
		return null;
	}

	/** Evento per la modifica di un nuovo dato */
	@Override
	public void onClick$bnt_aggiorna(Event event) {
		if (this.itemSelezionato==null){
			throw new WrongValueException("Non sono presenti datori di lavoro da aggiornare");
		}
		AdminCommand command = new EsperienzeLavorativeCommand();
		itemSelezionato=(CampiRicerca)populateSelectItem(itemSelezionato);
	
		boolean res=command.checkIfExist(itemSelezionato);
		if(res){
				try {
					Messagebox.show("La modifica effettuata a questo datore di lavoro avrà impatto anche su tutti i Curriculum aventi tale riferimento. Si vuole procedere?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
					    new org.zkoss.zk.ui.event.EventListener() {
					        public void onEvent(Event event) throws InterruptedException {
					            if (event.getName().equals("onOK")) {	                	
					            	Clients.showBusy("validazione in corso... ");
					        		Events.echoEvent("onMod",Path.getComponent("/"+winId),itemSelezionato);
					            } else {
					                return;
					            }
					        }
						});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else{
			Clients.showBusy("Validazione e Salvataggio in corso... ");
	    	Events.echoEvent("onMod",event.getTarget(),itemSelezionato);
		
		}
	}
	
	@Override
	public void onMod(Event event){
	}
	
	@Override
	public void onModifica(ForwardEvent event){	
	}
	
	

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.initModel(false);
	}

	@Override
	public void loadValoriDefault(){
		beRows = new BeRows();
		beRow = new BeRow();
		AdminCommand campiRicercaCommand = new CampiRicercaCommand();
		List<CampiRicerca> lista = new ArrayList<CampiRicerca>(campiRicercaCommand.list());
		Collections.sort(lista, ComparatorFactory.getInstance().retrieve("CampiRicerca"));
		beRow.setHeight("35px");
		beRow.setCampi(populateListaCampi(lista));
		beRows.addRow(beRow);
		inboxGrid.appendChild(beRows);
	}
	
	private Combobox populateListaCampi(List<CampiRicerca> listCampiRicerca){
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
	
	@Override
	public void onVediDettagli(ForwardEvent event) {
		super.onVediDettagli(event, beRows);
	
	}
	
	public void onClick$bnt_ricerca(Event event){
		
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onRicerca",event.getTarget(),null);
	}
	
	public void onRicerca(Event event){
	
		try{
			this.listEspertoRicercaDinamicaDTO = new HashSet<EspertoRicercaDinamicaDTO>();
			List<FiltroRicercaDTO> listaFiltroRicercaDTO=TrasformListViewToFiltroRicercaDTO.getInstance().execute(beRows);
			IRicercaCommand rc = new RicercaCommand(listaFiltroRicercaDTO);
			List<EspertoRicercaDinamicaDTO> listRicercaDinamicaDTO = new ArrayList<EspertoRicercaDinamicaDTO>(rc.execute());
			Collections.sort(listRicercaDinamicaDTO, ComparatorFactory.getInstance().retrieve("EspertoRicercaDinamicaDTO"));
			this.listEspertoRicercaDinamicaDTO.addAll(listRicercaDinamicaDTO);
			
	
			
			Messagebox.show("Ricerca effettuata.","Info", Messagebox.OK, Messagebox.INFORMATION);
			bnt_esport.setDisabled(false);
		}catch(Exception ex){
			
			throw new WrongValueException("Errore nella ricerca dei dati: " + ex.getMessage());
		}finally{
			try {
				Clients.clearBusy();
				// reset dei campi a video
				
				this.initModel(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void initModel(boolean isUpdate) throws Exception{
		try{
	
			if (isUpdate){
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
			}else{
				loadValoriDefault();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/** Evento per l'aggiunta di un nuovo dato */
	@Override
	public void onClick$bnt_aggiungi(Event event) {
		Clients.showBusy("Validazione e Salvataggio in corso... ");
    	Events.echoEvent("onAggiungi",event.getTarget(),null);
		
	}
	@Override
	public void onAggiungi(Event event){
		
		try {
			Integer id = null;
			DatoreDiLavoro datoreDiLavoro = new DatoreDiLavoro();
			datoreDiLavoro=(DatoreDiLavoro)populateSelectItem(datoreDiLavoro);
			AdminCommand datoreDiLavoroCommand = new DatoreDiLavoroCommand();
			id=datoreDiLavoroCommand.inserisci(datoreDiLavoro);
			if (id==null){
				throw new Exception("non è possibile eseguire l'operazione di aggiornamento dei dati");
			}else{
				Messagebox.show("Salvataggio completato.","Info", Messagebox.OK, Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WrongValueException("Errore nel salvataggio dei dati: " + e.getMessage());
		}finally{
			Clients.clearBusy();
			try {
				this.initModel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	//primo evento generato al clic su un pulsante di rimozione istruzione
	@Override
	public void onVerificaRimuovi(ForwardEvent event){
		
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			DatoreDiLavoro datoreDiLavoro = (DatoreDiLavoro) rigaAttiva.getValue();
			AdminCommand command = new EsperienzeLavorativeCommand();
			boolean res=command.checkIfExist(datoreDiLavoro);
			if(res){
				Messagebox.show("La cancellazione del datore di lavoro non può essere effettuata, la voce è presente all'interno di uno o più Curriculum Vitae"); 
				return;		
			}else{
				Clients.showBusy("Cancellazione in corso... ");
		    	Events.echoEvent("onRimuovi",event.getTarget(),datoreDiLavoro);
			
			}
			
		}catch(Exception ex){
			throw new WrongValueException("Errore nella cancellazione dei Dati: " + ex.getMessage());
		}
	}
	

	@Override
	public void onRimuovi(Event event){	
		boolean esito;
		try{
			DatoreDiLavoro datoreDiLavoro = (DatoreDiLavoro) event.getData();
			AdminCommand command = new DatoreDiLavoroCommand();
			esito = command.delete(datoreDiLavoro.getId());
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
	
	
	
	
	
}