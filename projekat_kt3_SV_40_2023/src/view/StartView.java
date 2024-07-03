package view;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import entiteti.Osoba;
import kontroleri.UserKontroler;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class StartView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField korisnickoIme;
	private JPasswordField sifra;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartView frame = new StartView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartView() {
		setResizable(false);
		setTitle("MarkHotel ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 546, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DOBRODOSLI ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(222, 41, 238, 45);
		contentPane.add(lblNewLabel);
		
		JButton prijava = new JButton("Prijava");
		prijava.setFont(new Font("Tahoma", Font.PLAIN, 18));
		prijava.setBounds(222, 299, 154, 54);
		contentPane.add(prijava);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(56, 122, 86, 27);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Lozinka");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(56, 198, 105, 32);
		contentPane.add(lblNewLabel_2);
		
		korisnickoIme = new JTextField();
		korisnickoIme.setFont(new Font("Tahoma", Font.PLAIN, 14));
		korisnickoIme.setBounds(222, 122, 141, 32);
		contentPane.add(korisnickoIme);
		korisnickoIme.setColumns(10);
		
		korisnickoIme.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sifra.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
		
		
		sifra = new JPasswordField();
		sifra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sifra.setBounds(222, 198, 141, 32);
		contentPane.add(sifra);
		sifra.setColumns(10);
		
		
		sifra.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					prijava.doClick()
					
					;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		
		prijava.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                String korisnicko = korisnickoIme.getText().trim();
                String lozinka = new String(sifra.getPassword()).trim();
                Osoba o = UserKontroler.getInstance().login(korisnicko, lozinka);
                if (o == null) {
                    JOptionPane.showMessageDialog(StartView.this, "Pogresno korisnicko ime ili lozinka");
                    korisnickoIme.setText("");
                    sifra.setText("");
                    korisnickoIme.grabFocus();
                    korisnickoIme.requestFocus();
                }else {
                	switch (o.getTip()) {
					case 3:
						contentPane.setVisible(false);
						dispose();
//						AdminView adminView = new AdminView(o);
//						adminView.setVisible(true);
						dispose();
						break;
					case 1:
						contentPane.setVisible(false);
						dispose();
						GostView gostView = new GostView(o);
						gostView.setVisible(true);
						dispose();
						break;
					case 4:
						contentPane.setVisible(false);
						dispose();
						RecepcionarView recepcionerView = new RecepcionarView();
						recepcionerView.setVisible(true);
						dispose();
						break;
					case 2:
						contentPane.setVisible(false);
						dispose();
//						SobaricaView sobaricaView = new SobaricaView(o);
//						sobaricaView.setVisible(true);
						dispose();
						break;
					default:
						JOptionPane.showMessageDialog(StartView.this, "Nepoznata uloga");
						contentPane.setVisible(false);
						dispose();
						break;
                	}
                }
		}
                });
		
		
		
		
	}
}
