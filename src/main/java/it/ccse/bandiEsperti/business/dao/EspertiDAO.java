package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO.TipoFiltro;
import it.ccse.bandiEsperti.business.model.AllegatoPubblEsperto;
import it.ccse.bandiEsperti.business.model.CartaIdentitaEsperto;
import it.ccse.bandiEsperti.business.model.Competenza;
import it.ccse.bandiEsperti.business.model.DocumentoAllegatoEsperto;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.InfoEspertoApprovato;
import it.ccse.bandiEsperti.business.model.Specializzazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.zkoss.zkplus.hibernate.HibernateUtil;

public class EspertiDAO extends BaseDAO implements IEspertiDAO {
	
	private static final Log log = LogFactory.getLog(EspertiDAO.class);
	
	@Override
	public Integer save(Esperto element) throws Exception {
		log.debug("save Competenza instance");
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

	
	@Override
	public void save(Esperto element, Session session) throws Exception { 
        Transaction t = session.beginTransaction();
		session.get(Esperto.class, new Integer(element.getId()));
		session.save(element);
		t.commit();
	}	
	
	/** Merge con commit */
	@Override
	public void merge(Esperto element, Session session) throws Exception {
		Esperto i2 = (Esperto) session.get(Esperto.class, new Integer(element.getId()));
		i2 = (Esperto) session.merge(element); // Lo stato dell’oggetto
														// viene variato con le
														// proprietà modificate		
	}
	


	@Override
	public Esperto findById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Esperto.class);
		criteria.add(Restrictions.eq("id", id));
		Esperto esperto = (Esperto) criteria.uniqueResult();
		t.commit();
		session.close();
		return esperto;
	}
	
	@Override
	public Esperto findById(int id, Session session) {
		Criteria criteria = session.createCriteria(Esperto.class);
		criteria.add(Restrictions.eq("id", id));
		Esperto esperto = (Esperto) criteria.uniqueResult();
		return esperto;
	}	
	
	@Override
	public Esperto findByUsername(String username) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Esperto.class);
		criteria.add(Restrictions.eq("username", username));
		Esperto e = (Esperto) criteria.uniqueResult();
		t.commit();
		session.close();
		return e;
	}
	
	@Override
	public Esperto findByEmail(String email) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Esperto.class);
		criteria.add(Restrictions.eq("email", email));
		Esperto e = (Esperto) criteria.uniqueResult();
		t.commit();
		session.close();
		return e;
	}
	
	@Override
	public Esperto findByCodFisc(String codFisc) throws Exception
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Esperto.class);
		criteria.add(Restrictions.eq("cf", codFisc).ignoreCase());
		Esperto e = (Esperto) criteria.uniqueResult();
		t.commit();
		session.close();
		return e;
	}
	
	@Override
	public Esperto findByUsernameAndPassword(String username, String password) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Esperto.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));
		Esperto e = (Esperto) criteria.uniqueResult();
		t.commit();
		session.close();
		return e;
	}
	
	@Override
	public Esperto findByCodFiscAndPassword(String codFisc, String password) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Esperto.class);
		criteria.add(Restrictions.eq("cf", codFisc));
		criteria.add(Restrictions.eq("password", password));
		Esperto e = (Esperto) criteria.uniqueResult();
		t.commit();
		session.close();
		return e;
	}
	
	@Override
	public void delete(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		
		Esperto e = (Esperto) session.load(Esperto.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}		
	
	// ********************************************************************************************
	
	@Override
	public Set<Esperto> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Esperto.class);

		// TODO DA ESCLUDERE
		//criteria.add(Restrictions.not(Restrictions.eq("admin", 1)));
		Set<Esperto> listaEsperti=new HashSet<Esperto>(criteria.list());
		
		
		t.commit();
		session.close();
		return listaEsperti;
	}
	
	/**
	 * Map<String, Boolean> temi : la chiave della Map è idTema
	 * */
	@Override
	public List<Esperto> findByCompetenzeANDAnagrafica(List<Integer> spec2, String cognome, Date dataNascita) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		
		// STEP 1: RECUPERO DI TUTTE LE COMPETENZE DATE LE SPECIALIZZAZIONI
		Criteria criteriaCompetenze = session.createCriteria(Competenza.class);
		Iterator<Integer> it = spec2.iterator();
		criteriaCompetenze.createCriteria("specializzazione", "specializzazione", Criteria.LEFT_JOIN);
		while (it.hasNext()) {
			criteriaCompetenze.add(Restrictions.eq("specializzazione.id", it.next()));
		}
		List<Competenza> lCompetenza = criteriaCompetenze.list();
		
		
		// STEP 2: DEFINIZIONE DEI CRITERIA ANAGRAFICI		
		Criteria criteria = session.createCriteria(Esperto.class);
		criteria.add(Restrictions.eq("cognome", cognome));
		criteria.add(Restrictions.eq("dataNascita", dataNascita));

		// STEP 3: AGGIUNGO AI CRITERIA ANAGRAFICI I CRITERIA DELLE COMPETENZE		
		Iterator<Competenza> itCompetenzePerEsperto = lCompetenza.iterator();
		while (itCompetenzePerEsperto.hasNext()) {
			criteria.add(Restrictions.eq("idCompetenza", it.next()));
		}
		
