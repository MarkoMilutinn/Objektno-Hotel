package manage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import entiteti.Admin;
import entiteti.Gost;
import entiteti.NivoStrucnosti;
import entiteti.Osoba;
import entiteti.Pol;
import entiteti.Recepcionar;
import entiteti.Soba;
import entiteti.Sobarica;
import utils.FileCheck;


public class ManagerOsoba {
	
	ArrayList<Osoba> osobe;
	private String osobeFajl;
	private static ManagerOsoba instance;
	
	public static ManagerOsoba getInstance() {
		if (instance == null) {
			instance = new ManagerOsoba(new ArrayList<Osoba>(), "data/osobe.csv");
			instance.ucitajOsobeUListu();
		}
		return instance;
	}
	
	
	private ManagerOsoba(ArrayList<Osoba> osobe, String osobeFajl) {
		super();
		this.osobe = osobe;
		this.osobeFajl = osobeFajl;
	}
	
	
	public Osoba pronadjiOsobuPoKorisnickom(String korisnicko) {
		Osoba ret = null;
		for (int i = 0; i < osobe.size(); i++) {
			Osoba neki = osobe.get(i);
			if (neki.getKorisnickoIme().equals(korisnicko)) {
				ret = neki;
				break;
			}

		}
		return ret;
	}
	
	
	public Osoba pronadjiOsobuPoId(int id) {
		Osoba ret = null;
		for (int i = 0; i < osobe.size(); i++) {
			Osoba neki = osobe.get(i);
			if (neki.getId() == id) {
				ret = neki;
				break;
			}

		}
		return ret;
	}
	
	public ArrayList<Osoba> getOsobe() {
		return osobe;
	}

	public ArrayList<Gost> getGosti() {
		ArrayList<Gost> gosti = new ArrayList<Gost>();
		for (Osoba o : osobe) {
			if (o instanceof Gost) {
				gosti.add((Gost) o);
			}
		}
		return gosti;
	}
	
	public ArrayList<Sobarica> getSobarice() {
		ArrayList<Sobarica> sobarice = new ArrayList<Sobarica>();
		for (Osoba o : osobe) {
			if (o instanceof Sobarica) {
				sobarice.add((Sobarica) o);
			}
		}
		return sobarice;
	}
	
	public ArrayList<Admin> getAdmini() {
		ArrayList<Admin> admini = new ArrayList<Admin>();
		for (Osoba o : osobe) {
			if (o instanceof Admin) {
				admini.add((Admin) o);
			}
		}
		return admini;
	}
	
	public ArrayList<Recepcionar> getRecepcionari() {
		ArrayList<Recepcionar> recepcionari = new ArrayList<Recepcionar>();
		for (Osoba o : osobe) {
			if (o instanceof Recepcionar) {
				recepcionari.add((Recepcionar) o);
			}
		}
		return recepcionari;
	}
	
	
	
	
	public void dodajOsobu(Osoba o) {
		osobe.add(o);
		sacuvajOsobe();	
	}
	
	public void izmenaOsobe(int id, String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa, String korisnickoIme, String lozinka) {
		Osoba o = pronadjiOsobuPoId(id);
		o.setIme(ime);
		o.setPrezime(prezime);
		o.setPol(pol);
		o.setDatumRodjenja(datumRodjenja);
		o.setTelefon(telefon);
		o.setAdresa(adresa);
		o.setKorisnickoIme(korisnickoIme);
		o.setLozinka(lozinka);
		sacuvajOsobe();
		
	}
	
	public void brisanjeOsobe(int id) {
		Osoba o = pronadjiOsobuPoId(id);
		osobe.remove(o);
		sacuvajOsobe();
	}
	
	
	
