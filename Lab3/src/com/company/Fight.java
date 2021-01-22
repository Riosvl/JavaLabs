package com.company;

import com.droids.*;

import java.util.List;
import java.util.Random;

import static com.colors.Colors.*;

public class Fight {

    List<com.droids.Droid> team1;
    List<com.droids.Droid> team2;
    List<com.droids.Droid> assaultTeam;
    List<com.droids.Droid> targetTeam;

    public Fight(List<com.droids.Droid> team1, List<com.droids.Droid> team2) {
        this.team1 = team1;
        this.team2 = team2;
    }
    public void teamFight() {
            int round = 1;
            do{
                System.out.println( "\n----------" +
                                    "\nRound " + round++);
                pickAttackerTeam();
                for(com.droids.Droid attacker : assaultTeam){
                    if (!isTeamAlive(targetTeam))
                        break;
                    if(attacker.isHealer())
                        attacker.action(pickTargetInTeam(assaultTeam));
                    else
                        attacker.action(pickTargetInTeam(targetTeam));
                    removeCorpse();
                }

                System.out.println(assaultTeam.toString());
                System.out.println(targetTeam.toString());


            } while (isTeamAlive(targetTeam));
            System.out.println(ANSI_GREEN + "\n\n"+assaultTeam.toString() + " won" + ANSI_RESET);
    }
    private void pickAttackerTeam(){
        if (new Random().nextBoolean()){
            assaultTeam = team1;
            targetTeam = team2;
        } else {
            targetTeam = team1;
            assaultTeam = team2;
        }
    }
    private boolean isTeamAlive(List<com.droids.Droid> team){
        for(com.droids.Droid droid : team)
            if (droid.isAlive())
                return true;
        return false;
    }
    private void removeCorpse(){ targetTeam.removeIf(droid -> !droid.isAlive()); }
    private com.droids.Droid pickTargetInTeam(List<com.droids.Droid> target){ return target.get(new Random().nextInt(target.size())); }
}