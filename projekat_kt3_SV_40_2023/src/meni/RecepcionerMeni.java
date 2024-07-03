package meni;

import java.util.ArrayList;

import entiteti.Osoba;
import entiteti.Rezervacije;
import entiteti.StatusRezervacije;
import kontroleri.RezervacijaKontroler;
import main.Main;
import manage.ManagerRezervacije;

public class RecepcionerMeni extends Meni {
	public RecepcionerMeni(Osoba ulogovaniKorisnik) {
		super(ulogovaniKorisnik);
	}

	@Override
	public boolean run() {
		while (true) {
			System.out.println("0. Izlazak iz aplikacije");
			System.out.println("1. Odjava");
			System.out.println("2. Dodaj novog gosta");
			System.out.println("3. Dodaj novu rezervaciju");
			System.out.println("4. Pregledaj sve rezervacije");
			System.out.println("5. Potvrdi rezervaciju");
			System.out.println("6. Otkazi rezervaciju");
			System.out.println("7. Check in");
			System.out.println("8. Check out");

			System.out.print("Unesite opciju: ");
			String opcija = Main.sc.nextLine();

			switch (opcija) {
			case "0":
				System.out.println("Izlazak iz aplikacije");
				return false;
			case "1":
				System.out.println("Odjava");
				return true;

			case "2":
				dodajGosta();
				break;

			case "3":
				dodajRezervaciju();
				break;

			case "4":
				prikaziRezervacije();
				break;
			case "5":
				potvrdiRezervaciju();
				break;
			case "6":
				otkaziRezervaciju();
				break;
			case "7":
				checkIn();
				break;

			case "8":
				checkOut();
				break;
			default:
				System.out.println("Nepostojeca opcija");
			}

		}

	}

	private void otkaziRezervaciju() {
		
		
	}

	private void potvrdiRezervaciju() {
		ArrayList<Rezervacije> sveRezervacije = ManagerRezervacije.getInstance().getRezervacije();
		ArrayList<Rezervacije> nepotvrdjeneRezervacije = new ArrayList<Rezervacije>();
		for (Rezervacije r : sveRezervacije) {
			if (r.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)) {
				nepotvrdjeneRezervacije.add(r);
			}
		}
		if (nepotvrdjeneRezervacije.size() == 0) {
			System.out.println("Nema nepotvrdjenih rezervacija");
			return;
		}
		for (int i = 0; i < nepotvrdjeneRezervacije.size(); i++) {
			System.out.println((i + 1) + ". " + nepotvrdjeneRezervacije.get(i).toString());
		}
		while (true) {
			System.out.print("Unesite redni broj rezervacije koju želite da potvrdite: ");
			String redniBrojString = Main.sc.nextLine();
			try {
				int redniBroj = Integer.parseInt(redniBrojString);
				if (redniBroj < 1 || redniBroj > nepotvrdjeneRezervacije.size()) {
					throw new Exception();
				}
				RezervacijaKontroler.getInstance().potvrdiRezervaciju(nepotvrdjeneRezervacije.get(redniBroj - 1));
				break;
			} catch (Exception e) {
				System.out.println("Nepostojeca opcija");
			}
		}
		
	}

	private void checkOut() {
		ArrayList<Rezervacije> sveRezervacije = ManagerRezervacije.getInstance().getRezervacije();
		ArrayList<Rezervacije> checkInRezervacije = new ArrayList<Rezervacije>();
		for (Rezervacije r : sveRezervacije) {
			if (r.getSoba() != null) {
				checkInRezervacije.add(r);
				
			}
		}
		if (checkInRezervacije.size() == 0) {
			System.out.println("Nema soba koje su check inovane");
			return;
		}
		for (int i = 0; i < checkInRezervacije.size(); i++) {
			System.out.println((i + 1) + ". " + checkInRezervacije.get(i).toString());
		}
		while (true) {
			System.out.print("Unesite redni broj rezervacije koju želite da otkažete: ");
			String redniBrojString = Main.sc.nextLine();
			try {
				int redniBroj = Integer.parseInt(redniBrojString);
				if (redniBroj < 1 || redniBroj > checkInRezervacije.size()) {
					throw new Exception();
				}
				RezervacijaKontroler.getInstance().checkOut(checkInRezervacije.get(redniBroj - 1));
				break;
			} catch (Exception e) {
				System.out.println("Nepostojeca opcija");
			}
		}
		
		

	}

	private void checkIn() {
		ArrayList<Rezervacije> rezervacije = ManagerRezervacije.getInstance().getRezervacije();
		ArrayList<Rezervacije> potvrdjeneRezervacije = new ArrayList<Rezervacije>();
		for (Rezervacije r : rezervacije) {
			if (r.getStatusRezervacije().equals(StatusRezervacije.POTVRDJENA)) {
				potvrdjeneRezervacije.add(r);
			}
		}
		if (potvrdjeneRezervacije.size() == 0) {
			System.out.println("Nema potvrdjenih rezervacija");
			return;
		}
		for (int i = 0; i < potvrdjeneRezervacije.size(); i++) {
			System.out.println((i + 1) + ". " + potvrdjeneRezervacije.get(i).toString());
		}
		while (true) {
			System.out.print("Unesite redni broj rezervacije koju želite da checkinujete: ");
			String redniBrojString = Main.sc.nextLine();
			try {
				int redniBroj = Integer.parseInt(redniBrojString);
				if (redniBroj < 1 || redniBroj > potvrdjeneRezervacije.size()) {
					throw new Exception();
				}
				RezervacijaKontroler.getInstance().checkIn(potvrdjeneRezervacije.get(redniBroj - 1));
				break;
			} catch (Exception e) {
				System.out.println("Nepostojeca opcija");
			}
		}

	}

	private void prikaziRezervacije() {
		ArrayList<Rezervacije> rezervacije = ManagerRezervacije.getInstance().getRezervacije();
		for (int i = 0; i < rezervacije.size(); i++) {
			System.out.println((i + 1) + ". " + rezervacije.get(i).toString());
		
		}

	}

	private void dodajRezervaciju() {
		// TODO Auto-generated method stub

	}

	private void dodajGosta() {
		// TODO Auto-generated method stub

	}
}
