using System;
using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models{

    public class ColaboradorModelView{

        [Key]
        public int IdColaborador {get;set;}
        public string NomeColaborador {get;set;}
        public string PrimeiroNomeCol {get;set;}
        public string UltimoNomeCol {get;set;}
        public DateTime DataNasc {get;set;}
        public int ccColaborador {get;set;}
        public int NifColaborador {get;set;}
        public String GeneroCol {get;set;}
        public int TelefoneCol {get;set;}
        public string MoradaCol {get;set;}
        public string EmailCol {get;set;}
        public DateTime DataRegistoCol {get;set;}
        public int NomeCargo {get;set;}
    }
}