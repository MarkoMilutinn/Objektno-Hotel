package dijalozi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entiteti.Gost;
import entiteti.Pol;

public class DodavanjeGostaDijalog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField ime;
	private JTextField prezime;
	private Pol pol;

	
	public DodavanjeGostaDijalog(Gost g) {
		if (g == null) {
			setTitle("Dodavanje gosta");
		} else {
			setTitle("Izmena gosta");
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbco = new GridBagConstraints();
		gbco.fill = GridBagConstraints.HORIZONTAL;
		gbco.insets = new Insets(5, 5, 5, 5);
		
		JLabel lblIme = new JLabel("Ime:");
		gbco.gridx = 0;
		gbco.gridy = 0;
		lblIme.setFont(new Font("Tahoma", Font.PLAIN,16	));
		contentPanel.add(lblIme, gbco);
		
		this.ime = new JTextField();
		gbco.gridx = 1;
		gbco.gridy = 0;
		ime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ime.setColumns(10);
		contentPanel.add(ime, gbco);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		gbco.gridx = 0;
		gbco.gridy = 1;
		lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblPrezime, gbco);
		
		this.prezime= new JTextField();
		gbco.gridx = 1;
		gbco.gridy = 1;
		prezime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		prezime.setColumns(10);
		contentPanel.add(prezime, gbco);
		
		JLabel lblPol = new JLabel("Pol");
		gbco.gridx = 0;
		gbco.gridy = 2;
		lblPol.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPanel.add(lblPol, gbco);
		
		JPanel poloviPanel=	new JPanel(new FlowLayout(FlowLayout.LEFT));
		ButtonGroup polovi = new ButtonGroup();
		
		JRadioButton rdbtnMuski = new JRadioButton("Muski");
		rdbtnMuski.setFont(new Font("Tahoma", Font.PLAIN, 16));
		polovi.add(rdbtnMuski);
		
		JRadioButton rdbtnZenski = new JRadioButton("Zenski");
		rdbtnZenski.setFont(new Font("Tahoma", Font.PLAIN, 16));
		polovi.add(rdbtnZenski);
		
		poloviPanel.add(rdbtnMuski);
		poloviPanel.add(rdbtnZenski);
		
		gbco.gridx = 1;
		gbco.gridy = 2;
		contentPanel.add(poloviPanel, gbco);
		
		rdbtnMuski.addActionListener(e -> {
            rdbtnZenski.setSelected(false);
			if (rdbtnMuski.isSelected()) {
				rdbtnMuski.setSelected(true);
				this.pol = Pol.MUSKI;
			}
     
		});
		
		
		
		
		
	}

}
