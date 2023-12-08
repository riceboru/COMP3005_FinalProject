import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Statement;

import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.Scanner;

public class FitnessClubDBOP {
    private final String url = "jdbc:postgresql://localhost:5432/Project_FitnessClubDB";
    private final String user = "postgres";
    private final String password = "postgres";

    //Selection 1
    //get all users include member coach and staff
    public void getAllUsers(){
        System.out.println("\nPrinting all users....");

        System.out.println("==========================");
        getAllMember();
        System.out.println("=========================");
        getAllCoaches();
        System.out.println("==========================");
        getAllStaff();
    }
    //print all members
    public void getAllMember(){
        System.out.println("Member list: ");

        String SQL = "SELECT * FROM member";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int MID = rs.getInt("mid");
                String name = rs.getString("name");
                int loyaltypoint = rs.getInt("loyaltypoint");
                String goal = rs.getString("fitnessgoals");
                String metrics = rs.getString("healthmetrics");

                // printing student info
                System.out.println("--------------------------");
                System.out.println("#" + MID + " " + name);
                System.out.println("Loyaltypoint: " + loyaltypoint);
                System.out.println("Fitness goals: " + goal);
                System.out.println("Healthmetrics: " + metrics);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //print all coaches
    public void getAllCoaches(){
        System.out.println("Coach list");

        String SQL = "SELECT * FROM coach";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int CID = rs.getInt("cid");
                String name = rs.getString("name");

                // printing student info
                System.out.println("#" + CID + " " + name);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //print all staff
    public void getAllStaff(){
        System.out.println("Staff list");

        String SQL = "SELECT * FROM staff";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int SID = rs.getInt("sid");
                String name = rs.getString("name");

                // printing student info
                System.out.println("#" + SID + " " + name);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //Selection 2
    //Update member's Fitness Goals
    public void UpdateFitnessGoals(int MID, String text){
        String SQL = "UPDATE member SET fitnessgoals = ? WHERE mid = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, text); // new FitnessGoal
            pstmt.setInt(2, MID); // member ID

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Fitness goals updated successfully for member ID " + MID);
            } else {
                System.out.println("Member ID not found: " + MID);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //Update member's HealthMetrics
    public void UpdateHealthMetrics(int MID, String text){
        String SQL = "UPDATE member SET healthmatrics = ? WHERE mid = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, text); // new health matrics
            pstmt.setInt(2, MID); // member ID

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Fitness goals updated successfully for member ID " + MID);
            } else {
                System.out.println("Member ID not found: " + MID);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //Update member's LoyaltyPoint
    public void UpdateLoyaltyPoint(int MID, int newPoint){
        String SQL = "UPDATE member SET loyaltypoint = ? WHERE mid = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, newPoint); // new loyalty point

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Fitness goals updated successfully for member ID " + MID);
            } else {
                System.out.println("Member ID not found: " + MID);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //Adding new member
    public void AddNewMember(String name){
        String SQL = "INSERT INTO member (name,LoyaltyPoint) VALUES (?,0)";
        int loyaltypoint = 0;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name); //member's name

            int affectedRows = pstmt.executeUpdate();

            // Check if success
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long newMemberId = rs.getLong(1);
                        System.out.println("New member added with ID: " + newMemberId);
                    }
                }
            } else {
                System.out.println("A new member could not be added.");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //A comprehensive platform to manage membership status
    public void manageMember(){
        Scanner scanner = new Scanner(System.in);
        String input;
        int choose = -1;
        int chooseMID;

        while (choose != 0) {
            System.out.println("--------------MEMBER MANAGEMENT------------");
            System.out.println("1. Show all Members");
            System.out.println("2. Update fitness goals");
            System.out.println("3. Update health metrics");
            System.out.println("4. Update loyalty point");
            System.out.println("5. Add new member");
            System.out.println("0. Return to main menu");

            choose = scanner.nextInt();
            scanner.nextLine();

            switch (choose) {
                case 1:
                    getAllMember();
                    break;
                case 2:
                    System.out.println("Please enter Menber's MID");
                    chooseMID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new fitness goals:");
                    String newGoals = scanner.nextLine();

                    UpdateFitnessGoals(chooseMID, newGoals);
                    break;
                case 3:
                    System.out.println("Please enter Menber's MID");
                    chooseMID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new Health Metrics:");
                    String newMetrics = scanner.nextLine();

                    UpdateHealthMetrics(chooseMID,newMetrics);
                    break;
                case 4:
                    System.out.println("Please enter Menber's MID");
                    chooseMID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Please enter new Loyalty Point");
                    int newPoint = scanner.nextInt();
                    scanner.nextLine();

                    UpdateLoyaltyPoint(chooseMID, newPoint);
                    break;

                case 5:
                    System.out.println("Please new Menber's name");
                    String name = scanner.nextLine();

                    AddNewMember(name);
                    break;
                case 0:
                    return;
            }
        }
    }
    //Selection 3 Manage coach
    //A comprehensive platform to manage coach
    public void getAllAvailableTime(){
        System.out.println("Coach's Available Time:");

        String SQL = "SELECT coachschedules.schedulesid, coachschedules.CID, coach.Name, coachschedules.availabletime "
                + "FROM coachschedules "
                + "JOIN coach ON coachschedules.CID = coach.CID";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int schedulesid = rs.getInt("schedulesid");
                int CID = rs.getInt("CID");
                String name = rs.getString("Name");
                Date availabletime = rs.getDate("availabletime");

                // printing student info
                System.out.println(schedulesid + ": #" + CID + " " + name + " Avaliable time: " + availabletime);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void AddNewCoach(String name) {
        String SQL = "INSERT INTO coach (name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name); //member's name

            int affectedRows = pstmt.executeUpdate();

            // Check if success
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long newMemberId = rs.getLong(1);
                        System.out.println("New coach added with ID: " + newMemberId);
                    }
                }
            } else {
                System.out.println("A new coach could not be added.");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //add new schedule for a coach
    public void AddnewAvailableTime(int chooseCID,String dateString){
        Date date;

        String SQL = "INSERT INTO CoachSchedules (CID,AvailableTime) VALUES (?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            //change String date into Date
            try {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                date = new java.sql.Date(utilDate.getTime());
            } catch (ParseException e) {
                System.out.println("ERROR!! invalid date format.");
                return;
            }

            pstmt.setInt(1, chooseCID); //coach's cid
            pstmt.setDate(2, date);

            int affectedRows = pstmt.executeUpdate();

            // Check if success
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long newMemberId = rs.getLong(1);
                        System.out.println("New coach schedule added with ID: " + newMemberId);
                    }
                }
            } else {
                System.out.println("A new coach schedule could not be added.");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //main for manage coach
    public void manageCoach(){
        Scanner scanner = new Scanner(System.in);
        int choose = -1;
        int chooseCID;

        while (choose != 0) {
            System.out.println("--------------COACH MANAGEMENT------------");
            System.out.println("1. Show all Coaches");
            System.out.println("2. Show all available times");
            System.out.println("3. Add new coach");
            System.out.println("4. Add new available times");

            choose = scanner.nextInt();
            scanner.nextLine();

            switch (choose) {
                case 1:
                    getAllCoaches();
                    break;
                case 2:
                    getAllAvailableTime();
                    break;
                case 3:
                    System.out.println("Please new Coach's name");
                    String name = scanner.nextLine();

                    AddNewCoach(name);
                    break;


                case 4:
                    System.out.println("Please enter Coach's MID:");
                    chooseCID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Now, Please enter New available time: (yyyy-MM-dd)");
                    String dateString = scanner.nextLine();

                    AddnewAvailableTime(chooseCID,dateString);

                    break;

                case 0:
                    return;
            }
        }
    }
    //Selection 4 Manage staff
    public void AddNewStaff(String name) {
        String SQL = "INSERT INTO staff (name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name); //staff's name

            int affectedRows = pstmt.executeUpdate();

            // Check if success
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long newMemberId = rs.getLong(1);
                        System.out.println("New staff added with ID: " + newMemberId);
                    }
                }
            } else {
                System.out.println("A new staff could not be added.");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //To manage membership staff
    public void manageStaff(){
        Scanner scanner = new Scanner(System.in);
        int choose = -1;

        while (choose != 0) {
            System.out.println("--------------STAFF MANAGEMENT------------");
            System.out.println("1. Show all Staff");
            System.out.println("2. Add new staff");
            System.out.println("0. Return to main menu");

            choose = scanner.nextInt();
            scanner.nextLine();

            switch (choose) {
                case 1:
                    getAllStaff();
                    break;
                case 2:
                    System.out.println("Please new staff's name");
                    String name = scanner.nextLine();

                    AddNewStaff(name);
                    break;
                case 0:
                    return;
            }
        }
    }
    //Selection 5
    //First input who(member) want to make a appointment
    //Then choose a coaches
    //Then choose available time of that coach
    public void getCoachWithCID(int CID){
        System.out.println("Available Time For this Coach:");

        String SQL = "SELECT coachschedules.schedulesid, coachschedules.CID, coach.Name, coachschedules.availabletime "
                + "FROM coachschedules "
                + "JOIN coach ON coachschedules.CID = coach.CID "
                + "WHERE coachschedules.CID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, CID);

            ResultSet rs = pstmt.executeQuery();
            //use to check is this coach have available time
            if (!rs.isBeforeFirst()) {
                System.out.println("No available times found for Coach ID: " + CID);
                return;
            }

            while (rs.next()) {
                int schedulesid = rs.getInt("schedulesid");
                int outputCID = rs.getInt("CID");
                String name = rs.getString("Name");
                Date availabletime = rs.getDate("availabletime");

                // printing student info
                System.out.println(schedulesid + ": #" + outputCID + " " + name + " Avaliable time: " + availabletime);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //Check if coach is available in that date
    public boolean checkIfAvailable(int CID, Date date){
        String SQL = "SELECT COUNT(*) FROM coachschedules WHERE CID = ? AND availabletime = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, CID);
            pstmt.setDate(2, date);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;  // return true if count > 0
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }
    public void addNewPersonalTrain(int MID, int CID, Timestamp timestamp){
        String SQL = "INSERT INTO personaltrain (MID,CID,traintime) VALUES (?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, MID); //member's name
            pstmt.setInt(2, CID); //CID
            pstmt.setTimestamp(3, timestamp); //date

            int affectedRows = pstmt.executeUpdate();

            // Check if success
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long newMemberId = rs.getLong(1);
                        System.out.println("New personal train added with ID: " + newMemberId);
                    }
                }
            } else {
                System.out.println("personal train failed to add.");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //main function for 5
    public void BookPersonalTrain(){
        int memberId,coachID;
        String dateString;
        Scanner scanner = new Scanner(System.in);

        getAllMember();

        System.out.println("Please enter which member want's to book a personal train? (in MID-memberID)");
        memberId = scanner.nextInt();
        scanner.nextLine();

        //print all coach let member to choose
        getAllCoaches();

        System.out.println("Which coach that member want to make a appointment? (in CID)");
        coachID = scanner.nextInt();
        scanner.nextLine();

        getCoachWithCID(coachID);
        System.out.println("Which DATE you want to make a appointment? (yyyy-[m]m-[d]d)");
        dateString = scanner.nextLine();
        Date date;
        try {
            date = Date.valueOf(dateString);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please enter the date in format yyyy-[m]m-[d]d.");
            return;
        }

        if(checkIfAvailable(coachID, date)){
            int hour;

            System.out.println("Which time do you want? (in hour)");
            hour = scanner.nextInt();

            scanner.nextLine();
            LocalDate ld = date.toLocalDate();
            LocalTime lt = LocalTime.of(hour,0,0);
            LocalDateTime localDateTime = LocalDateTime.of(ld,lt);

            //change it into Timestamp
            Timestamp timestamp = Timestamp.valueOf(localDateTime);

            addNewPersonalTrain(memberId, coachID, timestamp);
            //if Book Personal Train success get 100 LoyaltyPoint
            increaseLoyaltyPoint(memberId,100);
        }else{
            System.out.println("Error! this coach is not available in that time!");
        }
    }
    //Selection 6
    //Create a Group Class
    public void createNewGroupClass(String className,int CID, String place, Date date){
        String SQL = "INSERT INTO GroupClass (ClassName,CID, Place, StartDate) VALUES (?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, className); //class name
            pstmt.setInt(2, CID); //Coach ID
            pstmt.setString(3, place); //place
            pstmt.setDate(4, date); //class start date

            int affectedRows = pstmt.executeUpdate();

            // Check if success
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long newMemberId = rs.getLong(1);
                        System.out.println("New Group class added with ID: " + newMemberId);
                    }
                }
            } else {
                System.out.println("Group class failed to add.");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //this is main function for Selection 6
    public void AddNewGroupClass(){
        Scanner scanner = new Scanner(System.in);
        int coachID;
        String dateString,place,className;

        System.out.println("Adding new group class..");
        //print all coach let member to choose
        getAllCoaches();
        System.out.println("Which coach is teaching? (Cid)");
        coachID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Name of the class?");
        className = scanner.nextLine();

        System.out.println("Which place?");
        place = scanner.nextLine();

        getCoachWithCID(coachID);
        System.out.println("Which DATE you want to do a group class? (yyyy-[m]m-[d]d)");
        dateString = scanner.nextLine();
        Date date;
        try {
            date = Date.valueOf(dateString);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please enter the date in format yyyy-[m]m-[d]d.");
            return;
        }

        if(checkIfAvailable(coachID, date)){
            System.out.println("Creating new class...");
            createNewGroupClass(className ,coachID, place, date);
        }else{
            System.out.println("Error! this coach is not available in that time!");
        }
    }
    //Selection 7
    //Member can join a Group that already exist.
    public void getAllGroupClass(){
        System.out.println("Group Class list:");

        String SQL = "SELECT * FROM groupclass";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int classid = rs.getInt("classid");
                String className = rs.getString("classname");
                String place = rs.getString("place");
                Date startDate = rs.getDate("startdate");

                // printing student info
                System.out.println("#" + classid + " " + className);
                System.out.println("Where it is? :" + place);
                System.out.println("When does it start? :" + startDate);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void addToJoinList(int MID, int classID){
        String SQL = "INSERT INTO MemberJoinGroupClass (MID, ClassID) VALUES (?, ?);";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, MID); //class name
            pstmt.setInt(2, classID); //Coach ID

            int affectedRows = pstmt.executeUpdate();

            // Check if success
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long newMemberId = rs.getLong(1);
                        System.out.println("New Group class member added with ID: " + newMemberId);
                    }
                }
            } else {
                System.out.println("Group class member failed to add.");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //there is main function for Selection 7
    public void joinGroupClass(){
        Scanner scanner = new Scanner(System.in);
        int memberID, classID;

        System.out.println("Who are you? (enter your MID)");
        memberID = scanner.nextInt();
        scanner.nextLine();

        getAllGroupClass();

        System.out.println("Which class you want to join? (enter classID)");
        classID = scanner.nextInt();
        scanner.nextLine();

        addToJoinList(memberID, classID);
        //if join GroupClass success get 50 LoyaltyPoint
        increaseLoyaltyPoint(memberID,50);
    }
    //Selection 8
    public void showAllMaintainHistory(){
        System.out.println("Maintain History:");

        String SQL = "SELECT equipmentmaintain.mid, equipment.name AS equipmentName, equipmentmaintain.sid, staff.name AS staffName, equipmentmaintain.maintaindate "
                + "FROM equipmentmaintain "
                + "JOIN equipment ON equipmentmaintain.EID = equipment.EID "
                + "JOIN staff ON equipmentmaintain.SID = staff.SID";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int MID = rs.getInt("mid");
                String equipmentName = rs.getString("equipmentName");
                int SID = rs.getInt("sid");
                String staffName = rs.getString("staffName");
                Date maintainDate = rs.getDate("maintaindate");

                // printing maintain history
                System.out.println("Maintain ID: " + MID);
                System.out.println("Equipment: " + equipmentName);
                System.out.println("Staff ID: " + SID);
                System.out.println("Staff Name: " + staffName);
                System.out.println("Maintain Date: " + maintainDate);
                System.out.println("---------------------------");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void showAllEquipment(){
        System.out.println("Equipment list");

        String SQL = "SELECT * FROM equipment";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int EID = rs.getInt("eid");
                String name = rs.getString("name");
                boolean available = rs.getBoolean("available");

                // printing student info
                System.out.println("#" + EID + " " + name + " Can be rent: " + available);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //let staff maintain a equipment
    public void staffMaintainEquipment(){
        Scanner scanner = new Scanner(System.in);
        int staffID, equipmentID;

        Date maintainDate;

        getAllStaff();
        System.out.println("Which staff are going to do the maintain? (SID)");
        staffID = scanner.nextInt();
        scanner.nextLine();

        showAllEquipment();
        System.out.println("Which equipment are going to maintain? (EID)");
        equipmentID = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Input the time that the staff maintain the equipment");
        String input = scanner.nextLine();
        //try catch the wrong format
        try {
            // change input to Date
            maintainDate = Date.valueOf(input);
            System.out.println("Maintain date is: " + maintainDate);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid format. Please enter the date and time in the correct format.");
        }

        String SQL = "INSERT INTO EquipmentMaintain (SID, EID, MaintainDate) VALUES (?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            maintainDate = Date.valueOf(input);

            pstmt.setInt(1, staffID);
            pstmt.setInt(2, equipmentID);
            pstmt.setDate(3, maintainDate); //date

            int affectedRows = pstmt.executeUpdate();

            // Check if success
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long newMemberId = rs.getLong(1);
                        System.out.println("Maintain data added with ID: " + newMemberId);
                    }
                }
            } else {
                System.out.println("Maintain data failed to add.");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //Main menu for Equipment Maintain manage
    public void EquipmentMaintain(){
        Scanner scanner = new Scanner(System.in);
        int choose = -1;

        while (choose != 0) {
            System.out.println("--------------EQUIPMENT MAINTAIN MANAGE------------");
            System.out.println("1. Show all Equipment Maintain history");
            System.out.println("2. Equipment Maintain");
            System.out.println("0. Return to main menu");

            choose = scanner.nextInt();
            scanner.nextLine();

            switch (choose) {
                case 1:
                    showAllMaintainHistory();
                    break;
                case 2:
                    staffMaintainEquipment();
                    break;
                case 0:
                    return;
            }
        }
    }
    //Selection 9
    //Show Borrow history
    public void showBorrowHistory(){
        System.out.println("Show Borrow History");

        String SQL = "SELECT equipmentrental.rentalid, equipmentrental.eid, equipmentrental.mid, member.name, equipmentrental.rentaltime "
                + "FROM equipmentrental "
                + "JOIN member ON equipmentrental.mid = member.mid";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int rentalID = rs.getInt("rentalid");
                int eid = rs.getInt("eid");
                int mid = rs.getInt("mid");
                String name = rs.getString("name");
                Timestamp rentalTime = rs.getTimestamp("rentaltime");

                // printing student info
                System.out.println("#" + rentalID);
                System.out.println("Equipment ID: " + eid);
                System.out.println("Who borrow it? #" + mid + " " + name);
                System.out.println("Time: " + rentalTime);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //Selection 10
    //let user borrow an equipment
    public void setEquipmentUnavailable(int equipmentID){
        String SQL = "UPDATE equipment SET Available = false WHERE EID = ?;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, equipmentID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Equipment ID " + equipmentID + " set to unavailable.");
            } else {
                System.out.println("No equipment was updated. Check if the equipment ID exists?");
            }

        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }
    }
    public void setEquipmentAvailable(int equipmentID){
        String SQL = "UPDATE equipment SET Available = true WHERE EID = ?;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, equipmentID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Equipment ID " + equipmentID + " is now available.");
            } else {
                System.out.println("No equipment was updated. Check if the equipment ID exists?");
            }

        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }
    }
    //print all available equipment
    public void showAllAvailableEquipment(){
        System.out.println("Available items:");

        String SQL = "SELECT * FROM equipment WHERE Available = true";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                int EID = rs.getInt("EID");
                String name = rs.getString("Name");

                System.out.println("Equipment ID: " + EID + ", Name: " + name);
                found = true;
            }

            if (!found) {
                System.out.println("No available equipment found.");
            }

        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }
    }
    public void borrowEquipment(){
        Scanner scanner = new Scanner(System.in);
        int memberID,EID;
        String borrowTimeString;
        Timestamp borrowTime;

        getAllMember();
        System.out.println("Who is borrowing equipment?: (enter member ID)");
        memberID = scanner.nextInt();
        scanner.nextLine();

        showAllAvailableEquipment();
        System.out.println("Which equipment?: (enter EID)");
        EID = scanner.nextInt();
        scanner.nextLine();

        System.out.println("When did you borrow it?: (enter EID)");
        System.out.println("In format : (yyyy-MM-dd HH:mm:ss (e.g., 2023-12-01 14:30:00))");
        borrowTimeString = scanner.nextLine();

        try {
            borrowTime = Timestamp.valueOf(borrowTimeString);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid format. Please enter the date and time in the correct format.");
            return;//return if wrong format
        }

        String SQL = "INSERT INTO equipmentrental (EID, MID, rentaltime) VALUES (?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, EID);
            pstmt.setInt(2, memberID);
            pstmt.setTimestamp(3, borrowTime);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Equipment borrow record added successfully.");
            } else {
                System.out.println("Failed to add equipment maintain record.");
            }

        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }

        //then set equipment to unavailable
        setEquipmentUnavailable(EID);
    }
    //Selection 11
    //return an equipment
    public int getEquipmentIDFromRentalID(int rentalID) {
        String SQL = "SELECT EID FROM EquipmentRental WHERE RentalID = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, rentalID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("EID");
                }
            }

        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }
        return -1; // return -1 if can't find any
    }
    public void returnEquipment(){
        Scanner scanner = new Scanner(System.in);
        int rentalID;

        showBorrowHistory();
        System.out.println("Which equiment you would like to return? (Enter rentalid)");
        rentalID = scanner.nextInt();

        int EID = getEquipmentIDFromRentalID(rentalID);
        if (EID != -1) {
            setEquipmentAvailable(EID);
        } else {
            System.out.println("No equipment found with the given rental ID.");
        }
    }
    //Selection 12
    //check all equipmentID

    //Increase loyalty point
    public void increaseLoyaltyPoint(int MID,int point){
        // get Loyalty Point before change
        String getPointsSQL = "SELECT LoyaltyPoint FROM Member WHERE MID = ?";

        // update Loyalty Point
        String updatePointsSQL = "UPDATE Member SET LoyaltyPoint = ? WHERE MID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement getPointsStmt = conn.prepareStatement(getPointsSQL);
             PreparedStatement updatePointsStmt = conn.prepareStatement(updatePointsSQL)) {

            getPointsStmt.setInt(1, MID);
            ResultSet rs = getPointsStmt.executeQuery();
            //if found MID
            if (rs.next()) {
                int currentPoints = rs.getInt("LoyaltyPoint");
                int newPoints = currentPoints + point;

                // update
                updatePointsStmt.setInt(1, newPoints);
                updatePointsStmt.setInt(2, MID);
                int affectedRows = updatePointsStmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Member ID " + MID + " loyalty points increased to " + newPoints);
                } else {
                    System.out.println("No member found with ID: " + MID);
                }
            } else {
                System.out.println("No member found with ID: " + MID);
            }

        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }
    }

    //Main function
    public static void main(String[] args) {
        FitnessClubDBOP fitnessOps = new FitnessClubDBOP();
        Scanner scanner = new Scanner(System.in);

        int choose = -1;
        int userGroup = -1;

        //main menu loop until user choose 0
        System.out.println("Hello!");
        System.out.println("Welcome to Fitness Club manage system");
        System.out.println("Please select your user group: ");
        System.out.println("(1. Member 2. Staff)");
        userGroup = scanner.nextInt();
        scanner.nextLine();

        if(userGroup == 1){
            while (choose != 0) {
                System.out.println("-----------------MEMBER MAIN MENU-----------------");
                System.out.println("1. Book a Personal Train Session");
                System.out.println("2. Join a Group Class");
                System.out.println("3. Borrow equipment");
                System.out.println("4. Return equipment");
                System.out.println("0. Exit");

                choose = scanner.nextInt();
                scanner.nextLine();

                switch (choose) {
                    case 1:
                        fitnessOps.BookPersonalTrain(); //get all users include member coach and staff
                        break;

                    case 2:
                        fitnessOps.joinGroupClass();
                        break;

                    case 3:
                        fitnessOps.borrowEquipment();
                        break;

                    case 4:
                        fitnessOps.returnEquipment();
                        break;

                    case 0:
                        return;
                }
            }
        }else if(userGroup == 2){
            while (choose != 0) {
                System.out.println("-----------------STAFF MAIN MENU-----------------");
                System.out.println("1. Show all users");
                System.out.println("2. Manage members");
                System.out.println("3. Manage coaches");
                System.out.println("4. Manage Staff");
                System.out.println("5. Create a Group Class");
                System.out.println("6. Equipment Maintain manage");
                System.out.println("7. Show Borrow history");
                System.out.println("8.Equipment status");
                System.out.println("0. Exit");

                choose = scanner.nextInt();
                scanner.nextLine();

                switch (choose) {
                    case 1:
                        fitnessOps.getAllUsers(); //get all users include member coach and staff
                        break;
                    case 2:
                        fitnessOps.manageMember();
                        break;
                    case 3:
                        fitnessOps.manageCoach();
                        break;
                    case 4:
                        fitnessOps.manageStaff();
                        break;

                    case 5:
                        fitnessOps.AddNewGroupClass();
                        break;

                    case 6:
                        fitnessOps.EquipmentMaintain();
                        break;
                    case 7:
                        fitnessOps.showBorrowHistory();
                        break;

                    case 8:
                        fitnessOps.showAllEquipment();
                        break;
                    case 0:
                        return;
                }
            }
        }
    }
}
