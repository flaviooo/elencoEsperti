package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;



import java.util.List;
import org.hibernate.Session;

public interface IEsperienzeLavorativeDAO {

	/**
	 * Nel metodo di salvataggio non viene invocato il "commit"; il "commit"
	 * viene richiamato nella fase conclusiva del processo di salvataggio, a
	 * fronte del salvataggio dell'esperto
	 * @return 
	 * */
	public Integer save(EsperienzaLavorativa esperienza) throws Exception;

	/** Merge con commit */
	public void merge(EsperienzaLavorativa element, Session session) throws Exception;

	@SuppressWarnings("unchecked")
	public List<EsperienzaLavorativa> findAll() throws Exception;

	public EsperienzaLavorativa findById(int id);

	public List<EsperienzaLavorativa> findByIdEsperto(Integer idEsperto,
			FiltroRicercaEspertiDTO filtroRicerca) throws Exception;

	public void delete(int id);

	public List<EsperienzaLavorativa> findCarriereByIdEsperto(Integer idEsperto)
			throws Exception;

	public  EsperienzaLavorativa findCarrieraPrincipaleByIdEsperto (Integer idEsperto) throws Exception;
	
	public List<EsperienzaLavorativa> findEsperienzeLavorativeByIdEsperto(Integer idEsperto) throws Exception;

	
	

}

