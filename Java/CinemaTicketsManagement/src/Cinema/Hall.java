package Cinema;

import DBSocketConnection.Client;
import Infos.HallInfo_Update;
import Infos.TicketInfo_Create;
import Infos.TicketInfo_ReadUpdateDelete;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

public class Hall extends javax.swing.JFrame {

    String User;                    //Username-ul utilizatorului curent
    int CM;                         //Statutul utilizatorului curent
    int maxnr = 60;                 //Nr de scaune din sala curenta
    ArrayList<Integer> occupied;    //Vector cu locurile ocupate din sala curenta existente in BD

    public Hall(String user, int _CM, String hallID) {
        initComponents();
        User = new String(user);
        CM = _CM;
        int hall_ID = Integer.parseInt(hallID.substring(2)); //Se preia identificatorul unic al salii

        //Se preia din BD nr de scaune corespnzator salii curente
        try {
            String SQL = "select numberChairs from halls where id=" + hall_ID;
            Client sclav = new Client();
            sclav.connectToServer();

            sclav.Query(SQL);
            ResultSet rs = sclav.rs;

            while (rs.next()) {
                maxnr = rs.getInt("numberChairs");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Hall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Hall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Hall.class.getName()).log(Level.SEVERE, null, ex);
        }

        String Logged = "Hello " + user;
        if (CM == 1) {
            Logged = Logged + " ( Manager )";

        } else {
            Logged = Logged + " ( Client )";
            EditHallInfo.setVisible(false);
        }

        UserLogged.setText(Logged);
        HallAccesed.setText(hallID);

        occupied = new ArrayList<>();
        //Se preiau locurile ocupate din BD din sala curenta
        try {
            String SQL = "select * from ticket where id_hall=" + hall_ID;
            Client sclav = new Client();
            sclav.connectToServer();
            
            sclav.Query(SQL);
            ResultSet rs= sclav.rs;
            
            while (rs.next()) {
                occupied.add(rs.getInt("chairNumber"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Hall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Hall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Hall.class.getName()).log(Level.SEVERE, null, ex);
        }

        ChairsPanel.setLayout(new GridLayout(5, 12));
        for (int i = 1; i <= maxnr; i++) {
            JButton J = new JButton("" + i);
            //CM=1 =>manager
            //CM=0 =>client
            if (!occupied.contains(i)) //Daca nu este in BD => Loc liber
            {
                J.setBackground(Color.GREEN);
                if (CM == 1) /*Daca utilizatorul este manager=> 
                Apasand pe un loc liber se deschide o noua forma de tip  TicketInfo_Create in care 
                    se completeaza datele si se ocupa locul */ {
                    J.addActionListener((ActionEvent e)
                            -> {
                        this.dispose();
                        TicketInfo_Create TIC = new TicketInfo_Create(User, CM, hallID, ((JButton) e.getSource()).getText());
                        TIC.setResizable(false);
                        TIC.setVisible(true);
                    });
                }
            } else {
                J.setBackground(Color.RED);
                if (CM == 1) /*Daca utilizatorul este manager=> 
                Apasand pe un loc ocupat se deschide o noua forma de tip  TicketInfo_ReadUpdateDelete in care 
                    se pot edita datele sau se poate elibera locul */ {
                    J.addActionListener((ActionEvent e) -> {
                        this.dispose();
                        TicketInfo_ReadUpdateDelete TIRUD = new TicketInfo_ReadUpdateDelete(User, CM, hallID, ((JButton) e.getSource()).getText());
                        TIRUD.setResizable(false);
                        TIRUD.setVisible(true);
                    });
                }
            }

            J.setVisible(true);
            ChairsPanel.add(J);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Menu = new javax.swing.JPanel();
        LogOutBtn = new javax.swing.JButton();
        UserLogged = new javax.swing.JLabel();
        HallAccesed = new javax.swing.JLabel();
        EditHallInfo = new javax.swing.JButton();
        ChairsPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(getPreferredSize());

        Menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        LogOutBtn.setText("LogOut");
        LogOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutBtnActionPerformed(evt);
            }
        });

        UserLogged.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        UserLogged.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        HallAccesed.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        HallAccesed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        EditHallInfo.setText("Edit");
        EditHallInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditHallInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MenuLayout = new javax.swing.GroupLayout(Menu);
        Menu.setLayout(MenuLayout);
        MenuLayout.setHorizontalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(HallAccesed, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditHallInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(379, 379, 379)
                .addComponent(UserLogged, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LogOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        MenuLayout.setVerticalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(EditHallInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(HallAccesed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UserLogged, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LogOutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        ChairsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ChairsPanelLayout = new javax.swing.GroupLayout(ChairsPanel);
        ChairsPanel.setLayout(ChairsPanelLayout);
        ChairsPanelLayout.setHorizontalGroup(
            ChairsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ChairsPanelLayout.setVerticalGroup(
            ChairsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 451, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ChairsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChairsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void LogOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutBtnActionPerformed

        this.dispose();
    }//GEN-LAST:event_LogOutBtnActionPerformed

    private void EditHallInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditHallInfoActionPerformed

        int sala = Integer.parseInt(HallAccesed.getText().substring(2));
        /* Pt editarea informatiilor salii curente se deschide o noua forma de tip HallInfo_Update
        in care se pot edita informatiile dupa care se updateaza BD. */
        this.dispose();
        HallInfo_Update HIU = new HallInfo_Update(User, CM, sala);
        HIU.setVisible(true);
        HIU.setResizable(false);

    }//GEN-LAST:event_EditHallInfoActionPerformed

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
            java.util.logging.Logger.getLogger(Hall.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Hall.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Hall.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Hall.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
        new Hall().setVisible(true);
        }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChairsPanel;
    private javax.swing.JButton EditHallInfo;
    private javax.swing.JLabel HallAccesed;
    private javax.swing.JButton LogOutBtn;
    private javax.swing.JPanel Menu;
    private javax.swing.JLabel UserLogged;
    // End of variables declaration//GEN-END:variables
}
