package p2;

import p2.dao.ReizigerDAO;
import p2.dao.ReizigerDAOPsql;
import p2.domein.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = getConnection();
        ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(connection);


//        Reiziger reiziger = new Reiziger(99, "R", "van", "Rooijen", LocalDate.of(2003, 4,23));

        try {
            testReizigerDAO(reizigerDAOPsql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        reizigerDAOPsql.save(reiziger);
//        reizigerDAOPsql.delete(reiziger);
//        reiziger.setAchternaam("Looien");
//        reiziger.setTussenvoegsel("te");
//        reiziger.setVoorletters("T.I");
//        reizigerDAOPsql.update(reiziger);
        closeConnection();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql:DataPersistency", "postgres", "ruben");
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", LocalDate.of(1981, 3, 14));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");

        System.out.println();
        System.out.println("[Test] DELETE FUNCTIE");
        System.out.println("Aantal reizigers voor de Delete functie: " + reizigers.size());

        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("Aantal reizigers na de Delete functie: " + reizigers.size());

        System.out.println();

        System.out.println("[Test] UPDATE FUNCTIE");
        System.out.println("De reiziger voor de update: \n\t" + sietske.getId() + "\n\t" + sietske.getVoorletters() + "\n\t" + sietske.getTussenvoegsel() + "\n\t" + sietske.getAchternaam() + "\n\t" + sietske.getGeboortedatum());
        sietske.setVoorletters("R");
        sietske.setTussenvoegsel("van");
        sietske.setAchternaam("Rooijen");
        sietske.setGeboortedatum(LocalDate.of(2003, 4, 23));
        System.out.println("De reiziger na de update: \n\t" + sietske.getId() + "\n\t" + sietske.getVoorletters() + "\n\t" + sietske.getTussenvoegsel() + "\n\t" + sietske.getAchternaam() + "\n\t" + sietske.getGeboortedatum());




    }

    
}