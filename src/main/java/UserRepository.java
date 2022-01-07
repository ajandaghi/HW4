import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private Connection connection;

    public UserRepository(Connection connection) throws SQLException {
        this.connection = connection;
        String createTable="Create Table if NOT EXISTS user_profile("+
                "id serial Primary Key,"+
                "name varchar(255),"+
                "user_name varchar(255),"+
                "pass varchar(255),"+
                "natinalCode char(10));";

        PreparedStatement preparedStatement=connection.prepareStatement(createTable) ;
        preparedStatement.execute();
    }

    public void insertCinema(UserProfile user) throws SQLException {
        String createTable="Insert into  user_profile(name,user_name,pass,natinalCode)values(?,?,?,?);";


        PreparedStatement preparedStatement=connection.prepareStatement(createTable) ;
        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2,user.getUserName());
        preparedStatement.setString(3,user.getPass());
        preparedStatement.setString(4,user.getNatinalCode());


        preparedStatement.executeUpdate();
    }
    public UserProfile searchByUser(String user) throws SQLException {
        UserProfile userProfile=null;
        String select = "select * from  user_profile where user_name=? ";


        PreparedStatement preparedStatement = connection.prepareStatement(select);
        preparedStatement.setString(1, user);

        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            userProfile = new UserProfile(connection);
            userProfile.setName(result.getString("name"));
            userProfile.setUserName(result.getString("user_name"));
            userProfile.setPass(result.getString("pass"));
            userProfile.setNatinalCode(result.getString("natinalCode"));

        }
        return userProfile;
    }
}
