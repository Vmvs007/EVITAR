using System;
using System.ComponentModel.DataAnnotations;

namespace EvitarBackEnd.Models
{
    public class EPIModelView
    {
        [Key]
        public long IdEPI {get;set;}
        public string NomeEPI {get;set;}
        public DateTime DataRegistoEPI {get;set;}
        public DateTime DataValidadeEPI {get;set;}
        public int Valido{get;set;}
        public int IdTipoEPI {get;set;}
        public String NomeTipoEPI {get;set;}
        public long IdColaborador {get;set;}
        public String NomeInspector {get;set;}
    }
}