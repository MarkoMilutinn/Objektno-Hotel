package kontroleri;

import java.time.LocalDate;
import java.util.ArrayList;

import entiteti.DodatneUsluge;
import entiteti.Gost;
import entiteti.OsobinaSobe;
import entiteti.Rezervacije;
import entiteti.Soba;
import entiteti.StatusRezervacije;
import entiteti.StatusSobe;
import entiteti.TipSobe;
import main.Main;
import manage.ManagerDodatneUsluge;
import manage.ManagerOsoba;
import manage.ManagerRezervacije;
import manage.ManagerSoba;
import manage.ManagerSobarica;
import manage.ManagerStavkeCenovnika;

public class RezervacijaKontroler {

	private static RezervacijaKontroler instance;

	private RezervacijaKontroler() {
		super();
	}

	public static RezervacijaKontroler getInstance() {
		if (instance == null) {
			instance = new RezervacijaKontroler();
		}
		return instance;
	}

	public Rezervacije dodajRezervaciju(TipSobe tipSobe,Gost gost, LocalDate datumOd, LocalDate datumDo, ArrayList<DodatneUsluge> dodatneUsluge, ArrayList<OsobinaSobe> osobine ) {
		double cena = 0;
		int brojNocenja = (int) (datumOd.until(datumDo).getDays());
		ManagerStavkeCenovnika msc = ManagerStavkeCenovnika.getInstance();
		cena+=msc.pronadjiStavkuCenovnikaPoTipu(tipSobe).getCena()*brojNocenja;
		for (DodatneUsluge du : dodatneUsluge) {
			cena += msc.pronadjiStavkuCenovnikaPoTipu(du).getCena()*brojNocenja;
		}
		
		ManagerRezervacije mr = ManagerRezervacije.getInstance();
		Rezervacije r = mr.dodajRezervaciju(tipSobe, gost, datumOd, datumDo, brojNocenja, cena, false,StatusRezervacije.NA_CEKANJU, null, dodatneUsluge, LocalDate.now(),osobine);
//		TipSobe tip, Gost gost, LocalDate datumOd, LocalDate datumDo, long brojNocenja, double cena, boolean placeno, StatusRezervacije status, Soba soba, ArrayList<DodatneUsluge> dodatneUsluge, LocalDate datumRezervacije
		return r;
	}
	
	public ArrayList<Rezervacije> rezervacijeGosta(Gost gost) {
		ArrayList<Rezervacije> ret = new ArrayList<Rezervacije>();
		ManagerRezervacije mr = ManagerRezervacije.getInstance();
		for (Rezervacije r : mr.getRezervacije()) {
			if (r.getGost().equals(gost)) {
				ret.add(r);
			}
		}
		return ret;
	}

	public void otkaziRezervaciju(int id) {
		Rezervacije r = ManagerRezervacije.getInstance().pronadjiRezervacijuPoId(id);
		if (r == null) {
			System.out.println("Rezervacija ne postoji");
			return;
		}
		r.setStatusRezervacije(StatusRezervacije.OTKAZANA);
		ManagerRezervacije.getInstance().snimiRezervacije();
	}
	
	public void odbijRezervaciju(Rezervacije r) {
		r.setStatusRezervacije(StatusRezervacije.ODBIJENA);
		ManagerRezervacije.getInstance().snimiRezervacije();
	
	}
	
	public boolean potvrdiRezervaciju(Rezervacije r) {
	    TipSobe tip = r.getTipSobe();
	    ArrayList<Soba> sobe = ManagerSoba.getInstance().pronadjiSobePoTipu(tip);
	    ArrayList<Rezervacije> rezervacije = ManagerRezervacije.getInstance().getRezervacije();

	    if (sobe.size() == 0) {
	        System.out.println("Nema slobodnih soba tog tipa za taj period");
	        r.setStatusRezervacije(StatusRezervacije.ODBIJENA);
	        ManagerRezervacije.getInstance().snimiRezervacije();
	        return false;
	    }

	    for (Soba s : sobe) {
	        boolean slobodna = true;

	        for (Rezervacije rez : rezervacije) {
	            if (!rez.getTipSobe().equals(tip) || 
	                rez.getStatusRezervacije().equals(StatusRezervacije.OTKAZANA) || 
	                rez.getStatusRezervacije().equals(StatusRezervacije.ODBIJENA) || 
	                rez.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)  ) {
	                continue;
	            }

	            if (rez.getTipSobe().equals(tip)) {
	                if ((r.getDatumDolaska().isBefore(rez.getDatumOdlaska()) && r.getDatumOdlaska().isAfter(rez.getDatumDolaska())) ||
	                    (r.getDatumOdlaska().isAfter(rez.getDatumDolaska()) && r.getDatumOdlaska().isBefore(rez.getDatumOdlaska())) ||
	                    (r.getDatumDolaska().isBefore(rez.getDatumDolaska()) && r.getDatumOdlaska().isAfter(rez.getDatumOdlaska()))) {
	                    slobodna = false;
	                    break;
	                }
	            }
	        }

	        if (slobodna) {
	            System.out.println("Soba je slobodna");
	            r.setStatusRezervacije(StatusRezervacije.POTVRDJENA);
	            ManagerRezervacije.getInstance().snimiRezervacije();
	            return true;
	        }
	    }

