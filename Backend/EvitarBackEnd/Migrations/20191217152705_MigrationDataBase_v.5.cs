using Microsoft.EntityFrameworkCore.Migrations;

namespace EvitarBackEnd.Migrations
{
    public partial class MigrationDataBase_v5 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "IdTipoEPI",
                table: "EPIModels",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_EPIModels_IdTipoEPI",
                table: "EPIModels",
                column: "IdTipoEPI");

            migrationBuilder.AddForeignKey(
                name: "FK_EPIModels_TipoEPIModels_IdTipoEPI",
                table: "EPIModels",
                column: "IdTipoEPI",
                principalTable: "TipoEPIModels",
                principalColumn: "IdTipoEPI",
                onDelete: ReferentialAction.NoAction);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_EPIModels_TipoEPIModels_IdTipoEPI",
                table: "EPIModels");

            migrationBuilder.DropIndex(
                name: "IX_EPIModels_IdTipoEPI",
                table: "EPIModels");

            migrationBuilder.DropColumn(
                name: "IdTipoEPI",
                table: "EPIModels");
        }
    }
}
