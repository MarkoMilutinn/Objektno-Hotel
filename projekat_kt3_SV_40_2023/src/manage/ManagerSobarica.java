package manage;

import java.util.ArrayList;

import entiteti.NivoStrucnosti;
import entiteti.Osoba;
import entiteti.Pol;
import entiteti.Soba;
import entiteti.Sobarica;
import entiteti.StatusSobe;

public class ManagerSobarica {
	private ArrayList<Sobarica> sobarice ;
	private ManagerOsoba managerOsoba;
	private static ManagerSobarica instance;
	
	private ManagerSobarica(ArrayList<Sobarica> sobarice) {
		super();
		this.sobarice = sobarice;
	}
	
	public static ManagerSobarica getInstance() {
		if (instance == null) {
			instance = new ManagerSobarica(new ArrayList<Sobarica>());
			instance.managerOsoba = ManagerOsoba.getInstance();
			instance.sobarice = instance.managerOsoba.getSobarice();
		}
		return instance;
	}
	
	
	public Sobarica pronadjiSobaricuPoKorisnickom(String korisnicko) {
		Osoba s = managerOsoba.pronadjiOsobuPoKorisnickom(korisnicko);
		if (s instanceof Sobarica) {
			return (Sobarica) s;
		}
		return null;
	}
	
	public Sobarica pronadjiSobaricuPoId(int id) {
		Osoba s = managerOsoba.pronadjiOsobuPoId(id);
		if (s instanceof Sobarica) {
			return (Sobarica) s;
		}
		return null;
	}
	
	public void dodajSobaricu(String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
            String korisnickoIme, String lozinka, NivoStrucnosti nivoStrucnosti, int staz, int osnovica) {
		int id = dobijanjeIdSobarice();
		double plata = plataSobarice(staz, osnovica, nivoStrucnosti);
		Sobarica s = new Sobarica(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, nivoStrucnosti, staz, osnovica, plata);
		managerOsoba.dodajOsobu(s);
		sobarice.add(s);
	}
	
	public void dodajSobaricu(Sobarica s) {
		managerOsoba.dodajOsobu(s);
		sobarice.add(s);
	}
	
	public void obrisiSobaricu(int id) {
		Sobarica s = pronadjiSobaricuPoId(id);
		if (s != null) {
			sobarice.remove(s);
			managerOsoba.brisanjeOsobe(id);
		} else {
			System.out.println("Sobarica sa unetim id-jem ne postoji!");
		}
	}
	
	public void izmenaSobarice(int id, String ime, String prezime, Pol pol, String datumRodjenja, String telefon,
			String adresa, String korisnickoIme, String lozinka, NivoStrucnosti nivoStrucnosti, int staz,
			int osnovica) {
		Sobarica s = pronadjiSobaricuPoId(id);
		if (s != null) {
			s.setIme(ime);
			s.setPrezime(prezime);
			s.setPol(pol);
			s.setDatumRodjenja(datumRodjenja);
			s.setTelefon(telefon);
			s.setAdresa(adresa);
			s.setKorisnickoIme(korisnickoIme);
			s.setLozinka(lozinka);
			s.setNivoStrucnosti(nivoStrucnosti);
			s.setStaz(staz);
			s.setOsnovica(osnovica);
			s.setPlata(plataSobarice(staz, osnovica, nivoStrucnosti));
			managerOsoba.sacuvajOsobe();
		} else {
			System.out.println("Sobarica sa unetim id-jem ne postoji!");
		}
	}

	public double plataSobarice(int staz, int osnovica, NivoStrucnosti nivoStrucnosti) {
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
		
	
	public ArrayList<Sobarica> getSobarice() {
		return sobarice;
	}

	public int dobijanjeIdSobarice() {
		if (sobarice.size() == 0) {
			return 2;
		}
		Sobarica neka = sobarice.get(sobarice.size() - 1);
		return (neka.getId() + 4);
	}
	
	
	
	public Sobarica dobijanjeSobariceSaNajmanjeSoba() {
		Sobarica s = null;
		int min =1000;
		for (Sobarica so : sobarice) {
				if (so.getSoba().size() < min) {
					min = so.getSoba().size();
					s = so;
				}
			}
		return s;
	}
	
	public Sobarica dobijanjeSobariceSaNajviseSoba() {
		Sobarica s = null;
		int max = 0;
		for (Sobarica so : sobarice) {
			if (so.getSoba().size() > max) {
				max = so.getSoba().size();
				s = so;
			}
		}
		return s;
	}
	
	public void dodajSobuSobarici(int idSobe) {
		Sobarica s = dobijanjeSobariceSaNajmanjeSoba();
		ManagerSoba managerSoba = ManagerSoba.getInstance();
		Soba so= managerSoba.pronadjiSobuPoId(idSobe);
		if (so != null) {
            s.getSoba().add(so);
            so.setStatusSobe(StatusSobe.SPREMANJE);
        }else {
            System.out.println("Ne postoji ta soba");
        }
		
    }
	
	
	public void dodajSobuSobarici(Soba s) {
		Sobarica sobarica = dobijanjeSobariceSaNajmanjeSoba();
        if (sobarica != null) {
            sobarica.getSoba().add(s);
            s.setStatusSobe(StatusSobe.SPREMANJE);
        } else {
            System.out.println("Ne postoji sobarica");
        }
    }
	
	
	public void obrisiSobuSobarici(Sobarica s,int idSobe) {
		ManagerSoba managerSoba = ManagerSoba.getInstance();
        Soba so= managerSoba.pronadjiSobuPoId(idSobe);
        if (s != null) {
            s.getSoba().remove(so);
            so.setStatusSobe(StatusSobe.SLOBODNA);
        }else {
            System.out.println("Ne postoji ta soba");
        }
    }

	public void obrisiSobuSobarici(Sobarica S, Soba s) {
		S.getSoba().remove(s);
		s.setStatusSobe(StatusSobe.SLOBODNA);
	}
	
	public void obrisisSobuSobarici(int sobarice,int idSobe) {
		Sobarica s = pronadjiSobaricuPoId(sobarice);
		if (s != null) {
			ManagerSoba managerSoba = ManagerSoba.getInstance();
			Soba so = managerSoba.pronadjiSobuPoId(idSobe);
			if (so != null) {
				s.getSoba().remove(so);
				so.setStatusSobe(StatusSobe.SLOBODNA);
			} else {
				System.out.println("Ne postoji ta soba");
			}
		} else {
			System.out.println("Ne postoji sobarica sa tim id");
		}
		
		
	}
	
	
	
}
