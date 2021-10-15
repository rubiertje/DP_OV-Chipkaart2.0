package p5.dao;

import p5.domein.Adres;
import p5.domein.OVChipkaart;
import p5.domein.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class ProductDOAPsql implements ProductDAO{
    private Connection connection;
    private OVChipkaartDAO ovdao;

    public ProductDOAPsql(Connection connection) {
        this.connection = connection;
    }

    public void setOvdao(OVChipkaartDAO ovdao) {
        this.ovdao = ovdao;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        try {
            String s = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, product.getNummer());
            ps.setString(2, product.getNaam());
            ps.setString(3, product.getBeschrijving());
            ps.setDouble(4, product.getPrijs());
            ps.executeQuery();
            ps.close();

            ArrayList<Integer> ovcardnummers = product.getChipkaartsnummers();

            String s2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, ?)";
            for (Integer kaartnummer : ovcardnummers){
                PreparedStatement ps2 = connection.prepareStatement(s2);
                ps2.setInt(1, kaartnummer);
                ps2.setInt(2, product.getNummer());
                ps2.setString(3, "actief");
                ps2.setDate(4, new Date(System.currentTimeMillis()));
                ps2.executeQuery();
                ps2.close();
            }
            return true;
        }catch (SQLException ignored){}
        catch (Exception e){e.getStackTrace();}
        return false;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        try {
            String s = "UPDATE product (naam, beschrijving, prijs) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setString(1, product.getNaam());
            ps.setString(2, product.getBeschrijving());
            ps.setDouble(3, product.getPrijs());
            ps.executeQuery();
            ps.close();

            String s2 = "DELETE FROM ov_chipkaart_product WHERE  product_nummer = ?";
            PreparedStatement ps2 = connection.prepareStatement(s2);
            ps2.setInt(1, product.getNummer());
            ps2.executeQuery();
            ps2.close();

            ArrayList<Integer> ovcardnummers = product.getChipkaartsnummers();

            String s3 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, ?)";
            for (Integer kaartnummer : ovcardnummers){
                PreparedStatement ps3 = connection.prepareStatement(s3);
                ps3.setInt(1, kaartnummer);
                ps3.setInt(2, product.getNummer());
                ps3.setString(3, "actief");
                ps3.setDate(4, new Date(System.currentTimeMillis()));
                ps3.executeQuery();
                ps3.close();
            }

            return true;
        }catch (SQLException ignored){}
        catch (Exception e){e.getStackTrace();}
        return false;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        try {
            String s = "DELETE FROM product WHERE product_nummer = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, product.getNummer());
            ps.executeQuery();
            ps.close();

            String s2 = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
            PreparedStatement ps2 = connection.prepareStatement(s2);
            ps2.setInt(1, product.getNummer());
            ps2.executeQuery();
            ps2.close();

            return true;
        }catch (SQLException ignored){}
        catch (Exception e){e.getStackTrace();}
        return false;
    }

    @Override
    public ArrayList<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        try {
            String s2 = "select * from product " +
                    "join ov_chipkaart_product ocp on product.product_nummer = ocp.product_nummer " +
                    "join ov_chipkaart oc on oc.kaart_nummer = ocp.kaart_nummer " +
                    "where ocp.kaart_nummer = ?";
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

            String s3 = "SELECT ov_chipkaart.kaart_nummer FROM ov_chipkaart " +
                    "JOIN ov_chipkaart_product ON ov_chipkaart.kaart_nummer = ov_chipkaart_product.kaart_nummer " +
                    "where ov_chipkaart_product.product_nummer = ?";

            for (Product product : products){
                PreparedStatement ps3 = connection.prepareStatement(s3);
                ps3.setInt(1, product.getNummer());
                ResultSet rs3 = ps3.executeQuery();
                if (!product.addChipkaartNummer(rs2.getInt("kaart_nummer"))){
                    throw new SQLException();
                };
                ps3.close();
                rs3.close();
            }

            return products;
        }catch (SQLException ignored){}
        catch (Exception e){e.getStackTrace();}
        return null;
    }

    @Override
    public ArrayList<Product> findAll() throws SQLException {
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

            String s3 = "SELECT ov_chipkaart.kaart_nummer FROM ov_chipkaart " +
                    "JOIN ov_chipkaart_product ON ov_chipkaart.kaart_nummer = ov_chipkaart_product.kaart_nummer " +
                    "where ov_chipkaart_product.product_nummer = ?";

            for (Product product : products){
                PreparedStatement ps3 = connection.prepareStatement(s3);
                ps3.setInt(1, product.getNummer());
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()){
                    int kaartnummer = rs3.getInt(1);
                    if (!product.addChipkaartNummer(kaartnummer)){
                        throw new SQLException();
                    };
                }
                ps3.close();
                rs3.close();
            }

            return products;
        }catch (SQLException e){
            e.getStackTrace();
        }
        catch (Exception e){e.getStackTrace();}
        return null;
    }
}
