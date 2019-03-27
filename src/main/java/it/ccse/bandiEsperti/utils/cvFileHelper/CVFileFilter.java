package it.ccse.bandiEsperti.utils.cvFileHelper;

import it.ccse.bandiEsperti.business.dto.EspertoDTO;

import java.io.File;
import java.io.FileFilter;

public class CVFileFilter implements FileFilter {
	private String baseName;
	public CVFileFilter(EspertoDTO dto) {
		String nome = dto.getNome();
		String cognome = dto.getCognome();
		nome = nome.replace(" ", "");
		nome = nome.replace("'", "");
		cognome = cognome.replace(" ", "");
		cognome = cognome.replace("'", "");
		baseName = cognome+nome+"_"+dto.getCodiceDomanda()+"_";
	}

	@Override
	public boolean accept(File pathname) {
		
		return pathname.isFile() && pathname.getName().startsWith(baseName);
	}
}
