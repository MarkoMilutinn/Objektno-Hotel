package entiteti;

import java.util.ArrayList;
import java.util.List;

public class Soba {
    private int id;
    
    private TipSobe tipSobe;
    private StatusSobe statusSobe;
    private ArrayList<OsobinaSobe>  osobine;
    
 

    public Soba(int id, TipSobe tipSobe,ArrayList<OsobinaSobe>  osobine) {
        this.id = id;
        
        this.osobine = osobine;
        this.tipSobe = tipSobe;
        this.statusSobe = StatusSobe.SLOBODNA;
    
    }

	public Soba(int id, TipSobe tipSobe, StatusSobe statusSobe,ArrayList<OsobinaSobe>  osobine) {
		this.id = id;
		this.tipSobe = tipSobe;
		this.statusSobe = statusSobe;
		this.osobine = osobine;
	}
    

    public Soba(int idSobe, int idTipSobe, String tipSobe, String ljudi) {
        this.id = idSobe;
        this.tipSobe = new TipSobe(idTipSobe, tipSobe, ljudi);
        this.statusSobe = StatusSobe.SLOBODNA;



    }





	public int getId() {
        return id;
    }

    
    public TipSobe getTipSobe() {
        return tipSobe;
    }

    public void setTipSobe(TipSobe tipSobe) {
        this.tipSobe = tipSobe;
    }

    public StatusSobe getStatusSobe() {
        return statusSobe;
    }

    public void setStatusSobe(StatusSobe statusSobe) {
        this.statusSobe = statusSobe;
    }

	public ArrayList<OsobinaSobe> getOsobine() {
		return osobine;
	}
	
	public void setOsobine(ArrayList<OsobinaSobe> osobine) {
		this.osobine = osobine;
	}
	
//    public List<Rezervacije> getRezervacija() {
//        return rezervacija;
//    }
//
//    public void dodajRezervacija(Rezervacije rezervacija) {
//        this.rezervacija.add(rezervacija);
//    }
//    

    @Override
    public String toString() {
        return "Soba [id = " + this.id + ", Status sobe: " + this.statusSobe.toString()
                +", " + getTipSobe().toString() + "]";
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Soba))
			return false;
		Soba other = (Soba) obj;
		if (id != other.id)
			return false;
		if (statusSobe != other.statusSobe)
			return false;
		if (tipSobe == null) {
			if (other.tipSobe != null)
				return false;
		} else if (!tipSobe.equals(other.tipSobe))
			return false;
		return true;
	}

    
}
