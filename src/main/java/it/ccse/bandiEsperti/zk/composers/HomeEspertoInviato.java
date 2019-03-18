package it.ccse.bandiEsperti.zk.composers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import it.ccse.bandiEsperti.business.command.EliminaCartaIdentitaEspertoCommand;
import it.ccse.bandiEsperti.business.command.EliminaDocumentoEspertoCommand;
import it.ccse.bandiEsperti.business.command.InserisciCartaIdentitaEspertoCommand;
import it.ccse.bandiEsperti.business.command.InserisciDocumentoEspertoCommand;
import it.ccse.bandiEsperti.business.command.ModificaEspertoCommand;
import it.ccse.bandiEsperti.business.model.CartaIdentitaEsperto;
import it.ccse.bandiEsperti.business.model.DocumentoAllegatoEsperto;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.utils.ConfigHelper;
import it.ccse.bandiEsperti.utils.PdfUtils;
import it.ccse.bandiEsperti.utils.invioMail.InvioMailHelper;


public class HomeEspertoInviato extends GenericForwardComposer {

	private static final long serialVersionUID = 862041909319557464L;
	private static final Logger logger = Logger.getLogger(HomeEspertoInviato.class);
	private Esperto user;
	
	private Button btn_vedi_doc;
	
	private Button btn_aggiungi_doc;
	
	private Button btn_elimina_doc;
	
	
	private Button btn_vedi_ci;
	
	private Button btn_aggiungi_ci;
	
	private Button btn_elimina_ci;
	
	private Button btn_trans_cand;
	
    
    @SuppressWarnings("unchecked")
    @Override
	public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
		this.user = (Esperto) session.getAttribute("login");
		
		
		btn_vedi_doc.setVisible(user.getDocumentoAllegato() != null);
		btn_vedi_ci.setVisible(user.getCartaIdentita() != null);
		
		if(user.getCandTrasmessa()){
			btn_trans_cand.setVisible(false);
			btn_aggiungi_doc.setVisible(false);
			btn_elimina_doc.setVisible(false);
			
			btn_aggiungi_ci.setVisible(false);
			btn_elimina_ci.setVisible(false);
			
			Messagebox.show("Candidatura trasmessa con successo.", "Information",
					Messagebox.OK, Messagebox.INFORMATION);
			
		}else{
			btn_trans_cand.setVisible(true);
			btn_aggiungi_doc.setVisible(user.getDocumentoAllegato() == null);
			btn_elimina_doc.setVisible(user.getDocumentoAllegato() != null);
			
			btn_aggiungi_ci.setVisible(user.getCartaIdentita() == null);
			btn_elimina_ci.setVisible(user.getCartaIdentita() != null);
			
		}
		
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
		btn_aggiungi_doc.setVisible(false);
		btn_vedi_doc.setVisible(true);
		btn_elimina_doc.setVisible(true);
	}
    
    public void onUpload$btn_aggiungi_ci(UploadEvent event) throws Exception {
		Media media = event.getMedia();
		String estensione = getFileExtension(media.getName());
		byte [] arrData = getBytesFromStream(media.getStreamData());
		CartaIdentitaEsperto allegato = new CartaIdentitaEsperto();
		allegato.setAllegato(arrData);
		allegato.setEstensione(estensione);
		allegato.setContentType(media.getContentType());
		InserisciCartaIdentitaEspertoCommand cmd = new InserisciCartaIdentitaEspertoCommand(user, allegato);
		cmd.execute();
		Messagebox.show("L'Allegato è stato caricato con successo.", "Inserimento Allegato",
				Messagebox.OK, Messagebox.INFORMATION);
		btn_aggiungi_ci.setVisible(false);
		btn_vedi_ci.setVisible(true);
		btn_elimina_ci.setVisible(true);
	}
	
	public void onClick$btn_elimina_doc(Event event) throws Exception{
		
		EliminaDocumentoEspertoCommand cmd = new EliminaDocumentoEspertoCommand(user);
		cmd.execute();
		Messagebox.show("L'Allegato è stato rimosso con successo.", "Eliminazione Allegato",
				Messagebox.OK, Messagebox.INFORMATION);
		btn_elimina_doc.setVisible(false);
		btn_vedi_doc.setVisible(false);
		btn_aggiungi_doc.setVisible(true);
	}
	
	
	public void onClick$btn_elimina_ci(Event event) throws Exception{
		
		EliminaCartaIdentitaEspertoCommand cmd = new EliminaCartaIdentitaEspertoCommand(user);
		cmd.execute();
		Messagebox.show("L'Allegato è stato rimosso con successo.", "Eliminazione Allegato",
				Messagebox.OK, Messagebox.INFORMATION);
		btn_elimina_ci.setVisible(false);
		btn_vedi_ci.setVisible(false);
		btn_aggiungi_ci.setVisible(true);
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
			Messagebox.show("Non è stato inserito nessun file come allegato.", "CCSE Bandi Esperti", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	
	public void onClick$btn_vedi_ci(Event event) throws Exception{
		
		CartaIdentitaEsperto allegato = user.getCartaIdentita();
		if (allegato != null){
			String fileName="CartaIdentita_"+user.getUsername()+"."+allegato.getEstensione();
			Filedownload.save(allegato.getAllegato(), allegato.getContentType(), fileName);
		}
		else{
			Messagebox.show("Non è stato inserito nessun file come allegato.", "CCSE Bandi Esperti", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
 
	public void onCreate$win_home_esperto(Event evt) throws Exception {
		Esperto user = (Esperto) session.getAttribute("login");
		if (user == null) {
			Executions.sendRedirect("login.zul");
		}
		
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
	
		Esperto user = (Esperto) session.getAttribute("login");
		byte [] arrPdf = PdfUtils.generaPDFContent(user, true, false);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("byteArr", arrPdf);
		params.put("winTitle", "Anteprima CV");
		params.put("estensione", "pdf");
		params.put("contentType", "application/pdf");
		Window w = (Window) Executions.createComponents("/pages/BandiEspertiPages/anteprimaDoc.zul", this.self, params);
	
		w.doModal();
		

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
	
	private String getDestPath(boolean approvato)
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
	
	public void onClick$btn_trans_cand(Event evt) throws Exception {
		Esperto user = (Esperto) session.getAttribute("login");

		DocumentoAllegatoEsperto allegato = user.getDocumentoAllegato();
		CartaIdentitaEsperto cartaIdentita = user.getCartaIdentita();
		
		if (allegato != null && cartaIdentita!= null){

			boolean flagCVNuovo = (user.getDataInvio() ==  null);
			
			// Imposto in sessione il Cand Trasmessa
			user.setCandTrasmessa(true);
						
			ModificaEspertoCommand command = new ModificaEspertoCommand(user);
			command.execute();
			
			
						
			InvioMailHelper mailHelper = new InvioMailHelper();
	
			if (flagCVNuovo){
				mailHelper.invioMailCVInviato(user.getEmail(), user.getNome(), user.getCognome());
				mailHelper.invioNotificaCVInviato(user.getNome(), user.getCognome(), user.getDataInvio());
			}else{
				mailHelper.invioMailCVAggiornato(user.getEmail(), user.getNome(), user.getCognome());
				mailHelper.invioNotificaCVAggiornato(user.getNome(), user.getCognome(), user.getDataInvio());
			}
						
			btn_trans_cand.setVisible(false);
			btn_elimina_doc.setVisible(false);
			
			
			session.setAttribute("login", user);
			initModel();
			
		}else{
			Messagebox.show("E' obbligatorio caricare il curriculum firmato e inserire l'immagine della propria carta d'identità.",
					"Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
}
