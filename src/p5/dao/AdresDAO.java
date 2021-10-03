package p5.dao;

import p5.domein.Adres;
import p5.domein.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdresDAO {

    public boolean save(Adres adres) throws SQLException;

    public boolean update(Adres adres) throws SQLException;

    public boolean delete(Adres adres) throws SQLException;

    public Adres findByReiziger(Reiziger reiziger) throws SQLException;

    public ArrayList<Adres> findAll() throws SQLException;

}
