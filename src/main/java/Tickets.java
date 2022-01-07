import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class Tickets {

private String holderUser;
private String ticketId;

private Connection connection;
    public Tickets(Connection connection) throws SQLException {
        this.connection=connection;
    }

    public Tickets(String holderUser, String ticketId, Connection connection) {
        this.holderUser = holderUser;
        this.ticketId = ticketId;
        this.connection = connection;
    }

    public String getHolderUser() {
        return holderUser;
    }

    public void setHolderUser(String holderUser) {
        this.holderUser = holderUser;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "holderUser='" + holderUser + '\'' +
                ", ticketId='" + ticketId + '\'' +
                '}';
    }
}
