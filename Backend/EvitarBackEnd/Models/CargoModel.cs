using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models
{
    public class CargoModel{
        [Key]
        public int IdCargo {get;set;}
        [StringLength(100)]
        public string NomeCargo {get;set;}
        [StringLength(100)]
        public string ZonaCargo {get;set;}
    }
}