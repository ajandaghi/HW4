import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;


public class CinemaSystems {
private Connection connection;
private CinemaRepository cinemaRepository;
private TicketTypeRepository ticketTypeRepository;
private  UserRepository userRepository;
private TicketRepository ticketRepository;
private String cUser="";
private String user="";

private  String adminUser="admin";
private String adminPass="admin";

    public CinemaSystems(Connection connection)  {
        this.connection=connection;
        cinemaRepository =new CinemaRepository(connection);
        ticketTypeRepository=new TicketTypeRepository(connection);
        ticketRepository=new TicketRepository(connection);
        userRepository=new UserRepository(connection);
    }

    public  void createTicketType(String[] cmd)  {
       String newId=  ticketTypeRepository.getLastIndexId()+cUser;
        TicketType ticketType=new TicketType(cUser,newId,cmd[0], Timestamp.valueOf(cmd[1]) ,Long.parseLong(cmd[2]),Integer.parseInt(cmd[3]));

        if(ticketTypeRepository.isUnique(ticketType) && Timestamp.valueOf(cmd[1]).after(new Timestamp(System.currentTimeMillis())) ) {
            ticketTypeRepository.importTicketType(ticketType);
            System.out.println("ticket successfully created.");
        }
        else if (!ticketTypeRepository.isUnique(ticketType))
            System.out.println("your ticket time is not unique");
        else
            System.out.println("enter new time for future after:"+new Timestamp(System.currentTimeMillis()));
    }



    public void addCinema(String cinemaName,String cinemaUser,String cinemaPass)  {

    cinemaRepository.insertCinema(new Cinema(connection,cinemaName,cinemaUser,cinemaPass));
}

public void addUser(String name, String userName, String pass, String nationalCode)  {
    userRepository.insertCinema(new UserProfile(connection,name,userName,pass,nationalCode));

}

public boolean checkCinemaUser(String cinemaUser)  {
if(cinemaRepository.searchByUser(cinemaUser)!=null){
    return true;
}
return false;
}

public boolean cinemaLogin(String cinemaUser, String pass)  {
        if (cinemaRepository.searchByUser(cinemaUser)!=null) {
            if (cinemaRepository.searchByUser(cinemaUser).getCinemaUser().equals(cinemaUser) && cinemaRepository.searchByUser(cinemaUser).getCinemaPass().equals(pass)) {
                cUser=cinemaUser;
                return true;
            }
        }
    return false;
}

    public boolean userLogin(String user, String pass) {
        if(userRepository.searchByUser(user)!=null ) {
            if (userRepository.searchByUser(user).getUserName().equals(user) && userRepository.searchByUser(user).getPass().equals(pass)) {
                this.user = user;
                return true;
            }
        }
        return false;

    }

    public boolean adminLogin(String user, String pass) {
        if (adminUser.equals(user)&& adminPass.equals(pass)){
            return true;
        }
        return false;
    }

    public void showUnverifiedCinema()  {

        for(int i=0;i<cinemaRepository.unverifiedCinemaList().length;i++){
            System.out.println((i+1)+". Cinema Name:"+cinemaRepository.unverifiedCinemaList()[i].getCinemaName()+" user:"+cinemaRepository.unverifiedCinemaList()[i].getCinemaUser()+" totalTicketAmount:"+cinemaRepository.unverifiedCinemaList()[i].getTotalSoldTicketAmmount());
        }
    }
    public void verifyCinema(int i)  {
        cinemaRepository.verifyCinema(cinemaRepository.unverifiedCinemaList()[i]);
    }

    public void showAllAvailableTicket()  {
        if(ticketTypeRepository.showAllAvailable()!=null){
           for(int i=0;i<ticketTypeRepository.showAllAvailable().length;i++) {
               System.out.println((i+1)+" "+ticketTypeRepository.showAllAvailable()[i].toString());
           }
        } else
            System.out.println("no available ticket");
    }

    public void searchByFilmName(String filmName)  {
        if(ticketTypeRepository.searchByFilmName(filmName)!=null){
            for(int i=0;i<ticketTypeRepository.searchByFilmName(filmName).length;i++) {
                System.out.println((i+1)+" "+ticketTypeRepository.searchByFilmName(filmName)[i].toString());
            }
        } else
            System.out.println("no available ticket");

    }

