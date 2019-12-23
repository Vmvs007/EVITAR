using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace EvitarBackEnd.Models{
    public class MovEPIModel{
    
         public int IdMovimento {get;set;}
        [ForeignKey("IdMovimento")]
        [NotMapped]
        public MovimentoModel IdMovimentoForeignKey {get;set;}

        
         public int IdEPI {get;set;}
        [ForeignKey("IdEPI")]
        [NotMapped]
        public EPIModel IdEPIForeignKey {get;set;}
    }
}