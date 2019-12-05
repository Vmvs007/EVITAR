using Microsoft.EntityFrameworkCore;
using EvitarBackEnd.Entities;

namespace EvitarBackEnd.Models
{
    public class EVITARContext : DbContext
    {
        public EVITARContext(DbContextOptions<EVITARContext> options)
                : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<EPICargoModel>().HasKey(table => new
            {
                table.IdCargo,
                table.IdEPI
            });
        }
        public DbSet<ColaboradorModel> ColaboradorModels { get; set; }
        public DbSet<EPIModel> EPIModels { get; set; }
        public DbSet<CargoModel> CargoModels { get; set; }
        public DbSet<MovimentoModel> MovimentoModels { get; set; }
        public DbSet<EPICargoModel> EPICargoModels { get; set; }
        public DbSet<User> Users { get; set; }


    }
}