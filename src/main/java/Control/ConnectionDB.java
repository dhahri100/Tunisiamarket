package Control;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static Connection conn = null;

    private ConnectionDB() {}

    public static Connection openConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tunisiamarket", "root", "");
        }
        return conn;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            // Gérer l'exception, par exemple en affichant un message d'erreur
            ex.printStackTrace();
        }
    }
}
