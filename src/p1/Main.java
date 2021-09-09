package p1;

import java.sql.*;
import java.util.Properties;

public class Main {

    public static final String URL = "jdbc:mysql://localhost:5432/voorbereiding";
    public static final String USER = "postgres";
    public static final String PASS = "ruben";

    public static void main(String[] args) throws SQLException {
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql:DataPersistency", "postgres", "ruben");
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM reiziger");
            System.out.println("Alle reizigers:");
            while (resultSet.next()){
                if (resultSet.getString("tussenvoegsel") == null){
                    System.out.println("\t #" + resultSet.getString("reiziger_id") + ": " + resultSet.getString("voorletters") + " " + resultSet.getString("achternaam") + "  (" + resultSet.getString("geboortedatum") + ")");
                }else{
                    System.out.println("\t #" + resultSet.getString("reiziger_id") + ": " + resultSet.getString("voorletters") + " " + resultSet.getString("tussenvoegsel") + " " + resultSet.getString("achternaam") + "  (" + resultSet.getString("geboortedatum") + ")");
                }
            }

            conn.close();
            stmt.close();
            resultSet.close();
        }catch (Exception e){
            System.out.println(e);
        }


    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException throwables) {
            System.out.println("iets fout");
        }
        return null;
    }

    
}