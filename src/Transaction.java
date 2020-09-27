import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {
    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/demo-jdbc-transaction?useSSL=false";
        String username = "root";
        String password = "123456";
        try{
            Connection conn = DriverManager.getConnection(dbURL,username,password);
            Statement statement = conn.createStatement();

            conn.setAutoCommit(false);

            try{
                statement.executeUpdate("UPDATE account_banking SET amount = 90000000.0 WHERE name = 'A'");
                statement.executeUpdate("UPDATE account_banking SET amount = 140000000.0 WHERE name = 'B'");
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                conn.rollback();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
