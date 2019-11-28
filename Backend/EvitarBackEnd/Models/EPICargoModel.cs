using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models
{
    public class EPICargoModel{
        [Key]
        public int IdCargo {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdCargo")]
        public CargoModel IdCargoForeignKey {get;set;}
        [Key]
        public int IdEPI {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdEPI")]
        public EPIModel IdEPIForeignKey {get;set;}
    }
}