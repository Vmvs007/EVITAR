package com.example.evitar.EpiFolder;

public class EpiAdd {
    private String nomeEPI;
    private String dataRegistoEPI;
    private String dataValidadeEPI;
    private int idColaborador;


    public EpiAdd(String nomeEPI, String dataRegistoEPI, String dataValidadeEPI, int idColaborador) {
        this.nomeEPI = nomeEPI;
        this.dataRegistoEPI = dataRegistoEPI;
        this.dataValidadeEPI = dataValidadeEPI;
        this.idColaborador = idColaborador;
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
}
