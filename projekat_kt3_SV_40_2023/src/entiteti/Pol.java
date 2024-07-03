package entiteti;

public enum Pol {
	MUSKI (1), ZENSKI (2);
	int pol;
	
	private Pol(int pol) {
		this.pol = pol;
	}
	
	public static Pol getPol(int i) {
		switch (i) {
		case 1:
			return MUSKI;
		case 2:
			return ZENSKI;
		default:
			return null;
		}
	}
	
	public int getRedniBrojPol() {
		return pol;
	}
	
	public void setPol(int pol) {
		this.pol = pol;
	}
	
	public String toString() {
		switch (pol) {
		case 1:
			return "Muski";
		case 2:
			return "Zenski";
		default:
			return null;
		}
	}
	
}
