package it.ccse.bandiEsperti.business.dao;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.Session;

import it.ccse.bandiEsperti.business.model.Atenei;
import it.ccse.bandiEsperti.business.model.Citta;

public interface IAteneoDAO {

	public abstract void persist(Atenei transientInstance);

	public abstract void remove(Atenei persistentInstance);

	public abstract Atenei findById(Integer id);

	public abstract Set<Atenei> findAll() throws Exception;

	public abstract Set<Atenei> findByCitta(Citta citta) throws Exception;

	public abstract Integer save(Serializable element)  throws Exception;

	public abstract void delete(Integer id);

	void merge(Atenei element, Session session) throws Exception;

}