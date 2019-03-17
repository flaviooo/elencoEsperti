package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.model.Istruzione;

import java.util.List;

import org.hibernate.Session;

public interface IIstruzioneDAO {

	/**
	 * Nel metodo di salvataggio non viene invocato il "commit"; il "commit"
	 * viene richiamato nella fase conclusiva del processo di salvataggio, a
	 * fronte del salvataggio dell'esperto
	 * @return 
	 * */
	public Integer save(Istruzione istruzione) throws Exception;

	/** Merge con commit */
	public void merge(Istruzione istruzione, Session session) throws Exception;

	@SuppressWarnings("unchecked")
	public List<Istruzione> findAll() throws Exception;

	public List<Istruzione> findByIdEsperto(Integer idEsperto) throws Exception;

	public Istruzione findById(int id);

	public void delete(int id);

}