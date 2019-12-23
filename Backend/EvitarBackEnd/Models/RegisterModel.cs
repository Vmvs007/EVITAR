using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace EvitarBackEnd.Models.Users
{
    public class RegisterModel
    {

        [Required]
        public string Username { get; set; }

        [Required]
        public string Password { get; set; }

        public int IdColaborador{get;set;}
        [ForeignKey("IdColaborador")]
        [NotMapped]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
    }
}