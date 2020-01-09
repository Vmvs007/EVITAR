using System;
using System.ComponentModel.DataAnnotations;
using System.Text.RegularExpressions;

namespace EvitarBackEnd.Models
{
    public class MovimentoModel{
        [Key]
        public int IdMovimento {get;set;}

        [RegularExpression(@"^E$|^S$")]
        public string TypeMov {get;set;}
        public long IdColaborador {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdColaborador")]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
        public int Check {get;set;}
        public DateTime DataHora {get;set;}
    }
}