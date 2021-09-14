package p3;

import p3.dao.AdresDAO;
import p3.dao.AdresDAOPsql;
import p3.dao.ReizigerDAO;
import p3.dao.ReizigerDAOPsql;
import p3.domein.Adres;
import p3.domein.Reiziger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        connection = getConnection();

        ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(connection);
        AdresDAOPsql adresDAOPsql = new AdresDAOPsql(connection);
        reizigerDAOPsql.setAdoa(adresDAOPsql);
        adresDAOPsql.setRdoa(reizigerDAOPsql);
//        System.out.println(adresDAOPsql.findAll());
//        testReizigerDAO(reizigerDAOPsql);
//        testAdresDAO(adresDAOPsql);
//        System.out.println(reizigerDAOPsql.findById(1));
//        System.out.println(reizigerDAOPsql.findAll());




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
        System.out.println("[Test] Aantal reizigers voor de Delete functie: " + reizigers.size());

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

    public static void testAdresDAO(AdresDAO adoa) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        List<Adres> adressen = adoa.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende Adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        Reiziger r1 = new Reiziger(99, "T.I", "van", "Rooijen", LocalDate.of(2003, 4, 23));
        Adres adres5 = new Adres(100, "1111 AA", "1", "heidelberglaan", "Spakenburg", r1);
        System.out.print("[Test] Eerst " + adressen.size() + " Adressen, na AdresDAO.save() ");
        adoa.save(adres5);
        adressen = adoa.findAll();
        System.out.println(adressen.size() + " adressen");


        System.out.println();
        System.out.println("[Test] UPDATE FUNCTIE");
        System.out.println("Het adres voor de update: \n\t" + adres5.getId() + "\n\t" + adres5.getPostcode() + "\n\t" + adres5.getHuisnummer() + "\n\t" + adres5.getStraat() + "\n\t" + adres5.getWoonplaats() + "\n\t" + adres5.getReiziger());
        adres5.setPostcode("2222 BB");
        adres5.setHuisnummer("2");
        adres5.setStraat("Padualaan");
        adres5.setWoonplaats("Utrecht");
        adoa.update(adres5);
        System.out.println("Het adres na de update: \n\t" + adres5.getId() + "\n\t" + adres5.getPostcode() + "\n\t" + adres5.getHuisnummer() + "\n\t" + adres5.getStraat() + "\n\t" + adres5.getWoonplaats() + "\n\t" + adres5.getReiziger());



        System.out.println();
        System.out.println("[Test] Aantal adressen voor de Delete functie: " + adressen.size());

        adoa.delete(adres5);
        adressen = adoa.findAll();
        System.out.println("Aantal adressen na de Delete functie: " + adressen.size());

    }

    
}