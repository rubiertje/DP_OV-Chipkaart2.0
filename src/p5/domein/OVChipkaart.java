package p5.domein;

import java.time.LocalDate;
import java.util.ArrayList;

public class OVChipkaart {
    private int kaartNummer;
    private LocalDate geldigTot;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;
    private ArrayList<Product> producten;

    public OVChipkaart(int kaartNummer, LocalDate geldigTot, int klasse, double saldo, Reiziger reiziger) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        this.producten = new ArrayList<>();
        reiziger.addChipkaart(this);
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public LocalDate getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(LocalDate geldigTot) {
        this.geldigTot = geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public ArrayList<Product> getProducten() {
        return producten;
    }

    public void addProduct(Product product) {
        this.producten.add(product);
    }

    public void removeProduct(Product product){
        this.producten.remove(product);
    }

    public String toString() {
        return reiziger.toString() + producten;
    }
}
