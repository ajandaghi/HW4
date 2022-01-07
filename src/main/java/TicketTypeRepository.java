import java.sql.*;

public class TicketTypeRepository {
    private Connection connection;

    public TicketTypeRepository(Connection connection) throws SQLException {
        this.connection = connection;
        String createTable = "Create Table if NOT EXISTS ticket_type(" +
                "id serial Primary Key," +
                "cinema_user varchar(255)," +
                "film_name varchar(255)," +
                "start_time timestamp ," +
                "ticketId varchar(255)," +
                "price bigint, " +
                "capacity integer," +
                "sold integer);";

        PreparedStatement preparedStatement = connection.prepareStatement(createTable);
        preparedStatement.execute();

    }


    public int getLastIndexId() throws SQLException {
        String lastIndexId = "select max(id) from ticket_type ";
        PreparedStatement preparedStatement = connection.prepareStatement(lastIndexId);

        ResultSet result = preparedStatement.executeQuery();

        result.next();
        return result.getInt(1);


    }

    public void importTicketType(TicketType ticketType) throws SQLException {
        String insert = "insert into ticket_type (cinema_user,film_name,start_time,ticketId,price,capacity,sold) values (?,?,?,?,?,?,0)";
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setString(1, ticketType.getCinemaUser());
        preparedStatement.setString(2, ticketType.getFilmName());
        preparedStatement.setTimestamp(3, ticketType.getStartTime());
        preparedStatement.setString(4, ticketType.getTicketId());
        preparedStatement.setLong(5, ticketType.getPrice());
        preparedStatement.setInt(6, ticketType.getCapacity());
        preparedStatement.executeUpdate();

    }

    public Boolean isUnique(TicketType ticketType) throws SQLException {
        String selectSql = "select * from ticket_type where cinema_user=? and film_name=? and start_time=?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        preparedStatement.setString(1, ticketType.getCinemaUser());
        preparedStatement.setString(2, ticketType.getFilmName());
        preparedStatement.setTimestamp(3, ticketType.getStartTime());
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()) {
            return false;
        }

