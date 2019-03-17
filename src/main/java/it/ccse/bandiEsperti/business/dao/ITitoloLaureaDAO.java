package it.ccse.bandiEsperti.business.dao;
import java.util.Set;

import org.hibernate.Session;

import it.ccse.bandiEsperti.business.model.TipoLaurea;
import it.ccse.bandiEsperti.business.model.TitoloLaurea;

public interface ITitoloLaureaDAO {
	public abstract void persist(TitoloLaurea transientInstance);
	public abstract void remove(TitoloLaurea persistentInstance);
	public abstract TitoloLaurea merge(TitoloLaurea detachedInstance);
	public abstract TitoloLaurea findById(Integer id);
	public abstract Set<TitoloLaurea> findAll() throws Exception;
	public abstract Set<TitoloLaurea> findByTipoLaurea(TipoLaurea tipoLaurea) throws Exception;
	public abstract void delete(Integer id) throws Exception;
	public abstract void merge(TitoloLaurea titoloLaurea, Session session) throws Exception;
	public abstract Integer save(TitoloLaurea element) throws Exception;
}
