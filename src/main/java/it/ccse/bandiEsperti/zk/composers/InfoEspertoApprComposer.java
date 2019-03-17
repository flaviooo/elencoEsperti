package it.ccse.bandiEsperti.zk.composers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.ccse.bandiEsperti.business.command.AggiornaEspertoApprovatoCommand;
import it.ccse.bandiEsperti.business.command.EspertoCommand;
import it.ccse.bandiEsperti.business.command.IRicercaCommand;
import it.ccse.bandiEsperti.business.command.RecuperaTuttiEspertiCommand;
import it.ccse.bandiEsperti.business.command.RicercaCommand;
import it.ccse.bandiEsperti.business.dto.EspertoDTO;
import it.ccse.bandiEsperti.business.dto.EspertoRicercaDinamicaDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.InfoEspertoApprovato;
import it.ccse.bandiEsperti.utils.TrasformListViewToFiltroRicercaDTO;
import it.ccse.bandiEsperti.zk.assemblers.EspertoAssembler;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;
import it.ccse.bandiEsperti.zk.view.BeRows;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class InfoEspertoApprComposer extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Label lblNome;
	private Label lblCognome;
	
	private Label lblDataNas;
	private Label lblCodDom;
	private Textbox txtTipoIncarico;
	private Textbox txtValutazione;
	private Textbox txtMail;
	private Textbox txtTelefono;
	private EspertoDTO esperto;
	private Boolean ricerca = false;
	private Window win;
	private BeRows beRows;
	//inboxGrid

	@Override
	public void doAfterCompose(Component win) throws Exception {
		super.doAfterCompose(win);
		esperto = (EspertoDTO) arg.get("esperto");
	
		if(arg.get("ricerca")!=null){
			ricerca=(Boolean) arg.get("ricerca");
			beRows = (BeRows) arg.get("beRows");
		}

		if ((esperto.getGiudizio()!=null && !esperto.getGiudizio().equals("")) && (esperto.getTipoIncarico()!=null && !esperto.getTipoIncarico().equals("")) && !ricerca)
		{
			txtTipoIncarico.setValue(esperto.getTipoIncarico());
			txtValutazione.setValue(esperto.getGiudizio());
		}
		//popolamento maschera
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		lblNome.setValue(esperto.getNome());
		lblCognome.setValue(esperto.getCognome());
		
		lblDataNas.setValue(sdf.format(esperto.getDataNascita()));
		lblCodDom.setValue(esperto.getCodiceDomanda());
		
		txtMail.setValue(esperto.getEmail());
		txtTelefono.setValue(esperto.getTelefono());
		this.win = (Window) win;
	}

	public void onClick$btn_salva(Event event) throws Exception {


		
		Messagebox.show("I dati sono stati salvati correttamente.", null, Messagebox.OK, Messagebox.NONE);
		
		EspertoCommand cmdEsperto = new EspertoCommand();
		cmdEsperto.aggiornaEsperto(esperto.getId(), txtMail.getValue(),  txtTelefono.getValue());
		
		//refresh del model della grid degli esperti approvati
		if(!ricerca){
			InfoEspertoApprovato espApprNew = new InfoEspertoApprovato();
			AggiornaEspertoApprovatoCommand cmd = new AggiornaEspertoApprovatoCommand(esperto.getId(), espApprNew);
			cmd.execute();
			Grid compToRefresh = (Grid) win.getParent().getFellow("inboxGrid");
			RecuperaTuttiEspertiCommand recEspertiCommand = new RecuperaTuttiEspertiCommand(true);
			List<Esperto> listaEsp = recEspertiCommand.ricercaEsperti(new FiltroRicercaEspertiDTO());
			List<EspertoDTO> listaDTO = new ArrayList<EspertoDTO>();
			for (Esperto e: listaEsp){
				listaDTO.add(EspertoAssembler.toDTO(e));
			}
			Collections.sort(listaDTO, ComparatorFactory.getInstance().retrieve("EspertoDTO"));
			compToRefresh.setModel(new ListModelList(listaDTO));
			compToRefresh.renderAll();
			win.detach();	
		}else{
			Grid compToRefresh = (Grid) win.getParent().getFellow("resultGrid");
			List<FiltroRicercaDTO> listaFiltroRicercaDTO=TrasformListViewToFiltroRicercaDTO.getInstance().execute(beRows);
			IRicercaCommand rc = new RicercaCommand(listaFiltroRicercaDTO);
			List<EspertoRicercaDinamicaDTO> listRicercaDinamicaDTO = new ArrayList<EspertoRicercaDinamicaDTO>(rc.execute());
			Collections.sort(listRicercaDinamicaDTO, ComparatorFactory.getInstance().retrieve("EspertoRicercaDinamicaDTO"));
			Set<EspertoRicercaDinamicaDTO> listEspertoRicercaDinamicaDTO = new HashSet<EspertoRicercaDinamicaDTO>();
			listEspertoRicercaDinamicaDTO.addAll(listRicercaDinamicaDTO);	
			compToRefresh.setModel(new ListModelList(listEspertoRicercaDinamicaDTO));
			compToRefresh.renderAll();
			win.detach();	
		}
		

	}

}
