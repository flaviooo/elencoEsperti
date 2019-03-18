package it.ccse.bandiEsperti.utils;

import it.ccse.bandiEsperti.business.command.RicercaCarrieraCommand;
import it.ccse.bandiEsperti.business.command.RicercaCompetenzeCommand;
import it.ccse.bandiEsperti.business.command.EsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.command.RicercaIstruzioneCommand;
import it.ccse.bandiEsperti.business.command.RicercaPrecedentiIncarichiCommand;
import it.ccse.bandiEsperti.business.command.RicercaPubblicazioniCommand;
import it.ccse.bandiEsperti.business.dto.EspertoRicercaDinamicaDTO;
import it.ccse.bandiEsperti.business.dto.RicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.Associazioni;
import it.ccse.bandiEsperti.business.model.Competenza;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Istruzione;
import it.ccse.bandiEsperti.business.model.PrecedenteIncarico;
import it.ccse.bandiEsperti.business.model.Pubblicazione;
import it.ccse.bandiEsperti.zk.comparators.ComparatorFactory;
import it.ccse.bandiEsperti.zk.comparators.EsperienzeProfessionaliComparator;
import it.ccse.bandiEsperti.zk.comparators.IstruzioneComparator;
import it.ccse.bandiEsperti.zk.comparators.PubblicazioneComparator;



import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


public class PdfUtils {
	
	public static void generaPDF(Esperto esperto, String pathCV, boolean publish)
	{
		try {
			FileOutputStream fos = new FileOutputStream(pathCV);
			byte arrCnt[] = generaPDFContent(esperto, false, publish); 
			fos.write(arrCnt);
			fos.close();
		} catch (Exception e) {
			
		}
	}
	
