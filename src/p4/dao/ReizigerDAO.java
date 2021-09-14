package p4.dao;

import p4.domein.Reiziger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ReizigerDAO {

    boolean save(Reiziger reiziger) throws SQLException;

    boolean update(Reiziger reiziger) throws SQLException;

    boolean delete(Reiziger reiziger) throws SQLException;

    Reiziger findById(int id) throws SQLException;

    ArrayList<Reiziger> findByGbDatum(LocalDate geboortedatum) throws SQLException;

    ArrayList<Reiziger> findAll() throws SQLException;
}
