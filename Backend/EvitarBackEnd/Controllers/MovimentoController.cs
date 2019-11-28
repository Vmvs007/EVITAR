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
