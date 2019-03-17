package it.ccse.bandiEsperti.zk.comparators;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

public class ComparatorFactory {
	 private ConcurrentHashMap<String, Comparator> comparators;
	 private static ComparatorFactory _instance = null;

	  private ComparatorFactory() {
		 comparators = new ConcurrentHashMap<String, Comparator>();
		 comparators.put("IRicerca", new RicercaComparator());
		 comparators.put("CampiRicerca", new CampiRicercaComparator());
		 comparators.put("Competenza", new CompetenzaComparator());
		 comparators.put("Associazioni", new AssociazioniComparator());
		 comparators.put("Delibera", new DeliberaComparator());
		 comparators.put("EsperienzaLavorativa", new EsperienzeProfessionaliComparator());
		 comparators.put("Istruzione", new IstruzioneComparator());
		 
		 comparators.put("Paesi", new PaesiComparator());
		 comparators.put("Pubblicazione", new PubblicazioneComparator());
		 comparators.put("Regioni", new RegioniComparator());
		 comparators.put("Specializzazione", new SpecializzazioniComparator());
		 comparators.put("Tema", new TemiComparator());
		 comparators.put("TipoLaurea", new TipoLaureaComparator());
		 comparators.put("TitoloLaurea", new TitoloLaureaComparator());
		 comparators.put("Esperto", new EspertoComparator());
		 comparators.put("EspertoRicercaDinamicaDTO", new RicercaDinamicaDTOComparator());
		 comparators.put("EspertoDTO", new EspertoDTOComparator());
		 
	
	  }

	  public static synchronized ComparatorFactory getInstance() {
	    if (_instance == null) {
	    	_instance = new ComparatorFactory();
	    }

	    return _instance;
	  }

	  public synchronized void clear() {
		  comparators.clear();
	  }

	  public synchronized void store(String key, Comparator value) {
		  comparators.put(key, value);
	  }

	  public synchronized Comparator retrieve(String key) {
	    return comparators.get(key);
	  }

	  public synchronized Comparator remove(String key) {
	    return comparators.remove(key);
	  }

	  public synchronized boolean containsKey(Object key) {
	    return comparators.containsKey(key);
	  }

	}
