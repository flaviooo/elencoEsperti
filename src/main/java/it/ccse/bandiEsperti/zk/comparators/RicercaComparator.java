package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.command.interfaces.IRicerca;
import java.util.Comparator;

public class RicercaComparator implements Comparator<IRicerca> {

	@Override
	public int compare(IRicerca o1, IRicerca o2) {
		// TODO Auto-generated method stub
		String denominazione1 = o1.getDenominazione();
		String denominazione2= o2.getDenominazione();
		return denominazione1.compareToIgnoreCase(denominazione2);
			
	}

}
