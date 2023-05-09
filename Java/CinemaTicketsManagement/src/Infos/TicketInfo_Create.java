package Infos;

import Cinema.Hall;
import DBSocketConnection.Client;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TicketInfo_Create extends javax.swing.JFrame {

    String User;
    int CM;
    String hallNr;          //Numele salii de tip "S"+identificator
    int hall_ID;            //Identificatorul salii 
    int seat_NR;            //Nr de locuri

    public TicketInfo_Create(String user, int _CM, String hallID, String seat) {
        initComponents();
        User = new String(user);
        CM = _CM;
        hallNr = hallID;

        hall_ID = Integer.parseInt(hallID.substring(2));
        seat_NR = Integer.parseInt(seat);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        java.util.Date d = new java.util.Date();

        //Se extrage ora filmului din sala curenta
        try {
            String SQL = "SELECT m.startHour,h.id "
                       + "FROM movies m join halls h on (m.name=h.movieName) "
                       + "WHERE h.id=" + hall_ID;
            Client sclav = new Client();
            sclav.connectToServer();
            sclav.Query(SQL);
            ResultSet rs = sclav.rs;
            while (rs.next()) {
                d = rs.getTime(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TicketInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TicketInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TicketInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Se completeaza campurile formularului
        HallTxt.setText(hall_ID + "");
        HallTxt.setEditable(false);
        SeatTxt.setText(seat);
        SeatTxt.setEditable(false);
        MovieStartHourTxt.setText(formatter.format(d));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Ticket_CD_Lbl = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Hall_Lbl = new javax.swing.JLabel();
        HallTxt = new javax.swing.JTextField();
        Client_Name_Lbl = new javax.swing.JLabel();
        ClientNameTxt = new javax.swing.JTextField();
        Seat_Lbl = new javax.swing.JLabel();
        SeatTxt = new javax.swing.JTextField();
        MovieStartHour_Lbl = new javax.swing.JLabel();
        MovieStartHourTxt = new javax.swing.JTextField();
        CreateTBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(getPreferredSize());

        Ticket_CD_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Ticket_CD_Lbl.setText("Ticket Info");
        Ticket_CD_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Hall_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Hall_Lbl.setText("Hall");
        Hall_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        HallTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Client_Name_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Client_Name_Lbl.setText("Client_Name");
        Client_Name_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ClientNameTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Seat_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Seat_Lbl.setText("Seat");
        Seat_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        SeatTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        MovieStartHour_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MovieStartHour_Lbl.setText("MovieStartHour");
        MovieStartHour_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        MovieStartHourTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        CreateTBtn.setText("Create");
        CreateTBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateTBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Client_Name_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Hall_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Seat_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MovieStartHour_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                        .addGap(17, 17, 17)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HallTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(ClientNameTxt)
                    .addComponent(SeatTxt)
                    .addComponent(MovieStartHourTxt))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CreateTBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Hall_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HallTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Client_Name_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClientNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Seat_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SeatTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MovieStartHour_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MovieStartHourTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CreateTBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Ticket_CD_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Ticket_CD_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CreateTBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateTBtnActionPerformed

        //Valdare
        String Nume = ClientNameTxt.getText();

        /*Se valideaza numele clientului de pe bilet, se introduce in BD biletul 
            si se revine la forma precedenta de tip Hall */
        if (Nume.length() > 0) {
            try {
                String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                String SQL = "Insert into ticket (id_hall,clientName,chairNumber,MovieStartHour,SellingHour) "
                        + "values (" + hall_ID + ",'" + Nume + "'," + seat_NR + ",'" + MovieStartHourTxt.getText() + "','" + datetime + "');";
                Client sclav = new Client();
                sclav.connectToServer();
                sclav.Query(SQL);
                int confExecUpdate = sclav.confExec;

                this.dispose();
                Hall H = new Hall(User, CM, hallNr);
                H.setVisible(true);
                H.setResizable(false);

            } catch (IOException ex) {
                Logger.getLogger(TicketInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TicketInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Wrong Name");
        }

    }//GEN-LAST:event_CreateTBtnActionPerformed

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
            java.util.logging.Logger.getLogger(TicketInfo_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicketInfo_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicketInfo_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicketInfo_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TicketInfo_CD().setVisible(true);
            }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TicketInfo_CD().setVisible(true);
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ClientNameTxt;
    private javax.swing.JLabel Client_Name_Lbl;
    private javax.swing.JButton CreateTBtn;
    private javax.swing.JTextField HallTxt;
    private javax.swing.JLabel Hall_Lbl;
    private javax.swing.JTextField MovieStartHourTxt;
    private javax.swing.JLabel MovieStartHour_Lbl;
    private javax.swing.JTextField SeatTxt;
    private javax.swing.JLabel Seat_Lbl;
    private javax.swing.JLabel Ticket_CD_Lbl;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
