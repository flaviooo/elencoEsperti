package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.model.Esperto;

import java.util.Comparator;

public class EspertoComparator implements Comparator<Esperto> {

	@Override
	public int compare(Esperto o1, Esperto o2) {
		// TODO Auto-generated method stub
		String cognome1 = o1.getCognome();
		String cognome2= o2.getCognome();
		return cognome1.compareToIgnoreCase(cognome2);
			
	}

}
