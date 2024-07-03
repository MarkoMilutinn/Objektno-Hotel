package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dijalozi.CheckInDijalog;
import dijalozi.DodavanjeGostaDijalog;
import dijalozi.RezervacijeDijalog;
import entiteti.Rezervacije;
import entiteti.StatusRezervacije;
import kontroleri.RezervacijaKontroler;
import manage.ManagerGost;
import manage.ManagerRezervacije;
import manage.ManagerSoba;

public class RecepcionarView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Object[] headerSoba= {"ID", "Ljudi", "Tip Sobe", "Status"};
	private static Object[] headerRezervacije = {"ID", "Gost", "Soba", "Ljudi", "Datum od", "Datum do", "Cena", "Status"};
	private static Object[] headerDolasci = {"ID", "Gost", "Soba", "Ljudi", "Datum od", "Datum do", "Cena", "Status"};
	private static Object[] headerGost = {"ID", "Ime", "Prezime", "Pol", "Datum rodjenja", "Korisnicko ime"};
	private JTable tableRezervacije;
	private JTable tableSobe;
	private JTable tableDolasci;
	private JTable tableOdlasci;
	private JTable tableGosti;

	
	public RecepcionarView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel odjavaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton odjavaBut = new JButton("Odjavi se");
		odjavaBut.setFont(new Font("Tahoma", Font.PLAIN, 14));
		odjavaPanel.add(odjavaBut);
		contentPane.add(odjavaPanel, BorderLayout.NORTH);
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
		
//		sobe
		JPanel sobaPanel = new JPanel();
		tabbedPane.addTab("Sobe", null, sobaPanel, null);
		sobaPanel.setLayout(new BorderLayout(0, 0));
		
		
		Object[][] sobe = ManagerSoba.getInstance().prikazSoba();
		
		JLabel NaslovSobe = new JLabel("Sobe hotela");
		NaslovSobe.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 20));
		NaslovSobe.setHorizontalAlignment(JLabel.CENTER);
		sobaPanel.add(NaslovSobe, BorderLayout.NORTH);
		
		JScrollPane scrollPaneSobe = new JScrollPane();
		TableModel TModelSobe = new DefaultTableModel(sobe, headerSoba) {
            private static final long serialVersionUID = 5668735439825556971L;

            @Override
            public Class<?> getColumnClass(int column) {
                if (getRowCount() == 0) {
                    return Object.class;
                }
                return getValueAt(0, column).getClass();
            }
        };
        tableSobe = new JTable(TModelSobe);
        tableSobe.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
        tableSobe.getTableHeader().setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 18));
        tableSobe.getTableHeader().setReorderingAllowed(false);
        scrollPaneSobe.setViewportView(tableSobe);
                sobaPanel.add(scrollPaneSobe, BorderLayout.CENTER);
                
                
        TableRowSorter<TableModel> sorterSobe = new TableRowSorter<TableModel>(TModelSobe);
        tableSobe.setRowSorter(sorterSobe);
        
//        JPanel gostOdjavi = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25));
//		JButton odjavaBut = new JButton("Odjavi se");
//		odjavaBut.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		gostOdjavi.add(odjavaBut);
//		contentPane.add(gostOdjavi, BorderLayout.SOUTH);
//		
//		odjavaBut.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int confirm = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da se odjavite?");
//				if (confirm == JOptionPane.YES_OPTION) {
//					setVisible(false);
//					dispose();
//					StartView sv = new StartView();
//					sv.setVisible(true);
//				}
//			}
//		});
		
		
		
