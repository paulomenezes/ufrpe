package br.deinfo.ufrpe.models;

import org.parceler.Parcel;
import org.simpleframework.xml.Element;

/**
 * Created by paulo on 11/02/2017.
 */
@Parcel
public class RestaurantMeal {
    @Element(name = "principal")
    private String principal;

    @Element(name = "acompanhamento")
    private String acompanhamento;

    @Element(name = "grelha")
    private String grelha;

    @Element(name = "sobremesa")
    private String sobremesa;

    @Element(name = "suco")
    private String suco;

    @Element(name = "salada")
    private String salada;

    @Element(name = "fastgrill")
    private String fastgrill;

    @Element(name = "vegetariano")
    private String vegetariano;

    @Element(name = "sopa", required = false)
    private String sopa;

    public String getPrincipal() {
        return principal.substring(principal.indexOf(':') + 2);
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getAcompanhamento() {
        return acompanhamento.substring(acompanhamento.indexOf(':') + 2);
    }

    public void setAcompanhamento(String acompanhamento) {
        this.acompanhamento = acompanhamento;
    }

    public String getGrelha() {
        return grelha.substring(grelha.indexOf(':') + 2);
    }

    public void setGrelha(String grelha) {
        this.grelha = grelha;
    }

    public String getSobremesa() {
        return sobremesa.substring(sobremesa.indexOf(':') + 2);
    }

    public void setSobremesa(String sobremesa) {
        this.sobremesa = sobremesa;
    }

    public String getSuco() {
        return suco.substring(suco.indexOf(':') + 2);
    }

    public void setSuco(String suco) {
        this.suco = suco;
    }

    public String getSalada() {
        return salada.substring(salada.indexOf(':') + 2);
    }

    public void setSalada(String salada) {
        this.salada = salada;
    }

    public String getFastgrill() {
        return fastgrill.substring(fastgrill.indexOf(':') + 2);
    }

    public void setFastgrill(String fastgrill) {
        this.fastgrill = fastgrill;
    }

    public String getVegetariano() {
        return vegetariano.substring(vegetariano.indexOf(':') + 2);
    }

    public void setVegetariano(String vegetariano) {
        this.vegetariano = vegetariano;
    }

    public String getSopa() {
        return sopa.substring(sopa.indexOf(':') + 2);
    }

    public void setSopa(String sopa) {
        this.sopa = sopa;
    }
}
