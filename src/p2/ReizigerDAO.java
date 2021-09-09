package p2;

import p2.Reiziger;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ReizigerDAO {

    public boolean save(Reiziger reiziger);

    public boolean update (Reiziger reiziger);

    public boolean delete (Reiziger reiziger);

    public ArrayList<Reiziger> findAll();
}
