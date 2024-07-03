package entiteti;

public class StavkaCenovnika {
	private int id;
	private TipSobe tipSobe;
	private DodatneUsluge dodatnaUsluga;
	private double cena;
	
	
	
	
	
	public StavkaCenovnika(int id, TipSobe tipSobe, double cena) {
		super();
		this.id = id;
		this.tipSobe = tipSobe;
		this.dodatnaUsluga = null;
		this.cena = cena;
	}
	
	public StavkaCenovnika(int id,  DodatneUsluge dodatnaUsluga, double cena) {
		super();
		this.id = id;
		this.tipSobe = null;
		this.dodatnaUsluga = dodatnaUsluga;
		this.cena = cena;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public TipSobe getTipSobe() {
		return tipSobe;
	}
	
	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}
	
	
	public DodatneUsluge getDodatnaUsluga() {
		return dodatnaUsluga;
	}
	
	public void setDodatnaUsluga(DodatneUsluge dodatnaUsluga) {
		this.dodatnaUsluga = dodatnaUsluga;
	}
	
	
	public double getCena() {
		return cena;
	}
	
	public void setCena(double cena) {
		this.cena = cena;
	}
	
	public boolean proveraTipa() {
		if (this.tipSobe != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		if (this.tipSobe != null) {
			return "StavkaCenovnika [id=" + id + ", tipSobe=" + this.tipSobe.toString() + ", cena=" + cena + "]";
		}else {
			
            return "StavkaCenovnika [id=" + id + ", dodatnaUsluga=" + this.dodatnaUsluga.toString() + ", cena=" + cena + "]";
        }
	
	}
	
	
	
	
}
