package it.ccse.bandiEsperti.business.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.zkplus.hibernate.HibernateUtil;

import it.ccse.bandiEsperti.business.dao.AssociazioniDAO;
import it.ccse.bandiEsperti.business.dao.AteneoDAO;
import it.ccse.bandiEsperti.business.dao.CampiRicercaDAO;
import it.ccse.bandiEsperti.business.dao.CittaDAO;
import it.ccse.bandiEsperti.business.dao.CompetenzeDAO;
import it.ccse.bandiEsperti.business.dao.CondizioniRicercaDAO;
import it.ccse.bandiEsperti.business.dao.DatoreDiLavoroDAO;
import it.ccse.bandiEsperti.business.dao.DeliberaDAO;
import it.ccse.bandiEsperti.business.dao.EsperienzeLavorativeDAO;
import it.ccse.bandiEsperti.business.dao.EspertiDAO;
import it.ccse.bandiEsperti.business.dao.IAdministrationDAO;
import it.ccse.bandiEsperti.business.dao.IAssociazioniDAO;
import it.ccse.bandiEsperti.business.dao.IAteneoDAO;
import it.ccse.bandiEsperti.business.dao.ICampiRicercaDAO;
import it.ccse.bandiEsperti.business.dao.ICittaDAO;
import it.ccse.bandiEsperti.business.dao.ICompetenzeDAO;
import it.ccse.bandiEsperti.business.dao.ICondizioniRicercaDAO;
import it.ccse.bandiEsperti.business.dao.IDatoreDiLavoroDAO;
import it.ccse.bandiEsperti.business.dao.IDeliberaDAO;
import it.ccse.bandiEsperti.business.dao.IEsperienzeLavorativeDAO;
import it.ccse.bandiEsperti.business.dao.IEspertiDAO;
import it.ccse.bandiEsperti.business.dao.IIstruzioneDAO;
import it.ccse.bandiEsperti.business.dao.IPaesiDAO;
import it.ccse.bandiEsperti.business.dao.IPrecedentiIncarichiDAO;
import it.ccse.bandiEsperti.business.dao.IProfessioneDAO;
import it.ccse.bandiEsperti.business.dao.IProvincieDAO;
import it.ccse.bandiEsperti.business.dao.IPubblicazioniDAO;
import it.ccse.bandiEsperti.business.dao.IRegioniDAO;
import it.ccse.bandiEsperti.business.dao.ISpecializzazioniDAO;
import it.ccse.bandiEsperti.business.dao.ITemiSpecializzazioniDAO;
import it.ccse.bandiEsperti.business.dao.ITipoLaureaDAO;
import it.ccse.bandiEsperti.business.dao.ITipoProfessioneDAO;
import it.ccse.bandiEsperti.business.dao.ITitoloLaureaDAO;
import it.ccse.bandiEsperti.business.dao.IstruzioneDAO;
import it.ccse.bandiEsperti.business.dao.PaesiDAO;
import it.ccse.bandiEsperti.business.dao.PrecedentiIncarichiDAO;
import it.ccse.bandiEsperti.business.dao.ProfessioneDAO;
import it.ccse.bandiEsperti.business.dao.ProvincieDAO;
import it.ccse.bandiEsperti.business.dao.PubblicazioniDAO;
import it.ccse.bandiEsperti.business.dao.RegioniDAO;
import it.ccse.bandiEsperti.business.dao.SpecializzazioniDAO;
import it.ccse.bandiEsperti.business.dao.TemiSpecializzazioniDAO;
import it.ccse.bandiEsperti.business.dao.TipoLaureaDAO;
import it.ccse.bandiEsperti.business.dao.TipoProfessioneDAO;
import it.ccse.bandiEsperti.business.dao.TitoloLaureaDAO;
import it.ccse.bandiEsperti.business.dto.FiltriRicercaDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.AllegatoPubblEsperto;
import it.ccse.bandiEsperti.business.model.Associazioni;
import it.ccse.bandiEsperti.business.model.Atenei;
import it.ccse.bandiEsperti.business.model.CampiRicerca;
import it.ccse.bandiEsperti.business.model.CartaIdentitaEsperto;
import it.ccse.bandiEsperti.business.model.Citta;
import it.ccse.bandiEsperti.business.model.Competenza;
import it.ccse.bandiEsperti.business.model.CondizioniRicerca;
import it.ccse.bandiEsperti.business.model.DatoreDiLavoro;
import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.model.DocumentoAllegatoEsperto;
import it.ccse.bandiEsperti.business.model.EsperienzaLavorativa;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.InfoEspertoApprovato;
import it.ccse.bandiEsperti.business.model.Istruzione;
import it.ccse.bandiEsperti.business.model.Paesi;
import it.ccse.bandiEsperti.business.model.PrecedenteIncarico;
import it.ccse.bandiEsperti.business.model.Professione;
import it.ccse.bandiEsperti.business.model.Provincie;
import it.ccse.bandiEsperti.business.model.Pubblicazione;
import it.ccse.bandiEsperti.business.model.Regioni;
import it.ccse.bandiEsperti.business.model.Specializzazione;
import it.ccse.bandiEsperti.business.model.Tema;
import it.ccse.bandiEsperti.business.model.TipoLaurea;
import it.ccse.bandiEsperti.business.model.TipoProfessione;
import it.ccse.bandiEsperti.business.model.TitoloLaurea;
import it.ccse.bandiEsperti.utils.CCSEUtils;

public class EspertiService implements IEspertiService {
	
