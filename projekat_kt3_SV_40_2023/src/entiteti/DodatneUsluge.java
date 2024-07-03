package entiteti;

public class DodatneUsluge {

    private int id;
    private String naziv;
    

    public DodatneUsluge(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
      
    }
   

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

   

    @Override
	public String toString() {
		return "[Dodatna usluga =" + naziv + "]";
	}
    
    
    @Override
	public boolean equals(Object obj) {
    	if (obj == null) return false;
		if (obj instanceof DodatneUsluge) {
			DodatneUsluge du = (DodatneUsluge) obj;
			return du.getId() == this.id && du.getNaziv().equals(this.naziv);
		}
		return false;
	}
    
    
    
}
