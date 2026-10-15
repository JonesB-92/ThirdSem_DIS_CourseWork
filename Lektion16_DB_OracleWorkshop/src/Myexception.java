
import java.sql.Connection;

public class Myexception extends Exception {
    Connection con;

    Myexception(String message, Connection c) {
        super(message);
        con = c;
    }

}
