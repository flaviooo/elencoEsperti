package it.ccse.bandiEsperti.business.dao;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.Session;

import it.ccse.bandiEsperti.business.model.Specializzazione;

public interface ISpecializzazioniDAO {

	public abstract void persist(Specializzazione transientInstance) throws Exception;

	public abstract void remove(Specializzazione persistentInstance) throws Exception;

	public abstract Specializzazione merge(Specializzazione detachedInstance) throws Exception;

	public abstract Specializzazione findById(Integer id) throws Exception;
	
	public abstract Set<Specializzazione> findAll() throws Exception;

	public abstract void delete(Integer id) throws Exception;

	public abstract void merge(Specializzazione element, Session session) throws Exception;

	public abstract Integer save(Serializable element) throws Exception;
	
	

}