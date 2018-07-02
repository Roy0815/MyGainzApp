package p.d064905.mygainzapp;

public class Uebung {

    public int id;
    public String name;
    public int gewicht;
    public int reps;
    public int saetze;
    public int pausenzeit;
    public int steigerung;

    Uebung(int id, String name, int gewicht, int reps, int saetze, int pausenzeit, int steigerung) {
        this.id = id;
        this.name = name;
        this.gewicht = gewicht;
        this.reps = reps;
        this.saetze = saetze;
        this.pausenzeit = pausenzeit;
        this.steigerung = steigerung;
        System.out.println("Übung " +name + gewicht + reps + saetze +pausenzeit+ " wurde erstellt.");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGewicht() {
        return gewicht;
    }

    public int getReps() {
        return reps;
    }

    public int getSaetze() {
        return saetze;
    }

    public int getPausenzeit() {
        return pausenzeit;
    }

    public int getSteigerung() {
        return steigerung;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setSaetze(int saetze) {
        this.saetze = saetze;
    }

    public void setPausenzeit(int pausenzeit) {
        this.pausenzeit = pausenzeit;
    }

    public void setSteigerung(int steigerung) {
        this.steigerung = steigerung;
    }

}
