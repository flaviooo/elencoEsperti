package it.ccse.bandiEsperti.zk.comparators;
import it.ccse.bandiEsperti.business.model.Specializzazione;
import java.util.Comparator;

public class SpecializzazioniComparator implements Comparator<Specializzazione> {

	@Override
	public int compare(Specializzazione o1, Specializzazione o2) {
		// TODO Auto-generated method stub
		String nome1 = o1.getNome();
		String nome2= o2.getNome();
		return nome1.compareToIgnoreCase(nome2);
			
	}

}
