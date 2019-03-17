package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.model.Atenei;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.model.Istruzione;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.hibernate.HibernateUtil;

public class IstruzioneDAO extends BaseDAO implements IIstruzioneDAO, IAdministrationDAO {

	private static final Log log = LogFactory.getLog(IstruzioneDAO.class);
	/**
	 * Nel metodo di salvataggio non viene invocato il "commit"; il "commit"
	 * viene richiamato nella fase conclusiva del processo di salvataggio, a
	 * fronte del salvataggio dell'esperto
	 * */
	@Override
	public Integer save(Istruzione element) throws Exception {
		log.debug("save Delibera instance");
		Integer id = null;
		 Transaction t = null;
		 Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
	        t = session.beginTransaction();
	        id = (Integer)session.save(element);
				
		} finally {
			t.commit();
			session.close();	
		}
		return id;
	}

	// @Deprecated
	// /** Sostituito dal metodo "save" sprovvisto di "commit" */
	// public void _saveCommit(Istruzione istruzione) throws Exception {
	// currentSession().save(istruzione);
	// currentSession().getTransaction().commit();
	// }

	/** Merge con commit */
	@Override
	public void merge(Istruzione istruzione, Session session) throws Exception {
		@SuppressWarnings("unused")
		Istruzione i2 = (Istruzione) session.get(Istruzione.class, new Integer(istruzione.getId()));
		i2 = (Istruzione) session.merge(istruzione); // Lo stato dell’oggetto
														// viene variato con le
														// proprietà modificate
	}

	// /** "commit" esplicito sulla sessione */
	// public void _commit() throws Exception {
	// currentSession().getTransaction().commit();
	// }

	// @SuppressWarnings("unchecked")
	// public List<Istruzione> _findAll() throws Exception {
	//
	// Criteria criteria =
	// currentSession().createCriteria(Istruzione.class).addOrder(
	// Order.desc("data") );
	// return criteria.list();
	// }

	@Override
	@SuppressWarnings("unchecked")
	public List<Istruzione> findAll() throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(Istruzione.class).addOrder(Order.desc("data"));
		List<Istruzione> list = criteria.list();
		t.commit();
		session.close();

		return list;
	}

	@Override
	public List<Istruzione> findByIdEsperto(Integer idEsperto) throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();

		// System.out.println("STATISTICHE CACHE:" +
		// HibernateUtil.getSessionFactory().getStatistics().toString());
	//	Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(Istruzione.class).add(Restrictions.eq("id_esperti", idEsperto));
		criteria.addOrder(Order.desc("data"));

		List<Istruzione> list =(List<Istruzione>) criteria.list();
		//FIXME 
		System.out.println(" (ISTRUZIONE) NUMERO DI RECORD: " + list.size());

//		t.commit();
		session.close();
		return list;
	}

	@Override
	public Istruzione findById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Istruzione element = (Istruzione) session.load(Istruzione.class, id);
		session.close();
		return element;
	}
	
	// public void _delete(Istruzione i) {
	// currentSession().delete(i);
	// currentSession().getTransaction().commit();
	// }

	@Override
	public void delete(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Istruzione e = (Istruzione) session.load(Istruzione.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}

	@Override
	public boolean checkIfExist(Serializable ateneo) throws Exception {
		boolean res=false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Istruzione.class).add( Restrictions.eq("atenei", (Atenei)ateneo));
		List<EsperienzaLavorativa> list = criteria.list();
		t.commit();
		session.close();
		if(list.size()>0){
			res=true;
		}
		return res;
	}

}
