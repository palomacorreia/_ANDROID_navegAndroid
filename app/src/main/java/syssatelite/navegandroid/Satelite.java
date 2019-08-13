package syssatelite.navegandroid;

//import android.widget.int;

public class Satelite {
    private int latitudePosition;
    private int longitudePosition;
    private int altitudePosition;
    private float GNSS;
    private float SNR;
    private float ELEV;
    private float AZIM;
    private boolean TIPOSAT;


    public Satelite(float GNSS, float SNR, float ELEV, float AZIM, boolean TIPOSAT) {
        this.GNSS = GNSS;
        this.SNR = SNR;
        this.ELEV = ELEV;
        this.AZIM = AZIM;
        this.TIPOSAT = TIPOSAT;
    }

    public float getGNSS() {
        return GNSS;
    }

    public void setGNSS(float GNSS) {
        this.GNSS = GNSS;
    }

    public float getSNR() {
        return SNR;
    }

    public void setSNR(float SNR) {
        this.SNR = SNR;
    }

    public float getELEV() {
        return ELEV;
    }

    public void setELEV(float ELEV) {
        this.ELEV = ELEV;
    }

    public float getAZIM() {
        return AZIM;
    }

    public void setAZIM(float AZIM) {
        this.AZIM = AZIM;
    }

    public boolean isTIPOSAT() {
        return TIPOSAT;
    }

    public void setTIPOSAT(boolean TIPOSAT) {
        this.TIPOSAT = TIPOSAT;
    }
}