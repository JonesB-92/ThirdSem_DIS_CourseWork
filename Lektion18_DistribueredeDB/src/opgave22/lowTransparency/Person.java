package opgave22.lowTransparency;

public class Person {
    String cpr;
    String navn;
    String bynavn;
    int loen;
    int skatteprocent;

    public String getCpr() {
        return cpr;
    }
    /**
     * @return Returns the navn.
     */
    public String getNavn() {
        return navn;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setBynavn(String bynavn) {
        this.bynavn = bynavn;
    }

    public void setSkatteprocent(int skatteprocent) {
        this.skatteprocent = skatteprocent;
    }

    public String getBynavn() {
        return bynavn;
    }

    public int getSkatteprocent() {
        return skatteprocent;
    }

    /**
     * @return Returns the loen.
     */
    public int getLoen() {
        return loen;
    }
    /**
     * @param loen The loen to set.
     */
    public void setLoen(int loen) {
        this.loen = loen;
    }


}
