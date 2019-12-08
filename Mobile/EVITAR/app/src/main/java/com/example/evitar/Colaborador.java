package com.example.evitar;

public class Colaborador {

    private int idColaborador;
    private String NomeColaborador;
    private String PrimeiroNomeCol;
    private String UltimoNomeCol;
    private String DataNasc;
    private int ccColaborador;
    private int NifColaborador;
    private String GeneroCol;
    private int TelefoneCol;
    private String MoradaCol;
    private String EmailCol;
    private String DataRegistoCol;
    private int IdCargo;

    public Colaborador(int idColaborador, String nomeColaborador, String primeiroNomeCol, String ultimoNomeCol, String dataNasc, int ccColaborador, int nifColaborador, String generoCol, int telefoneCol, String moradaCol, String emailCol, String dataRegistoCol, int idCargo) {
        this.idColaborador = idColaborador;
        NomeColaborador = nomeColaborador;
        PrimeiroNomeCol = primeiroNomeCol;
        UltimoNomeCol = ultimoNomeCol;
        DataNasc = dataNasc;
        this.ccColaborador = ccColaborador;
        NifColaborador = nifColaborador;
        GeneroCol = generoCol;
        TelefoneCol = telefoneCol;
        MoradaCol = moradaCol;
        EmailCol = emailCol;
        DataRegistoCol = dataRegistoCol;
        IdCargo = idCargo;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNomeColaborador() {
        return NomeColaborador;
    }

    public void setNomeColaborador(String nomeColaborador) {
        NomeColaborador = nomeColaborador;
    }

    public String getPrimeiroNomeCol() {
        return PrimeiroNomeCol;
    }

    public void setPrimeiroNomeCol(String primeiroNomeCol) {
        PrimeiroNomeCol = primeiroNomeCol;
    }

    public String getUltimoNomeCol() {
        return UltimoNomeCol;
    }

    public void setUltimoNomeCol(String ultimoNomeCol) {
        UltimoNomeCol = ultimoNomeCol;
    }

    public String getDataNasc() {
        return DataNasc;
    }

    public void setDataNasc(String dataNasc) {
        DataNasc = dataNasc;
    }

    public int getCcColaborador() {
        return ccColaborador;
    }

    public void setCcColaborador(int ccColaborador) {
        this.ccColaborador = ccColaborador;
    }

    public int getNifColaborador() {
        return NifColaborador;
    }

    public void setNifColaborador(int nifColaborador) {
        NifColaborador = nifColaborador;
    }

    public String getGeneroCol() {
        return GeneroCol;
    }

    public void setGeneroCol(String generoCol) {
        GeneroCol = generoCol;
    }

    public int getTelefoneCol() {
        return TelefoneCol;
    }

    public void setTelefoneCol(int telefoneCol) {
        TelefoneCol = telefoneCol;
    }

    public String getMoradaCol() {
        return MoradaCol;
    }

    public void setMoradaCol(String moradaCol) {
        MoradaCol = moradaCol;
    }

    public String getEmailCol() {
        return EmailCol;
    }

    public void setEmailCol(String emailCol) {
        EmailCol = emailCol;
    }

    public String getDataRegistoCol() {
        return DataRegistoCol;
    }

    public void setDataRegistoCol(String dataRegistoCol) {
        DataRegistoCol = dataRegistoCol;
    }

    public int getIdCargo() {
        return IdCargo;
    }

    public void setIdCargo(int idCargo) {
        IdCargo = idCargo;
    }
}
