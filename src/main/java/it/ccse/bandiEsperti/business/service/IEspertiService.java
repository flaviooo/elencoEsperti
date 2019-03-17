package it.ccse.bandiEsperti.business.service;

import java.util.List;
import java.util.Set;

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

public interface IEspertiService {

	/** Login di un esperto e/o amministratore al sistema */
	public abstract Esperto login(String codFisc, String password) throws Exception;
	
	/** Metodo per la registrazione di un nuovo esperto (dei soli dati anagrafici) */
	public abstract boolean registrazione(Esperto e);
	
	public abstract Boolean existentUser(String codFisc) throws Exception;
	public abstract Boolean overMaxNumPubblication(Integer idEsperto) throws Exception;
	
//	/** Metodo per il salvataggio di un <strong>NUOVO</strong> esperto e di tutti i suoi dati correlati */
//	boolean salvaCompleto(Esperto e);

	// metodi per il salvataggio dei dati correlati ad un esperto registrato
	public abstract void salvaListaPubblicazioniPerEsperto(Integer idEsperto, Set<Pubblicazione> set) throws Exception;
	public abstract void salvaListaCompetenzePerEsperto(Integer idEsperto, Set<Istruzione> set) throws Exception;
	
	public abstract void salvaListaIstruzionePerEsperto(Integer idEsperto, Set<Istruzione> set) throws Exception;
	public abstract void salvaListaPrecedentiIncarichiPerEsperto(Integer idEsperto, Set<PrecedenteIncarico> set) throws Exception;
	
	// metodi per l'aggiornamento di un esperto
	public abstract boolean salvaModificaEsperto(Esperto e)throws Exception ;
	public abstract String inviaDatiEsperto(Integer idEsperto);
	
	// metodi per la ricerca
	public abstract Set<Pubblicazione> ricercaTuttePubblicazioni(Integer idEsperto);
	public abstract Set<Competenza> ricercaTutteCompetenze(Integer idEsperto, FiltroRicercaEspertiDTO filtroRicerca);
	public abstract Set<EsperienzaLavorativa> ricercaTutteEsperienzeLavorative(Integer idEsperto, FiltroRicercaEspertiDTO filtroRicerca);
	public abstract Set<Istruzione> ricercaTutteIstruzione(Integer idEsperto);
	public abstract Set<PrecedenteIncarico> ricercaTuttePrecedentiIncarichi(Integer idEsperto);	
	public abstract List<Object> ricerca(String query) throws Exception;
	//metodi di utilità
	public abstract List<Tema> ricercaTuttiTemi();
	public abstract List<Specializzazione> ricercaTutteSpecializzazioni(Integer idTema);
	
	// metodi per la cancellazione con id
	public abstract boolean cancellaCompetenza(Integer id) throws Exception;
	public abstract boolean cancellaPubblicazione(Integer id) throws Exception;
	public abstract boolean cancellaEsperienzaLavorativa(Integer id) throws Exception;
	public abstract boolean cancellaIstruzione(Integer id) throws Exception;
	public abstract boolean cancellaPrecedentiIncarichi(Integer id) throws Exception;
	public abstract boolean cancellaDatorediLavoro(Integer id) throws Exception;
	public abstract boolean cancellaDelibera(Integer id) throws Exception;
	public abstract boolean cancellaAteneo(Integer id) throws Exception;
	public abstract boolean cancellaTema(Integer id) throws Exception;
	public abstract boolean cancellaSpecializzazione(Integer id) throws Exception;
	public abstract boolean cancellaTitoloLaurea(Integer id) throws Exception;
	
