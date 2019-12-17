using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models
{
    public class TipoEPIModel
    {
        [Key]
        public int IdTipoEPI {get;set;}
        public string NomeTipoEPI {get;set;}
    }
}