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
    [Route("api/TipoEPI")]
    [ApiController]
    public class TipoEPIController : ControllerBase
    {
        private readonly EVITARContext _context;

        public TipoEPIController(EVITARContext context)
        {
            _context = context;
        }

        // GET: api/TipoEPI
        [Authorize]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TipoEPIModel>>> GetTipoEPIModels()
        {
            return await _context.TipoEPIModels.ToListAsync();
        }

        // GET: api/TipoEPI/5
        [Authorize]
        [HttpGet("{id}")]
        public async Task<ActionResult<TipoEPIModel>> GetTipoEPIModel(int id)
        {
            var tipoEPIModel = await _context.TipoEPIModels.FindAsync(id);

            if (tipoEPIModel == null)
            {
                return NotFound();
            }

            return tipoEPIModel;
        }

        // PUT: api/TipoEPI/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize(Roles="1,2,3")]
        [HttpPut("{id}")]
        public async Task<IActionResult> PutTipoEPIModel(int id, TipoEPIModel tipoEPIModel)
        {
            if (id != tipoEPIModel.IdTipoEPI)
            {
                return BadRequest();
            }

            _context.Entry(tipoEPIModel).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TipoEPIModelExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Ok(new { message ="OK"});
        }

        // POST: api/TipoEPI
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize(Roles="1,2,3")]
        [HttpPost]
        public async Task<ActionResult<TipoEPIModel>> PostTipoEPIModel(TipoEPIModel tipoEPIModel)
        {
            _context.TipoEPIModels.Add(tipoEPIModel);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetTipoEPIModel", new { id = tipoEPIModel.IdTipoEPI }, tipoEPIModel);
        }

        // DELETE: api/TipoEPI/5
        [Authorize(Roles="1,2,3")]
        [HttpDelete("{id}")]
        public async Task<ActionResult<TipoEPIModel>> DeleteTipoEPIModel(int id)
        {
            var tipoEPIModel = await _context.TipoEPIModels.FindAsync(id);
            if (tipoEPIModel == null)
            {
                return NotFound();
            }

            _context.TipoEPIModels.Remove(tipoEPIModel);
            await _context.SaveChangesAsync();

            return tipoEPIModel;
        }

        private bool TipoEPIModelExists(int id)
        {
            return _context.TipoEPIModels.Any(e => e.IdTipoEPI == id);
        }
    }
}
