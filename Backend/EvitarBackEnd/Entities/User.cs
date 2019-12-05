using EvitarBackEnd.Models;
using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Entities
{
    public class User
    {
        public int Id { get; set; }

        [StringLength(50)]
        public string Username { get; set; }
        public int IdColaborador{get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdColaborador")]
        public ColaboradorModel IdColaboradorForeignKey {get;set;}
        public byte[] PasswordHash { get; set; }
        public byte[] PasswordSalt { get; set; }
    }
}