package kontroleri;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;

import entiteti.Admin;
import entiteti.Recepcionar;
import entiteti.Rezervacije;
import entiteti.Soba;
import entiteti.Sobarica;
import entiteti.StatusRezervacije;
import entiteti.TipSobe;
import manage.ManagerOsoba;
import manage.ManagerRezervacije;
import manage.ManagerSoba;
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
	
	public double[] prihodiRashodiZaPeriod(LocalDate pocetak, LocalDate kraj) {
		// prihodiRashodi[0] - prihodi
		// prihodiRashodi[1] - rashodi
		double[] prihodiRashodi = new double[2];
		ManagerRezervacije mr = ManagerRezervacije.getInstance();
		ManagerOsoba mo = ManagerOsoba.getInstance();
		for (Rezervacije r: mr.getRezervacije()) {
			if (r.getDatumOdlaska().isBefore(pocetak) || r.getDatumDolaska().isAfter(kraj)) {
				continue;
			}
			// if (r.isPlaceno()) {
			//	 prihodiRashodi[0] += r.getCena();
			// }
			prihodiRashodi[0] += r.getCena();
		}
		int dani = pocetak.until(kraj).getDays() + pocetak.until(kraj).getMonths() * 30 + pocetak.until(kraj).getYears() * 365;
		System.out.println("Dani: " + dani);
		for (Sobarica s : mo.getSobarice()) {
			prihodiRashodi[1] += s.getPlata() * dani / 30.0;
		}
		for (Recepcionar r : mo.getRecepcionari()) {
			prihodiRashodi[1] += r.getPlata() * dani / 30.0;
		}
		for (Admin a : mo.getAdmini()) {
			//prihodiRashodi[1] += a.getPlata() * dani / 30.0;
		}
		
		return prihodiRashodi;
	}
	
	public HashMap<Integer, String[]> podaciOSobamaZaPeriod(LocalDate pocetak, LocalDate kraj) {
		// kljuc je broj sobe
		// String[] sadrži redom tip sobe, status sobe, broj nocenja i prihode
		HashMap<Integer, String[]> podaci = new HashMap<Integer, String[]>();
		ManagerRezervacije mr = ManagerRezervacije.getInstance();
		ManagerSoba ms = ManagerSoba.getInstance();
		for(Soba s: ms.getSobe()) {
			podaci.put(s.getId(), new String[] {s.getTipSobe().getTipSobe().toString(), s.getStatusSobe().toString(), "0", "0"});
		}
		for (Rezervacije r: mr.getRezervacije()) {
			if (r.getDatumOdlaska().isBefore(pocetak) || r.getDatumDolaska().isAfter(kraj)) {
				continue;
			}
			if (r.getSoba() == null) {
				continue;
			}
			LocalDate levi = r.getDatumDolaska().isBefore(pocetak) ? pocetak : r.getDatumDolaska();
			LocalDate desni = r.getDatumOdlaska().isAfter(kraj) ? kraj : r.getDatumOdlaska();
			int brojNocenja = levi.until(desni).getDays();
			double prihod = r.getCena();
			podaci.get(r.getSoba().getId())[2] = Integer.toString(Integer.parseInt(podaci.get(r.getSoba().getId())[2]) + brojNocenja);
			podaci.get(r.getSoba().getId())[3] = Double.toString(Double.parseDouble(podaci.get(r.getSoba().getId())[3]) + prihod);
		}
		return podaci;
	}
}
