using Microsoft.EntityFrameworkCore.Migrations;

namespace EvitarBackEnd.Migrations
{
    public partial class Mg2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "PrimeiroNomeCol",
                table: "ColaboradorModels",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "UltimoNomeCol",
                table: "ColaboradorModels",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "ZonaCargo",
                table: "CargoModels",
                nullable: true);

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
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_EPICargoModels_IdEPI",
                table: "EPICargoModels",
                column: "IdEPI");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "EPICargoModels");

            migrationBuilder.DropColumn(
                name: "PrimeiroNomeCol",
                table: "ColaboradorModels");

            migrationBuilder.DropColumn(
                name: "UltimoNomeCol",
                table: "ColaboradorModels");

            migrationBuilder.DropColumn(
                name: "ZonaCargo",
                table: "CargoModels");
        }
    }
}
