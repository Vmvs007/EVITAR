package com.example.evitar.EpiFolder;

public class Epi {
    private int idEPI;
    private String nomeEPI;
    private String dataRegistoEPI;
    private String dataValidadeEPI;
    private int idColaborador;

    public Epi(int idEPI, String nomeEPI, String dataRegistoEPI, String dataValidadeEPI, int idColaborador) {
        this.idEPI = idEPI;
        this.nomeEPI = nomeEPI;
        this.dataRegistoEPI = dataRegistoEPI;
        this.dataValidadeEPI = dataValidadeEPI;
        this.idColaborador = idColaborador;
    }

    public int getIdEPI() {
        return idEPI;
    }

    public void setIdEPI(int id) {
        this.idEPI = id;
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
