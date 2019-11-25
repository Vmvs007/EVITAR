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

            modelBuilder.Entity("EvitarBackEnd.Models.CargoModel", b =>
                {
                    b.Property<int>("IdCargo")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("NomeCargo")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("TypeCargo")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("ZonaCargo")
                        .HasColumnType("nvarchar(max)");

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
                        .HasColumnType("nvarchar(max)");

                    b.Property<int>("NifColaborador")
                        .HasColumnType("int");

                    b.Property<string>("NomeColaborador")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("PrimeiroNomeCol")
                        .HasColumnType("nvarchar(max)");

                    b.Property<int>("TelefoneCol")
                        .HasColumnType("int");

                    b.Property<string>("UltimoNomeCol")
                        .HasColumnType("nvarchar(max)");

                    b.Property<int>("ccColaborador")
                        .HasColumnType("int");

                    b.HasKey("IdColaborador");

                    b.HasIndex("IdCargo");

                    b.ToTable("ColaboradorModels");
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

                    b.Property<string>("NomeEPI")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("IdEPI");

                    b.ToTable("EPIModels");
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

                    b.Property<int>("IdEPI")
                        .HasColumnType("int");

                    b.Property<string>("TypeMov")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("IdMovimento");

                    b.HasIndex("IdColaborador");

                    b.HasIndex("IdEPI");

                    b.ToTable("MovimentoModels");
                });

            modelBuilder.Entity("EvitarBackEnd.Models.ColaboradorModel", b =>
                {
                    b.HasOne("EvitarBackEnd.Models.CargoModel", "IdCargoForeignKey")
                        .WithMany()
                        .HasForeignKey("IdCargo")
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

                    b.HasOne("EvitarBackEnd.Models.EPIModel", "IdEPIForeignKey")
                        .WithMany()
                        .HasForeignKey("IdEPI")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });
#pragma warning restore 612, 618
        }
    }
}
