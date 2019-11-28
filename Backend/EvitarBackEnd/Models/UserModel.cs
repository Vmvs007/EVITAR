namespace EvitarBackEnd.Models.Users
{
  public class UserModel
    {
        public int Id { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Username { get; set; }

        public int IdColaborador{get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdColaborador")]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
    }
}