package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.model.Provincie;


import java.util.Set;

public interface IProvincieDAO {

	public abstract void persist(Provincie transientInstance);

	public abstract void remove(Provincie persistentInstance);

	public abstract Provincie merge(Provincie detachedInstance);

	public abstract Provincie findById(Integer id);

	public abstract Provincie findByCodiceProvincia(String codiceProvincia)
			throws Exception;

	 Set<Provincie> findByCodiceRegione(String codiceRegione) throws Exception;
	 
	public abstract Set<Provincie> findAll() throws Exception;

}