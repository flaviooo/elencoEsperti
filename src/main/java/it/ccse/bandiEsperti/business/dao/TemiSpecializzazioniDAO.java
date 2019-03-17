package it.ccse.bandiEsperti.business.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.hibernate.HibernateUtil;

import it.ccse.bandiEsperti.business.model.Specializzazione;
import it.ccse.bandiEsperti.business.model.Tema;

public class TemiSpecializzazioniDAO extends BaseDAO implements ITemiSpecializzazioniDAO, IAdministrationDAO {

	private static final Log log = LogFactory.getLog(TemiSpecializzazioniDAO.class);
	@Override
	public Tema getTemiBySpecializzazioniId(int idSpecializzazioni) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Specializzazione.class);
		criteria.add(Restrictions.eq("id", idSpecializzazioni));
		Specializzazione spec = (Specializzazione) criteria.uniqueResult();
		Tema tema = spec.getTema();
		
		t.commit();
		session.close();
		return tema;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Specializzazione> getSpecializzazioniByTema(int idTema) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
	
		Criteria criteria = session.createCriteria(Specializzazione.class);

		criteria.createCriteria("tema", "tema", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("tema.id", idTema));

		List<Specializzazione> list = criteria.list();
		
		t.commit();
		session.close();
		
		return list;
	}
	
	
	@Override
	public boolean checkIfExist(Serializable tema) throws Exception {
		boolean res=false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Specializzazione.class).add( Restrictions.eq("tema", (Tema)tema));
		List<Specializzazione> list = criteria.list();
		t.commit();
		session.close();
		if(list.size()>0){
			res=true;
		}
		return res;
	}

	/** Merge con commit */
	@Override
	public void merge(Tema element, Session session) throws Exception {
	    @SuppressWarnings("unused")
	    Tema i2 = (Tema) session.get(Tema.class, new Integer(element.getId()));
		i2 = (Tema) session.merge(element); 
	}

	@Override
	public Tema findById(Integer id) {
		log.debug("getting TipoLaurea instance with id: " + id);
		try {
			Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(Tema.class);
			criteria.add(Restrictions.eq("id", id));
			Tema instance = (Tema) criteria.uniqueResult();
			
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	

	@Override
	public void delete(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Tema e = (Tema) session.load(Tema.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}
	
	@Override
	public Tema getTema(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
	
		Criteria criteria = session.createCriteria(Tema.class);

		criteria.add(Restrictions.idEq(id));
		Tema temi = (Tema) criteria.uniqueResult();
		
		t.commit();
		session.close();
		
		return temi;
	}

	@Override
	public Specializzazione getSpecializzazione(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
	
		Criteria criteria = session.createCriteria(Specializzazione.class);

		criteria.add(Restrictions.idEq(id));
		Specializzazione temi = (Specializzazione) criteria.uniqueResult();
		
		t.commit();
		session.close();
		
		return temi;
	}

	@Override
	public List<Tema> getListaTemi() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
	
		Criteria criteria = session.createCriteria(Tema.class);

		List<Tema> lista = (List<Tema>) criteria.list();
		
		t.commit();
		session.close();
		
		return lista;
	}
	
	public Integer save(Serializable element) {
		log.debug("save Tema instance");
		Integer id = null;
	
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        id = (Integer)session.save(element);
			t.commit();
			session.close();		
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			
		}
		return id;
		
	}

	
}
