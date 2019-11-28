using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace EvitarBackEnd.Migrations
{
    public partial class MigrationDataBase : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "CargoModels",
                columns: table => new
                {
                    IdCargo = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    TypeCargo = table.Column<string>(nullable: true),
                    NomeCargo = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CargoModels", x => x.IdCargo);
                });

            migrationBuilder.CreateTable(
                name: "EPIModels",
                columns: table => new
                {
                    IdEPI = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    NomeEPI = table.Column<string>(nullable: true),
                    DataRegistoEPI = table.Column<DateTime>(nullable: false),
                    DataValidadeEPI = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_EPIModels", x => x.IdEPI);
                });

            migrationBuilder.CreateTable(
                name: "ColaboradorModels",
                columns: table => new
                {
                    IdColaborador = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    NomeColaborador = table.Column<string>(nullable: true),
                    DataNasc = table.Column<DateTime>(nullable: false),
                    ccColaborador = table.Column<int>(nullable: false),
                    NifColaborador = table.Column<int>(nullable: false),
                    GeneroCol = table.Column<string>(nullable: true),
                    TelefoneCol = table.Column<int>(nullable: false),
                    MoradaCol = table.Column<string>(nullable: true),
                    EmailCol = table.Column<string>(nullable: true),
                    DataRegistoCol = table.Column<DateTime>(nullable: false),
                    IdCargo = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ColaboradorModels", x => x.IdColaborador);
                    table.ForeignKey(
                        name: "FK_ColaboradorModels_CargoModels_IdCargo",
                        column: x => x.IdCargo,
                        principalTable: "CargoModels",
                        principalColumn: "IdCargo",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "MovimentoModels",
                columns: table => new
                {
                    IdMovimento = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    TypeMov = table.Column<string>(nullable: true),
                    IdColaborador = table.Column<int>(nullable: false),
                    Check = table.Column<int>(nullable: false),
                    DataHora = table.Column<DateTime>(nullable: false),
                    IdEPI = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_MovimentoModels", x => x.IdMovimento);
                    table.ForeignKey(
                        name: "FK_MovimentoModels_ColaboradorModels_IdColaborador",
                        column: x => x.IdColaborador,
                        principalTable: "ColaboradorModels",
                        principalColumn: "IdColaborador",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_MovimentoModels_EPIModels_IdEPI",
                        column: x => x.IdEPI,
                        principalTable: "EPIModels",
                        principalColumn: "IdEPI",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ColaboradorModels_IdCargo",
                table: "ColaboradorModels",
                column: "IdCargo");

            migrationBuilder.CreateIndex(
                name: "IX_MovimentoModels_IdColaborador",
                table: "MovimentoModels",
                column: "IdColaborador");

            migrationBuilder.CreateIndex(
                name: "IX_MovimentoModels_IdEPI",
                table: "MovimentoModels",
                column: "IdEPI");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "MovimentoModels");

            migrationBuilder.DropTable(
                name: "ColaboradorModels");

            migrationBuilder.DropTable(
                name: "EPIModels");

            migrationBuilder.DropTable(
                name: "CargoModels");
        }
    }
}
