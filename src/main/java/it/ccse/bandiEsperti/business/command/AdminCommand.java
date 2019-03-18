package it.ccse.bandiEsperti.business.command;

import java.io.Serializable;

import java.util.Set;

public interface AdminCommand {
	public  boolean checkIfExist(Serializable serializable);
	public Serializable findById(Integer id);
	public Integer inserisci(Serializable serializable);
	
	public boolean aggiorna(Serializable serializable);
	public Set list();
	

	
	public boolean delete(Integer id);
	
	

}
