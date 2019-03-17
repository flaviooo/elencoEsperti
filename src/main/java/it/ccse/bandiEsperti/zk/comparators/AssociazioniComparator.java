package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.model.Associazioni;
import java.util.Comparator;

public class AssociazioniComparator implements Comparator<Associazioni> {

	@Override
	public int compare(Associazioni o1, Associazioni o2) {
		// TODO Auto-generated method stub
		String descrizioneDelibera1 = o1.getDelibera().getDescrizioneDelibera();
		String descrizioneDelibera2 = o2.getDelibera().getDescrizioneDelibera();
		return descrizioneDelibera1.compareToIgnoreCase(descrizioneDelibera2);
			
	}
}
