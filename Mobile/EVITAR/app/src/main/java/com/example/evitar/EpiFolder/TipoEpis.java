package com.example.evitar.EpiFolder;

public class TipoEpis {

    private int idTipoEPI;
    private String nomeTipoEPI;

    public TipoEpis(int idTipoEPI, String nomeTipoEPI) {
        this.idTipoEPI = idTipoEPI;
        this.nomeTipoEPI = nomeTipoEPI;
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
}
