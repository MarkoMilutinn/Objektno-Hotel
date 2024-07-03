package dijalozi;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.jdatepicker.impl.*;
import entiteti.*;
import kontroleri.RezervacijaKontroler;
import manage.*;
import utils.*;

public class RezervacijeDijalog extends JDialog {

    private static final long serialVersionUID = 8735125040034589330L;
    private JTextField username;
    private JComboBox<String> comboBox;
    private JPanel uslugePanel;
    private ArrayList<JCheckBox> usluge;
    private ArrayList<JCheckBox> osobineSobaCheck;
    private JLabel ukupnaCena;
    private JDatePickerImpl datePickerOd;
    private JDatePickerImpl datePickerDo;

    public RezervacijeDijalog(boolean Admin, Rezervacije r, String korisnik, boolean Edit) {
        setBounds(100, 100, 650, 550);
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc_contentPanel = new GridBagConstraints();
        gbc_contentPanel.gridwidth = 1;
        gbc_contentPanel.fill = GridBagConstraints.BOTH;

        JLabel usernameLabel = new JLabel("Korisnicko ime: ");
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 0;
        gbc_contentPanel.anchor = GridBagConstraints.WEST;
        contentPanel.add(usernameLabel, gbc_contentPanel);

        this.username = new JTextField();
        username.setColumns(10);
        gbc_contentPanel.gridx = 1;
        gbc_contentPanel.gridy = 0;
        gbc_contentPanel.gridwidth = 2;
        contentPanel.add(username, gbc_contentPanel);

        JLabel tipSobe = new JLabel("Tip sobe: ");
        tipSobe.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 1;
        gbc_contentPanel.gridwidth = 1;
        contentPanel.add(tipSobe, gbc_contentPanel);

        this.comboBox = new JComboBox<>();
        napuniComboBox(comboBox, ManagerTipSobe.getInstance().getTipSobe());
        gbc_contentPanel.gridx = 1;
        gbc_contentPanel.gridy = 1;
        contentPanel.add(comboBox, gbc_contentPanel);

        JLabel datumOd = new JLabel("Datum od: ");
        datumOd.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 2;
        contentPanel.add(datumOd, gbc_contentPanel);

        UtilDateModel modelOd = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanelOd = new JDatePanelImpl(modelOd, p);
        datePickerOd = new JDatePickerImpl(datePanelOd, new DateLabelFormatter());
        gbc_contentPanel.gridx = 1;
        gbc_contentPanel.gridy = 2;
        contentPanel.add(datePickerOd, gbc_contentPanel);

        JLabel datumDo = new JLabel("Datum do: ");
        datumDo.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 3;
        contentPanel.add(datumDo, gbc_contentPanel);

        UtilDateModel modelDo = new UtilDateModel();
        JDatePanelImpl datePanelDo = new JDatePanelImpl(modelDo, p);
        datePickerDo = new JDatePickerImpl(datePanelDo, new DateLabelFormatter());
        gbc_contentPanel.gridx = 1;
        gbc_contentPanel.gridy = 3;
        contentPanel.add(datePickerDo, gbc_contentPanel);

        JLabel dodatneUsluge = new JLabel("Dodatne usluge: ");
        dodatneUsluge.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 4;
        gbc_contentPanel.gridwidth = 2;
        contentPanel.add(dodatneUsluge, gbc_contentPanel);

        uslugePanel = new JPanel();
        uslugePanel.setLayout(new BoxLayout(uslugePanel, BoxLayout.Y_AXIS));
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 5;
        gbc_contentPanel.gridwidth = 2;
        contentPanel.add(uslugePanel, gbc_contentPanel);
        usluge = napuniUsluge(uslugePanel, ManagerDodatneUsluge.getInstance().getDodatneUsluge());

        JLabel osobineSoba = new JLabel("Osobine soba: ");
        osobineSoba.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 6;
        gbc_contentPanel.anchor = GridBagConstraints.WEST;
        contentPanel.add(osobineSoba, gbc_contentPanel);

        JPanel osobinePanel = new JPanel();
        osobinePanel.setLayout(new BoxLayout(osobinePanel, BoxLayout.Y_AXIS));
        gbc_contentPanel.gridx = 1;
        gbc_contentPanel.gridy = 6;
        gbc_contentPanel.gridwidth = 1;
        contentPanel.add(osobinePanel, gbc_contentPanel);

        ArrayList<OsobinaSobe> osobineSobe = new ArrayList<>();
        osobineSobe.add(OsobinaSobe.KLIMA);
        osobineSobe.add(OsobinaSobe.TV);
        osobineSobe.add(OsobinaSobe.BALKON);
        osobineSobe.add(OsobinaSobe.PUSACKA);
        osobineSobe.add(OsobinaSobe.NEPUSACKA);

        osobineSobaCheck = napuniOsobine(osobinePanel, osobineSobe);

        ukupnaCena = new JLabel("Ukupna cena: ");
        ukupnaCena.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 7;
        gbc_contentPanel.gridwidth = 2;
        contentPanel.add(ukupnaCena, gbc_contentPanel);

        if (korisnik != null && !Edit) {
            username.setText(korisnik);
            username.setEnabled(false);
        }

        if (korisnik != null && Edit && !Admin) {
            username.setText(korisnik);
            username.setEnabled(false);
        }

        if (r != null) {
            LocalDate datumDolaska = r.getDatumDolaska();
            LocalDate datumOdlaska = r.getDatumOdlaska();

            modelOd.setValue(Date.from(datumDolaska.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            modelOd.setSelected(true);

            modelDo.setValue(Date.from(datumOdlaska.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            modelDo.setSelected(true);

            if (!Admin) {
                datePickerDo.getComponent(1).setEnabled(false);
                datePickerOd.getComponent(1).setEnabled(false);
            }
        }

        ActionListener updatePriceListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCena();
            }
        };

        comboBox.addActionListener(updatePriceListener);
        datePickerOd.addActionListener(updatePriceListener);
        datePickerDo.addActionListener(updatePriceListener);

        for (JCheckBox cb : usluge) {
            cb.addActionListener(updatePriceListener);
        }

        for (JCheckBox cb : osobineSobaCheck) {
            cb.addActionListener(updatePriceListener);
        }

        getContentPane().add(contentPanel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (username.getText().isEmpty()) {
            		                    JOptionPane.showMessageDialog(RezervacijeDijalog.this, "Morate uneti korisnicko ime!");
            		                    return;
            	}else{
					if (ManagerGost.getInstance().pronadjiGostaPoKorisnickom(username.getText()) == null) {
						JOptionPane.showMessageDialog(RezervacijeDijalog.this,
								"Gost sa unetim korisnickim imenom ne postoji!");
						return;
					}
				}
				if (comboBox.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(RezervacijeDijalog.this, "Morate izabrati tip sobe!");
					return;
            		
            	}
                LocalDate datumOd = ((UtilDateModel) datePickerOd.getModel()).getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				
                LocalDate datumDo = ((UtilDateModel) datePickerDo.getModel()).getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (datumOd.isBefore(LocalDate.now())) {
					JOptionPane.showMessageDialog(RezervacijeDijalog.this, "Datum dolaska ne moze biti u proslosti!");
					return;
				}
                if (datumDo.isBefore(datumOd)) {
					JOptionPane.showMessageDialog(RezervacijeDijalog.this,
							"Datum dolaska ne moze biti posle datuma odlaska!");
					return;
                }
                TipSobe selectedTipSobe = ManagerTipSobe.getInstance().getTipSobe().get(comboBox.getSelectedIndex());
                ArrayList<DodatneUsluge> selectedUsluge = getSelectedUsluge();
                ArrayList<OsobinaSobe> selectedOsobine = getSelectedOsobine();

                if (!Edit) {
                    Rezervacije novaRezervacija = RezervacijaKontroler.getInstance().dodajRezervaciju(selectedTipSobe, ManagerGost.getInstance().pronadjiGostaPoKorisnickom(username.getText()), datumOd, datumDo, selectedUsluge, selectedOsobine);

                    JOptionPane.showMessageDialog(RezervacijeDijalog.this, "Nova rezervacija kreirana!");
                    dispose();
              
                }
            }
        });
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 8;
        gbc_contentPanel.gridwidth = 1;
        contentPanel.add(okButton, gbc_contentPanel);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(e -> dispose());
        gbc_contentPanel.gridx = 1;
        gbc_contentPanel.gridy = 8;
        contentPanel.add(cancelButton, gbc_contentPanel);

        getContentPane().add(contentPanel, BorderLayout.CENTER);
    }

