package p5.domein;

import java.util.ArrayList;

public class Product {
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private ArrayList<Integer> chipkaartsnummers;

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.chipkaartsnummers = new ArrayList<>();
    }

    public int getNummer() {
        return productNummer;
    }

    public void setNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public ArrayList<Integer> getChipkaartsnummers() {
        return chipkaartsnummers;
    }

    public boolean addChipkaartNummer(Integer chipkaartnummer) {
        this.chipkaartsnummers.add(chipkaartnummer);
        return this.chipkaartsnummers.contains(chipkaartnummer);
    }

    public boolean removeChipkaartNummer(Integer chipkaartnummer){
        this.chipkaartsnummers.remove(chipkaartnummer);
        return !this.chipkaartsnummers.contains(chipkaartnummer);
    }

    public String toString() {
        return "productnummer: " + productNummer + " naam: " + naam + " beschrijving: " + beschrijving + " prijs: " + prijs;
    }
}
