package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.model.CampiRicerca;
import java.util.Comparator;

public class CampiRicercaComparator implements Comparator<CampiRicerca> {

	@Override
	public int compare(CampiRicerca o1, CampiRicerca o2) {
		// TODO Auto-generated method stub
		String et1= o1.getEtichetta();
		String et2= o2.getEtichetta();
		return et1.compareToIgnoreCase(et2);
			
	}

}
