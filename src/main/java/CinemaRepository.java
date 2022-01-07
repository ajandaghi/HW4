import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CinemaRepository {
    private Connection connection;

    public CinemaRepository(Connection connection) throws SQLException {
        this.connection = connection;
        String createTable="Create Table if NOT EXISTS cinema("+
                "id serial Primary Key,"+
                "cinema_name varchar(255),"+
                "cinema_user varchar(255),"+
                "cinema_pass varchar(255),"+
                "totalSoldTicketAmmount bigint,"+
                 "isVerified boolean);  ";

        PreparedStatement preparedStatement=connection.prepareStatement(createTable) ;
        preparedStatement.execute();

    }

    public void insertCinema(Cinema cinema) throws SQLException {
        String createTable="Insert into  cinema(cinema_name,cinema_user,cinema_pass,totalSoldTicketAmmount,isVerified)values(?,?,?,?,?);";


        PreparedStatement preparedStatement=connection.prepareStatement(createTable) ;
        preparedStatement.setString(1,cinema.getCinemaName());
        preparedStatement.setString(2,cinema.getCinemaUser());
        preparedStatement.setString(3,cinema.getCinemaPass());
        preparedStatement.setLong(4,0L);
        preparedStatement.setBoolean(5,false);
        preparedStatement.executeUpdate();
    }
    public Cinema searchByUser(String user) throws SQLException {
        Cinema cinema=null;
        String select = "select * from  cinema where cinema_user=? ";


        PreparedStatement preparedStatement = connection.prepareStatement(select);
        preparedStatement.setString(1, user);

        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {

            cinema = new Cinema();
            cinema.setCinemaName(result.getString("cinema_name"));
            cinema.setCinemaUser(result.getString("cinema_user"));
            cinema.setCinemaPass(result.getString("cinema_pass"));
            cinema.setTotalSoldTicketAmmount(result.getLong("totalSoldTicketAmmount"));
            cinema.setVerified(result.getBoolean("isVerified"));

        }
        return cinema;
    }

    public Cinema[] unverifiedCinemaList() throws SQLException {

        String selectNum = "select *  from  cinema where isVerified=? ";

        String select0 = "select count(*)  from  cinema where isVerified=? ";
        PreparedStatement preparedStatement0 = connection.prepareStatement(select0);
        preparedStatement0.setBoolean(1, false);
        ResultSet result0 = preparedStatement0.executeQuery();
        result0.next(); Cinema[] cinema=new Cinema[result0.getInt(1)];

        String select = "select *  from  cinema where isVerified=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(select);
        preparedStatement.setBoolean(1, false);

        ResultSet result = preparedStatement.executeQuery();
        int i=0;

        while (result.next()) {
            cinema[i]= new Cinema();
            cinema[i].setCinemaName(result.getString("cinema_name"));
            cinema[i].setCinemaUser(result.getString("cinema_user"));
            cinema[i].setCinemaPass(result.getString("cinema_pass"));
            cinema[i].setTotalSoldTicketAmmount(result.getLong("totalSoldTicketAmmount"));
            cinema[i].setVerified(result.getBoolean("isVerified"));
            i++;
        }
        return cinema;

    }

    public void verifyCinema(Cinema cinema) throws SQLException {
        String select = "update cinema set  isVerified=?  where cinema_user=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(select);
        preparedStatement.setBoolean(1, true);
        preparedStatement.setString(2, cinema.getCinemaUser());
        preparedStatement.executeUpdate();
    }

    public Boolean isVerified(String cUser) throws SQLException {
        String selectSql="select isVerified from cinema where cinema_user=? ";
        PreparedStatement preparedStatement=connection.prepareStatement(selectSql) ;
        preparedStatement.setString(1,cUser);

        ResultSet result= preparedStatement.executeQuery();

        if(result.next()){
            return result.getBoolean(1);
        }

        return true;

    }

    public Long showSoldMoney(String cUser) throws SQLException {
        String select = "select * from  cinema where cinema_user=? ";
        Long amount=0L;
        PreparedStatement preparedStatement=connection.prepareStatement(select) ;
        preparedStatement.setString(1,cUser);
        ResultSet result= preparedStatement.executeQuery();
        if(result.next()){
             amount= result.getLong("totalSoldTicketAmmount");
        }
        return amount;


    }

    public void addSoldMoney(String cUser,Long price) throws SQLException {
        String select = "update  cinema set totalSoldTicketAmmount=?  where cinema_user=? ";
        Long amount=showSoldMoney(cUser);
        PreparedStatement preparedStatement=connection.prepareStatement(select) ;
        preparedStatement.setLong(1,(amount+price));
        preparedStatement.setString(2,cUser);
        preparedStatement.executeUpdate();

    }

    public Cinema[] showAllCinema() throws SQLException {
        String select = "select * from  cinema ";
        PreparedStatement preparedStatement=connection.prepareStatement(select) ;
        ResultSet resultSet=preparedStatement.executeQuery();
         preparedStatement=connection.prepareStatement("select count(*) from cinema ") ;
        ResultSet resultNum= preparedStatement.executeQuery();
        Cinema[] cinema=null;
        int i=0;
        while(resultSet.next()){
            if(i==0) {resultNum.next(); cinema=new Cinema[resultNum.getInt(1)];}
            cinema[i]=new Cinema(resultSet.getString("cinema_name"),resultSet.getString("cinema_user"),resultSet.getLong("totalSoldTicketAmmount"),resultSet.getBoolean("isVerified"));
            i++;
        }

        return cinema;

    }
}
