using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace EvitarBackEnd.Models
{
    public class EPICargoModel{
        [Key]
        public int IdCargo {get;set;}
        [ForeignKey("IdCargo")]
        [NotMapped]
        public CargoModel IdCargoForeignKey {get;set;}
        [Key]
        public int IdTipoEPI {get;set;}
        [ForeignKey("IdTipoEPI")]
        [NotMapped]
        public TipoEPIModel IdTipoEPIForeignKey {get;set;}
    }
}