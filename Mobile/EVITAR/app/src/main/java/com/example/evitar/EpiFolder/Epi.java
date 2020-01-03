package com.example.evitar.EpiFolder;

public class Epi {
    private int idEPI;
    private String nomeEPI;
    private String dataRegistoEPI;
    private String dataValidadeEPI;
    private int idColaborador;
    private int valido;
    private int idTipoEPI;
    private String nomeTipoEPI;
    private String nomeInspector;

    public Epi(int idEPI, String nomeEPI, String dataRegistoEPI, String dataValidadeEPI, int idColaborador, int valido, int idTipoEPI, String nomeTipoEPI, String nomeInspector) {
        this.idEPI = idEPI;
        this.nomeEPI = nomeEPI;
        this.dataRegistoEPI = dataRegistoEPI;
        this.dataValidadeEPI = dataValidadeEPI;
        this.idColaborador = idColaborador;
        this.valido = valido;
        this.idTipoEPI = idTipoEPI;
        this.nomeTipoEPI = nomeTipoEPI;
        this.nomeInspector = nomeInspector;
    }
    public Epi(int idEPI, String nomeEPI, String dataRegistoEPI, String dataValidadeEPI, int idColaborador, int valido, int idTipoEPI) {
        this.idEPI = idEPI;
        this.nomeEPI = nomeEPI;
        this.dataRegistoEPI = dataRegistoEPI;
        this.dataValidadeEPI = dataValidadeEPI;
        this.idColaborador = idColaborador;
        this.valido = valido;
        this.idTipoEPI = idTipoEPI;
    }

    public int getIdEPI() {
        return idEPI;
    }

    public void setIdEPI(int idEPI) {
        this.idEPI = idEPI;
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

    public String getNomeTipoEPI() {
        return nomeTipoEPI;
    }

    public void setNomeTipoEPI(String nomeTipoEPI) {
        this.nomeTipoEPI = nomeTipoEPI;
    }

    public String getNomeInspector() {
        return nomeInspector;
    }

    public void setNomeInspector(String nomeInspector) {
        this.nomeInspector = nomeInspector;
    }
}
