import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.Scanner;
public class ConsoleMenus {
    private Connection connection;
    private CinemaSystems cinemaSystems;

    Scanner scanner=new Scanner(System.in);
    public ConsoleMenus(Connection connection) throws SQLException {
        this.connection = connection;
        cinemaSystems=new CinemaSystems(connection);
    }

    public void showMainMenu() throws SQLException, ParseException {
        System.out.println("select number of what you want to do");
        System.out.println("-1.exit");
        System.out.println("1.Login to System");
        System.out.println("2.register new Account");
        Scanner scanner=new Scanner(System.in);
        String in= scanner.nextLine();
        switch(in){
            case "-1":
                System.exit(1);
            case "1":
                loginMenu();
                break;
            case "2":
                registerMenu();
                break;
            default:
                System.out.println("wrong Input");
                showMainMenu();
        }
    }

    private void loginMenu() throws SQLException, ParseException {
        System.out.println("select number of what you want to do.");
        System.out.println("-1.return main menu");
        System.out.println("o.login  Administrator");
        System.out.println("1.login  Cinema");
        System.out.println("2.login  User Account");
        String in= scanner.nextLine();
        switch(in){
            case "-1":
                showMainMenu();
                break;
            case "0":
                doLogin("admin");
                break;
            case "1":
                doLogin("cinema");
                break;
            case "2":
                doLogin("user");
                break;
            default:
                System.out.println("wrong input");
                showMainMenu();
        }
    }

    private void registerMenu() throws SQLException, ParseException {
        System.out.println("select number of what you want to do?");
        System.out.println("-1.return main menu");
        System.out.println("1.register new Cinema");
        System.out.println("2.register new User Account");
        String in= scanner.nextLine();

        switch(in){
            case "=1":
                showMainMenu();
                break;
            case "1":
                doRegister("cinema");
                break;
            case "2":
                doRegister("user");
                break;
            default:
                System.out.println("wrong Input");
                showMainMenu();
        }
    }

    private void doRegister(String type) throws SQLException, ParseException {
        String[] cmd= null;

        switch (type) {
            case "cinema":
                System.out.println("enter command as: cinemaName/cinemaUser/cinemaPass");
                cmd = scanner.nextLine().split("/");
                if (cmd.length != 3 && !cmd[0].equals("-1")) {
                    System.out.println("wrong command format!");
                    doRegister(type);
                    registerMenu();
                } else if(cmd.length != 3 ){
                    registerMenu();
                }
                if (cinemaSystems.getCinemaRepository().searchByUser(cmd[1]) == null){
                    cinemaSystems.addCinema(cmd[0], cmd[1], cmd[2]);
                    System.out.println("successfully created.");
                    loginMenu();


                } else{
                    System.out.println("cinema user is unavailable, Choose another one!");
                    doRegister(type);

                }
            break;

            case "user":
                System.out.println("enter command as: name/userName/pass/nationalCode");
                cmd = scanner.nextLine().split("/");
                if (cmd.length != 4 && !cmd[0].equals("-1")) {
                    System.out.println("wrong command format!");
                    doRegister(type);
                    showMainMenu();

                 } else if(cmd.length != 4 ){
            registerMenu();
               }
                if (cinemaSystems.getUserRepository().searchByUser(cmd[1]) == null){
                    cinemaSystems.addUser(cmd[0], cmd[1], cmd[2],cmd[3]);
                    System.out.println("successfully created.");
                    loginMenu();
                } else{
                    System.out.println("cinema user is unavailable, Choose another one!");
                    doRegister(type);
                    loginMenu();
                }
                break;
        }
    }

    private void doLogin(String type) throws SQLException, ParseException {

        String user="";
        String pass="";
        
        switch(type){
            case "cinema":
                System.out.println("enter User:");
               user=scanner.nextLine();
               if (user.equals("")){
                   doLogin(type);

               } else if (user.equals("-1")){
                   loginMenu();
               }
                System.out.println("enter Pass:");
                pass=scanner.nextLine();
                if (pass.equals("")){
                    doLogin(type);

                } else if (pass.equals("-1")){
                    loginMenu();
                }

             if(cinemaSystems.cinemaLogin(user,pass)){
                  cinemaMenu();
             } else {
                 System.out.println("wrong Login.");
                 doLogin(type);
             }
                break;
            case "user":
                System.out.println("enter User:");
                user=scanner.nextLine();
                if (user.equals("")){
                    doLogin(type);

                    } else if (user.equals("-1")) {
                    loginMenu();
                }
                System.out.println("enter Pass:");
                pass=scanner.nextLine();
                if (pass.equals("")){
                    doLogin(type);

                 } else if (pass.equals("-1")) {
                    loginMenu();
                }

                if(cinemaSystems.userLogin(user,pass)){
                    UserMenu();
                } else {
                    System.out.println("wrong Login.");
                    doLogin(type);
                }
                break;
                
            case "admin":
                System.out.println("enter User:");
                user=scanner.nextLine();
                if (user.equals("")){
                    doLogin(type);

                } else if (user.equals("-1")) {
                    loginMenu();
                }

                System.out.println("enter Pass:");
                pass=scanner.nextLine();
                if (pass.equals("")){
                    doLogin(type);

                 } else if (pass.equals("-1")) {
                    loginMenu();
                }

                if(cinemaSystems.adminLogin(user,pass)){
                    adminMenu();
                } else {
                    System.out.println("wrong Login.");
                    doLogin(type);
                }
                break;
            default:
                System.out.println("wrong Input");
                loginMenu();
        }

    }