//		rezervacije
		JPanel panelRezervacije = new JPanel();
		panelRezervacije.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Rezervacije", null, panelRezervacije, null);
		
		
		Object[][] rezervacije = ManagerRezervacije.getInstance().prikazSvihRezervacija();
		
		JLabel NaslovRezervacije = new JLabel("Rezervacije hotela");
		NaslovRezervacije.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 20));
		NaslovRezervacije.setHorizontalAlignment(JLabel.CENTER);
		panelRezervacije.add(NaslovRezervacije, BorderLayout.NORTH);
		
		
		JPanel filtriranje =new JPanel();
		filtriranje.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
		JLabel filtriranjeLabel = new JLabel("Filtriranje: ");
		filtriranjeLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
		filtriranje.add(filtriranjeLabel);
		
		JTextField filter = new JTextField();
		filter.setColumns(10);
		filter.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
		filtriranje.add(filter);
		JButton filtriraj = new JButton("Filtriraj");
		filtriraj.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
		filtriranje.add(filtriraj);
		
		JPanel ukupno = new JPanel();
		ukupno.setLayout(new BorderLayout());
		ukupno.add(filtriranje, BorderLayout.WEST);
		ukupno.add(NaslovRezervacije, BorderLayout.CENTER);
		
		panelRezervacije.add(ukupno, BorderLayout.NORTH);
		
		JScrollPane scrollPaneRezervacije = new JScrollPane();
		
		TableModel TModelRezervacije = new DefaultTableModel(rezervacije, headerRezervacije) {
			private static final long serialVersionUID = 5668735439825556971L;

			@Override
			public Class<?> getColumnClass(int column) {
				if (getRowCount() == 0) {
					return Object.class;
				}
				return getValueAt(0, column).getClass();
			}
		};
		tableRezervacije = new JTable(TModelRezervacije);
		tableRezervacije.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
		tableRezervacije.getTableHeader().setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 18));
		tableRezervacije.getTableHeader().setReorderingAllowed(false);
		scrollPaneRezervacije.setViewportView(tableRezervacije);
		panelRezervacije.add(scrollPaneRezervacije, BorderLayout.CENTER);

		TableRowSorter<TableModel> sorterRezervacije = new TableRowSorter<>(TModelRezervacije);
		tableRezervacije.setRowSorter(sorterRezervacije);
		
        JPanel buttonsMetode = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25));
        JButton potvrdiRezervaciju = new JButton("Potvrdi rezervaciju");
        potvrdiRezervaciju.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
        buttonsMetode.add(potvrdiRezervaciju);
        JButton odbijRezervaciju = new JButton("Odbij rezervaciju");
        odbijRezervaciju.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
        buttonsMetode.add(odbijRezervaciju);
        JButton checkIn = new JButton("Check in");
        checkIn.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
        buttonsMetode.add(checkIn);
        JButton checkOut = new JButton("Check out");
        checkOut.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
        buttonsMetode.add(checkOut);
        JButton dodajRezervaciju = new JButton("Dodaj rezervaciju");
        dodajRezervaciju.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
        buttonsMetode.add(dodajRezervaciju);
        JButton izmeniRezervaciju = new JButton("Izmeni rezervaciju");
        izmeniRezervaciju.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
        buttonsMetode.add(izmeniRezervaciju);
        panelRezervacije.add(buttonsMetode, BorderLayout.SOUTH);
        
        filtriraj.addActionListener(new ActionListener() {
        	            @Override
        	            public void actionPerformed(ActionEvent e) {
        	                String text = filter.getText();
        	                if (text.length() == 0) {
        	                    sorterRezervacije.setRowFilter(null);
        	                } else {
        	                    sorterRezervacije.setRowFilter(new RowFilter<TableModel, Integer>() {
        	                        @Override
        	                        public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
        	                            for (int i = 0; i < entry.getValueCount(); i++) {
        	                                if (entry.getStringValue(i).toLowerCase().contains(text.toLowerCase())) {
        	                                    return true;
        	                                }
        	                            }
        	                            return false;
        	                        }
        	                    });
        	                }
        	            }
        	
        });
        
        potvrdiRezervaciju.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		int row = tableRezervacije.getSelectedRow();
        		if (row == -1) {
        			JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli", "Greska",
        					JOptionPane.WARNING_MESSAGE);
        		} else {
        			int confirm = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da potvrdite rezervaciju?");
        			if (confirm == JOptionPane.YES_OPTION) {
        				Rezervacije r = ManagerRezervacije.getInstance().pronadjiRezervacijuPoId(Integer.parseInt(tableRezervacije.getModel().getValueAt(row, 0).toString()));
        				if (r.getStatusRezervacije() != StatusRezervacije.NA_CEKANJU) {
        					JOptionPane.showMessageDialog(null, "Samo rezervacije koje su na cekanju", "Greska",
        							JOptionPane.WARNING_MESSAGE);
        					return;
        			}
        				else {
        					boolean uspesnostPotvrde=RezervacijaKontroler.getInstance().potvrdiRezervaciju(r);
							if (uspesnostPotvrde) {
								JOptionPane.showMessageDialog(null, "Rezervacija je potvrdjena", "Potvrda rezervacije",
										JOptionPane.INFORMATION_MESSAGE);
								setTableRezervacije();
							} else {
								JOptionPane.showMessageDialog(null, "Nema slobodnih soba za taj period", "Greska",
										JOptionPane.WARNING_MESSAGE);
								setTableRezervacije();
							}
        				}
        				
        		}
        	}
        	}});
        
        odbijRezervaciju.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		int row = tableRezervacije.getSelectedRow();
        		if (row == -1) {
        			JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli", "Greska",
        					JOptionPane.WARNING_MESSAGE);
        		} else {
        			int confirm = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da odbijete rezervaciju?");
        			if (confirm == JOptionPane.YES_OPTION) {
        				Rezervacije r = ManagerRezervacije.getInstance().pronadjiRezervacijuPoId(Integer.parseInt(tableRezervacije.getModel().getValueAt(row, 0).toString()));
        				if (r.getStatusRezervacije() != StatusRezervacije.NA_CEKANJU) {
        					JOptionPane.showMessageDialog(null, "Samo rezervacije koje su na cekanju", "Greska",
        							JOptionPane.WARNING_MESSAGE);
        					return;
        			}
        				else {
        					RezervacijaKontroler.getInstance().odbijRezervaciju(r);
        					JOptionPane.showMessageDialog(null, "Rezervacija je odbijena", "Odbijanje rezervacije", JOptionPane.INFORMATION_MESSAGE);
        					setTableRezervacije();
        					
        }
            }
        	}}});
        
        
        checkIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableRezervacije.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int confirm = JOptionPane.showConfirmDialog(null,
							"Da li ste sigurni da zelite da check inujete rezervaciju?");
					if (confirm == JOptionPane.YES_OPTION) {
						Rezervacije r = ManagerRezervacije.getInstance().pronadjiRezervacijuPoId(
								Integer.parseInt(tableRezervacije.getModel().getValueAt(row, 0).toString()));
						if (r.getStatusRezervacije() != StatusRezervacije.POTVRDJENA) {
							JOptionPane.showMessageDialog(null, "Samo rezervacije koje su potvrdjene", "Greska",
									JOptionPane.WARNING_MESSAGE);
							return;
						} else {
							if (r.getDatumDolaska().equals(java.time.LocalDate.now())) {
								JOptionPane.showMessageDialog(null, "Check in nije moguc za ovu rezervaciju, mora danasnje", "Greska",
										JOptionPane.WARNING_MESSAGE);
								return;
							}
							CheckInDijalog cid = new CheckInDijalog(r);
							cid.setVisible(true);
							setTableRezervacije();
							setTableSobe();
//							setTableDolasci();
//							setTableDanasnje();

						}
					}
				}
			}
		});
        
        
        checkOut.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               int row = tableRezervacije.getSelectedRow();
               if (row == -1) {
                   JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli", "Greska",
                           JOptionPane.WARNING_MESSAGE);
               } else {
                   int confirm = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da check outujete rezervaciju?");
                   if (confirm == JOptionPane.YES_OPTION) {
                       Rezervacije r = ManagerRezervacije.getInstance().pronadjiRezervacijuPoId(
                               Integer.parseInt(tableRezervacije.getModel().getValueAt(row, 0).toString()));
                       if (r.getStatusRezervacije() != StatusRezervacije.TRENUTNA) {
                           JOptionPane.showMessageDialog(null, "Samo rezervacije koje su check inovane", "Greska",
                                   JOptionPane.WARNING_MESSAGE);
                           return;
                       } else {
                           RezervacijaKontroler.getInstance().checkOut(r);
                           JOptionPane.showMessageDialog(null, "Check out je uspesan", "Check out", JOptionPane.INFORMATION_MESSAGE);
                           setTableRezervacije();
                           setTableSobe();
                           //                           setTableDolasci();
                           //                           setTableDanasnje();
                       }
                   }
               }
           }
        });
        
		dodajRezervaciju.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                RezervacijeDijalog rd = new RezervacijeDijalog(false,null,null,false);
                rd.setVisible(true);
