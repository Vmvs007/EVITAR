using System;
using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models
{
    public class MovEPIModelView{
        [Key]
        public int IdMovimento {get;set;}
        [Key]
         public int IdEPI {get;set;}
         public string TypeMov {get;set;}
        public int IdColaborador {get;set;}
        public int Check {get;set;}
        public DateTime DataHora {get;set;}
    }
}