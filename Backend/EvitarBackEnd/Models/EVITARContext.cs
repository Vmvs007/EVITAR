using Microsoft.EntityFrameworkCore;
namespace EvitarBackEnd.Models
{
    public class EVITARContext: DbContext
    {
        public EVITARContext (DbContextOptions<EVITARContext> options)
                :base(options)
        {
        }
        public DbSet<ColaboradorModel> ColaboradorModels {get;set;}
        public DbSet<EPIModel> EPIModels{get;set;}
        public DbSet<CargoModel> CargoModels {get;set;}
        public DbSet<MovimentoModel> MovimentoModels {get;set;}
    }
}