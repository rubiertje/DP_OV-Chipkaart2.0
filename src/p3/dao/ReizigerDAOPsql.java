package p3.dao;

import p3.domein.Adres;
import p3.domein.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReizigerDAOPsql implements ReizigerDAO{

    private Connection connection;
    private AdresDAO adoa;

    public ReizigerDAOPsql(Connection connection){
        this.connection = connection;
    }

    public void setAdoa(AdresDAO adoa) {
        this.adoa = adoa;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException{
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
            Adres adres = reiziger.getAdres();
            if (adres != null){
                adoa.save(adres);
            }
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException{
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
            Adres adres = reiziger.getAdres();
            if (adres != null){
                adoa.update(adres);
            }
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException{
//        System.out.println("DELETE FUNCTIE");
        try {
            String s = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, reiziger.getId());
            ps.executeQuery();
            ps.close();
            Adres adres = reiziger.getAdres();
            if (adres != null){
                adoa.delete(adres);
            }
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        try {
            String s = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            String voorletters = resultSet.getString("voorletters");
            String tussenvoegsel = resultSet.getString("tussenvoegsel");
            String achternaam = resultSet.getString("achternaam");
            LocalDate geboorteDatum = resultSet.getDate("geboortedatum").toLocalDate();
            Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboorteDatum);
            resultSet.close();
            ps.close();
            adoa.findByReiziger(reiziger);
            //In de constructor van adres word de het adres bij de reiziger geset.
            return reiziger;
        }catch (SQLException ignored) {}
        return null;
    }

    @Override
    public ArrayList<Reiziger> findByGbDatum(LocalDate geboortedatum) throws SQLException {
        try {
            String s = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setDate(1, Date.valueOf(geboortedatum));
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Reiziger> reizigers = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt("reiziger_id");
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
                adoa.findByReiziger(reiziger);
                //In de constructor van adres word de het adres bij de reiziger geset.
                reizigers.add(reiziger);
            }
            resultSet.close();
            ps.close();
            return reizigers;
        }catch (SQLException ignored) {}
        return null;
    }

    @Override
    public ArrayList<Reiziger> findAll() throws SQLException{
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
                Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
                adoa.findByReiziger(reiziger);
                //In de constructor van adres word de het adres bij de reiziger geset.
                reizigers.add(reiziger);
            }
            ps.close();
            resultSet.close();
            return reizigers;
        } catch (SQLException ignored) {}
        return null;
    }


}
