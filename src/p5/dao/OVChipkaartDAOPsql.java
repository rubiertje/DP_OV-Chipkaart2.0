package p5.dao;

import p5.domein.OVChipkaart;
import p5.domein.Product;
import p5.domein.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    private Connection connection;
    private ReizigerDAO rdoa;
    private ProductDAO pdao;

    public OVChipkaartDAOPsql(Connection connection) {
        this.connection = connection;
    }

    public void setRdoa(ReizigerDAO rdoa) {
        this.rdoa = rdoa;
    }

    public void setPdao(ProductDAO pdao) {
        this.pdao = pdao;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
//        System.out.println("SAVE FUNTIE");
        try {
            String s = "INSERT INTO ov_chipkaart VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, ovChipkaart.getKaartNummer());
            ps.setDate(2, Date.valueOf(ovChipkaart.getGeldigTot()));
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger().getId());
            ps.executeQuery();
            ps.close();
            return true;
        }catch (SQLException ignored) {}
        catch (Exception e){e.getStackTrace();}
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
//        System.out.println("UPDATE FUNCTIE");
        try {
            String s = "UPDATE ov_chipkaart SET geldig_tot = ? , klasse = ? , saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setDate(1, Date.valueOf(ovChipkaart.getGeldigTot()));
            ps.setInt(2, ovChipkaart.getKlasse());
            ps.setDouble(3, ovChipkaart.getSaldo());
            ps.setInt(4, ovChipkaart.getReiziger().getId());
            ps.setInt(5, ovChipkaart.getKaartNummer());
            ps.executeQuery();
            ps.close();
            return true;
        }catch (SQLException ignored){}
        catch (Exception e){e.getStackTrace();}
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
//        System.out.println("DELETE FUNCTIE");
        try {
            String s = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, ovChipkaart.getKaartNummer());
            ps.executeQuery();
            ps.close();
            return true;
        }catch (SQLException ignored){}
        catch (Exception e){e.getStackTrace();}
        return false;
    }

    @Override
    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        try {
            String s2 = "SELECT * FROM product";
            PreparedStatement ps2 = connection.prepareStatement(s2);
            ResultSet rs2 = ps2.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs2.next()){
                int nummer = rs2.getInt("product_nummer");
                String naam = rs2.getString("naam");
                String beschrijving = rs2.getString("beschrijving");
                double prijs = rs2.getDouble("prijs");
                products.add(new Product(nummer, naam, beschrijving, prijs));
            }
            ps2.close();
            rs2.close();


            String s = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, reiziger.getId());
            ResultSet rs = ps.executeQuery();

            ArrayList<OVChipkaart> chipkaarts = new ArrayList<>();
            while(rs.next()){
                int kaartNummer = rs.getInt("kaart_nummer");
                LocalDate geldigTot = rs.getDate("geldig_tot").toLocalDate();
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                OVChipkaart ovChipkaart = new OVChipkaart(kaartNummer, geldigTot, klasse, saldo, reiziger);

                String s3 = "SELECT product.product_nummer FROM product " +
                        "JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                        "where ov_chipkaart_product.kaart_nummer = ?";
                PreparedStatement ps3 = connection.prepareStatement(s3);
                ps3.setInt(1, kaartNummer);
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()){
                    int productNummer = rs3.getInt("product_nummer");
                    for (Product product : products){
                        if (product.getNummer() == productNummer){
                            ovChipkaart.addProduct(product);
                            product.addChipkaartNummer(ovChipkaart.getKaartNummer());
                        }
                    }
                }
                ps3.close();
                rs3.close();

                chipkaarts.add(ovChipkaart);
            }
            ps.close();
            rs.close();
            return chipkaarts;
        }catch (SQLException ignored){}
        catch (Exception e){e.getStackTrace();}
        return null;
    }

    @Override
    public ArrayList<OVChipkaart> findAll() throws SQLException {
        try {
            String s2 = "SELECT * FROM product";
            PreparedStatement ps2 = connection.prepareStatement(s2);
            ResultSet rs2 = ps2.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs2.next()){
                int nummer = rs2.getInt("product_nummer");
                String naam = rs2.getString("naam");
                String beschrijving = rs2.getString("beschrijving");
                double prijs = rs2.getDouble("prijs");
                products.add(new Product(nummer, naam, beschrijving, prijs));
            }
            ps2.close();
            rs2.close();


            String s = "SELECT * FROM ov_chipkaart";
            PreparedStatement ps = connection.prepareStatement(s);
            ResultSet rs = ps.executeQuery();
            ArrayList<OVChipkaart> chipkaarts = new ArrayList<>();
            while (rs.next()){
                int kaartNummer = rs.getInt("kaart_nummer");
                LocalDate geldigTot = rs.getDate("geldig_tot").toLocalDate();
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                int reizigerId = rs.getInt("reiziger_id");
                OVChipkaart ovChipkaart = new OVChipkaart(kaartNummer, geldigTot, klasse, saldo, rdoa.findById(reizigerId));

                String s3 = "SELECT product.product_nummer FROM product " +
                        "JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                        "where ov_chipkaart_product.kaart_nummer = ?";
                PreparedStatement ps3 = connection.prepareStatement(s3);
                ps3.setInt(1, kaartNummer);
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()){
                    int productNummer = rs3.getInt("product_nummer");
                    for (Product product : products){
                        if (product.getNummer() == productNummer){
                            ovChipkaart.addProduct(product);
                            product.addChipkaartNummer(ovChipkaart.getKaartNummer());
                        }
                    }
                }
                ps3.close();
                rs3.close();

                chipkaarts.add(ovChipkaart);
            }
            ps.close();
            rs.close();
            return chipkaarts;
        }catch (SQLException exception){exception.getStackTrace();}
        catch (Exception e){e.getStackTrace();}
        return null;
    }
}
