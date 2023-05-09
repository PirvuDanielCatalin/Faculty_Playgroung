package Logins;

import Cinema.Halls;
import DBSocketConnection.Client;
import DBSocketConnection.Server;
import java.io.IOException;
import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start extends JFrame {

    public Start() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        Panou = new javax.swing.JPanel();
        UserLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        UserTxt = new javax.swing.JTextField();
        PasswordTxt = new javax.swing.JPasswordField();
        Register_Btn = new javax.swing.JButton();
        Login_Btn = new javax.swing.JButton();
        Reset_Btn = new javax.swing.JButton();
        ClientCheck = new javax.swing.JRadioButton();
        ManagerCheck = new javax.swing.JRadioButton();
        Title = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Start");
        setSize(getPreferredSize());

        Panou.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Panou.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Panou.setEnabled(false);
        Panou.setPreferredSize(new java.awt.Dimension(420, 260));

        UserLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UserLabel.setLabelFor(UserTxt);
        UserLabel.setText("User");
        UserLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        PasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PasswordLabel.setLabelFor(PasswordTxt);
        PasswordLabel.setText("Password");
        PasswordLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        UserTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        UserTxt.setText("admin");
        UserTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserTxtActionPerformed(evt);
            }
        });

        PasswordTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PasswordTxt.setText("admin");
        PasswordTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordTxtActionPerformed(evt);
            }
        });

        Register_Btn.setText("Register");
        Register_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Register_BtnActionPerformed(evt);
            }
        });

        Login_Btn.setText("Login");
        Login_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login_BtnActionPerformed(evt);
            }
        });

        Reset_Btn.setText("Reset");
        Reset_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Reset_BtnActionPerformed(evt);
            }
        });

        ClientCheck.setText("Client");
        ClientCheck.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientCheckActionPerformed(evt);
            }
        });

        ManagerCheck.setSelected(true);
        ManagerCheck.setText("Manager");
        ManagerCheck.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ManagerCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManagerCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanouLayout = new javax.swing.GroupLayout(Panou);
        Panou.setLayout(PanouLayout);
        PanouLayout.setHorizontalGroup(
            PanouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanouLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(PanouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanouLayout.createSequentialGroup()
                        .addComponent(Register_Btn, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Login_Btn, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Reset_Btn, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                    .addGroup(PanouLayout.createSequentialGroup()
                        .addComponent(ClientCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ManagerCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanouLayout.createSequentialGroup()
                        .addGroup(PanouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UserLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PanouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanouLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(UserTxt))
                            .addGroup(PanouLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(PasswordTxt)))))
                .addContainerGap())
        );

        PanouLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {PasswordLabel, UserLabel});

        PanouLayout.setVerticalGroup(
            PanouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanouLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(PanouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UserLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(PanouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordLabel)
                    .addComponent(PasswordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(PanouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ClientCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ManagerCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(Register_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Login_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Reset_Btn))
                .addGap(16, 16, 16))
        );

        PanouLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {PasswordLabel, PasswordTxt, Reset_Btn, UserLabel, UserTxt});

        PanouLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ClientCheck, ManagerCheck});

        Title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Title.setForeground(new java.awt.Color(240, 240, 240));
        Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Title.setText("Login System");
        Title.setToolTipText("");
        Title.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Title.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Title.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Panou, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panou, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Reset_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Reset_BtnActionPerformed

        /*La apasarea butonului Reset se creeaza o noua forma de tip UserForm in care 
        vom introduce username-ul atasat contului a carui parola vrem sa o modificam*/
        UserForm UF = new UserForm();
        UF.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        UF.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Start JF = new Start();
                JF.setVisible(true);
                JF.setResizable(false);
                UF.dispose();
            }
        });
        UF.setVisible(true);
        UF.setLocation(300, 200);
        this.dispose();


    }//GEN-LAST:event_Reset_BtnActionPerformed

    private void Login_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Login_BtnActionPerformed

        /*Se preiau username-ul din textfield-ul UserTxt,
                    parola din passwordfield-ul PasswordTxt 
                    si statutul dat de radiobutton-ul ManagerCheck.
         */
        String User = UserTxt.getText();
        String Password = new String(PasswordTxt.getPassword());
        int Expectation = (ManagerCheck.isSelected()) ? 1 : 0;

        int Reality = 0;//Se presupune ca utilizatorul este implicit client.

        if (Password.length() > 0 && UserTxt.getText().length() > 0) {
            /*
                Client sclav = new Client();
                sclav.connectToServer();
                String SQL = 
                sclav.Query(SQL);
                ResultSet rs = sclav.rs;
            */
            try {
                
                Client sclav = new Client();
                sclav.connectToServer();

                String SQL = "select * from users where user='" + User + "' and password='" + Password + "'";
                /*Se preiau datele din baza de date corespunzatoare user-ului si parolei introduse.*/
                sclav.Query(SQL);
                ResultSet rs = sclav.rs;

                //Statutul real al utilizatorului este cel din baza de date daca in aceasta exista contul introdus.
                Reality = (rs.first()) ? rs.getInt(4) : 0;

                //Daca datele introduse corespund cu cele din BD atunci logare se efectueaza cu succes.
                if (rs.first() && Expectation == Reality) {
                    /*Se deschide o noua forma de tip Halls in care sunt afisate salile si rapoartele.*/
                    Halls HS = new Halls(User, Reality);
                    HS.setVisible(true);
                    HS.setResizable(false);
                    this.dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login details", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegisterPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            JOptionPane.showMessageDialog(null, "Invalid login details", "Login Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_Login_BtnActionPerformed

    private void Register_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Register_BtnActionPerformed

        /*Se deschide o noua forma de tip RegisterPage in care se introduc datele unui nou cont*/
        RegisterPage RegPg = new RegisterPage();
        RegPg.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        RegPg.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Start JF = new Start();
                JF.setVisible(true);
                JF.setResizable(false);
                RegPg.dispose();
            }
        });
        RegPg.setVisible(true);
        RegPg.setResizable(false);
        this.dispose();

    }//GEN-LAST:event_Register_BtnActionPerformed

    private void PasswordTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordTxtActionPerformed

    }//GEN-LAST:event_PasswordTxtActionPerformed

    private void UserTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserTxtActionPerformed

    }//GEN-LAST:event_UserTxtActionPerformed

    private void ManagerCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManagerCheckActionPerformed

        ClientCheck.setSelected(false);
    }//GEN-LAST:event_ManagerCheckActionPerformed

    private void ClientCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientCheckActionPerformed

        ManagerCheck.setSelected(false);
    }//GEN-LAST:event_ClientCheckActionPerformed

    public static void main(String args[]) throws IOException, SQLException {
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
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            Start JF = new Start();
            JF.setVisible(true);
            JF.setResizable(false);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton ClientCheck;
    private javax.swing.JButton Login_Btn;
    private javax.swing.JRadioButton ManagerCheck;
    private javax.swing.JPanel Panou;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JPasswordField PasswordTxt;
    private javax.swing.JButton Register_Btn;
    private javax.swing.JButton Reset_Btn;
    private javax.swing.JLabel Title;
    private javax.swing.JLabel UserLabel;
    private javax.swing.JTextField UserTxt;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    // End of variables declaration//GEN-END:variables
}
