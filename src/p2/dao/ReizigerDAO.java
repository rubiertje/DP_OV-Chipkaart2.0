package p2.dao;

import p2.domein.Reiziger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ReizigerDAO{

    public boolean save(Reiziger reiziger) throws SQLException;

    public boolean update (Reiziger reiziger) throws SQLException;

    public boolean delete (Reiziger reiziger) throws SQLException;

    public Reiziger findById(int id) throws SQLException;

    public ArrayList<Reiziger> findByGbDatum(LocalDate geboortedatum) throws SQLException;

    public ArrayList<Reiziger> findAll() throws SQLException;
}
