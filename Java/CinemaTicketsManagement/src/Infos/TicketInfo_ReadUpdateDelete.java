package Infos;

import Cinema.Hall;
import DBSocketConnection.Client;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketInfo_ReadUpdateDelete extends javax.swing.JFrame {

    Connection con;
    String User;
    int CM;
    String hallNr;          //Numele salii de tip "S"+identificator
    int hall_ID;            //Identificatorul salii 
    int seat_NR;            //Nr de locuri

    public void DatabaseConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cinemamanagement", //database name
                    "root", //user
                    "");                                            //password 
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TicketInfo_ReadUpdateDelete(String user, int _CM, String hallID, String seat) {
        initComponents();
        DatabaseConnect();
        User = new String(user);
        CM = _CM;
        hallNr = hallID;

        hall_ID = Integer.parseInt(hallID.substring(2));
        seat_NR = Integer.parseInt(seat);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        java.util.Date d = new java.util.Date();

        SimpleDateFormat sold = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date e = new java.util.Date();

        String Name = new String();

        //Se preiau informatiile corespunzatoare locului ales din BD
        try {
            String SQL = "SELECT * "
                    + "FROM ticket t "
                    + "WHERE t.chairNumber=" + seat_NR;
            Client sclav = new Client();
            sclav.connectToServer();
            sclav.Query(SQL);
            ResultSet rs = sclav.rs;
            while (rs.next()) {
                d = rs.getTime("MovieStartHour");
                e = rs.getTimestamp("SellingHour");
                Name = rs.getString("clientName");
            }

        } catch (SQLException ex) {
            Logger.getLogger(TicketInfo_Create.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Se completeaza campurile formularului 
        HallTxt.setText(hall_ID + "");
        HallTxt.setEditable(false);
        SeatTxt.setText(seat);
        SeatTxt.setEditable(false);
        MovieStartHourTxt.setText(formatter.format(d));
        MovieStartHourTxt.setEditable(false);
        ClientNameTxt.setText(Name);
        SellingTimeTxt.setText(sold.format(e));
        SellingTimeTxt.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Ticket_RU_Lbl = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Hall_Lbl = new javax.swing.JLabel();
        Client_Name_Lbl = new javax.swing.JLabel();
        Seat_Lbl = new javax.swing.JLabel();
        MovieStartHour_Lbl = new javax.swing.JLabel();
        SellingTime_Lbl = new javax.swing.JLabel();
        HallTxt = new javax.swing.JTextField();
        ClientNameTxt = new javax.swing.JTextField();
        SeatTxt = new javax.swing.JTextField();
        MovieStartHourTxt = new javax.swing.JTextField();
        SellingTimeTxt = new javax.swing.JTextField();
        UpdateBtn = new javax.swing.JButton();
        DeleteTBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(getPreferredSize());

        Ticket_RU_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Ticket_RU_Lbl.setText("Ticket Info");
        Ticket_RU_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Hall_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Hall_Lbl.setText("Hall");
        Hall_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Client_Name_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Client_Name_Lbl.setText("Client_Name");
        Client_Name_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Seat_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Seat_Lbl.setText("Seat");
        Seat_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        MovieStartHour_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MovieStartHour_Lbl.setText("MovieStartHour");
        MovieStartHour_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        SellingTime_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SellingTime_Lbl.setText("SellingTime");
        SellingTime_Lbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        HallTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ClientNameTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        SeatTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        MovieStartHourTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        SellingTimeTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SellingTime_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MovieStartHour_Lbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                    .addComponent(Seat_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Hall_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Client_Name_Lbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(HallTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addComponent(ClientNameTxt)
                    .addComponent(SeatTxt)
                    .addComponent(MovieStartHourTxt)
                    .addComponent(SellingTimeTxt))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(HallTxt)
                    .addComponent(Hall_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ClientNameTxt)
                    .addComponent(Client_Name_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SeatTxt)
                    .addComponent(Seat_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MovieStartHourTxt)
                    .addComponent(MovieStartHour_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SellingTime_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(SellingTimeTxt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Client_Name_Lbl, Hall_Lbl, MovieStartHour_Lbl, Seat_Lbl, SellingTime_Lbl});

        UpdateBtn.setText("Update");
        UpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateBtnActionPerformed(evt);
            }
        });

        DeleteTBtn.setText("Delete");
        DeleteTBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteTBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Ticket_RU_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DeleteTBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Ticket_RU_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UpdateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(DeleteTBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void DeleteTBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteTBtnActionPerformed

        //Se sterge/elibereaza din BD locul ales, dupa care se revine la forma anterioara de tip Hall
        try {
            String SQL = "DELETE from ticket where chairNumber=" + seat_NR;
            Client sclav = new Client();
            sclav.connectToServer();
            sclav.Query(SQL);
            int deleteResult = sclav.confExec;
            
            this.dispose();
            Hall H = new Hall(User, CM, hallNr);
            H.setVisible(true);
            H.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DeleteTBtnActionPerformed

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed

        try {
            //Se editeaza locul ales, se updateaza BD, dupa care se revine la forma anterioara de tip Hall
            String SQL = "UPDATE ticket set clientName='" + ClientNameTxt.getText() + "' where chairNumber=" + seat_NR;
            Client sclav = new Client();
            sclav.connectToServer();
            sclav.Query(SQL);
            int deleteResult = sclav.confExec;
            this.dispose();
            Hall H = new Hall(User, CM, hallNr);
            H.setVisible(true);
            H.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UpdateBtnActionPerformed

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
            java.util.logging.Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicketInfo_ReadUpdateDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TicketInfo_RU().setVisible(true);
            }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TicketInfo_RU().setVisible(true);
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ClientNameTxt;
    private javax.swing.JLabel Client_Name_Lbl;
    private javax.swing.JButton DeleteTBtn;
    private javax.swing.JTextField HallTxt;
    private javax.swing.JLabel Hall_Lbl;
    private javax.swing.JTextField MovieStartHourTxt;
    private javax.swing.JLabel MovieStartHour_Lbl;
    private javax.swing.JTextField SeatTxt;
    private javax.swing.JLabel Seat_Lbl;
    private javax.swing.JTextField SellingTimeTxt;
    private javax.swing.JLabel SellingTime_Lbl;
    private javax.swing.JLabel Ticket_RU_Lbl;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
