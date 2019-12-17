﻿// <auto-generated />
using System;
using EvitarBackEnd.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace EvitarBackEnd.Migrations
{
    [DbContext(typeof(EVITARContext))]
    partial class EVITARContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "3.0.1")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("EvitarBackEnd.Entities.User", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<int>("IdColaborador")
                        .HasColumnType("int");

                    b.Property<byte[]>("PasswordHash")
                        .HasColumnType("varbinary(max)");

                    b.Property<byte[]>("PasswordSalt")
                        .HasColumnType("varbinary(max)");

                    b.Property<string>("Username")
                        .HasColumnType("nvarchar(50)")
                        .HasMaxLength(50);

                    b.HasKey("Id");

                    b.HasIndex("IdColaborador");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("EvitarBackEnd.Models.CargoModel", b =>
                {
                    b.Property<int>("IdCargo")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("NomeCargo")
                        .HasColumnType("nvarchar(100)")
                        .HasMaxLength(100);

                    b.Property<string>("ZonaCargo")
                        .HasColumnType("nvarchar(100)")
                        .HasMaxLength(100);

                    b.HasKey("IdCargo");

                    b.ToTable("CargoModels");
                });

            modelBuilder.Entity("EvitarBackEnd.Models.ColaboradorModel", b =>
                {
                    b.Property<int>("IdColaborador")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<DateTime>("DataNasc")
                        .HasColumnType("datetime2");

                    b.Property<DateTime>("DataRegistoCol")
                        .HasColumnType("datetime2");

                    b.Property<string>("EmailCol")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("GeneroCol")
                        .HasColumnType("nvarchar(max)");

                    b.Property<int>("IdCargo")
                        .HasColumnType("int");

                    b.Property<string>("MoradaCol")
                        .HasColumnType("nvarchar(150)")
                        .HasMaxLength(150);

                    b.Property<int>("NifColaborador")
                        .HasColumnType("int");

                    b.Property<string>("NomeColaborador")
                        .HasColumnType("nvarchar(150)")
                        .HasMaxLength(150);

                    b.Property<string>("PrimeiroNomeCol")
                        .HasColumnType("nvarchar(50)")
                        .HasMaxLength(50);

                    b.Property<int>("TelefoneCol")
                        .HasColumnType("int");

                    b.Property<string>("UltimoNomeCol")
                        .HasColumnType("nvarchar(50)")
                        .HasMaxLength(50);

                    b.Property<int>("ccColaborador")
                        .HasColumnType("int");

                    b.HasKey("IdColaborador");

                    b.HasIndex("IdCargo");

                    b.ToTable("ColaboradorModels");
                });

            modelBuilder.Entity("EvitarBackEnd.Models.EPICargoModel", b =>
                {
                    b.Property<int>("IdCargo")
                        .HasColumnType("int");

                    b.Property<int>("IdTipoEPI")
                        .HasColumnType("int");

                    b.HasKey("IdCargo", "IdTipoEPI");

                    b.HasIndex("IdTipoEPI");

                    b.ToTable("EPICargoModels");
                });

            modelBuilder.Entity("EvitarBackEnd.Models.EPIModel", b =>
                {
                    b.Property<int>("IdEPI")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<DateTime>("DataRegistoEPI")
                        .HasColumnType("datetime2");

                    b.Property<DateTime>("DataValidadeEPI")
                        .HasColumnType("datetime2");

                    b.Property<int>("IdColaborador")
                        .HasColumnType("int");

                    b.Property<int>("IdTipoEPI")
                        .HasColumnType("int");

                    b.Property<string>("NomeEPI")
                        .HasColumnType("nvarchar(100)")
                        .HasMaxLength(100);

                    b.HasKey("IdEPI");

                    b.HasIndex("IdColaborador");

                    b.HasIndex("IdTipoEPI");

                    b.ToTable("EPIModels");
                });

            modelBuilder.Entity("EvitarBackEnd.Models.MovEPIModel", b =>
                {
                    b.Property<int>("IdMovimento")
                        .HasColumnType("int");

                    b.Property<int>("IdEPI")
                        .HasColumnType("int");

                    b.HasKey("IdMovimento", "IdEPI");

                    b.HasIndex("IdEPI");

                    b.ToTable("MovEPIModels");
                });

            modelBuilder.Entity("EvitarBackEnd.Models.MovimentoModel", b =>
                {
                    b.Property<int>("IdMovimento")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<int>("Check")
                        .HasColumnType("int");

                    b.Property<DateTime>("DataHora")
                        .HasColumnType("datetime2");

                    b.Property<int>("IdColaborador")
                        .HasColumnType("int");

                    b.Property<string>("TypeMov")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("IdMovimento");

                    b.HasIndex("IdColaborador");

                    b.ToTable("MovimentoModels");
                });

            modelBuilder.Entity("EvitarBackEnd.Models.TipoEPIModel", b =>
                {
                    b.Property<int>("IdTipoEPI")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("NomeTipoEPI")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("IdTipoEPI");

                    b.ToTable("TipoEPIModels");
                });

            modelBuilder.Entity("EvitarBackEnd.Entities.User", b =>
                {
                    b.HasOne("EvitarBackEnd.Models.ColaboradorModel", "IdColaboradorForeignKey")
                        .WithMany()
                        .HasForeignKey("IdColaborador")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("EvitarBackEnd.Models.ColaboradorModel", b =>
                {
                    b.HasOne("EvitarBackEnd.Models.CargoModel", "IdCargoForeignKey")
                        .WithMany()
                        .HasForeignKey("IdCargo")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("EvitarBackEnd.Models.EPICargoModel", b =>
                {
                    b.HasOne("EvitarBackEnd.Models.CargoModel", "IdCargoForeignKey")
                        .WithMany()
                        .HasForeignKey("IdCargo")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("EvitarBackEnd.Models.TipoEPIModel", "IdTipoEPIForeignKey")
                        .WithMany()
                        .HasForeignKey("IdTipoEPI")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("EvitarBackEnd.Models.EPIModel", b =>
                {
                    b.HasOne("EvitarBackEnd.Models.ColaboradorModel", "IdColaboradorForeignKey")
                        .WithMany()
                        .HasForeignKey("IdColaborador")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("EvitarBackEnd.Models.TipoEPIModel", "IdTipoEPIForeignKey")
                        .WithMany()
                        .HasForeignKey("IdTipoEPI")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("EvitarBackEnd.Models.MovEPIModel", b =>
                {
                    b.HasOne("EvitarBackEnd.Models.EPIModel", "IdEPIForeignKey")
                        .WithMany()
                        .HasForeignKey("IdEPI")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("EvitarBackEnd.Models.MovimentoModel", "IdMovimentoForeignKey")
                        .WithMany()
                        .HasForeignKey("IdMovimento")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("EvitarBackEnd.Models.MovimentoModel", b =>
                {
                    b.HasOne("EvitarBackEnd.Models.ColaboradorModel", "IdColaboradorForeignKey")
                        .WithMany()
                        .HasForeignKey("IdColaborador")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });
#pragma warning restore 612, 618
        }
    }
}
