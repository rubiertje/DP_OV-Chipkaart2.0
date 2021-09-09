package p2;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection connection;

    public ReizigerDAOPsql(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger) {
//        System.out.println("SAVE FUNTIE");
        try {
            String s = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, Date.valueOf(reiziger.getGeboortedatum()));
            ps.executeQuery();
            ps.close();
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
//        System.out.println("UPDATE FUNCTIE");
        try{
            String s = "UPDATE reiziger SET voorletters = ? , tussenvoegsel = ? , achternaam = ? , geboortedatum = ? WHERE reiziger_id = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, Date.valueOf(reiziger.getGeboortedatum()));
            ps.setInt(5, reiziger.getId());
            ps.executeQuery();
            ps.close();
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
//        System.out.println("DELETE FUNCTIE");
        try {
            String s = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, reiziger.getId());
            ps.executeQuery();
            ps.close();
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public ArrayList<Reiziger> findAll() {
        try {
            String s = "SELECT * FROM reiziger";
            PreparedStatement ps = connection.prepareStatement(s);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Reiziger> reizigers = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt("reiziger_id");
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                LocalDate geboortedatum = resultSet.getDate("geboortedatum").toLocalDate();
                reizigers.add(new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum));
            }
            ps.close();
            resultSet.close();
            return reizigers;
        } catch (SQLException ignored) {}
        return null;
    }


}
