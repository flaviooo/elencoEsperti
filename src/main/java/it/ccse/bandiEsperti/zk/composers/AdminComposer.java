package it.ccse.bandiEsperti.zk.composers;

import it.ccse.bandiEsperti.business.command.ModificaStatoEspertoCommand;
import it.ccse.bandiEsperti.business.command.RecuperaTuttiEspertiCommand;
import it.ccse.bandiEsperti.business.dto.EspertoDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO.MacroFiltro;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO.TipoFiltro;
import it.ccse.bandiEsperti.business.dto.RicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.enums.OperazioniAmministratore;
import it.ccse.bandiEsperti.utils.CVFileFilter;
import it.ccse.bandiEsperti.utils.PdfUtils;
import it.ccse.bandiEsperti.utils.invioMail.InvioMailHelper;
import it.ccse.bandiEsperti.zk.assemblers.EspertoAssembler;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

public class AdminComposer extends CommonComposer {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(AdminComposer.class);

	private List<EspertoDTO> listaEsperti;
	private List<EspertoDTO> listaEspertiNonAppr;
	
	private FiltroRicercaEspertiDTO filtroRicerca;

	private Button btnFiltriRicerca;
	private Label lblTotEsperti;
	private Label lblTotEspertiNonAppr;

	private Label lblFltCompetenze;
	private Label lblFltEsperienze;
	private Label lblFltPubblicazioni;
	private Label lblFltIncarichi;
	private Label lblFltDatiAnagrafici;

	private Row rowFiltriApplicati;
	private Vlayout vlayFltCompetenze;
	private Vlayout vlayFltEsperienze;
	private Vlayout vlayFltPubblicazioni;
	private Vlayout vlayFltIncarichi;
	private Vlayout vlayFltDatiAnagrafici;
	private Window win;


