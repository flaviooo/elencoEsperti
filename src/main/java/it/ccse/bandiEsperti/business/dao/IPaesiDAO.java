package it.ccse.bandiEsperti.business.dao;

import java.util.Set;

import it.ccse.bandiEsperti.business.model.Paesi;

public interface IPaesiDAO {

	public abstract void persist(Paesi transientInstance);

	public abstract void remove(Paesi persistentInstance);

	public abstract Paesi merge(Paesi detachedInstance);

	public abstract Paesi findById(Integer id);
	
	public abstract Set<Paesi> findAll() throws Exception;
	
	public abstract Paesi findByCodicePaese(String codicePaese) throws Exception;

}