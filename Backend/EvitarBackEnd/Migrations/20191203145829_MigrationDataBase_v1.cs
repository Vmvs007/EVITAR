using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace EvitarBackEnd.Migrations
{
    public partial class MigrationDataBase_v1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "CargoModels",
                columns: table => new
                {
                    IdCargo = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    NomeCargo = table.Column<string>(nullable: true),
                    ZonaCargo = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CargoModels", x => x.IdCargo);
                });

            migrationBuilder.CreateTable(
                name: "ColaboradorModels",
                columns: table => new
                {
                    IdColaborador = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    NomeColaborador = table.Column<string>(nullable: true),
                    PrimeiroNomeCol = table.Column<string>(nullable: true),
                    UltimoNomeCol = table.Column<string>(nullable: true),
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
                name: "EPIModels",
                columns: table => new
                {
                    IdEPI = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    NomeEPI = table.Column<string>(nullable: true),
                    DataRegistoEPI = table.Column<DateTime>(nullable: false),
                    DataValidadeEPI = table.Column<DateTime>(nullable: false),
                    IdColaborador = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_EPIModels", x => x.IdEPI);
                    table.ForeignKey(
                        name: "FK_EPIModels_ColaboradorModels_IdColaborador",
                        column: x => x.IdColaborador,
                        principalTable: "ColaboradorModels",
                        principalColumn: "IdColaborador",
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
                    DataHora = table.Column<DateTime>(nullable: false)
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
                });

            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Username = table.Column<string>(maxLength: 50, nullable: true),
                    IdColaborador = table.Column<int>(nullable: false),
                    PasswordHash = table.Column<byte[]>(nullable: true),
                    PasswordSalt = table.Column<byte[]>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Users_ColaboradorModels_IdColaborador",
                        column: x => x.IdColaborador,
                        principalTable: "ColaboradorModels",
                        principalColumn: "IdColaborador",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "EPICargoModels",
                columns: table => new
                {
                    IdCargo = table.Column<int>(nullable: false),
                    IdEPI = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_EPICargoModels", x => new { x.IdCargo, x.IdEPI });
                    table.ForeignKey(
                        name: "FK_EPICargoModels_CargoModels_IdCargo",
                        column: x => x.IdCargo,
                        principalTable: "CargoModels",
                        principalColumn: "IdCargo",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_EPICargoModels_EPIModels_IdEPI",
                        column: x => x.IdEPI,
                        principalTable: "EPIModels",
                        principalColumn: "IdEPI",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateTable(
                name: "MovEPIModels",
                columns: table => new
                {
                    IdMovimento = table.Column<int>(nullable: false),
                    IdEPI = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_MovEPIModels", x => new { x.IdMovimento, x.IdEPI });
                    table.ForeignKey(
                        name: "FK_MovEPIModels_EPIModels_IdEPI",
                        column: x => x.IdEPI,
                        principalTable: "EPIModels",
                        principalColumn: "IdEPI",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_MovEPIModels_MovimentoModels_IdMovimento",
                        column: x => x.IdMovimento,
                        principalTable: "MovimentoModels",
                        principalColumn: "IdMovimento",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ColaboradorModels_IdCargo",
                table: "ColaboradorModels",
                column: "IdCargo");

            migrationBuilder.CreateIndex(
                name: "IX_EPICargoModels_IdEPI",
                table: "EPICargoModels",
                column: "IdEPI");

            migrationBuilder.CreateIndex(
                name: "IX_EPIModels_IdColaborador",
                table: "EPIModels",
                column: "IdColaborador");

            migrationBuilder.CreateIndex(
                name: "IX_MovEPIModels_IdEPI",
                table: "MovEPIModels",
                column: "IdEPI");

            migrationBuilder.CreateIndex(
                name: "IX_MovimentoModels_IdColaborador",
                table: "MovimentoModels",
                column: "IdColaborador");

            migrationBuilder.CreateIndex(
                name: "IX_Users_IdColaborador",
                table: "Users",
                column: "IdColaborador");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "EPICargoModels");

            migrationBuilder.DropTable(
                name: "MovEPIModels");

            migrationBuilder.DropTable(
                name: "Users");

            migrationBuilder.DropTable(
                name: "EPIModels");

            migrationBuilder.DropTable(
                name: "MovimentoModels");

            migrationBuilder.DropTable(
                name: "ColaboradorModels");

            migrationBuilder.DropTable(
                name: "CargoModels");
        }
    }
}
