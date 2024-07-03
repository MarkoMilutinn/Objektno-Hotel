package entiteti;

import java.util.ArrayList;
import java.util.Date;

public class Cenovnik {

	private int id;
	private ArrayList<StavkaCenovnika> stavkeCenovnika;
	private Date datumOd;
	private Date datumDo;
	
	
	public Cenovnik(int id, ArrayList<StavkaCenovnika> stavkeCenovnika, Date datumOd, Date datumDo) {
		super();
		this.id = id;
		this.stavkeCenovnika = stavkeCenovnika;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
	}
	
	public Cenovnik(int id, Date datumOd, Date datumDo) {
		super();
		this.id = id;
		this.stavkeCenovnika = new ArrayList<StavkaCenovnika>();
		this.datumOd = datumOd;
		this.datumDo = datumDo;
	}
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public ArrayList<StavkaCenovnika> getStavkeCenovnika() {
		return stavkeCenovnika;
	}
	
	public void setStavkeCenovnika(ArrayList<StavkaCenovnika> stavkeCenovnika) {
		this.stavkeCenovnika = stavkeCenovnika;
	}
	
	
	public Date getDatumOd() {
		return datumOd;
	}
	
	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}
	
	public Date getDatumDo() {
		return datumDo;
	}
	
	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}
	
	public void dodavanjeStavke(StavkaCenovnika stavka) {
		stavkeCenovnika.add(stavka);
	}

	public void brisanjeStavke(StavkaCenovnika stavka) {
		stavkeCenovnika.remove(stavka);
	}
	
	
	
	@Override
	public String toString() {
		return "Cenovnik [id=" + id + ", stavkeCenovnika=" + stavkeCenovnika.toString() + ", datumOd=" + datumOd + ", datumDo="
				+ datumDo + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
