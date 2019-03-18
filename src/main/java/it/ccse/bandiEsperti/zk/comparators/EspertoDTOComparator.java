package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.dto.EspertoDTO;
import it.ccse.bandiEsperti.business.model.Esperto;

import java.util.Comparator;

public class EspertoDTOComparator implements Comparator<EspertoDTO> {

	@Override
	public int compare(EspertoDTO o1, EspertoDTO o2) {
		// TODO Auto-generated method stub
		String cognome1 = o1.getCognome();
		String cognome2= o2.getCognome();
		return cognome1.compareToIgnoreCase(cognome2);
			
	}

}
