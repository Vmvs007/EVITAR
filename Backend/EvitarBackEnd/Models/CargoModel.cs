using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models
{
    public class CargoModel{
        [Key]
        public int IdCargo {get;set;}
        public string TypeCargo {get;set;}
        public string NomeCargo {get;set;}
        public string ZonaCargo {get;set;}
    }
}