package p.d064905.mygainzapp;

public class Plan {

        Boolean PlanActive;
        String PlanName;
        Long ID;

        public Plan (String aPlanName,Boolean aPlanActive,Long id){

            ID=id;
            PlanName= aPlanName;
            PlanActive= aPlanActive;
    }
    public String toString(){
        return PlanName;
    }
}


