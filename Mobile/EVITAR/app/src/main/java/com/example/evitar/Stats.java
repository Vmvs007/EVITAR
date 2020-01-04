package com.example.evitar;

public class Stats {

    private int alertasDiarios;
    private int movimentosDiarios;
    private int movimentosSemanais;

    public Stats(int alertasDiarios, int movimentosDiarios, int movimentosSemanais) {
        this.alertasDiarios = alertasDiarios;
        this.movimentosDiarios = movimentosDiarios;
        this.movimentosSemanais = movimentosSemanais;
    }

    public int getAlertasDiarios() {
        return alertasDiarios;
    }

    public void setAlertasDiarios(int alertasDiarios) {
        this.alertasDiarios = alertasDiarios;
    }

    public int getMovimentosDiarios() {
        return movimentosDiarios;
    }

    public void setMovimentosDiarios(int movimentosDiarios) {
        this.movimentosDiarios = movimentosDiarios;
    }

    public int getMovimentosSemanais() {
        return movimentosSemanais;
    }

    public void setMovimentosSemanais(int movimentosSemanais) {
        this.movimentosSemanais = movimentosSemanais;
    }
}
