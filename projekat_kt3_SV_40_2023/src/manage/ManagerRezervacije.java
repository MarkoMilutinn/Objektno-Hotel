package manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import entiteti.DodatneUsluge;
import entiteti.Gost;
import entiteti.OsobinaSobe;
import entiteti.Rezervacije;
import entiteti.Soba;
import entiteti.StatusRezervacije;
import entiteti.TipSobe;
import utils.FileCheck;

public class ManagerRezervacije {
	ArrayList<Rezervacije> rezervacije;
	String rezervacijeFajl;

	private static ManagerRezervacije instance;

	private ManagerRezervacije(ArrayList<Rezervacije> rezervacije, String rezervacijeFajl) {
		super();
		this.rezervacije = rezervacije;
		this.rezervacijeFajl = rezervacijeFajl;
	}

	public static ManagerRezervacije getInstance() {
		if (instance == null) {
			instance = new ManagerRezervacije(new ArrayList<Rezervacije>(), "data/rezervacije.txt");
			instance.ucitajRezervacije();
		}
		return instance;
	}

	public ArrayList<Rezervacije> getRezervacije() {
		return rezervacije;
	}

	
	public Rezervacije pronadjiRezervacijuPoId(int id) {
		Rezervacije ret = null;
		for (int i = 0; i < rezervacije.size(); i++) {
			Rezervacije neki = rezervacije.get(i);
			if (neki.getId() == id) {
				ret = neki;
				break;
			}

		}
		return ret;
	}
	public void dodajRezervaciju(Rezervacije rezervacija) {
		rezervacije.add(rezervacija);
		snimiRezervacije();
	}
	
	public Rezervacije dodajRezervaciju(TipSobe tip, Gost gost, LocalDate datumOd, LocalDate datumDo, long brojNocenja, double cena, boolean placeno, StatusRezervacije status, Soba soba, ArrayList<DodatneUsluge> dodatneUsluge, LocalDate datumRezervacije,ArrayList<OsobinaSobe> osobine) {
		int id = rezervacije.size() + 1;
		Rezervacije rezervacija = new Rezervacije(id, tip, gost, datumOd, datumDo, brojNocenja, cena, placeno, status, soba, dodatneUsluge, datumRezervacije,osobine);
		rezervacije.add(rezervacija);
		snimiRezervacije();
		
		return rezervacija;
		
	}
	
	public void obrisiRezervaciju(int id) {
		Rezervacije rezervacija = pronadjiRezervacijuPoId(id);
		if (rezervacija != null) {
			rezervacije.remove(rezervacija);
			snimiRezervacije();
		}
	}
	
	public Object[][] prikazRezervacijaGosta(Gost gost) {
		ArrayList<Rezervacije> rezervacijeGosta = new ArrayList<Rezervacije>();
		for (Rezervacije rezervacija : rezervacije) {
			if (rezervacija.getGost().equals(gost)) {
				rezervacijeGosta.add(rezervacija);
			}
		}
		Object[][] rezervacijeData = new Object[rezervacijeGosta.size()][8];
		for (int i = 0; i < rezervacijeGosta.size(); i++) {
			Rezervacije rezervacija = rezervacijeGosta.get(i);
			rezervacijeData[i][0] = rezervacija.getId();
			rezervacijeData[i][1] = gost.getKorisnickoIme();
			rezervacijeData[i][2] = rezervacija.getStringDatumDolaska();
			rezervacijeData[i][3] = rezervacija.getStringDatumOdlaska();
			rezervacijeData[i][4] = rezervacija.getTipSobe().getTipSobe();
			ArrayList<DodatneUsluge> dodatneUsluge = rezervacija.getDodatneUsluge();
			String usluge = "";
			for (DodatneUsluge usluga : dodatneUsluge) {
				usluge += usluga.getNaziv() + ", ";
			}
			rezervacijeData[i][5] = usluge;
			rezervacijeData[i][7] = rezervacija.getCena();
			rezervacijeData[i][6] = rezervacija.getStatusRezervacije();
		}
		return rezervacijeData;
	}
	
	public double ukupnaCenaRezervacija(Gost gost) {
		double ukupnaCena = 0;
		for (Rezervacije rezervacija : rezervacije) {
			if (rezervacija.getGost().equals(gost) && rezervacija.getStatusRezervacije() != StatusRezervacije.ODBIJENA) {
				ukupnaCena += rezervacija.getCena();
			}
		}
		return ukupnaCena;
	}
	
	
	public Object [][] prikazSvihRezervacija(){
		Object[][] ret = new Object[rezervacije.size()][8];
		for (int i = 0; i < rezervacije.size(); i++) {
			Rezervacije r = rezervacije.get(i);
			ret[i][0] = r.getId();
			ret[i][1] = r.getGost().getKorisnickoIme();
			ret[i][2] = r.getSoba() == null ? " " : r.getSoba().getId();
			ret[i][3] = r.getTipSobe().getLjudi();
			ret[i][4] = r.getStringDatumDolaska();
			ret[i][5] = r.getStringDatumOdlaska();
			ret[i][6] = r.getCena();
			ret[i][7] = r.getStatusRezervacije();
		}
		return ret;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean ucitajRezervacije() {
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(rezervacijeFajl));
			String line;
			while ((line = bfr.readLine()) != null) {
				String data[] = line.split(",");
				if (data.length > 0) {
					rezervacije.add(parseRezervacije(data));
				}
			}
			bfr.close();
		} catch (IOException e) {
			return false;

		}

