package manage;




import java.util.ArrayList;
import java.util.List;

import entiteti.Admin;
import entiteti.Cenovnik;
import entiteti.DodatneUsluge;
import entiteti.Gost;
import entiteti.NivoStrucnosti;
import entiteti.Osoba;
import entiteti.Pol;
import entiteti.Recepcionar;
import entiteti.Rezervacije;
import entiteti.Soba;
import entiteti.Sobarica;
import entiteti.StatusSobe;
import entiteti.TipSobe;

public class ManagerAdmin {

		private ArrayList<Admin> admin;
		private ManagerOsoba managerOsoba;
		private static ManagerAdmin instance;
		
		
		private ManagerAdmin(ArrayList<Admin> admin) {
			super();
			this.admin = admin;
		}
		
		public static ManagerAdmin getInstance() {
			if (instance == null) {
				instance = new ManagerAdmin(new ArrayList<Admin>());
				instance.managerOsoba = ManagerOsoba.getInstance();
				instance.admin = instance.managerOsoba.getAdmini();
			}
			return instance;
		}
		
		
		
		
		
		
		public int dobijanjeIdAdmina() {
			if (admin.size() == 0) {
				return 4;
			}
			Admin neki = admin.get(admin.size() - 1);
			return (neki.getId() + 4);
		}
		
		public Admin pronadjiAdminaPoKorisnickom(String korisnicko) {
			Osoba a = managerOsoba.pronadjiOsobuPoKorisnickom(korisnicko);
			if (a instanceof Admin) {
				return (Admin) a;
			}
			return null;
		}
		
		
		
		public Admin pronadjiAdminaPoId(int id) {
			Osoba a = managerOsoba.pronadjiOsobuPoId(id);
			if (a instanceof Admin) {
				return (Admin) a;
			}
			return null;
		}
		
		public ArrayList<Admin> getAdmini() {
			return admin;
		}
		
		
		public void dodajAdmina(String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
                String korisnickoIme, String lozinka) {
			int id = dobijanjeIdAdmina();
			Admin a = new Admin(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
			managerOsoba.dodajOsobu(a);
			admin.add(a);
		}
		
		
		public void dodajAdmina(Admin a) {
			managerOsoba.dodajOsobu(a);
			admin.add(a);
		}
		
		
		public void obrisiAdmina(int id) {
			Admin a = pronadjiAdminaPoId(id);
			if (a != null) {
				admin.remove(a);
				managerOsoba.brisanjeOsobe(id);
			}
		}
		
		public void izmenaAdmina(int id, String ime, String prezime, Pol pol, String datumRodjenja, String telefon,
				String adresa, String korisnickoIme, String lozinka) {
			Osoba a = managerOsoba.pronadjiOsobuPoId(id);
			a.setIme(ime);
			a.setPrezime(prezime);
			a.setPol(pol);
			a.setDatumRodjenja(datumRodjenja);
			a.setTelefon(telefon);
			a.setAdresa(adresa);
			a.setKorisnickoIme(korisnickoIme);
			a.setLozinka(lozinka);
			managerOsoba.sacuvajOsobe();

		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
