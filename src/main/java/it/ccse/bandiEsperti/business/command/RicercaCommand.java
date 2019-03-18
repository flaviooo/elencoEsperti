/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import it.ccse.bandiEsperti.business.dto.EspertoRicercaDinamicaDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaDTO;
import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;
import it.ccse.bandiEsperti.utils.ConfigHelper;
import it.ccse.bandiEsperti.utils.TrasformListToDTOObject;

/**
 * @author CCSE
 */
public class RicercaCommand implements IRicercaCommand{
	
	List<FiltroRicercaDTO> listFiltroRicerca = null;
	Logger logger = Logger.getLogger(RicercaCommand.class);
	
	
	public RicercaCommand(List<FiltroRicercaDTO> listFiltroRicerca) {
		super();
		this.listFiltroRicerca = listFiltroRicerca;
	}
	
	@Override
	public Set<EspertoRicercaDinamicaDTO> execute() throws Exception{
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		Set<EspertoRicercaDinamicaDTO> espertoRicercaDinamicaDTO = null;
		try {
			VelocityEngine ve = new VelocityEngine();
			ConfigHelper cfgHelper = new ConfigHelper();
			Properties cfgProperties = cfgHelper.getConfigProperties();
			Properties props = new Properties();
			props.load(new FileInputStream(cfgProperties.getProperty(ConfigHelper.CV_KEY)));
		
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file"); 
			ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "C:/CCSE/bandiEsperti/template/");
			Template template = null;
			  Map<String, Object> context = new HashMap<String, Object>();
			
			context.put("query", new String());
			if(listFiltroRicerca!=null && listFiltroRicerca.size()>0){
				template = ve.getTemplate("sqlComposer.vm");
				context.put("list", listFiltroRicerca);
			}
			else{
				template = ve.getTemplate("sqlComposerAll.vm");
			}
		    StringWriter writer = new StringWriter(5000);
		  
		    VelocityContext velocityContext = new VelocityContext(context);
			template.merge(velocityContext, writer);
			
			String query = (String) context.get("query");
			System.out.println("****************************************");
			System.out.println("****************************************");
			System.out.println("query:" + query);
			System.out.println("****************************************");
			System.out.println("****************************************");
	    	IEspertiService es = new EspertiService();
			List<Object> objects = es.ricerca(query);
			if(objects!=null && objects.size()>0){
				TrasformListToDTOObject trasformListToDTOObject = it.ccse.bandiEsperti.utils.TrasformListToDTOObject.getInstance();
				espertoRicercaDinamicaDTO=trasformListToDTOObject.execute(objects);
			}else{
				espertoRicercaDinamicaDTO=new HashSet<EspertoRicercaDinamicaDTO>();
			}
			
		} catch (Exception e) {
			logger.debug(" errore nell'esecuzione del service:" + e.toString());
		}
		return espertoRicercaDinamicaDTO;
	}



	


		
}