        return true;

    }
    public TicketType[] searchByCuser(String cUser) throws SQLException {
        String selectSql = "select * from ticket_type where cinema_user=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        preparedStatement.setString(1,cUser);
        ResultSet result = preparedStatement.executeQuery();
        TicketType[] rslt = null;

        preparedStatement= connection.prepareStatement("select count(*) from ticket_type where cinema_user=? ");
        preparedStatement.setString(1,cUser);
        ResultSet resultNum=preparedStatement.executeQuery();
        int i = 0;
        while (result.next()) {
            if (i == 0) {
                resultNum.next();
                rslt = new TicketType[resultNum.getInt(1)];
            }

            rslt[i] = new TicketType(result.getString("cinema_user"), result.getString("ticketId"), result.getString("film_name"), result.getTimestamp("start_time"), result.getLong("price"), result.getInt("capacity"));
            rslt[i].setSold(result.getInt("sold"));
            i++;
        }

        return rslt;
    }
    public TicketType[] showAllAvailable() throws SQLException {
        String selectSql = "select * from ticket_type where (capacity-sold)>0 and (age(start_time)<age(now()))";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        ResultSet result = preparedStatement.executeQuery();
        TicketType[] rslt = null;

        ResultSet resultNum = connection.prepareStatement("select count(*) from ticket_type where (capacity-sold)>0 and (age(start_time)<age(now()))").executeQuery();

        int i = 0;
        while (result.next()) {
            if (i == 0) {
                resultNum.next();
                rslt = new TicketType[resultNum.getInt(1)];
            }

            rslt[i] = new TicketType(result.getString("cinema_user"), result.getString("ticketId"), result.getString("film_name"), result.getTimestamp("start_time"), result.getLong("price"), result.getInt("capacity"));
            rslt[i].setSold(result.getInt("sold"));
            i++;
        }

        return rslt;
    }



  public TicketType[] searchByFilmName(String fimName) throws SQLException {
      String selectSql="select * from ticket_type where ((capacity-sold)>0 and age(start_time)<age(now())) and film_name=?";
      PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
      preparedStatement.setString(1,fimName);
      ResultSet result =preparedStatement.executeQuery();
      TicketType[] rslt = null;

      preparedStatement = connection.prepareStatement("select count(*) from ticket_type where (capacity-sold)>0 and (age(start_time)<age(now())) and film_name=?");
      preparedStatement.setString(1,fimName);
      ResultSet resultNum=preparedStatement.executeQuery();
      int i = 0;

      while(result.next()){
          if (i == 0) {
              resultNum.next();
              rslt = new TicketType[resultNum.getInt(1)];
          }

          rslt[i] = new TicketType(result.getString("cinema_user"), result.getString("ticketId"), result.getString("film_name"), result.getTimestamp("start_time"), result.getLong("price"), result.getInt("capacity"));
          rslt[i].setSold(result.getInt("sold"));
          i++;


      }
  return rslt;
  }

    public TicketType[] searchByDate(Timestamp dateTime) throws SQLException {
        String selectSql="select * from ticket_type where (capacity-sold)>0 and (age(start_time)<age(now())) and start_time=?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        preparedStatement.setTimestamp(1,dateTime);
        ResultSet result =preparedStatement.executeQuery();
        TicketType[] rslt = null;

        preparedStatement = connection.prepareStatement("select count(*) from ticket_type where (capacity-sold)>0 and (age(start_time)<age(now())) and start_time=?");
        preparedStatement.setTimestamp(1,dateTime);
        ResultSet resultNum=preparedStatement.executeQuery();
        int i = 0;

        while(result.next()){
            if (i == 0) {
                resultNum.next();
                rslt = new TicketType[resultNum.getInt(1)];
            }

            rslt[i] = new TicketType(result.getString("cinema_user"), result.getString("ticketId"), result.getString("film_name"), result.getTimestamp("start_time"), result.getLong("price"), result.getInt("capacity"));
            rslt[i].setSold(result.getInt("sold"));
            i++;


        }
        return rslt;
    }

    public TicketType[] searchByfNameAndDate(String fName, Timestamp dateTime ) throws SQLException {
        String selectSql="select * from ticket_type where (capacity-sold)>0 and (age(start_time)<age(now())) and start_time=? and film_name=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        preparedStatement.setTimestamp(1,dateTime);
        preparedStatement.setString(2,fName);

        ResultSet result =preparedStatement.executeQuery();
        TicketType[] rslt = null;

        preparedStatement = connection.prepareStatement("select count(*) from ticket_type where (capacity-sold)>0 and (age(start_time)<age(now())) and start_time=? and film_name=?");
        preparedStatement.setTimestamp(1,dateTime);
        preparedStatement.setString(2,fName);
        ResultSet resultNum=preparedStatement.executeQuery();
        int i = 0;

        while(result.next()){
            if (i == 0) {
                resultNum.next();
                rslt = new TicketType[resultNum.getInt(1)];
            }

            rslt[i] = new TicketType(result.getString("cinema_user"), result.getString("ticketId"), result.getString("film_name"), result.getTimestamp("start_time"), result.getLong("price"), result.getInt("capacity"));
            rslt[i].setSold(result.getInt("sold"));
            i++;


        }
        return rslt;
    }



    public void addToSoldTicket(String tickId) throws SQLException {
      String selectSql="select sold from ticket_type where ticketId=?";
      PreparedStatement preparedStatement=connection.prepareStatement(selectSql) ;
      preparedStatement.setString(1,tickId);
      ResultSet result= preparedStatement.executeQuery();
      result.next(); int sld=result.getInt(1);

      preparedStatement=connection.prepareStatement("update ticket_type set sold=? where ticketId=?");
     preparedStatement.setInt(1,(sld+1));
        preparedStatement.setString(2,tickId);

        preparedStatement.executeUpdate();



  }

  public void deleteByTicketId(String tickId, String cuser) throws SQLException {
      String deleteSql="Delete  from ticket_type where ticketId=? and (age(start_time)<age(now())) and cinema_user=?" ;
      PreparedStatement preparedStatement=connection.prepareStatement(deleteSql) ;
      preparedStatement.setString(1,tickId);
      preparedStatement.setString(2,cuser);
      int l=preparedStatement.executeUpdate();
      if(l>0) System.out.println("Deleted"); else System.out.println("you can't delete it!");

  }


}
