using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models
{
    public class EPICargoModel{
        [Key]
        public int IdCargo {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdCargo")]
        public CargoModel IdCargoForeignKey {get;set;}
        [Key]
        public int IdTipoEPI {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdTipoEPI")]
        public TipoEPIModel IdTipoEPIForeignKey {get;set;}
    }
}