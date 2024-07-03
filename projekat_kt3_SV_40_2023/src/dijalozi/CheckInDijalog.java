package dijalozi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entiteti.Rezervacije;
import entiteti.Soba;
import entiteti.StatusRezervacije;
import entiteti.StatusSobe;
import kontroleri.RezervacijaKontroler;
import manage.ManagerSoba;

public class CheckInDijalog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JComboBox<String> comboBoxSobe;

    public CheckInDijalog(Rezervacije r) {
        setTitle("Check In");
        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Get available rooms
        String[] sobe = RezervacijaKontroler.getInstance().dobijanjeIdSlobodnihSoba(r);
        if (sobe.length == 0) {
            JOptionPane.showMessageDialog(null, "Nema slobodnih soba za ovaj tip sobe");
            return;
        }

        // Create the combo box with available rooms
        comboBoxSobe = new JComboBox<>(sobe);
        comboBoxSobe.setSelectedIndex(0);

        // Set up content panel
        contentPanel.setLayout(new FlowLayout());
        contentPanel.add(new JLabel("Odaberite sobu:"));
        contentPanel.add(comboBoxSobe);

        // Add content panel to the dialog
        add(contentPanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        // Confirm button
        JButton btnConfirm = new JButton("Potvrdi");
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                potvrdi(r);
            }
        });
        buttonPane.add(btnConfirm);
        getRootPane().setDefaultButton(btnConfirm);

        // Cancel button
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(btnCancel);

        // Add button panel to the dialog
        add(buttonPane, BorderLayout.SOUTH);

        // Make the dialog visible
        setVisible(true);
    }

    // Method to handle the confirmation action
    private void potvrdi(Rezervacije r) {
        String selectedSoba = (String) comboBoxSobe.getSelectedItem();
        if (selectedSoba != null) {
            r.setStatusRezervacije(StatusRezervacije.TRENUTNA);
            Soba s=ManagerSoba.getInstance().pronadjiSobuPoId(Integer.parseInt(selectedSoba));
            r.setSoba(s);
            s.setStatusSobe(StatusSobe.ZAUZETA);
            
            dispose();
        }
		
    }
    
}

  