    public void orderTicketFromAll(String input)  {
ticketRepository.orderTicket(new Tickets(user,ticketTypeRepository.showAllAvailable()[Integer.parseInt(input)-1].getTicketId(),connection));
        cinemaRepository.addSoldMoney(ticketTypeRepository.showAllAvailable()[Integer.parseInt(input)-1].getCinemaUser(),ticketTypeRepository.showAllAvailable()[Integer.parseInt(input)-1].getPrice());
        ticketTypeRepository.addToSoldTicket(ticketTypeRepository.showAllAvailable()[Integer.parseInt(input)-1].getTicketId());

    }


    public void orderTicketFromFilmName(String filmName, String input1)  {
        ticketRepository.orderTicket(new Tickets(user,ticketTypeRepository.searchByFilmName(filmName)[Integer.parseInt(input1)-1].getTicketId(),connection));
        cinemaRepository.addSoldMoney(ticketTypeRepository.searchByFilmName(filmName)[Integer.parseInt(input1)-1].getCinemaUser(),ticketTypeRepository.searchByFilmName(filmName)[Integer.parseInt(input1)-1].getPrice());
        ticketTypeRepository.addToSoldTicket(ticketTypeRepository.searchByFilmName(filmName)[Integer.parseInt(input1)-1].getTicketId());

    }

    public void searchByDate(String filmDate)  {
        if(ticketTypeRepository.searchByDate(Timestamp.valueOf(filmDate))!=null){
            for(int i=0;i<ticketTypeRepository.searchByDate(Timestamp.valueOf(filmDate)).length;i++) {
                System.out.println((i+1)+" "+ticketTypeRepository.searchByDate(Timestamp.valueOf(filmDate))[i].toString());
            }
        } else
            System.out.println("no available ticket");

    }

    public void orderFromDate(String filmDate, String input)  {
        ticketRepository.orderTicket(new Tickets(user,ticketTypeRepository.searchByDate(Timestamp.valueOf(filmDate))[Integer.parseInt(input)-1].getTicketId(),connection));
        cinemaRepository.addSoldMoney(ticketTypeRepository.searchByDate(Timestamp.valueOf(filmDate))[Integer.parseInt(input)-1].getCinemaUser(),ticketTypeRepository.searchByDate(Timestamp.valueOf(filmDate))[Integer.parseInt(input)-1].getPrice());
        ticketTypeRepository.addToSoldTicket(ticketTypeRepository.searchByDate(Timestamp.valueOf(filmDate))[Integer.parseInt(input)-1].getTicketId());

    }

    public void searchByfNameAndDate(String[] input)  {
        if (ticketTypeRepository.searchByfNameAndDate(input[0], Timestamp.valueOf(input[1])) != null) {
            for (int i = 0; i < ticketTypeRepository.searchByfNameAndDate(input[0], Timestamp.valueOf(input[1])).length; i++) {
                System.out.println((i + 1) + " " + ticketTypeRepository.searchByfNameAndDate(input[0], Timestamp.valueOf(input[1]))[i].toString());
            }

        } else
            System.out.println("no available ticket");

    }

    public void orderFromfNameAndDate(String[] input, String input1)  {
        ticketRepository.orderTicket(new Tickets(user,ticketTypeRepository.searchByfNameAndDate(input[0], Timestamp.valueOf(input[1]))[Integer.parseInt(input1)-1].getTicketId(),connection));
        cinemaRepository.addSoldMoney(ticketTypeRepository.searchByfNameAndDate(input[0], Timestamp.valueOf(input[1]))[Integer.parseInt(input1)-1].getCinemaUser(),ticketTypeRepository.searchByfNameAndDate(input[0], Timestamp.valueOf(input[1]))[Integer.parseInt(input1)-1].getPrice());
        ticketTypeRepository.addToSoldTicket(ticketTypeRepository.searchByfNameAndDate(input[0], Timestamp.valueOf(input[1]))[Integer.parseInt(input1)-1].getTicketId());

    }
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public CinemaRepository getCinemaRepository() {
        return cinemaRepository;
    }

    public void setCinemaRepository(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public TicketRepository getTicketRepository() {
        return ticketRepository;
    }


    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    public TicketTypeRepository getTicketTypeRepository() {
        return ticketTypeRepository;
    }

    public void setTicketTypeRepository(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }



}
