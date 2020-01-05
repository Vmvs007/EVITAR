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
    [Route("api/Movimento")]
    [ApiController]
    public class MovimentoController : ControllerBase
    {
        private readonly EVITARContext _context;

        public MovimentoController(EVITARContext context)
        {
            _context = context;
        }

        // GET: api/Movimento
        [Authorize] //Podem todos ver desde que estejam autenticados
        [HttpGet]
        public async Task<ActionResult<IEnumerable<MovimentoModel>>> GetMovimentoModels()
        {

            var movimentoModelAux = await _context.MovimentoModels.ToListAsync();

            var movimentoModel = movimentoModelAux.OrderByDescending(x=>x.IdMovimento).ToList();

            return movimentoModel;
        }

        // GET: api/Movimento/5
        [Authorize] //Podem todos ver desde que estejam autenticados 
        [HttpGet("{id}")]
        public async Task<ActionResult<MovimentoModel>> GetMovimentoModel(int id)
        {
            var movimentoModel = await _context.MovimentoModels.FindAsync(id);

            if (movimentoModel == null)
            {
                return NotFound();
            }

            return movimentoModel;
        }


        [Route("view")]
        [Authorize] //Podem todos ver desde que estejam autenticados 
        [HttpGet]
        public async Task<ActionResult<IEnumerable<MovimentoModelView>>> GetMovimentoModelView(int id)
        {
          
            var movimentoModelAux = await _context.MovimentoModelViews.ToListAsync();

            if (movimentoModelAux == null)
            {
                return NotFound();
            }

            var movimentoModel = movimentoModelAux.OrderByDescending(x=>x.IdMovimento).ToList();

            return movimentoModel;

        }

        [Route("viewVal")]
        [Authorize] //Podem todos ver desde que estejam autenticados 
        [HttpGet]
        public async Task<ActionResult<IEnumerable<MovimentoModelView>>> GetMovimentoModelViewVal(int id)
        {
          
            var movimentoModelAux = await _context.MovimentoModelViews.ToListAsync();

            if (movimentoModelAux == null)
            {
                return NotFound();
            }

            var movimentoModel = movimentoModelAux.OrderByDescending(x=>x.Check).ThenByDescending(y=>y.IdMovimento).ToList();

            return movimentoModel;

        }

        
        [Authorize] //Podem todos ver desde que estejam autenticados 
        [Route("alert/{data}")]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<MovimentoModelView>>> GetMovimentoModelAlert(DateTime data)
        {
           
            var movimentoModel = await _context.MovimentoModelViews.ToListAsync();

            if (movimentoModel == null)
            {
                return NotFound();
            }
        
            var movimentoAlert = (from x in movimentoModel where (x.DataHora).Date == data && x.Check==0 select x).ToList();
            var novo = movimentoAlert.OrderByDescending(x=>x.IdMovimento).ToList();
            return novo;
        }

         
        [Authorize] //Podem todos ver desde que estejam autenticados 
        [Route("Entradas/{data}")]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<MovimentoModelView>>> GetMovimentoModelEntradas(DateTime data)
        {
           
            var movimentoModel = await _context.MovimentoModelViews.ToListAsync();

            if (movimentoModel == null)
            {
                return NotFound();
            }
        
            var movimentoAlert = (from x in movimentoModel where (x.DataHora).Date == data select x).ToList();
            var novo = movimentoAlert.OrderByDescending(x=>x.IdMovimento).ToList();
            return novo;
        }

        [Authorize] //Podem todos ver desde que estejam autenticados 
        [Route("Stats/{data}")]
        [HttpGet]
        public async Task<IActionResult> GetMovimentoModelEntradasDiarias(DateTime data)
        {
            
            var movimentoModel = await _context.MovimentoModelViews.ToListAsync();

            DateTime data1 = data.AddDays(-7);

            if (movimentoModel == null)
            {
                return BadRequest(new { message = "Sem moviemntos" }); 
            }


            var movimentosWeek = (from x in movimentoModel where x.TypeMov == "E" && (x.DataHora).Date <=data && (x.DataHora).Date >=data1 select ( x.IdColaborador, (x.DataHora).Date)).ToList();
            var movimentosDay = (from x in movimentoModel where x.TypeMov == "E" && (x.DataHora).Date == data select ( x.IdColaborador)).ToList();
            var movimentosAlert = (from x in movimentoModel where x.TypeMov == "E" && (x.DataHora).Date == data && x.Check == 0 select ( x.IdMovimento)).ToList();

            var movimentoWeek = movimentosWeek.Distinct().Count();
            var movimentoDay = movimentosDay.Distinct().Count();
            var movimentoAlert = movimentosAlert.Distinct().Count(); 
            
             return Ok(new
                 {
                    AlertasDiarios = movimentoAlert,
                    MovimentosDiarios = movimentoDay,
                    MovimentosSemanais = movimentoWeek
                    });
        }

        
        [Authorize] //Podem todos ver desde que estejam autenticados 
        [Route("NumeroWarnigs/{data}")]
        [HttpGet]
        public async Task<IActionResult> GetNumerodeWarnigs6Meses(DateTime data)
        {
            DateTime data1 = data.AddMonths(-1);
            DateTime data2 = data.AddMonths(-2);
            DateTime data3 = data.AddMonths(-3);
            DateTime data4 = data.AddMonths(-4);
            DateTime data5 = data.AddMonths(-5);
            DateTime data6 = data.AddMonths(-6);

           
            var movimentoModel = await _context.MovimentoModelViews.ToListAsync();

            if (movimentoModel == null)
            {
                return  BadRequest(new { message = "Sem moviemntos" });
            }

             var movimentosData = (from x in movimentoModel where x.TypeMov == "E" && x.Check == 0 && (x.DataHora).Date <=data && (x.DataHora).Date >data1 select x.IdMovimento).ToList();
             var movimentosData1 = (from x in movimentoModel where x.TypeMov == "E" && x.Check == 0 && (x.DataHora).Date <=data1 && (x.DataHora).Date >data2 select x.IdMovimento).ToList();
             var movimentosData2 = (from x in movimentoModel where x.TypeMov == "E" && x.Check == 0 && (x.DataHora).Date <=data2 && (x.DataHora).Date >data3 select x.IdMovimento).ToList();
             var movimentosData3 = (from x in movimentoModel where x.TypeMov == "E" && x.Check == 0 && (x.DataHora).Date <=data3 && (x.DataHora).Date >data4 select x.IdMovimento).ToList();
             var movimentosData4 = (from x in movimentoModel where x.TypeMov == "E" && x.Check == 0 && (x.DataHora).Date <=data4 && (x.DataHora).Date >data5 select x.IdMovimento).ToList();
             var movimentosData5 = (from x in movimentoModel where x.TypeMov == "E" && x.Check == 0 && (x.DataHora).Date <=data5 && (x.DataHora).Date >=data6 select x.IdMovimento).ToList();

            var movimentosCountData = movimentosData.Count();
            var movimentosCountData1 = movimentosData1.Count();
            var movimentosCountData2 = movimentosData2.Count();
            var movimentosCountData3 = movimentosData3.Count();
            var movimentosCountData4 = movimentosData4.Count();
            var movimentosCountData5 = movimentosData5.Count();
            
            return Ok(new
                 {
                    mes = movimentosCountData,
                    mes1 = movimentosCountData1,
                    mes2 = movimentosCountData2,
                    mes3 = movimentosCountData3,
                    mes4 = movimentosCountData4,
                    mes5 = movimentosCountData5,
                    });
        }
        
        
        // PUT: api/Movimento/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
       
        [Authorize(Roles = "1")]//Só podem editar os Admins
        [HttpPut("{id}")]
        public async Task<IActionResult> PutMovimentoModel(int id, MovimentoModel movimentoModel)
        {
            if (id != movimentoModel.IdMovimento)
            {
                return BadRequest();
            }

            _context.Entry(movimentoModel).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!MovimentoModelExists(id))
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

        // POST: api/Movimento
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize]//Adicionar Movimento, pode toda a gente (Temos que rever)
        [HttpPost]
        public async Task<ActionResult<MovimentoModel>> PostMovimentoModel(MovimentoModel movimentoModel)
        {
            _context.MovimentoModels.Add(movimentoModel);
            await _context.SaveChangesAsync();
            

            return CreatedAtAction(nameof(GetMovimentoModel), new { id = movimentoModel.IdMovimento }, movimentoModel);
        }

        // DELETE: api/Movimento/5
        [Authorize(Roles = "1")]//Só podem apagar Admins
        [HttpDelete("{id}")]
        public async Task<ActionResult<MovimentoModel>> DeleteMovimentoModel(int id)
        {
            var movimentoModel = await _context.MovimentoModels.FindAsync(id);
            if (movimentoModel == null)
            {
                return NotFound();
            }

            _context.MovimentoModels.Remove(movimentoModel);
            await _context.SaveChangesAsync();

            return movimentoModel;
        }

        private bool MovimentoModelExists(int id)
        {
            return _context.MovimentoModels.Any(e => e.IdMovimento == id);
        }
    }
}
