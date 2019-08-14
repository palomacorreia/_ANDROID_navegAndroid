package syssatelite.navegandroid;

//import android.widget.int;

public class Satelite {
    private int latitudePosition;
    private int longitudePosition;
    private int altitudePosition;
    private double GNSS;
    private double SNR;
    private double ELEV;
    private double AZIM;
    private boolean TIPOSAT;

    public Satelite() {
    }

    public Satelite(double GNSS, double SNR, double ELEV, double AZIM, boolean TIPOSAT) {
        this.GNSS = GNSS;
        this.SNR = SNR;
        this.ELEV = ELEV;
        this.AZIM = AZIM;
        this.TIPOSAT = TIPOSAT;
    }

    public double getGNSS() {
        return GNSS;
    }

    public void setGNSS(double GNSS) {
        this.GNSS = GNSS;
    }

    public double getSNR() {
        return SNR;
    }

    public void setSNR(double SNR) {
        this.SNR = SNR;
    }

    public double getELEV() {
        return ELEV;
    }

    public void setELEV(double ELEV) {
        this.ELEV = ELEV;
    }

    public double getAZIM() {
        return AZIM;
    }

    public void setAZIM(double AZIM) {
        this.AZIM = AZIM;
    }

    public boolean getTIPOSAT() {
        return TIPOSAT;
    }

    public void setTIPOSAT(boolean  TIPOSAT) {
        this.TIPOSAT = TIPOSAT;
    }
}