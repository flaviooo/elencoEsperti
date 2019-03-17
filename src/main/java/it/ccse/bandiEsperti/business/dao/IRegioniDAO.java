package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.model.Paesi;
import it.ccse.bandiEsperti.business.model.Regioni;

import java.util.Set;

public interface IRegioniDAO {

	public abstract void persist(Regioni transientInstance);

	public abstract void remove(Regioni persistentInstance);

	public abstract Regioni merge(Regioni detachedInstance);

	public abstract Regioni findById(Integer id);

	public abstract Set<Regioni> findAll() throws Exception;
	
	public Regioni findByCodiceRegione(String codiceRegione) throws Exception;

	public abstract Set<Regioni> listaRegione(Paesi paesi) throws Exception;
}