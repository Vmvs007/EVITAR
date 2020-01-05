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
            return await _context.MovimentoModels.ToListAsync();
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

        // GET: api/Movimento/5
        [Route("search")]
        [Authorize] //Podem todos ver desde que estejam autenticados 
        [HttpGet]
        public async Task<ActionResult<IEnumerable<MovimentoModel>>> GetMovimentoModelData(int id)
        {
            // var movimentoModel = await _context.MovimentoModels.FromSql("SELECT * From dbo.MovimentoModels WHERE DataHora > 01/11/2019 and DataHora < 31/11/2019 ").ToList();
            var DB = await _context.MovimentoModels.ToListAsync();
            DateTime dataI = new DateTime(2019, 11, 01);
            DateTime dataF = new DateTime(2019, 11, 30);

            var movimentoModelAux = from x in DB where x.DataHora > dataI && x.DataHora < dataF select x;
            var movimentoModel = movimentoModelAux.ToList();

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
            // var movimentoModel = await _context.MovimentoModels.FromSql("SELECT * From dbo.MovimentoModels WHERE DataHora > 01/11/2019 and DataHora < 31/11/2019 ").ToList();
           // _context.Database.ExecuteSqlRaw(@"CREATE VIEW MovimentoModelViews AS
            //SELECT m.IdMovimento,m.TypeMov,m.IdColaborador,m.[Check],m.DataHora,c.PrimeiroNomeCol,c.UltimoNomeCol
            //FROM MovimentoModels m,ColaboradorModels c
            //WHERE c.IdColaborador=m.IdColaborador");

            var movimentoModel = await _context.MovimentoModelViews.ToListAsync();

            if (movimentoModel == null)
            {
                return NotFound();
            }

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
        
            var movimentoAlert = (from x in movimentoModel where (x.DataHora).Date == data && x.Check==1 select x).ToList();
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
        [Route("EntradasDiaria/{data}")]
        [HttpGet]
        public async Task<int> GetMovimentoModelEntradasDiarias(DateTime data)
        {
            
           
            var movimentoModel = await _context.MovimentoModelViews.ToListAsync();

            if (movimentoModel == null)
            {
                return 0;
            }
        
            var movimentoAlert = (from x in movimentoModel where x.TypeMov == "E" && (x.DataHora).Date == data select ( x.IdColaborador)).ToList();
            var movimentoAlert1=movimentoAlert.Distinct().Count();
            
            return movimentoAlert1;
        }
        [Authorize] //Podem todos ver desde que estejam autenticados 
        [Route("EntradasIntervalo/{data}/{data1}")]
        [HttpGet]
        public async Task<int> GetMovimentoModelEntradasIntervaloDatas(DateTime data, DateTime data1)
        {
            
           
            var movimentoModel = await _context.MovimentoModelViews.ToListAsync();

            if (movimentoModel == null)
            {
                return 0;
            }
        
            var movimentoAlert = (from x in movimentoModel where x.TypeMov == "E" && (x.DataHora).Date >=data && (x.DataHora).Date <=data1 select ( x.IdColaborador)).ToList();
            var movimentoAlert1=movimentoAlert.Distinct().Count();
            
            return movimentoAlert1;
        }
        /*AINDA EM PREPARAÇÂO
        [Authorize] //Podem todos ver desde que estejam autenticados 
        [Route("NumeroWarnigs")]
        [HttpGet]
        public async Task<int> GetNumerodeWarnigs6Meses(DateTime data)
        {
            
           
            var movimentoModel = await _context.MovimentoModelViews.ToListAsync();

            if (movimentoModel == null)
            {
                return 0;
            }
            

            var movimentoAlert = (from x in movimentoModel select ( x.IdColaborador)).ToList();
            
            
            return movimentoAlert;
        }
        */
        
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
