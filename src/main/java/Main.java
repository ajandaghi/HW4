import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

public class Main

{
    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("org.postgresql.Driver");
        Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","test");

         ConsoleMenus consoleMenus=new ConsoleMenus(connection);
         consoleMenus.showMainMenu();

        System.out.println();
        connection.close();

    }
}
