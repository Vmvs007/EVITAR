package com.example.evitar.MovimentosFolder;

public class Movimento {

    private int idMovimento;
    private String typeMov;
    private int idColaborador;
    private int check;
    private String dataHora;
    private String primeiroNomeCol;
    private String ultimoNomeCol;

    public Movimento(int idMovimento, String typeMov, int idColaborador, int check, String dataHora, String primeiroNomeCol, String ultimoNomeCol) {
        this.idMovimento = idMovimento;
        this.typeMov = typeMov;
        this.idColaborador = idColaborador;
        this.check = check;
        this.dataHora = dataHora;
        this.primeiroNomeCol = primeiroNomeCol;
        this.ultimoNomeCol = ultimoNomeCol;
    }

    public int getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    public String getTypeMov() {
        return typeMov;
    }

    public void setTypeMov(String typeMov) {
        this.typeMov = typeMov;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
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
}