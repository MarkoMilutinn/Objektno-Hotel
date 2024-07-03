package entiteti;

public enum StatusSobe {
    SLOBODNA(1), ZAUZETA(2), SPREMANJE(3);
    int status;

    private StatusSobe(int status) {
        this.status = status;
    }

    public static StatusSobe getStatus(int status) {
        switch (status) {
            case 1:
                return SLOBODNA;
            case 2:
                return ZAUZETA;
            case 3:
                return SPREMANJE;
            default:
                return null;
        }
    }


    public int getRedniBrojStatusa() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
	public String toString() {
		switch (status) {
		case 1:
			return "Slobodna";
		case 2:
			return "Zauzeta";
		case 3:
			return "Spremanje";
		default:
			return null;
		}
	}
    
   
    
}
