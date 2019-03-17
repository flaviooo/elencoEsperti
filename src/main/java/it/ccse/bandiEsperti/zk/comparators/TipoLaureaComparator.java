package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.model.TipoLaurea;
import java.util.Comparator;

public class TipoLaureaComparator implements Comparator<TipoLaurea> {

	@Override
	public int compare(TipoLaurea o1, TipoLaurea o2) {
		// TODO Auto-generated method stub
		String tipoLaurea1 = o1.getDenominazione();
		String tipoLaurea2 = o2.getDenominazione();
		return (-1)*tipoLaurea1.compareToIgnoreCase(tipoLaurea2);
			
	}

}
