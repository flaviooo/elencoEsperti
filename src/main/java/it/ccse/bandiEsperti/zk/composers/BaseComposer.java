package it.ccse.bandiEsperti.zk.composers;

import it.ccse.bandiEsperti.business.command.AggiornaListaIstruzioneCommand;
import it.ccse.bandiEsperti.business.command.CancellaIstruzioneCommand;
import it.ccse.bandiEsperti.business.command.RicercaIstruzioneCommand;
import it.ccse.bandiEsperti.business.command.SalvaIstruzioneCommand;
import it.ccse.bandiEsperti.business.command.interfaces.ICommand;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Istruzione;
import it.ccse.bandiEsperti.zk.comparators.IstruzioneComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

public class BaseComposer extends GenericForwardComposer {
	private static final long serialVersionUID = -3011711889760264355L;

	private Textbox txt_titoloStudio;
	private Datebox dta_data_conseguimento;
	private Button btn_aggiorna_istruzione;
	
	private TreeSet<Istruzione> listaIstruzione;
	private Esperto user;
	
	
	public Set<Istruzione> getIstruzione() {
		return this.listaIstruzione;
	}
	
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		this.user = (Esperto) session.getAttribute("login");
		this.initModel(false);
	}
	
	@SuppressWarnings("unchecked")
	public void initModel(boolean isUpdate) throws Exception{
		try{
			ICommand command = new RicercaIstruzioneCommand(this.user.getId());
			command.execute();
			
			this.listaIstruzione = new TreeSet<Istruzione>(new IstruzioneComparator());
			this.listaIstruzione.addAll(command.getSet());
			
			if (this.listaIstruzione!=null){
				this.btn_aggiorna_istruzione.setDisabled(!(this.listaIstruzione.size()>0));
			}

			if (isUpdate){
				((AnnotateDataBinder)this.self.getAttribute("binder")).loadAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Evento per l'aggiornamento dei dati modificati */
	public void onClick$btn_aggiorna_istruzione(Event event) {
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onAggiornaIstruzione",event.getTarget(),null);
	}
	
	public void onAggiornaIstruzione(Event event){			
		try{
			this.aggiornaIstruzione();
		}catch(Exception ex){
			throw new WrongValueException("Errore nel salvataggio dei dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
		
	}
	
	/**
	 * Metodo che provvede all'inserimento di una nuova istruzione 
	 */
	private void aggiornaIstruzione() throws Exception{
		boolean esito = false;
		
		List<Istruzione> list = new ArrayList<Istruzione>(this.listaIstruzione);
		
		ICommand command = new AggiornaListaIstruzioneCommand(list);
		esito = command.execute();
		
		if (!esito){
			throw new Exception("non � possibile eseguire l'operazione di aggiornamento dei dati");
		}
		
		this.initModel(true);
	}
	
	/** Evento per l'inserimento di un nuovo dato */
	public void onClick$bnt_aggiungi_istruzione(Event event) {
		if (this.listaIstruzione==null){
			throw new WrongValueException("Non sono presenti istruzioni da aggiornare");
		}
		Clients.showBusy("Validazione e Salvataggio in corso... ");
		Events.echoEvent("onAggiungiIstruzione",event.getTarget(),null);
	}
	
	public void onAggiungiIstruzione(Event event){
		Istruzione istruzioneRef = null;
		try{
			Istruzione istruzione = new Istruzione();
			istruzione.setTitoloStudi(txt_titoloStudio.getValue());
			istruzione.setData(dta_data_conseguimento.getValue());
			this.listaIstruzione.add(istruzione);
			istruzioneRef = istruzione;
			
			this.aggiungiIstruzione();
		}catch(Exception ex){
			if (this.listaIstruzione!=null && istruzioneRef!=null){
				this.listaIstruzione.remove(istruzioneRef);
			}
			throw new WrongValueException("Errore nel salvataggio dei dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
			Clients.alert("Salvataggio preso in consegna dal sistema. Al pi� presto verr� aggiornato.");
			//Executions.sendRedirect("istruzione2.zul");
		}
		
	}
	
	/**
	 * Metodo che provvede all'inserimento di una nuova istruzione 
	 */
	private void aggiungiIstruzione() throws Exception{
		boolean esito = false;
		
		ICommand command = new SalvaIstruzioneCommand(this.user.getId(), this.listaIstruzione);
		esito = command.execute();
		
		if (!esito){
			throw new Exception("non � possibile eseguire l'operazione di inserimento dei nuovi dati");
		}
		
		this.initModel(true);
	}
	
	//primo evento generato al clic su un pulsante di rimozione istruzione
	public void onVerificaRimuoviIstruzione(ForwardEvent event){
		try{
			Row rigaAttiva = (Row)event.getOrigin().getTarget().getParent();
			
			final Istruzione istruzioneSelezionata = (Istruzione) rigaAttiva.getValue();
			String titolo = istruzioneSelezionata.getTitoloStudi();
	       
			Messagebox.show("Procedere con l'eliminazione di '"+titolo+"' ?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
	        new org.zkoss.zk.ui.event.EventListener() {
	            public void onEvent(Event event) throws InterruptedException {
	                if (event.getName().equals("onOK")) {	                	
	                	Clients.showBusy("validazione in corso... ");
	            		Events.echoEvent("onRimuoviIstruzione",Path.getComponent("/win_istruzione"),istruzioneSelezionata);
	                } else {
	                    
	                }
	            }
	        });
		}catch(WrongValueException ex){
			throw ex;
		}catch(Exception ex){
			
		}
	}
	
	public void onRimuoviIstruzione(Event event){	
		boolean esito = false;
		try{
			Istruzione istruzioneSelezionata = (Istruzione) event.getData();
			
			ICommand command = new CancellaIstruzioneCommand(istruzioneSelezionata.getId());
			esito = command.execute();
			
			if (!esito){
				throw new Exception("non � possibile eseguire l'operazione di rimozione del record selezionato");
			}
			
			this.initModel(true);

		}catch(Exception ex){
			throw new WrongValueException("Errore nella validazione e salvataggio dei Dati: " + ex.getMessage());
		}finally{
			Clients.clearBusy();
		}
		
	}
}