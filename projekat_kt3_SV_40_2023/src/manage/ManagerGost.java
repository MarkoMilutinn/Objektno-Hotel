package manage;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entiteti.Rezervacije;
import entiteti.Soba;
import entiteti.Sobarica;
import entiteti.Gost;
import entiteti.Osoba;
import entiteti.Pol;
import entiteti.TipSobe;
import entiteti.DodatneUsluge;


public class ManagerGost{

	private List<Gost> gosti;
	private static ManagerGost instance;
	private  ManagerOsoba managerOsoba;
	
	private ManagerGost(List<Gost> gosti) {
		super();
		this.gosti = gosti;
		managerOsoba = ManagerOsoba.getInstance();
	}
	
	public static ManagerGost getInstance() {
		if (instance == null) {
			instance = new ManagerGost(new ArrayList<Gost>());
			instance.managerOsoba = ManagerOsoba.getInstance();
			instance.gosti = instance.managerOsoba.getGosti();
		}
		return instance;
	}
	
	
	
//	public int dobijanjeId(List<Rezervacije> rezervacije) {
//		if (rezervacije.size()==0) {
//			return 1;
//		}
//		Rezervacije neka= rezervacije.get(rezervacije.size()-1);
//		return (neka.getId()+1);
//	}
//	
	
	
	


	public Gost pronadjiGostaPoKorisnickom(String korisnicko){
		Osoba g= managerOsoba.pronadjiOsobuPoKorisnickom(korisnicko);
		if (g instanceof Gost) {
			return (Gost) g;
		}
		else {
			return null;
		}
	}

	
	public Gost pronadjiGostaPoId(int id) {
		Osoba g= managerOsoba.pronadjiOsobuPoId(id);
		if (g instanceof Gost) {
			return (Gost) g;
		} else {
			return null;
		}
	}
	
	public int dobijanjeIdGosta() {
		if (gosti.size() == 0) {
			return 1;
		}
		Gost neki = gosti.get(gosti.size() - 1);
		return (neki.getId() + 4);
	}
	
	
	public void dodajGosta(Gost gost, int id) {
		gost.setId(dobijanjeIdGosta());
		managerOsoba.dodajOsobu(gost);
		gosti.add(gost);
	}
	
	public void dodajGosta(String ime, String prezime,Pol pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		int id = dobijanjeIdGosta();
		Gost gost = new Gost(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		managerOsoba.dodajOsobu(gost);
		gosti.add(gost);
	}
	
	
	public void brisanjeGosta(int id) {
		Gost gost = pronadjiGostaPoId(id);
		if (gost == null) {
			System.out.println("Gost sa tim id-jem ne postoji");
			return;
		} else {
			managerOsoba.brisanjeOsobe(id);
			gosti.remove(gost);
		}
		
	}
	
	public void izmenaGosta(int id, String ime, String prezime,Pol pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		Gost gost = pronadjiGostaPoId(id);
		if (gost == null) {
			System.out.println("Gost sa tim id-jem ne postoji");
			return;
		}
		gost.setIme(ime);
		gost.setPrezime(prezime);
		gost.setPol(pol);
		gost.setDatumRodjenja(datumRodjenja);
		gost.setTelefon(telefon);
		gost.setAdresa(adresa);
		gost.setKorisnickoIme(korisnickoIme);
		gost.setLozinka(lozinka);
		managerOsoba.izmenaOsobe(id, ime, prezime, pol,datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		}
	
	public void prikazGostiju() {
		for (int i = 0; i < gosti.size(); i++) {
			Gost gost = gosti.get(i);
			System.out.println(gost.toString());
		}
	}
	
	
	
	
	
	
	
//	public TipSobe pronasaoTip(String tip,String ljudi) {
//		TipSobe ret = null;
//		for (int i=0; i<tipSobe.size();i++) {
//			TipSobe neki=tipSobe.get(i);
//			if (neki.getTipSobe().equals(tip)){
//				if (neki.getLjudi().equals(ljudi)) {
//					ret=neki;
//					break;
//				}
//				
//			}
//		}
//		return ret;
//	}
	

	
	
//	public void rezervisanje(TipSobe tipSobe,Gost gost,String datumDolaska,String datumOdlaska,List<DodatneUsluge> dodatneUsluge) {
//		int id = dobijanjeId(rezervacije);
//		
//		Rezervacije nova = new Rezervacije(id,tipSobe,gost,datumDolaska,datumOdlaska,dodatneUsluge);
//		rezervacije.add(nova);
//		gost.dodajRezervaciju(nova);
//	}
	
	
	
//	public void prikazSlobodnihTipovaSobe(String datumDolaska,String datumOdlaska, List<Soba> sobe,List<TipSobe> sviTipoviSobe) {
//		LocalDate dolazak = LocalDate.parse(datumDolaska, DateTimeFormatter.ofPattern("dd.MM.yyyy.")); ;
//        LocalDate odlazak = LocalDate.parse(datumOdlaska, DateTimeFormatter.ofPattern("dd.MM.yyyy.")); ;
//        List<TipSobe> tipovi= new ArrayList<TipSobe>();
//        for(Soba s:sobe) {
//        	if (tipovi.size()==sviTipoviSobe.size()) {
//    			break;
//    		}
//        	if (tipovi.contains(s.getTipSobe())) {
//    			continue;
//    		}
//        	
//        	
//        	
//        	List<Rezervacije> rezervacije=s.getRezervacija();
//        	if (rezervacije.size()==0) {
//        		tipovi.add(s.getTipSobe());
//        	}
//        	for (Rezervacije r:rezervacije) {
//        		
//        		
//        		LocalDate pocetakRez = r.getDatumDolaska();
//        		LocalDate krajRez =r.getDatumOdlaska();
//        		if(pocetakRez.isBefore(dolazak) && dolazak.isBefore(krajRez)) {
//        			continue;
//        		}
//        		if(pocetakRez.isBefore(odlazak) && odlazak.isBefore(krajRez)) {
//        			continue;
//        		}
//        		if(dolazak.isBefore(pocetakRez) && pocetakRez.isBefore(odlazak)) {
//        			continue;
//        		}
//        		TipSobe tip=s.getTipSobe();
//        		
//        		if (tipovi.contains(tip)) {
//        			continue;
//        		}
//        		
//        		tipovi.add(tip);
//        		
//        	}
//        
//        		
//        }
//        
//        for(TipSobe t:tipovi) {
//        	System.out.println(t.toString());
//        }
//        
//        
//        
//        
//	}
	
	
//	public void prikazivanjeRezervacija(Gost gost) {
//		List<Rezervacije> r = gost.getRezervacije();
//		
//		for (int i=0; i<r.size();i++) {
//			Rezervacije rez= r.get(i);
//			
//			System.out.println(rez.toString());
//			
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}