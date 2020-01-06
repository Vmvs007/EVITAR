

namespace EvitarBackEnd.Models.Users
{
  public class UpdateModel
    {
        public string Username { get; set; }
        public string Password { get; set; }

        public long IdColaborador{get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdColaborador")]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
    }
}