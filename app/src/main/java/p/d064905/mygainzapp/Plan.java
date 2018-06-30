package p.d064905.mygainzapp;

public class Plan {

        int Planid;
        Boolean PlanActive;
        String PlanName;

        public Plan (int aPlanid, String aPlanName){

            Planid = aPlanid;
            PlanName= aPlanName;
    }
    public String toString(){
        return Planid+" "+PlanName;
    }
}


