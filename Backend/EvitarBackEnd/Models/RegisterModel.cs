using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models.Users
{
    public class RegisterModel
    {

        [Required]
        public string Username { get; set; }

        [Required]
        public string Password { get; set; }

        public int IdColaborador{get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdColaborador")]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
    }
}