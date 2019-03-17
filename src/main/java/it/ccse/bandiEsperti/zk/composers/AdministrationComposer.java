package it.ccse.bandiEsperti.zk.composers;

import java.io.Serializable;
import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;

public interface AdministrationComposer {

	java.io.Serializable getItemSelezionato();

	void onClick$bnt_modifica(Event event);

	Serializable populateSelectItem(java.io.Serializable serializable);
	
	void onClick$bnt_aggiorna(Event event);

	void onMod(Event event);

	void onModifica(ForwardEvent event);

	void loadValoriDefault();

	void initModel(boolean isUpdate) throws Exception;

	void onClick$bnt_aggiungi(Event event);

	void onAggiungi(Event event);

	void onVerificaRimuovi(ForwardEvent event);

	void onRimuovi(Event event);
	
	Set<Serializable> getLista();
	

}
