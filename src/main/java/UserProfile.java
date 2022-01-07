import java.sql.Connection;
import java.sql.SQLException;

public class UserProfile {
  private   String name;
  private  String userName;
  private String pass;
  private String natinalCode;

  private Connection connection;
  public UserProfile(Connection connection) throws SQLException {
    this.connection=connection;
  }

  public UserProfile(Connection connection,String name, String userName, String pass, String natinalCode) {
    this.name = name;
    this.userName = userName;
    this.pass = pass;
    this.natinalCode = natinalCode;
    this.connection = connection;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public String getNatinalCode() {
    return natinalCode;
  }

  public void setNatinalCode(String natinalCode) {
    this.natinalCode = natinalCode;
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }
}

