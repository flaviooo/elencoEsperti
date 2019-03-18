package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.model.Pubblicazione;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class PubblicazioneComparator implements Comparator {

	  public int compare(Object o1, Object o2) {
	    Pubblicazione p1=(Pubblicazione)o1;
	    Pubblicazione p2=(Pubblicazione)o2;
		   
	    int compareData = p2.getData().compareTo(p1.getData());
	    
	    if(compareData != 0)
	    	return compareData;
	    
	    return p1.getId() - p2.getId();
    }

}
