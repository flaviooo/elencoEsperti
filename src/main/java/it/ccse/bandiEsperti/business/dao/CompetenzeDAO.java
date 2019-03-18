package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO.TipoFiltro;
import it.ccse.bandiEsperti.business.model.Competenza;
import it.ccse.bandiEsperti.business.model.Specializzazione;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.hibernate.HibernateUtil;

public class CompetenzeDAO extends BaseDAO implements ICompetenzeDAO {
	
	private static final Log log = LogFactory.getLog(CompetenzeDAO.class);
	/**
	 * Nel metodo di salvataggio non viene invocato il "commit";
	 * il "commit" viene richiamato nella fase conclusiva del processo di salvataggio,
	 * a fronte del salvataggio dell'esperto
	 * */	
	@Override
	public Integer save(Competenza competenza) throws Exception {
		log.debug("save Competenza instance");
		Integer id = null;
		 Transaction t = null;
		 Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
	        t = session.beginTransaction();
	        id = (Integer)session.save(competenza);
				
		} finally {
			t.commit();
			session.close();	
			
		}
		return id;
	}
	
	/** Merge con commit */
	@Override
	public void merge(Competenza element, Session session) throws Exception {
	    @SuppressWarnings("unused")
//	    Competenza i2 = (Competenza) currentSession().get(Competenza.class, new Integer(element.getId()));
//	    i2 = (Competenza)currentSession().merge(element); //Lo stato dell’oggetto viene variato con le proprietà modificate
	    Competenza i2 = (Competenza) session.get(Competenza.class, new Integer(element.getId()));
		i2 = (Competenza) session.merge(element);     
	}
	
//	@Deprecated 
//	/** Sostituito dal metodo "save" sprovvisto di "commit" */
//	public void saveCommit(EsperienzaLavorativa esperienza) throws Exception {
//		currentSession().save(esperienza);
//		currentSession().getTransaction().commit();
//	}	

//	/** "commit" esplicito sulla sessione */
//	public void commit() throws Exception {
//		currentSession().getTransaction().commit();
//	}	
		
	@Override
	@SuppressWarnings("unchecked")
	public List<Competenza> findAll() throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(Competenza.class).addOrder(Order.desc("id")).setCacheable(true);
		List<Competenza> list = criteria.list();
		t.commit();
		session.close();

		return list;
	}	
	
	

	@Override
	public List<Competenza> findByIdEsperto(Integer idEsperto, FiltroRicercaEspertiDTO filtroRicerca) throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();

		// System.out.println("STATISTICHE CACHE:" +
		// HibernateUtil.getSessionFactory().getStatistics().toString());
		Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(Competenza.class).add(Restrictions.eq("id_esperti", idEsperto));
//		criteria.addOrder(Order.desc("data"));
		
		if(filtroRicerca != null){
			/*
			 * Competenza
			 */
			if(filtroRicerca.getCompetenze().get(TipoFiltro.FILTRADETTAGLICOMPETENZE) != null && (Boolean)filtroRicerca.getCompetenze().get(TipoFiltro.FILTRADETTAGLICOMPETENZE) == true){
			
				if(filtroRicerca.getCompetenze().get(TipoFiltro.LISTACOMPETENZE) != null){
					List<Specializzazione> specializzazioni = (List<Specializzazione>)filtroRicerca.getCompetenze().get(TipoFiltro.LISTACOMPETENZE);
					//Retrò like code style
					Integer[] specializzazioniId = new Integer[specializzazioni.size()];
					
					for(int i=0; i<specializzazioni.size(); i++){
						Specializzazione specializzazione = specializzazioni.get(i);
						specializzazioniId[i] = new Integer(specializzazione.getId());
					}
					
					criteria.createCriteria("specializzazione").
					add(Restrictions.in("id", specializzazioniId));
				}
			
			}
		}

		List<Competenza> list = criteria.list();

		System.out.println(" (ISTRUZIONE) NUMERO DI RECORD: " + list.size());

		t.commit();
		session.close();
		return list;
	}	
	
	
//	@SuppressWarnings("unchecked")
//	public List<Competenza> findAll() throws Exception {
//		return currentSession().createCriteria(Competenza.class).addOrder( Order.desc("id") ).setCacheable(true).list();
//	}
	
	
	@Override
	public Competenza findById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Competenza element = (Competenza) session.load(Competenza.class, id);
		session.close();
		return element;
	}	

//	public Competenza findById(int idCompetenza) {
//		return (Competenza) currentSession().load(Competenza.class, idCompetenza);
//	}
	
//	public void delete(Competenza c) {
//		currentSession().delete(c);
//		currentSession().getTransaction().commit();
//	}
//
//	public void delete(int id) {
//		Competenza c = (Competenza) currentSession().load(Competenza.class, id);
//		currentSession().delete(c);
//		currentSession().getTransaction().commit();
//	}		

	@Override
	public void delete(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Competenza e = (Competenza) session.load(Competenza.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}		
	
	@Override
	public List<Competenza> findBySpecializzazione(Integer idSpecializzazione) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		// STEP 1: RECUPERO DI TUTTE LE COMPETENZE DATE LE SPECIALIZZAZIONI
		Criteria criteriaCompetenze = session.createCriteria(Competenza.class);

		criteriaCompetenze.createCriteria("specializzazione", "specializzazione", Criteria.LEFT_JOIN);
		criteriaCompetenze.add(Restrictions.eq("specializzazione.id", idSpecializzazione));

		List<Competenza> lCompetenza = criteriaCompetenze.list();

		t.commit();
		session.close();
		return lCompetenza;
	}
	
	@Override
	public List<Competenza> findBySpecializzazioneTest(Integer idSpecializzazione) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		// STEP 1: RECUPERO DI TUTTE LE COMPETENZE DATE LE SPECIALIZZAZIONI
		Criteria criteriaCompetenze = session.createCriteria(Competenza.class);

		criteriaCompetenze.createCriteria("specializzazione", "specializzazione", Criteria.LEFT_JOIN);
		criteriaCompetenze.add(Restrictions.eq("specializzazione.id", idSpecializzazione));

//		criteriaCompetenze.createCriteria("specializzazione", "specializzazione", Criteria.LEFT_JOIN);
		criteriaCompetenze.add(Restrictions.eq("specializzazione.id", 2));
		
		List<Competenza> lCompetenza = criteriaCompetenze.list();

		t.commit();
		session.close();
		return lCompetenza;
	}	
}
