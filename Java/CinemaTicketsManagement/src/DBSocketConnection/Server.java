package DBSocketConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import com.sun.rowset.CachedRowSetImpl;
import javax.sql.rowset.CachedRowSet;

public class Server {

    public static void main(String[] args) throws Exception {
        System.out.println("The server is running...\n");
        int clientNumber = 1;
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                new Capitalizer(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Capitalizer extends Thread {

        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client #" + clientNumber + " at " + socket);

        }

        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                Connection con = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/cinemamanagement", //database name
                            "root", //user
                            "");                                            //password 
                } catch (ClassNotFoundException | SQLException ex) {
                    log(ex + "");
                }
                
                // Get messages from the client, line by line; return them capitalized
                String SQL = (String) in.readObject();

                //Getting the result ( ResultSet / int )
                Statement stmt = con.createStatement();
                
                ResultSet rs = null;
                int confExec=0;
                
                String cmd=SQL.substring(0, SQL.indexOf(' ')).toUpperCase();
                
                if(cmd.equals("SELECT")) // SQL e de tip SELECT
                {
                    rs = stmt.executeQuery(SQL);
                    CachedRowSet CRS = new CachedRowSetImpl();
                    CRS.populate(rs);
                    out.writeObject(CRS);
                    out.flush();
                    
                }
                else //  SQL e de tip INSERT/UPDATE/DELETE
                {
                    confExec=stmt.executeUpdate(SQL);
                    out.writeInt(confExec);
                    out.flush();
                }

            } catch (IOException e) {
                log("Error handling client #" + clientNumber + ": " + e);
            } catch (ClassNotFoundException ex) {
                log("Error handling client #" + clientNumber + ": " + ex);
            } catch (SQLException ex) {
                log("Error handling client #" + clientNumber + ": " + ex);
            } finally {
                try {
                    socket.close();

                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client #" + clientNumber + " closed!\n\n");
            }
        }

        private void log(String message) {
            System.out.println(message);
        }
    }
}
