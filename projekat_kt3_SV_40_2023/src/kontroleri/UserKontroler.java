package kontroleri;

import java.util.ArrayList;

import entiteti.Osoba;
import manage.ManagerOsoba;

public class UserKontroler {

	private static UserKontroler instance;

	private UserKontroler() {
		super();
	}

	public static UserKontroler getInstance() {
		if (instance == null) {
			instance = new UserKontroler();
		}
		return instance;
	}

	public Osoba login(String korisnicko, String lozinka) {
		ManagerOsoba mo = ManagerOsoba.getInstance();
		ArrayList<Osoba> osobe = mo.getOsobe();
		for (int i = 0; i < osobe.size(); i++) {
			Osoba o = osobe.get(i);
//			System.out.println(o.getTip());
//			System.out.println("lol svaka osoba");
			if (o.getKorisnickoIme().equals(korisnicko) && o.getLozinka().equals(lozinka)) {
				System.out.println(o.getTip());
				return o;
			}
		}
		return null;
	}
	
	
	

}
