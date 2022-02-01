import java.sql.Connection;
import java.sql.SQLException;

public class Cinema {
    private Connection connection;
private String cinemaName;
private String cinemaUser;
private String cinemaPass;
private Long totalSoldTicketAmmount=0L;
private boolean isVerified;

    public Cinema(Connection connection, String cinemaName, String cinemaUser, String cinemaPass)  {
        this.connection = connection;
        this.cinemaName = cinemaName;
        this.cinemaUser = cinemaUser;
        this.cinemaPass = cinemaPass;

    }

    public Cinema(String cinemaName, String cinemaUser , Long totalSoldTicketAmmount, boolean isVerified) {
        this.cinemaName = cinemaName;
        this.cinemaUser = cinemaUser;
        this.totalSoldTicketAmmount = totalSoldTicketAmmount;
        this.isVerified = isVerified;
    }

    public Cinema() {

    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaUser() {
        return cinemaUser;
    }

    public void setCinemaUser(String cinemaUser) {
        this.cinemaUser = cinemaUser;
    }

    public String getCinemaPass() {
        return cinemaPass;
    }

    public void setCinemaPass(String cinemaPass) {
        this.cinemaPass = cinemaPass;
    }

    public Long getTotalSoldTicketAmmount() {
        return totalSoldTicketAmmount;
    }

    public void setTotalSoldTicketAmmount(Long totalSoldTicketAmmount) {
        this.totalSoldTicketAmmount = totalSoldTicketAmmount;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "cinemaName='" + cinemaName + '\'' +
                ", cinemaUser='" + cinemaUser + '\'' +
                ", totalSoldTicketAmmount=" + totalSoldTicketAmmount +
                ", isVerified=" + isVerified +
                '}';
    }
}
