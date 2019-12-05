using Microsoft.EntityFrameworkCore.Migrations;

namespace EvitarBackEnd.Migrations
{
    public partial class MigrationDataBase_v2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "NomeEPI",
                table: "EPIModels",
                maxLength: 2,
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "NomeEPI",
                table: "EPIModels",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 2,
                oldNullable: true);
        }
    }
}
