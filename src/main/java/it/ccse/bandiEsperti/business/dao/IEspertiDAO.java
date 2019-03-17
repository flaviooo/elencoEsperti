package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.AllegatoPubblEsperto;
import it.ccse.bandiEsperti.business.model.CartaIdentitaEsperto;
import it.ccse.bandiEsperti.business.model.DocumentoAllegatoEsperto;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.InfoEspertoApprovato;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

public interface IEspertiDAO {

	public Integer save(Esperto element) throws Exception;
	public void save(Esperto element, Session session) throws Exception;

	/** Merge con commit */
	public void merge(Esperto element, Session session) throws Exception;
	public Esperto findById(int id);
	public Esperto findById(int id, Session session);
	public Esperto findByUsername(String username) throws Exception;
	public Esperto findByEmail(String email) throws Exception;
	public Esperto findByCodFisc(String codFisc) throws Exception;
	public Esperto findByUsernameAndPassword(String username, String password) throws Exception;
	public Esperto findByCodFiscAndPassword(String codFisc, String password) throws Exception;

	public void delete(int id);
	public Set<Esperto> findAll() throws Exception;

	/**
	 * Map<String, Boolean> temi : la chiave della Map è idTema
	 * */
	public List<Esperto> findByCompetenzeANDAnagrafica(List<Integer> spec2, String cognome, Date dataNascita) throws Exception;
	/*
	 * funzione che effettua ricerche di esperti su base Criteri
	 */
	public List<Esperto> searchEsperti(FiltroRicercaEspertiDTO filtroRicerca, boolean approvato);
	public void aggiornaDettaglio(int idEsperto, InfoEspertoApprovato dettaglio) throws Exception;
	public void inserisciAllegato(Esperto esperto, AllegatoPubblEsperto allegato) throws Exception;
	public void inserisciAllegato(Esperto esperto, DocumentoAllegatoEsperto allegato) throws Exception;
	public void modificaStatoEsperto(int idEsperto, int stato, boolean bloccato) throws Exception;
	public void eliminaAllegato(Esperto esperto) throws Exception;
	public  void salvaCompetenzaEsperto(int idEsperto, String competenza) throws Exception;
	public  List<Object> findEspertoByCustomSql(String sql) throws Exception;
	public void eliminaDocumentoAllegato(Esperto esperto) throws Exception;
	public  boolean aggiornaEsperto(int idEsperto, String email, String tel) throws Exception;
	public void salvaPresentazioneEsperto(int idEsperto, String presentazione) throws Exception;
	
	public void inserisciCartaIdentita(Esperto esperto, CartaIdentitaEsperto allegato) throws Exception;
	public void eliminaCartaIdentita(Esperto esperto) throws Exception;

}