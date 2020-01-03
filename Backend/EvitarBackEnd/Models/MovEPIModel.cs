using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models{
    public class MovEPIModel{
    
         public int IdMovimento {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdMovimento")]
        public MovimentoModel IdMovimentoForeignKey {get;set;}

        
         public int IdTipoEPI {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdTipoEPI")]
        public TipoEPIModel IdTipoEPIForeignKey {get;set;}
    }
}