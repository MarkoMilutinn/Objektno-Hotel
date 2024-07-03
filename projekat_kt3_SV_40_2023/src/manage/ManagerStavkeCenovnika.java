package manage;

import java.util.ArrayList;
import java.util.Scanner;

import entiteti.DodatneUsluge;
import entiteti.StavkaCenovnika;
import entiteti.TipSobe;

public class ManagerStavkeCenovnika {
	private ArrayList<StavkaCenovnika> stavkeCenovnika;
	private static ManagerStavkeCenovnika instance;
	
	
	private ManagerStavkeCenovnika(ArrayList<StavkaCenovnika> stavkeCenovnika) {
		super();
		this.stavkeCenovnika = stavkeCenovnika;
	}
	
	public static ManagerStavkeCenovnika getInstance() {
		if (instance == null) {
			instance = new ManagerStavkeCenovnika(new ArrayList<StavkaCenovnika>());
			instance.dobaviStavkeCenovnika();
		}
		return instance;
	}
	
	public int dobijanjeId() {
		return stavkeCenovnika.size()+1;
	}
	
	public ArrayList<StavkaCenovnika> getStavkeCenovnika() {
		return stavkeCenovnika;
	}
	
	public StavkaCenovnika pronadjiStavkuCenovnikaPoId(int id) {
		StavkaCenovnika ret = null;
		for (int i = 0; i < stavkeCenovnika.size(); i++) {
			StavkaCenovnika neki = stavkeCenovnika.get(i);
			if (neki.getId() == id) {
				ret = neki;
				break;
			}

		}
		return ret;
	}
	
	public StavkaCenovnika pronadjiStavkuCenovnikaPoTipu(TipSobe tip) {
		StavkaCenovnika ret = null;
		for (int i = 0; i < stavkeCenovnika.size(); i++) {
			StavkaCenovnika neki = stavkeCenovnika.get(i);
			if (neki == null || neki.getTipSobe() == null) {
				continue;
			}
			if (neki.getTipSobe().equals(tip)) {
				ret = neki;
				break;
			}

		}
		return ret;
	}
	
	public StavkaCenovnika pronadjiStavkuCenovnikaPoTipu(DodatneUsluge du) {
		StavkaCenovnika ret = null;
		for (int i = 0; i < stavkeCenovnika.size(); i++) {
			StavkaCenovnika neki = stavkeCenovnika.get(i);
			if (neki == null || neki.getDodatnaUsluga() == null) {
				continue;
			}
			if (neki.getDodatnaUsluga().equals(du)) {
				ret = neki;
				break;
			}

		}
		return ret;
	}
	
	public void dobaviStavkeCenovnika() {
		ManagerTipSobe mts = ManagerTipSobe.getInstance();
		ManagerDodatneUsluge mdu = ManagerDodatneUsluge.getInstance();
//		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < mts.getTipSobe().size(); i++) {
//			double cena = getValue(sc, mts.getTipSobe().get(i).getTipSobe());
			stavkeCenovnika.add(new StavkaCenovnika(dobijanjeId(), mts.getTipSobe().get(i), 300.0));
		}
		for (int i = 0; i < mdu.getDodatneUsluge().size(); i++) {
//			double cena = getValueD(sc, mdu.getDodatneUsluge().get(i).getNaziv());
			stavkeCenovnika.add(new StavkaCenovnika(dobijanjeId(), mdu.getDodatneUsluge().get(i), 100.0));
		}
//		sc.close();
		

	}
	
	
//	public double getValue(Scanner sc, String tipSobe) {
//		double ret = 0;
//		while (true) {
//			try {
//				
//				System.out.println("Molimo unesite cenu u dinarima za " + tipSobe + " sobe");
//				String str = sc.nextLine();
//				ret = Double.parseDouble(str);
//				break;
//				
//			} catch (Exception e) {
//				System.out.println("Molimo unesite broj.");
//			}
//		
//		}
//		return ret;
//	}
//	
//	public double getValueD(Scanner sc, String naziv) {
//		System.out.println("Molimo unesite cenu u dinarima za " + naziv);
//		double ret = 0;
//		while (true) {
//			try {
//				ret = sc.nextDouble();
//				break;
//
//			} catch (Exception e) {
//				System.out.println("Molimo unesite broj.");
//				sc.nextLine();
//			}
//			
//		}
//		return ret;
//
//	}
	
	
	public void prikazStavkiCenovnika() {
		for (int i = 0; i < stavkeCenovnika.size(); i++) {
			StavkaCenovnika stavka = stavkeCenovnika.get(i);
			System.out.println(stavka.toString());
		}
	}
	
	public void dodajStavkuCenovnika(StavkaCenovnika sc) {
		stavkeCenovnika.add(sc);
	}
	
	public void dodajStavkuCenovnika( TipSobe ts, double cena) {
//		Scanner sc = new Scanner(System.in);
//		double cena = getValue(sc, ts.getTipSobe());
        stavkeCenovnika.add(new StavkaCenovnika(dobijanjeId(), ts, cena));
        ManagerCenovnik mc = ManagerCenovnik.getInstance();
        for (int i = 0; i < mc.getCenovnici().size(); i++) {
        	mc.getCenovnici().get(i).dodavanjeStavke(stavkeCenovnika.get(stavkeCenovnika.size()-1));
        }
//        sc.close();
    }
	
	public void dodajStavkuCenovnika(DodatneUsluge du, double cena) {
//        Scanner sc = new Scanner(System.in);
//        double cena = getValueD(sc, du.getNaziv());
        stavkeCenovnika.add(new StavkaCenovnika(dobijanjeId(), du, cena));
        ManagerCenovnik mc = ManagerCenovnik.getInstance();
        for (int i = 0; i < mc.getCenovnici().size(); i++) {
        	mc.getCenovnici().get(i).dodavanjeStavke(stavkeCenovnika.get(stavkeCenovnika.size()-1));
        }
//        sc.close();
    }
	
	public void izmeniStavkuCenovnika(StavkaCenovnika sc, double cena) {
        sc.setCena(cena);
    }

	public void izmeniStavkuCenovnika(int id,double cena) {
		StavkaCenovnika sc = pronadjiStavkuCenovnikaPoId(id);
		if (sc != null) {
			sc.setCena(cena);
		}else {
			System.out.println("Stavka cenovnika sa id " + id + " ne postoji.");
		}
		
	}
	
	
	
	
	public void brisanjeStavkeCenovnika(TipSobe ts) {
		StavkaCenovnika sc = pronadjiStavkuCenovnikaPoTipu(ts);
        if (sc != null) {
            stavkeCenovnika.remove(sc);
            ManagerCenovnik mc = ManagerCenovnik.getInstance();
            for (int i = 0; i < mc.getCenovnici().size(); i++) {
                mc.getCenovnici().get(i).brisanjeStavke(sc);
            }
        }else {
            System.out.println("Stavka cenovnika za tip sobe " + ts.getTipSobe() + " ne postoji.");
        }
        
    }
	
	
	public void brisanjeStavkeCenovnika(DodatneUsluge du) {
		StavkaCenovnika sc = pronadjiStavkuCenovnikaPoTipu(du);
		if (sc != null) {
			stavkeCenovnika.remove(sc);
			ManagerCenovnik mc = ManagerCenovnik.getInstance();
			for (int i = 0; i < mc.getCenovnici().size(); i++) {
				mc.getCenovnici().get(i).brisanjeStavke(sc);
			}
		} else {
			System.out.println("Stavka cenovnika za dodatnu uslugu " + du.getNaziv() + " ne postoji.");
		}
	}
	
	
	
	
	
	
	
	
}
