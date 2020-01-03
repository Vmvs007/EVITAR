package com.example.evitar.EpiFolder;

public class EpiAdd {
    private String nomeEPI;
    private String dataRegistoEPI;
    private String dataValidadeEPI;
    private int idColaborador;
    private int valido;
    private int idTipoEPI;

    public EpiAdd(String nomeEPI, String dataRegistoEPI, String dataValidadeEPI, int idColaborador, int valido, int idTipoEPI) {
        this.nomeEPI = nomeEPI;
        this.dataRegistoEPI = dataRegistoEPI;
        this.dataValidadeEPI = dataValidadeEPI;
        this.idColaborador = idColaborador;
        this.valido = valido;
        this.idTipoEPI = idTipoEPI;
    }

    public String getNomeEPI() {
        return nomeEPI;
    }

    public void setNomeEPI(String nomeEPI) {
        this.nomeEPI = nomeEPI;
    }

    public String getDataRegistoEPI() {
        return dataRegistoEPI;
    }

    public void setDataRegistoEPI(String dataRegistoEPI) {
        this.dataRegistoEPI = dataRegistoEPI;
    }

    public String getDataValidadeEPI() {
        return dataValidadeEPI;
    }

    public void setDataValidadeEPI(String dataValidadeEPI) {
        this.dataValidadeEPI = dataValidadeEPI;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public int getValido() {
        return valido;
    }

    public void setValido(int valido) {
        this.valido = valido;
    }

    public int getIdTipoEPI() {
        return idTipoEPI;
    }

    public void setIdTipoEPI(int idTipoEPI) {
        this.idTipoEPI = idTipoEPI;
    }
}
