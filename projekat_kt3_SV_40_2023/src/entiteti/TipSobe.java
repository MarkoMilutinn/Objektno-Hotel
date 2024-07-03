package entiteti;

public class TipSobe {
    private int id;
    private String tipSobe;
    private String ljudi;





   

    public TipSobe(int id, String tipSobe, String ljudi) {
		super();
		this.id = id;
		this.tipSobe = tipSobe;
		this.ljudi = ljudi;
	}
	
    
    
    
    
    public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public String getTipSobe() {
		return tipSobe;
	}





	public void setTipSobe(String tipSobe) {
		this.tipSobe = tipSobe;
	}





	public String getLjudi() {
		return ljudi;
	}





	public void setLjudi(String ljudi) {
		this.ljudi = ljudi;
	}










	@Override
	public String toString() {
		return "[Tip sobe=" + tipSobe + ", " + ljudi  + "]";
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipSobe other = (TipSobe) obj;
        if (id != other.id)
            return false;
        if (tipSobe == null) {
            if (other.tipSobe != null)
                return false;
        } else if (!tipSobe.equals(other.tipSobe))
            return false;
        return true;
    }


   

}
