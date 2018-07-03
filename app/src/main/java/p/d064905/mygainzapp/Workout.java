package p.d064905.mygainzapp;

public class Workout {

    String name;
    long id;
    String planKey;

    public Workout (String name, long id, String planKey){
        this.name = name;
        this.id = id;
        this.planKey = planKey;
    }

    @Override
    public String toString() {
        return name;
    }
}
