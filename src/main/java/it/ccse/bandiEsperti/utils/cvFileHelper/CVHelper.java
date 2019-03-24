package it.ccse.bandiEsperti.utils.cvFileHelper;

import it.ccse.bandiEsperti.business.dto.EspertoDTO;
import it.ccse.bandiEsperti.utils.cvFileHelper.CVFileFilter;
import it.ccse.bandiEsperti.utils.ConfigHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class CVHelper {
	
	private static final String APPROVATI_DIR = "cv.path.approvati";
	private static final String NON_APPROVATI_DIR = "cv.path.non_approvati";
	
	private Properties props = new Properties();

	public CVHelper() {
		try
		{
			ConfigHelper helper = new ConfigHelper();
			Properties configProps = helper.getConfigProperties();
			String cvCfgFile = configProps.getProperty(ConfigHelper.CV_KEY);
			props.load(new FileInputStream(cvCfgFile));
			
		}
		catch (Exception e) {
			
		}
	}
	
	public boolean spostaCVEsperto(EspertoDTO espertoDTO, boolean approvato) 
	{
		String dirAppr = props.getProperty(APPROVATI_DIR);
		String dirNonAppr = props.getProperty(NON_APPROVATI_DIR);
		if (approvato)
			return spostaFile(espertoDTO, dirNonAppr, dirAppr);
		else
			return spostaFile(espertoDTO, dirAppr, dirNonAppr);
		
	}
	
	public byte[] getContent(String cvPath)
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileInputStream is = new FileInputStream(cvPath);
			int abyte = is.read();
			while (abyte != -1)
			{
				baos.write(abyte);
				abyte = is.read();
			}
			is.close();
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}
	
	private boolean spostaFile(EspertoDTO espertoDTO, String sourceDirPath, String destDirPath)
	{
		File srcDir = new File(sourceDirPath);
		File destDir = new File(destDirPath);
		if (!destDir.exists())
			destDir.mkdirs();
		
		File cvFileList[] = srcDir.listFiles(new CVFileFilter(espertoDTO));
		boolean flag=true;
		for (File f: cvFileList)
		{
			flag = flag && f.renameTo(new File(destDir, f.getName()));
			if (flag == false)
				break;
		}
		return flag;
		
	}
	
	

}
