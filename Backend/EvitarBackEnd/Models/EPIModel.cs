using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema; 

namespace EvitarBackEnd.Models
{
    public class EPIModel
    {
        [Key]
        public long IdEPI {get;set;}

        [StringLength(100)]
        public string NomeEPI {get;set;}
        public DateTime DataRegistoEPI {get;set;}
        public DateTime DataValidadeEPI {get;set;}
       
         public long IdColaborador {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdColaborador")]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}

        public int Valido{get;set;}

        public int IdTipoEPI {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdTipoEPI")]
        public TipoEPIModel IdTipoEPIForeignKey {get;set;}
        
       

    }
}