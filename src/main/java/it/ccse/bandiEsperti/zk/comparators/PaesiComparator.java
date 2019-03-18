package it.ccse.bandiEsperti.zk.comparators;


import it.ccse.bandiEsperti.business.model.Paesi;

import java.util.Comparator;

public class PaesiComparator implements Comparator<Paesi> {

	@Override
	public int compare(Paesi o1, Paesi o2) {
		// TODO Auto-generated method stub
		String denominazione1 = o1.getDenominazione();
		String denominazione2= o2.getDenominazione();
		return denominazione1.compareToIgnoreCase(denominazione2);
			
	}

}
