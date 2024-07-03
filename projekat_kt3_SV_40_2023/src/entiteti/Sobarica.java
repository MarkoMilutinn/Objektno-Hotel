package entiteti;

import java.util.ArrayList;
import java.util.List;

public class Sobarica extends Zaposleni{

    private double plata;
    private ArrayList<Soba> soba;

    

    public Sobarica(int id,String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, NivoStrucnosti nivoStrucnosti, int staz, int osnovica,double plata,
			ArrayList<Soba> soba) {
		super(id,ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, nivoStrucnosti, staz,
				osnovica);
		this.soba = soba;
		this.setPlata(plata);
		this.setTip(2);
	}

	

    public Sobarica(int id,String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, NivoStrucnosti nivoStrucnosti, int staz, int osnovica,double plata) {
		super(id,ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, nivoStrucnosti, staz,
				osnovica);

		this.plata=plata;
		this.soba= new ArrayList<Soba>();
		this.setTip(2);
		
	}

    


    
//    public Sobarica(int id, String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
//			String korisnickoIme, String lozinka, NivoStrucnosti nivoStrucnosti, int staz, int osnovica, double plata,
//			ArrayList<Soba> soba) {
//		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, nivoStrucnosti, staz,
//				osnovica);
//		this.plata = plata;
//		this.soba = soba;
//	}



	public ArrayList<Soba> getSoba() {
        return this.soba;
        }
    

    public void setSoba(ArrayList<Soba> soba) {
        this.soba = soba;
    }

    

    public double getPlata() {
		return plata;
	}



	public void setPlata(double plata) {
		this.plata = plata;
	}



	@Override
    public String toString() {
        return "sobarica:[id=" + getId() + ", ime=" + getIme() + ", prezime=" + getPrezime() + ", pol=" + getPol()
                + ", datumRodjenja=" + getDatumRodjenja() + ", telefon=" + getTelefon() + ", adresa=" + getAdresa() + ", tip"+ getTip()
                + ", korisnickoIme=" + getKorisnickoIme() + ", lozinka=" + getLozinka() + ", nivoStrucnosti=" + getNivoStrucnosti() + ", staz=" + getStaz() + "]";
    }

   

	public void dodajSobu(Soba s) {
        soba.add(s);
    }

    public void obrisiSobu(Soba s) {
        soba.remove(s);
    }

    

}
