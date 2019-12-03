using System.Globalization;
using System;
using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models
{
    public class MovimentoModel{
        [Key]
        public int IdMovimento {get;set;}
        public string TypeMov {get;set;}
        public int IdColaborador {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdColaborador")]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
        public int Check {get;set;}
        public DateTime DataHora {get;set;}
    }
}