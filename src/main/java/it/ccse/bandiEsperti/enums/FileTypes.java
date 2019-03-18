package it.ccse.bandiEsperti.enums;

public enum FileTypes {
	
	PDF("pdf");
	private String format;

	private FileTypes(String format) {
		this.format = format;
	}
	
	public String getFormat() {
		return format;
	}
	
	public static boolean searchTypeByFormat(String format)
	{
		FileTypes types[] = FileTypes.values();
		boolean trovato = false;
		for (FileTypes f: types)
		{
			trovato = trovato || f.getFormat().equals(format);
			if (trovato == true)
				break;
		}
		return trovato;
	}
	
}