    private void adminMenu() throws SQLException, ParseException {
        System.out.println("select number of Cinema what you want to verify:");
        System.out.println("-1. logout");
        System.out.println("0.show all cinema status");
        cinemaSystems.showUnverifiedCinema();
        String input=scanner.nextLine();

        if(Integer.parseInt(input)<-1 || Integer.parseInt(input)>cinemaSystems.getCinemaRepository().unverifiedCinemaList().length){
            System.out.println("wrong input");
            adminMenu();
        }else if(input.equals("-1")) {
            loginMenu();
        } else if (Integer.parseInt(input)==0) {
            for (int i=0;i<cinemaSystems.getCinemaRepository().showAllCinema().length;i++) {
                System.out.println(cinemaSystems.getCinemaRepository().showAllCinema()[i].toString());
            }
            adminMenu();
        }else{
            cinemaSystems.verifyCinema(Integer.parseInt(input)-1);
            System.out.println("cinema number "+ input + " from list is now verified");
            adminMenu();
        }

    }

    private void UserMenu() throws SQLException, ParseException {
        System.out.println("select number of what you want to do.");
        System.out.println("0.logout");
        System.out.println("1.show all available tickets / order:");
        System.out.println("2.search tickets by film name / order");
        System.out.println("3.search tickets by date / order");
        System.out.println("4.search tickets by film name and date / order");
        System.out.println("5.show my orders.");
        String input = scanner.nextLine();
        String[] cmd = null;
        switch (input) {
            case "0":
                loginMenu();
                break;
            case "1":
                System.out.println("enter number of ticket you want to select:");
                System.out.println("-1.return user menu");
                cinemaSystems.showAllAvailableTicket();
                input=scanner.nextLine();
                if (!input.equals("-1")) {
                    cinemaSystems.orderTicketFromAll(input);
                    System.out.println("added to your account.");
                }
                UserMenu();

                break;

            case "2":
                System.out.println("enter film name:");
                System.out.println("-1.return user menu");
                input=scanner.nextLine();

                if (!input.equals("-1")) {
                    cinemaSystems.searchByFilmName(input);
                    String input1 = scanner.nextLine();
                    System.out.println("enter number of ticket you want to select:");
                    if(!input1.equals("-1")) {
                        cinemaSystems.orderTicketFromFilmName(input, input1);
                        System.out.println("your order is added.");
                    }
                    UserMenu();

                }
                UserMenu();
                break;

            case "3":
                System.out.println("enter dateTime as: YYYY-MM-dd HH:mm:ss");
                System.out.println("-1.return user menu");
                input=scanner.nextLine();


                if (!input.equals("-1")) {
                    cinemaSystems.searchByDate(input);
                    System.out.println("enter number of ticket you want to select:");
                    String input1 = scanner.nextLine();
                    if(!input1.equals("-1")) {
                        cinemaSystems.orderFromDate(input, input1);
                        System.out.println("your order is added.");
                    }
                    UserMenu();
                }
                UserMenu();
                break;

            case "4":
                System.out.println("enter command: filmname/YYYY-MM-dd HH:mm:ss");
                System.out.println("-1.return user menu");

               String[] input2=scanner.nextLine().split("/");
                if (!input2[0].equals("-1")) {
                    cinemaSystems.searchByfNameAndDate(input2);
                    System.out.println("enter number of ticket you want to select:");
                    String input1 = scanner.nextLine();
                    if(!input1.equals("-1")) {
                        cinemaSystems.orderFromfNameAndDate(input2, input1);
                        System.out.println("your order is added.");

                    }
                    UserMenu();
                }
                UserMenu();
                break;
            case "5":
                for(int i=0;i<cinemaSystems.getTicketRepository().findTicketByUser(cinemaSystems.getUser()).length;i++) {
                    System.out.println(cinemaSystems.getTicketRepository().findTicketByUser(cinemaSystems.getUser())[i].toString());
                }
                UserMenu();
                break;
            default:
                System.out.println("wrong input");
                UserMenu();

        }
    }

    private void cinemaMenu() throws SQLException, ParseException {
        System.out.println("select number of what you want:");

        if (cinemaSystems.getCinemaRepository().isVerified(cinemaSystems.getcUser())) {
            System.out.println("0. logout");
            System.out.println("1. add a ticket Type");
            System.out.println("2. remove a ticket Type");
            System.out.println("3. show sold Money");
            System.out.println("4. show all of cinema ticket");


        String input=scanner.nextLine();
        String[] cmd=null;
        switch(input) {
            case "0":
                loginMenu();
                break;
            case "1":
                System.out.println("enter command as: filmName/startTime(yyyy-MM-dd hh:mm:ss)/price/capacity");
                cmd = scanner.nextLine().split("/");
                if (cmd.length != 4 && cmd[0].equals("-1")) {
                    System.out.println("wrong command");
                    cinemaMenu();
                }else if(cmd.length != 4){
                    cinemaMenu();
                }
                cinemaSystems.createTicketType(cmd);
                cinemaMenu();
                break;
            case "2":
                System.out.println("enter ticketId");
                input=scanner.nextLine();
                if(!input.equals("-1")){
                    cinemaSystems.getTicketTypeRepository().deleteByTicketId(input,cinemaSystems.getcUser());
                }
                cinemaMenu();
            case "3":
                System.out.println("total earned amount: " +cinemaSystems.getCinemaRepository().showSoldMoney(cinemaSystems.getcUser()));
                cinemaMenu();

                break;
            case "4":
                for(int i=0;i<cinemaSystems.getTicketTypeRepository().searchByCuser(cinemaSystems.getcUser()).length;i++) {
                    System.out.println(cinemaSystems.getTicketTypeRepository().searchByCuser(cinemaSystems.getcUser())[i].toString());
                }
                cinemaMenu();
                break;
        }

        } else {
            System.out.println("your account should be verified to access full features");
            System.out.println("press any key to logout");
            String input=scanner.nextLine();
            loginMenu();
        }

    }

}
