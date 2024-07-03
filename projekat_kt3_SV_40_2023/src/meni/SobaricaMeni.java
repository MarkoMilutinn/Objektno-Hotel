package meni;

import java.util.ArrayList;

import entiteti.Osoba;
import entiteti.Soba;
import entiteti.Sobarica;
import main.Main;
import manage.ManagerOsoba;
import manage.ManagerSoba;
import manage.ManagerSobarica;

public class SobaricaMeni extends Meni {
	
	public SobaricaMeni(Osoba ulogovaniKorisnik) {
		super(ulogovaniKorisnik);
	}

	@Override
	public boolean run() {
		while (true) {
            System.out.println("0. Izlazak iz aplikacije");
            System.out.println("1. Odjava");
            System.out.println("2. Pregledaj svoje sobe");
            System.out.println("3. Oznaci sobu kao ociscenu");
           

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
                pregledajSobe();
                break;
            case "3":
                oznaciKaoCiscenu();
                break;
            
            default:
                System.out.println("Nepostojeca opcija");
            }
        }
    }
	
	private void pregledajSobe() {
		Sobarica s = (Sobarica) ulogovaniKorisnik;
		System.out.println("Pregled svih soba");
		ArrayList<Soba> sobe = s.getSoba();
		
		if (sobe.isEmpty()) {
			System.out.println("Nemate soba");
			return;
		}
		
		for (int i = 0; i < sobe.size(); i++) {
			System.out.println((i + 1) + ". " + sobe.get(i).toString());
		}
	}
	
	private void oznaciKaoCiscenu() {
		Sobarica s = (Sobarica) ulogovaniKorisnik;
		System.out.println("Pregled svih soba");
		ArrayList<Soba> sobe = s.getSoba();
		
		if (sobe.isEmpty()) {
			System.out.println("Nemate soba");
			return;
		}
		
		for (int i = 0; i < sobe.size(); i++) {
			System.out.println((i + 1) + ". " + sobe.get(i).toString());
		}

		System.out.println("Unesite redni broj sobe koju zelite da oznacite kao ociscenu");
		int redniBroj = Main.sc.nextInt();
		Main.sc.nextLine();

		if (redniBroj < 1 || redniBroj > sobe.size()) {
			System.out.println("Nepostojeci redni broj");
			return;
		}

		Soba so = sobe.get(redniBroj - 1);
		ManagerSobarica.getInstance().obrisiSobuSobarici(s, so);
		ManagerOsoba.getInstance().sacuvajOsobe();
		ManagerSoba.getInstance().snimiSobe();
		System.out.println("Soba je oznacena kao ociscena");
	}
	
}
