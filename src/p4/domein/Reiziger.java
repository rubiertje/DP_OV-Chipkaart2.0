package p4.domein;

import java.time.LocalDate;
import java.util.ArrayList;

public class Reiziger {

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedatum;
    private Adres adres;
    private ArrayList<OVChipkaart> chipkaarts;

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.chipkaarts = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public ArrayList<OVChipkaart> getChipkaarts() {
        return chipkaarts;
    }

    public void addChipkaart(OVChipkaart ovChipkaart) {
        boolean toevoegen = true;
        for (OVChipkaart ovChipkaart1 : chipkaarts){
            if (ovChipkaart1.getKaartNummer() == ovChipkaart.getKaartNummer()){
                toevoegen = false;
                break;
            }
        }
        if (toevoegen){
//            if (ovChipkaart.getReiziger() == this){
                this.chipkaarts.add(ovChipkaart);
//            }else{
//                System.out.println("TOEVOEGEN MISLUKT! IEMAND ANDERS BEZIT DEZE KAART");
//            }
//        }else{
//            System.out.println("TOEVOEGEN MISLUKT! REIZIGER HEEFT DEZE CHIPKAART AL");
        }
    }

    public int getAantalOV(){
        return this.chipkaarts.size();
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("#" + id + ": " + voorletters + " ");
        if (tussenvoegsel != null){
            stringBuilder.append(tussenvoegsel).append(" ");
        }
        stringBuilder.append(achternaam).append(" (").append(geboortedatum).append("). ");
        if (adres != null){
            stringBuilder.append(", Adres: ").append(adres);
        }
        if (chipkaarts.size() != 0){
            stringBuilder.append(" Chipkaart(en): ");
            for (OVChipkaart ovChipkaart : chipkaarts){
                stringBuilder.append("#").append(ovChipkaart.getKaartNummer()).append(", saldo: ").append(ovChipkaart.getSaldo()).append(", klasse: ").append(ovChipkaart.getKlasse()).append(". ");
            }
        }

        return stringBuilder.toString();
    }
}
