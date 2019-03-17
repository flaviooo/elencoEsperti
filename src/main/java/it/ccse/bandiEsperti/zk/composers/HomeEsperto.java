package it.ccse.bandiEsperti.zk.composers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import it.ccse.bandiEsperti.business.command.EliminaDocumentoEspertoCommand;
import it.ccse.bandiEsperti.business.command.EsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.InserisciDocumentoEspertoCommand;
import it.ccse.bandiEsperti.business.command.InviaDatiEspertoCommand;
import it.ccse.bandiEsperti.business.command.RicercaCompetenzeCommand;
import it.ccse.bandiEsperti.business.command.RicercaIstruzioneCommand;
import it.ccse.bandiEsperti.business.model.Competenza;
import it.ccse.bandiEsperti.business.model.DocumentoAllegatoEsperto;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Istruzione;
import it.ccse.bandiEsperti.utils.ConfigHelper;
import it.ccse.bandiEsperti.utils.PdfUtils;


public class HomeEsperto extends GenericForwardComposer {

	private static final long serialVersionUID = 862041909319557464L;
	private static final Logger logger = Logger.getLogger(HomeEsperto.class);
	private Esperto user;
	private Checkbox chk_privacy;
	private Checkbox chk_lingua;
	private Button btn_invia;
	private Button btn_cv_pdf;
	private Button btn_cv_pdf_ante;
	boolean approvato = false;
	private Button btn_aggiungi_doc;
	private Button btn_vedi_doc;
	private Button btn_elimina_doc;
	
	private Window winExportFile = null;
	