//                JOptionPane.showMessageDialog(null, "Rezervacija je dodata", "Dodavanje rezervacije",
//                        JOptionPane.INFORMATION_MESSAGE);
                setTableRezervacije();
            }
        });
        
        
       
        
        
        izmeniRezervaciju.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableRezervacije.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int confirm = JOptionPane.showConfirmDialog(null,
							"Da li ste sigurni da zelite da izmenite rezervaciju?");
					if (confirm == JOptionPane.YES_OPTION) {
						Rezervacije r = ManagerRezervacije.getInstance().pronadjiRezervacijuPoId(
								Integer.parseInt(tableRezervacije.getModel().getValueAt(row, 0).toString()));
						if (r.getStatusRezervacije() == StatusRezervacije.NA_CEKANJU || r.getStatusRezervacije() == StatusRezervacije.POTVRDJENA) {
							RezervacijeDijalog rd = new RezervacijeDijalog(false,r,r.getGost().getKorisnickoIme(),true);
							rd.setVisible(true);
							JOptionPane.showMessageDialog(null, "Rezervacija je izmenjena", "Izmena rezervacije",
									JOptionPane.INFORMATION_MESSAGE);
							setTableRezervacije();
						} else {
							JOptionPane.showMessageDialog(null, "Rezervacija nije na cekanju ili potvrdjena", "Greska",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}
			}
		});
        
        
