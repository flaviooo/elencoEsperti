package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.model.Delibera;


import java.util.Comparator;

public class DeliberaComparator implements Comparator<Delibera> {

	@Override
	public int compare(Delibera o1, Delibera o2) {
		// TODO Auto-generated method stub
		String codiceDelibera1 = o1.getCodiceDelibera();
		String codiceDelibera2= o2.getCodiceDelibera();
		return codiceDelibera1.compareToIgnoreCase(codiceDelibera2);
			
	}

}
