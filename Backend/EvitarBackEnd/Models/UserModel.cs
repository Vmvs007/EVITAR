

namespace EvitarBackEnd.Models.Users
{
  public class UserModel
    {
        public string Username { get; set; }

        public int IdColaborador{get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdColaborador")]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
    }
}