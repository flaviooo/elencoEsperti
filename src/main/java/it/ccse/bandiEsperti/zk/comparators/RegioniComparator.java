package it.ccse.bandiEsperti.zk.comparators;


import it.ccse.bandiEsperti.business.model.Regioni;

import java.util.Comparator;

public class RegioniComparator implements Comparator<Regioni> {

	@Override
	public int compare(Regioni o1, Regioni o2) {
		// TODO Auto-generated method stub
		String denominazione1 = o1.getDenominazione();
		String denominazione2= o2.getDenominazione();
		return denominazione1.compareToIgnoreCase(denominazione2);
			
	}

}
