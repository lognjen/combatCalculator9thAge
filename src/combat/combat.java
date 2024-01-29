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

    int getTargetArmourSave(int ap, int arm){
        return 7 - arm + ap;
    }

    int rollArmourSaves(unit unit1, unit unit2, int numberOfWounds){
        int armourSave  = getTargetArmourSave(unit1.ap, unit2.arm);
        if(armourSave > 6)
            return numberOfWounds;

        Random rand = new Random();
        for(int i = 0; i < numberOfWounds; i++){
            if(rand.nextInt(6) + 1 >= armourSave)
                numberOfWounds--;
        }
        return  numberOfWounds;

    }

    int rollAegisSaves(unit unit, int numberOfWounds){
        Random rand = new Random();
        for(int i = 0; i < numberOfWounds; i++){
            if(rand.nextInt(6) + 1 >= unit.aeg)
                numberOfWounds--;
        }
        return  numberOfWounds;
    }

    int strikes(unit unit1, unit unit2){
        int numberOfHits = rollToHit(unit1, unit2);
        int numberOfWounds = rollToWound(unit1, unit2, numberOfHits);
        numberOfWounds = rollArmourSaves(unit1, unit2, numberOfWounds);
        if(unit2.aeg != null)
            numberOfWounds = rollAegisSaves(unit2, numberOfWounds);
        return numberOfWounds;
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




     int calculate(unit  unit1, unit unit2, int whoCharged){
        boolean combatOngoing = true;
        int firstRound = 0;
        int winner = 0;

        while(combatOngoing) {
            int roundResults = combatRound(unit1, unit2);
            if(firstRound > 0){
                whoCharged = 0;
            }
            firstRound++;
            if(whoCharged == 1){
                roundResults++;
            }
            else if(whoCharged == -1){
                roundResults--;
            }

            if(unit1.bnb == true)
                roundResults++;
            if(unit2.bnb == true)
                roundResults--;

            int rankBonus1, rankBonus2;
            if(unit1.columns < 8){
                rankBonus1 = unit1.models / unit1.columns - 1;
                if(rankBonus1 > 3)
                    rankBonus1 = 3;
                roundResults += rankBonus1;
            }
            if(unit2.columns < 8){
                rankBonus2 = unit2.models / unit2.columns - 1;
                if(rankBonus2 > 3)
                    rankBonus2 = 3;
                roundResults -= rankBonus2;
            }


            rankBonus1 = unit1.models/ unit1.columns;
            rankBonus2 = unit2.models / unit2.columns;
            Random rand = new Random();
            if(roundResults > 0){
                if(rankBonus2 > rankBonus1){
                    if(rand.nextInt(6) + rand.nextInt(6) + 2 > unit2.dis){
                        combatOngoing = false;
                        winner = 1;
                    }
                }
                if(rand.nextInt(6) + rand.nextInt(6) + 2 > unit2.dis - roundResults) {
                    combatOngoing = false;
                    winner = 1;
                }
            }

            if(roundResults < 0){
                if(rankBonus1 > rankBonus2){
                    if(rand.nextInt(6) + rand.nextInt(6) + 2 > unit1.dis) {
                        combatOngoing = false;
                        winner = -1;
                    }
                }
                if(rand.nextInt(6) + rand.nextInt(6) + 2 > unit1.dis - roundResults) {
                    combatOngoing = false;
                    winner = -1;
                }
            }
            if(unit1.models == 0){
                combatOngoing = false;
                winner = -2;
            }
            if(unit2.models == 0){
                combatOngoing = false;
                winner = 2;
            }
        }
        return winner;
    }

        public String simulate(unit unit1, unit unit2, int whoCharged, int numberOfSimulations){//need to add pursuit and chance to destroy the entire unit, maybe even chance to win in first round
                int wins1 = 0, wins2 = 0, weirdBugs = 0;
                for(int i = 0; i < numberOfSimulations; i++){
                    int winner = calculate(unit1, unit2, whoCharged);
                    if(winner > 0)
                        wins1++;
                    else if(winner < 0)
                        wins2++;
                    else weirdBugs++;
                }
                return unit1.name + "has won " + wins1 + "combats, while " +  unit2.name + " has won " + wins2 + " combats, out of " + numberOfSimulations;
        }
    }