	public boolean ucitajOsobeUListu() {
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(osobeFajl));
			String line;
			while ((line = bfr.readLine()) != null){
				String data[] = line.split(",");
				if (data.length > 0) {
					osobe.add(parseOsoba(data));
				}
			}bfr.close();
			return true;
		}
		catch (IOException e){
			return false;
			
		}
	}
	
	public Osoba parseOsoba(String[] data) {
		
		int id = Integer.parseInt(data[0]);
		String ime = data[1];
		String prezime = data [2];
		Pol pol = Pol.getPol(Integer.parseInt(data[3]));
		String datumRodjenja = data[4];
		String telefon = data[5];
		String adresa = data[6];
		String korisnickoIme = data[7];
		String lozinka = data[8];
		int tip = Integer.parseInt(data[9]);
//		System.out.println("ocitano");
//		System.out.println(data[9]);
		switch (tip) {
		case 0:
			return new Osoba(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		case 1:
			return new Gost(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		case 2:
			NivoStrucnosti nivo = NivoStrucnosti.getNivo(Integer.parseInt(data[10]));
			int staz= Integer.parseInt(data[11]);
			int osnovica = Integer.parseInt(data[12]);
			double plata= Double.parseDouble(data[13]);
			ArrayList<Soba> sobe = new ArrayList<Soba>();
			String lista=data[14];
			if (!lista.equals(" ")) {
				String[] listaStr = data[14].split(".");
				ManagerSoba ms = ManagerSoba.getInstance();
				for (String str : listaStr) {
					sobe.add(ms.pronadjiSobuPoId(Integer.parseInt(str)));
					
				}	
			}
			Sobarica s=new Sobarica(id, ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, nivo, staz, osnovica, plata, sobe);
//			System.out.println(s);
			return s;
		case 3:
			return new Admin(id,ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
			
		
			
		case 4:
			NivoStrucnosti nivo1 = NivoStrucnosti.getNivo(Integer.parseInt(data[10]));
			int staz1 = Integer.parseInt(data[11]);
			int osnovica1 = Integer.parseInt(data[12]);
			double plata1 = Double.parseDouble(data[13]);
			return new Recepcionar(id,ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, nivo1, staz1, osnovica1, plata1);
			
		default:
			System.out.println("Greska pri ucitavanju osobe");
			return null;
			
			
		}
		
		
	}
	
	public void sacuvajOsobe() {
		FileWriter upisivanje;
		try {
			File f = FileCheck.getFile(osobeFajl);
//			System.out.println(f.getAbsolutePath());
			upisivanje = new FileWriter(osobeFajl, false);
			for (Osoba o : osobe) {
				if (o instanceof Gost) {
                    upisivanje.write(toFileStringGost(o));
                }
				if (o instanceof Sobarica) {
                    upisivanje.write(toFileStringSobarica(o));
                }
				if (o instanceof Admin) {
                    upisivanje.write(toFileStringAdmin(o));
                }
				if (o instanceof Recepcionar) {
                    upisivanje.write(toFileStringRecepcionar(o));
                }
				
//				upisivanje.write(o.toFileString());
			}
			upisivanje.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public String toFileStringGost(Osoba o) {
		return String.valueOf(o.getId()) + "," + o.getIme() + "," + o.getPrezime() + "," + String.valueOf(o.getPol().getRedniBrojPol()) + ","
				+ o.getDatumRodjenja() + "," + o.getTelefon() + "," + o.getAdresa() + "," + o.getKorisnickoIme() + ","
				+ o.getLozinka() + "," + "1" + "\n";
	}
	
	public String toFileStringSobarica(Osoba o) {
		Sobarica s = (Sobarica) o;
		String sobe = "";
		if (s.getSoba().size() == 0) {
            sobe = " ";
        }else {
            for (Soba so : s.getSoba()) {
                sobe += String.valueOf(so.getId()) + ".";
            }
        }
		return String.valueOf(o.getId()) + "," + o.getIme() + "," + o.getPrezime() + "," + String.valueOf(o.getPol().getRedniBrojPol()) + ","
				+ o.getDatumRodjenja() + "," + o.getTelefon() + "," + o.getAdresa() + "," + o.getKorisnickoIme() + ","
				+ o.getLozinka() + "," + "2" + "," + String.valueOf(s.getNivoStrucnosti().getRedniBrojNivoa()) + "," + String.valueOf(s.getStaz()) + ","
				+ String.valueOf(s.getOsnovica()) + "," + String.valueOf(s.getPlata()) + "," + sobe + "\n";
	}
	
	public String toFileStringAdmin(Osoba o) {
		return String.valueOf(o.getId()) + "," + o.getIme() + "," + o.getPrezime() + ","
				+ String.valueOf(o.getPol().getRedniBrojPol()) + "," + o.getDatumRodjenja() + "," + o.getTelefon() + ","
				+ o.getAdresa() + "," + o.getKorisnickoIme() + "," + o.getLozinka() + "," + "3" + "\n";
	}
	
	
	public String toFileStringRecepcionar(Osoba o) {
		Recepcionar r = (Recepcionar) o;
		return String.valueOf(o.getId()) + "," + o.getIme() + "," + o.getPrezime() + ","
				+ String.valueOf(o.getPol().getRedniBrojPol()) + "," + o.getDatumRodjenja() + "," + o.getTelefon() + ","
				+ o.getAdresa() + "," + o.getKorisnickoIme() + "," + o.getLozinka() + "," + "4" + ","
				+ String.valueOf(r.getNivoStrucnosti().getRedniBrojNivoa()) + "," + String.valueOf(r.getStaz()) + ","
				+ String.valueOf(r.getOsnovica()) + "," + String.valueOf(r.getPlata()) + "\n";
	}
	
	


}