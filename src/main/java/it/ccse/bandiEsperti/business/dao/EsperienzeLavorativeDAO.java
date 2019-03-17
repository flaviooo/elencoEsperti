package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO.TipoFiltro;
import it.ccse.bandiEsperti.business.model.DatoreDiLavoro;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;





import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.hibernate.HibernateUtil;

public class EsperienzeLavorativeDAO implements IEsperienzeLavorativeDAO, IAdministrationDAO {

	private static final Log log = LogFactory.getLog(EsperienzeLavorativeDAO.class);
	/**
	 * Nel metodo di salvataggio non viene invocato il "commit"; il "commit"
	 * viene richiamato nella fase conclusiva del processo di salvataggio, a
	 * fronte del salvataggio dell'esperto
	 * */
	@Override
	public Integer save(EsperienzaLavorativa esperienza) throws Exception {
		log.debug("save EsperienzaLavorativa instance");
		Integer id = null;
		 Transaction t = null;
		 Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
	        t = session.beginTransaction();
	        id = (Integer)session.save(esperienza);
				
		} finally {
			t.commit();
			session.close();	
		}
		return id;	
	}

	/** Merge con commit */
	@Override
	public void merge(EsperienzaLavorativa element, Session session) throws Exception {
	    @SuppressWarnings("unused")
//	    EsperienzaLavorativa i2 = (EsperienzaLavorativa) currentSession().get(EsperienzaLavorativa.class, new Integer(element.getId()));
//	    i2 = (EsperienzaLavorativa)currentSession().merge(element); //Lo stato dell’oggetto viene variato con le proprietà modificate
	    EsperienzaLavorativa i2 = (EsperienzaLavorativa) session.get(EsperienzaLavorativa.class, new Integer(element.getId()));
		i2 = (EsperienzaLavorativa) session.merge(element); 
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
	
//	@SuppressWarnings("unchecked")
//	public List<EsperienzaLavorativa> findAll() throws Exception {
//		Criteria criteria = currentSession().createCriteria(EsperienzaLavorativa.class).addOrder(Order.desc("data"));
//		return criteria.list();
//	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EsperienzaLavorativa> findAll() throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(EsperienzaLavorativa.class).addOrder(Order.desc("data"));
		List<EsperienzaLavorativa> list = criteria.list();
		t.commit();
		session.close();

		return list;
	}	
	
//	public EsperienzaLavorativa findById(int id) {
//		return (EsperienzaLavorativa) currentSession().load(EsperienzaLavorativa.class, id);
//	}
	
	@Override
	public EsperienzaLavorativa findById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		EsperienzaLavorativa element = (EsperienzaLavorativa) session.load(EsperienzaLavorativa.class, id);
		session.close();
		return element;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public EsperienzaLavorativa findCarrieraPrincipaleByIdEsperto(Integer idEsperto) throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(EsperienzaLavorativa.class).add(Restrictions.eq("id_esperti", idEsperto))
																			  .add( Restrictions.eq("flagCarriera", true))
																			  .add( Restrictions.eq("flagPrincipale", true));
		EsperienzaLavorativa esperienzaLavorativa = (EsperienzaLavorativa)criteria.uniqueResult();
		t.commit();
		session.close();
		return esperienzaLavorativa;
	}

	@Override
	public List<EsperienzaLavorativa> findCarriereByIdEsperto(Integer idEsperto) throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(EsperienzaLavorativa.class).add(Restrictions.eq("id_esperti", idEsperto)).add( Restrictions.eq("flagCarriera", true));
		criteria.addOrder(Order.asc("dataInizio"));
		List<EsperienzaLavorativa> list = criteria.list();
		t.commit();
		session.close();
		return list;
	}
	@Override
	public List<EsperienzaLavorativa> findEsperienzeLavorativeByIdEsperto(Integer idEsperto) throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(EsperienzaLavorativa.class).add(Restrictions.eq("id_esperti", idEsperto)).add( Restrictions.eq("flagCarriera", false));
		criteria.addOrder(Order.asc("dataInizio"));
		List<EsperienzaLavorativa> list = criteria.list();
		t.commit();
		session.close();
		return list;
	}
	
	@Override
	public boolean checkIfExist(Serializable datoreDiLavoro) throws Exception {
		boolean res=false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(EsperienzaLavorativa.class).add( Restrictions.eq("datoreDiLavoro", (DatoreDiLavoro)datoreDiLavoro));
		criteria.addOrder(Order.asc("dataInizio"));
		List<EsperienzaLavorativa> list = criteria.list();
		t.commit();
		session.close();
		if(list.size()>0){
			res=true;
		}
		return res;
	}

	
	
	@Override
	public List<EsperienzaLavorativa> findByIdEsperto(Integer idEsperto, FiltroRicercaEspertiDTO filtroRicerca) throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();

		// System.out.println("STATISTICHE CACHE:" +
		// HibernateUtil.getSessionFactory().getStatistics().toString());
		Transaction t = session.beginTransaction();

		Criteria criteria = session.createCriteria(EsperienzaLavorativa.class).add(Restrictions.eq("id_esperti", idEsperto));
