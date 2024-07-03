package entiteti;

public enum OsobinaSobe {
	KLIMA (1), TV (2),BALKON (3),PUSACKA (4),NEPUSACKA (5);
	int osobina;
	
	private OsobinaSobe(int osobina) {
		this.osobina = osobina;
	}
	
	public static OsobinaSobe getOsobina(int osobina) {
		switch (osobina) {
		case 1:
			return KLIMA;
		case 2:
			return TV;
		case 3:
			return BALKON;
		case 4:
			return PUSACKA;
		case 5:
			return NEPUSACKA;
		default:
			return null;
		}
	}
	
	public int getRedniBrojOsobine() {
		return osobina;
	}
	
	public void setOsobina(int osobina) {
		this.osobina = osobina;
	}
	
	public String getStringOsobina() {
		switch (osobina) {
		case 1:
			return "Klima";
		case 2:
			return "TV";
		case 3:
			return "Balkon";
		case 4:
			return "Pusacka";
		case 5:
			return "Nepusacka";
		default:
			return null;
		}
	}
	
}
