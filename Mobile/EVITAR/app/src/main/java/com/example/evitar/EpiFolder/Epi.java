package com.example.evitar.EpiFolder;

public class Epi {
    private Long idEPI;
    private String nomeEPI;
    private String dataRegistoEPI;
    private String dataValidadeEPI;
    private Long idColaborador;
    private int valido;
    private int idTipoEPI;
    private String nomeTipoEPI;
    private String nomeInspector;

    public Epi(Long idEPI, String nomeEPI, String dataRegistoEPI, String dataValidadeEPI, Long idColaborador, int valido, int idTipoEPI, String nomeTipoEPI, String nomeInspector) {
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
    public Epi(Long idEPI, String nomeEPI, String dataRegistoEPI, String dataValidadeEPI, Long idColaborador, int valido, int idTipoEPI) {
        this.idEPI = idEPI;
        this.nomeEPI = nomeEPI;
        this.dataRegistoEPI = dataRegistoEPI;
        this.dataValidadeEPI = dataValidadeEPI;
        this.idColaborador = idColaborador;
        this.valido = valido;
        this.idTipoEPI = idTipoEPI;
    }

    public Long getIdEPI() {
        return idEPI;
    }

    public void setIdEPI(Long idEPI) {
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

    public Long getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Long idColaborador) {
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
