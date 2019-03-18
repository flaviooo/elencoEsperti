package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.model.Pubblicazione;

import java.util.List;

import org.hibernate.Session;

public interface IPubblicazioniDAO {

	/**
	 * Nel metodo di salvataggio non viene invocato il "commit"; il "commit"
	 * viene richiamato nella fase conclusiva del processo di salvataggio, a
	 * fronte del salvataggio dell'esperto
	 * */
	public void save(Pubblicazione pubblicazione) throws Exception;

	/** Merge con commit */
	public void merge(Pubblicazione element, Session session) throws Exception;

	@SuppressWarnings("unchecked")
	public List<Pubblicazione> findAll() throws Exception;

	public List<Pubblicazione> findByIdEsperto(Integer idEsperto)
			throws Exception;

	public Pubblicazione findById(int id);

	public void delete(int id);
	
	public Integer countPubblicationByIdEsperto(Integer idEsperto) throws Exception;

}