	// metodi per l'aggiornamento di liste di attributi di un esperto
	public abstract boolean aggiornaPubblicazioniEsperto(List<Pubblicazione> list) throws Exception ;
	public abstract boolean aggiornaCompetenzeEsperto(List<Competenza> list) throws Exception ;
	public abstract boolean aggiornaIstruzioneEsperto(List<Istruzione> list) throws Exception ;
	public abstract boolean aggiornaPrecedentiIncarichiEsperto(List<PrecedenteIncarico> list) throws Exception ;
	public abstract boolean aggiornaEsperienzeLavorativeEsperto(List<EsperienzaLavorativa> list) throws Exception;	
	public abstract boolean aggiornaTitoloLaurea(TitoloLaurea titoloLaurea) throws Exception;
	public abstract boolean aggiornaAssociazioni(Associazioni associazioni) throws Exception;
	public abstract boolean aggiornaDatoreDiLavoro(DatoreDiLavoro datoreDiLavoro) throws Exception;
	public abstract boolean aggiornaDelibera(Delibera delibera) throws Exception;
	public abstract boolean aggiornaEsperto(int idEsperto, String email, String tel) throws Exception;
	public abstract boolean aggiornaAtenei(Atenei ateneo) throws Exception;
	public abstract boolean aggiornaTema(Tema tema) throws Exception;
	public abstract boolean aggiornaSpecializzazione(Specializzazione specializzazione) throws Exception;
	
	// metodi per l'amministrazione
	public abstract Set<Esperto> ricercaTuttiEsperti();
	public abstract Set<Esperto> ricercaFiltroEsperti(FiltriRicercaDTO filtri);
	public abstract List<Esperto> ricercaEsperti(FiltroRicercaEspertiDTO filtroRicerca, boolean approvato);
	public abstract Esperto recuperoByEmail(String email) throws Exception;
	public abstract void aggiornaDettaglio(int idEsperto, InfoEspertoApprovato dettaglio) throws Exception;
	public abstract void inserisciAllegatoPubbl(Esperto esperto, AllegatoPubblEsperto allegato) throws Exception;
	public abstract void eliminaAllegatoPubbl(Esperto esperto) throws Exception;
	public abstract Esperto recuperoEspertoById(int idEsperto) throws Exception;
	public abstract void modificaStatoEsperto(int idEsperto, int statoEsperto, boolean bloccato) throws Exception;
	//public abstract Citta findById(Integer id) throws Exception;
	public abstract Provincie findCodiceProvincia(String codiceProvincia) throws Exception;
	public abstract  Regioni findCodiceRegione(String codiceRegione) throws Exception;
	public abstract Paesi findByCodicePaese(String codicePaese) throws Exception;
	public abstract TipoLaurea findTipoLaureaById(Integer id) throws Exception;
	public abstract TitoloLaurea findTitoloLaureaById(Integer id) throws Exception;
	public abstract Citta findCittaById(Integer id) throws Exception;
	public abstract Atenei findAteneoById(Integer id) throws Exception;
	public abstract TipoProfessione findTipoProfessioneById(Integer id) throws Exception;
	public abstract Set<TipoProfessione> listaTipoProfessione() throws Exception;
	public abstract Professione findProfessioneById(Integer id) throws Exception;
	public abstract CampiRicerca findCampiRicercaById(Integer id) throws Exception;
	public abstract CondizioniRicerca findCondizioniRicercaById(Integer id) throws Exception;
	public abstract DatoreDiLavoro findDatoreDiLavoroById(Integer id) throws Exception;
	public abstract Set<EsperienzaLavorativa> findCarriereByIdEsperto(Integer idEsperto) throws Exception;
	public abstract EsperienzaLavorativa findCarrieraPrincipaleByIdEsperto(Integer idEsperto) throws Exception;
	public abstract Set<EsperienzaLavorativa> findEsperienzeLavorativeByIdEsperto(Integer idEsperto) throws Exception;
	public abstract Tema findTemaById(Integer id) throws Exception;
	
