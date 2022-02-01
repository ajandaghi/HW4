import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRepository {
    private Connection connection;

    public TicketRepository(Connection connection)  {
        try {
            this.connection = connection;
            String createTable = "Create Table if NOT EXISTS ticket(" +
                    "id serial Primary Key," +

                    "ticketId varchar(255)," +
                    "holder_user varchar(255))  ";

            PreparedStatement preparedStatement = connection.prepareStatement(createTable);
            preparedStatement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void orderTicket(Tickets tickets )  {
        try {
            String sql = "insert into ticket (ticketId,holder_user) values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tickets.getTicketId());
            preparedStatement.setString(2, tickets.getHolderUser());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Tickets[] findTicketByUser(String user)  {
        Tickets[] ticket=null;
try {

    String select = "select * from  ticket where holder_user=? ";
    PreparedStatement preparedStatement = connection.prepareStatement(select);
    preparedStatement.setString(1, user);
    ResultSet resultSet = preparedStatement.executeQuery();
    preparedStatement = connection.prepareStatement("select count(*) from  ticket where holder_user=?  ");
    preparedStatement.setString(1, user);
    ResultSet resultNum = preparedStatement.executeQuery();
    int i = 0;
    while (resultSet.next()) {
        if (i == 0) {
            resultNum.next();
            ticket = new Tickets[resultNum.getInt(1)];
        }
        ticket[i] = new Tickets(resultSet.getString("holder_user"), resultSet.getString("ticketId"), connection);
        i++;
    }
} catch (SQLException e){
    e.printStackTrace();
}

        return ticket;
    }

}
