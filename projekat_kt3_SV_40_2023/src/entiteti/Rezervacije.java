package entiteti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Rezervacije {

	private int id;
	private TipSobe tipSobe;
	private Gost gost;
	private LocalDate datumDolaska;
	private LocalDate datumOdlaska;
	private long brojNocenja;
	private double cena;
	private boolean placeno;
	private StatusRezervacije statusRezervacije;
	private Soba soba;
	private ArrayList<DodatneUsluge> dodatneUsluge;
	private LocalDate datumRezervacije;
	private ArrayList<OsobinaSobe> osobina;

	public LocalDate getDatumRezervacije() {
		return datumRezervacije;
	}

	public void setDatumRezervacije(LocalDate datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}

	public void setDatumDolaska(LocalDate datumDolaska) {
		this.datumDolaska = datumDolaska;
	}

	public void setDatumOdlaska(LocalDate datumOdlaska) {
		this.datumOdlaska = datumOdlaska;
	}

	public void setBrojNocenja(long brojNocenja) {
		this.brojNocenja = brojNocenja;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public void setStatusRezervacije(StatusRezervacije statusRezervacije) {
		this.statusRezervacije = statusRezervacije;
	}

	public Rezervacije(int id, TipSobe tipSobe, Gost gost, LocalDate datumDolaska, LocalDate datumOdlaska,
			long brojNocenja, double cena, boolean placeno, StatusRezervacije statusRezervacije, Soba soba,
			ArrayList<DodatneUsluge> dodatneUsluge, LocalDate datumRezervacije,ArrayList<OsobinaSobe>  osobine) {
		super();
		this.id = id;
		this.tipSobe = tipSobe;
		this.gost = gost;
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.brojNocenja = brojNocenja;
		this.cena = cena;
		this.placeno = placeno;
		this.statusRezervacije = statusRezervacije;
		this.soba = soba;
		this.dodatneUsluge = dodatneUsluge;
		this.datumRezervacije = datumRezervacije;
		this.osobina = osobine;
	}
	
	public Rezervacije(int id, TipSobe tipSobe, Gost gost, LocalDate datumDolaska, LocalDate datumOdlaska,
			ArrayList<DodatneUsluge> dodatneUsluge,ArrayList<OsobinaSobe>  osobine) {
		super();
		this.id = id;
		this.tipSobe = tipSobe;
		this.gost = gost;
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.brojNocenja = ChronoUnit.DAYS.between(this.datumDolaska, this.datumOdlaska);
		this.cena = 0;
		this.placeno = false;
		this.statusRezervacije = StatusRezervacije.NA_CEKANJU;
		this.soba = null;
		this.dodatneUsluge = dodatneUsluge;
		this.datumRezervacije = LocalDate.now();
		this.osobina = osobine;
	}


	public Rezervacije(int id, TipSobe tipSobe, Gost gost, String datumDolaska, String datumOdlaska,
			ArrayList<DodatneUsluge> dodatneUsluge,ArrayList<OsobinaSobe>  osobine) {
		this.id = id;
		this.tipSobe = tipSobe;
		this.gost = gost;
		this.datumDolaska = LocalDate.parse(datumDolaska, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		;
		this.datumOdlaska = LocalDate.parse(datumOdlaska, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		;

		this.brojNocenja = ChronoUnit.DAYS.between(this.datumDolaska, this.datumOdlaska);

		this.placeno = false;
		this.statusRezervacije = StatusRezervacije.NA_CEKANJU;
		this.dodatneUsluge = dodatneUsluge;
		this.datumRezervacije = LocalDate.now();
		this.osobina = osobine;
	}

	public Rezervacije(int id, TipSobe tipSobe, Gost gost, String datumDolaska, String datumOdlaska) {
		this.id = id;
		this.tipSobe = tipSobe;
		this.gost = gost;
		this.datumDolaska = LocalDate.parse(datumDolaska, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		;
		this.datumOdlaska = LocalDate.parse(datumOdlaska, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		;
		this.brojNocenja = ChronoUnit.DAYS.between(this.datumDolaska, this.datumOdlaska);

		this.placeno = false;
		this.statusRezervacije = StatusRezervacije.NA_CEKANJU;
		this.dodatneUsluge = new ArrayList<DodatneUsluge>();
		this.datumRezervacije = LocalDate.now();
	}

	public int getId() {
		return id;
	}

	public TipSobe getTipSobe() {
		return tipSobe;
	}

	public Gost getGost() {
		return gost;
	}

	public LocalDate getDatumDolaska() {
		return datumDolaska;
	}

	public String getStringDatumDolaska() {
		return this.datumDolaska.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	}

	public LocalDate getDatumOdlaska() {
		return datumOdlaska;
	}

	public String getStringDatumOdlaska() {
		return this.datumOdlaska.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	}

	public String getStringDatumRezervacije() {
		return this.datumRezervacije.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	}

	public long getBrojNocenja() {
		return brojNocenja;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public boolean isPlaceno() {
		return placeno;
	}

	public void setPlaceno(boolean placeno) {
		this.placeno = placeno;
	}

	public StatusRezervacije getStatusRezervacije() {
		return this.statusRezervacije;
	}

	public void setStatusRezervacije(int status) {
		this.statusRezervacije.setStatus(status);
		;
	}

	public Soba getSoba() {
		return soba;
	}

	public void setSoba(Soba soba) {
		this.soba = soba;
	}

	public void setGost(Gost gost) {
		this.gost = gost;
	}

	public ArrayList<DodatneUsluge> getDodatneUsluge() {
		return dodatneUsluge;
	}

	public void setDodatneUsluge(ArrayList<DodatneUsluge> dodatneUsluge) {
		this.dodatneUsluge = dodatneUsluge;
	}

	public void dodajDodatnuUslugu(DodatneUsluge du) {
		dodatneUsluge.add(du);
	}

	public void otkaziDodatnuUslugu(DodatneUsluge du) {
		dodatneUsluge.remove(du);
	}

	public ArrayList<OsobinaSobe> getOsobina() {
		return osobina;
	}

	public void setOsobina(ArrayList<OsobinaSobe> osobina) {
		this.osobina = osobina;
	}
	
	
	

	public String ispisDodatnihUsluga() {
		String s = "";
		for (DodatneUsluge du : dodatneUsluge) {
//            System.out.println(du.toString());
			s += du.toString() + " ";

		}
		return s;
	}

	@Override
	public String toString() {
		return "Rezervacije [" + this.tipSobe.toString() + ", gost=" + this.gost.getIme() + ", datumDolaska="
				+ this.getStringDatumDolaska() + ", datumOdlaska=" + this.getStringDatumOdlaska() + ", brojNocenja="
				+ brojNocenja + ", Usluge: " + this.ispisDodatnihUsluga() + "," + this.getStatusRezervacije().toString()
				+ "]";
	}
}