    private void napuniComboBox(JComboBox<String> comboBox, ArrayList<TipSobe> tipSobe) {
        for (TipSobe tip : tipSobe) {
            comboBox.addItem(tip.getLjudi());
        }
    }

    private ArrayList<JCheckBox> napuniUsluge(JPanel uslugePanel, ArrayList<DodatneUsluge> dodatneUsluge) {
        ArrayList<JCheckBox> usluge = new ArrayList<>();
        for (DodatneUsluge du : dodatneUsluge) {
            JCheckBox cb = new JCheckBox(du.getNaziv());
            uslugePanel.add(cb);
            usluge.add(cb);
        }
        return usluge;
    }

    private ArrayList<JCheckBox> napuniOsobine(JPanel osobinePanel, ArrayList<OsobinaSobe> osobineSobe) {
        ArrayList<JCheckBox> osobine = new ArrayList<>();
        for (OsobinaSobe os : osobineSobe) {
            JCheckBox cb = new JCheckBox(os.name());
            osobinePanel.add(cb);
            osobine.add(cb);
        }
        return osobine;
    }

    private void updateCena() {
        TipSobe selectedTipSobe = ManagerTipSobe.getInstance().getTipSobe().get(comboBox.getSelectedIndex());
        ArrayList<DodatneUsluge> selectedUsluge = getSelectedUsluge();
        ArrayList<OsobinaSobe> selectedOsobine = getSelectedOsobine();

        try {
            if (datePickerOd == null || datePickerDo == null) {
                throw new NullPointerException("Date pickers are not initialized.");
            }
            Date dateOd = (Date) datePickerOd.getModel().getValue();
            Date dateDo = (Date) datePickerDo.getModel().getValue();

            if (dateOd == null || dateDo == null) {
                throw new NullPointerException("Datum od or Datum do is null");
            }
            LocalDate datumOd = dateOd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate datumDo = dateDo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            Rezervacije r = new Rezervacije(
                100,  // id, random
                selectedTipSobe,
                ManagerGost.getInstance().pronadjiGostaPoKorisnickom(username.getText()),
                datumOd,
                datumDo,
                selectedUsluge,
                selectedOsobine
            );

            double totalPrice = RezervacijaKontroler.getInstance().dobijanjeCene(r);
            
            ukupnaCena.setText("Ukupna cena: " + totalPrice);
        } catch (NullPointerException e) {
            ukupnaCena.setText("Ukupna cena: /");
        } catch (DateTimeParseException e) {
            ukupnaCena.setText("Ukupna cena: /");
        } catch (Exception e) {
            ukupnaCena.setText("Ukupna cena: /");
        }
    }



    private ArrayList<DodatneUsluge> getSelectedUsluge() {
        ArrayList<DodatneUsluge> selectedUsluge = new ArrayList<>();
        for (JCheckBox cb : usluge) {
            if (cb.isSelected()) {
                selectedUsluge.add(ManagerDodatneUsluge.getInstance().getDodatneUsluge().get(usluge.indexOf(cb)));
            }
        }
        return selectedUsluge;
    }

    private ArrayList<OsobinaSobe> getSelectedOsobine() {
        ArrayList<OsobinaSobe> selectedOsobine = new ArrayList<>();
        for (JCheckBox cb : osobineSobaCheck) {
            if (cb.isSelected()) {
                selectedOsobine.add(OsobinaSobe.valueOf(cb.getText()));
            }
        }
        return selectedOsobine;
    }
}