	public abstract Set<Associazioni> listaAssociazioni() throws Exception;
	public abstract Set<Professione> listaProfessione() throws Exception;
	public abstract Set<CampiRicerca> listaCampiRicerca() throws Exception;
	public abstract Set<TipoLaurea> listaTipoLaurea() throws Exception;
	public abstract Set<TitoloLaurea> listaTitoloLaureaByTipoLaurea(TipoLaurea tipoLaurea) throws Exception;
	public abstract Set<Atenei> listaAteneiByCitta(Citta citta) throws Exception;
	public abstract Set<Atenei> listaAtenei() throws Exception;
	public abstract Set<DatoreDiLavoro> listaDatoreDiLavoro() throws Exception;
	public abstract Set<Paesi> listaPaesi() throws Exception;
	public abstract Set<Regioni> listaRegioni() throws Exception;
	public abstract Set<Regioni> listaRegioni(Paesi paesi)  throws Exception;
	public abstract Set<Provincie> listaProvincie() throws Exception;
	public abstract Set<Provincie> findByCodiceRegione(String codiceRegione) throws Exception;
	public abstract  Set<Citta> findByCodiceProvincia(String codiceProvincia) throws Exception;
	public abstract  Set<Citta> listaCitta() throws Exception;
	public abstract  Set<TitoloLaurea> listaTitoloLaurea()  throws Exception;
	public abstract Set<Delibera> listaDelibera() throws Exception;
	public abstract Set<Specializzazione> listaSpecializzazioni() throws Exception;
	public abstract  Set<CondizioniRicerca> listaCondizioniRicerca() throws Exception;
	public abstract Set<Esperto> listaEsperti() throws Exception;

	public abstract void inserisciDocumentoAllegatoEsperto(Esperto e, DocumentoAllegatoEsperto allegato) throws Exception;
	public void salvaListaEsperienzeLavorativePerEsperto(Integer idEsperto, Set<EsperienzaLavorativa> set) throws Exception;
	
	public Integer inserisciDatoreDiLavoro(DatoreDiLavoro datoreDiLavoro) throws Exception;
	public Integer inserisciDelibera(Delibera delibera) throws Exception;
	public Integer inserisciAteneo(Atenei ateneo) throws Exception;
	public Integer inserisciTema(Tema tema) throws Exception;
	public Integer inserisciSpecializzazione(Specializzazione specializzazione) throws Exception;
	public abstract Integer inserisciTitoloLaurea(TitoloLaurea titoloLaurea) throws Exception;
	public abstract Integer inserisciAssociazioni(Associazioni associazioni) throws Exception;
	
	
	public abstract Associazioni findAssociazioniById(Integer id) throws Exception;
	public abstract Delibera findDeliberaById(Integer id) throws Exception;
	public abstract void salvaCompetenzaEsperto(int idEsperto, String competenza) throws Exception;
	public abstract Esperto findEspertoById(Integer id)  throws Exception;
	public abstract Specializzazione findSpecializzazioneById(Integer id)  throws Exception;
	
	public abstract boolean checkIfExist(DatoreDiLavoro serializable) throws Exception;
	public abstract boolean checkIfExist( Delibera serializable) throws Exception;
	public abstract boolean checkIfExist(Atenei serializable) throws Exception;
	public abstract boolean checkIfExist(Tema serializable) throws Exception;
	public abstract boolean checkIfExist(Specializzazione serializable) throws Exception;
	public abstract boolean checkIfExist(TitoloLaurea serializable) throws Exception;

	boolean aggiornaAssociazioni(List<Associazioni> associazionis)	throws Exception;

	public abstract Set<Associazioni> ricerca(List<Esperto> listEsperti,
			List<Tema> listTemi, List<Delibera> listDelibera) 	throws Exception;

	boolean cancellaAssociazioni(Esperto esperto, Delibera delibera) throws Exception;

	public void inserisciDocumentoAllegatoEsperto(Esperto esperto,
			AllegatoPubblEsperto allegato) throws Exception;

	public abstract void eliminaDocumentoAllegato(Esperto e) throws Exception;
	public void salvaPresentazioneEsperto(int idEsperto, String presentazione) throws Exception;
	public void inserisciCartaIdentitaEsperto(Esperto esperto,CartaIdentitaEsperto allegato) throws Exception ;	
	public void eliminaCartaIdentita(Esperto esperto) throws Exception;
	

}
