package kontroleri;

import java.time.LocalDate;
import java.util.HashMap;

import entiteti.Rezervacije;
import entiteti.StatusRezervacije;
import entiteti.TipSobe;
import manage.ManagerRezervacije;
import manage.ManagerTipSobe;

public class IzvestajiKontroler {
	private static IzvestajiKontroler instance;
	
	private IzvestajiKontroler() {
		super();
	}

	public static IzvestajiKontroler getInstance() {
		if (instance == null) {
			instance = new IzvestajiKontroler();
		}
		return instance;
	}
	
	public HashMap<String, double[]> prihodiZaChart() {
		ManagerTipSobe mts = ManagerTipSobe.getInstance();
		ManagerRezervacije mr = ManagerRezervacije.getInstance();
		HashMap<String, double[]> prihodi = new HashMap<String, double[]>();
		for (TipSobe ts: mts.getTipSobe()) {
			prihodi.put(ts.getTipSobe(), new double[12]);
		}
		String ukupno = "Ukupno";
		prihodi.put(ukupno, new double[12]);
		// index se racuna kao (mesec + offset) % 12 da bi trenutni mesec bio 11 a mesec pre godinu dana 0
		int offset = 11 - LocalDate.now().getMonthValue();
		for (Rezervacije r: mr.getRezervacije()) {
			// Ako je rezervacija starija od godinu dana ili je rezervacija za sledeci mesec, preskoci je
			if (r.getDatumOdlaska().isBefore(LocalDate.now().minusMonths(12)) || r.getDatumDolaska().isAfter(LocalDate.now().withMonth(LocalDate.now().getMonthValue()%12 + 1).minusDays(1))) {
				continue;
			}
			if (r.getSoba() == null) {
				continue;
			}
			int mesec = r.getDatumDolaska().getMonthValue();
			int index = (mesec + offset) % 12;
			prihodi.get(r.getSoba().getTipSobe().getTipSobe())[index] += r.getCena();
			prihodi.get(ukupno)[index] += r.getCena();
		}
		return prihodi;
	}
	
	public HashMap<String, Integer> statusiRezervacijaPeriod(LocalDate pocetak, LocalDate kraj) {
		ManagerRezervacije mr = ManagerRezervacije.getInstance();
		HashMap<String, Integer> statusi = new HashMap<String, Integer>();
		for (StatusRezervacije status : StatusRezervacije.values()) {
			statusi.put(status.toString(), 0);
		}
		for (Rezervacije r : mr.getRezervacije()) {
			if (r.getDatumRezervacije().isBefore(pocetak) || r.getDatumRezervacije().isAfter(kraj)) {
				continue;
			}
			statusi.put(r.getStatusRezervacije().toString(), statusi.get(r.getStatusRezervacije().toString()) + 1);
		}
		return statusi;
	}
}
