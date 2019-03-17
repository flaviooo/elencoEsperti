package it.ccse.bandiEsperti.zk.composers;

import java.util.ArrayList;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import it.ccse.bandiEsperti.business.command.RicercaCompetenzeCommand;
import it.ccse.bandiEsperti.business.model.Competenza;
import it.ccse.bandiEsperti.zk.comparators.CompetenzaComparator;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import java.util.Collections;

public class AdminCompetenzeComposer extends GenericForwardComposer {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(AdminCompetenzeComposer.class);

	private int idEsperto;
	
	private Set<Competenza> listaCompetenze;
	
	public void doAfterCompose(Component win) throws Exception {
		super.doAfterCompose(win);
		
		try{
			this.idEsperto = (Integer) arg.get("idEsperto");
			this.initModel(false);
		} catch (Exception e) {
			log.error(e.getMessage());
			this.self.detach();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		boolean esito = false;
		try{	
			RicercaCompetenzeCommand commandCompetenze = new RicercaCompetenzeCommand(idEsperto);
			esito = commandCompetenze.execute();
			if(!esito){
				throw new Exception("Non è possibile eseguire l'operazione di ricerca delle Competenze");
			}
			
			List<Competenza> tmpCompList = new ArrayList<Competenza>(commandCompetenze.getSet());
			Collections.sort(tmpCompList, new CompetenzaComparator());
			this.listaCompetenze = new LinkedHashSet<Competenza>(tmpCompList);
			
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new WrongValueException("Si è verificato un errore nella inizializzazione della pagina");
		}
	}
	
	public Set<Competenza> getListaCompetenze() {
		return this.listaCompetenze;
	}
}
