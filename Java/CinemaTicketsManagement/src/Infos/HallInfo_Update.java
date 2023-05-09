package Infos;

import Cinema.Hall;
import DBSocketConnection.Client;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class HallInfo_Update extends javax.swing.JFrame {

    String User;
    int CM;
    int ID;

    public HallInfo_Update(String _User, int _CM, int id) {
        initComponents();
        FillComboBox();

        User = new String(_User);
        CM = _CM;
        ID = id;

        String hallName = new String(),
                managerId = new String(),
                movieName = new String();
        int nrChrs = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        java.util.Date t1 = new java.util.Date(),
                t2 = new java.util.Date();

        //Se extrag informatiile salii din BD
        ResultSet rs = null;
        try {
            Client sclav = new Client();
            sclav.connectToServer();
            String SQL = "Select * from halls where id=" + id;
            sclav.Query(SQL);
            rs = sclav.rs;
        } catch (IOException ex) {
            Logger.getLogger(HallInfo_Update.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HallInfo_Update.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (rs.next()) {
                hallName = rs.getString("name");
                nrChrs = rs.getInt("numberChairs");
                managerId = rs.getString("manager_id");
                t1 = rs.getTime("startHour");
                t2 = rs.getTime("finalHour");
                movieName = rs.getString("movieName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Hall.class.getName()).log(Level.SEVERE, null, ex);
        }
        String s1 = formatter.format(t1);
        String s2 = formatter.format(t2);

        //Se completeaza campurile formularului
        Hall_Number_Txt.setText(id + "");
        Hall_Number_Txt.setEditable(false);
        Name_Txt.setText(hallName);
        Chairs_Txt.setText(nrChrs + "");
        ManagerID_Txt.setText(managerId);
        StartHour_Txt.setText(s1);
        FinalHour_Txt.setText(s2);
        MovieNames.setSelectedItem(movieName);

    }

    private void FillComboBox() {
        try {
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
            Logger.getLogger(HallInfo_Update.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HallInfo_Update.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HallTitle = new javax.swing.JLabel();
        Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Hall_Number_Txt = new javax.swing.JTextField();
        Name_Txt = new javax.swing.JTextField();
        Chairs_Txt = new javax.swing.JTextField();
        ManagerID_Txt = new javax.swing.JTextField();
        StartHour_Txt = new javax.swing.JTextField();
        FinalHour_Txt = new javax.swing.JTextField();
        MovieNames = new javax.swing.JComboBox<>();
        UpdateHallInfo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        HallTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HallTitle.setText("Hall ");
        HallTitle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        HallTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Hall_Number");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Hall_Name");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Chairs");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Manager_ID");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Opened between");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("and");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Movie");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Hall_Number_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Name_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Chairs_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ManagerID_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        StartHour_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        FinalHour_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        UpdateHallInfo.setText("Update");
        UpdateHallInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateHallInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Hall_Number_Txt)
                    .addComponent(Name_Txt)
                    .addComponent(Chairs_Txt)
                    .addComponent(ManagerID_Txt)
                    .addComponent(StartHour_Txt)
                    .addComponent(FinalHour_Txt)
                    .addComponent(MovieNames, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addComponent(UpdateHallInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        PanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7});

        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Hall_Number_Txt)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Name_Txt)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Chairs_Txt)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ManagerID_Txt)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(StartHour_Txt)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FinalHour_Txt)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(MovieNames))
                .addGap(18, 18, 18)
                .addComponent(UpdateHallInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HallTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HallTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void UpdateHallInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateHallInfoActionPerformed

        // Validarea tipurilor de date
        String S = Name_Txt.getText();                      //Numele salii
        String T = (String) MovieNames.getSelectedItem();   //Filmul ales
        String M = ManagerID_Txt.getText();                 //Id Manager -- Username-ul Managerului

        int nr = 0;
        int nrc = 0;
        try {
            nr = parseInt(Hall_Number_Txt.getText()); //Nr salii 
            try {
                nrc = parseInt(Chairs_Txt.getText()); //Nr locuri
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Wrong Chair Number");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Wrong Hall Number");
        }
        //Validarea numarului salii
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
            d1 = (java.util.Date) formatter.parse(StartHour_Txt.getText()); //Ora de deschidere a salii
            d2 = (java.util.Date) formatter.parse(FinalHour_Txt.getText()); //Ora de inchidere a salii
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
            se reincearca prin deschiderea unei noi forme de tip  HallInfo_Update in care se repeta procesul*/
            this.dispose();
            HallInfo_Update HIU = new HallInfo_Update(User, CM, ID);
            HIU.setVisible(true);
            HIU.setResizable(false);

        } else {
            try {
                String aux1 = formatter.format(d1);
                String aux2 = formatter.format(d2);
                
                //Se updateaza BD si se revine la forma precedenta de tip Hall
                Client sclav = new Client();
                sclav.connectToServer();
                String SQL_update = "Update halls "
                        + "set name='" + S + "', "
                        + "numberChairs=" + nrc + ", "
                        + "manager_id='" + M + "', "
                        + "startHour='" + aux1 + "', "
                        + "finalHour='" + aux2 + "', "
                        + "movieName='" + T + "' "
                        + "where id=" + ID + ";";
                sclav.Query(SQL_update);
                int updt = sclav.confExec;
                this.dispose();
                if (updt > 0) {
                    JOptionPane.showMessageDialog(null, "Update Done");
                }
                Hall H = new Hall(User, CM, "S " + ID);
                H.setVisible(true);
                H.setResizable(false);
            } catch (IOException ex) {
                Logger.getLogger(HallInfo_Update.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HallInfo_Update.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_UpdateHallInfoActionPerformed

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
            java.util.logging.Logger.getLogger(HallInfo_Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HallInfo_Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HallInfo_Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HallInfo_Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*
        java.awt.EventQueue.invokeLater(() -> {
            new HallInfo_Update().setVisible(true);
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Chairs_Txt;
    private javax.swing.JTextField FinalHour_Txt;
    private javax.swing.JLabel HallTitle;
    private javax.swing.JTextField Hall_Number_Txt;
    private javax.swing.JTextField ManagerID_Txt;
    private javax.swing.JComboBox<String> MovieNames;
    private javax.swing.JTextField Name_Txt;
    private javax.swing.JPanel Panel;
    private javax.swing.JTextField StartHour_Txt;
    private javax.swing.JButton UpdateHallInfo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
