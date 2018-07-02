package p.d064905.mygainzapp;

public class Plan {

        Boolean PlanActive;
        String PlanName;

        public Plan (String aPlanName,Boolean aPlanActive){


            PlanName= aPlanName;
            PlanActive= aPlanActive;
    }
    public String toString(){
        return PlanName;
    }
}


