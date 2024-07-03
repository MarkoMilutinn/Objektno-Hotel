package manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entiteti.DodatneUsluge;
import utils.FileCheck;

public class ManagerDodatneUsluge {
	private ArrayList<DodatneUsluge> dodatneUsluge;
	private String dodatneUslugeFajl;
	private static ManagerDodatneUsluge instance;
	
	private ManagerDodatneUsluge(ArrayList<DodatneUsluge> dodatneUsluge, String dodatneUslugeFajl) {
		super();
		this.dodatneUsluge = dodatneUsluge;
		this.dodatneUslugeFajl = dodatneUslugeFajl;
	}
	
	public static ManagerDodatneUsluge getInstance() {
		if (instance == null) {
			instance = new ManagerDodatneUsluge(new ArrayList<DodatneUsluge>(), "data/dodatneUsluge.txt");
			instance.ucitajDodatneUsluge();
		}
		return instance;
	}
	
	public DodatneUsluge pronadjiDodatneUslugePoId(int id) {
		DodatneUsluge ret = null;
		for (int i = 0; i < dodatneUsluge.size(); i++) {
			DodatneUsluge neki = dodatneUsluge.get(i);
			if (neki.getId() == id) {
				ret = neki;
				break;
			}
		}
		return ret;
	}
	
	
	public DodatneUsluge pronadjiDodatneUslugePoNazivu(String naziv) {
		DodatneUsluge ret = null;
		for (int i = 0; i < dodatneUsluge.size(); i++) {
			DodatneUsluge neki = dodatneUsluge.get(i);
			if (neki.getNaziv().equals(naziv)) {
				ret = neki;
				break;
			}
		}
		return ret;
	}
	
	public void dodajDodatneUsluge(String naziv,double cena) {
		int id = dodatneUsluge.size() + 1;
		DodatneUsluge du = new DodatneUsluge(id, naziv);
		dodatneUsluge.add(du);
		ManagerStavkeCenovnika.getInstance().dodajStavkuCenovnika(du,cena);
		snimiDodatneUsluge();
	}
	
	public void izmenaDodatneUsluge(int id, String naziv) {
		DodatneUsluge du = pronadjiDodatneUslugePoId(id);
		if (du != null) {
			du.setNaziv(naziv);
			snimiDodatneUsluge();
		} else {
			System.out.println("Dodatne usluge ne postoji.");
		}
	}
	
	public void brisanjeDodatneUsluge(int id) {
		DodatneUsluge du = pronadjiDodatneUslugePoId(id);
		if (du != null) {
			dodatneUsluge.remove(du);
			ManagerStavkeCenovnika.getInstance().brisanjeStavkeCenovnika(du);
			snimiDodatneUsluge();
		} else {
			System.out.println("Dodatne usluge ne postoji.");
		}
	}
	
	
	public ArrayList<DodatneUsluge> getDodatneUsluge() {
		return dodatneUsluge;
	}
	
	public void setDodatneUsluge(ArrayList<DodatneUsluge> dodatneUsluge) {
		this.dodatneUsluge = dodatneUsluge;
	}
	
	public String getDodatneUslugeFajl() {
		return dodatneUslugeFajl;
	}
	
	public void setDodatneUslugeFajl(String dodatneUslugeFajl) {
		this.dodatneUslugeFajl = dodatneUslugeFajl;
	}
	
	
	public boolean ucitajDodatneUsluge() {
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(dodatneUslugeFajl));
			String line;
			while ((line = bfr.readLine()) != null){
				String data[] = line.split(",");
				if (data.length > 0) {
					dodatneUsluge.add(parseUsluga(data));
				}
			}bfr.close();
		}
		catch (IOException e){
			return false;
			
		}
	return true;
	}
	
	public DodatneUsluge parseUsluga(String[] data) {
		int id = Integer.parseInt(data[0]);
		String naziv = data[1];
		return new DodatneUsluge(id, naziv);
	}
	
	
	
	public void snimiDodatneUsluge() {
		String data = "";
		for (DodatneUsluge du : dodatneUsluge) {
			data += String.valueOf(du.getId()) + "," + du.getNaziv() + "\n";
		}
		try {
			File f = FileCheck.getFile(dodatneUslugeFajl);
			java.io.FileWriter fw = new java.io.FileWriter(f);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	

}
