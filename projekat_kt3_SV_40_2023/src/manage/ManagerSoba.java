package manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entiteti.OsobinaSobe;
import entiteti.Soba;
import entiteti.StatusSobe;
import entiteti.TipSobe;
import utils.FileCheck;

public class ManagerSoba {

    private ArrayList<Soba> sobe;
    private String sobeFajl;
    private static ManagerSoba instance;

    private ManagerSoba(ArrayList<Soba> sobe, String sobeFajl) {
        super();
        this.sobe = sobe;
        this.sobeFajl = sobeFajl;
    }

    public static ManagerSoba getInstance() {
        if (instance == null) {
            instance = new ManagerSoba(new ArrayList<Soba>(), "data/sobe.txt");
            instance.UcitajSobu();
        }
        return instance;
    }

    public ArrayList<Soba> getSobe() {
        return sobe;
    }

    public int dobijanjeIdSobe() {
        return sobe.size() + 1;
    }

    public Soba pronadjiSobuPoId(int id) {
        Soba ret = null;
        for (Soba neki : sobe) {
            if (neki.getId() == id) {
                ret = neki;
                break;
            }
        }
        return ret;
    }

    public Soba pronadjiSobuPoTipu(TipSobe tip) {
        Soba ret = null;
        for (Soba neki : sobe) {
            if (neki.getTipSobe().equals(tip)) {
                ret = neki;
                break;
            }
        }
        return ret;
    }

    public ArrayList<Soba> pronadjiSobePoTipu(TipSobe tip) {
        ArrayList<Soba> ret = new ArrayList<Soba>();
        for (Soba neki : sobe) {
            if (neki.getTipSobe().equals(tip)) {
                ret.add(neki);
            }
        }
        return ret;
    }

    public ArrayList<Soba> pronadjiSobePoStatusu(StatusSobe status) {
        ArrayList<Soba> ret = new ArrayList<Soba>();
        for (Soba neki : sobe) {
            if (neki.getStatusSobe().equals(status)) {
                ret.add(neki);
            }
        }
        return ret;
    }

    public Object[][] prikazSoba() {
        Object[][] ret = new Object[sobe.size()][4];
        for (int i = 0; i < sobe.size(); i++) {
            Soba s = sobe.get(i);
            ret[i][0] = s.getId();
            ret[i][1] = s.getTipSobe().getLjudi();
            ret[i][2] = s.getTipSobe().getTipSobe();
            ret[i][3] = s.getStatusSobe().toString();
        }
        return ret;
    }

    public boolean UcitajSobu() {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(sobeFajl));
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    sobe.add(parseSoba(data));
                }
            }
            bfr.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public Soba parseSoba(String[] data) {
        // Ensure data array has at least 3 elements to avoid ArrayIndexOutOfBoundsException
        if (data.length < 3) {
            throw new IllegalArgumentException("Invalid data format: " + String.join(",", data));
        }

        int id = Integer.parseInt(data[0].trim());
        ManagerTipSobe mts = ManagerTipSobe.getInstance();
        TipSobe tip = mts.pronadjiTipSobePoId(Integer.parseInt(data[1].trim()));
        StatusSobe status = StatusSobe.getStatus(Integer.parseInt(data[2].trim()));

        ArrayList<OsobinaSobe> osobine = new ArrayList<OsobinaSobe>();

        // Check if there is a fourth element (index 3) in data array for osobine
        if (data.length > 3) {
            String os = data[3].trim();
            if (!os.equals("")) {
                String[] osString = os.split(";");
                for (String o : osString) {
                    osobine.add(OsobinaSobe.getOsobina(Integer.parseInt(o.trim())));
                }
            }
        }

        return new Soba(id, tip, status, osobine);
    }

    public void dodajSobu(TipSobe tip, StatusSobe status, ArrayList<OsobinaSobe> osobine) {
        int id = dobijanjeIdSobe();
        Soba s = new Soba(id, tip, status, osobine);
        sobe.add(s);
        snimiSobe();
    }

    public void dodajSobu(TipSobe tip, ArrayList<OsobinaSobe> osobine) {
        int id = dobijanjeIdSobe();
        Soba s = new Soba(id, tip, StatusSobe.SLOBODNA, osobine);
        sobe.add(s);
        snimiSobe();
    }

    public void dodajSobu(Soba s) {
        sobe.add(s);
        snimiSobe();
    }

    public void izmenaSobe(int id, TipSobe tip, StatusSobe status, ArrayList<OsobinaSobe> osobine) {
        Soba s = pronadjiSobuPoId(id);
        s.setTipSobe(tip);
        s.setStatusSobe(status);
        s.setOsobine(osobine);
        snimiSobe();
    }

    public void brisanjeSobe(int id) {
        Soba s = pronadjiSobuPoId(id);
        sobe.remove(s);
        snimiSobe();
    }

    public void snimiSobe() {
        StringBuilder data = new StringBuilder();
        for (Soba s : sobe) {
            ArrayList<OsobinaSobe> osobine = s.getOsobine();
            if (osobine.size() > 0) {
                StringBuilder os = new StringBuilder();
                for (OsobinaSobe o : osobine) {
                    os.append(o.getRedniBrojOsobine()).append(";");
                }
                data.append(s.getId()).append(",").append(s.getTipSobe().getId()).append(",")
                        .append(s.getStatusSobe().getRedniBrojStatusa()).append(",").append(os).append("\n");
            } else {
                data.append(s.getId()).append(",").append(s.getTipSobe().getId()).append(",")
                        .append(s.getStatusSobe().getRedniBrojStatusa()).append("\n");
            }
        }
        try {
            File f = FileCheck.getFile(sobeFajl);
            java.io.FileWriter fw = new java.io.FileWriter(f);
            fw.write(data.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