//		criteria.addOrder(Order.desc("data"));

		if(filtroRicerca != null){			
			/*
			 * Esperienze
			 */
			
			if(filtroRicerca.getEsperienze().get(TipoFiltro.FILTRADETTAGLIESPERIENZE) != null && (Boolean)filtroRicerca.getEsperienze().get(TipoFiltro.FILTRADETTAGLIESPERIENZE) == true){
				//In corso
				Criterion critInCorso = null;
				if(filtroRicerca.getEsperienze().get(TipoFiltro.INCORSO) != null && (Boolean)filtroRicerca.getEsperienze().get(TipoFiltro.INCORSO) == true){
					critInCorso = Restrictions.eq("inCorso", true);
				}
	
				//a partire da
				Disjunction disjAPartireDa = null;
				if(filtroRicerca.getEsperienze().get(TipoFiltro.APARTIREDA) != null){
					//critAPartireDa = Restrictions.ge("dataInizio", (Date)filtroRicerca.getEsperienze().get(TipoFiltro.APARTIREDA));
					Criterion critAPartireDaInf = Restrictions.le("dataInizio", (Date)filtroRicerca.getEsperienze().get(TipoFiltro.APARTIREDA));
					Criterion critAPartireDaSup = Restrictions.ge("dataFine", (Date)filtroRicerca.getEsperienze().get(TipoFiltro.APARTIREDA));
					Conjunction conjBetween = Restrictions.conjunction();
					conjBetween.add(critAPartireDaInf).add(critAPartireDaSup);
					
					Criterion critAPartireDaInSu = Restrictions.ge("dataInizio", (Date)filtroRicerca.getEsperienze().get(TipoFiltro.APARTIREDA));
					
					Disjunction disjDate = Restrictions.disjunction();				
					disjDate.add(conjBetween).add(critAPartireDaInSu);
					
					Criterion critAPartireDaInCorso = Restrictions.eq("inCorso", true);
					
					disjAPartireDa = Restrictions.disjunction();
					disjAPartireDa.add(disjDate).add(critAPartireDaInCorso);				
				}
			
				//Datore di Lavoro In
				Criterion critEsperienzeLavorative = null;
				if(filtroRicerca.getEsperienze().get(TipoFiltro.DATORELAVORO) != null){
					String sqlRestriction = "";
					boolean firstIteration = true;
					
					for(String datore : (List<String>)filtroRicerca.getEsperienze().get(TipoFiltro.DATORELAVORO)){
						if (firstIteration){
							firstIteration = false;
						}else{
							sqlRestriction = sqlRestriction + " or ";
						}
						
						if (datore.equals("RSE")){
							sqlRestriction = sqlRestriction + " datore_lavoro like ('%"+datore+"%') collate utf8_bin ";
						}else{
							sqlRestriction = sqlRestriction + " datore_lavoro like ('%"+datore+"%')";
						}
					}
					sqlRestriction = sqlRestriction.trim();
					sqlRestriction = "("+sqlRestriction+")";
					
					if(sqlRestriction != null && sqlRestriction != ""){
						critEsperienzeLavorative = Restrictions.sqlRestriction(sqlRestriction);
					}
				}
				
				if(critInCorso != null && disjAPartireDa != null && critEsperienzeLavorative != null){
					criteria.add(critInCorso).add(disjAPartireDa).add(critEsperienzeLavorative);
				}else if(critInCorso != null && disjAPartireDa != null){
					criteria.add(critInCorso).add(disjAPartireDa);
				}else if(critInCorso != null && critEsperienzeLavorative != null){
					criteria.add(critInCorso).add(critEsperienzeLavorative);
				}else if(disjAPartireDa != null && critEsperienzeLavorative != null){
					criteria.add(disjAPartireDa).add(critEsperienzeLavorative);
				}else if(critInCorso != null){
					criteria.add(critInCorso);
				}else if(disjAPartireDa != null){
					criteria.add(disjAPartireDa);
				}else if(critEsperienzeLavorative != null){
					criteria.add(critEsperienzeLavorative);
				}
			}
		}
		criteria.addOrder(Order.asc("dataInizio"));
		List<EsperienzaLavorativa> list = criteria.list();

		//System.out.println(" (ISTRUZIONE) NUMERO DI RECORD: " + list.size());

		t.commit();
		session.close();
		return list;
	}
	
	
//	public void delete(EsperienzaLavorativa e) {
//		currentSession().delete(e);
//		currentSession().getTransaction().commit();
//	}
//
//	public void delete(int id) {
//		EsperienzaLavorativa e = (EsperienzaLavorativa) currentSession().load(EsperienzaLavorativa.class, id);
//		currentSession().delete(e);
//		currentSession().getTransaction().commit();
//	}	
	
	@Override
	public void delete(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		EsperienzaLavorativa e = (EsperienzaLavorativa) session.load(EsperienzaLavorativa.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}

	
}