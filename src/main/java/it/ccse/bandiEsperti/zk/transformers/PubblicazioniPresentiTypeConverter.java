package it.ccse.bandiEsperti.zk.transformers;

import it.ccse.bandiEsperti.business.dto.EspertoDTO;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Label;

public class PubblicazioniPresentiTypeConverter implements TypeConverter{

	@Override
	public Object coerceToUi(Object val, Component comp) {
		Label label = (Label) comp;
		
		EspertoDTO espertoSelezionato = (EspertoDTO) val;
		
		return String.valueOf(espertoSelezionato.getNumPubblicazioni());
	}

	@Override
	public Object coerceToBean(Object val, Component comp) {
		return null;
	}
	
}
