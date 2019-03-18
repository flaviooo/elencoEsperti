package it.ccse.bandiEsperti.zk.composers;

import java.util.Set;

import java.util.TreeSet;
import it.ccse.bandiEsperti.business.command.EsperienzeLavorativeCommand;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.zk.comparators.EsperienzeProfessionaliComparator;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public class AdminEsperienzeComposer  extends GenericForwardComposer {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(AdminEsperienzeComposer.class);
	
	private int idEsperto;
	private Set<EsperienzaLavorativa> listaEsperienze;
	
	public void doAfterCompose(Component win) throws Exception {
		super.doAfterCompose(win);
		
		try{
			//this.esperto = (Esperto)arg.get("esperto");
			this.idEsperto = (Integer) arg.get("idEsperto");
			this.initModel(false);
		} catch (Exception e) {
			log.error(e.getMessage());
			this.self.detach();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		try{
		
			EsperienzeLavorativeCommand command = new EsperienzeLavorativeCommand(idEsperto);
		    this.listaEsperienze = new TreeSet<EsperienzaLavorativa>(new EsperienzeProfessionaliComparator());
			this.listaEsperienze.addAll(command.list());	
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new WrongValueException("Si è verificato un errore nella inizializzazione della pagina");
		}
	}
	
	public Set<EsperienzaLavorativa> getListaEsperienze() {
		return this.listaEsperienze;
	}
}
