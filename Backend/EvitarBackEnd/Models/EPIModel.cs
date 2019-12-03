using System;
using System.ComponentModel.DataAnnotations;


namespace EvitarBackEnd.Models
{
    public class EPIModel
    {
        [Key]
        public int IdEPI {get;set;}

        [StringLength(100)]
        public string NomeEPI {get;set;}
        public DateTime DataRegistoEPI {get;set;}
        public DateTime DataValidadeEPI {get;set;}
        
        public int IdColaborador {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdColaborador")]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
    }
}