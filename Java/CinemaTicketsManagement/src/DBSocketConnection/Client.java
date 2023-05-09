package DBSocketConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import javax.sql.rowset.CachedRowSet;

public class Client 
{

    private ObjectInputStream in;
    private ObjectOutputStream out;
    public ResultSet rs = null;
    public int confExec = 0;
    
    public void connectToServer() throws IOException, ClassNotFoundException 
    {
        Socket socket = new Socket("localhost", 9898);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

    }
    
    public void Query(String SQL) 
    {
        String aux = SQL;

        try {
            out.writeObject(aux);
            out.flush();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        
        ResultSet RS_response = null;
        int INT_response = 0;
        
        String cmd=aux.substring(0, SQL.indexOf(' ')).toUpperCase();
        if(cmd.equals("SELECT")) // SQL e de tip SELECT
        {
            try {
                RS_response = (CachedRowSet) in.readObject();
                rs = RS_response;

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else // SQL e de tip INSERT/UPDATE/DELETE
        {
           try {
                INT_response = in.readInt();
                confExec = INT_response;

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
