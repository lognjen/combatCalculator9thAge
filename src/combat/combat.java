package combat;

import armies.unit;

import java.util.Random;

public class combat {
    unit unit1, unit2;

    public combat(unit unit1, unit unit2) {
        this.unit1 = unit1;
        this.unit2 = unit2;
    }

    public unit getUnit1() {
        return unit1;
    }

    public void setUnit1(unit unit1) {
        this.unit1 = unit1;
    }

    public unit getUnit2() {
        return unit2;
    }

    public void setUnit2(unit unit2) {
        this.unit2 = unit2;
    }

     int initiative(){
        return unit1.agi - unit2.agi;
    }

    int getTargetToHit(unit unit1, unit unit2){
        int diff = unit1.off - unit2.def;

        if(diff >= 4)
            return 2;

        if(diff >= 1)
            return 3;

        if(diff >= -3)
            return 4;

        if(diff >=-7)
            return 5;

        return 6;
    }

    int getTargetToWound(unit unit1, unit unit2){
        int diff = unit1.str - unit2.res;

        if(diff >= 2)
            return 2;

        if(diff == 1)
            return 3;

        if(diff == 0)
            return 4;

        if(diff == -1)
            return 5;

        return 6;
    }

    int rollToHit(unit unit1, unit unit2){
        int targetToHit = getTargetToHit(unit1, unit2);
        int numberOfAttacks = unit1.columns * unit1.att + unit1.columns;
        int numberOfHits = 0;
        Random rand = new Random();

        for(int i = 0; i < numberOfAttacks; i++){
            if(rand.nextInt(6) + 1 >= targetToHit)
                numberOfHits++;
        }

        return numberOfHits;
    }

    int rollToWound(unit unit1, unit unit2, int numberOfHits){
        int targetToWound = getTargetToWound(unit1, unit2);
        int numberOfWounds = 0;
        Random rand = new Random();

        for(int i = 0; i < numberOfHits; i++){
            if(rand.nextInt(6) + 1 >= targetToWound)
                numberOfWounds++;
        }

        return numberOfWounds;
    }

    int strikes(unit unit1, unit unit2){
        int numberOfHits = rollToHit(unit1, unit2);
        int numberOfWounds = rollToWound(unit1, unit2, numberOfHits);
        //implements armor and special saves and then return number of wounds
    }

    int combatRound(unit unit1,unit unit2) {//works out a round of combat and returns the wound score
        int init = initiative();
        int unit1Wounds = 0, unit2Wounds = 0;


        if (init > 0) {
            //if unit 1 strikes first, kills off models and returns wound difference

            unit1Wounds = strikes(unit1, unit2);
            if (unit2.models < unit1Wounds) {
                unit1Wounds = unit2.models;
            }
            unit2.models -= unit1Wounds;

            if (unit2.models != 0) {
                unit2Wounds = strikes(unit2, unit1);
                if (unit1.models < unit2Wounds) {
                    unit2Wounds = unit1.models;
                }
                unit1.models -= unit2Wounds;
            }

        }


        else if (init < 0) {
            //if unit 2 strikes first, kills off models and returns wound difference

            unit2Wounds = strikes(unit2, unit1);
            if (unit1.models < unit2Wounds) {
                unit2Wounds = unit1.models;
            }
            unit1.models -= unit2Wounds;

            if (unit1.models != 0) {
                unit1Wounds = strikes(unit1, unit2);
                if (unit2.models < unit1Wounds) {
                    unit1Wounds = unit2.models;
                }
                unit2.models -= unit1Wounds;
            }
        }

        else if(init == 0){
            //if the unit strikes at the same time kills off models and returns wound difference

            unit1Wounds = strikes(unit1, unit2);
            unit2Wounds = strikes(unit2, unit1);

            if(unit1.models < unit2Wounds)
                unit2Wounds = unit1.models;

            if(unit2.models < unit1Wounds)
                unit1Wounds = unit2.models;

            unit1.models -= unit2Wounds;
            unit2.models -= unit1Wounds;
        }
        return unit1Wounds - unit2Wounds;

    }




    public String calculate(){



        }
    }
