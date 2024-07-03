package entiteti;

import java.util.List;

import java.util.ArrayList;

//?import entiteti.Rezervacije;

public class Gost extends Osoba {

	public Gost(int id, String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, List<Rezervacije> rezervacije) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);

		this.setTip(1);
	}

	public Gost(int id, String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		super(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);

		this.setTip(1);
	}

	@Override
	public String toString() {
		return "Gost:[id=" + getId() + ", ime=" + getIme() + ", prezime=" + getPrezime() + ", pol=" + getPol()
				+ ", datumRodjenja=" + getDatumRodjenja() + ", telefon=" + getTelefon() + ", adresa=" + getAdresa()
				+ ", korisnickoIme=" + getKorisnickoIme() + ", lozinka=" + getLozinka() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof Gost) {
			Gost gost = (Gost) obj;
			return gost.getId() == this.getId() && gost.getIme().equals(this.getIme())
					&& gost.getPrezime().equals(this.getPrezime()) && gost.getPol().equals(this.getPol())
					&& gost.getDatumRodjenja().equals(this.getDatumRodjenja())
					&& gost.getTelefon().equals(this.getTelefon()) && gost.getAdresa().equals(this.getAdresa())
					&& gost.getKorisnickoIme().equals(this.getKorisnickoIme())
					&& gost.getLozinka().equals(this.getLozinka());
		}
		return false;
	}

	public String toFileString() {
		String data = "";
		data += String.valueOf(this.getId()) + "," + this.getIme() + "," + this.getPrezime() + ","
				+ this.getPol().toString() + "," + this.getDatumRodjenja() + "," + this.getTelefon() + ","
				+ this.getAdresa() + "," + this.getKorisnickoIme() + "," + this.getLozinka() + ","
				+ String.valueOf(this.getTip()) + "\n";
		return data;
	}

}
