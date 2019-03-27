package it.ccse.bandiEsperti.zk.composers;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import it.ccse.bandiEsperti.business.dto.AllegatoDTO;
import it.ccse.bandiEsperti.business.dto.EspertoDTO;
import it.ccse.bandiEsperti.utils.cvFileHelper.CVFileFilter;
import it.ccse.bandiEsperti.utils.ConfigHelper;
import it.ccse.bandiEsperti.utils.cvFileHelper.CVHelper;
import it.ccse.bandiEsperti.zk.view.BeRows;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

public class CommonComposer extends GenericForwardComposer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void onScaricaAllegatoPubbl(ForwardEvent event) {
		try {
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO esperto = (EspertoDTO)rigaAttiva.getValue();
			AllegatoDTO allDTO = esperto.getAllegatoPubblicazioni();
			if (allDTO != null)
			{
				String fileName="Pubblicazioni_"+esperto.getCodiceDomanda()+"."+allDTO.getEstensione();
				Filedownload.save(allDTO.getContent(), allDTO.getContentType(), fileName);
			}
			else
				Messagebox.show("Nessun allegato presente", "CCSE Bandi Esperti", Messagebox.OK, 
						Messagebox.INFORMATION);
		} catch (Exception ex) {
			throw new WrongValueException("Errore nella fase di download dell'allegato delle pubblicazioni: "+ex.getMessage());
		} finally {
			Clients.clearBusy();
		}
	}

	protected void callWinDettaglio(ForwardEvent event, Map<String, Object> params, String path, String title) throws SuspendNotAllowedException, InterruptedException{
		Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
		EspertoDTO espertoSelezionato = (EspertoDTO)rigaAttiva.getValue();
		params.put("esperto", espertoSelezionato);
		Window window = (Window) Executions.createComponents(path, this.self, params);
		window.setWidth("570px");
		window.setClosable(true);
		window.setTitle(title);
		window.doModal();
	}
	public void onVediDettagli(ForwardEvent event, BeRows beRows) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("beRows", beRows);
			params.put("ricerca", true);
			String path = "/pages/BandiEspertiPages/dettaglioRicercaEspertoSelezionato.zul";
			callWinDettaglio(event,params,path, "Contatti");

		} catch (Exception ex) {
			throw new WrongValueException("Errore nella apertura della finestra di dettaglio: "+ex.getMessage());
		} finally {
			Clients.clearBusy();
		}
	}
	public void onVediDettagli(ForwardEvent event) {
		try {
			StringBuilder sb = new StringBuilder();
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO espertoSelezionato = (EspertoDTO)rigaAttiva.getValue();
			String path = "/pages/BandiEspertiPages/dettaglioEspertoSelezionato.zul";
			sb.append("Esperto: ").append(espertoSelezionato.getNome()).append(" ").append(espertoSelezionato.getCognome()).append(" - Info Approvazione");
			callWinDettaglio(event, new HashMap<String, Object>(),path, sb.toString());

		} catch (Exception ex) {
			throw new WrongValueException("Errore nella apertura della finestra di dettaglio: "+ex.getMessage());
		} finally {
			Clients.clearBusy();
		}
	}
	
	public void onVisualizzaCVPubli(ForwardEvent event){
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO esperto = (EspertoDTO)rigaAttiva.getValue();

			//String pathCVDir = getDestPath(esperto.isApprovato());
			String pathCVDir = getDestPublish();
			String pathCVFile = getCVPath(pathCVDir, esperto, true);

			if (pathCVFile != null)
				showCVAnteprima(pathCVFile);
			else
				Messagebox.show("Attenzione: file PDF non presente! ");

		}catch(Exception ex){
			throw new WrongValueException("Errore nella ricerca di download del CV: " + ex.getMessage());
		}
	}	
	
	public void onVisualizzaCompetenze(ForwardEvent event){
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO espertoSelezionato = (EspertoDTO)rigaAttiva.getValue();

			Map<String,Object> params = new HashMap<String,Object>();
			params.put("idEsperto", espertoSelezionato.getId());
			
			Window window = (Window) Executions.createComponents("/pages/BandiEspertiPages/welcomePageAdminCompetenze.zul", this.self, params);
			window.setWidth("800px");
			window.setClosable(true);
			window.setTitle("Esperto: "+espertoSelezionato.getNome()+" "+espertoSelezionato.getCognome()+" - Competenze Acquisite");

			window.doModal();
		}catch(Exception ex){
			throw new WrongValueException("Errore nella ricerca dele Competenze: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
	}

	public void onVisualizzaEsperienze(ForwardEvent event){
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO espertoSelezionato = (EspertoDTO)rigaAttiva.getValue();

			Map<String,Object> params = new HashMap<String,Object>();
			params.put("idEsperto", espertoSelezionato.getId());
		
			Window window = (Window) Executions.createComponents("/pages/BandiEspertiPages/welcomePageAdminEsperienze.zul", this.self, params);
			window.setWidth("970px");
			window.setClosable(true);
			window.setTitle("Esperto: "+espertoSelezionato.getNome()+" "+espertoSelezionato.getCognome()+" - Esperienze");

			window.doModal();
		}catch(Exception ex){
			throw new WrongValueException("Errore nella ricerca delle Esperienze: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
	}
	
	
	private String getDestPath(boolean approvato)
	{
		try {
			ConfigHelper helper = new ConfigHelper();
			Properties configProps = helper.getConfigProperties();
			String cvCfgFile = configProps.getProperty(ConfigHelper.CV_KEY);
			Properties props = new Properties();
			props.load(new FileInputStream(cvCfgFile));
			String key = "cv.path.approvati";
			if (!approvato){
				key="cv.path.non_approvati";
			}
		
			return props.getProperty(key);
	
		} catch (Exception e) {
			return null;
		}
	}

	private String getDestPublish()
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
	
	public void onVisualizzaCV(ForwardEvent event){
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent().getParent().getParent();
			EspertoDTO esperto = (EspertoDTO)rigaAttiva.getValue();

			String pathCVDir = getDestPath(esperto.isApprovato());
			String pathCVFile = getCVPath(pathCVDir, esperto, false);

			if (pathCVFile != null)
				showCVAnteprima(pathCVFile);
			else
				Messagebox.show("Attenzione: file PDF non presente! ");

		}catch(Exception ex){
			throw new WrongValueException("Errore nella ricerca di download del CV: " + ex.getMessage());
		}
	}
	
	private void showCVAnteprima(String cvPath) throws Exception
	{
		CVHelper helper = new CVHelper();
		byte arrPdf[] = helper.getContent(cvPath);
		if (arrPdf == null)
			return;
		File f = new File(cvPath);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("byteArr", arrPdf);
		params.put("winTitle", "Anteprima CV - "+f.getName());
		params.put("estensione", "pdf");
		params.put("contentType", "application/pdf");
		Window w = (Window) Executions.createComponents("/pages/BandiEspertiPages/anteprimaDoc.zul", this.self, params);
		w.doModal();
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

}
