
using System.Globalization;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema; 

namespace EvitarBackEnd.Models
{
    public class ColaboradorModel{
        [Key]
        public long IdColaborador {get;set;}
        
        [StringLength(150)]
        public string NomeColaborador {get;set;}
        [StringLength(50)]
        public string PrimeiroNomeCol {get;set;}
        [StringLength(50)]
        public string UltimoNomeCol {get;set;}
        public DateTime DataNasc {get;set;}

        [RegularExpression(@"[0-9]{8}")]
        public int ccColaborador {get;set;}

        [RegularExpression(@"[1-9][0-9]{8}")]
        public int NifColaborador {get;set;}
        [RegularExpression(@"^M$|^F$")]
        public String GeneroCol {get;set;}
        [RegularExpression(@"9[1236][0-9]{7}|2[1-9][0-9]{7}")]
        public int TelefoneCol {get;set;}
        [StringLength(150)]
        public string MoradaCol {get;set;}
        [DataType(DataType.EmailAddress)]
        public string EmailCol {get;set;}
        public DateTime DataRegistoCol {get;set;}

        public int IdCargo {get;set;}
        [System.ComponentModel.DataAnnotations.Schema.ForeignKey("IdCargo")]
        public CargoModel IdCargoForeignKey {get;set;}
    }
}