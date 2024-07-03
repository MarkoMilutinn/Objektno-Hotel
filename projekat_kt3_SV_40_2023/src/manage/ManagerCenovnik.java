package manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entiteti.Cenovnik;
import entiteti.DodatneUsluge;
import entiteti.StavkaCenovnika;
import entiteti.TipSobe;
import utils.FileCheck;

public class ManagerCenovnik {
	
	private ArrayList<Cenovnik> cenovnici;
	private String cenovniciFajl;
	private static ManagerCenovnik instance;
	
	private ManagerCenovnik(ArrayList<Cenovnik> cenovnici, String cenovniciFajl) {
		super();
		this.cenovnici = cenovnici;
		this.cenovniciFajl = cenovniciFajl;
	}
	
	public static ManagerCenovnik getInstance() {
		if (instance == null) {
			instance = new ManagerCenovnik(new ArrayList<Cenovnik>(), "data/cenovnici.txt");
			instance.ucitajCenovnike();
		}
		return instance;
	}
	
	public int dobijanjeIdCenovnika() {
		return cenovnici.size() + 1;
	}

	public ArrayList<Cenovnik> getCenovnici() {
		return cenovnici;
	}
	
	
	public Cenovnik pronadjiCenovnikPoId(int id) {
		Cenovnik ret = null;
		for (int i = 0; i < cenovnici.size(); i++) {
			Cenovnik neki = cenovnici.get(i);
			if (neki.getId() == id) {
				ret = neki;
				break;
			}

		}
		return ret;
	}
	
	
	public void dodajCenovnik(Date datumOd, Date datumDo, ArrayList<StavkaCenovnika> stavke) {
		int id = dobijanjeIdCenovnika();
		Cenovnik c = new Cenovnik(id, stavke, datumOd, datumDo);
		cenovnici.add(c);
		snimiCenovnike();
	}
	
	public void obrisiCenovnik(int id) {
		Cenovnik c = pronadjiCenovnikPoId(id);
		if (c != null) {
			cenovnici.remove(c);
			snimiCenovnike();
		} else {
			System.out.println("Ne postoji cenovnik sa tim id");
		}
		
	}
	
	
	public void izmeniCenovnik(int id, Date datumOd, Date datumDo, ArrayList<StavkaCenovnika> stavke) {
		Cenovnik c = pronadjiCenovnikPoId(id);
		if (c != null) {
			c.setDatumOd(datumOd);
			c.setDatumDo(datumDo);
			c.setStavkeCenovnika(stavke);
			snimiCenovnike();
		} else {
			System.out.println("Ne postoji cenovnik sa tim id");
		}
	}
	
	public void izmeniCenuStavkeCenovnika(int id, double cena,DodatneUsluge du) {
		Cenovnik c = pronadjiCenovnikPoId(id);
		if (c != null) {
            ArrayList<StavkaCenovnika> stavke = c.getStavkeCenovnika();
            for (StavkaCenovnika s : stavke) {
                if (s.getDodatnaUsluga().equals(du)) {
                    s.setCena(cena);
                    snimiCenovnike();
                    return;
                }
            }
            System.out.println("Ne postoji stavka sa tom uslugom");
        } else {
            System.out.println("Ne postoji cenovnik sa tim id");
        }
		
	}
	
	
	public void izmeniCenuStavkeCenovnika(int id,double cena,TipSobe tip) {
		Cenovnik c = pronadjiCenovnikPoId(id);
		if (c != null) {
			ArrayList<StavkaCenovnika> stavke = c.getStavkeCenovnika();
			for (StavkaCenovnika s : stavke) {
				if (s.getTipSobe().equals(tip)) {
					s.setCena(cena);
					snimiCenovnike();
					return;
				}
			}
			System.out.println("Ne postoji stavka sa tim tipom sobe");
		} else {
			System.out.println("Ne postoji cenovnik sa tim id");
		}

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean ucitajCenovnike() {
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(cenovniciFajl));
			String line;
			while ((line = bfr.readLine()) != null){
				String data[] = line.split(",");
				if (data.length > 0) {
					cenovnici.add(parseCenovnik(data));
				}
			}bfr.close();
		}
		catch (IOException e){
			return false;
			
		}
	return true;
	}
		
	public Cenovnik parseCenovnik(String[] data) {
		int id = Integer.parseInt(data[0]);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date datumOd = null;
		Date datumDo = null;
		try {
		    datumOd = format.parse(data[1]);
		    datumDo = format.parse(data[2]);
		} catch (ParseException e) {
		    e.printStackTrace();
		    System.out.println("Neuspesno konvertovanje datuma");
		}
		ArrayList<StavkaCenovnika> lista=new ArrayList<StavkaCenovnika>();
		String stavke=data[3];
		if (!stavke.equals(" ")) {
			String[] stavkeNiz = stavke.split(".");
			ManagerStavkeCenovnika msc=ManagerStavkeCenovnika.getInstance();
			for (String s : stavkeNiz) {
				lista.add(msc.pronadjiStavkuCenovnikaPoId(Integer.parseInt(s)));
                
            }
				
			}
		return new Cenovnik(id,lista, datumOd, datumDo);
		}
	
	
	
	public void snimiCenovnike() {
		String data = "";
		for (Cenovnik c : cenovnici) {
			data += String.valueOf(c.getId()) + "," + c.getDatumOd() + "," + c.getDatumDo() + ",";
			if (c.getStavkeCenovnika().size() == 0) {
				data += " ";
			} else {
				for (StavkaCenovnika s : c.getStavkeCenovnika()) {
					data += String.valueOf(s.getId()) + ".";
				}
			}
			data += "\n";
		}
		try {
			File f = FileCheck.getFile(cenovniciFajl);
			java.io.FileWriter fw = new java.io.FileWriter(f);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	
	
	
	
}
