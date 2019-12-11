package com.example.evitar.NotificationFolder;

import java.util.Date;

public class Notification {

    private int idMovimento;
    private String typeMov;
    private int idColaborador;
    private int check;
    private String dataHora;

    public Notification(int idMovimento, String typeMov, int idColaborador, int check, String dataHora) {
        this.idMovimento = idMovimento;
        this.typeMov = typeMov;
        this.idColaborador = idColaborador;
        this.check = check;
        this.dataHora = dataHora;
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
}