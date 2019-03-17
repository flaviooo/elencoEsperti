package it.ccse.bandiEsperti.zk.comparators;
import it.ccse.bandiEsperti.business.model.Tema;

import java.util.Comparator;

public class TemiComparator implements Comparator<Tema> {

	@Override
	public int compare(Tema o1, Tema o2) {
		// TODO Auto-generated method stub
		String nome1 = o1.getNome();
		String nome2= o2.getNome();
		return nome1.compareToIgnoreCase(nome2);
			
	}

}