	public static byte[] generaPdfContentRicercaEsperti(RicercaEspertiDTO ricercaDTO, Date dataElaborazione)
	{
		try {
			ConfigHelper cfgHelper = new ConfigHelper();
			Properties cfgProperties = cfgHelper.getConfigProperties();
			Properties props = new Properties();
			props.load(new FileInputStream(cfgProperties.getProperty(ConfigHelper.CV_KEY)));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter export = new JRPdfExporter();
			List<RicercaEspertiDTO> espertiList = new ArrayList<RicercaEspertiDTO>();
			espertiList.add(ricercaDTO);
			File templateFile = new File(props.getProperty("cv.ricerca.template.path"));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("DataOra", dataElaborazione);
			JasperPrint print = JasperFillManager.fillReport(templateFile.getPath(),
					paramMap, new JRBeanCollectionDataSource(espertiList));
			export.setParameter(JRExporterParameter.JASPER_PRINT, print);
			export.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			export.exportReport();
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
		
	}
	public static byte[] generaExcelContentAssociazioniEsperti(List<Associazioni> associazioni, Date dataElaborazione)
	{
		try {
			ConfigHelper cfgHelper = new ConfigHelper();
			Properties cfgProperties = cfgHelper.getConfigProperties();
			Properties props = new Properties();
			props.load(new FileInputStream(cfgProperties.getProperty(ConfigHelper.CV_KEY)));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			File templateFile = new File(props.getProperty("associazioni.export.template.path"));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("DataOra", dataElaborazione);
			JRDataSource datasource = new JRBeanCollectionDataSource(associazioni);
			JasperReport jasperReport = JasperCompileManager.compileReport(templateFile.getPath());     
			JasperPrint print = JasperFillManager.fillReport(jasperReport, paramMap, datasource);
			JRXlsExporter exporterXLS = new JRXlsExporter(); 
			
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print); 
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos); 
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		
			exporterXLS.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 1000);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE); 
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
			exporterXLS.exportReport(); 
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public static byte[] generaExcelContentRicercaEsperti(Set<EspertoRicercaDinamicaDTO> espertoRicercaDinamicaDTO, Date dataElaborazione, String filtro)
	{
		try {
			ConfigHelper cfgHelper = new ConfigHelper();
			Properties cfgProperties = cfgHelper.getConfigProperties();
			Properties props = new Properties();
			props.load(new FileInputStream(cfgProperties.getProperty(ConfigHelper.CV_KEY)));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			List<EspertoRicercaDinamicaDTO> listEpertoRicercaDinamicaDTO = new ArrayList<EspertoRicercaDinamicaDTO>(espertoRicercaDinamicaDTO);
			File templateFile = new File(props.getProperty("ricerca.export.template.path"));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("DataOra", dataElaborazione);
			paramMap.put("filtro", filtro);
			JRDataSource datasource = new JRBeanCollectionDataSource(listEpertoRicercaDinamicaDTO);
			JasperReport jasperReport = JasperCompileManager.compileReport(templateFile.getPath());     
			JasperPrint print = JasperFillManager.fillReport(jasperReport, paramMap, datasource);
			JRXlsExporter exporterXLS = new JRXlsExporter(); 
			
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print); 
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos); 
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 1000);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE); 
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
			exporterXLS.exportReport(); 
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public static byte [] generaPDFContent(Esperto e, boolean anteprima, boolean publish) throws Exception{

		// Set the Header
		Document doc = new Document(PageSize.A4, 50, 50, 10, 30);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
	
		try {
			ConfigHelper cfgHelper = new ConfigHelper();
			Properties cfgProperties = cfgHelper.getConfigProperties();
			Properties props = new Properties();
			props.load(new FileInputStream(cfgProperties.getProperty(ConfigHelper.CV_KEY)));
			Image img = null;
			if(!anteprima){
				img = Image.getInstance(props.getProperty("img.header.pdf.path"));
			}else{
				img = Image.getInstance(props.getProperty("img.header.ante.path"));	
			}
			img.setAlignment(Image.ALIGN_CENTER);
			img.scalePercent(80);

			Paragraph chunkPara = new Paragraph();
			chunkPara.setSpacingAfter(-10);
			chunkPara.add(img);
			HeaderFooter header = new HeaderFooter(chunkPara, false);
			header.setBorder(0);
			header.setTop(0);

			// Set the Footer
			Phrase infoFooter = new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 8, Color.DARK_GRAY));

			HeaderFooter footer = new HeaderFooter(infoFooter, true);
			footer.setAlignment(Element.ALIGN_RIGHT);

			//OutputStream os = new FileOutputStream(pathCV);
			PdfWriter.getInstance(doc, os);
			doc.setHeader(header);
			doc.setFooter(footer);
			doc.open();
			
			/**
			Paragraph nomeAzienda = new Paragraph(e.getCognome() + " " + e.getNome() + "\n", FontFactory.getFont(FontFactory.HELVETICA, 14, Color.blue));
			nomeAzienda.setAlignment(Paragraph.ALIGN_CENTER);

			Paragraph codeCCSE = new Paragraph("Codice Domanda: " + e.getCodiceDomanda() + "\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Color.blue));
			codeCCSE.setAlignment(Paragraph.ALIGN_CENTER);
			
			Paragraph candidatura = new Paragraph("Candidatura per l’inserimento nell’Elenco degli esperti di cui all’art. 11 d.m. 8 marzo 2006\n\n\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Color.DARK_GRAY));
			candidatura.setAlignment(Paragraph.ALIGN_CENTER);
			candidatura.getFirstLineIndent();
			**/
			Paragraph privacy = new Paragraph("\nDichiaro, ai sensi del D.lgs. n. 196 del 30 giugno 2003, di essere informato/a che i dati personali raccolti saranno trattati dalla CCSE anche con strumenti informatici, esclusivamente nell’ambito del procedimento per il quale la presente dichiarazione viene resa, per lo svolgimento delle sue funzioni istituzionali e nei limiti di legge.\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Color.DARK_GRAY));
			privacy.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			privacy.getFirstLineIndent();
			
			Paragraph lingua = new Paragraph("\nDichiaro di avere padronanza della lingua italiana.\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Color.DARK_GRAY));
			lingua.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			lingua.getFirstLineIndent();
			
			Paragraph allegati = new Paragraph("\nALLEGATI:\n\t   1) Documento di identità \n\t   2) Eventuali ulteriori pubblicazione rispetto a quelle inserite nella sezione “Pubblicazioni”.\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Color.DARK_GRAY));
			allegati.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			allegati.getFirstLineIndent();
			
			Paragraph vuoto = new Paragraph(" \n\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Color.blue));
			vuoto.setAlignment(Paragraph.ALIGN_CENTER);

			PdfPTable tabDatiAnagrafici = creaTabellaDatiAnagrafici(e, "INFORMAZIONI PERSONALI",props, publish);
			PdfPTable tabDatiIstruzioneFormazione = creaTabellaIstruzioneFormazione(e,"ISTRUZIONE E FORMAZIONE");
			PdfPTable tabDatiCompetenzePersonali = creaTabellaCompetenzePersonali(e,"COMPETENZE");
			PdfPTable tabDatiAltreEsperienzeProfessionale = creaTabellaAltreEsperienzeProfessionale(e,"ESPERIENZE PROFESSIONALI");
			PdfPTable tabDatiPrecedentiIncarichi = creaTabellaPrecedentiIncarichi(e,"INCARICHI DI VALUTAZIONE PROGETTI");
			PdfPTable tabPubblicazioni = creaTabellaUlterioriInformazioni(e,"PUBBLICAZIONI");
		
			
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Paragraph firma = new Paragraph("FIRMA                    " ,FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.GRAY));
			firma.setAlignment(Paragraph.ALIGN_RIGHT);
			Paragraph line = new Paragraph("__________________________" ,FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.GRAY));
			line.setAlignment(Paragraph.ALIGN_RIGHT);
			
			
			Paragraph nota = new Paragraph("N.B.: è possibile firmare il documento con firma autografa o con firma digitale (p7m)" ,FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.GRAY));
			nota.setAlignment(Paragraph.ALIGN_CENTER);
			nota.setSpacingBefore(15);

			doc.add(tabDatiAnagrafici);
			doc.add(tabDatiIstruzioneFormazione);	
			doc.add(tabDatiCompetenzePersonali);	
			doc.add(tabDatiAltreEsperienzeProfessionale);	
			doc.add(tabDatiPrecedentiIncarichi);	
			doc.add(tabPubblicazioni);	
			
			doc.add(firma);
			doc.add(line);
			doc.add(nota);
			
			/**
			doc.add(vuoto);
			doc.add(tabIstruzione);
			doc.add(vuoto);
			doc.add(tabEsperienzaCarriera);
			doc.add(vuoto);
			doc.add(tabEsperienzaLavorativa);
			doc.add(vuoto);
			doc.add(tabCompetenze);
			doc.add(vuoto);
			doc.add(tabPrecedentiIncarichi);
			doc.add(vuoto);
			doc.add(tabPubblicazioni);
			doc.add(privacy);
			doc.add(lingua);
			doc.add(allegati);
			doc.add(data);
			doc.add(firma);
			doc.add(line);
			**/
			doc.close();
			os.close();

		} catch (FileNotFoundException eFile) {
			eFile.printStackTrace();
		} catch (DocumentException eDoc) {
			eDoc.printStackTrace();
		} catch (IOException eIo) {
			eIo.printStackTrace();
		}

		 return os.toByteArray();
	}

	private static PdfPTable creaTabellaDatiAnagrafici(Esperto e, String intestazione, Properties props, boolean publish) throws Exception{
	
		PdfPTable aTable = new PdfPTable(6);
		aTable.setWidthPercentage(90);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {5f, 5f, 5f, 10f, 5f, 10f};
		aTable.setWidths(columnWidths);
		StringBuilder sbNome = new StringBuilder();
		 
		// INTESTAZIONE
		creaIntestazione(intestazione, aTable,2, Element.ALIGN_TOP, Color.BLUE);
		creaCella(sbNome.append(e.getNome()).append(" ").append(e.getCognome()).toString(), aTable,4,1,Element.ALIGN_TOP, Color.DARK_GRAY);
	
		
	
		PdfPTable pdfPhoto = new PdfPTable(1);
		//pdfPhoto.setWidthPercentage(100);
		//Image imgPhoto = Image.getInstance(props.getProperty("img.body.ante.path.photo"));
		//creaCella(imgPhoto,pdfPhoto,1,1,Element.ALIGN_TOP, Color.DARK_GRAY);
		addCella(pdfPhoto,aTable,2,1,Element.ALIGN_TOP);
		
		PdfPTable pdfData = new PdfPTable(4);
		pdfData.setWidthPercentage(70);
		columnWidths = new float[] {3f, 10f, 3f, 10f};
		pdfData.setWidths(columnWidths);
		
		if(!publish){
			StringBuilder sbIndirizzo = new StringBuilder();
			Image imgIndirizzo = Image.getInstance(props.getProperty("img.body.ante.path.indirizzo"));	
			creaCella(imgIndirizzo,pdfData,1,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			creaCella(sbIndirizzo.append(e.getResidenza()!=null?e.getResidenza():"").append(", ")
						     .append(e.getCap()!=null?e.getCap():"").append(", ")
						     .append(e.getCitta()!=null?e.getCitta().getDenominazione():"")
						     .append(", ")
						     .append(e.getPaesi()!=null?e.getPaesi().getDenominazione():"")
						     .toString(), pdfData,3,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
		
	
			Image imgTelefono = Image.getInstance(props.getProperty("img.body.ante.path.telefono"));	
			Image imgCellulare = Image.getInstance(props.getProperty("img.body.ante.path.cellulare"));	
			creaCella(imgTelefono,pdfData,1,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			creaCella(e.getTel(), pdfData,1,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			creaCella(imgCellulare,pdfData,1,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			creaCella(e.getCel(), pdfData,1,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
		
			Image imgEmail = Image.getInstance(props.getProperty("img.body.ante.path.email"));	
			creaCella(imgEmail,pdfData,1,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			creaCella(e.getEmail(), pdfData,3,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
		
		}
		DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
		creaCella("Data di Nascita:", pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
		creaCella(df1.format(e.getDataNascita()), pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
		
		/**
		Image imgSito = Image.getInstance(props.getProperty("img.body.ante.path.sito"));	
		creaCella(imgSito,pdfData,1,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
		creaCella("", pdfData,3,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
	
	
		Image imgMessaggistica = Image.getInstance(props.getProperty("img.body.ante.path.messaggistica"));	
		creaCella(imgMessaggistica,pdfData,1,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
		creaCella("", pdfData,3,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
		**/
		
	
		addCella(pdfData,aTable,4,1,Element.ALIGN_MIDDLE);
	
		return aTable;

	}
	
	private static PdfPTable creaTabellaOccupazione(String intestazione) throws Exception{

		
		PdfPTable aTable = new PdfPTable(2);
		aTable.setWidthPercentage(70);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {8f,25f};
		aTable.setWidths(columnWidths);
		creaIntestazione(intestazione, aTable,1, Element.ALIGN_TOP, Color.BLUE);
		PdfPTable pdfData = new PdfPTable(1);
		pdfData.setWidthPercentage(100);
		columnWidths = new float[] {20f};
		pdfData.setWidths(columnWidths);
		creaCella("Candidatura per l’inserimento nell’Elenco degli esperti di cui all’art. 11 d.m. 8 marzo 2006\n\n\n", pdfData,1,1,Element.ALIGN_TOP, Color.DARK_GRAY);	
		addCella(pdfData,aTable,1,1,Element.ALIGN_TOP);
		creaCellaVuota(aTable,2,Element.ALIGN_TOP,2,Color.BLUE);
		
		
		
		
		return aTable;
	}
	
	private static PdfPTable creaTabellaPresentazioneCandidato(Esperto e, String intestazione) throws Exception{

		
		PdfPTable aTable = new PdfPTable(2);
		aTable.setWidthPercentage(70);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {8f,25f};
		aTable.setWidths(columnWidths);
		creaIntestazione(intestazione, aTable,1, Element.ALIGN_TOP, Color.BLUE);
		PdfPTable pdfData = new PdfPTable(1);
		pdfData.setWidthPercentage(100);
		columnWidths = new float[] {20f};
		pdfData.setWidths(columnWidths);
		creaCella(e.getPresentazione(), pdfData,1,1,Element.ALIGN_TOP, Color.DARK_GRAY);	
		addCella(pdfData,aTable,1,1,Element.ALIGN_TOP);
		creaCellaVuota(aTable,2,Element.ALIGN_TOP,2,Color.BLUE);
		
		
		
		
		return aTable;
	}
	
    private static PdfPTable creaTabellaCompetenzaPrincipaleCandidato(Esperto e, String intestazione) throws Exception{

		
		PdfPTable aTable = new PdfPTable(2);
		aTable.setWidthPercentage(70);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {8f,25f};
		aTable.setWidths(columnWidths);
		creaIntestazione(intestazione, aTable,1, Element.ALIGN_TOP, Color.BLUE);
		PdfPTable pdfData = new PdfPTable(1);
		pdfData.setWidthPercentage(100);
		columnWidths = new float[] {20f};
		pdfData.setWidths(columnWidths);
		creaCella(e.getCompetenza(), pdfData,1,1,Element.ALIGN_TOP, Color.DARK_GRAY);	
		addCella(pdfData,aTable,1,1,Element.ALIGN_TOP);
		creaCellaVuota(aTable,2,Element.ALIGN_TOP,2,Color.BLUE);
		
		
		
		
		return aTable;
	}
	
	private static PdfPTable creaTabellaEsperienzeProfessionale(Esperto e,String intestazione) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy");
		PdfPTable aTable = new PdfPTable(6);
		aTable.setWidthPercentage(70);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {5f, 5f, 5f, 10f, 5f, 10f};
		aTable.setWidths(columnWidths);
		// INTESTAZIONE
		creaIntestazione(intestazione, aTable,3, Element.ALIGN_TOP, Color.BLUE);
		creaCellaVuota(aTable,3,Element.ALIGN_TOP,0,Color.BLUE);
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		RicercaCarrieraCommand cmd = new RicercaCarrieraCommand(e.getId());
		cmd.execute();		
		@SuppressWarnings("unchecked")
		List<EsperienzaLavorativa> lista = new ArrayList<EsperienzaLavorativa>(cmd.getSet());
		Collections.sort(lista);
		Collections.reverse(lista);
		Set<EsperienzaLavorativa> listaEsperienze = new LinkedHashSet<EsperienzaLavorativa>();;
		listaEsperienze.addAll(lista);
		
	
		Iterator<EsperienzaLavorativa> it = listaEsperienze.iterator();
	
		while (it.hasNext()) {
			PdfPTable pdfDataDa = new PdfPTable(3);
			columnWidths = new float[] {5f, 5f, 5f};
			pdfDataDa.setWidthPercentage(100);
			pdfDataDa.setWidths(columnWidths);
			
			EsperienzaLavorativa esperienzaLavorativa = (EsperienzaLavorativa) it.next();
			creaCella(df.format(esperienzaLavorativa.getDataInizio()),  pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			creaCella("-", pdfDataDa, 1,1,Element.ALIGN_TOP, Element.ALIGN_CENTER, Color.BLUE);
			if(esperienzaLavorativa.getDataFine()!=null){
				creaCella(df.format(esperienzaLavorativa.getDataFine()),  pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			}else{
				creaCella("", pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			}
			addCella(pdfDataDa,aTable,2,1,Element.ALIGN_TOP);
			PdfPTable pdfData = new PdfPTable(4);
			pdfData.setWidthPercentage(100);
			columnWidths = new float[] {7f, 7f, 15f, 15f};
			pdfData.setWidths(columnWidths);
			creaCella("Datore di lavoro:",pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			if(esperienzaLavorativa.getDatoreDiLavoro()!=null){
				creaCella(esperienzaLavorativa.getDatoreDiLavoro().getDenominazione(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}else{
				creaCella("",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			creaCella("Professione:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
			if(esperienzaLavorativa.getProfessione()!=null){
				creaCella(esperienzaLavorativa.getProfessione().getDenominazione(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}else{
				creaCella("",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			creaCella("Tipo Professione:",pdfData, 2, 1,  Element.ALIGN_MIDDLE, Color.BLUE);
			if(esperienzaLavorativa.getTipoProfessione()!=null){
				creaCella(esperienzaLavorativa.getTipoProfessione().getDenominazione(), pdfData, 2, 1,  Element.ALIGN_MIDDLE,Color.DARK_GRAY);
			}else{
				creaCella("",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			creaCella("Descrizione attività:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
			creaCella(esperienzaLavorativa.getAttivitaLavorative(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			creaCella("Ruolo:",pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			creaCella(esperienzaLavorativa.getRuolo(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			/**
			creaCella("In corso:",pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			if (esperienzaLavorativa.isInCorso()) {
				creaCella("SI", pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			} else { 
				creaCella("NO", pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}	
			**/
			creaCella("Carriera:", pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			if (esperienzaLavorativa.isFlagCarriera()) {
				creaCella("SI", pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			} else { 
				creaCella("NO", pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			creaCella("Attuale posizione lavorativa:", pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			if (esperienzaLavorativa.isFlagPrincipale()) {
				creaCella("SI", pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			} else { 
				creaCella("NO", pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}	
			creaCella("Tipo rapporto lavorativo:", pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			creaCella(esperienzaLavorativa.getTipoEsperienza(), pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			if(it.hasNext()){
				creaCellaVuota(pdfData,4,Element.ALIGN_TOP,2,Color.BLUE);
			}
			addCella(pdfData,aTable,4,1,Element.ALIGN_MIDDLE);
			
		}
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		
		return aTable;
	}
	
	
	private static PdfPTable creaTabellaAltreEsperienzeProfessionale(Esperto esperto,String intestazione) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy");
		PdfPTable aTable = new PdfPTable(6);
		aTable.setWidthPercentage(100);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {5f, 5f, 5f, 10f, 5f, 10f};
		aTable.setWidths(columnWidths);
		// INTESTAZIONE
		creaIntestazione(intestazione, aTable,3, Element.ALIGN_TOP, Color.BLUE);
		creaCellaVuota(aTable,3,Element.ALIGN_TOP,0,Color.BLUE);
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		EsperienzeLavorativeCommand cmd = new EsperienzeLavorativeCommand(esperto.getId());
		
		@SuppressWarnings("unchecked")
		List<EsperienzaLavorativa> lista = new ArrayList<EsperienzaLavorativa>(cmd.list());
		Collections.sort(lista);
		Collections.reverse(lista);
		Set<EsperienzaLavorativa> listaEsperienze = new LinkedHashSet<EsperienzaLavorativa>();;
		listaEsperienze.addAll(lista);
		
	
		Iterator<EsperienzaLavorativa> it = listaEsperienze.iterator();
	
		while (it.hasNext()) {
			PdfPTable pdfDataDa = new PdfPTable(3);
			columnWidths = new float[] {5f, 5f, 5f};
			pdfDataDa.setWidthPercentage(100);
			pdfDataDa.setWidths(columnWidths);
			
			
			EsperienzaLavorativa esperienzaLavorativa = (EsperienzaLavorativa) it.next();
			creaCella(df.format(esperienzaLavorativa.getDataInizio()),  pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
				
			creaCella("-", pdfDataDa, 1,1,Element.ALIGN_TOP, Element.ALIGN_CENTER, Color.BLUE);
			if (esperienzaLavorativa.isInCorso()) {
				creaCella("In corso", pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			}
			
			if(esperienzaLavorativa.getDataFine()!=null){
				creaCella(df.format(esperienzaLavorativa.getDataFine()),  pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			}else{
				creaCella("", pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			}
			addCella(pdfDataDa,aTable,2,1,Element.ALIGN_TOP);
			PdfPTable pdfData = new PdfPTable(4);
			pdfData.setWidthPercentage(100);
			columnWidths = new float[] {7f, 7f, 15f, 15f};
			pdfData.setWidths(columnWidths);
			
			creaCella("Tipo rapporto lavorativo:", pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			creaCella(esperienzaLavorativa.getTipoProfessione().getDenominazione(), pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			if(esperienzaLavorativa.getDatoreDiLavoro()!=null){
				creaCella("Datore di lavoro:",pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
				creaCella(esperienzaLavorativa.getDatoreDiLavoro().getDenominazione(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			creaCella("Descrizione attività:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
			creaCella(esperienzaLavorativa.getAttivitaLavorative(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			creaCella("Professione:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
			if(esperienzaLavorativa.getProfessione()!=null){
				creaCella(esperienzaLavorativa.getProfessione().getDenominazione(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}else{
				creaCella("",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			if(esperienzaLavorativa.getRuolo()!=null){
				creaCella("Ruolo:",pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
				creaCella(esperienzaLavorativa.getRuolo(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			if(esperienzaLavorativa.getSpecializzazione1()!=null){
				creaCella("Keyword 1:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
				creaCella(esperienzaLavorativa.getSpecializzazione1()!=null?esperienzaLavorativa.getSpecializzazione1().getNome():"",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			if(esperienzaLavorativa.getSpecializzazione2()!=null){
				creaCella("Keyword 2:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
				creaCella(esperienzaLavorativa.getSpecializzazione2()!=null?esperienzaLavorativa.getSpecializzazione2().getNome():"",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			if(esperienzaLavorativa.getSpecializzazione3()!=null){
				creaCella("Keyword 3:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
				creaCella(esperienzaLavorativa.getSpecializzazione3()!=null?esperienzaLavorativa.getSpecializzazione3().getNome():"",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
		
			if(it.hasNext()){
				creaCellaVuota(pdfData,4,Element.ALIGN_TOP,2,Color.BLUE);
			}
			addCella(pdfData,aTable,4,1,Element.ALIGN_MIDDLE);
			
		}
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		
		return aTable;
	}
	
	private static PdfPTable creaTabellaPrecedentiIncarichi(Esperto e,String intestazione) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy");
		PdfPTable aTable = new PdfPTable(6);
		aTable.setWidthPercentage(100);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {5f, 5f, 5f, 10f, 5f, 10f};
		aTable.setWidths(columnWidths);
		// INTESTAZIONE
		creaIntestazione(intestazione, aTable,3, Element.ALIGN_TOP, Color.BLUE);
		creaCellaVuota(aTable,3,Element.ALIGN_TOP,0,Color.BLUE);
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		RicercaPrecedentiIncarichiCommand cmd = new RicercaPrecedentiIncarichiCommand(e.getId());
		cmd.execute();
		Set<PrecedenteIncarico> set = cmd.getSet();
		Iterator<PrecedenteIncarico> it = set.iterator();
	
	
		while (it.hasNext()) {
			PdfPTable pdfDataDa = new PdfPTable(3);
			columnWidths = new float[] {5f, 5f, 5f};
			pdfDataDa.setWidthPercentage(100);
			pdfDataDa.setWidths(columnWidths);
			
			PrecedenteIncarico precedenteIncarico  = (PrecedenteIncarico) it.next();
			if(precedenteIncarico.getAnno()!=null){
				creaCella(df.format(precedenteIncarico.getAnno()),  pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			}else{
				creaCella("",  pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			}
			
			creaCella("-", pdfDataDa, 1,1,Element.ALIGN_TOP, Element.ALIGN_CENTER, Color.BLUE);
			if (precedenteIncarico.isInCorso()) {
				creaCella("In corso", pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			}
			
			if(precedenteIncarico.getDataFine()!=null){
				creaCella(df.format(precedenteIncarico.getDataFine()),  pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			}else{
				creaCella("", pdfDataDa, 1,1,Element.ALIGN_TOP, Color.BLUE);
			}
			
			addCella(pdfDataDa,aTable,2,1,Element.ALIGN_TOP);
			
			PdfPTable pdfData = new PdfPTable(4);
			pdfData.setWidthPercentage(100);
			columnWidths = new float[] {7f, 7f, 15f, 15f};
			pdfData.setWidths(columnWidths);
			creaCella("Soggetto che ha conferito l’incarico:",pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			creaCella( precedenteIncarico.getNomeProgetto(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			creaCella("Descrizione sintetica incarico/progetto:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
			creaCella( precedenteIncarico.getAbstractProgetto(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			creaCellaVuota(pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
			creaCellaVuota(pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
			creaCella("Amministrazione:",pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.BLUE);
			creaCellaVuota(pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
			
			String esito = "";
			if (precedenteIncarico.isInternazionale()) {
				esito = "SI";
			
				creaCellaVuota(pdfData, 1, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
				creaCella("Internazionale:",pdfData, 2, 1,  Element.ALIGN_MIDDLE, Color.BLUE);
				creaCella(esito, pdfData,1, 1,  Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			} 
			
			if (precedenteIncarico.isEuropeo()) {
				esito = "SI";
			 
				creaCellaVuota(pdfData, 1, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
				creaCella("Europeo:",pdfData, 2, 1,  Element.ALIGN_MIDDLE, Color.BLUE);
				creaCella(esito, pdfData,1, 1,  Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			}

			if (precedenteIncarico.isMiur()) {
				esito = "SI";
				
				creaCellaVuota(pdfData, 1, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
				creaCella("Nazionale:",pdfData, 2, 1,  Element.ALIGN_MIDDLE, Color.BLUE);
				creaCella(esito, pdfData,1, 1,  Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			} 
			
			
			if (precedenteIncarico.isRds()) {
				esito = "SI";
				
				creaCellaVuota(pdfData, 1, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
				creaCella("Ricerca di sistema (RdS):",pdfData, 2, 1,  Element.ALIGN_MIDDLE, Color.BLUE);
				creaCella(esito, pdfData,1, 1,  Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			} 
			
			creaCellaVuota(pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
			creaCellaVuota(pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
			
			if(precedenteIncarico.getProgrammaFinanziamento()!=null){
				creaCella("Programma finanziamento:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
				creaCella(precedenteIncarico.getProgrammaFinanziamento(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
		
			creaCella("Titolo progetto:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
			creaCella(precedenteIncarico.getTitoloProgetto(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			if(it.hasNext()){
				creaCellaVuota(pdfData,4,Element.ALIGN_TOP,2,Color.BLUE);
			}
			addCella(pdfData,aTable,4,1,Element.ALIGN_MIDDLE);
			
		}
	
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		return aTable;
	}
	
	private static PdfPTable creaTabellaIstruzioneFormazione(Esperto e,String intestazione) throws Exception{
		
	
		
		DateFormat df = new SimpleDateFormat("yyyy");
		PdfPTable aTable = new PdfPTable(6);
		aTable.setWidthPercentage(100);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {5f, 5f, 5f, 10f, 5f, 10f};
		aTable.setWidths(columnWidths);
		
		creaIntestazione(intestazione, aTable,3, Element.ALIGN_TOP, Color.BLUE);
		creaCellaVuota(aTable,3,Element.ALIGN_TOP,0,Color.BLUE);
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		RicercaIstruzioneCommand cmd = new RicercaIstruzioneCommand(e.getId());
		cmd.execute();
		

		Set<Istruzione> set = new TreeSet<Istruzione>(new IstruzioneComparator());
		set.addAll(cmd.getSet());	
		Iterator<Istruzione> it = set.iterator();
		//creaIntestazione(intestazione, aTable,2);
	
		
	
		while (it.hasNext()) {
			
			PdfPTable pdfDataDa = new PdfPTable(3);
			pdfDataDa.setWidthPercentage(100);
			columnWidths = new float[] {5f, 5f, 5f};
			pdfDataDa.setWidths(columnWidths);
			Istruzione istruzione = (Istruzione) it.next();
			creaCella(df.format(istruzione.getData()),  pdfDataDa, 3,1,Element.ALIGN_TOP, Color.BLUE);
			addCella(pdfDataDa,aTable,2,1,Element.ALIGN_TOP);
			PdfPTable pdfData = new PdfPTable(4);
			pdfData.setWidthPercentage(100);
			columnWidths = new float[] {7f, 7f, 15f, 15f};
			pdfData.setWidths(columnWidths);
			creaCella("Livello:",pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			if(istruzione.getTipoLaurea()!=null){
				creaCella(istruzione.getTipoLaurea().getDenominazione(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}else{
				creaCella("",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			
			if(istruzione.getTitoloLaurea()!=null){
				creaCella("Titolo di Studio:",pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
				creaCella(istruzione.getTitoloLaurea().getDenominazione(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			if(istruzione.getCitta()!=null){
				creaCella("Città:",pdfData, 2, 1,  Element.ALIGN_MIDDLE, Color.BLUE);
				creaCella(istruzione.getCitta().getDenominazione(), pdfData, 2, 1,  Element.ALIGN_MIDDLE,Color.DARK_GRAY);
			}
			
			
			if(istruzione.getAtenei()!=null){
				creaCella("Ateneo:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
				creaCella(istruzione.getAtenei().getDenominazione(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
		
			
			creaCella("Anno di conseguimento:",pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			if(istruzione.getData()!=null){
				creaCella(df.format(istruzione.getData()),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}else{
				creaCella("",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			String esito = "";
			if (istruzione.isEstero()) {
				esito = "SI";
				
				creaCella("Estero:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
				creaCella(esito,pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			} 
			
			
			if (!istruzione.getTitoloStudi().isEmpty()) {
				creaCella("Descrizione:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
				creaCella(istruzione.getTitoloStudi(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			
			if(it.hasNext()){
				creaCellaVuota(pdfData,4,Element.ALIGN_TOP,2,Color.BLUE);
			}
			addCella(pdfData,aTable,4,1,Element.ALIGN_MIDDLE);
			
		}
	
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		return aTable;
	}

	

	

	private static PdfPTable creaTabellaCompetenzePersonali(Esperto e,String intestazione) throws Exception{
	
		PdfPTable aTable = new PdfPTable(6);
		aTable.setWidthPercentage(100);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {5f, 5f, 5f, 10f, 5f, 10f};
		aTable.setWidths(columnWidths);
		
		creaIntestazione(intestazione, aTable,3, Element.ALIGN_TOP, Color.BLUE);
		creaCellaVuota(aTable,3,Element.ALIGN_TOP,0,Color.BLUE);
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		RicercaCompetenzeCommand cmd = new RicercaCompetenzeCommand(e.getId());
		cmd.execute();		
		Set<Competenza> set = cmd.getSet();
		Iterator<Competenza> it = set.iterator();
		
		PdfPTable pdfDescLingua = new PdfPTable(3);
		columnWidths = new float[] {5f, 5f, 5f};
		pdfDescLingua.setWidthPercentage(100);
		pdfDescLingua.setWidths(columnWidths);
		creaCella("Lingua",  pdfDescLingua, 3,1,Element.ALIGN_TOP, Color.BLUE);
		addCella(pdfDescLingua,aTable,2,1,Element.ALIGN_TOP);
		PdfPTable pdfDataLingua = new PdfPTable(4);
		pdfDataLingua.setWidthPercentage(100);
		columnWidths = new float[] {7f, 7f, 15f, 15f};
		pdfDataLingua.setWidths(columnWidths);
		creaCella("Italiano",pdfDataLingua,4,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
		addCella(pdfDataLingua,aTable,4,1,Element.ALIGN_MIDDLE);
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		
		
		int i =0;
		while (it.hasNext()) {
			PdfPTable pdfDesc = new PdfPTable(3);
			columnWidths = new float[] {5f, 5f, 5f};
			pdfDesc.setWidthPercentage(100);
			pdfDesc.setWidths(columnWidths);
			
			Competenza competenza = (Competenza) it.next();
			
			if(i==0){
				creaCella("Competenze Professionali",  pdfDesc, 3,1,Element.ALIGN_TOP, Color.BLUE);
				addCella(pdfDesc,aTable,2,1,Element.ALIGN_TOP);
			}else{
				creaCella("",  pdfDesc, 3,1,Element.ALIGN_TOP, Color.BLUE);
				addCella(pdfDesc,aTable,2,1,Element.ALIGN_TOP);
			}
			
			PdfPTable pdfData = new PdfPTable(4);
			pdfData.setWidthPercentage(100);
			columnWidths = new float[] {7f, 7f, 15f, 15f};
			pdfData.setWidths(columnWidths);
			creaCella("Settore:",pdfData,2,1,Element.ALIGN_MIDDLE, Color.BLUE);
			creaCella(competenza.getSpecializzazione().getTema().getNome(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			creaCella("Competenza specifica: ",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
			creaCella(competenza.getSpecializzazione().getNome(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			creaCellaVuota(pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
			creaCellaVuota(pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
			creaCella("Caratteristiche:",pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.BLUE);
			creaCellaVuota(pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
			
			String esito = "";
			if (competenza.isPrincipale()) {
				esito = "SI";
				
				creaCellaVuota(pdfData, 1, 1,  Element.ALIGN_LEFT, Color.WHITE);
				creaCella("Principale:",pdfData, 2, 1,  Element.ALIGN_MIDDLE, Color.BLUE);
				creaCella(esito, pdfData,1, 1,  Element.ALIGN_LEFT, Color.DARK_GRAY);
			} 
			
			
			if (!competenza.isPrincipale()) {
				esito = "SI";
				
				creaCellaVuota(pdfData, 1, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
				creaCella("Secondario:",pdfData, 2, 1,  Element.ALIGN_MIDDLE, Color.BLUE);
				creaCella(esito, pdfData,1, 1,  Element.ALIGN_LEFT, Color.DARK_GRAY);
			} 
			
			creaCellaVuota(pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
			creaCellaVuota(pdfData, 4, 1,  Element.ALIGN_MIDDLE, Color.WHITE);
			
			if(it.hasNext()){
				creaCellaVuota(pdfData,4,Element.ALIGN_TOP,2,Color.BLUE);
			}
			addCella(pdfData,aTable,4,1,Element.ALIGN_MIDDLE);
			i++;
			
		}
	
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		return aTable;
	}
	
	
	private static PdfPTable creaTabellaUlterioriInformazioni(Esperto e,String intestazione) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy");
		PdfPTable aTable = new PdfPTable(6);
		aTable.setWidthPercentage(100);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {5f, 5f, 5f, 10f, 5f, 10f};
		aTable.setWidths(columnWidths);
		// INTESTAZIONE
		creaIntestazione(intestazione, aTable,3, Element.ALIGN_TOP, Color.BLUE);
		creaCellaVuota(aTable,3,Element.ALIGN_TOP,0,Color.BLUE);
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		
		RicercaPubblicazioniCommand rpc = new RicercaPubblicazioniCommand(e.getId());
		rpc.execute();
		
		Set<Pubblicazione> pubblicazioneList = new TreeSet<Pubblicazione>(new PubblicazioneComparator());
		pubblicazioneList.addAll(rpc.getSet());
		//creaIntestazione(intestazione, aTable,2); 
		Iterator<Pubblicazione> it = pubblicazioneList.iterator();
		
		while (it.hasNext()) {
			PdfPTable pdfDataDa = new PdfPTable(3);
			columnWidths = new float[] {5f, 5f, 5f};
			pdfDataDa.setWidthPercentage(100);
			pdfDataDa.setWidths(columnWidths);
			
			Pubblicazione pubblicazione = (Pubblicazione) it.next();
			creaCella(df.format(pubblicazione.getData()),  pdfDataDa, 3,1,Element.ALIGN_TOP, Color.BLUE);
			addCella(pdfDataDa,aTable,2,1,Element.ALIGN_TOP);
			
			PdfPTable pdfData = new PdfPTable(4);
			pdfData.setWidthPercentage(100);
			columnWidths = new float[] {7f, 7f, 15f, 15f};
			pdfData.setWidths(columnWidths);
			creaCella("Pubblicazione:",pdfData,2,1,Element.ALIGN_TOP, Color.BLUE);
			creaCella(pubblicazione.getDescrizione(),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			creaCella("Anno di pubblicazione:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
			creaCella(df.format(pubblicazione.getData()),pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			
			if(pubblicazione.getSpecializzazione1()!=null){
				creaCella("Keyword 1:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
				creaCella(pubblicazione.getSpecializzazione1()!=null?pubblicazione.getSpecializzazione1().getNome():"",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
				
			}
			
			if(pubblicazione.getSpecializzazione2()!=null){
				creaCella("Keyword 2:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
				creaCella(pubblicazione.getSpecializzazione2()!=null?pubblicazione.getSpecializzazione2().getNome():"",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			if(pubblicazione.getSpecializzazione3()!=null){
				creaCella("Keyword 3:",pdfData,2,1,Element.ALIGN_MIDDLE,  Color.BLUE);
				creaCella(pubblicazione.getSpecializzazione3()!=null?pubblicazione.getSpecializzazione3().getNome():"",pdfData,2,1,Element.ALIGN_MIDDLE, Color.DARK_GRAY);
			}
			
			addCella(pdfData,aTable,4,1,Element.ALIGN_MIDDLE);
			if(it.hasNext()){
				creaCellaVuota(pdfData,4,Element.ALIGN_TOP,2,Color.BLUE);
			}
			
		}
		creaCellaVuota(aTable,6,Element.ALIGN_TOP,2,Color.BLUE);
		
		return aTable;
	}
	

	private static PdfPTable creaTabellaAllegati(Esperto e, String intestazione) throws Exception{
		PdfPTable aTable = new PdfPTable(2);
		aTable.setWidthPercentage(70);
		aTable.setSpacingAfter(20);
		float[] columnWidths = new float[] {9f,25f};
		aTable.setWidths(columnWidths);
		creaIntestazione(intestazione, aTable,1, Element.ALIGN_TOP, Color.BLUE);
		PdfPTable pdfData = new PdfPTable(1);
		pdfData.setWidthPercentage(100);
		columnWidths = new float[] {20f};
		pdfData.setWidths(columnWidths);
	
		
		if(e.getAllegato()!=null && e.getAllegato().getContentType()!=null){
			creaCella("Formato:" + e.getAllegato().getContentType(), pdfData,1,1,Element.ALIGN_TOP, Color.DARK_GRAY);	
		}else{
			creaCella("Nessuno", pdfData,1,1,Element.ALIGN_TOP, Color.DARK_GRAY);	
		}
		
		addCella(pdfData,aTable,1,1,Element.ALIGN_TOP);
		creaCellaVuota(aTable,2,Element.ALIGN_TOP,2,Color.BLUE);
		
		return aTable;
	}
	
	private static void creaCellaVuota(PdfPTable aTable, int colspan,  int verticalAlign, int border, Color borderColor) {
		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setColspan(colspan);
		pdfPCell.setBorderColor(borderColor);
		pdfPCell.setBackgroundColor(Color.white);
		pdfPCell.setRowspan(1);
		pdfPCell.setBorder(border);
		pdfPCell.setTop(0);
		pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfPCell.setVerticalAlignment(verticalAlign);
		aTable.addCell(pdfPCell);
		
		
	}

	private  static void creaCella(Image img,PdfPTable aTable, int colspan, int rowspan, int verticalAlign, Color textColor) {
		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setColspan(colspan);
		pdfPCell.setRowspan(rowspan);
		pdfPCell.setBorderColor(Color.white);
		pdfPCell.setBackgroundColor(Color.white);
		pdfPCell.setBorder(0);
		pdfPCell.setTop(0);
		pdfPCell.setImage(img);
		pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfPCell.setVerticalAlignment(verticalAlign);
		aTable.addCell(pdfPCell);
	}
	private static void creaCella(String descrizione, PdfPTable aTable, int colspan, int rowspan,  int verticalAlign, Color textColor) {
		PdfPCell pdfPCell = new PdfPCell(new Paragraph(descrizione, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,textColor)));
		pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfPCell.setColspan(colspan);
		pdfPCell.setRowspan(rowspan);
		pdfPCell.setBorder(0);
		pdfPCell.setTop(0);
		pdfPCell.setVerticalAlignment(verticalAlign);
		aTable.addCell(pdfPCell);
		
	
	}
	
	private static void creaCella(String descrizione, PdfPTable aTable, int colspan, int rowspan,  int verticalAlign, int horizontalAlignment, Color textColor) {
		PdfPCell pdfPCell = new PdfPCell(new Paragraph(descrizione, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10,textColor)));
		pdfPCell.setHorizontalAlignment(horizontalAlignment);
		pdfPCell.setColspan(colspan);
		pdfPCell.setRowspan(rowspan);
		pdfPCell.setBorder(0);
		pdfPCell.setTop(0);
		pdfPCell.setVerticalAlignment(verticalAlign);
		aTable.addCell(pdfPCell);
		
	
	}
	
	
	private static void addCella(Element element, PdfPTable aTable, int colspan, int rowspan,  int verticalAlign) {
		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(element);
		pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfPCell.setColspan(colspan);
		pdfPCell.setRowspan(rowspan);
		pdfPCell.setBorder(0);
		pdfPCell.setTop(0);
		pdfPCell.setNoWrap(false);
		pdfPCell.setVerticalAlignment(verticalAlign);
		aTable.addCell(pdfPCell);
		
	
		/**
		PdfPCell pdfPCellValue = new PdfPCell(new Paragraph(valore, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Color.DARK_GRAY)));
		pdfPCellValue.setVerticalAlignment(Element.ALIGN_LEFT);
		pdfPCellValue.setColspan(2);
		pdfPCellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
		aTable.addCell(pdfPCellValue);
		**/
	}
	

	private static void creaIntestazione(String intestazione, PdfPTable aTable, int colspan,  int verticalAlign, Color textColor) {
		PdfPCell pdfPCell = new PdfPCell(new Paragraph(intestazione, FontFactory.getFont(FontFactory.HELVETICA, 9,textColor)));
		pdfPCell.setColspan(colspan);
		pdfPCell.setRowspan(1);
		pdfPCell.setBorderColor(Color.white);
		pdfPCell.setBackgroundColor(Color.white);
		pdfPCell.setBorder(2);
		pdfPCell.setTop(0);
		pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfPCell.setVerticalAlignment(verticalAlign);
		aTable.addCell(pdfPCell);
		
	}

}
