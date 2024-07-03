package meni;

import entiteti.Osoba;

public class AdminMeni extends Meni {
	public AdminMeni(Osoba ulogovaniKorisnik) {
		super(ulogovaniKorisnik);
	}

	@Override
	public boolean run() {
		System.out.println("ADMINNNNN");
		return true;
	}
}
