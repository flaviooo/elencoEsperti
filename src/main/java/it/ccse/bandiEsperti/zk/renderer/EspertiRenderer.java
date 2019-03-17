package it.ccse.bandiEsperti.zk.renderer;

import it.ccse.bandiEsperti.business.dto.EspertoDTO;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.zkoss.zk.ui.Component;
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

public class EspertiRenderer implements RowRenderer {

	private static final String REVOCA_IMG_KEY = "image.revoca.path";
	private static final String	APPROVA_IMG_KEY = "image.approva.path";
	private static final String	RIFIUTA_IMG_KEY = "image.rifiuta.path";
	private static final String BLOCCA_IMG_KEY= "image.blocca.path";
	private static final String SBLOCCA_IMG_KEY= "image.sblocca.path";
	private static final String CV_IMG_KEY	= "image.cv.path";
	private static final String CV_ORIG_IMG_KEY = "image.cvOrig.path";
	private static final String	INFO_APPROVAZIONE_IMG_KEY = "image.editInfo.path";
	private static final String VIEW_CV_DATA_IMG_KEY = "image.view.path";
	
	private Properties props = new Properties();
	public EspertiRenderer() {
		try {
			Desktop desktop = Executions.getCurrent().getDesktop();
			String imgCfg = desktop.getWebApp().getRealPath("/WEB-INF/conf/adminOperImages.properties");
			props.load(new FileInputStream(imgCfg));
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void render(Row row, Object val) throws Exception {

		row.setValue(val);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		EspertoDTO dto = (EspertoDTO) val;
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


	private Cell createCompetenzeField() {
		Cell cell = new Cell();
		cell.setAlign("center");
		Button btn = createButtonOperation("Vedi", "onVisualizzaCompetenze");
		cell.appendChild(btn);
		return cell;
	}

	private Cell createEsperienzeField() {
		Cell cell = new Cell();
		cell.setAlign("center");
		Button btn = createButtonOperation("Vedi", "onVisualizzaEsperienze");
		cell.appendChild(btn);
		return cell;
	}

	protected Cell createPubblicazioniField(int numPubblicazioni) {
		Cell cell = new Cell();
		cell.setAlign("center");
		Hbox div = new Hbox();
		div.setAlign("center");
		Label lblPubbl = new Label();
		lblPubbl.setWidth("30px");
		lblPubbl.setValue(String.valueOf(numPubblicazioni));
		div.appendChild(lblPubbl);
		Separator sep = new Separator("vertical");
		sep.setSpacing("20px");
		div.appendChild(sep);
		Button btn = createButtonOperation("Aggiuntive", "onScaricaAllegatoPubbl");
		div.appendChild(btn);
		cell.appendChild(div);
		return cell;
	}

	private Cell createCVField() {
		Cell cell = new Cell();
		cell.setAlign("center");
		Button btn = createButtonOperation("PDF", "onVisualizzaCV");
		cell.appendChild(btn);
		return cell;
	}

	private Cell createDettagliField()
	{
		Cell cellDett = new Cell();
		Button btnDett = createButtonOperation("Vedi", "onVediDettagli");
		cellDett.appendChild(btnDett);
		return cellDett;
	}

	protected Cell createOperationsField(EspertoDTO dto)
	{
		Cell cellOp = new Cell();
		Toolbar toolbar = new Toolbar("horizontal");
		toolbar.setStyle("background-color: transparent; border: 0 none; background-image: none");
		if (!dto.isApprovato())
		{
			//Bottone Approva
			Toolbarbutton btnApprova = createToolBarOperation(props.getProperty(APPROVA_IMG_KEY), "onConfermaApprova", dto.isSospeso(), "Approva Esperto");
			toolbar.appendChild(btnApprova);
			//Bottone rifiuta
			Toolbarbutton btnRifiuta = createToolBarOperation(props.getProperty(RIFIUTA_IMG_KEY), "onConfermaRifiuta", dto.isSospeso(), "Rifiuta Esperto");
			toolbar.appendChild(btnRifiuta);
			//Bottone ripristina
			Toolbarbutton btnRipristina = createToolBarOperation(props.getProperty(REVOCA_IMG_KEY), "onConfermaRipristina", dto.isRifiutato(), "Ripristina Esperto");
			toolbar.appendChild(btnRipristina);
		}
		//Bottone blocca
		Toolbarbutton btnBlocca = createToolBarOperation(props.getProperty(BLOCCA_IMG_KEY), "onConfermaBlocca", !dto.isBloccato(), "Blocca Esperto");
		toolbar.appendChild(btnBlocca);
		//Bottone sblocca
		Toolbarbutton btnSblocca = createToolBarOperation(props.getProperty(SBLOCCA_IMG_KEY), "onConfermaSblocca", dto.isBloccato(), "Sblocca Esperto");
		toolbar.appendChild(btnSblocca);
		//Bottone revoca
		if (dto.isApprovato()) {
			Toolbarbutton btnRevoca = createToolBarOperation(props.getProperty(REVOCA_IMG_KEY), "onConfermaRevoca", true, "Revoca Esperto");
			toolbar.appendChild(btnRevoca);
		}
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

	protected Toolbarbutton createToolBarOperation(String imagePath, String operationMethod, boolean visible, String tooltipText)
	{
		Toolbarbutton button = new Toolbarbutton();
		button.setImage(imagePath);
		button.setVisible(visible);
		button.setTooltiptext(tooltipText);
		button.addForward("onClick", (Component) null, operationMethod);
		return button;
	}

	private Button createButtonOperation(String text, String methodNameToExecute)
	{

		Button btn = new Button(text);
		btn.addForward("onClick", (Component) null, methodNameToExecute);
		return btn;
	}

	
}
