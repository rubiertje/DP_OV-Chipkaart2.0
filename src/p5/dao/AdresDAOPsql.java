package p5.dao;

import p5.domein.Adres;
import p5.domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdresDAOPsql implements AdresDAO {

    private Connection connection;
    private ReizigerDAO rdoa;

    public AdresDAOPsql(Connection connection) {
        this.connection = connection;
    }

    public void setRdoa(ReizigerDAO rdoa) {
        this.rdoa = rdoa;
    }


    @Override
    public boolean save(Adres adres) throws SQLException{
//        System.out.println("SAVE FUNTIE");
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
        catch (Exception e){e.getStackTrace();}
        return false;
    }

    @Override
    public boolean update(Adres adres) throws SQLException{
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
        catch (Exception e){e.getStackTrace();}
        return false;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException{
//        System.out.println("DELETE FUNCTIE");
        try {
            String s = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, adres.getId());
            ps.executeQuery();
            ps.close();
            return true;
        } catch (SQLException ignored) {}
        catch (Exception e){e.getStackTrace();}
        return false;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException{
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
    catch (Exception e){e.getStackTrace();}
    return null;
    }

    @Override
    public ArrayList<Adres> findAll() throws SQLException{
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
                adressen.add(new Adres(id, postcode, huisnummer, straat, woonplaats, rdoa.findById(reiziger_id)));
            }
            ps.close();
            resultSet.close();
            return adressen;
        } catch (SQLException ignored){}
        catch (Exception e){e.getStackTrace();}
        return null;
    }


}
