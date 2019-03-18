package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.model.TitoloLaurea;

import java.util.Comparator;

public class TitoloLaureaComparator implements Comparator<TitoloLaurea> {

	@Override
	public int compare(TitoloLaurea o1, TitoloLaurea o2) {
		// TODO Auto-generated method stub
		String titoloLaurea1 = o1.getDenominazione();
		String titoloLaurea2= o2.getDenominazione();
		return titoloLaurea1.compareToIgnoreCase(titoloLaurea2);
			
	}

}