		return true;

	}

	public Rezervacije parseRezervacije(String[] data) {
		int id = Integer.parseInt(data[0]);

		TipSobe tip = ManagerTipSobe.getInstance().pronadjiTipSobePoId(Integer.parseInt(data[1]));
		Gost gost = ManagerGost.getInstance().pronadjiGostaPoId(Integer.parseInt(data[2]));
		String datumOd = data[3];
		String datumDo = data[4];
		long brojNocenja = Long.parseLong(data[5]);
		double cena = Double.parseDouble(data[6]);
		boolean placeno = Boolean.parseBoolean(data[7]);
		StatusRezervacije status = StatusRezervacije.getStatus(Integer.parseInt(data[8]));
		String sobaId = data[9];
		Soba soba = null;
		if (!sobaId.equals(" ")) {
			soba = ManagerSoba.getInstance().pronadjiSobuPoId(Integer.parseInt(sobaId));
		}
		
		String dodatneUsluge = data[10];
		ArrayList<DodatneUsluge> dodatneUslugeList = new ArrayList<DodatneUsluge>();
		if (!dodatneUsluge.equals(" ")) {
			String[] usluge = dodatneUsluge.split(";");
			for (String usluga : usluge) {
				dodatneUslugeList
						.add(ManagerDodatneUsluge.getInstance().pronadjiDodatneUslugePoId(Integer.parseInt(usluga)));
			}
		}
		String datumRezervacije = data[11];
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.uuuu.");
		LocalDate datumOdl = null;
		LocalDate datumDol = null;
		LocalDate datumRez = null;

		datumOdl = LocalDate.parse(datumOd, formater);
		datumDol = LocalDate.parse(datumDo, formater);
		datumRez = LocalDate.parse(datumRezervacije, formater);
		String os = data[12];
	    ArrayList<OsobinaSobe> osobine = new ArrayList<OsobinaSobe>();
			if (!os.equals(" ")) {
				String[] osString = os.split(";");
				for (String o : osString) {
					osobine.add(OsobinaSobe.getOsobina(Integer.parseInt(o)));
				}
				
			}
		return new Rezervacije(id, tip, gost, datumOdl, datumDol, brojNocenja, cena, placeno, status, soba,
				dodatneUslugeList, datumRez,osobine);
	}

	public void snimiRezervacije() {
		String rezervacijeZaUpis = "";
		for (Rezervacije rezervacija : rezervacije) {
			rezervacijeZaUpis += rezervacija.getId() + "," + rezervacija.getTipSobe().getId() + ","
					+ rezervacija.getGost().getId() + "," + rezervacija.getStringDatumDolaska() + ","
					+ rezervacija.getStringDatumOdlaska() + "," + rezervacija.getBrojNocenja() + "," + rezervacija.getCena()
					+ "," + rezervacija.isPlaceno() + "," + rezervacija.getStatusRezervacije().getRedniBrojStatusa()
					+ ","  + (rezervacija.getSoba() == null ? " " : rezervacija.getSoba().getId()) + ",";
			
			if (rezervacija.getDodatneUsluge().isEmpty()) {
				rezervacijeZaUpis += " ";
			}else {
				for (DodatneUsluge usluga : rezervacija.getDodatneUsluge()) {
					rezervacijeZaUpis += usluga.getId() + ";";
				}
			}
			
			rezervacijeZaUpis += "," + rezervacija.getStringDatumRezervacije() + ",";
			if (rezervacija.getOsobina().isEmpty()) {
                rezervacijeZaUpis += " ";
              } else {
            	 for (OsobinaSobe os : rezervacija.getOsobina()) {
                  rezervacijeZaUpis += os.getRedniBrojOsobine() + ";";
              }
              }
			rezervacijeZaUpis += "\n";
                
			
		}
		try {
			File f = FileCheck.getFile(rezervacijeFajl);
			java.io.FileWriter fw = new java.io.FileWriter(f);
			fw.write(rezervacijeZaUpis);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		

		}

	}
	
	
	public Object [][] prikazDolazaka(){
		Object[][] ret = new Object[rezervacije.size()][8];
		LocalDate danas = LocalDate.now();
		for (int i = 0; i < rezervacije.size(); i++) {
			Rezervacije r = rezervacije.get(i);
			if (r.getStatusRezervacije() == StatusRezervacije.POTVRDJENA) {
				if (r.getDatumDolaska().isEqual(danas)) {
                    ret[i][0] = r.getId();
                    ret[i][1] = r.getGost().getKorisnickoIme();
                    ret[i][2] = r.getSoba() == null ? " " : r.getSoba().getId();
                    ret[i][3] = r.getTipSobe().getLjudi();
                    ret[i][4] = r.getStringDatumDolaska();
                    ret[i][5] = r.getStringDatumOdlaska();
                    ret[i][6] = r.getCena();
                    ret[i][7] = r.getStatusRezervacije();
			}
		}
		
		
	}
	return ret;
}
	
		
	public Object [][] prikazOdlazaka(){
		Object[][] ret = new Object[rezervacije.size()][8];
        LocalDate danas = LocalDate.now();
        for (int i = 0; i < rezervacije.size(); i++) {
            Rezervacije r = rezervacije.get(i);
            if (r.getStatusRezervacije() == StatusRezervacije.TRENUTNA) {
                if (r.getDatumOdlaska().isEqual(danas)) {
                    ret[i][0] = r.getId();
                    ret[i][1] = r.getGost().getKorisnickoIme();
                    ret[i][2] = r.getSoba() == null ? " " : r.getSoba().getId();
                    ret[i][3] = r.getTipSobe().getLjudi();
                    ret[i][4] = r.getStringDatumDolaska();
                    ret[i][5] = r.getStringDatumOdlaska();
                    ret[i][6] = r.getCena();
                    ret[i][7] = r.getStatusRezervacije();
            }
        }
        
        
    }
        return ret;
        }
       
	
	
	
	
	
	

}
