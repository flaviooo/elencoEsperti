package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.command.interfaces.ICommand;
import it.ccse.bandiEsperti.business.model.Esperto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * In questa classe vengono inseriti tutti gli oggetti comuni tra i command
 * */
public abstract class AbstractCommand implements ICommand {
	
	Logger logger = Logger.getLogger(AbstractCommand.class);
	
	@SuppressWarnings("rawtypes")
	public Set set;
	
	/**
	 * Classe Model Esperto
	 * */
	private Esperto e;
	
	/**
	 * id utilizzato per le operazioni di cancellazione
	 * */
	private Integer id;
	
	/**
	 * id utilizzato per le operazioni in cui è necessario usare l'Esperto
	 * */
	private Integer idEsperto;	

	/**
	 * lista utilizzata per le operazioni di aggiornamento batch
	 * */
	@SuppressWarnings("rawtypes")
	private List list;	

	/** 
	 * Costruttore classe astratta 
	 **/
	public AbstractCommand(Esperto e) {
		this.e = e;
	}	
	
	@Override
	public boolean execute() {
		logger.debug("execute dell'AbstractCommand.");
		return true;
	}
	
	public Esperto getE() {
		return e;
	}

	public void setE(Esperto e) {
		this.e = e;
	}
	
	@SuppressWarnings("rawtypes")
	public Set getSet() {
		return set;
	}

	@SuppressWarnings("rawtypes")
	public void setSet(Set set) {
		this.set = set;
	}	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
	

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
	
	
	public Integer getIdEsperto() {
		return idEsperto;
	}

	public void setIdEsperto(Integer idEsperto) {
		this.idEsperto = idEsperto;
	}

	

	public boolean salvaCompetenzaEsperto(String competenza) {
		// TODO Auto-generated method stub
		return false;
	}	
	
	public  boolean checkIfExist(Serializable serializable){
		// TODO Auto-generated method stub
		return false;
	}
	public Serializable findById(Integer id){
		return null;
	}

	public Integer inserisci(Serializable serializable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean aggiorna(Serializable serializable){
		// TODO Auto-generated method stub
		return false;
	}

	public boolean salvaPresentazioneEsperto(String presentazione) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
