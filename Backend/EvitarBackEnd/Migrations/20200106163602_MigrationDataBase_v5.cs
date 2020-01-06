using Microsoft.EntityFrameworkCore.Migrations;

namespace EvitarBackEnd.Migrations
{
    public partial class MigrationDataBase_v5 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {

            migrationBuilder.DropForeignKey(
            name: "FK_MovimentoModels_ColaboradorModels_IdColaborador",
            table: "MovimentoModels");

            migrationBuilder.DropForeignKey(
            name: "FK_Users_ColaboradorModels_IdColaborador",
            table: "Users");

            migrationBuilder.DropForeignKey(
            name: "FK_EPIModels_ColaboradorModels_IdColaborador",
            table: "EPIModels");

            migrationBuilder.DropPrimaryKey(
            name: "PK_EPIModels",
            table: "EPIModels");

             migrationBuilder.DropPrimaryKey(
            name: "PK_ColaboradorModels",
            table: "ColaboradorModels");


            migrationBuilder.AlterColumn<long>(
                name: "IdColaborador",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<long>(
                name: "IdColaborador",
                table: "MovimentoModels",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<long>(
                name: "IdColaborador",
                table: "EPIModels",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<long>(
                name: "IdEPI",
                table: "EPIModels",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int")
                .Annotation("SqlServer:Identity", "1, 1")
                .OldAnnotation("SqlServer:Identity", "1, 1");

            migrationBuilder.AlterColumn<long>(
                name: "IdColaborador",
                table: "ColaboradorModels",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int")
                .Annotation("SqlServer:Identity", "1, 1")
                .OldAnnotation("SqlServer:Identity", "1, 1");


                migrationBuilder.AddPrimaryKey(
                name: "PK_EPIModels",
                table: "EPIModels",
                column: "IdEPI");

                
                migrationBuilder.AddPrimaryKey(
                name: "PK_ColaboradorModels",
                table: "ColaboradorModels",
                column: "IdColaborador");

                migrationBuilder.AddForeignKey(
                name: "FK_EPIModels_ColaboradorModels_IdColaborador",
                table: "EPIModels",
                column: "IdColaborador",
                principalTable: "ColaboradorModels",
                principalColumn: "IdColaborador",
                onDelete: ReferentialAction.Restrict);

                 migrationBuilder.AddForeignKey(
                name: "FK_Users_ColaboradorModels_IdColaborador",
                table: "Users",
                column: "IdColaborador",
                principalTable: "ColaboradorModels",
                principalColumn: "IdColaborador",
                onDelete: ReferentialAction.Restrict);

                 migrationBuilder.AddForeignKey(
                name: "FK_MovimentoModels_ColaboradorModels_IdColaborador",
                table: "MovimentoModels",
                column: "IdColaborador",
                principalTable: "ColaboradorModels",
                principalColumn: "IdColaborador",
                onDelete: ReferentialAction.Restrict);
 


        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "IdColaborador",
                table: "Users",
                type: "int",
                nullable: false,
                oldClrType: typeof(long));

            migrationBuilder.AlterColumn<int>(
                name: "IdColaborador",
                table: "MovimentoModels",
                type: "int",
                nullable: false,
                oldClrType: typeof(long));

            migrationBuilder.AlterColumn<int>(
                name: "IdColaborador",
                table: "EPIModels",
                type: "int",
                nullable: false,
                oldClrType: typeof(long));

            migrationBuilder.AlterColumn<int>(
                name: "IdEPI",
                table: "EPIModels",
                type: "int",
                nullable: false,
                oldClrType: typeof(long))
                .Annotation("SqlServer:Identity", "1, 1")
                .OldAnnotation("SqlServer:Identity", "1, 1");

            migrationBuilder.AlterColumn<int>(
                name: "IdColaborador",
                table: "ColaboradorModels",
                type: "int",
                nullable: false,
                oldClrType: typeof(long))
                .Annotation("SqlServer:Identity", "1, 1")
                .OldAnnotation("SqlServer:Identity", "1, 1");
        }
    }
}
