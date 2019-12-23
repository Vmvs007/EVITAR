using System.ComponentModel.DataAnnotations.Schema;

namespace EvitarBackEnd.Models.Users
{
  public class UserModel
    {
        public string Username { get; set; }

        public int IdColaborador{get;set;}
        [ForeignKey("IdColaborador")]
        [NotMapped]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
    }
}