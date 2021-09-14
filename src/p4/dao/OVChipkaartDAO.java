package p4.dao;

import p4.domein.OVChipkaart;
import p4.domein.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OVChipkaartDAO {

    public boolean save(OVChipkaart ovChipkaart) throws SQLException;

    public boolean update(OVChipkaart ovChipkaart) throws SQLException;

    public boolean delete(OVChipkaart ovChipkaart) throws SQLException;

    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;

    public OVChipkaart findByKaartNummer(int kaartNummer) throws SQLException;

    public ArrayList<OVChipkaart> findAll() throws SQLException;
}