    @SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.user = (Esperto) session.getAttribute("login");
		//btn_aggiungi_doc.setVisible(user.getDocumentoAllegato() == null);
		//btn_vedi_doc.setVisible(user.getDocumentoAllegato() != null);
		//btn_elimina_doc.setVisible(user.getDocumentoAllegato() != null);
	
		
	
	}
    public void onUpload$btn_aggiungi_doc(UploadEvent event) throws Exception {
		Media media = event.getMedia();
		String estensione = getFileExtension(media.getName());
		byte [] arrData = getBytesFromStream(media.getStreamData());
		DocumentoAllegatoEsperto allegato = new DocumentoAllegatoEsperto();
		allegato.setAllegato(arrData);
		allegato.setEstensione(estensione);
		allegato.setContentType(media.getContentType());
		InserisciDocumentoEspertoCommand cmd = new InserisciDocumentoEspertoCommand(user, allegato);
		cmd.execute();
		Messagebox.show("L'Allegato è stato caricato con successo.", "Inserimento Allegato",
				Messagebox.OK, Messagebox.INFORMATION);
		//btn_aggiungi_doc.setVisible(false);
		//btn_vedi_doc.setVisible(true);
		//btn_elimina_doc.setVisible(true);
	}
	
	public void onClick$btn_elimina_doc(Event event) throws Exception{
		
		EliminaDocumentoEspertoCommand cmd = new EliminaDocumentoEspertoCommand(user);
		cmd.execute();
		Messagebox.show("L'Allegato è stato rimosso con successo.", "Eliminazione Allegato",
				Messagebox.OK, Messagebox.INFORMATION);
		//btn_elimina_doc.setVisible(false);
		//btn_vedi_doc.setVisible(false);
		//btn_aggiungi_doc.setVisible(true);
	}

	private byte[] getBytesFromStream(InputStream is){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int abyte = is.read();
			while (abyte != -1)
			{
				baos.write(abyte);
				abyte = is.read();
			}
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	private String getFileExtension(String fileName){
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos+1);
	}

	public void onClick$btn_vedi_doc(Event event) throws Exception{
	
		DocumentoAllegatoEsperto allegato = user.getDocumentoAllegato();
		if (allegato != null){
			String fileName="Documento_"+user.getUsername()+"."+allegato.getEstensione();
			Filedownload.save(allegato.getAllegato(), allegato.getContentType(), fileName);
		}
		else{
			Messagebox.show("Non è stato inserito nessun file come allegato.", "CSEA Elenco Esperti", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
    
	public void onCreate$win_home_esperto(Event evt) throws Exception {
		Esperto user = (Esperto) session.getAttribute("login");
		if (user != null) {
			if(!user.getInviato()){
				btn_invia.setVisible(true);
				chk_privacy.setVisible(true);
				chk_lingua.setVisible(true);
				btn_cv_pdf_ante.setVisible(true);
				btn_cv_pdf.setVisible(false);
			}else{
				//inviato
				btn_invia.setVisible(false);
				chk_privacy.setVisible(false);
				chk_lingua.setVisible(false);
				btn_cv_pdf_ante.setVisible(false);
				btn_cv_pdf.setVisible(true);
			}	
		} else {
			Executions.sendRedirect("login.zul");
		}
	}



	public void onClick$btn_invia(Event evt) throws Exception {
		Esperto user = (Esperto) session.getAttribute("login");

		// ******************************************************************************
		RicercaIstruzioneCommand ric = new RicercaIstruzioneCommand(user.getId());
		ric.execute();
		Set<Istruzione> istr = ric.getSet();
		boolean istruzioniExist = !istr.isEmpty();

		// ******************************************************************************		
		EsperienzeLavorativeCommand relc = new EsperienzeLavorativeCommand(user.getId());
		Set<EsperienzaLavorativa> esp = relc.list();
		boolean esperienzeExist = !esp.isEmpty();

		// ******************************************************************************		
		RicercaCompetenzeCommand rcc = new RicercaCompetenzeCommand(user.getId());
		rcc.execute();
		Set<Competenza> comp = rcc.getSet();
		boolean competenzeExist = !comp.isEmpty();

		// ******************************************************************************		
		logger.debug(istruzioniExist+" - "+esperienzeExist+" - "+competenzeExist);
		// ******************************************************************************		
		DocumentoAllegatoEsperto allegato = user.getDocumentoAllegato();
		//if (allegato != null){
			if(chk_privacy.isChecked()){

				if(chk_lingua.isChecked()){

					if(istruzioniExist && esperienzeExist && competenzeExist){

						boolean flagCVNuovo = (user.getDataInvio() ==  null);
						InviaDatiEspertoCommand command = new InviaDatiEspertoCommand(user.getId());
						command.execute();
						// Imposto in sessione il flag inviato
						user.setInviato(true);
						user.setDataInvio(new Date());
						// imposto in sessione il codice domanda generato 
						user.setCodiceDomanda(command.getCodiceDomanda());
						// genero il path assoluto del file pdf
	
						int numProgr = 1;
						
						
						String basePath = getDestPath(approvato) +	getBaseNameFile(user);
						String pathCV = basePath + "_" + numProgr + ".pdf";
						
						String basePathPublish = getDestPubblicabile() + getBaseNameFile(user);
						String pathCVPublish = basePathPublish + "_" + numProgr + ".pdf";
						
						while (new File(pathCV).exists()){
							++numProgr;
							pathCV = basePath + "_" + numProgr + ".pdf";
						}
						
						while (new File(pathCVPublish).exists())
						{
							++numProgr;
							pathCVPublish = basePath + "_" + numProgr + ".pdf";
						}
	
						File cvToGenerate = new File(pathCV);
						if (!cvToGenerate.getParentFile().exists())
							cvToGenerate.getParentFile().mkdirs();
						PdfUtils.generaPDF(user,pathCV, false);
						
						File cvToPublish = new File(pathCVPublish);
						if (!cvToPublish.getParentFile().exists())
							cvToPublish.getParentFile().mkdirs();
						PdfUtils.generaPDF(user,pathCVPublish, true);
						
						String fileName="CV_"+user.getUsername()+"."+"pdf";
						Filedownload.save(PdfUtils.generaPDFContent(user, false, false), "application/pdf", fileName);
						
						//InvioMailHelper mailHelper = new InvioMailHelper();
	
						btn_invia.setVisible(false);
						chk_privacy.setVisible(false);
						chk_lingua.setVisible(false);
						btn_cv_pdf_ante.setVisible(false);
						btn_cv_pdf.setVisible(true);
						
	
//						if (flagCVNuovo)
//						{
//							mailHelper.invioMailCVInviato(user.getEmail(), user.getNome(), user.getCognome());
//							mailHelper.invioNotificaCVInviato(user.getNome(), user.getCognome(), user.getDataInvio());
//						}
//						else
//						{
//							mailHelper.invioMailCVAggiornato(user.getEmail(), user.getNome(), user.getCognome());
//							mailHelper.invioNotificaCVAggiornato(user.getNome(), user.getCognome(), user.getDataInvio());
//						}
						
						session.setAttribute("login", user);
						initModel();
					//						user.setInviato(true);
					}else{
						Messagebox.show("Il curriculum vitae non è stato compilato in tutte le sue parti.",
								"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
					}
				}else{
					Messagebox.show("E' obbligatoria la conoscenza della lingua italiana.",
							"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}else{
				Messagebox.show("E' obbligatorio dare il consenso ai dati personali.",
						"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			
		
			
//		}else{
//			Messagebox.show("E' obbligatorio inserire l'immagine della propria carta d'identità.",
//					"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
//		}
	}

	
	
	public void initModel() throws Exception{
		try{
			this.user = (Esperto) session.getAttribute("login");
			if(user.getInviato()) {
				String redirectUrlInviato = "/pages/BandiEspertiPages/homeEspertoInviato.zul";
				Executions.sendRedirect(redirectUrlInviato);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	/*
	public void onClick$btn_invia_ante(Event evt) throws Exception {
		Esperto user = (Esperto) session.getAttribute("login");

		// ******************************************************************************
		RicercaIstruzioneCommand ric = new RicercaIstruzioneCommand(user.getId());
		ric.execute();
		Set<Istruzione> istr = ric.getSet();
		boolean istruzioniExist = !istr.isEmpty();

		// ******************************************************************************		
		RicercaEsperienzeLavorativeCommand relc = new RicercaEsperienzeLavorativeCommand(user.getId());
		relc.execute();
		Set<EsperienzaLavorativa> esp = relc.getSet();
		boolean esperienzeExist = !esp.isEmpty();

		// ******************************************************************************		
		RicercaCompetenzeCommand rcc = new RicercaCompetenzeCommand(user.getId());
		rcc.execute();
		Set<Competenza> comp = rcc.getSet();
		boolean competenzeExist = !comp.isEmpty();

		// ******************************************************************************		
		logger.debug(istruzioniExist+" - "+esperienzeExist+" - "+competenzeExist);
		// ******************************************************************************		

		if(istruzioniExist && esperienzeExist && competenzeExist){

			String pathCVAnteprima = getDestPath() + user.getUsername() + "_" + user.getCodiceDomanda() + "Anteprima.pdf";
			PdfUtils.genreaPDF(user,true, pathCVAnteprima);

			btn_invia.setVisible(true);
			chk_privacy.setVisible(true);
			chk_lingua.setVisible(true);
			btn_cv_pdf.setVisible(false);
		}else{
			Messagebox.show("Il curriculum vitae non è stato compilato in tutte le sue parti.",
					"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}

	}*/
	/**
     public void onUpload$fileupload(UploadEvent event) {
    	 showSpace.removeChild(img);
	     Media media = event.getMedia();
	     img.setContent((org.zkoss.image.Image)media);
	     img.setParent(showSpace);
	     
	    
	  }  
	**/
	  
	  public void onUpload$uploadBtn(Event event) {
		    ForwardEvent fe = (ForwardEvent)event;
		    UploadEvent origin = (UploadEvent) fe.getOrigin();
		    Media media = origin.getMedia();
		    Image img = new Image();
		    img.setContent((org.zkoss.image.Image)media);
		  
		 }
	
	public void onClick$btn_cv_pdf_ante(Event event) throws Exception
	{
		/*		Esperto user = (Esperto) session.getAttribute("login");
		String confPath = "/WEB-INF/conf/conf.properties";
		WebApp webApp = Executions.getCurrent().getDesktop().getWebApp();
		Properties props = new XProperties();
		props.load(new FileInputStream(webApp.getRealPath(confPath)));
		File pathCVFile = new File(props.getProperty("cv.path")  + user.getCognome() + user.getNome() + "_" + user.getCodiceDomanda() + ".pdf");
		 if (pathCVFile.exists())
			 Filedownload.save(pathCVFile, "application/pdf");
		else
	   	 	Messagebox.show("Attenzione: file PDF non presente! "+pathCVFile.getName(), 
	   			 "CCSE Bandi Esperti", Messagebox.OK, Messagebox.EXCLAMATION);*/
		Esperto user = (Esperto) session.getAttribute("login");

		// ******************************************************************************
		RicercaIstruzioneCommand ric = new RicercaIstruzioneCommand(user.getId());
		ric.execute();
		Set<Istruzione> istr = ric.getSet();
		boolean istruzioniExist = !istr.isEmpty();

		// ******************************************************************************		
		EsperienzeLavorativeCommand relc = new EsperienzeLavorativeCommand(user.getId());
		Set<EsperienzaLavorativa> esp = relc.list();
		boolean esperienzeExist = !esp.isEmpty();

		// ******************************************************************************		
		RicercaCompetenzeCommand rcc = new RicercaCompetenzeCommand(user.getId());
		rcc.execute();
		Set<Competenza> comp = rcc.getSet();
		boolean competenzeExist = !comp.isEmpty();

		// ******************************************************************************		
		logger.debug(istruzioniExist+" - "+esperienzeExist+" - "+competenzeExist);
		// ******************************************************************************		

		if(istruzioniExist && esperienzeExist && competenzeExist){

			//PdfUtils.genreaPDF(user,true, pathCVAnteprima);
			
			btn_invia.setVisible(true);
			chk_privacy.setVisible(true);
			chk_lingua.setVisible(true);
			btn_cv_pdf.setVisible(false);
			
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("byteArr", arrPdf);
//			params.put("winTitle", "Anteprima CV");
//			params.put("estensione", "pdf");
//			params.put("contentType", "application/pdf");
		
			winExportFile = new Window();
			winExportFile.setParent(self);
			winExportFile.setTitle("pathCVAnteprima");
			winExportFile.setWidth("800px");
			winExportFile.setHeight("600px");
			winExportFile.setClosable(true);
			winExportFile.setBorder("normal");
			winExportFile.setStyle("position:absolute");
			//Filedownload.save(arrPdf, "application/pdf", pathCVAnteprima);
			
			String filePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");		
			String pathCVAnteprima = user.getUsername() + "_" + user.getCodiceDomanda() + "Anteprima.pdf";
			byte [] arrPdf = PdfUtils.generaPDFContent(user, true, false);
			String pathFileNamePDF = filePath+pathCVAnteprima;
			String aa = Executions.getCurrent().getContextPath();
			URL a = Executions.getCurrent().getDesktop().getWebApp().getResource(pathFileNamePDF);
			String ggg = Executions.getCurrent().toAbsoluteURI(pathCVAnteprima, false);
			FileOutputStream fos = new FileOutputStream(pathFileNamePDF);
			fos.write(arrPdf);
			fos.close();
			
			AMedia media = new AMedia(pathCVAnteprima, "pdf", "application/pdf", arrPdf);
			//Executions.getCurrent().getDesktop().getDownloadMediaURI(media, pathFilePDF);
			//Filedownload.save(media, filePath+pathCVAnteprima);
			
			Iframe frame = new Iframe();
			frame.setWidth("800px");
			frame.setHeight("600px");
			frame.setContent(media);
			frame.setId("frame");
			//frame.applyProperties();
			//frame.setSrc(aa+ggg);
			winExportFile.appendChild(frame);
			winExportFile.doModal();
			//Window w = (Window) Executions.createComponents("/pages/BandiEspertiPages/anteprimaDoc.zul", this.self, params);
			/*
			Iframe frm = new Iframe();
			AMedia media = new AMedia(null, null, null, arrPdf);
			frm.setContent(media);
			 */
			//w.appendChild(frm);
			//w.doModal();
		}else{
			Messagebox.show("Il curriculum vitae non è stato compilato in tutte le sue parti.",
					"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}

	}

	private String getBaseNameFile(Esperto user) {
		
		String nome=user.getNome();
		String cognome=user.getCognome();
		nome = nome.replace(" ", "");
		nome = nome.replace("'", "");
		cognome = cognome.replace(" ", "");
		cognome = cognome.replace("'", "");
		return cognome + nome + "_" + user.getCodiceDomanda();
	}
	
	public String getDestPath(boolean approvato)
	{
		try {
			ConfigHelper helper = new ConfigHelper();
			Properties configProps = helper.getConfigProperties();
			String cvCfgFile = configProps.getProperty(ConfigHelper.CV_KEY);
			Properties props = new Properties();
			props.load(new FileInputStream(cvCfgFile));
			String key="cv.path.approvati";
			if (!approvato){
				key = "cv.path.non_approvati";
			}
			return props.getProperty(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	private String getDestPubblicabile()
	{
		try {
			ConfigHelper helper = new ConfigHelper();
			Properties configProps = helper.getConfigProperties();
			String cvCfgFile = configProps.getProperty(ConfigHelper.CV_KEY);
			Properties props = new Properties();
			props.load(new FileInputStream(cvCfgFile));
			return props.getProperty("cv.path.pubblicabili");
		
		} catch (Exception e) {
			return null;
		}
	}
}
