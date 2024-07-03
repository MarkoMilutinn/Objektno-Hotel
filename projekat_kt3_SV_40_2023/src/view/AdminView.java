package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;

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

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategorySeries.CategorySeriesRenderStyle;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;

import entiteti.Osoba;
import entiteti.Rezervacije;
import entiteti.StatusRezervacije;
import kontroleri.IzvestajiKontroler;
import kontroleri.RezervacijaKontroler;
import manage.ManagerRezervacije;
import manage.ManagerSoba;

public class AdminView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Object[] headerSoba = { "ID", "Ljudi", "Tip Sobe", "Status" };
	private static Object[] headerRezervacije = { "ID", "Gost", "Soba", "Ljudi", "Datum od", "Datum do", "Cena",
			"Status" };
	private JTable tableRezervacije;
	private JTable tableSobe;

	public AdminView(Osoba o) {
		setTitle("Admin panel");
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

		JPanel filtriranje = new JPanel();
		filtriranje.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
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
//        JButton dodajRezervaciju = new JButton("Dodaj rezervaciju");
//        dodajRezervaciju.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
//        buttonsMetode.add(dodajRezervaciju);
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
					int confirm = JOptionPane.showConfirmDialog(null,
							"Da li ste sigurni da zelite da potvrdite rezervaciju?");
					if (confirm == JOptionPane.YES_OPTION) {
						Rezervacije r = ManagerRezervacije.getInstance().pronadjiRezervacijuPoId(
								Integer.parseInt(tableRezervacije.getModel().getValueAt(row, 0).toString()));
						if (r.getStatusRezervacije() != StatusRezervacije.NA_CEKANJU) {
							JOptionPane.showMessageDialog(null, "Samo rezervacije koje su na cekanju", "Greska",
									JOptionPane.WARNING_MESSAGE);
							return;
						} else {
							boolean uspesnostPotvrde = RezervacijaKontroler.getInstance().potvrdiRezervaciju(r);
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
			}
		});

		odbijRezervaciju.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableRezervacije.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int confirm = JOptionPane.showConfirmDialog(null,
							"Da li ste sigurni da zelite da odbijete rezervaciju?");
					if (confirm == JOptionPane.YES_OPTION) {
						Rezervacije r = ManagerRezervacije.getInstance().pronadjiRezervacijuPoId(
								Integer.parseInt(tableRezervacije.getModel().getValueAt(row, 0).toString()));
						if (r.getStatusRezervacije() != StatusRezervacije.NA_CEKANJU) {
							JOptionPane.showMessageDialog(null, "Samo rezervacije koje su na cekanju", "Greska",
									JOptionPane.WARNING_MESSAGE);
							return;
						} else {
							RezervacijaKontroler.getInstance().odbijRezervaciju(r);
							JOptionPane.showMessageDialog(null, "Rezervacija je odbijena", "Odbijanje rezervacije",
									JOptionPane.INFORMATION_MESSAGE);
							setTableRezervacije();

						}
					}
				}
			}
		});

		/* Izvestaji tab */
		JPanel izvestajiPanel = new JPanel();
		tabbedPane.addTab("Izvestaji", null, izvestajiPanel, null);
		izvestajiPanel.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPaneIzvestaji = new JScrollPane();
		izvestajiPanel.add(scrollPaneIzvestaji, BorderLayout.CENTER);
		JPanel panelIzvestaji = new JPanel();
		scrollPaneIzvestaji.setViewportView(panelIzvestaji);
		panelIzvestaji.setLayout(new BorderLayout(0, 0));
		JLabel lblNewLabel = new JLabel("Izvestaji");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		panelIzvestaji.add(lblNewLabel, BorderLayout.NORTH);

		JPanel podaciPanel = new JPanel();
		podaciPanel.setLayout(new GridLayout(2, 1));
		panelIzvestaji.add(podaciPanel, BorderLayout.CENTER);

		HashMap<String, double[]> prihodi = IzvestajiKontroler.getInstance().prihodiZaChart();
		CategoryChart revenueChart = new CategoryChart(1300, 600);
		revenueChart.getStyler().setDefaultSeriesRenderStyle(CategorySeriesRenderStyle.Line);
		revenueChart.getStyler().setMarkerSize(5);
		// revenueChart.setCustomXAxisTickLabelsFormatter(i ->
		// revenues.get(0).getMonths()[(int) (double) i]);
		XChartPanel<CategoryChart> revenueChartPanel = new XChartPanel<>(revenueChart);
		double xData[] = new double[12];
		for (int i = 0; i < 12; i++) {
			xData[i] = i;
		}
		for (String tipSobe : prihodi.keySet()) {
			revenueChart.addSeries(tipSobe, xData, prihodi.get(tipSobe));
		}
		revenueChartPanel.setSize(500, 500);
		podaciPanel.add(revenueChartPanel);

		HashMap<String, Integer> statusi = IzvestajiKontroler.getInstance()
				.statusiRezervacijaPeriod(LocalDate.now().minusDays(30), LocalDate.now());
		PieChart statusiChart = new PieChart(500, 500);
		for (String status : statusi.keySet()) {
			statusiChart.addSeries(status.toString(), statusi.get(status));
		}
		XChartPanel<PieChart> statusiChartPanel = new XChartPanel<>(statusiChart);
		statusiChartPanel.setSize(500, 500);
		podaciPanel.add(statusiChartPanel);
	}

	private void setTableRezervacije() {
		Object[][] rezervacije = ManagerRezervacije.getInstance().prikazSvihRezervacija();
		DefaultTableModel model = (DefaultTableModel) tableRezervacije.getModel();
		model.setDataVector(rezervacije, headerRezervacije);
		tableRezervacije.clearSelection();
		tableRezervacije.revalidate();
		tableRezervacije.repaint();

	}
	/*
	 * private void setTableSobe() { Object[][] sobe =
	 * ManagerSoba.getInstance().prikazSoba(); DefaultTableModel model =
	 * (DefaultTableModel) tableSobe.getModel(); model.setDataVector(sobe,
	 * headerSoba); tableSobe.clearSelection(); tableSobe.revalidate();
	 * tableSobe.repaint(); }
	 */
}
