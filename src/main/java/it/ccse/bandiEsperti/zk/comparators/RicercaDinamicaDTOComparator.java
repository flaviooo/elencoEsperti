package it.ccse.bandiEsperti.zk.comparators;


import it.ccse.bandiEsperti.business.dto.EspertoRicercaDinamicaDTO;


import java.util.Comparator;

public class RicercaDinamicaDTOComparator implements Comparator<EspertoRicercaDinamicaDTO> {

	@Override
	public int compare(EspertoRicercaDinamicaDTO o1, EspertoRicercaDinamicaDTO o2) {
		// TODO Auto-generated method stub
		String cognome1 = o1.getCognome();
		String cognome2 = o2.getCognome();
		return cognome1.compareToIgnoreCase(cognome2);
			
	}

}
