package entiteti;

public class Zaposleni extends Osoba {
	private NivoStrucnosti nivoStrucnosti;
    private int staz;
    private int osnovica;
	public Zaposleni(int id,String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, NivoStrucnosti nivoStrucnosti, int staz, int osnovica) {
		super(id,ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		this.nivoStrucnosti = nivoStrucnosti;
		this.staz = staz;
		this.osnovica = osnovica;
		
		
	}
	public NivoStrucnosti getNivoStrucnosti() {
		return nivoStrucnosti  ;
	}
	public void setNivoStrucnosti(NivoStrucnosti nivoStrucnosti) {
		this.nivoStrucnosti = nivoStrucnosti;
	}
	public int getStaz() {
		return staz;
	}
	public void setStaz(int staz) {
		this.staz = staz;
	}
	public int getOsnovica() {
		return osnovica;
	}
	public void setOsnovica(int osnovica) {
		this.osnovica = osnovica;
	}
    
    

}
