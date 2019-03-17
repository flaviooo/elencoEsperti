package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.model.Pubblicazione;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.hibernate.HibernateUtil;

public class PubblicazioniDAO extends BaseDAO implements IPubblicazioniDAO {

	/**
	 * Nel metodo di salvataggio non viene invocato il "commit"; il "commit"
	 * viene richiamato nella fase conclusiva del processo di salvataggio, a
	 * fronte del salvataggio dell'esperto
	 * */
	@Override
	public void save(Pubblicazione pubblicazione) throws Exception {
//		currentSession().save(pubblicazione);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(pubblicazione);
		t.commit();
		session.close();			
	}

//	@Deprecated
//	/** Sostituito dal metodo "save" sprovvisto di "commit" */
//	public void saveCommit(Pubblicazione pubblicazione) throws Exception {
//		currentSession().save(pubblicazione);
//		currentSession().getTransaction().commit();
//	}

	/** Merge con commit */
	@Override
	public void merge(Pubblicazione element, Session session) throws Exception {
	    @SuppressWarnings("unused")
//	    Pubblicazione i2 = (Pubblicazione) currentSession().get(Pubblicazione.class, new Integer(element.getId()));
//	    i2 = (Pubblicazione)currentSession().merge(element); //Lo stato dell’oggetto viene variato con le proprietà modificate
	    Pubblicazione i2 = (Pubblicazione) session.get(Pubblicazione.class, new Integer(element.getId()));
		i2 = (Pubblicazione) session.merge(element); // Lo stato dell’oggetto
														// viene variato con le
														// proprietà modificate	
	}
	
//	/** "commit" esplicito sulla sessione */
//	public void commit() throws Exception {
//		currentSession().getTransaction().commit();
//	}	
	
//	@SuppressWarnings("unchecked")
//	public List<Pubblicazione> findAll() throws Exception {
//		Criteria criteria = currentSession().createCriteria(Pubblicazione.class).addOrder(Order.desc("data"));
//		return criteria.list();
//	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Pubblicazione> findAll() throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(Pubblicazione.class).addOrder(Order.desc("data"));
		List<Pubblicazione> list = criteria.list();
		t.commit();
		session.close();

		return list;
	}
	
	
	@Override
	public List<Pubblicazione> findByIdEsperto(Integer idEsperto) throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();

		// System.out.println("STATISTICHE CACHE:" +
		// HibernateUtil.getSessionFactory().getStatistics().toString());
		Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(Pubblicazione.class).add(Restrictions.eq("id_esperti", idEsperto));
		criteria.addOrder(Order.desc("data"));

		List<Pubblicazione> list = criteria.list();

		System.out.println(" (ISTRUZIONE) NUMERO DI RECORD: " + list.size());

		t.commit();
		session.close();
		return list;
	}
	
	@Override
	public Integer countPubblicationByIdEsperto(Integer idEsperto) throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(Pubblicazione.class).add(Restrictions.eq("id_esperti", idEsperto));
		Integer res= ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		

		t.commit();
		session.close();
		return res;
	}
	
	
//	public Pubblicazione findById(int id) {
//		return (Pubblicazione) currentSession().load(Pubblicazione.class, id);
//	}
//	
	
	@Override
	public Pubblicazione findById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Pubblicazione element = (Pubblicazione) session.load(Pubblicazione.class, id);
		session.close();
		return element;
	}
	
	
//	public void delete(Pubblicazione p) {
//		currentSession().delete(p);
//		currentSession().getTransaction().commit();
//	}
//
//	public void delete(int id) {
//		Pubblicazione p = (Pubblicazione) currentSession().load(Pubblicazione.class, id);
//		currentSession().delete(p);
//		currentSession().getTransaction().commit();
//	}		
	
	@Override
	public void delete(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Pubblicazione e = (Pubblicazione) session.load(Pubblicazione.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}		
}
