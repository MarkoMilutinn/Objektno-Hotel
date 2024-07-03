package manage;




import java.util.ArrayList;

import java.util.List;

import entiteti.Gost;
import entiteti.NivoStrucnosti;
import entiteti.Osoba;
import entiteti.Pol;
import entiteti.Recepcionar;
import entiteti.Rezervacije;

public class ManagerRecepcionar {
	private ArrayList<Recepcionar> recepcionari;
	private  ManagerOsoba managerOsoba;
	private static ManagerRecepcionar instance;
	
	
	
	private ManagerRecepcionar(ArrayList<Recepcionar> recepcionari) {
		super();
		this.recepcionari = recepcionari;
	}
	
	public static ManagerRecepcionar getInstance() {
		if (instance == null) {
			instance = new ManagerRecepcionar(new ArrayList<Recepcionar>());
			instance.managerOsoba = ManagerOsoba.getInstance();
			instance.recepcionari = instance.managerOsoba.getRecepcionari();
		}
		return instance;
	}


	public int dobijanjeIdRecepcionara() {
		if (recepcionari.size() == 0) {
			return 3;
		}
		Recepcionar neki = recepcionari.get(recepcionari.size() - 1);
		return (neki.getId() + 4);
	}

	public Recepcionar pronadjiRecepcionaraPoKorisnickom(String korisnicko) {
		Osoba r = managerOsoba.pronadjiOsobuPoKorisnickom(korisnicko);
		if (r instanceof Recepcionar) {
			return (Recepcionar) r;
		}
		return null;
	}
	
	public Recepcionar pronadjiRecepcionaraPoId(int id) {
		Osoba r = managerOsoba.pronadjiOsobuPoId(id);
		if (r instanceof Recepcionar) {
			return (Recepcionar) r;
		}
		return null;
	}
	
	public void dodajRecepcionara(String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
            String korisnickoIme, String lozinka,NivoStrucnosti nivoStrucnosti, int staz, int osnovica){
		int id = dobijanjeIdRecepcionara();
		double plata = plataRecepcionara(staz, osnovica, nivoStrucnosti);
		Recepcionar r = new Recepcionar(id,ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, nivoStrucnosti, staz, osnovica,plata);
		managerOsoba.dodajOsobu(r);
		recepcionari.add(r);
	}

	public void dodajRecepcionara(Recepcionar r) {
		managerOsoba.dodajOsobu(r);
		recepcionari.add(r);
	}
	

	public void obrisiRecepcionara(int id) {
		Recepcionar r = pronadjiRecepcionaraPoId(id);
		if (r != null) {
			recepcionari.remove(r);
			managerOsoba.brisanjeOsobe(id);
		} else {
			System.out.println("Ne postoji recepcionar sa tim id");
		}
	}
	
	public ArrayList<Recepcionar> getRecepcionari() {
        return recepcionari;
	}
	
	
	public void izmenaRecepcionara(int id, String ime, String prezime, Pol pol, String datumRodjenja, String telefon,
			String adresa, String korisnickoIme, String lozinka, NivoStrucnosti nivoStrucnosti, int staz, int osnovica, double plata) {
		Recepcionar r = pronadjiRecepcionaraPoId(id);
		if (r == null) {
			System.out.println("Ne postoji recepcionar sa tim id-jem");
			return;
		}else {
		r.setIme(ime);
		r.setPrezime(prezime);
		r.setPol(pol);
		r.setDatumRodjenja(datumRodjenja);
		r.setTelefon(telefon);
		r.setAdresa(adresa);
		r.setKorisnickoIme(korisnickoIme);
		r.setLozinka(lozinka);
		r.setNivoStrucnosti(nivoStrucnosti);
		r.setStaz(staz);
		r.setOsnovica(osnovica);
		r.setPlata(plataRecepcionara(staz, osnovica, nivoStrucnosti));
		managerOsoba.sacuvajOsobe();
		}
	}
	

	public double plataRecepcionara(int staz, int osnovica, NivoStrucnosti nivoStrucnosti) {
		double plata = 0;
		if (nivoStrucnosti.getRedniBrojNivoa()==1) {
			plata = osnovica + (0.05 * osnovica) + (0.02 * staz);
		} else if (nivoStrucnosti.getRedniBrojNivoa()==2) {
			plata = osnovica + (0.1 * osnovica) + (0.03 * staz);
		} else if (nivoStrucnosti.getRedniBrojNivoa()==3) {
			plata = osnovica + (0.15 * osnovica) + (0.05 * staz);
		} else if (nivoStrucnosti.getRedniBrojNivoa() == 4) {
			plata = osnovica + (0.2 * osnovica) + (0.07 * staz);
		}
		return plata;
	}
		
	
	
	
	
}
