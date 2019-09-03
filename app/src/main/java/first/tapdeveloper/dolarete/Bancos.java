package first.tapdeveloper.dolarete;

class Bancos {


    private String nombreBanco;
    private String compraDolar;
    private String ventaDolar;
    private String variacionDolar;
    private String cierre;



    public Bancos(String nombreBanco) {
        this.nombreBanco = nombreBanco;
        this.compraDolar = "1";
        this.ventaDolar = "1";
        this.variacionDolar = "1";
    }

    String getNombreBanco() {
        return nombreBanco;
    }

     String getCompraDolar() {
        return compraDolar;
    }

     String getVentaDolar() {
        return ventaDolar;
    }

     String getVariacionDolar() {
        return variacionDolar;
    }

    public void setCompraDolar(String compraDolar) {
        this.compraDolar = compraDolar;
    }

    public void setVentaDolar(String ventaDolar) {
        this.ventaDolar = ventaDolar;
    }

    public void setVariacionDolar(String variacionDolar) {
        this.variacionDolar = variacionDolar;
    }

    public String getCierre() {
        return cierre;
    }

    public void setCierre(String cierre) {
        this.cierre = cierre;
    }

    @Override
    public String toString() {
        return "Bancos{" +
                "nombreBanco='" + nombreBanco + '\'' +
                ", compraDolar='" + compraDolar + '\'' +
                ", ventaDolar='" + ventaDolar + '\'' +
                ", variacionDolar='" + variacionDolar + '\'' +
                '}';
    }
}
