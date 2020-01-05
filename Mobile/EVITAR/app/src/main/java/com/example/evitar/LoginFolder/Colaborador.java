package com.example.evitar.LoginFolder;

public class Colaborador {

    private int idColaborador;
    private String nomeColaborador;
    private String primeiroNomeCol;
    private String ultimoNomeCol;
    private String dataNasc;
    private int ccColaborador;
    private int nifColaborador;
    private String generoCol;
    private int telefoneCol;
    private String moradaCol;
    private String emailCol;
    private String dataRegistoCol;
    private int idCargo;

    public Colaborador(int idColaborador, String nomeColaborador, String primeiroNomeCol, String ultimoNomeCol, String dataNasc, int ccColaborador, int nifColaborador, String generoCol, int telefoneCol, String moradaCol, String emailCol, String dataRegistoCol, int idCargo) {
        this.idColaborador = idColaborador;
        this.nomeColaborador = nomeColaborador;
        this.primeiroNomeCol = primeiroNomeCol;
        this.ultimoNomeCol = ultimoNomeCol;
        this.dataNasc = dataNasc;
        this.ccColaborador = ccColaborador;
        this.nifColaborador = nifColaborador;
        this.generoCol = generoCol;
        this.telefoneCol = telefoneCol;
        this.moradaCol = moradaCol;
        this.emailCol = emailCol;
        this.dataRegistoCol = dataRegistoCol;
        this.idCargo = idCargo;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNomeColaborador() {
        return nomeColaborador;
    }

    public void setNomeColaborador(String nomeColaborador) {
        this.nomeColaborador = nomeColaborador;
    }

    public String getPrimeiroNomeCol() {
        return primeiroNomeCol;
    }

    public void setPrimeiroNomeCol(String primeiroNomeCol) {
        this.primeiroNomeCol = primeiroNomeCol;
    }

    public String getUltimoNomeCol() {
        return ultimoNomeCol;
    }

    public void setUltimoNomeCol(String ultimoNomeCol) {
        this.ultimoNomeCol = ultimoNomeCol;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public int getCcColaborador() {
        return ccColaborador;
    }

    public void setCcColaborador(int ccColaborador) {
        this.ccColaborador = ccColaborador;
    }

    public int getNifColaborador() {
        return nifColaborador;
    }

    public void setNifColaborador(int nifColaborador) {
        this.nifColaborador = nifColaborador;
    }

    public String getGeneroCol() {
        return generoCol;
    }

    public void setGeneroCol(String generoCol) {
        this.generoCol = generoCol;
    }

    public int getTelefoneCol() {
        return telefoneCol;
    }

    public void setTelefoneCol(int telefoneCol) {
        this.telefoneCol = telefoneCol;
    }

    public String getMoradaCol() {
        return moradaCol;
    }

    public void setMoradaCol(String moradaCol) {
        this.moradaCol = moradaCol;
    }

    public String getEmailCol() {
        return emailCol;
    }

    public void setEmailCol(String emailCol) {
        this.emailCol = emailCol;
    }

    public String getDataRegistoCol() {
        return dataRegistoCol;
    }

    public void setDataRegistoCol(String dataRegistoCol) {
        this.dataRegistoCol = dataRegistoCol;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }
}