	@Override
	public boolean registrazione(Esperto e) {
		boolean res = false;
		IEspertiDAO eDAO = new EspertiDAO();
		try {
			eDAO.save(e);
			res = true;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		return res;
	}

	@Override
	public Esperto login(String codFisc, String password) throws Exception{
		IEspertiDAO eDAO = new EspertiDAO();
		Esperto e = new Esperto();
		e = eDAO.findByCodFiscAndPassword(codFisc, password);
		return e;
	}
	
	@Override
	public Set<Paesi> listaPaesi() throws Exception{
		IPaesiDAO eDAO = new PaesiDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<Regioni> listaRegioni() throws Exception{
		IRegioniDAO eDAO = new RegioniDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<Regioni> listaRegioni(Paesi paesi) throws Exception{
		IRegioniDAO eDAO = new RegioniDAO();
		return eDAO.listaRegione(paesi);
	}
	
	@Override
	public Set<Specializzazione> listaSpecializzazioni() throws Exception{
		ISpecializzazioniDAO eDAO = new SpecializzazioniDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<Esperto> listaEsperti() throws Exception{
		IEspertiDAO eDAO = new EspertiDAO();
		return eDAO.findAll();
	}
	
	
	@Override
	public Set<Provincie> listaProvincie() throws Exception{
		IProvincieDAO eDAO = new ProvincieDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<Citta> listaCitta() throws Exception{
		ICittaDAO eDAO = new CittaDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<Atenei> listaAtenei() throws Exception{
		IAteneoDAO eDAO = new AteneoDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<TipoLaurea> listaTipoLaurea() throws Exception{
		ITipoLaureaDAO eDAO = new TipoLaureaDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<TipoProfessione> listaTipoProfessione() throws Exception{
		ITipoProfessioneDAO eDAO = new TipoProfessioneDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<Delibera> listaDelibera() throws Exception{
		IDeliberaDAO eDAO = new DeliberaDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<Professione> listaProfessione() throws Exception{
		IProfessioneDAO eDAO = new ProfessioneDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<Associazioni> listaAssociazioni() throws Exception{
		IAssociazioniDAO eDAO = new AssociazioniDAO();
		return eDAO.findAll();
	}
	
	
	@Override
	public Set<CampiRicerca> listaCampiRicerca() throws Exception{
		ICampiRicercaDAO eDAO = new CampiRicercaDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<CondizioniRicerca> listaCondizioniRicerca() throws Exception{
		ICondizioniRicercaDAO eDAO = new CondizioniRicercaDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<DatoreDiLavoro> listaDatoreDiLavoro() throws Exception{
		IDatoreDiLavoroDAO eDAO = new DatoreDiLavoroDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<TitoloLaurea> listaTitoloLaurea() throws Exception{
		ITitoloLaureaDAO eDAO = new TitoloLaureaDAO();
		return eDAO.findAll();
	}
	
	@Override
	public Set<TitoloLaurea> listaTitoloLaureaByTipoLaurea(TipoLaurea tipoLaurea) throws Exception{
		ITitoloLaureaDAO eDAO = new TitoloLaureaDAO();
		return eDAO.findByTipoLaurea(tipoLaurea);
	}
	
	@Override
	public Set<Atenei> listaAteneiByCitta(Citta citta) throws Exception{
		IAteneoDAO eDAO = new AteneoDAO();
		return eDAO.findByCitta(citta);
	}
	
	@Override
	public Set<Citta> findByCodiceProvincia(String codiceProvincia)
			throws Exception {
		ICittaDAO eDAO = new CittaDAO();
		return eDAO.findByCodiceProvincia(codiceProvincia);
	}
	
	@Override
	public Provincie findCodiceProvincia(String codiceProvincia)
			throws Exception {
		IProvincieDAO eDAO = new ProvincieDAO();
		return eDAO.findByCodiceProvincia(codiceProvincia);
	}
	
	@Override
	public Paesi findByCodicePaese(String codicePaese)
			throws Exception {
		IPaesiDAO eDAO = new PaesiDAO();
		return eDAO.findByCodicePaese(codicePaese);
	}
	
	@Override
	public Citta findCittaById(Integer id)
			throws Exception {
		ICittaDAO eDAO = new CittaDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public Atenei findAteneoById(Integer id)
			throws Exception {
		IAteneoDAO eDAO = new AteneoDAO();
		return eDAO.findById(id);
	}
	

	@Override
	public TipoLaurea findTipoLaureaById(Integer id)
			throws Exception {
		ITipoLaureaDAO eDAO = new TipoLaureaDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public TipoProfessione findTipoProfessioneById(Integer id)
			throws Exception {
		ITipoProfessioneDAO eDAO = new TipoProfessioneDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public CampiRicerca findCampiRicercaById(Integer id)
			throws Exception {
		ICampiRicercaDAO eDAO = new CampiRicercaDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public CondizioniRicerca findCondizioniRicercaById(Integer id)
			throws Exception {
		ICondizioniRicercaDAO eDAO = new CondizioniRicercaDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public Delibera findDeliberaById(Integer id)
			throws Exception {
		IDeliberaDAO eDAO = new DeliberaDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public Esperto findEspertoById(Integer id)
			throws Exception {
		
		
		IEspertiDAO eDAO = new EspertiDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public Associazioni findAssociazioniById(Integer id)
			throws Exception {
		IAssociazioniDAO eDAO = new AssociazioniDAO();
		return eDAO.findById(id);
	}
	
	
	@Override
	public Tema findTemaById(Integer id)
			throws Exception {
		ITemiSpecializzazioniDAO eDAO = new TemiSpecializzazioniDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public Specializzazione findSpecializzazioneById(Integer id)
			throws Exception {
		ISpecializzazioniDAO eDAO = new SpecializzazioniDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public Professione findProfessioneById(Integer id)
			throws Exception {
		IProfessioneDAO eDAO = new ProfessioneDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public DatoreDiLavoro findDatoreDiLavoroById(Integer id)
			throws Exception {
		IDatoreDiLavoroDAO eDAO = new DatoreDiLavoroDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public TitoloLaurea findTitoloLaureaById(Integer id) 
			throws Exception{
		ITitoloLaureaDAO eDAO = new TitoloLaureaDAO();
		return eDAO.findById(id);
	}
	
	@Override
	public Set<Provincie> findByCodiceRegione(String codiceRegione)
			throws Exception {
		IProvincieDAO eDAO = new ProvincieDAO();
		return eDAO.findByCodiceRegione(codiceRegione);
	}
	@Override
	public Regioni findCodiceRegione(String codiceRegione)
			throws Exception {
		IRegioniDAO eDAO = new RegioniDAO();
		return eDAO.findByCodiceRegione(codiceRegione);
	}
	
	@Override
	public Boolean existentUser(String codFisc) throws Exception{
		boolean res = false;
		IEspertiDAO eDAO = new EspertiDAO();
		Esperto e = eDAO.findByCodFisc(codFisc);
		if (e!= null && e.getCf().length()>0)
			res = true;
		
		return res;
	}
	
	@Override
	public Esperto recuperoByEmail(String email) throws Exception
	{
		IEspertiDAO eDAO = new EspertiDAO();
		return eDAO.findByEmail(email);
	}
	
	/**
	 * Lista Pubblicazioni
	 * Questo metodo ha bisogno dell'oggetto Esperto recuperato dal DB tramite il DAO. 
	 * A questo oggetto si assegnano le nuove pubblicazioni e si salvano tramite il metodo UPDATE del DAO 
	 */ 	
	@SuppressWarnings("rawtypes")
	public void salvaListaPubblicazioniPerEsperto(Integer idEsperto, Set<Pubblicazione> set) throws Exception {

		if(set != null && set.size()>0){
//			EspertiDAO eDao = new EspertiDAO();
			// Scorre il Set e valorizza l'esperto che si sta salvando
//			Iterator it = e.getPubblicazioni().iterator();
//			PubblicazioniDAO dao = new PubblicazioniDAO();
			
			Iterator it = set.iterator();
			IPubblicazioniDAO dao = new PubblicazioniDAO();
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        
	        IEspertiDAO eDao = new EspertiDAO();
	    	Esperto e = eDao.findById(idEsperto, session);
	        
			while (it.hasNext()) {
			    // Get element
				Pubblicazione element = (Pubblicazione)it.next();
				element.setEsperto(e);
				dao.merge(element, session);
			}
			t.commit();

			session.close();	
		}		
	}	

	@SuppressWarnings("rawtypes")
	@Override
	public void salvaListaCompetenzePerEsperto(Integer idEsperto, Set<Istruzione> set) throws Exception {
		if(set != null && set.size()>0){
//			EspertiDAO eDao = new EspertiDAO();
			// Scorre il Set e valorizza l'esperto che si sta salvando
//			Iterator it = e.getCompetenze().iterator();
//			CompetenzeDAO dao = new CompetenzeDAO();
			
			Iterator it = set.iterator();
			ICompetenzeDAO dao = new CompetenzeDAO();
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
			IEspertiDAO eDao = new EspertiDAO();
			Esperto e = eDao.findById(idEsperto, session);
	        
			while (it.hasNext()) {
			    // Get element
				Competenza element = (Competenza)it.next();

				element.setEsperto(e);
				dao.merge(element, session);
			}
			t.commit();

			session.close();	
		}		 
	}
	
	

	@SuppressWarnings("rawtypes")
	@Override
	public void salvaListaEsperienzeLavorativePerEsperto(Integer idEsperto, Set<EsperienzaLavorativa> set) throws Exception {
		if(set != null && set.size()>0){
//			EspertiDAO eDao = new EspertiDAO();
			// Scorre il Set e valorizza l'esperto che si sta salvando
//			Iterator it = e.getEsperienzeLavorative().iterator();
//			EsperienzeLavorativeDAO dao = new EsperienzeLavorativeDAO();
			
			
			Iterator it = set.iterator();
			IEsperienzeLavorativeDAO dao = new EsperienzeLavorativeDAO();
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();

			IEspertiDAO eDao = new EspertiDAO();
			Esperto e = eDao.findById(idEsperto, session);
	        
			while (it.hasNext()) {
			    // Get element
				EsperienzaLavorativa element = (EsperienzaLavorativa)it.next();

				element.setEsperto(e);
				dao.merge(element, session);
			}
			t.commit();

			session.close();	
		}	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void salvaListaIstruzionePerEsperto(Integer idEsperto, Set<Istruzione> set) throws Exception {
		if(set != null && set.size()>0){
			// Scorre il Set e valorizza l'esperto che si sta salvando
			Iterator it = set.iterator();
			IIstruzioneDAO dao = new IstruzioneDAO();
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
			IEspertiDAO eDao = new EspertiDAO();
			Esperto e = eDao.findById(idEsperto, session);
			while (it.hasNext()) {
			    // Get element
				Istruzione element = (Istruzione)it.next();
				//IEspertiDAO eDao = new EspertiDAO();
				//Esperto e = eDao.findById(idEsperto, session);
				element.setEsperto(e);
				dao.merge(element, session);
			}
			t.commit();

			session.close();			
		}			
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void salvaListaPrecedentiIncarichiPerEsperto(Integer idEsperto, Set<PrecedenteIncarico> set) throws Exception {
		if(set != null && set.size()>0){
//			EspertiDAO eDao = new EspertiDAO();
//			// Scorre il Set e valorizza l'esperto che si sta salvando
//			Iterator it = e.getPrecedentiIncarichi().iterator();
//			PrecedentiIncarichiDAO dao = new PrecedentiIncarichiDAO();
			
			Iterator it = set.iterator();
			IPrecedentiIncarichiDAO dao = new PrecedentiIncarichiDAO();
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        
			IEspertiDAO eDao = new EspertiDAO();
			Esperto e = eDao.findById(idEsperto, session);
	        
			while (it.hasNext()) {
			    // Get element
				PrecedenteIncarico element = (PrecedenteIncarico)it.next();
				element.setEsperto(e);
				dao.merge(element, session);
			}
			t.commit();

			session.close();	
		}	
	}	
	
	@Override
	public void salvaCompetenzaEsperto(int idEsperto, String competenza) throws Exception {
		IEspertiDAO eDao = new EspertiDAO();
		eDao.salvaCompetenzaEsperto(idEsperto, competenza);
		
	}
	@Override
	public void salvaPresentazioneEsperto(int idEsperto, String presentazione) throws Exception {
		IEspertiDAO eDao = new EspertiDAO();
		eDao.salvaPresentazioneEsperto(idEsperto, presentazione);
		
	}
	
	
	@Override
	public boolean salvaModificaEsperto(Esperto element) throws Exception {
		boolean res = false;
//		EspertiDAO eDAO = new EspertiDAO();
//		try {
//			Esperto eFromDB = eDAO.findByUsername(e.getUsername());
//			assemblyEsperto(e, eFromDB);
//			eDAO.save(eFromDB);
//			res = true;
//		} catch (Exception e1) { 
//			e1.printStackTrace();
//		}
//		
		IEspertiDAO dao = new EspertiDAO();
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        Esperto espertoFromDB = dao.findByUsername(element.getUsername());
		assemblyEsperto(element, espertoFromDB);
		
		dao.merge(espertoFromDB, session);
		
		 
		t.commit();
		session.close();
		res=true;
		return res; 
	}

	@Override
	public String inviaDatiEsperto(Integer idEsperto) {
		String res = null;
		IEspertiDAO eDAO = new EspertiDAO();
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			Esperto e = eDAO.findById(idEsperto, session);

			e.setInviato(true);
			e.setDataInvio(new Date());
			String codiceDomanda = CCSEUtils.rightAlignFive(e.getId());
			e.setCodiceDomanda(codiceDomanda);
			
			eDAO.merge(e, session);
			t.commit();
			session.close();
			res = codiceDomanda;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return res;
	}
	
	private Esperto assemblyEsperto(Esperto espertoDTO, Esperto espertoFromDB) {
		
		espertoFromDB.setUsername(espertoDTO.getUsername());
		espertoFromDB.setPassword(espertoDTO.getPassword());
		espertoFromDB.setNome(espertoDTO.getNome());
		espertoFromDB.setCognome(espertoDTO.getCognome());
		espertoFromDB.setDataNascita(espertoDTO.getDataNascita());
		espertoFromDB.setCf(espertoDTO.getCf());
		espertoFromDB.setCel(espertoDTO.getCel());
		espertoFromDB.setEmail(espertoDTO.getEmail());
		espertoFromDB.setPiva(espertoDTO.getPiva());
		espertoFromDB.setFax(espertoDTO.getFax());
		espertoFromDB.setTel(espertoDTO.getTel());
		espertoFromDB.setResidenza(espertoDTO.getResidenza());
		espertoFromDB.setDomicilio(espertoDTO.getDomicilio());
		espertoFromDB.setCodiceDomanda(espertoDTO.getCodiceDomanda());
		espertoFromDB.setInviato(espertoDTO.getInviato());
		espertoFromDB.setPrivacy(espertoDTO.getPrivacy());
		espertoFromDB.setDettaglio(espertoDTO.getDettaglio());
		espertoFromDB.setCitta(espertoDTO.getCitta());
		espertoFromDB.setProvincie(espertoDTO.getProvincie());
		espertoFromDB.setRegioni(espertoDTO.getRegioni());
		espertoFromDB.setPaesi(espertoDTO.getPaesi());
		espertoFromDB.setCap(espertoDTO.getCap());
		espertoFromDB.setCittaNascita(espertoDTO.getCittaNascita());
		espertoFromDB.setProvincieNascita(espertoDTO.getProvincieNascita());
		espertoFromDB.setRegioniNascita(espertoDTO.getRegioniNascita());
		espertoFromDB.setPaesiNascita(espertoDTO.getPaesiNascita());
		espertoFromDB.setCandTrasmessa(espertoDTO.getCandTrasmessa()!=null?espertoDTO.getCandTrasmessa():false);
		espertoFromDB.setCittaEstera(espertoDTO.getCittaEstera());
		espertoFromDB.setCittaNascitaEstera(espertoDTO.getCittaNascitaEstera());
	
		return espertoFromDB;
	}
	
	@Override
	public Set<Pubblicazione> ricercaTuttePubblicazioni(Integer idEsperto) {
		Set<Pubblicazione> res = null;
//		try {
//			EspertiDAO eDAO = new EspertiDAO();
//			Esperto e = eDAO.findById(idEsperto);
//			res = e.getPubblicazioni();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		
		try {
			IPubblicazioniDAO dao = new PubblicazioniDAO();
//			List<Pubblicazione> list = dao.findByIdEsperto(idEsperto); 
			res =  new HashSet<Pubblicazione>(dao.findByIdEsperto(idEsperto));
			
		} catch (Exception e1) { 
			e1.printStackTrace();  
		}				
		return res;
	}

	@Override
	public Set<Competenza> ricercaTutteCompetenze(Integer idEsperto, FiltroRicercaEspertiDTO filtroRicerca) {
		Set<Competenza> res = null;
//		try {
//			EspertiDAO eDAO = new EspertiDAO();
//			Esperto e = eDAO.findById(idEsperto);
//			res = e.getCompetenze();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		
		try {
//			EspertiDAO eDAO = new EspertiDAO();
//			Esperto e = eDAO.findById(idEsperto);
//			res = e.getIstruzioni();
			
			ICompetenzeDAO dao = new CompetenzeDAO();
//			List<Competenza> list = dao.findByIdEsperto(idEsperto, filtroRicerca); 
			res =  new LinkedHashSet<Competenza>(dao.findByIdEsperto(idEsperto, filtroRicerca));
			
		} catch (Exception e1) { 
			e1.printStackTrace();  
		}		
		return res;
	}

	@Override
	public Set<EsperienzaLavorativa> ricercaTutteEsperienzeLavorative(Integer idEsperto, FiltroRicercaEspertiDTO filtroRicerca) {
		Set<EsperienzaLavorativa> res = new LinkedHashSet<EsperienzaLavorativa>();
//		try {
//			EspertiDAO eDAO = new EspertiDAO();
//			Esperto e = eDAO.findById(idEsperto);
//			res = e.getEsperienzeLavorative();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		
		try {
//			EspertiDAO eDAO = new EspertiDAO();
//			Esperto e = eDAO.findById(idEsperto);
//			res = e.getIstruzioni();
			
			IEsperienzeLavorativeDAO dao = new EsperienzeLavorativeDAO();
//			List<EsperienzaLavorativa> list = dao.findByIdEsperto(idEsperto, filtroRicerca);
			//res =  new HashSet<EsperienzaLavorativa>(dao.findByIdEsperto(idEsperto, filtroRicerca));
			List<EsperienzaLavorativa> esperienzeLavorative = dao.findByIdEsperto(idEsperto, filtroRicerca);
			for(EsperienzaLavorativa e: esperienzeLavorative){
				res.add(e);
			}
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		return res;
	}
	
	
	@Override
	public Set<EsperienzaLavorativa> findCarriereByIdEsperto(Integer idEsperto) {
		Set<EsperienzaLavorativa> res = new LinkedHashSet<EsperienzaLavorativa>();

		try {
			IEsperienzeLavorativeDAO dao = new EsperienzeLavorativeDAO();
			List<EsperienzaLavorativa> esperienzeLavorative = dao.findCarriereByIdEsperto(idEsperto);
			for(EsperienzaLavorativa e: esperienzeLavorative){
				res.add(e);
			}
		
			
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		return res;
	}
	
	@Override
	public Set<EsperienzaLavorativa> findEsperienzeLavorativeByIdEsperto(Integer idEsperto) {
		Set<EsperienzaLavorativa> res = new LinkedHashSet<EsperienzaLavorativa>();

		try {
			IEsperienzeLavorativeDAO dao = new EsperienzeLavorativeDAO();
			List<EsperienzaLavorativa> esperienzeLavorative = dao.findEsperienzeLavorativeByIdEsperto(idEsperto);
			for(EsperienzaLavorativa e: esperienzeLavorative){
				res.add(e);
			}
			
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		return res;
	}
	
	@Override
	public boolean checkIfExist(DatoreDiLavoro datoreDiLavoro) {
		boolean res = false;

		try {
			IAdministrationDAO dao = new EsperienzeLavorativeDAO();
			res =  dao.checkIfExist(datoreDiLavoro);
			
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		return res;
	}
	
	@Override
	public boolean checkIfExist(Delibera serializable){
		boolean res = false;

		try {
			IAdministrationDAO dao = new  PrecedentiIncarichiDAO();
			res =  dao.checkIfExist(serializable);
			
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		
		
		return res;
	}
	
	@Override
	public boolean checkIfExist(Tema serializable){
		boolean res = false;

		try {
			IAdministrationDAO dao = new  TemiSpecializzazioniDAO();
			res =  dao.checkIfExist(serializable);
			
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		
		
		return res;
	}
	
	@Override
	public boolean checkIfExist(Atenei serializable){
		boolean res = false;

		try {
			IAdministrationDAO dao = new  IstruzioneDAO();
			res =  dao.checkIfExist(serializable);
			
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		
		
		return res;
	}
	
	@Override
	public boolean checkIfExist(Specializzazione serializable) {
		boolean res = false;

		try {
			IAdministrationDAO dao = new SpecializzazioniDAO();
			res =  dao.checkIfExist(serializable);
			
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		return res;
	}
	
	@Override
	public boolean checkIfExist(TitoloLaurea serializable) {
		boolean res = false;

		try {
			IAdministrationDAO dao = new TitoloLaureaDAO();
			res =  dao.checkIfExist(serializable);
			
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		return res;
	}
	
	
	@Override
	public EsperienzaLavorativa findCarrieraPrincipaleByIdEsperto(Integer idEsperto) {
		EsperienzaLavorativa res = null;

		try {
			IEsperienzeLavorativeDAO dao = new EsperienzeLavorativeDAO();
			res =  dao.findCarrieraPrincipaleByIdEsperto(idEsperto);
			
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		
		
		return res;
	}
	

//	@Override
//	public Set<Istruzione> ricercaTutteIstruzione(Integer idEsperto) {
//		Set<Istruzione> res = null;
//		try {
//			EspertiDAO eDAO = new EspertiDAO();
//			Esperto e = eDAO.findById(idEsperto);
//			res = e.getIstruzioni();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		return res;
//	}
	
//	@Override
	public Set<Istruzione> ricercaTutteIstruzione(Integer idEsperto) {
		Set<Istruzione> res = null;
		try {
//			EspertiDAO eDAO = new EspertiDAO();
//			Esperto e = eDAO.findById(idEsperto);
//			res = e.getIstruzioni();
			
			IIstruzioneDAO dao = new IstruzioneDAO();
//			List<Istruzione> list = dao.findByIdEsperto(idEsperto);
			res =  new HashSet<Istruzione>(dao.findByIdEsperto(idEsperto));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return res;
	}	

	@Override
	public Set<PrecedenteIncarico> ricercaTuttePrecedentiIncarichi(Integer idEsperto) {
		Set<PrecedenteIncarico> res = null;
//		try {
//			EspertiDAO eDAO = new EspertiDAO();
//			Esperto e = eDAO.findById(idEsperto);
//			res = e.getPrecedentiIncarichi();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		
		try {
			IPrecedentiIncarichiDAO dao = new PrecedentiIncarichiDAO();
//			List<PrecedenteIncarico> list = dao.findByIdEsperto(idEsperto); 
			res =  new HashSet<PrecedenteIncarico>(dao.findByIdEsperto(idEsperto));
			
		} catch (Exception e1) { 
			e1.printStackTrace();  
		}	
		
		
		return res;
	}
	
	@Override
	public List<Object> ricerca(String sql) throws Exception{
		List<Object> res = null;
		IEspertiDAO dao = new EspertiDAO();
		res =  dao.findEspertoByCustomSql(sql);
		return res;
	}
	
	
	@Override
	public Set<Associazioni> ricerca(List<Esperto> listEsperti, List<Tema> listTemi, List<Delibera> listDelibere) throws Exception{
		
		IAssociazioniDAO dao = new AssociazioniDAO();
		Set<Associazioni> res =  dao.ricerca(listEsperti, listTemi, listDelibere);
		return res;
	}
	
	@Override
	public List<Tema> ricercaTuttiTemi() {
		ITemiSpecializzazioniDAO tsDAO = new TemiSpecializzazioniDAO();
		return tsDAO.getListaTemi();
	}

	@Override
	public List<Specializzazione> ricercaTutteSpecializzazioni(Integer idTema) {
		ITemiSpecializzazioniDAO tsDAO = new TemiSpecializzazioniDAO();
		return tsDAO.getSpecializzazioniByTema(idTema);
	}

	@Override
	public boolean cancellaCompetenza(Integer id) throws Exception {
		boolean res = false;
		
		ICompetenzeDAO dao = new CompetenzeDAO();
		dao.delete(id);
		
		return res;
	}

	@Override
	public boolean cancellaPubblicazione(Integer id) throws Exception {
		boolean res = false;
		
		IPubblicazioniDAO dao = new PubblicazioniDAO();
		dao.delete(id);
		
		return res;
	}

	@Override
	public boolean cancellaEsperienzaLavorativa(Integer id) throws Exception {
		boolean res = false;
		IEsperienzeLavorativeDAO dao = new EsperienzeLavorativeDAO();
		dao.delete(id);
		return res;
	}
	
	@Override
	public boolean cancellaDatorediLavoro(Integer id) throws Exception {
		boolean res = false;
		IDatoreDiLavoroDAO dao = new DatoreDiLavoroDAO();
		dao.delete(id);
		return res;
	}

	@Override
	public boolean cancellaDelibera(Integer id) throws Exception {
		boolean res = false;
		IDeliberaDAO dao = new DeliberaDAO();
		dao.delete(id);
		return res;
	}
	
	
	@Override
	public boolean cancellaTema(Integer id) throws Exception {
		boolean res = false;
		ITemiSpecializzazioniDAO dao = new TemiSpecializzazioniDAO();
		dao.delete(id);
		return res;
	}
	
	@Override
	public boolean cancellaAteneo(Integer id) throws Exception {
		boolean res = false;
		IAteneoDAO dao = new AteneoDAO();
		dao.delete(id);
		return res;
	}
	
	@Override
	public boolean cancellaIstruzione(Integer id) throws Exception {
		boolean res = false;
		
		IIstruzioneDAO dao = new IstruzioneDAO();
		dao.delete(id);
		
		return res;
	}

	@Override
	public boolean cancellaPrecedentiIncarichi(Integer id) throws Exception {
		boolean res = false;
		
		IPrecedentiIncarichiDAO dao = new PrecedentiIncarichiDAO();
		dao.delete(id);
		
		return res;
	}
	
	@Override
	public boolean cancellaSpecializzazione(Integer id) throws Exception {
		boolean res = false;
		ISpecializzazioniDAO dao = new SpecializzazioniDAO();
		dao.delete(id);
		return res;
	}
	
	
	@Override
	public boolean cancellaTitoloLaurea(Integer id) throws Exception {
		boolean res = false;
		ITitoloLaureaDAO dao = new TitoloLaureaDAO();
		dao.delete(id);
		return res;
	}
	
	@Override
	public boolean cancellaAssociazioni(Esperto esperto, Delibera delibera) throws Exception {
		boolean res = false;
		IAssociazioniDAO dao = new AssociazioniDAO();
		dao.delete(esperto, delibera);
		return res;
	}


	@Override
	/** Ogni oggetto da aggiornare presente nella lista deve avere l'id valorizzato */
	public boolean aggiornaPubblicazioniEsperto(List<Pubblicazione> list) throws Exception {
		boolean res = false;
		// dao
//		PubblicazioniDAO dao = new PubblicazioniDAO();
//		@SuppressWarnings("rawtypes")
//		Iterator it = list.iterator();
//		while (it.hasNext()) {
//			Pubblicazione element = (Pubblicazione) it.next();
//			dao.merge(element);
//		}
//		dao.commit();
		
		if(list != null && list.size()>0){
			IPubblicazioniDAO dao = new PubblicazioniDAO();
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        
	        Esperto esp = list.get(0).getEsperto();
			
			for (Pubblicazione p: list)
				dao.merge(p, session);
			
			t.commit();
			session.close();
		}
		
		
		
		return res;
	}

	@Override
	/** Ogni oggetto da aggiornare presente nella lista deve avere l'id valorizzato */	
	public boolean aggiornaCompetenzeEsperto(List<Competenza> list) throws Exception  {
		boolean res = false;
		// dao
//		CompetenzeDAO dao = new CompetenzeDAO();
//		@SuppressWarnings("rawtypes")
//		Iterator it = list.iterator();
//		while (it.hasNext()) {
//			Competenza element = (Competenza) it.next();
//			dao.merge(element);
//		}
//		dao.commit();
		
		if(list != null && list.size()>0){
			ICompetenzeDAO dao = new CompetenzeDAO();
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        
	        Esperto esp = list.get(0).getEsperto();
	        
	        for (Competenza comp: list)
	        	dao.merge(comp, session);
	        
			
			t.commit();
			session.close();
		}
		
		
		return res;
	}

//	@Override
//	/** Ogni oggetto da aggiornare presente nella lista deve avere l'id valorizzato */	
//	public boolean aggiornaIstruzioneEsperto(List<Istruzione> list) throws Exception {
//		boolean res = false;
//		// dao
//		IstruzioneDAO dao = new IstruzioneDAO();
//		@SuppressWarnings("rawtypes")
//		Iterator it = list.iterator();
//		while (it.hasNext()) {
//			Istruzione element = (Istruzione) it.next();
//			dao.merge(element);
//		}
//		dao.commit();
//		
//		return res;
//	}
	

	@Override
	/** Ogni oggetto da aggiornare presente nella lista deve avere l'id valorizzato */	
	public boolean aggiornaPrecedentiIncarichiEsperto(List<PrecedenteIncarico> list) throws Exception {
		boolean res = false;
		// dao
		if(list != null && list.size()>0){
			IPrecedentiIncarichiDAO dao = new PrecedentiIncarichiDAO();
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        
	        Esperto esp = list.get(0).getEsperto();
	        for (PrecedenteIncarico precInc: list)
	        	dao.merge(precInc, session);
			
			t.commit();
			session.close();
		}
		return res;
	}
	
	
	
	@Override
	public Integer inserisciDatoreDiLavoro(DatoreDiLavoro datoreDiLavoro) throws Exception {
		Integer id=null;
		try {
			IDatoreDiLavoroDAO datoreDiLavoroDAO = new DatoreDiLavoroDAO();
			id = datoreDiLavoroDAO.save(datoreDiLavoro);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return id;
	 
	}
	
	@Override
	public Integer inserisciTitoloLaurea(TitoloLaurea titoloLaurea) throws Exception {
		Integer id=null;
		try {
			ITitoloLaureaDAO titoloLaureaDAO = new TitoloLaureaDAO();
			id = titoloLaureaDAO.save(titoloLaurea);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return id;
	 
	}
	
	
	@Override
	public Integer inserisciAssociazioni(Associazioni associazioni) throws Exception {
		Integer id=null;
		try {
			IAssociazioniDAO associazioniDAO = new AssociazioniDAO();
			
			id = associazioniDAO.save(associazioni);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return id;
	 
	}
	
	
	@Override
	public Integer inserisciTema(Tema tema) throws Exception {
		Integer id=null;
		try {
			ITemiSpecializzazioniDAO temiSpecializzazioniDAO = new TemiSpecializzazioniDAO();
			id = temiSpecializzazioniDAO.save(tema);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return id;
	 
	}
	
	
	@Override
	public Integer inserisciSpecializzazione(Specializzazione specializzazione) throws Exception {
		Integer id=null;
		try {
			ISpecializzazioniDAO specializzazioniDAO = new SpecializzazioniDAO();
			id = specializzazioniDAO.save(specializzazione);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return id;
	 
	}
	
	@Override
	public Integer inserisciDelibera(Delibera delibera) throws Exception {
		Integer id=null;
		try {
			IDeliberaDAO deliberaDAO = new DeliberaDAO();
			id = deliberaDAO.save(delibera);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return id;
	 
	}
	
	
	@Override
	public Integer inserisciAteneo(Atenei ateneo) throws Exception {
		Integer id=null;
		try {
			IAteneoDAO ateneoDAO = new AteneoDAO();
			id = ateneoDAO.save(ateneo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return id;
	 
	}
	
	@Override
	public boolean aggiornaDatoreDiLavoro(DatoreDiLavoro datoreDiLavoro) throws Exception {
	
		IDatoreDiLavoroDAO dao = new DatoreDiLavoroDAO();
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        dao.merge(datoreDiLavoro, session);
     	t.commit();
		session.close();			
		return true;
	}
	
	@Override
	public boolean aggiornaTitoloLaurea(TitoloLaurea titoloLaurea) throws Exception {
	
		ITitoloLaureaDAO dao = new TitoloLaureaDAO();
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        dao.merge(titoloLaurea, session);
     	t.commit();
		session.close();			
		return true;
	}
	
	@Override
	public boolean aggiornaAssociazioni(Associazioni associazioni) throws Exception {
	
		IAssociazioniDAO dao = new AssociazioniDAO();
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        dao.merge(associazioni, session);
     	t.commit();
		session.close();			
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean aggiornaAssociazioni(List<Associazioni> associazionis) throws Exception {
		boolean res = false;
		
		
			IAssociazioniDAO dao = new AssociazioniDAO();
			 Session session = HibernateUtil.getSessionFactory().openSession();
			 
			// dao.delete( associazionis.get(0).getEsperto().getId(),associazionis.get(0).getDelibera().getId());
			dao.delete( associazionis.get(0).getEsperto(),associazionis.get(0).getDelibera());
				
			 Transaction t = session.beginTransaction();
			
	        
	         for (Associazioni associazioni: associazionis){
	        		dao.merge(associazioni, session);
	         }
	         t.commit();
	 		 session.close();	
	 		 res=true;
	 		 return res;
	}
	
	@Override
	public boolean aggiornaDelibera(Delibera delibera) throws Exception {
	
		IDeliberaDAO dao = new DeliberaDAO();
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        dao.merge(delibera, session);
     	t.commit();
		session.close();			
		return true;
	}
	
	
	@Override
	public boolean aggiornaEsperto(int idEsperto, String email, String tel) throws Exception {
		IEspertiDAO eDao = new EspertiDAO();
		return eDao.aggiornaEsperto(idEsperto, email, tel);
	
	}
	
	@Override
	public boolean aggiornaSpecializzazione(Specializzazione specializzazione) throws Exception {
	
		ISpecializzazioniDAO dao = new SpecializzazioniDAO();
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        dao.merge(specializzazione, session);
     	t.commit();
		session.close();			
		return true;
	}
	
	
	@Override
	public boolean aggiornaTema(Tema tema) throws Exception {
	
		ITemiSpecializzazioniDAO dao = new TemiSpecializzazioniDAO();
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        dao.merge(tema, session);
     	t.commit();
		session.close();			
		return true;
	}
	
	
	@Override
	public boolean aggiornaAtenei(Atenei ateneo) throws Exception {
	
		IAteneoDAO dao = new AteneoDAO();
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        dao.merge(ateneo, session);
     	t.commit();
		session.close();			
		return true;
	}
	
	@Override
	/** Ogni oggetto da aggiornare presente nella lista deve avere l'id valorizzato */	
	public boolean aggiornaEsperienzeLavorativeEsperto(List<EsperienzaLavorativa> list) throws Exception  {
		boolean res = false;
		// dao
//		EsperienzeLavorativeDAO dao = new EsperienzeLavorativeDAO();
//		@SuppressWarnings("rawtypes")
//		Iterator it = list.iterator();
//		while (it.hasNext()) {
//			EsperienzaLavorativa element = (EsperienzaLavorativa) it.next();
//			dao.merge(element);
//		}
//		dao.commit();
		
		
		if(list != null && list.size()>0){
//			EspertiDAO eDao = new EspertiDAO();
			// Scorre il Set e valorizza l'esperto che si sta salvando
//			Iterator it = set.iterator();
			IEsperienzeLavorativeDAO dao = new EsperienzeLavorativeDAO();
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	        
	        Esperto esp = list.get(0).getEsperto();
	        
	        for (EsperienzaLavorativa espLav: list)
	        	dao.merge(espLav, session);
			
//			while (it.hasNext()) {
//			    // Get element
//				Istruzione element = (Istruzione)it.next();
//				EspertiDAO eDao = new EspertiDAO();
//				Esperto e = eDao.findById(idEsperto, session);
//				element.setEsperto(e);
////				dao.save(element);
//				dao.merge(element, session);
//			}
			t.commit();
			session.close();			
		}
		
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean aggiornaIstruzioneEsperto(List<Istruzione> list) throws Exception {
		boolean res = false;
		if(list != null && list.size()>0){
//			EspertiDAO eDao = new EspertiDAO();
			// Scorre il Set e valorizza l'esperto che si sta salvando
//			Iterator it = set.iterator();
			IIstruzioneDAO dao = new IstruzioneDAO();
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction t = session.beginTransaction();
	       
	        Esperto esp = list.get(0).getEsperto();
	        for (Istruzione istr: list)
	        	dao.merge(istr, session);
			
//			while (it.hasNext()) {
//			    // Get element
//				Istruzione element = (Istruzione)it.next();
//				EspertiDAO eDao = new EspertiDAO();
//				Esperto e = eDao.findById(idEsperto, session);
//				element.setEsperto(e);
////				dao.save(element);
//				dao.merge(element, session);
//			}
			t.commit();
			session.close();			
		}		
		return res;
	}

	@Override
	public List<Esperto> ricercaEsperti(FiltroRicercaEspertiDTO filtroRicerca, boolean approvato) {
		List<Esperto> listaEsperti = null;
		
		try{
			IEspertiDAO espertiDAO = new EspertiDAO();
			listaEsperti = espertiDAO.searchEsperti(filtroRicerca, approvato);
		}catch(Exception ex){
		}
		return listaEsperti;
	}

	@Override
	public Set<Esperto> ricercaTuttiEsperti() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Esperto> ricercaFiltroEsperti(FiltriRicercaDTO filtri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aggiornaDettaglio(int idEsperto, InfoEspertoApprovato dettaglio)
			throws Exception {
		IEspertiDAO eDao = new EspertiDAO();
		eDao.aggiornaDettaglio(idEsperto, dettaglio);
		
	}

	@Override
	public void inserisciAllegatoPubbl(Esperto esperto,
			AllegatoPubblEsperto allegato) throws Exception {
		
		IEspertiDAO eDao = new EspertiDAO();
		eDao.inserisciAllegato(esperto, allegato);
		
	}
	
	@Override
	public void inserisciDocumentoAllegatoEsperto(Esperto esperto,
			AllegatoPubblEsperto allegato) throws Exception {
		
		IEspertiDAO eDao = new EspertiDAO();
		eDao.inserisciAllegato(esperto, allegato);
		
	}
	
	@Override
	public void inserisciDocumentoAllegatoEsperto(Esperto esperto,
			DocumentoAllegatoEsperto allegato) throws Exception {
		
		IEspertiDAO eDao = new EspertiDAO();
		eDao.inserisciAllegato(esperto, allegato);
		
	}
	
	@Override
	public void inserisciCartaIdentitaEsperto(Esperto esperto,
			CartaIdentitaEsperto allegato) throws Exception {
		
		IEspertiDAO eDao = new EspertiDAO();
		eDao.inserisciCartaIdentita(esperto, allegato);
		
	}

	@Override
	public Esperto recuperoEspertoById(int idEsperto) throws Exception {
		
		IEspertiDAO eDao = new EspertiDAO();
		return eDao.findById(idEsperto);
	}

	@Override
	public void modificaStatoEsperto(int idEsperto, int statoEsperto,
			boolean bloccato) throws Exception {
		IEspertiDAO eDao = new EspertiDAO();
		eDao.modificaStatoEsperto(idEsperto, statoEsperto, bloccato);
		
	}

	@Override
	public void eliminaAllegatoPubbl(Esperto esperto) throws Exception {
		
		IEspertiDAO eDao = new EspertiDAO();
		eDao.eliminaAllegato(esperto);
		
	}

	@Override
	public Boolean overMaxNumPubblication(Integer idEsperto) throws Exception {
		IPubblicazioniDAO pDao =new PubblicazioniDAO();
		Integer curCount = pDao.countPubblicationByIdEsperto(idEsperto);
		if(curCount >= 10)
		{
			return true;
		}
		return false;
	}

	@Override
	public void eliminaDocumentoAllegato(Esperto esperto) throws Exception {
		
		IEspertiDAO eDao = new EspertiDAO();
		eDao.eliminaDocumentoAllegato(esperto);
		
	}
	
	@Override
	public void eliminaCartaIdentita(Esperto esperto) throws Exception {
		
		IEspertiDAO eDao = new EspertiDAO();
		eDao.eliminaCartaIdentita(esperto);
		
	}

	
	

	

	

	
	
}
