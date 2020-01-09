package com.example.evitar.EpiFolder;

public class EpiAlert {
    private int idMovimento;
    private int idTipoEPI;
    private String nomeTipoEPI;

    public EpiAlert(int idMovimento, int idTipoEPI, String nomeTipoEPI) {
        this.idMovimento = idMovimento;
        this.idTipoEPI = idTipoEPI;
        this.nomeTipoEPI = nomeTipoEPI;
    }

    public int getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
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
