package com.kanils.bank;

import java.sql.Date;
import java.util.Comparator;

public class Proposition {
    private double depossum;
    private double percenyear;
    private Date plannedtime;
    private Date currenttime;
    private Date puttime;
    private String currency;
    private int idDepos;

    public int getIdDepos() {
        return idDepos;
    }

    public void setIdDepos(int idDepos) {
        this.idDepos = idDepos;
    }

    public double getDepossum() {
        return depossum;
    }

    public void setDepossum(double depossum) {
        this.depossum = depossum;
    }

    public double getPercenyear() {
        return percenyear;
    }

    public void setPercenyear(double percenyear) {
        this.percenyear = percenyear;
    }

    public String getPlannedtimeS() {
        return plannedtime.toString();
    }

    public Date getPlannedtime() {
        return plannedtime;
    }

    public void setPlannedtime(Date plannedtime) {
        this.plannedtime = plannedtime;
    }

    public String getPuttimeS() {
        if (puttime==null)
            return "Нема даних";
        return puttime.toString();
    }

    public Date getPuttime() {
        return puttime;
    }

    public void setPuttime(Date puttime) {
        this.puttime = puttime;
    }

    public String getCurrenttimeS() {
        if (currenttime==null)
            return "Нема даних";
        return currenttime.toString();
    }

    public Date getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(Date currenttime) {
        this.currenttime = currenttime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return depossum + ", " + percenyear + ", " + puttime + ", " + currenttime + ", " + plannedtime + ", " + currency + "\n";
    }

    public static Comparator<Proposition> PropopositionPercentComparator = new Comparator<Proposition>() {
        @Override
        public int compare(Proposition proposition1, Proposition proposition2) {
            double proposition1Percent = proposition1.getPercenyear() + 0.5;
            double proposition2Percent = proposition2.getPercenyear() + 0.5;
            return (int) (proposition1Percent - proposition2Percent);
        }
    };

    public static Comparator<Proposition> PropositionDepossumComparator = new Comparator<Proposition>() {
        @Override
        public int compare(Proposition proposition1, Proposition proposition2) {
            double proposition1Depossum = proposition1.getDepossum();
            double proposition2Depossum = proposition2.getDepossum();
            return (int) (proposition1Depossum - proposition2Depossum);
        }
    };
}