	public void doAfterCompose(Component win) throws Exception {
		super.doAfterCompose(win);
		this.win = (Window) win;

		try{
		
			this.filtroRicerca = new FiltroRicercaEspertiDTO();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.initModel(false);
		this.initWidgets();
	}

	public void initModel(boolean isUpdate) throws Exception{
		try{
			//Qui ci dovrebbero gli esperti approvati
			RecuperaTuttiEspertiCommand recEspertiCommand = new RecuperaTuttiEspertiCommand(true);
			List<Esperto> lstEsp = recEspertiCommand.ricercaEsperti(this.filtroRicerca);
			this.listaEsperti = new ArrayList<EspertoDTO>();
			for (Esperto esp: lstEsp){
				listaEsperti.add(EspertoAssembler.toDTO(esp));
			}
			Collections.sort(listaEsperti, ComparatorFactory.getInstance().retrieve("EspertoDTO"));
			RecuperaTuttiEspertiCommand recEspertiNonAppr = new RecuperaTuttiEspertiCommand(false);
			List<Esperto> lstEspNonAppr = recEspertiNonAppr.ricercaEsperti(filtroRicerca);
			this.listaEspertiNonAppr = new ArrayList<EspertoDTO>();
			for (Esperto esp : lstEspNonAppr){
				this.listaEspertiNonAppr.add(EspertoAssembler.toDTO(esp));
			}
			Collections.sort(listaEspertiNonAppr, ComparatorFactory.getInstance().retrieve("EspertoDTO"));
			
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WrongValueException("Si è verificato un errore nella inizializzazione della pagina");
		}
	}

	public void initWidgets(){
		if(btnFiltriRicerca != null)
		{
			((Cell)this.lblFltCompetenze.getParent()).setStyle("");
			((Cell)this.lblFltDatiAnagrafici.getParent()).setStyle("");
			((Cell)this.lblFltEsperienze.getParent()).setStyle("");
			((Cell)this.lblFltIncarichi.getParent()).setStyle("");
			((Cell)this.lblFltPubblicazioni.getParent()).setStyle("");
	
			btnFiltriRicerca.setStyle("margin-right:27px");
	
			this.rowFiltriApplicati.setVisible(false);
			this.vlayFltCompetenze.getChildren().clear();
			this.vlayFltEsperienze.getChildren().clear();
			this.vlayFltPubblicazioni.getChildren().clear();
			this.vlayFltIncarichi.getChildren().clear();
			this.vlayFltDatiAnagrafici.getChildren().clear();
	
			if(!this.filtroRicerca.filtriApplicatiDefault()){
				btnFiltriRicerca.setStyle("margin-right:27px");
	
				Map<MacroFiltro, Map<TipoFiltro, List<String>>> filtriApplicati = this.filtroRicerca.getFiltriApplicati();
	
				if(filtriApplicati.get(MacroFiltro.COMPETENZE) != null && filtriApplicati.get(MacroFiltro.COMPETENZE).size() > 0){
					((Cell)this.lblFltCompetenze.getParent()).setStyle("background-color:#8BFC6F");
					this.creaListaLabels(filtriApplicati, MacroFiltro.COMPETENZE, this.vlayFltCompetenze);
				}
				if(filtriApplicati.get(MacroFiltro.DATIANAGRAFICI) != null && filtriApplicati.get(MacroFiltro.DATIANAGRAFICI).size() > 0){
					((Cell)this.lblFltDatiAnagrafici.getParent()).setStyle("background-color:#8BFC6F");
					this.creaListaLabels(filtriApplicati, MacroFiltro.DATIANAGRAFICI, this.vlayFltDatiAnagrafici);
				}
				if(filtriApplicati.get(MacroFiltro.ESPERIENZE) != null && filtriApplicati.get(MacroFiltro.ESPERIENZE).size() > 0){
					((Cell)this.lblFltEsperienze.getParent()).setStyle("background-color:#8BFC6F");
					this.creaListaLabels(filtriApplicati, MacroFiltro.ESPERIENZE, this.vlayFltEsperienze);
				}
				if(filtriApplicati.get(MacroFiltro.PRECEDENTIINCARICHI) != null && filtriApplicati.get(MacroFiltro.PRECEDENTIINCARICHI).size() > 0){
					((Cell)this.lblFltIncarichi.getParent()).setStyle("background-color:#8BFC6F");
					this.creaListaLabels(filtriApplicati, MacroFiltro.PRECEDENTIINCARICHI, this.vlayFltIncarichi);
				}
				if(filtriApplicati.get(MacroFiltro.PUBBLICAZIONI) != null && filtriApplicati.get(MacroFiltro.PUBBLICAZIONI).size() > 0){
					((Cell)this.lblFltPubblicazioni.getParent()).setStyle("background-color:#8BFC6F");
					this.creaListaLabels(filtriApplicati, MacroFiltro.PUBBLICAZIONI, this.vlayFltPubblicazioni);
				}
	
				this.rowFiltriApplicati.setVisible(true);
			}
		}

		this.lblTotEsperti.setValue("Esperti Selezionati: 0");
		if(this.listaEsperti!= null){
			this.lblTotEsperti.setValue("Esperti Selezionati: "+this.listaEsperti.size());
		}


		lblTotEspertiNonAppr.setValue("Esperti in attesa di valutazione: 0");
		if (this.listaEspertiNonAppr != null){
			this.lblTotEspertiNonAppr.setValue("Esperti in attesa di valutazione: "+this.listaEspertiNonAppr.size());
		}
	}

	private void creaListaLabels(Map<MacroFiltro, Map<TipoFiltro, List<String>>> filtriApplicati, MacroFiltro tipoMacroFiltro, Vlayout vlayout){
		for(Entry<TipoFiltro, List<String>> filtri : filtriApplicati.get(tipoMacroFiltro).entrySet()){
			for(String lbls : filtri.getValue()){
				Label lblFlt = new Label();
				lblFlt.setValue(lbls);

				vlayout.getChildren().add(lblFlt);
			}
		}
	}

	public List<EspertoDTO> getListaEsperti(){
		return this.listaEsperti;
	}

	public void setListaEsperti(List<EspertoDTO> listaEsperti){
		this.listaEsperti = listaEsperti;
	}

	public List<EspertoDTO> getListaEspertiNonAppr() {
		return listaEspertiNonAppr;
	}

	public void setListaEspertiNonAppr(List<EspertoDTO> listaEspertiNonAppr) {
		this.listaEspertiNonAppr = listaEspertiNonAppr;
	}

	
	
	public void onEsportaRicerca(ForwardEvent event) {
		try {
			//TODO: impostazione filtri
			AdminComposerHelper helper = new AdminComposerHelper();
			Date now = new Date();
			RicercaEspertiDTO dto = helper.createReportData(listaEsperti, filtroRicerca);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
			String fileName = "ricercaEsperti_"+sdf.format(now)+".pdf";
			byte byteArr[] = PdfUtils.generaPdfContentRicercaEsperti(dto, now);
			Filedownload.save(byteArr, "application/pdf", fileName);
		} catch (Exception e) {
			throw new WrongValueException("Errore durante il download della ricerca");
		}
	}

	


	public void onConfermaSblocca(ForwardEvent event) {
		try {
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO dto = (EspertoDTO) rigaAttiva.getValue();
			Messagebox.show("Con la seguente operazione si intende sbloccare " +
					"l'esperto. Procedere?", "Sblocco Esperto", 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new EspertoListener(dto, "onSblocca", "Operazione in corso ..."));

		} catch (Exception ex) {

		} 
	}

	public void onSblocca(Event event) {
		try {
			EspertoDTO dto = (EspertoDTO) event.getData();
			ModificaStatoEspertoCommand cmd = new ModificaStatoEspertoCommand(dto, OperazioniAmministratore.SBLOCCA);
			cmd.execute();
			if (dto.isApprovato())
				refreshGridApprovati();
			else	
				refreshGridNonApprovati();
			initWidgets();

		} catch (Exception ex) {
			throw new WrongValueException("Errore durante lo sblocco dell'esperto: " + ex.getMessage());
		} finally {
			Clients.clearBusy();
		}
	}

	public void onConfermaRevoca(ForwardEvent event) {
		try {
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO dto = (EspertoDTO) rigaAttiva.getValue();
			Messagebox.show("Con la seguente operazione si intende annullare " +
					"l'approvazione dell'esperto. Procedere?", "Revoca Esperto", 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
					new EspertoListener(dto, "onRevoca", "Operazione in corso ..."));

		} catch (Exception ex) {

		}
	}

	public void onRevoca(Event event) {
		try {
			EspertoDTO dto = (EspertoDTO) event.getData();
			ModificaStatoEspertoCommand cmd = new ModificaStatoEspertoCommand(dto, OperazioniAmministratore.REVOCA);
			cmd.execute();
			refreshGridApprovati();
			refreshGridNonApprovati();
			initWidgets();

		} catch (Exception ex) {
			throw new WrongValueException("Errore durante la revoca dell'esperto: " + ex.getMessage());
		} finally {
			Clients.clearBusy();
		}
	}


	public void onConfermaRipristina(ForwardEvent event) {
		try {
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO dto = (EspertoDTO) rigaAttiva.getValue();
			Messagebox.show("Con la seguente operazione si intende prendere nuovamente in " +
					"considerazione un esperto precedentemente rifiutato. Procedere?",
					"Ripristina Esperto", 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
					new EspertoListener(dto, "onRipristina", "Operazione in corso ..."));
		} catch (Exception ex) {

		}
	}

	public void onConfermaBlocca(ForwardEvent event) {
		try {
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO dto = (EspertoDTO) rigaAttiva.getValue();
			Messagebox.show("Con la seguente operazione si intende bloccare l'esperto. Procedere?",
					"Ripristina Esperto", 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
					new EspertoListener(dto, "onBlocca", "Operazione in corso ..."));
		} catch (Exception ex) {

		}
	}
	
	public void onBlocca(Event event) {
		try {
			EspertoDTO dto = (EspertoDTO) event.getData();
			ModificaStatoEspertoCommand cmd = new ModificaStatoEspertoCommand(dto, OperazioniAmministratore.BLOCCA);
			cmd.execute();
			if (dto.isApprovato())
				refreshGridApprovati();
			else
				refreshGridNonApprovati();
			initWidgets();

		} catch (Exception ex) {
			throw new WrongValueException("Errore durante il blocco dell'esperto: " + ex.getMessage());
		} finally {
			Clients.clearBusy();
		}
	}

	public void onRipristina(Event event) {
		try {
			EspertoDTO dto = (EspertoDTO) event.getData();
			ModificaStatoEspertoCommand cmd = new ModificaStatoEspertoCommand(dto, OperazioniAmministratore.REVOCA);
			cmd.execute();
			if (dto.isApprovato())
				refreshGridApprovati();
			refreshGridNonApprovati();

		} catch (Exception ex) {
			throw new WrongValueException("Errore durante il ripristino dell'esperto: " + ex.getMessage());
		} finally {
			Clients.clearBusy();
		}
	}

	public void onConfermaRifiuta(ForwardEvent event) {
		try {
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO dto = (EspertoDTO) rigaAttiva.getValue();
			Messagebox.show("Con la seguente operazione si intende " +
					"rifiutare l'esperto. Procedere?", "Riufiuta Esperto", 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
					new EspertoListener(dto, "onRifiuta", "Operazione in corso ..."));

		} catch (Exception ex) {

		}
	}

	public void onRifiuta(Event event) {
		try {
			EspertoDTO dto = (EspertoDTO) event.getData();
			ModificaStatoEspertoCommand cmd = new ModificaStatoEspertoCommand(dto, OperazioniAmministratore.RIFIUTA);
			cmd.execute();
			refreshGridNonApprovati();
			initWidgets();

		} catch (Exception ex) {
			throw new WrongValueException("Errore durante il rifiuto dell'esperto: " + ex.getMessage());
		} finally {
			Clients.clearBusy();
		}

	}




	public void onConfermaApprova(ForwardEvent event) {
		try {
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO dto = (EspertoDTO) rigaAttiva.getValue();
			Messagebox.show("Con la seguente operazione si intende" +
					" approvare l'esperto. Procedere?", "Approva Esperto",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
					new EspertoListener(dto, "onApprova", "Operazione in corso ..."));
		} catch (Exception ex) {

		}
	}

	public void onApprova(Event event) {
		try {

			EspertoDTO dto = (EspertoDTO) event.getData();
			ModificaStatoEspertoCommand cmd = new ModificaStatoEspertoCommand(dto , OperazioniAmministratore.APPROVA);
			cmd.execute();
			//refresh approvati
			refreshGridApprovati();
			//refresh non approvati
			refreshGridNonApprovati();
			initWidgets();
		} catch (Exception ex) {
			throw new WrongValueException("Errore durante l'approvazione dell'esperto: " + ex.getMessage());
		} finally {
			Clients.clearBusy();
		}

	}


	public void onFinestraFiltri(ForwardEvent event){
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("filtroRicerca", this.filtroRicerca);
			params.put("parentController", this);
			params.put("binder", this.self.getAttribute("binder"));

			Window window = (Window) Executions.createComponents("/pages/BandiEspertiPages/welcomePageAdminFiltriRicerca.zul", this.self, params);
			window.setWidth("850px");
			window.setClosable(true);
			window.setTitle("Filtri di Ricerca");

			window.doModal();
		}catch(Exception ex){
			throw new WrongValueException("Errore nella apertura della finestra di ricerca: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
	}

	


	


	
	private void refreshGridApprovati()
	{



		RecuperaTuttiEspertiCommand recEsperti = new RecuperaTuttiEspertiCommand(true);
		List<Esperto> lstEspAppr = recEsperti.ricercaEsperti(filtroRicerca);
		this.listaEsperti = new ArrayList<EspertoDTO>();
		for (Esperto esp : lstEspAppr)
			this.listaEsperti.add(EspertoAssembler.toDTO(esp));
		Grid espAppr = (Grid) win.getFellow("inboxGrid");
		espAppr.setModel(new ListModelList(listaEsperti));
		espAppr.renderAll();
	}

	private void refreshGridNonApprovati()
	{
		RecuperaTuttiEspertiCommand recEspertiNonAppr = new RecuperaTuttiEspertiCommand(false);
		List<Esperto> lstEspNonAppr = recEspertiNonAppr.ricercaEsperti(filtroRicerca);
		this.listaEspertiNonAppr = new ArrayList<EspertoDTO>();
		for (Esperto esp : lstEspNonAppr)
			this.listaEspertiNonAppr.add(EspertoAssembler.toDTO(esp));
		Grid espNonAppr = (Grid) win.getFellow("espertiNonApprGrid");
		espNonAppr.setModel(new ListModelList(listaEspertiNonAppr));
		espNonAppr.renderAll();
	}

	private String getCVPath(String basePath, EspertoDTO e, boolean first)
	{
		File dir = new File(basePath);
		//String baseNameFile = e.getCognome() + e.getNome() + "_" + e.getCodiceDomanda()+"_";
		int progr = 0, tmpProgr = 0;
		String tmpName;
		String tmpArr[];
		File tmpFile = null;
		for (File file: dir.listFiles(new CVFileFilter(e)))
		{
			tmpArr = file.getName().split("_");
			tmpName = tmpArr[tmpArr.length - 1];
			tmpName = tmpName.substring(0, tmpName.lastIndexOf("."));
				tmpProgr = Integer.parseInt(tmpName);

				if (tmpProgr > progr)
				{
					progr = tmpProgr;
					tmpFile = file;

					if (progr == 1 && first == true)
						break;
				}

		}
		return (tmpFile == null ? null : tmpFile.getAbsolutePath());	
	}

	public void onClick$btn_invio_cred(Event event)
	{

		try {
			String msg = "Con la seguente operazione si intende inviare per " +
					"mail le credenziali di accesso per tutti gli esperti approvati. Procedere ?";
			Messagebox.show(msg, "Question",
					Messagebox.YES | Messagebox.NO, 
					Messagebox.QUESTION, 
					new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {

					if (event.getName().equals("onYes"))
					{
						Clients.showBusy("Operazione in corso ...");
						Events.echoEvent("onInviaCredenziali", win, null);
					}
					else if (event.getName().equals("onClose"))
						event.stopPropagation();

				}
			});
		} catch (Exception ex) {

		} 
	}

	public void onInviaCredenziali(Event event) {
		try {
			InvioMailHelper mailHelper = new InvioMailHelper();
			//invio mail approvati
			for (EspertoDTO dto: listaEsperti)
				mailHelper.invioCredenziali(dto.getNome(), dto.getCognome(), 
						dto.getEmail(), dto.getUsername(), dto.getPassword());

			Messagebox.show("Mail inviate con successo.", "CCSE Bandi Esperti", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception ex) {
			throw new WrongValueException("Errore durante l'invio delle credenziali: "+ex.getMessage());
		} finally {
			Clients.clearBusy();
		}
	}

	private class EspertoListener implements EventListener {

		private EspertoDTO dto;
		private String operationName;
		private String waitMsg;

		EspertoListener(EspertoDTO dto, String operationName, String waitMsg) {
			this.dto = dto;
			this.operationName = operationName;
			this.waitMsg = waitMsg;
		}

		@Override
		public final void onEvent(Event ev) throws Exception {

			if (ev.getName().equals("onYes"))
			{
				Clients.showBusy(waitMsg);
				Events.echoEvent(operationName, AdminComposer.this.win, dto);
			}
			else if (ev.getName().equals("onClose"))
				ev.stopPropagation();

		}

	}

}
