package it.ccse.bandiEsperti.zk.comparators;

import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class EsperienzeProfessionaliComparator implements Comparator {

	  public int compare(Object o1, Object o2) {
	    EsperienzaLavorativa p1=(EsperienzaLavorativa)o1;
	    EsperienzaLavorativa p2=(EsperienzaLavorativa)o2;
	    
	    int compareDataFine = 0;
	    
	    if (p1.getDataFine() == null && p2.getDataFine() != null)
	    	compareDataFine = -1;
	    else if (p1.getDataFine() != null && p2.getDataFine() == null)
	    	compareDataFine = 1;
	    else if (p1.getDataFine() == null && p2.getDataFine() == null)
	    	compareDataFine = 0;
	    else
	    	compareDataFine = p2.getDataFine().compareTo(p1.getDataFine());
	    
	   
	    int compareInCorso = p2.isInCorso().compareTo(p1.isInCorso());

	    if(compareInCorso != 0)
	    	return compareInCorso;
	    
	    if (compareDataFine != 0)
	    	return compareDataFine;
	    
	    //return 0;
	    return p1.getId() - p2.getId();
    }

}
