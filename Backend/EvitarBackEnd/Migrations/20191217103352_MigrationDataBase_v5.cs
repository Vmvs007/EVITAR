using Microsoft.EntityFrameworkCore.Migrations;

namespace EvitarBackEnd.Migrations
{
    public partial class MigrationDataBase_v5 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "valido",
                table: "EPIModels",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "valido",
                table: "EPIModels");
        }
    }
}
