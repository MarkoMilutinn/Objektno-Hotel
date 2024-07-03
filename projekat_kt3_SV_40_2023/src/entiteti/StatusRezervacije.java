package entiteti;

public enum StatusRezervacije {

    NA_CEKANJU(1), POTVRDJENA(2), ODBIJENA(3), OTKAZANA(4), ZAVRSENA(5), TRENUTNA(6);
    int status;

    private StatusRezervacije(int status) {
        this.status = status;
    }

    public static StatusRezervacije getStatus(int status) {
        switch (status) {
            case 1:
                return NA_CEKANJU;
            case 2:
                return POTVRDJENA;
            case 3:
                return ODBIJENA;
            case 4:
                return 	OTKAZANA;
            case 5:
            	return ZAVRSENA;
            case 6:
            	return TRENUTNA;
                
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
}
