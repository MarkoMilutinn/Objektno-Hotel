package view;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dijalozi.RezervacijeDijalog;
import entiteti.Gost;
import entiteti.Osoba;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;

import java.awt.BorderLayout;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import manage.ManagerRezervacije;
import entiteti.Rezervacije;
import entiteti.StatusRezervacije;
import kontroleri.RezervacijaKontroler;

public class GostView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JLabel trosak;
	private static Object[] header = {"ID", "Korisnicko ime", "Datum pocetka", "Datum kraja", "Tip sobe", "Dodatne usluge", "Status rezervacije", "Cena"};
	

	public GostView(Osoba o) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		Object[][] listaRezervacija = ManagerRezervacije.getInstance().prikazRezervacijaGosta((Gost) o);
		TableModel TModel = new DefaultTableModel(listaRezervacija, header) {
            private static final long serialVersionUID = 5668735439825556971L;

            @Override
            public Class<?> getColumnClass(int column) {
                if (getRowCount() == 0) {
                    return Object.class;
                }
                return getValueAt(0, column).getClass();
            }
        };

        table = new JTable(TModel);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.getTableHeader().setReorderingAllowed(false);

		scrollPane.setViewportView(table);
		
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		
        JPanel gostButtonspanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25)); 

		JButton dodajRezDugme = new JButton("Pravljenje rezervacije");
		dodajRezDugme.setFont(new Font("Tahoma", Font.PLAIN, 14));
		gostButtonspanel.add(dodajRezDugme);
		JButton otkazi_dugme = new JButton("Otkazivanje rezervacije");
		otkazi_dugme.setFont(new Font("Tahoma", Font.PLAIN, 14));
		gostButtonspanel.add(otkazi_dugme);
		contentPane.add(gostButtonspanel, BorderLayout.NORTH);
		
		dodajRezDugme.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        RezervacijeDijalog rv = new RezervacijeDijalog(false, null, o.getKorisnickoIme(), false);
		        rv.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosed(WindowEvent e) {
		                setTableData((Gost) o);
		            }
		        });
		        rv.setVisible(true);
		    }
		});


		
		otkazi_dugme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int confirm = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da otkazete rezervaciju?");
					if (confirm == JOptionPane.YES_OPTION) {
						int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
						Rezervacije r = ManagerRezervacije.getInstance().pronadjiRezervacijuPoId(id);
						if (r.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)) {
	                        RezervacijaKontroler.getInstance().otkaziRezervaciju(r.getId());
	                        JOptionPane.showMessageDialog(null, "Rezervacija je otkazana", "Otkazivanje rezervacije",
									JOptionPane.INFORMATION_MESSAGE);
	                        setTableData((Gost) o);
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Rezervacija nije na cekanju", "Greska",
	                                JOptionPane.WARNING_MESSAGE);
	                    }
					}
				}
			}
		});

		this.trosak = new JLabel();
		double trosakBroj = ManagerRezervacije.getInstance().ukupnaCenaRezervacija((Gost) o);
		trosak.setText("Trosak: " + trosakBroj);
		trosak.setFont(new Font("Tahoma", Font.PLAIN, 14));
		gostButtonspanel.add(trosak);
		
		JPanel gostOdjavi = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25));
		JButton odjavaBut = new JButton("Odjavi se");
		odjavaBut.setFont(new Font("Tahoma", Font.PLAIN, 14));
		gostOdjavi.add(odjavaBut);
		contentPane.add(gostOdjavi, BorderLayout.SOUTH);
		
		odjavaBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da se odjavite?");
				if (confirm == JOptionPane.YES_OPTION) {
					setVisible(false);
					dispose();
					StartView sv = new StartView();
					sv.setVisible(true);
				}
			}
		});
	}

//	private void setTableData(Object[][] data) {
//		DefaultTableModel model = new DefaultTableModel(data, header);
//		table.setModel(model);
//		
//		model.setRowCount(0);
//		for (int i = 0; i < data.length; i++) {
//			model.addRow(data[i]);
//		}
//		
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
//		table.setRowSorter(null);
//		table.setRowSorter(sorter);
//		table.revalidate();
//		table.repaint();
//	}
	
	private void setTableData(Gost gost) {
		Object[][] data = ManagerRezervacije.getInstance().prikazRezervacijaGosta(gost);
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setDataVector(data, header);
	    table.clearSelection();
	    table.revalidate();
	    table.repaint();
	    double trosakBroj = ManagerRezervacije.getInstance().ukupnaCenaRezervacija(gost);
		trosak.setText("Trosak: " + trosakBroj);
	    
	}


}

