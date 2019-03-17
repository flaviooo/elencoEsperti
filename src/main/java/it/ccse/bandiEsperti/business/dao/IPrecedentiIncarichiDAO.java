package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.model.PrecedenteIncarico;

import java.util.List;

import org.hibernate.Session;

public interface IPrecedentiIncarichiDAO {

	/**
	 * Nel metodo di salvataggio non viene invocato il "commit"; il "commit"
	 * viene richiamato nella fase conclusiva del processo di salvataggio, a
	 * fronte del salvataggio dell'esperto
	 * */
	public void save(PrecedenteIncarico precedenteIncarico) throws Exception;

	/** Merge con commit */
	public void merge(PrecedenteIncarico element, Session session)
			throws Exception;

	@SuppressWarnings("unchecked")
	public List<PrecedenteIncarico> findAll() throws Exception;

	public List<PrecedenteIncarico> findByIdEsperto(Integer idEsperto)
			throws Exception;

	public PrecedenteIncarico findById(int id);

	//	
	//	public void delete(PrecedenteIncarico p) {
	//		currentSession().delete(p);
	//		currentSession().getTransaction().commit();
	//	}
	//
	//	public void delete(int id) {
	//		PrecedenteIncarico p = (PrecedenteIncarico) currentSession().load(PrecedenteIncarico.class, id);
	//		currentSession().delete(p);
	//		currentSession().getTransaction().commit();
	//	}		

	public void delete(int id);

}