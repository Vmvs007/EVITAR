using Microsoft.EntityFrameworkCore.Migrations;

namespace EvitarBackEnd.Migrations
{
    public partial class MigrationDataBase_v4 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_EPICargoModels_EPIModels_IdEPI",
                table: "EPICargoModels");

            migrationBuilder.DropPrimaryKey(
                name: "PK_EPICargoModels",
                table: "EPICargoModels");

            migrationBuilder.DropIndex(
                name: "IX_EPICargoModels_IdEPI",
                table: "EPICargoModels");

            migrationBuilder.DropColumn(
                name: "IdEPI",
                table: "EPICargoModels");

            migrationBuilder.AlterColumn<string>(
                name: "NomeEPI",
                table: "EPIModels",
                maxLength: 100,
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(2)",
                oldMaxLength: 2,
                oldNullable: true);

            migrationBuilder.AddColumn<int>(
                name: "IdTipoEPI",
                table: "EPICargoModels",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<string>(
                name: "UltimoNomeCol",
                table: "ColaboradorModels",
                maxLength: 50,
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "PrimeiroNomeCol",
                table: "ColaboradorModels",
                maxLength: 50,
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "NomeColaborador",
                table: "ColaboradorModels",
                maxLength: 150,
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "MoradaCol",
                table: "ColaboradorModels",
                maxLength: 150,
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "ZonaCargo",
                table: "CargoModels",
                maxLength: 100,
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "NomeCargo",
                table: "CargoModels",
                maxLength: 100,
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AddPrimaryKey(
                name: "PK_EPICargoModels",
                table: "EPICargoModels",
                columns: new[] { "IdCargo", "IdTipoEPI" });

            migrationBuilder.CreateTable(
                name: "TipoEPIModels",
                columns: table => new
                {
                    IdTipoEPI = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    NomeTipoEPI = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TipoEPIModels", x => x.IdTipoEPI);
                });

            migrationBuilder.CreateIndex(
                name: "IX_EPICargoModels_IdTipoEPI",
                table: "EPICargoModels",
                column: "IdTipoEPI");

            migrationBuilder.AddForeignKey(
                name: "FK_EPICargoModels_TipoEPIModels_IdTipoEPI",
                table: "EPICargoModels",
                column: "IdTipoEPI",
                principalTable: "TipoEPIModels",
                principalColumn: "IdTipoEPI",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_EPICargoModels_TipoEPIModels_IdTipoEPI",
                table: "EPICargoModels");

            migrationBuilder.DropTable(
                name: "TipoEPIModels");

            migrationBuilder.DropPrimaryKey(
                name: "PK_EPICargoModels",
                table: "EPICargoModels");

            migrationBuilder.DropIndex(
                name: "IX_EPICargoModels_IdTipoEPI",
                table: "EPICargoModels");

            migrationBuilder.DropColumn(
                name: "IdTipoEPI",
                table: "EPICargoModels");

            migrationBuilder.AlterColumn<string>(
                name: "NomeEPI",
                table: "EPIModels",
                type: "nvarchar(2)",
                maxLength: 2,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 100,
                oldNullable: true);

            migrationBuilder.AddColumn<int>(
                name: "IdEPI",
                table: "EPICargoModels",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<string>(
                name: "UltimoNomeCol",
                table: "ColaboradorModels",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 50,
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "PrimeiroNomeCol",
                table: "ColaboradorModels",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 50,
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "NomeColaborador",
                table: "ColaboradorModels",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 150,
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "MoradaCol",
                table: "ColaboradorModels",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 150,
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "ZonaCargo",
                table: "CargoModels",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 100,
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "NomeCargo",
                table: "CargoModels",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 100,
                oldNullable: true);

            migrationBuilder.AddPrimaryKey(
                name: "PK_EPICargoModels",
                table: "EPICargoModels",
                columns: new[] { "IdCargo", "IdEPI" });

            migrationBuilder.CreateIndex(
                name: "IX_EPICargoModels_IdEPI",
                table: "EPICargoModels",
                column: "IdEPI");

            migrationBuilder.AddForeignKey(
                name: "FK_EPICargoModels_EPIModels_IdEPI",
                table: "EPICargoModels",
                column: "IdEPI",
                principalTable: "EPIModels",
                principalColumn: "IdEPI",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
