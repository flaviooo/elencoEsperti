/**
 * 
 */
package it.ccse.bandiEsperti.business.command.interfaces;

import java.util.Set;

/**
 * @author CCSE
 * Interfaccia necessaria alla realizzazione del pattern Command
 */
public interface ICommand {
	boolean execute();
	@SuppressWarnings("rawtypes")
	Set getSet();

}
