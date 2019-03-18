/**
 * 
 */
package it.ccse.bandiEsperti.business.command;

import it.ccse.bandiEsperti.business.service.EspertiService;
import it.ccse.bandiEsperti.business.service.IEspertiService;

/**
 * @author CCSE
 * Command necessario alla registrazione di un nuovo esperto
 */
public class LoginCommand extends AbstractCommand {

	private String username;
	private String password;
	
	
	/** 
	 * Costruttore di default del Login inibito
	 * @throws Exception 
	 **/	
	public LoginCommand() throws Exception {
		super(null);
		throw new Exception("[ERRORE] Non è possibile utilizzare il costruttore di default per questa classe.");
	}	
	
	/** 
	 * Costruttore specializzato per il Login
	 **/	
	public LoginCommand(String username, String password) {
		super(null);
		this.username = username;
		this.password = password;
	}	

	@Override
	public boolean execute() {
		
		boolean res = false;
		logger.debug("execute del Command: [" + this.getClass().getName() + "]");
		
		IEspertiService es = new EspertiService();
		
		if(username != null && !username.isEmpty() && 
		   password != null && !password.isEmpty()){
			try {
				setE(es.login(username, password));
				res = true;
			} catch (Exception e) {
				logger.debug(" errore nel login: ");
				e.printStackTrace();
			}
		}
		
		return res;
	}

	
}
