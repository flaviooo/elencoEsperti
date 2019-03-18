package  it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.Associazioni;
import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Tema;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.hibernate.HibernateUtil;

/**
 * Home object for domain model class TipoLaurea.
 * @see it.ccse.bandiEsperti.business.dao.TipoLaurea
 * @author Hibernate Tools
 */

public class AssociazioniDAO extends BaseDAO implements IAssociazioniDAO{

	private static final Log log = LogFactory.getLog(AssociazioniDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Associazioni transientInstance) {
		log.debug("persisting TipoLaurea instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	@Override
	public void delete(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Associazioni e = (Associazioni) session.load(Associazioni.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}

	public void remove(Associazioni persistentInstance) {
		log.debug("removing TipoLaurea instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Associazioni merge(Associazioni detachedInstance) {
		log.debug("merging TipoLaurea instance");
		try {
			Associazioni result = (Associazioni)HibernateUtil.getSessionFactory().openSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public Associazioni findById(Integer id) {
		log.debug("getting TipoLaurea instance with id: " + id);
		try {
			Associazioni instance = (Associazioni)HibernateUtil.getSessionFactory().openSession().get(Associazioni.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	//public int delete(int idEsperto, int idDelibera) throws Exception {
	public int delete(Esperto esperto, Delibera delibera) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		int result=0;
		
		
		//String hql = "delete "+Associazioni.class.getName() +" where esperto.id=:idEsperto and delibera.id=:idDelibera";
		String hql = "delete "+Associazioni.class.getName() +" where esperto=:esperto and delibera=:delibera";
		    
		Query queryObject = session.createQuery(hql);
		queryObject.setParameter("esperto", esperto);
		queryObject.setParameter("delibera", delibera);
		result = queryObject.executeUpdate();
		t.commit();
		session.close();
		return result;
	
	}

	
	@Override
	@SuppressWarnings("unchecked")
	
	public Set<Associazioni> ricerca(List<Esperto> listEsperti, List<Tema> listTemi, List<Delibera> listDelibere) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Associazioni.class);
		if(listEsperti.size()>0){
			criteria.add(Restrictions.in("esperto", listEsperti));
		}
		if(listTemi.size()>0){
			criteria.add(Restrictions.in("tema", listTemi));
		}
		if(listDelibere.size()>0){
			criteria.add(Restrictions.in("delibera", listDelibere));
		}
		Set<Associazioni> listaAssociazioni = new HashSet<Associazioni>(criteria.list());
		t.commit();
		session.close();
		return listaAssociazioni;
	
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Associazioni> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		String hql = "from "+Associazioni.class.getName() +" group by esperto, delibera";
		Query queryObject = session.createQuery(hql);
		Set<Associazioni> listaAssociazioni = new HashSet<Associazioni>(queryObject.list());
		t.commit();
		session.close();
		return listaAssociazioni;
	}
	
	

	public Integer save(Associazioni element) throws Exception {
		log.debug("save Delibera instance");
		Integer id = null;
		 Transaction t = null;
		 Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
	        t = session.beginTransaction();
	        
	        IDeliberaDAO deliberaDAO = new DeliberaDAO();
			Delibera deliberaDB = deliberaDAO.findById(element.getDelibera().getId());
			
			IEspertiDAO espertiDAO = new EspertiDAO();
			Esperto espertoDB =  espertiDAO.findById(element.getEsperto().getId());
			
			ITemiSpecializzazioniDAO temiDAO = new TemiSpecializzazioniDAO();
			Tema temaDB =  temiDAO.findById(element.getTema().getId());
			
			element.setDelibera(deliberaDB);
			element.setEsperto(espertoDB);
			element.setTema(temaDB);
			
	        id = (Integer)session.save(element);
	        
				
		} finally {
			t.commit();
			
			session.close();	
		}
		return id;
	}
	
	
	
	@Override
	public void delete(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Associazioni e = (Associazioni) session.load(Associazioni.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}
	
	/** Merge con commit */
	@Override
	public void merge(Associazioni element, Session session) throws Exception {
	    @SuppressWarnings("unused")
	    Associazioni i2 = (Associazioni) session.get(Associazioni.class, new Integer(element.getId()));
		i2 = (Associazioni) session.merge(element); 
	}

	@Override
	public int delete(int idEsperto, int idDelibera) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}



}
