/**
 * 
 */
package it.ccse.bandiEsperti.business.command.interfaces;

import java.io.Serializable;


/**
 * @author CCSE
 * Interfaccia necessaria alla realizzazione del pattern Command
 */
public interface IRicerca extends Serializable{
	
	public String demominazione = "";
	public Integer id = 0;
	
	public Integer getId();
	public String getDenominazione();
	

}
