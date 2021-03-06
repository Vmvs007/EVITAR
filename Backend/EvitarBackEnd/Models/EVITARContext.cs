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
                table.IdTipoEPI
            });
            
            builder.Entity<MovEPIModel>().HasKey(table => new
            {
                table.IdMovimento,
                table.IdTipoEPI
            });

            builder.Entity<MovimentoModelView>(eb =>
        {   
            eb.HasNoKey();
            eb.ToView("MovimentoModelViews");
        });
         builder.Entity<AlertModelView>(eb =>
        {   
            eb.HasNoKey();
            eb.ToView("AlertViews");
        });
            builder.Entity<ColaboradorModelView>(eb =>
        {   
            eb.HasNoKey();
            eb.ToView("ColaboradorModelViews");
        });

         builder.Entity<EPIModelView>(eb =>
        {   
            eb.HasNoKey();
            eb.ToView("EPIModelViews");
        });
        builder.Entity<MovEPIModelView>(eb =>
        {   
            eb.HasNoKey();
            eb.ToView("MovEPIModelViews");
        });
         builder.Entity<EPICargoNecModelView>(eb =>
        {   
            eb.HasNoKey();
            eb.ToView("EPICargoNecModelViews");
        });
         builder.Entity<WarningsMovModelView>(eb =>
        {   
            eb.HasNoKey();
            eb.ToView("WarningsMovModelView");
        });
        }
        
        public DbSet<ColaboradorModel> ColaboradorModels { get; set; }
        public DbSet<EPIModel> EPIModels { get; set; }
        public DbSet<CargoModel> CargoModels { get; set; }
        public DbSet<MovimentoModel> MovimentoModels { get; set; }
        public DbSet<EPICargoModel> EPICargoModels { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<MovEPIModel> MovEPIModels { get; set; }
        public DbSet<TipoEPIModel> TipoEPIModels {get;set;}

        public DbSet<MovimentoModelView> MovimentoModelViews{get;set;}
        public DbSet<ColaboradorModelView> ColaboradorModelViews{get;set;}
        public DbSet<EPIModelView> EPIModelViews{get;set;}
        public DbSet<MovEPIModelView> MovEPIModelViews{get;set;}
        public DbSet<EPICargoNecModelView> EPICargoNecModelViews{get;set;}

        public DbSet<WarningsMovModelView> WarningsMovModelViews{get;set;}

        public DbSet<AlertModelView> AlertViews{get;set;}
    }
}