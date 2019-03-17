package it.ccse.bandiEsperti.zk.renderer;


import it.ccse.bandiEsperti.business.dto.EspertoDTO;
import it.ccse.bandiEsperti.business.dto.EspertoRicercaDinamicaDTO;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;


public class EspertiRicercaRenderer extends EspertiRenderer implements RowRenderer {

	private static final String CV_IMG_KEY	= "image.cv.path";
	private static final String CV_ORIG_IMG_KEY = "image.cvOrig.path";
	private static final String	INFO_APPROVAZIONE_IMG_KEY = "image.editInfo.path";
	private static final String VIEW_CV_DATA_IMG_KEY = "image.view.path";
	
	private Properties props = new Properties();
	public EspertiRicercaRenderer() {
		try {
			Desktop desktop = Executions.getCurrent().getDesktop();
			String imgCfg = desktop.getWebApp().getRealPath("/WEB-INF/conf/adminOperImages.properties");
			props.load(new FileInputStream(imgCfg));
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void render(Row row, Object val) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		row.setValue(val);
		EspertoRicercaDinamicaDTO dto = (EspertoRicercaDinamicaDTO) val;
		Cell cellCognome = new Cell(); //cognome
		Label lblCognome = new Label(String.valueOf(dto.getCognome())); 
		lblCognome.setMaxlength(100);
		cellCognome.appendChild(lblCognome);
		row.appendChild(cellCognome);
		Cell cellNome = new Cell(); //nome
		Label lblNome = new Label(String.valueOf(dto.getNome())); 
		lblNome.setMaxlength(100);
		cellNome.appendChild(lblNome);
		row.appendChild(cellNome);
		Cell cellDataNasc = new Cell(); //dataNascita
		Label lblDataNascita = new Label(); 
		
		if(dto.getDataNascita()!=null)
		
		if(dto.getDataNascita()!=null)
			lblDataNascita.setValue(sdf.format(dto.getDataNascita()));
		cellDataNasc.appendChild(lblDataNascita);
		row.appendChild(cellDataNasc);
		//Cell cellComp = createCompetenzeField(); //competenze
		//row.appendChild(cellComp);
		//Cell cellEsp = createEsperienzeField(); //esperienze
		//row.appendChild(cellEsp);
		Cell cellPubbl = createPubblicazioniField(dto.getNumPubblicazioni()); //pubblicazioni
		row.appendChild(cellPubbl);
		/*
		Cell cellCV = createCVField(); //CV
		row.appendChild(cellCV);
		*/
		Cell cellDataInvio = new Cell(); // data invio
		Label lblDataInvio = new Label(); 
		if (dto.getDataInvio() != null)
			lblDataInvio.setValue(sdf.format(dto.getDataInvio()));
		cellDataInvio.appendChild(lblDataInvio);
		row.appendChild(cellDataInvio);
		Cell cellOp = createOperationsField(dto); //operazioni 
		row.appendChild(cellOp);
		if (dto.isRifiutato())
			row.setSclass("redrow");

	}
	
	@Override
	protected Cell createPubblicazioniField(int numPubblicazioni) {
		Cell cell = new Cell();
		cell.setAlign("center");
		Hbox div = new Hbox();
		div.setAlign("center");
		Label lblPubbl = new Label();
		lblPubbl.setWidth("30px");
		lblPubbl.setValue(String.valueOf(numPubblicazioni));
		div.appendChild(lblPubbl);
		cell.appendChild(div);
		return cell;
	}
	@Override
	protected Cell createOperationsField(EspertoDTO dto)
	{
		Cell cellOp = new Cell();
		Toolbar toolbar = new Toolbar("horizontal");
		toolbar.setStyle("background-color: transparent; border: 0 none; background-image: none");
		
		Toolbarbutton btnCV = createToolBarOperation(props.getProperty(CV_IMG_KEY), "onVisualizzaCV", true, "Vedi CV");
		toolbar.appendChild(btnCV);
		if (dto.isApprovato())
		{
			Toolbarbutton btnCVOrig = createToolBarOperation(props.getProperty(CV_ORIG_IMG_KEY), "onVisualizzaCVPubli", true, "Vedi CV Pubblicabile");
			toolbar.appendChild(btnCVOrig);
			Toolbarbutton btnDetail = createToolBarOperation(props.getProperty(INFO_APPROVAZIONE_IMG_KEY), "onVediDettagli", true, "Vedi Dettagli");
			toolbar.appendChild(btnDetail);
		}
		Toolbarbutton btnComp = createToolBarOperation(props.getProperty(VIEW_CV_DATA_IMG_KEY), "onVisualizzaCompetenze", true, "Vedi Competenze");
		toolbar.appendChild(btnComp);
		Toolbarbutton btnEsp = createToolBarOperation(props.getProperty(VIEW_CV_DATA_IMG_KEY), "onVisualizzaEsperienze", true, "Vedi Esperienze");
		toolbar.appendChild(btnEsp);
		cellOp.appendChild(toolbar);
		return cellOp;
	}


	


}
