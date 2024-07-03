package entiteti;

public class Admin extends Osoba{


	public Admin(int id,String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		super(id,ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		this.setTip(3);
	}

	
	
	
}
