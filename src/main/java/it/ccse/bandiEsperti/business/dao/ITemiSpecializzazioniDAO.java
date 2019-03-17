package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.model.Specializzazione;
import it.ccse.bandiEsperti.business.model.Tema;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

public interface ITemiSpecializzazioniDAO {

	public abstract Tema getTemiBySpecializzazioniId(int idSpecializzazioni);
	
	public abstract List<Specializzazione> getSpecializzazioniByTema(int idTema);

	public abstract Tema getTema(int id);

	public abstract Specializzazione getSpecializzazione(int id);

	public abstract List<Tema> getListaTemi();

	public abstract Tema findById(Integer id);

	public abstract void delete(Integer id);

	public abstract  void merge(Tema element, Session session) throws Exception;

	public abstract Integer save(Serializable tema) throws Exception;

	

}