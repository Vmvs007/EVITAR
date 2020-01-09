using Microsoft.EntityFrameworkCore.Migrations;

namespace EvitarBackEnd.Migrations
{
    public partial class MigrationDataBase_v6 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddForeignKey(
                name: "FK_ColaboradorModels_CargoModels_IdCargo",
                table: "ColaboradorModels",
                column: "IdCargo",
                principalTable: "CargoModels",
                principalColumn: "IdCargo",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {

        }
    }
}
