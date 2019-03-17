package  it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1



import java.util.HashSet;

import java.util.Set;

import it.ccse.bandiEsperti.business.model.Citta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.hibernate.HibernateUtil;

/**
 * Home object for domain model class Citta.
 * @see it.ccse.bandiEsperti.business.dao.Citta
 * @author Hibernate Tools
 */

public class CittaDAO extends BaseDAO implements ICittaDAO{

	private static final Log log = LogFactory.getLog(CittaDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#persist(it.ccse.bandiEsperti.business.model.Citta)
	 */
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICittaDAO1#persist(it.ccse.bandiEsperti.business.model.Citta)
	 */
	
	@Override
	public void persist(Citta transientInstance) {
		log.debug("persisting Citta instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#remove(it.ccse.bandiEsperti.business.model.Citta)
	 */
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICittaDAO1#remove(it.ccse.bandiEsperti.business.model.Citta)
	 */
	
	@Override
	public void remove(Citta persistentInstance) {
		log.debug("removing Citta instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#merge(it.ccse.bandiEsperti.business.model.Citta)
	 */
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICittaDAO1#merge(it.ccse.bandiEsperti.business.model.Citta)
	 */
	
	@Override
	public Citta merge(Citta detachedInstance) {
		log.debug("merging Citta instance");
		try {
			Citta result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findById(java.lang.Integer)
	 */
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICittaDAO1#findById(java.lang.Integer)
	 */
	
	
	@Override
	public Citta findById(Integer id) {
		log.debug("getting Citta instance with id: " + id);
		try {
			Citta instance = (Citta)HibernateUtil.getSessionFactory().openSession().get(Citta.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findByCodiceRegione(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICittaDAO1#findByCodiceRegione(java.lang.String)
	 */

	@Override
	@SuppressWarnings("unchecked")
	public Set<Citta> findByCodiceRegione(String codiceRegione) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Citta.class);
		criteria.add(Restrictions.eq("codiceRegione", codiceRegione));
		Set<Citta> listaCitta =new HashSet<Citta>(criteria.list());
		t.commit();
		session.close();
		return listaCitta;
	}
	
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findByCodiceProvincia(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICittaDAO1#findByCodiceProvincia(java.lang.String)
	 */
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Citta> findByCodiceProvincia(String codiceProvincia) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Citta.class);
		criteria.createAlias("provincie", "prov").add(Restrictions.eq("prov.codiceProvincia", codiceProvincia));
		Set<Citta> listaCitta =new HashSet<Citta>(criteria.list());
		t.commit();
		session.close();
		return listaCitta;
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Citta> findByCodiceCittaMetropolitana(String codiceCittaMetropolitana) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Citta.class);
		criteria.add(Restrictions.eq("codiceCittaMetropolitana", codiceCittaMetropolitana));
		Set<Citta> listaCitta =new HashSet<Citta>(criteria.list());
		t.commit();
		session.close();
		return listaCitta;
	}
	
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findAll()
	 */
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICittaDAO1#findAll()
	 */
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Citta> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Citta.class);
		Set<Citta> listaCitta =new HashSet<Citta>(criteria.list());
		t.commit();
		session.close();
		return listaCitta;
	}
	
	
	


	



}
