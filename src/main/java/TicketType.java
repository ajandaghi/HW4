import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


public class TicketType {
    private String cinemaUser;
    private String ticketId;
    private String filmName;
    private Timestamp startTime;
    private Long price;
    private int capacity;
    private int sold;

    public TicketType(String cinemaUser, String ticketId, String filmName, Timestamp startTime, Long price, int capacity) {
        this.cinemaUser = cinemaUser;
        this.ticketId = ticketId;
        this.filmName = filmName;
        this.startTime = startTime;
        this.price = price;
        this.capacity=capacity;
    }

    public String getCinemaUser() {
        return cinemaUser;
    }

    public void setCinemaUser(String cinemaUser) {
        this.cinemaUser = cinemaUser;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    @Override
    public String toString() {
        return "TicketType{" +
                "cinemaUser='" + cinemaUser + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", filmName='" + filmName + '\'' +
                ", startTime=" + startTime +
                ", price=" + price +
                ", available=" + (capacity-sold) +

                '}';
    }


}
