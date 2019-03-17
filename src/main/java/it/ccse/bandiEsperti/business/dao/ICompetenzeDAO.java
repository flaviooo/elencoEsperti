package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.Competenza;

import java.util.List;

import org.hibernate.Session;

public interface ICompetenzeDAO {

	/**
	 * Nel metodo di salvataggio non viene invocato il "commit";
	 * il "commit" viene richiamato nella fase conclusiva del processo di salvataggio,
	 * a fronte del salvataggio dell'esperto
	 * */
	public abstract Integer save(Competenza competenza) throws Exception;

	/** Merge con commit */
	public abstract void merge(Competenza element, Session session) throws Exception;

	@SuppressWarnings("unchecked")
	public abstract List<Competenza> findAll() throws Exception;

	public abstract List<Competenza> findByIdEsperto(Integer idEsperto,
			FiltroRicercaEspertiDTO filtroRicerca) throws Exception;

	public abstract Competenza findById(int id);

	public abstract void delete(int id);

	public abstract List<Competenza> findBySpecializzazione(Integer idSpecializzazione)
			throws Exception;

	public abstract List<Competenza> findBySpecializzazioneTest(
			Integer idSpecializzazione) throws Exception;

}