//		pregled dolazaka i odlazaka
		
		JPanel panelDolasci = new JPanel();
		tabbedPane.addTab("Dolasci i odlasci", null, panelDolasci, null);
		
		panelDolasci.setLayout(new BorderLayout(0, 0));
		JTabbedPane tabbedPaneDolasci = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneDolasci.setBounds(25, 500, 904, 350);
		panelDolasci.add(tabbedPaneDolasci, BorderLayout.CENTER);
		
		JPanel panelDolasci1 = new JPanel(new BorderLayout());
		tabbedPaneDolasci.addTab("Dolasci", null, panelDolasci , null);
		
		Object[][] dolasci = ManagerRezervacije.getInstance().prikazDolazaka();
		Object [] [] odlasci = ManagerRezervacije.getInstance().prikazOdlazaka();
		
		this.tableDolasci = new JTable(new DefaultTableModel(dolasci, headerDolasci));
		panelDolasci.add(new JScrollPane(tableDolasci), BorderLayout.CENTER);
		
		
		JPanel panelOdlasci = new JPanel(new BorderLayout());
		tabbedPaneDolasci.addTab("Odlasci", null, panelOdlasci, null);
		
		tableOdlasci = new JTable(new DefaultTableModel(odlasci, headerDolasci));
		panelOdlasci.add(new JScrollPane(tableOdlasci), BorderLayout.CENTER);
		
		JPanel promene = new JPanel(new BorderLayout());
		tabbedPaneDolasci.addTab("Sve", null, promene, null);
		
		
		
//     za goste
	   JPanel gostPanel = new JPanel();
	   gostPanel.setLayout(new BorderLayout(0, 0));	
	   tabbedPane.addTab("Gosti", null, gostPanel, null);
	   
	   
	   JLabel NaslovGosti = new JLabel("Gosti hotela");
	   NaslovGosti.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 20));
	   NaslovGosti.setHorizontalAlignment(JLabel.CENTER);
	   gostPanel.add(NaslovGosti, BorderLayout.NORTH);
	   
	   Object[][] gosti = ManagerGost.getInstance().prikazGostijuTabela();
	   JScrollPane scrollPaneGosti = new JScrollPane();
	   TableModel modelGosti= new DefaultTableModel(gosti, headerGost) {
		   private static final long serialVersionUID = 5668735439825556971L;
		   @Override
		   public Class<?> getColumnClass(int column) {
               if (getRowCount() == 0) {
                   return Object.class;
               }
               return getValueAt(0, column).getClass();
           }
	   };
	   
	   tableGosti = new JTable(modelGosti);
	   tableGosti.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
	   tableGosti.getTableHeader().setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 18));
	   tableGosti.getTableHeader().setReorderingAllowed(false);
	   scrollPaneGosti.setViewportView(tableGosti);
	   gostPanel.add(scrollPaneGosti, BorderLayout.CENTER);
	   
	   TableRowSorter<TableModel> sorterGosti = new TableRowSorter<>(modelGosti);
	   tableGosti.setRowSorter(sorterGosti);
	   
	   JPanel buttonsGosti = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25));
	   JButton dodajGosta = new JButton("Dodaj gosta");
	   dodajGosta.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
	   buttonsGosti.add(dodajGosta);
	   gostPanel.add(buttonsGosti, BorderLayout.SOUTH);
	   
		dodajGosta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DodavanjeGostaDijalog dgd = new DodavanjeGostaDijalog(null);
				dgd.setVisible(true);
				setTableGosti();
			}
		});
	   
	   
	   
	   
	   
	   
	   
        
       
        
        
		
		
		
		
		
		
		
		
		
		
	}
	
	private void setTableRezervacije() {
		Object[][] rezervacije = ManagerRezervacije.getInstance().prikazSvihRezervacija();
		DefaultTableModel model = (DefaultTableModel) tableRezervacije.getModel();
		model.setDataVector(rezervacije, headerRezervacije);
		tableRezervacije.clearSelection();
	    tableRezervacije.revalidate();
	    tableRezervacije.repaint();
		
	}
	private void setTableSobe() {
		Object[][] sobe = ManagerSoba.getInstance().prikazSoba();
		DefaultTableModel model = (DefaultTableModel) tableSobe.getModel();
		model.setDataVector(sobe, headerSoba);
		tableSobe.clearSelection();
		tableSobe.revalidate();
		tableSobe.repaint();
	}

}
