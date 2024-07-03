package entiteti;

public class Recepcionar extends Zaposleni{

	private double plata;
	
    public Recepcionar(int id,String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, NivoStrucnosti nivoStrucnosti, int staz, int osnovica,double plata) {
		super(id,ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, nivoStrucnosti, staz,
				osnovica);
	
		this.plata=plata;
		this.setTip(4);
	}
	
	
	
	
	public double getPlata() {
		return plata;
	}

	public void setPlata(double plata) {
		this.plata = plata;
	}

	@Override
	public String toString() {
		return "Recepcionar [Nivostrucnosti: " + getNivoStrucnosti() + ", staz: " + getStaz() + ", Ime: "
				+ getIme() + ", Prezime : " + getPrezime() + "]";
	}


    
    
    
    
	
	
}
