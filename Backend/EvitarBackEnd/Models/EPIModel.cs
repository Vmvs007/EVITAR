using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

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

        public int Valido{get;set;}

        public int IdTipoEPI {get;set;}
        [ForeignKey("IdTipoEPI")]
        [NotMapped]
        public TipoEPIModel IdTipoEPIForeignKey {get;set;}
        
        public int IdColaborador {get;set;}
        [ForeignKey("IdColaborador")]
        [NotMapped]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
    }
}