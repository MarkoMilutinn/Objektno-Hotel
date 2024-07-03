package entiteti;

public enum NivoStrucnosti {
    OSNOVNA(1), SREDNJA(2), VISOKA(3), DOKTOR(4);
    int nivo;

    
    private NivoStrucnosti(int nivo) {
        this.nivo = nivo;
    }

    public static NivoStrucnosti getNivo(int nivo) {
        switch (nivo) {
            case 1:
                return OSNOVNA;
            case 2:
                return SREDNJA;
            case 3:
                return VISOKA;
            case 4:
                return DOKTOR;
            default:
                return null;
        }
    }

    public int getRedniBrojNivoa() {
        return nivo;
    }

    public void setNivo(int nivo) {
        this.nivo = nivo;
    }
    
	public String getNivo() {
		switch (nivo) {
		case 1:
			return "Osnovna";
		case 2:
			return "Srednja";
		case 3:
			return "Visoka";
		case 4:
			return "Doktor";
		default:
			return null;
		}
	}
    
    
    
}
