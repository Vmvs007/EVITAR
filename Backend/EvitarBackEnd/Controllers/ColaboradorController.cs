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
        [HttpGet("{id}")]
        public async Task<ActionResult<ColaboradorModel>> GetColaboradorModel(int id)
        {
            var colaboradorModel = await _context.ColaboradorModels.FindAsync(id);

            if (colaboradorModel == null)
            {
                return NotFound();
            }

            return colaboradorModel;
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
    }
}
