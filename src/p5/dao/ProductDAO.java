package p5.dao;

import p5.domein.OVChipkaart;
import p5.domein.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;

    boolean update(Product product) throws SQLException;

    boolean delete(Product product) throws SQLException;

    ArrayList<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException;

    ArrayList<Product> findAll() throws SQLException;
}
