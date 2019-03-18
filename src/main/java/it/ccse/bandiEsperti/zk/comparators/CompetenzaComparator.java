package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.model.Competenza;

import java.util.Comparator;

public class CompetenzaComparator implements Comparator<Competenza> {

	@Override
	public int compare(Competenza o1, Competenza o2) {
		// TODO Auto-generated method stub
		int idTemaComp1 = o1.getSpecializzazione().getTema().getId();
		int idTemaComp2 = o2.getSpecializzazione().getTema().getId();
		int idComp1 = o1.getSpecializzazione().getId();
		int idComp2 = o2.getSpecializzazione().getId();
		if (idTemaComp1 < idTemaComp2)
			return -1;
		else if(idTemaComp1 > idTemaComp2)
			return 1;
		
		if (idComp1 < idComp2)
			return -1;
		else if (idComp1 > idComp2)
			return 1;
		else
			return 0;
		
			
	}

}
