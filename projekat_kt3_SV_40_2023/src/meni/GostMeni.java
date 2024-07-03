package meni;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import entiteti.DodatneUsluge;
import entiteti.Gost;
import entiteti.Osoba;
import entiteti.Rezervacije;
import entiteti.StatusRezervacije;
import entiteti.TipSobe;
import kontroleri.RezervacijaKontroler;
import main.Main;
import manage.ManagerDodatneUsluge;
import manage.ManagerTipSobe;

public class GostMeni extends Meni {
	public GostMeni(Osoba ulogovaniKorisnik) {
		super(ulogovaniKorisnik);
	}

	@Override
	public boolean run() {
		while (true) {
			System.out.println("0. Izadjite iz aplikacije");
			System.out.println("1. Odjavi se");
			System.out.println("2. Rezervisi sobu");
			System.out.println("3. Pregledaj svoje rezervacije");
			System.out.println("4. Otkaži rezervaciju");

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
				rezervisiSobu();
				break;
			case "3":
				prikaziRezervacije();
				break;
			case "4":
				otkaziRezervaciju();
				break;
			default:
				System.out.println("Nepostojeca opcija");
			}
		}
	}

	private void rezervisiSobu() {
		TipSobe tipSobe = null;
		LocalDate datumOd = null;
		LocalDate datumDo = null;
		ArrayList<DodatneUsluge> dodatneUsluge = new ArrayList<DodatneUsluge>();
		ArrayList<TipSobe> tipoviSoba = ManagerTipSobe.getInstance().getTipSobe();
		for (int i = 0; i < tipoviSoba.size(); i++) {
			System.out.println((i + 1) + ". " + tipoviSoba.get(i).getTipSobe() + " - " + tipoviSoba.get(i).getLjudi());
		}
		while (true) {
			System.out.print("Unesite redni broj tipa sobe: ");
			String redniBroj = Main.sc.nextLine();
			int redniBrojInt = Integer.parseInt(redniBroj);
			if (redniBrojInt < 1 || redniBrojInt > tipoviSoba.size()) {
				System.out.println("Nepostojeca opcija");
				continue;
			} else {
				tipSobe = tipoviSoba.get(redniBrojInt - 1);
				System.out.println("Izabrali ste tip sobe: " + tipSobe.getTipSobe());
				break;
			}
		}
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.uuuu.");
		while (true) {
			System.out.print("Unesite datum od (dd.MM.yyyy.): ");
			try {
				String datumOdString = Main.sc.nextLine();
				datumOd = LocalDate.parse(datumOdString, formater);
				break;
			} catch (Exception e) {
				System.out.println("Neispravan format datuma");
			}
		}
		while (true) {
			System.out.print("Unesite datum do (dd.MM.yyyy.): ");
			try {
				String datumDoString = Main.sc.nextLine();
				datumDo = LocalDate.parse(datumDoString, formater);
				break;
			} catch (Exception e) {
				System.out.println("Neispravan format datuma");
			}
		}
		ArrayList<DodatneUsluge> dodatneUslugeSve = ManagerDodatneUsluge.getInstance().getDodatneUsluge();
		for (int i = 0; i < dodatneUslugeSve.size(); i++) {
			System.out.println((i + 1) + ". " + dodatneUslugeSve.get(i).getNaziv());
		}

		while (true) {
			System.out.print(
					"Unesite redne brojeve dodatnih usluga odvojene zarezima\nili enter ako ne želite ni jednu: ");
			String dodatneUslugeString = Main.sc.nextLine();
			if (dodatneUslugeString.equals("")) {
				break;
			}
			String[] dodatneUslugeStringNiz = dodatneUslugeString.split(",");
			try {
				for (String dodatnaUsluga : dodatneUslugeStringNiz) {
					int redniBroj = Integer.parseInt(dodatnaUsluga);
					if (redniBroj < 1 || redniBroj > dodatneUslugeSve.size()) {
						throw new Exception();
					}
					dodatneUsluge.add(dodatneUslugeSve.get(redniBroj - 1));
				}
				break;
			} catch (Exception e) {
				System.out.println("Nepostojeca opcija");
				continue;
			}
		}

		RezervacijaKontroler.getInstance().dodajRezervaciju(tipSobe, (Gost) ulogovaniKorisnik, datumOd, datumDo,
				dodatneUsluge, null);
	}

	

	private void prikaziRezervacije() {
        ArrayList<Rezervacije> rezervacije = RezervacijaKontroler.getInstance().rezervacijeGosta((Gost)ulogovaniKorisnik);
        for (Rezervacije rezervacija : rezervacije) {
            System.out.println(rezervacija.toString());
        }
    }
	
	private void otkaziRezervaciju() {
		ArrayList<Rezervacije> Sverezervacije = RezervacijaKontroler.getInstance()
				.rezervacijeGosta((Gost) ulogovaniKorisnik);
		ArrayList<Rezervacije> rezervacije = new ArrayList<Rezervacije>();
		for (Rezervacije r : Sverezervacije) {
			if (r.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)) {
				rezervacije.add(r);
			}
		}
		for (int i = 0; i < rezervacije.size(); i++) {
			System.out.println((i + 1) + ". " + rezervacije.get(i).toString());
		}
		while (true) {
			System.out.print("Unesite redni broj rezervacije koju želite da otkažete: ");
			String redniBrojString = Main.sc.nextLine();
			try {
				int redniBroj = Integer.parseInt(redniBrojString);
				if (redniBroj < 1 || redniBroj > rezervacije.size()) {
					throw new Exception();
				}
				RezervacijaKontroler.getInstance().otkaziRezervaciju(rezervacije.get(redniBroj - 1).getId());
				break;
			} catch (Exception e) {
				System.out.println("Nepostojeca opcija");
			}
		}
		System.out.println("Rezervacija je otkazana");
	}
}
