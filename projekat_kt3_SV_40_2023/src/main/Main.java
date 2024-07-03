package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import entiteti.NivoStrucnosti;
import entiteti.Osoba;
import entiteti.Pol;
import kontroleri.UserKontroler;
import manage.ManagerAdmin;
import manage.ManagerCenovnik;
import manage.ManagerDodatneUsluge;
import manage.ManagerGost;
import manage.ManagerRecepcionar;
import manage.ManagerSoba;
import manage.ManagerSobarica;
import manage.ManagerStavkeCenovnika;
import manage.ManagerTipSobe;
import meni.AdminMeni;
import meni.GostMeni;
import meni.Meni;
import meni.RecepcionerMeni;
import meni.SobaricaMeni;
import view.StartView;

public class Main {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		boolean gui = true;
		//gui = false;
//		posle prvog pokretanja aplikacije, komentarisati innit() metodu
//		innit();
		if (gui) {
			StartView frame = new StartView();
			frame.setVisible(true);
		}
		else {
			cli();
		}
		

//		managerGost je sta gost moze, ManagerRecepcionar sta recepcionar moze, ManagerAdmin sta admin moze

//		ManagerGost gost = new ManagerGost (rezervacije,gosti,tipSobe);
//		Admin a = new Admin("Pera","Peric","m","12.12.1980.", "069888888","Adresa aaa", "peraPlanina","lozinka",1);
//		ManagerOsoba managerOsoba = new ManagerOsoba(a);
//		gost.dodajGosta(new Gost(), managerOsoba.getLargestId());
//		ManagerAdmin admin = new ManagerAdmin(recepcionari,sobarice,sobe,tipSobe,gost,dodatneUsluge,cenovnik);
//		
////		ovde admin dodaje zaposlene
//		
//		admin.dodavanjeSobarice("Jana", "Janic", "z", "12.12.1999.", "088988989", "Adresa b", "janica", "lozinkaneka", NivoStrucnosti.OSNOVNA, 3, 30000);
//		
//		admin.dodavanjeRecepcionara("Mika", "Mikic", "m", "12.1.1999.", "023988989", "Adresa c", "mikica", "lozinkaneka", NivoStrucnosti.SREDNJA, 3, 70000);
//		
//		admin.dodavanjeRecepcionara("Nikola", "Nikolic", "m", "12.1.1999.", "023988989", "Adresa c", "nkica", "lozinkaNeka", NivoStrucnosti.SREDNJA, 3, 70000);
//		
////		ovde su kodovi za proveru dodavanja
//		
//		for(Sobarica s : sobarice) {
//			System.out.println(s.toString());
//		}
//		
//		
//		
//		for(int i=0; i<recepcionari.size();i++) {
//			Recepcionar r = recepcionari.get(i);
//			System.out.println(r.toString());			
//			System.out.println();
//			
//		}
//		Recepcionar brisanje= admin.pronadjiRecepcionaraPoKorisnickom("nkica");
//		admin.brisanjeRecepcionara(brisanje.getId());
//		
////		za proveru brisanja
//		
////		for(int i=0; i<recepcionari.size();i++) {
////			Recepcionar r = recepcionari.get(i);
////			System.out.println(r.toString()); 
////			System.out.println();
////			
////		}
//		
//		ManagerRecepcionar recepcionar = new ManagerRecepcionar(gosti);
//		
//		recepcionar.dodavanjeGosta("Milica", "Milic", "z", "12.12.1990.", "011188989", "Adresa f", "Mica", "pasword");
//		recepcionar.dodavanjeGosta("Ana", "Anic", "z", "12.12.1950.", "022188989", "Adresa l", "anci", "paswword");
//		
//		admin.dodavanjeTipaSobe("jednokrevetna", "1");
//		admin.dodavanjeSoba(2, admin.getManagerGost().pronasaoTip("jednokrevetna", "1"));
//		
//		admin.dodavanjeTipaSobe("dvokrevetna", "2");
//		admin.dodavanjeSoba(2, admin.getManagerGost().pronasaoTip("dvokrevetna", "2"));
//		
//		admin.dodavanjeTipaSobe("dvokrevetna", "1+1");
//		admin.dodavanjeSoba(2, admin.getManagerGost().pronasaoTip("dvokrevetna", "1+1"));
//		
//		admin.dodavanjeTipaSobe("trokrevetna", "2+1");
//		admin.dodavanjeSoba(2, admin.getManagerGost().pronasaoTip("trokrevetna", "2+1"));
//		
//		
////		for (int i=0; i< sobe.size();i++) {
////			Soba s = sobe.get(i);
////			System.out.println(s.toString());
////			System.out.println();
////		}
//		
////		   prvi parametar jeste soba dobijamo tako sto preko tipa trazimo
//		admin.izmenaSobe(admin.pronasaoSobuPoTipu(admin.getManagerGost().pronasaoTip("dvokrevetna", "2")), admin.getManagerGost().pronasaoTip("trokrevetna", "2+1"), StatusSobe.SLOBODNA);
//		
////		kod za proveru 
//		
////		for (int i=0; i< sobe.size();i++) {
////			Soba s = sobe.get(i);
////			System.out.println(s.toString());
////			System.out.println();
////		}
//		
//		admin.dodavanjeDodatneUsluge("dorucak");
//		admin.dodavanjeDodatneUsluge("rucak");
//		admin.dodavanjeDodatneUsluge("vecera");
//		admin.dodavanjeDodatneUsluge("bazen");
//		admin.dodavanjeDodatneUsluge("spa centar");
//		
////		for (DodatneUsluge u:dodatneUsluge) {
////			System.out.println(u.toString());
////			System.out.println();
////		}
//		admin.brisanjeDodatneUsluge(admin.pronasaoDodatnuUsluguPoNazivu("spa centar"));
//		
////		kod za proveru
//		
//		
////		for (DodatneUsluge u:dodatneUsluge) {
////			System.out.println(u.toString());
////			System.out.println();
////		}
//		
//		admin.dodavanjeCenovnikaTipSoba("01.01.2024.", "13.12.2024.", 4000);
//		admin.dodavanjeCenovnikaDodatnihUsluga("01.01.2024.", "13.12.2024.", 200);
//		
////		for (DodatneUsluge u:dodatneUsluge) {
////			System.out.println(u.toString());
////			System.out.println();
////		}
//		
//		admin.izmenaCeneUsluge("dorucak", 150);
//		
////		for (DodatneUsluge u:dodatneUsluge) {
////			System.out.println(u.toString());
////			System.out.println();
////		}
//		
//		System.out.println("Prikaz slobodnih tipova sobe za odabran datum (01.08.2024.-31.08.2024.) :");
//		admin.getManagerGost().prikazSlobodnihTipovaSobe( "01.08.2024.", "31.08.2024.", sobe, tipSobe);
//		System.out.println();
//		
//		Gost milica = admin.getManagerGost().pronadjiGostaPoKorisnickom("Mica");
//		List<DodatneUsluge> uslugeNeke = new ArrayList<DodatneUsluge>();
//		uslugeNeke.add(admin.pronasaoDodatnuUsluguPoNazivu("dorucak"));
//		uslugeNeke.add(admin.pronasaoDodatnuUsluguPoNazivu("vecera"));
//		
//		admin.getManagerGost().rezervisanje(admin.getManagerGost().pronasaoTip("trokrevetna", "2+1"),
//				milica, "13.08.2024.", "23.08.2024.", uslugeNeke);
//		
//		System.out.println("Prikaz slobodnih tipova sobe za odabran datum (01.06.2024.-30.06.2024.) :");
//		admin.getManagerGost().prikazSlobodnihTipovaSobe( "01.06.2024.", "30.06.2024.", sobe, tipSobe);
//		System.out.println();
//		
//		Gost ana = admin.getManagerGost().pronadjiGostaPoKorisnickom("anci");
//		List<DodatneUsluge> usluge= new ArrayList<DodatneUsluge>();
//		admin.getManagerGost().rezervisanje(admin.getManagerGost().pronasaoTip("dvokrevetna", "1+1"),
//				ana, "06.06.2024.", "12.06.2024.", usluge);
//		
//		
//		System.out.println("Prikaz svih rezervacija izabranog gosta (Milice) :");
//		admin.getManagerGost().prikazivanjeRezervacija(milica);
//		
//		

	}
	private static void cli() {
		while (true) {
			System.out.print("Unesite korisnicko ime: ");
			String korisnicko = sc.nextLine();
			System.out.print("Unesite lozinku: ");
			String lozinka = sc.nextLine();

			Osoba o = UserKontroler.getInstance().login(korisnicko, lozinka);

			if (o == null) {
				System.out.println("Pogresno korisnicko ime ili lozinka");
				continue;
			}
			System.out.println("Uspesno ste se ulogovali");
			Meni meni = new Meni(o);
			System.out.println(o.getTip());
			switch (o.getTip()) {
			case 3:
				meni = new AdminMeni(o);
				break;
			case 1:
				meni = new GostMeni(o);
				break;
			case 4:
				meni = new RecepcionerMeni(o);
				break;
			case 2:
				meni = new SobaricaMeni(o);
				break;
			default:
				System.out.println("Nepoznata uloga");
				break;
			}
			if (!meni.run()) break;
		}
	}
	static void innit() {
		ManagerAdmin.getInstance().dodajAdmina("Pera","Peric",Pol.MUSKI,"12.12.1980.", "069888888","Adresa aaa", "peraPlanina","lozinka");
		ManagerSobarica.getInstance().dodajSobaricu("Jana", "Janic", Pol.ZENSKI, "12.12.1999.", "088988989", "Adresa b", "janica", "lozinkaneka", NivoStrucnosti.OSNOVNA, 3, 30000);
		ManagerGost.getInstance().dodajGosta("Milica", "Milic", Pol.ZENSKI, "12.12.1990.", "011188989", "Adresa f", "Mica", "pasword");
		ManagerGost.getInstance().dodajGosta("Ana", "Anic", Pol.ZENSKI, "12.12.1950.", "022188989", "Adresa l", "anci", "paswword");
		ManagerRecepcionar.getInstance().dodajRecepcionara("Mika", "Mikic", Pol.MUSKI, "12.1.1999.", "023988989", "Adresa c", "mikica", "lozinkaneka", NivoStrucnosti.SREDNJA, 3, 70000);
        ManagerTipSobe.getInstance().dodajTipSobe("jednokrevetna", "1",2000);
        ManagerTipSobe.getInstance().dodajTipSobe("dvokrevetna", "2",2000);
        ManagerTipSobe.getInstance().dodajTipSobe("dvokrevetna", "1+1",2000);
        ManagerTipSobe.getInstance().dodajTipSobe("trokrevetna", "2+1",2000);
        ManagerSoba.getInstance().dodajSobu(ManagerTipSobe.getInstance().pronadjiTipSobePoId(1), new ArrayList<>());
        ManagerSoba.getInstance().dodajSobu(ManagerTipSobe.getInstance().pronadjiTipSobePoId(2), new ArrayList<>());
        ManagerSoba.getInstance().dodajSobu(ManagerTipSobe.getInstance().pronadjiTipSobePoId(3), new ArrayList<>());
        ManagerSoba.getInstance().dodajSobu(ManagerTipSobe.getInstance().pronadjiTipSobePoId(4), new ArrayList<>());
        ManagerDodatneUsluge.getInstance().dodajDodatneUsluge("dorucak",100);
        ManagerDodatneUsluge.getInstance().dodajDodatneUsluge("rucak",150);
        ManagerDodatneUsluge.getInstance().dodajDodatneUsluge("vecera",100);
        ManagerDodatneUsluge.getInstance().dodajDodatneUsluge("bazen",100);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy.");
        Date datumOd = null;
        Date datumDo = null;
        try {
            datumOd = format.parse("01.01.2024.");
            datumDo = format.parse("13.12.2024.");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        ManagerCenovnik.getInstance().dodajCenovnik(datumOd, datumDo, ManagerStavkeCenovnika.getInstance().getStavkeCenovnika());	
        
        
	}
}
