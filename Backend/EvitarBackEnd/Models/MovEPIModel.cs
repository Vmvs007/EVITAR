using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models{
    public class MovEPIModel{
    
         public int IdMovimento {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdMovimento")]
        public MovimentoModel IdMovimentoForeignKey {get;set;}

        
         public int IdEPI {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdEPI")]
        public EPIModel IdEPIForeignKey {get;set;}
    }
}