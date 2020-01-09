using Microsoft.EntityFrameworkCore.Migrations;

namespace EvitarBackEnd.Migrations
{
    public partial class MigrationDataBase_v4 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_MovEPIModels_EPIModels_IdEPI",
                table: "MovEPIModels");

            migrationBuilder.DropPrimaryKey(
                name: "PK_MovEPIModels",
                table: "MovEPIModels");

            migrationBuilder.DropIndex(
                name: "IX_MovEPIModels_IdEPI",
                table: "MovEPIModels");

            migrationBuilder.DropColumn(
                name: "IdEPI",
                table: "MovEPIModels");

            migrationBuilder.AddColumn<int>(
                name: "IdTipoEPI",
                table: "MovEPIModels",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddPrimaryKey(
                name: "PK_MovEPIModels",
                table: "MovEPIModels",
                columns: new[] { "IdMovimento", "IdTipoEPI" });

            migrationBuilder.CreateIndex(
                name: "IX_MovEPIModels_IdTipoEPI",
                table: "MovEPIModels",
                column: "IdTipoEPI");

            migrationBuilder.AddForeignKey(
                name: "FK_MovEPIModels_TipoEPIModels_IdTipoEPI",
                table: "MovEPIModels",
                column: "IdTipoEPI",
                principalTable: "TipoEPIModels",
                principalColumn: "IdTipoEPI",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_MovEPIModels_TipoEPIModels_IdTipoEPI",
                table: "MovEPIModels");

            migrationBuilder.DropPrimaryKey(
                name: "PK_MovEPIModels",
                table: "MovEPIModels");

            migrationBuilder.DropIndex(
                name: "IX_MovEPIModels_IdTipoEPI",
                table: "MovEPIModels");

            migrationBuilder.DropColumn(
                name: "IdTipoEPI",
                table: "MovEPIModels");

            migrationBuilder.AddColumn<int>(
                name: "IdEPI",
                table: "MovEPIModels",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddPrimaryKey(
                name: "PK_MovEPIModels",
                table: "MovEPIModels",
                columns: new[] { "IdMovimento", "IdEPI" });

            migrationBuilder.CreateIndex(
                name: "IX_MovEPIModels_IdEPI",
                table: "MovEPIModels",
                column: "IdEPI");

            migrationBuilder.AddForeignKey(
                name: "FK_MovEPIModels_EPIModels_IdEPI",
                table: "MovEPIModels",
                column: "IdEPI",
                principalTable: "EPIModels",
                principalColumn: "IdEPI",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
