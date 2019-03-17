package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.model.PrecedenteIncarico;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.hibernate.HibernateUtil;

public class PrecedentiIncarichiDAO extends BaseDAO implements IPrecedentiIncarichiDAO, IAdministrationDAO {

	/**
	 * Nel metodo di salvataggio non viene invocato il "commit"; il "commit"
	 * viene richiamato nella fase conclusiva del processo di salvataggio, a
	 * fronte del salvataggio dell'esperto
	 * */
	@Override
	public void save(PrecedenteIncarico precedenteIncarico) throws Exception {
//		currentSession().save(precedenteIncarico);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(precedenteIncarico);
		t.commit();
		session.close();		
	}

//	@Deprecated
//	/** Sostituito dal metodo "save" sprovvisto di "commit" */
//	public void saveCommit(PrecedenteIncarico precedenteIncarico) throws Exception {
//		currentSession().save(precedenteIncarico);
//		currentSession().getTransaction().commit();
//	}

	/** Merge con commit */
	@Override
	public void merge(PrecedenteIncarico element, Session session) throws Exception {
	    @SuppressWarnings("unused")
	    PrecedenteIncarico i2 = (PrecedenteIncarico) session.get(PrecedenteIncarico.class, new Integer(element.getId()));
		i2 = (PrecedenteIncarico) session.merge(element); // Lo stato dell’oggetto
														// viene variato con le
														// proprietà modificate
	}
	
//	/** "commit" esplicito sulla sessione */
//	public void commit() throws Exception {
//		currentSession().getTransaction().commit();
//	}	
//	
//	@SuppressWarnings("unchecked")
//	public List<PrecedenteIncarico> findAll() throws Exception {
//		Criteria criteria = currentSession().createCriteria(PrecedenteIncarico.class).addOrder(Order.desc("data"));
//		return criteria.list();
//	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PrecedenteIncarico> findAll() throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(PrecedenteIncarico.class).addOrder(Order.desc("data_inizio"));
		List<PrecedenteIncarico> list = criteria.list();
		t.commit();
		session.close();

		return list;
	}
	
	@Override
	public List<PrecedenteIncarico> findByIdEsperto(Integer idEsperto) throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();

		// System.out.println("STATISTICHE CACHE:" +
		// HibernateUtil.getSessionFactory().getStatistics().toString());
		Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(PrecedenteIncarico.class).add(Restrictions.eq("id_esperti", idEsperto));
//		criteria.addOrder(Order.desc("data"));

		List<PrecedenteIncarico> list = criteria.list();

		System.out.println(" (ISTRUZIONE) NUMERO DI RECORD: " + list.size());

		t.commit();
		session.close();
		return list;
	}
	
	
//	public PrecedenteIncarico findById(int id) {
//		return (PrecedenteIncarico) currentSession().load(PrecedenteIncarico.class, id);
//	}
	
	@Override
	public PrecedenteIncarico findById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		PrecedenteIncarico element = (PrecedenteIncarico) session.load(PrecedenteIncarico.class, id);
		session.close();
		return element;
	}
//	
//	public void delete(PrecedenteIncarico p) {
//		currentSession().delete(p);
//		currentSession().getTransaction().commit();
//	}
//
//	public void delete(int id) {
//		PrecedenteIncarico p = (PrecedenteIncarico) currentSession().load(PrecedenteIncarico.class, id);
//		currentSession().delete(p);
//		currentSession().getTransaction().commit();
//	}		
	
	@Override
	public void delete(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		PrecedenteIncarico e = (PrecedenteIncarico) session.load(PrecedenteIncarico.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}

	@Override
	public boolean checkIfExist(Serializable delibera) throws Exception {
		boolean res=false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(PrecedenteIncarico.class).add( Restrictions.eq("delibera", (Delibera)delibera));
		List<EsperienzaLavorativa> list = criteria.list();
		t.commit();
		session.close();
		if(list.size()>0){
			res=true;
		}
		return res;
	}
}