//		// STEP 4: AGGIUNGO AI CRITERIA ANAGRAFICI IL CRITERIO SULL'ESISTENZA DI PUBBLICAZIONI		
//		Iterator itCompetenzePerEsperto = lCompetenza.iterator();
//		while (itCompetenzePerEsperto.hasNext()) {
//			criteria.add(Restrictions.eq("idCompetenza", it.next()));
//		}
//		
				
		List<Esperto> lEsperto = criteria.list();
		
		t.commit();
		session.close();
		return lEsperto;
	}	
	
	/*
	 * funzione che effettua ricerche di esperti su base Criteri
	 */
	@Override
	public List<Esperto> searchEsperti(FiltroRicercaEspertiDTO filtroRicerca, boolean approvato){
		List<Esperto> listaEsperti = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		
		try{
			Criteria criteria = session.createCriteria(Esperto.class, "esp").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			if(filtroRicerca!=null && approvato == true){
				/*
				 * Precedenti Incarichi
				 */
				Criterion incarichiRds = null;
				if(filtroRicerca.getPrecedentiIncarichi().get(TipoFiltro.INCARICHIPRESENTIRDS) != null && (Boolean)filtroRicerca.getPrecedentiIncarichi().get(TipoFiltro.INCARICHIPRESENTIRDS) == true){
					incarichiRds = Restrictions.eq("rds", true);
				}
				
				Disjunction disjAltriIncarichi = null;
				if(filtroRicerca.getPrecedentiIncarichi().get(TipoFiltro.INCARICHIPRESENTIALTRO) != null && (Boolean)filtroRicerca.getPrecedentiIncarichi().get(TipoFiltro.INCARICHIPRESENTIALTRO) == true){
					Criterion internazionale = Restrictions.eq("internazionale", true);
			        Criterion miur = Restrictions.eq("miur", true);
			        Criterion europeo = Restrictions.eq("europeo", true);
			        
			        disjAltriIncarichi = Restrictions.disjunction();
			        disjAltriIncarichi.add(internazionale).add(miur).add(europeo);
				}
				
				if(incarichiRds != null && disjAltriIncarichi != null){
					criteria.createCriteria("precedentiIncarichi").add(incarichiRds).add(disjAltriIncarichi);
				}else if(incarichiRds != null){
					criteria.createCriteria("precedentiIncarichi").add(incarichiRds);
				}else if(disjAltriIncarichi != null){
					criteria.createCriteria("precedentiIncarichi").add(disjAltriIncarichi);
				}
							
				/*
				 * Pubblicazioni
				 */
				if(filtroRicerca.getPubblicazioni().get(TipoFiltro.PUBBLICAZIONIPRESENTI) != null && (Boolean)filtroRicerca.getPubblicazioni().get(TipoFiltro.PUBBLICAZIONIPRESENTI) == true){
					criteria.createCriteria("pubblicazioni").add(Restrictions.isNotNull("id"));
				}
				
				/*
				 *  Dati Anagrafici
				 */
				//Case Inseisitive
				if(filtroRicerca.getDatiAnagrafici().get(TipoFiltro.COGNOME) != null){
					criteria.add(Restrictions.like("cognome", filtroRicerca.getDatiAnagrafici().get(TipoFiltro.COGNOME)+"%"));
				}
				
				if(filtroRicerca.getDatiAnagrafici().get(TipoFiltro.DATALIMITE) != null){
					criteria.add(Restrictions.ge("dataNascita", (Date)filtroRicerca.getDatiAnagrafici().get(TipoFiltro.DATALIMITE)));
				}
				
				/*
				 * Esperienze
				 */
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
					criteria.createCriteria("esperienzeLavorative").add(critInCorso).add(disjAPartireDa).add(critEsperienzeLavorative);
				}else if(critInCorso != null && disjAPartireDa != null){
					criteria.createCriteria("esperienzeLavorative").add(critInCorso).add(disjAPartireDa);
				}else if(critInCorso != null && critEsperienzeLavorative != null){
					criteria.createCriteria("esperienzeLavorative").add(critInCorso).add(critEsperienzeLavorative);
				}else if(disjAPartireDa != null && critEsperienzeLavorative != null){
					criteria.createCriteria("esperienzeLavorative").add(disjAPartireDa).add(critEsperienzeLavorative);
				}else if(critInCorso != null){
					criteria.createCriteria("esperienzeLavorative").add(critInCorso);
				}else if(disjAPartireDa != null){
					criteria.createCriteria("esperienzeLavorative").add(disjAPartireDa);
				}else if(critEsperienzeLavorative != null){
					criteria.createCriteria("esperienzeLavorative").add(critEsperienzeLavorative);
				}
				
				/*
				 * Competenza
				 */
				
				
				if(filtroRicerca.getCompetenze().get(TipoFiltro.LISTACOMPETENZE) != null){
					List<Specializzazione> specializzazioni = (List<Specializzazione>)filtroRicerca.getCompetenze().get(TipoFiltro.LISTACOMPETENZE);
					//Retrò like code style
					Integer[] specializzazioniId = new Integer[specializzazioni.size()];
					
					for(int i=0; i<specializzazioni.size(); i++){
						Specializzazione specializzazione = specializzazioni.get(i);
						specializzazioniId[i] = new Integer(specializzazione.getId());
					}
					
					/*
					 * Modifica del 23-04-2013
					 * La seguente condizione non va bene in quanto non considera
					 * effettivamente gli esperti che hanno tutte le competenze ricercate
					 */
					criteria.createCriteria("competenze").
					createCriteria("specializzazione").
					add(Restrictions.in("id", specializzazioniId));
					
					//
					/*
					Integer listaIdEsperti[] = searchEspertiBySpec(specializzazioniId, session);
					if (listaIdEsperti.length > 0)
						criteria.add(Restrictions.in("id", listaIdEsperti));
					else
						return new ArrayList<Esperto>();
						*/
							
				}
			}
			
			
			criteria.add(Restrictions.eq("admin", false));
			
			if (approvato == true)
				criteria.add(Restrictions.eq("stato", 1));
			else
			{
				criteria.add(Restrictions.ne("stato", 1));
				//criteria.add(Restrictions.eq("inviato", true));
				criteria.add(Restrictions.isNotNull("dataInvio"));
			}
			
			criteria.addOrder(Order.asc("cognome"));
			
			
			listaEsperti = criteria.list();
		} catch (Exception ex){
			ex.printStackTrace();
		} finally {
			t.commit();
			session.close();
		}
		
		return listaEsperti;
	}
	
	
	@Override
	public boolean aggiornaEsperto(int idEsperto, String email, String tel)
			throws Exception {
		Session session =null;
		 Transaction t=null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
	        t = session.beginTransaction();
			
	        Esperto esp = findById(idEsperto, session);
	        esp.setEmail(email);
	        esp.setTel(tel);
	        session.merge(esp);
	        t.commit();
			session.close();	
			
		}finally{
			if(t!=null){
				t.commit();
			}
			if(session!=null){
				session.close();
			}
		}
		return true;
		
	}

	@Override
	public void aggiornaDettaglio(int idEsperto, InfoEspertoApprovato dettaglio)
			throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
		
        Esperto esp = findById(idEsperto, session);
        dettaglio.setEsperto(esp);
        dettaglio.setIdEsperto(idEsperto);
        
        if (esp.getDettaglio() == null)
        	session.save(dettaglio);
        else
        	session.merge(dettaglio);
        
		t.commit();
		session.close();	
		
	}
	
	@Override
	public void inserisciAllegato(Esperto esperto, AllegatoPubblEsperto allegato) throws Exception
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
		
        allegato.setEsperto(esperto);
        allegato.setIdEsperto(esperto.getId());
       // if (esperto.getAllegato() == null)
        	session.save(allegato);
       // else
       // 	session.merge(allegato);
        
        esperto.setAllegato(allegato);
        session.merge(esperto);
        
		t.commit();
		session.close();	
	}
	
	
	@Override
	public void inserisciAllegato(Esperto esperto, DocumentoAllegatoEsperto allegato) throws Exception
	{
		Session session = null;
		Transaction t = null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
	        t = session.beginTransaction();
			
	        allegato.setEsperto(esperto);
	        allegato.setIdEsperto(esperto.getId());
	        session.save(allegato);
	        esperto.setDocumentoAllegato(allegato);
	        session.merge(esperto);
		}finally{
			if(t!=null)
				t.commit();
			if(session!=null)
				session.close();	
		}
		
	}
	
	
	@Override
	public void inserisciCartaIdentita(Esperto esperto, CartaIdentitaEsperto allegato) throws Exception
	{
		Session session = null;
		Transaction t = null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
	        t = session.beginTransaction();
			
	        allegato.setEsperto(esperto);
	        allegato.setIdEsperto(esperto.getId());
	        session.save(allegato);
	        esperto.setCartaIdentita(allegato);
	        session.merge(esperto);
		}finally{
			if(t!=null)
				t.commit();
			if(session!=null)
				session.close();	
		}
		
	}
	
	
	@Override
	public void eliminaCartaIdentita(Esperto esperto) throws Exception
	{
		Session session = null;
		Transaction t = null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
	         t = session.beginTransaction();
	        session.delete(esperto.getCartaIdentita());
	        session.merge(esperto);
       
		}finally{
			if(t!=null)
				t.commit();
			if(session!=null)
				session.close();	
		}
	}
	
	@Override
	public void eliminaAllegato(Esperto esperto) throws Exception
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(esperto.getAllegato());
        session.merge(esperto);
        t.commit();
        session.close();
	}
	
	
	@Override
	public void eliminaDocumentoAllegato(Esperto esperto) throws Exception
	{
		Session session = null;
		Transaction t = null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
	         t = session.beginTransaction();
	        session.delete(esperto.getDocumentoAllegato());
	        session.merge(esperto);
       
		}finally{
			if(t!=null)
				t.commit();
			if(session!=null)
				session.close();	
		}
	}
	
	

	@Override
	public void salvaCompetenzaEsperto(int idEsperto,String competenza)
			throws Exception {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Esperto esp = findById(idEsperto, session);
		esp.setCompetenza(competenza);
		session.merge(esp);
		t.commit();
		session.close();
		
	}
	
	@Override
	public void salvaPresentazioneEsperto(int idEsperto,String presentazione)
			throws Exception {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Esperto esp = findById(idEsperto, session);
		esp.setPresentazione(presentazione);
		session.merge(esp);
		t.commit();
		session.close();
		
	}
	
	@Override
	public void modificaStatoEsperto(int idEsperto, int stato, boolean bloccato)
			throws Exception {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Esperto esp = findById(idEsperto, session);
		esp.setStato(stato);
		esp.setInviato(bloccato);
		session.merge(esp);
		t.commit();
		session.close();
		
	}
	
	private Integer [] searchEspertiBySpec(Integer idSpecializzazioni[], Session session) throws Exception {
		
		SearchEspertiSpec specW = new SearchEspertiSpec(idSpecializzazioni); 
		session.doWork(specW);
		Integer idEsperti[] = specW.getIdEsperti();
		return idEsperti;
		
	}
	
	private class SearchEspertiSpec implements Work {

		private Integer [] idSpecializzazioni;
		private List<Integer> lstEsperti = new ArrayList<Integer>();
		public SearchEspertiSpec(Integer []idSpec) {
			this.idSpecializzazioni = idSpec;
		}
		
		@Override
		public void execute(Connection conn) throws SQLException {
			
			String tmpSpec = "";
			for (Integer rr: idSpecializzazioni)
			{
				if (!tmpSpec.equals(""))
					tmpSpec = tmpSpec+", ";
				tmpSpec = tmpSpec+rr;
			}
			
			String query = "SELECT id_esperti, count(*) as numSpec FROM competenze "+
					"WHERE id_specializzazioni IN ("+tmpSpec+") GROUP BY id_esperti " +
					"having numSpec = ?";
			PreparedStatement stat = conn.prepareStatement(query);
			stat.setInt(1, idSpecializzazioni.length);
			ResultSet rs = stat.executeQuery();
			int idEspTmp;
			while (rs.next())
			{
				idEspTmp = rs.getInt(1);
				lstEsperti.add(idEspTmp);
			}
			
			
		}
		
		public Integer[] getIdEsperti()
		{
			return lstEsperti.toArray(new Integer[]{});
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findEspertoByCustomSql(String sql) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createSQLQuery(sql);
			return query.list();
			
		} finally {
			session.close();
		}
		
	}
	
	
}