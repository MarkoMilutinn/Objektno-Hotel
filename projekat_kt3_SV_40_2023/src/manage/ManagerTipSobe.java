package manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entiteti.TipSobe;
import utils.FileCheck;

public class ManagerTipSobe {

	ArrayList<TipSobe> tipSobe;
	String tipSobeFajl;
	private static ManagerTipSobe instance;
	
	private ManagerTipSobe(ArrayList<TipSobe> tipSobe, String tipSobeFajl) {
		super();
		this.tipSobe = tipSobe;
		this.tipSobeFajl = tipSobeFajl;
	}
	
	public static ManagerTipSobe getInstance() {
		if (instance == null) {
			instance = new ManagerTipSobe(new ArrayList<TipSobe>(), "data/tipSobe.txt");
			instance.ucitajTipSobe();
		}
		return instance;
	}
	
	public TipSobe pronadjiTipSobePoId(int id) {
		TipSobe ret = null;
		for (int i = 0; i < tipSobe.size(); i++) {
			TipSobe neki = tipSobe.get(i);
			if (neki.getId() == id) {
				ret = neki;
				break;
			}

		}
		return ret;
	}
	
	public TipSobe pronadjiTipSobePoTipu(String tip) {
		TipSobe ret = null;
		for (int i = 0; i < tipSobe.size(); i++) {
			TipSobe neki = tipSobe.get(i);
			if (neki.getTipSobe().equals(tip)) {
				ret = neki;
				break;
			}

		}
		return ret;
	}
	
	public void dodajTipSobe( String tip, String ljudi,double cena) {
		int id = tipSobe.size()+1;
		TipSobe t = new TipSobe(id, tip, ljudi);
		tipSobe.add(t);
		ManagerStavkeCenovnika.getInstance().dodajStavkuCenovnika(t,cena);
		snimiTipSobe();
	}
	
	public void dodajTipSobe(TipSobe t,double cena) {
		tipSobe.add(t);
		ManagerStavkeCenovnika.getInstance().dodajStavkuCenovnika(t,cena);
		snimiTipSobe();
	}
	
	public ArrayList<TipSobe> getTipSobe() {
		return tipSobe;
	}
	
	public void setTipSobe(ArrayList<TipSobe> tipSobe) {
		this.tipSobe = tipSobe;
	}
	
	public String getTipSobeFajl() {
		return tipSobeFajl;
	}
	
	public void setTipSobeFajl(String tipSobeFajl) {
		this.tipSobeFajl = tipSobeFajl;
	}
	
	
//	public void izmeniTipSobe(TipSobe t) {
//		for (int i = 0; i < tipSobe.size(); i++) {
//			if (tipSobe.get(i).getId() == t.getId()) {
//				tipSobe.set(i, t);
//				break;
//			}
//		}
//		snimiTipSobe();
//	}
	
	
	public void izmeniTipSobe(int id, String tip, String ljudi) {
		TipSobe t = pronadjiTipSobePoId(id);
		t.setTipSobe(tip);
		t.setLjudi(ljudi);
		snimiTipSobe();
	}
	
	public void obrisiTipSobe(TipSobe t) {
		for (int i = 0; i < tipSobe.size(); i++) {
			if (tipSobe.get(i).getId() == t.getId()) {
				tipSobe.remove(i);
				ManagerStavkeCenovnika.getInstance().brisanjeStavkeCenovnika(t);
				break;
			}
		}
		snimiTipSobe();
	}
	
	public void obrisiTipSobe(int id) {
		TipSobe tip= pronadjiTipSobePoId(id);
		tipSobe.remove(tip);
		ManagerStavkeCenovnika.getInstance().brisanjeStavkeCenovnika(tip);
		snimiTipSobe();
	}
	
	
	
	
	public void snimiTipSobe() {
		String data = "";
		for (TipSobe t : tipSobe) {
			data += String.valueOf(t.getId()) + "," + t.getTipSobe() + "," + t.getLjudi() + "\n";
		}
		try {
			File f = FileCheck.getFile(tipSobeFajl);
			java.io.FileWriter fw = new java.io.FileWriter(f);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean ucitajTipSobe() {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(tipSobeFajl));
            String line;
            while ((line = bfr.readLine()) != null){
                String data[] = line.split(",");
                if (data.length > 0) {
                    tipSobe.add(parseTipSobe(data));
                }
            }bfr.close();
        }
        catch (IOException e){
            return false;
            
        }
	
            return true;
            
	}
	
	public TipSobe parseTipSobe(String[] data) {
		int id = Integer.parseInt(data[0]);
		String tipSobe = data[1];
		String ljudi = data[2];
		return new TipSobe(id, tipSobe, ljudi);
	}
	
	
	
	
	
	
}
