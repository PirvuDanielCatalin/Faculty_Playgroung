package Infos;

import Cinema.Halls;
import DBSocketConnection.Client;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class HallInfo_Create extends javax.swing.JFrame {

    String User;
    int CM;
    
    public HallInfo_Create(String _user, int _CM) {
        
        initComponents();
        User = new String(_user);
        CM = _CM;
        ManagerID_Txt.setText(User);
        ManagerID_Txt.setEditable(false);
        FillComboBox();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HallTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        ID_Lbl = new javax.swing.JLabel();
        Hall_Number_Txt = new javax.swing.JTextField();
        Name_Lbl = new javax.swing.JLabel();
        Name_Txt = new javax.swing.JTextField();
        ChairNr_Lbl = new javax.swing.JLabel();
        Chairs_Txt = new javax.swing.JTextField();
        Manager_ID_Lbl = new javax.swing.JLabel();
        ManagerID_Txt = new javax.swing.JTextField();
        StartHour_Lbl = new javax.swing.JLabel();
        FinalHour_Lbl = new javax.swing.JLabel();
        StartHour_Txt = new javax.swing.JTextField();
        FinalHour_Txt = new javax.swing.JTextField();
        CreateHallInfo = new javax.swing.JButton();
        MovieNameLbl = new javax.swing.JLabel();
        MovieNames = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(getPreferredSize());

        HallTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HallTitle.setText("Hall ");
        HallTitle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        HallTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ID_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ID_Lbl.setText("Hall_Number");
        ID_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Hall_Number_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Name_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Name_Lbl.setText("Hall_Name");
        Name_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Name_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ChairNr_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChairNr_Lbl.setText("Chairs");
        ChairNr_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Chairs_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Manager_ID_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Manager_ID_Lbl.setText("Manager_ID");
        Manager_ID_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ManagerID_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ManagerID_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManagerID_TxtActionPerformed(evt);
            }
        });

        StartHour_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        StartHour_Lbl.setText("Opened between");
        StartHour_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        FinalHour_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FinalHour_Lbl.setText("and");
        FinalHour_Lbl.setToolTipText("");
        FinalHour_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        StartHour_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        StartHour_Txt.setText("8:00");
        StartHour_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartHour_TxtActionPerformed(evt);
            }
        });

        FinalHour_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        FinalHour_Txt.setText("21:00");

        CreateHallInfo.setText("Go");
        CreateHallInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateHallInfoActionPerformed(evt);
            }
        });

        MovieNameLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MovieNameLbl.setText("Movie");
        MovieNameLbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(Name_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ID_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(Manager_ID_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(StartHour_Lbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(FinalHour_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                            .addComponent(ChairNr_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FinalHour_Txt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Chairs_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(StartHour_Txt, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                                .addComponent(ManagerID_Txt))
                            .addComponent(Name_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Hall_Number_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(MovieNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(CreateHallInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(MovieNames, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ChairNr_Lbl, FinalHour_Lbl, ID_Lbl, Manager_ID_Lbl, Name_Lbl, StartHour_Lbl});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ID_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Hall_Number_Txt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Name_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Name_Txt, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ChairNr_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Chairs_Txt))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ManagerID_Txt)
                    .addComponent(Manager_ID_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StartHour_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(StartHour_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FinalHour_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FinalHour_Txt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MovieNameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MovieNames, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(CreateHallInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ChairNr_Lbl, FinalHour_Lbl, ID_Lbl, Manager_ID_Lbl, Name_Lbl, StartHour_Lbl});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HallTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HallTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void FillComboBox() {
        try {
            //Se preiau din BD toate titlurile de filme si se populeaza ComboBox-ul MovieNames
            Client sclav = new Client();
            sclav.connectToServer();

            String SQL = "Select * from movies";

            sclav.Query(SQL);
            ResultSet rs = sclav.rs;
            while (rs.next()) {
                String name = rs.getString("name");
                MovieNames.addItem(name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HallInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HallInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HallInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void CreateHallInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateHallInfoActionPerformed

        // Validarea tipurilor de date
        String S = Name_Txt.getText();                      //Numele salii (NU ID-UL)
        String T = (String) MovieNames.getSelectedItem();   //Filmul ales

        int nr = 0;
        int nrc = 0;
        try {
            nr = parseInt(Hall_Number_Txt.getText());       //Numarul salii (ID-ul)
            try {
                nrc = parseInt(Chairs_Txt.getText());       //Nr de locuri
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Wrong Chair Number");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Wrong Hall Number");
        }
        //Validarea id-ul salii
        if (nr < 1 || nr > 30) {
            nr = 0;
            JOptionPane.showMessageDialog(null, "Wrong Hall Number");
        }
        //Validarea nr de locuri
        if (nrc < 1 || nrc > 60) {
            nrc = 0;
            JOptionPane.showMessageDialog(null, "Wrong Chair Number");
        }

        int ok = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        java.util.Date d1 = null;
        java.util.Date d2 = null;
        try {
            d1 = (java.util.Date) formatter.parse(StartHour_Txt.getText());  //Ora de deschidere a salii
            d2 = (java.util.Date) formatter.parse(FinalHour_Txt.getText());  //Ora de inchidere a salii
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Wrong Time Information");
        }
        //Validarea orelor (Ora1 < Ora2)
        if (d1 != null && d2 != null && d2.before(d1)) {
            ok = 0;
            JOptionPane.showMessageDialog(null, "Wrong Time Information");
        }

        //Verificarea tuturor datelor introduse
        if (S.equals("") || nr == 0 || nrc == 0 || ok == 0) {
            /*Daca nu respecta conditiile, 
            se reincearca prin deschiderea unei noi forme de tip HallInfo_Create in care se repeta procesul*/
            this.dispose();
            HallInfo_Create HICU = new HallInfo_Create(User, CM);
            HICU.setVisible(true);
            HICU.setResizable(false);

        } else {
            // Validarea existentei in baza de date
            ResultSet rs = null;
            try {
                Client sclav = new Client();
                sclav.connectToServer();
                String SQL = "select id from halls where id=" + nr;
                sclav.Query(SQL);
                rs = sclav.rs;
            } catch (IOException ex) {
                Logger.getLogger(HallInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HallInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Se verifica in BD daca exista sala ce urmeaza a fi creata
            try {
                if (!rs.next()) //Daca nu exista se insereaza
                {
                    Client sclav = new Client();
                    sclav.connectToServer();

                    String aux1 = formatter.format(d1);
                    String aux2 = formatter.format(d2);

                    String SQL = "INSERT INTO halls values (" + nr + ",'" + S + "'," + nrc + ",'" + User + "','" + aux1 + "','" + aux2 + "','" + T + "')";
                    sclav.Query(SQL);
                    int conf=sclav.confExec;

                    this.dispose();
                    Halls HS = new Halls(User, CM);
                    HS.setVisible(true);
                    HS.setResizable(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Hall already created");
                }
            } catch (SQLException ex) {
                Logger.getLogger(HallInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HallInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HallInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_CreateHallInfoActionPerformed

    private void StartHour_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartHour_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StartHour_TxtActionPerformed

    private void ManagerID_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManagerID_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ManagerID_TxtActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HallInfo_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HallInfo_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HallInfo_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HallInfo_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HallInfo_CU().setVisible(true);
            }
        });
        //</editor-fold>

        /* Create and display the form */
 /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HallInfo_CU().setVisible(true);
            }
        });
         */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ChairNr_Lbl;
    private javax.swing.JTextField Chairs_Txt;
    private javax.swing.JButton CreateHallInfo;
    private javax.swing.JLabel FinalHour_Lbl;
    private javax.swing.JTextField FinalHour_Txt;
    private javax.swing.JLabel HallTitle;
    private javax.swing.JTextField Hall_Number_Txt;
    private javax.swing.JLabel ID_Lbl;
    private javax.swing.JTextField ManagerID_Txt;
    private javax.swing.JLabel Manager_ID_Lbl;
    private javax.swing.JLabel MovieNameLbl;
    private javax.swing.JComboBox<String> MovieNames;
    private javax.swing.JLabel Name_Lbl;
    private javax.swing.JTextField Name_Txt;
    private javax.swing.JLabel StartHour_Lbl;
    private javax.swing.JTextField StartHour_Txt;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
