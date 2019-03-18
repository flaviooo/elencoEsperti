package it.ccse.bandiEsperti.utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;

public class ConfigHelper {
	
	public static final String CV_KEY = "cfg.cv.path";
	public static final String MAIL_KEY = "cfg.mail.path";
	private static final String CFG_PATH="/WEB-INF/conf/conf.properties";
	
	private Properties configProperties = null;
	public ConfigHelper() {
		try {
			Desktop desktop = Executions.getCurrent().getDesktop();
			String realPath = desktop.getWebApp().getRealPath(CFG_PATH);
			configProperties = new Properties();
			configProperties.load(new FileInputStream(realPath));
		} catch (Exception e) {
			configProperties = null;
		}
	}
	
	public Properties getConfigProperties() {
		return configProperties;
	}

	
	
}
