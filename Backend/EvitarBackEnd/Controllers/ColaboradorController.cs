using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using EvitarBackEnd.Models;
using Microsoft.AspNetCore.Authorization;

namespace EvitarBackEnd.Controllers
{
    
    [Route("api/Colaborador")]
    [ApiController]
    public class ColaboradorController : ControllerBase
    {
        private readonly EVITARContext _context;

        public ColaboradorController(EVITARContext context)
        {
            _context = context;
        }
        

        // GET: api/Colaborador
        [Authorize]//Toda a gente pode ver os colaboradores
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ColaboradorModel>>> GetColaboradorModel()
        {
            return await _context.ColaboradorModels.ToListAsync();
        }
        
        // GET: api/Colaborador/5
        [Authorize]//Toda a gente pode ver os colaboradores
        [HttpGet("{idColaborador}")]
        public async Task<ActionResult<ColaboradorModel>> GetColaboradorModel(int idColaborador)
        {
            var colaboradorModel = await _context.ColaboradorModels.FindAsync(idColaborador);

            if (colaboradorModel == null)
            {
                return NotFound();
            }

            return colaboradorModel;
        }
       
        // GET: api/Colaborador/5/epi1&ep
        [Authorize]//Toda a gente pode ver os colaboradores
        [Route("sensor/{idColaborador}")]
        [HttpGet]
        public async Task<List<String>> GetEntradaColaborador(int idColaborador, [FromQuery] int [] idEPIs)
        {
            List<String> Retorno=new List<String>();
            var colaboradorModel = await _context.ColaboradorModels.FindAsync(idColaborador);

            if (colaboradorModel == null)
            {
                return null;
            }

            //Criação do Movimento da Entrada do Colaborador
               MovimentoModel movimentoModel=new MovimentoModel();
               movimentoModel.IdColaborador=idColaborador;
               movimentoModel.DataHora=DateTime.Now;
               movimentoModel.TypeMov="E";
               movimentoModel.Check=1;
               _context.MovimentoModels.Add(movimentoModel);
               await _context.SaveChangesAsync();
               
               Retorno.Add("Bem Vindo,"+colaboradorModel.PrimeiroNomeCol+" "+colaboradorModel.UltimoNomeCol+",está tudo correcto");
                            

               
               var epiNecessarios= await _context.EPICargoNecModelViews.ToListAsync();
               
               //Criação da query para verificar quais os epis necessarios para o cargo do Colaborador
               
               var epiNecessariosQuery = (from x in epiNecessarios where x.IdCargo == colaboradorModel.IdCargo  select x.IdTipoEpi).ToList();

            
               var epiNecessariosFinal=epiNecessariosQuery.ToArray();

               //Comparação dos epis necessarios e os epis que passaram no sensor

               var Compare=idEPIs.SequenceEqual(epiNecessariosFinal);
               if(Compare==true){
                   return Retorno.Distinct().ToList();
               }

               else if(Compare==false){
                   MovEPIModel movEPI=new MovEPIModel();
                   for(int i=0; i<epiNecessariosFinal.Length;i++){
                       if(idEPIs.ToList().Contains(epiNecessariosFinal[i])==false){
                           var epimodel= await _context.TipoEPIModels.FindAsync(epiNecessariosFinal[i]);
                           Retorno.Remove("Bem Vindo,"+colaboradorModel.PrimeiroNomeCol+" "+colaboradorModel.UltimoNomeCol+",está tudo correcto");
                           Retorno.Add("Bem Vindo,"+colaboradorModel.PrimeiroNomeCol+" "+colaboradorModel.UltimoNomeCol);
                           Retorno.Add("Falta:"+epimodel.NomeTipoEPI.ToString());

                           //Criação do MovEPI devido a epi em falta
                           movEPI.IdMovimento=movimentoModel.IdMovimento;
                           movEPI.IdEPI=epiNecessariosFinal[i];
                           _context.MovEPIModels.Add(movEPI);
                           await _context.SaveChangesAsync();

                       }
                    }
               }
               
         return Retorno.Distinct().ToList(); //colaboradorModel.PrimeiroNomeCol.ToString();
        }
        
        // PUT: api/Colaborador/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize(Roles = "1, 2")]//Pode editar Colaboradores Admins e RH
        [HttpPut("{id}")]
        public async Task<IActionResult> PutColaboradorModel(int id, ColaboradorModel colaboradorModel)
        {
            if (id != colaboradorModel.IdColaborador)
            {
                return BadRequest();
            }

            _context.Entry(colaboradorModel).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ColaboradorModelExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Colaborador
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize(Roles = "1, 2")]//Pode adicionar colaboradores os Admins e RH
        [HttpPost]
        public async Task<ActionResult<ColaboradorModel>> PostColaboradorModel(ColaboradorModel colaboradorModel)
        {
            _context.ColaboradorModels.Add(colaboradorModel);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetColaboradorModel), new { id = colaboradorModel.IdColaborador }, colaboradorModel);
        }

        // DELETE: api/Colaborador/5
        [Authorize(Roles = "1, 2")]//Pode remover colaborador os Admins e o RH
        [HttpDelete("{id}")]
        public async Task<ActionResult<ColaboradorModel>> DeleteColaboradorModel(int id)
        {
            
            var colaboradorModel = await _context.ColaboradorModels.FindAsync(id);
            if (colaboradorModel == null)
            {
                return NotFound();
            }

            _context.ColaboradorModels.Remove(colaboradorModel);
            await _context.SaveChangesAsync();

            return colaboradorModel;
        }

        private bool ColaboradorModelExists(int id)
        {
            return _context.ColaboradorModels.Any(e => e.IdColaborador == id);
        }

        [Route("View")]
        [Authorize] //Podem todos ver desde que estejam autenticados 
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ColaboradorModelView>>> GetColaboradorModelView()
        {

            var colaboradorModel = await _context.ColaboradorModelViews.ToListAsync();

            if (colaboradorModel == null)
            {
                return NotFound();
            }

            return colaboradorModel;
        }
    }
}
