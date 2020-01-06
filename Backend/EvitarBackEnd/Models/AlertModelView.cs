using System;
using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models
{
    public class AlertModelView{
        [Key]
        public int IdMovimento {get;set;}
        public string TypeMov {get;set;}
        public long IdColaborador {get;set;}
        public DateTime DataHora {get;set;}
        public string PrimeiroNomeCol {get;set;}
        public string UltimoNomeCol {get;set;}

        public int IdTipoEPI {get;set;}
        public string NomeTipoEPI {get;set;}

    }
}