	    System.out.println("Nema slobodnih soba za taj period");
	    r.setStatusRezervacije(StatusRezervacije.ODBIJENA);
	    ManagerRezervacije.getInstance().snimiRezervacije();
	    return false;
	}
	
	
	public void checkIn(Rezervacije r) {
		ArrayList<Soba> sveSobe = ManagerSoba.getInstance().pronadjiSobePoTipu(r.getTipSobe());
		ArrayList<Soba> slobodneSobe = new ArrayList<Soba>();
		for (Soba s : sveSobe) {
			if (s.getStatusSobe().equals(StatusSobe.SLOBODNA)) {
				slobodneSobe.add(s);
			}
			
		}
		if (slobodneSobe.size() == 0) {
			System.out.println("Nema slobodnih soba");
			r.setStatusRezervacije(StatusRezervacije.ODBIJENA);
			return;
		}else {
			for (int i = 0; i < slobodneSobe.size(); i++) {
				System.out.println((i + 1) + ". " + slobodneSobe.get(i).toString());
			}
			while (true) {
				
                System.out.println("Unesite redni broj sobe koju zelite da dodelite");
                String redniBroj = Main.sc.nextLine();
                if (Integer.parseInt(redniBroj) < 1 || Integer.parseInt(redniBroj) > slobodneSobe.size()) {
                    System.out.println("Uneli ste pogresan redni broj");
                    continue;
                }
                Soba s = slobodneSobe.get(Integer.parseInt(redniBroj) - 1);
                dodavanjeDodatnihUsluga(r);
                r.setSoba(s);
                s.setStatusSobe(StatusSobe.ZAUZETA);
//                r.setStatusRezervacije(StatusRezervacije.POTVRDJENA);
                ManagerRezervacije.getInstance().snimiRezervacije();
                ManagerSoba.getInstance().snimiSobe();
                return;
            }
			}
			
		}
	
	public void dodavanjeDodatnihUsluga(Rezervacije r) {
		
		ArrayList<DodatneUsluge> dodatneUslugeSve = ManagerDodatneUsluge.getInstance().getDodatneUsluge();
		for (int i = 0; i < dodatneUslugeSve.size(); i++) {
			System.out.println((i + 1) + ". " + dodatneUslugeSve.get(i).getNaziv());
		}

		while (true) {
			ArrayList<DodatneUsluge> dodatneUsluge = r.getDodatneUsluge();
			System.out.print(
					"Unesite redne brojeve dodatnih usluga odvojene zarezima\nili enter ako ne želite da promenite: ");
			String dodatneUslugeString = Main.sc.nextLine();
			if (dodatneUslugeString.equals("")) {
				break;
			}
			String[] dodatneUslugeStringNiz = dodatneUslugeString.split(",");
			try {
				dodatneUsluge.clear();
				r.getDodatneUsluge().clear();
				for (String dodatnaUsluga : dodatneUslugeStringNiz) {
					int redniBroj = Integer.parseInt(dodatnaUsluga);
					if (redniBroj < 1 || redniBroj > dodatneUslugeSve.size()) {
						throw new Exception();
					}
					dodatneUsluge.add(dodatneUslugeSve.get(redniBroj - 1));
				}
				r.setDodatneUsluge(dodatneUsluge);
				r.setCena(dobijanjeCene(r));
				break;
			} catch (Exception e) {
				System.out.println("Nepostojeca opcija");
				continue;
			}
		}
	}
		
	
	public void checkOut(Rezervacije r) {
		r.setStatusRezervacije(StatusRezervacije.ZAVRSENA);
		r.getSoba().setStatusSobe(StatusSobe.SPREMANJE);
		ManagerSobarica.getInstance().dodajSobuSobarici(r.getSoba());
		ManagerRezervacije.getInstance().snimiRezervacije();
		ManagerSoba.getInstance().snimiSobe();
		ManagerOsoba.getInstance().sacuvajOsobe();
		
	}
	
	public double dobijanjeCene(Rezervacije r) {
		double cena = 0;
		int brojNocenja = (int) (r.getDatumDolaska().until(r.getDatumOdlaska()).getDays());
		ManagerStavkeCenovnika msc = ManagerStavkeCenovnika.getInstance();
		cena += msc.pronadjiStavkuCenovnikaPoTipu(r.getTipSobe()).getCena() * brojNocenja;
		for (DodatneUsluge du : r.getDodatneUsluge()) {
			cena += msc.pronadjiStavkuCenovnikaPoTipu(du).getCena() * brojNocenja;
		}
		return cena;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
