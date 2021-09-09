package p3;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AdresDAOPsql implements AdresDAO{

    private Connection connection;

    public AdresDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Adres adres){
        System.out.println("SAVE FUNTIE");
        try {
            String s = "INSERT INTO adres VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, adres.getId());
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());
            ps.setInt(6, adres.getReiziger().getId());
            ps.executeQuery();
            ps.close();
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public boolean update(Adres adres){
//        System.out.println("UPDATE FUNCTIE");
        try{
            String s = "UPDATE adres SET postcode = ? , huisnummer = ? , straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setString(1, adres.getPostcode());
            ps.setString(2, adres.getHuisnummer());
            ps.setString(3, adres.getStraat());
            ps.setString(4, adres.getWoonplaats());
            ps.setInt(5, adres.getReiziger().getId());
            ps.setInt(6, adres.getId());
            ps.executeQuery();
            ps.close();
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public boolean delete(Adres adres){
//        System.out.println("DELETE FUNCTIE");
        try {
            String s = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, adres.getId());
            ps.executeQuery();
            ps.close();
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
    try {
        String s = "SELECT * FROM adres WHERE reiziger_id = ?";
        PreparedStatement ps = connection.prepareStatement(s);
        ps.setInt(1, reiziger.getId());
        ResultSet rs = ps.executeQuery();
        rs.next();
        int adres_id = rs.getInt("adres_id");
        String postcode = rs.getString("postcode");
        String huisnummer = rs.getString("huisnummer");
        String straat = rs.getString("straat");
        String woonplaats = rs.getString("woonplaats");
        Adres adres = new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger);
        ps.close();
        return adres;
    } catch (SQLException ignored) {}
    return null;
    }

    @Override
    public ArrayList<Adres> findAll() {
        try {
            String s = "SELECT * FROM adres";
            PreparedStatement ps = connection.prepareStatement(s);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Adres> adressen = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt("adres_id");
                String postcode = resultSet.getString("postcode");
                String huisnummer = resultSet.getString("huisnummer");
                String straat = resultSet.getString("straat");
                String woonplaats = resultSet.getString("woonplaats");
                int reiziger_id = resultSet.getInt("reiziger_id");
                String s2 = "SELECT * FROM reiziger WHERE reiziger_id = ?";
                PreparedStatement ps2 = connection.prepareStatement(s2);
                ps2.setInt(1, reiziger_id);
                ResultSet resultSet2 = ps2.executeQuery();
                resultSet2.next();
                String voorletters = resultSet2.getString("voorletters");
                String tussenvoegsel = resultSet2.getString("tussenvoegsel");
                String achternaam = resultSet2.getString("achternaam");
                LocalDate geboortedatum = resultSet2.getDate("geboortedatum").toLocalDate();
                Reiziger r1 = new Reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum);
                adressen.add(new Adres(id, postcode, huisnummer, straat, woonplaats, r1));
                ps2.close();
                resultSet2.close();
            }
            ps.close();
            resultSet.close();
            return adressen;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }


}
