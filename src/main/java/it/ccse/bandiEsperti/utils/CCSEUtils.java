package it.ccse.bandiEsperti.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class CCSEUtils {

	public static final long MILLISECS_PER_MINUTE = 60 * 1000;
	public static final long MILLISECS_PER_HOUR = 60 * MILLISECS_PER_MINUTE;
	public static final long MILLISECS_PER_DAY = 24 * MILLISECS_PER_HOUR;

	//private final static ResourceBundle rb = ResourceBundle.getBundle("ccse_portal");

//	public static String getProperty(String key) throws Exception {
//		try {
//			return rb.getString(key);
//		} catch (NullPointerException npe) {
//			throw new Exception("chiave nulla");
//		} catch (MissingResourceException mre) {
//			throw new Exception("chiave non trovata");
//		}
//	}

	public static String readFile(String path) throws Exception {
		File f = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
	try{	
			f = new File(path);
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			String content = "";
			String linea = "";
			
			while (linea != null) {
				linea = br.readLine();
				content = content + linea;
			}
		return content;
		}finally{
			if(fis!=null){
				fis.close();
			}
			if(isr!=null){
				isr.close();
			}
			if(br!=null){
				br.close();
			}
			
		}
	}

	public static String abbreviate(String description, int maxLenght) {
//		if (description.length() > maxLenght) {
			return StringUtils.abbreviate(description, maxLenght);
//		} else
//			return description;
	}

	
	public static long differenceBetweenDates(Date lateDate, Date earlyDate) {
		return (lateDate.getTime() - earlyDate.getTime()) / MILLISECS_PER_DAY;
	}

	public static String getNomeMese(int month) {
		HashMap<Integer, String> months = getMappaMesi();
		return months.get(new Integer(month));
	}

	public static int getNumeroGiorniMese(int month) {
		HashMap<Integer, Integer> months = getGiorniMese();
		return months.get(new Integer(month));
	}

	private static HashMap<Integer, String> getMappaMesi() {
		HashMap<Integer, String> months = new HashMap<Integer, String>();
		months.put(0, "Gennaio");
		months.put(1, "Febbraio");
		months.put(2, "Marzo");
		months.put(3, "Aprile");
		months.put(4, "Maggio");
		months.put(5, "Giugno");
		months.put(6, "Luglio");
		months.put(7, "Agosto");
		months.put(8, "Settembre");
		months.put(9, "Ottobre");
		months.put(10, "Novembre");
		months.put(11, "Dicembre");
		return months;
	}

	private static HashMap<Integer, Integer> getGiorniMese() {
		HashMap<Integer, Integer> months = new HashMap<Integer, Integer>();
		months.put(0, 31);
		months.put(1, 29);
		months.put(2, 31);
		months.put(3, 31);
		months.put(4, 31);
		months.put(5, 30);
		months.put(6, 31);
		months.put(7, 31);
		months.put(8, 30);
		months.put(9, 31);
		months.put(10, 30);
		months.put(11, 31);
		return months;
	}

	public static int getKeysFromValue(Map<Integer, String> hm, Object value) {
		for (Integer o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return 0;
	}

	public static Date parseDate(String dataString) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		return sdf.parse(dataString);
	}

	public static String getMimetype(String filetype) {
		HashMap<String, String> mimetypes = createMimetypesMap();
		if (mimetypes.get(filetype) != null)
			return mimetypes.get(filetype);
		else
			return "Default.png";
	}

	private static HashMap<String, String> createMimetypesMap() {
		HashMap<String, String> mimetypes = new HashMap<String, String>();
		mimetypes.put("bmp", "BMP.png");
		mimetypes.put("doc", "DOC.png");
		mimetypes.put("docx", "DOCX.png");
		mimetypes.put("html", "HTML.png");
		mimetypes.put("htm", "HTML.png");
		mimetypes.put("htm", "HTML.png");
		mimetypes.put("jpeg", "JPEG.png");
		mimetypes.put("jpg", "JPEG.png");
		mimetypes.put("pdf", "PDF.png");
		mimetypes.put("png", "PNG.png");
		mimetypes.put("ppt", "PPT.png");
		mimetypes.put("pptx", "PPTX.png");
		mimetypes.put("rar", "RAR.png");
		mimetypes.put("rtf", "RTF.png");
		mimetypes.put("txt", "TXT.png");
		mimetypes.put("xls", "xls.png");
		mimetypes.put("xlsx", "XLSX.png");
		mimetypes.put("xml", "XML.png");
		mimetypes.put("zip", "ZIP.png");
		return mimetypes;
	}
	
	public static String getStringDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		return dateFormat.format(date);
	}
	public static String rightAlignFive(int idEsperto) {
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("00000.##");
		return df.format(idEsperto);
	}
}
