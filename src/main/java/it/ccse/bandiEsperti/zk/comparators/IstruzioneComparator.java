package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.model.Istruzione;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class IstruzioneComparator implements Comparator {

	  public int compare(Object o1, Object o2) {
	    Istruzione p1=(Istruzione)o1;
	    Istruzione p2=(Istruzione)o2;
	   
	    int compareData =p2.getData().compareTo(p1.getData());
	    
	    if(compareData != 0){
	    	return compareData;
	    }
	    return p1.getId() - p2.getId();
    }

}
