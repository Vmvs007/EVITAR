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
    [Route("api/EPICargo")]
    [ApiController]
    public class EPICargoController : ControllerBase
    {
        private readonly EVITARContext _context;

        public EPICargoController(EVITARContext context)
        {
            _context = context;
        }

        // GET: api/EPICargo
        [Authorize]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<EPICargoModel>>> GetEPICargoModels()
        {
            return await _context.EPICargoModels.ToListAsync();
        }

        // GET: api/EPICargo/5
        [Authorize]
        [HttpGet("{id}")]
        public async Task<ActionResult<EPICargoModel>> GetEPICargoModel(int id)
        {
            var ePICargoModel = await _context.EPICargoModels.FindAsync(id);

            if (ePICargoModel == null)
            {
                return NotFound();
            }

            return ePICargoModel;
        }

        // PUT: api/EPICargo/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize]
        [HttpPut("{id}")]
        public async Task<IActionResult> PutEPICargoModel(int id, EPICargoModel ePICargoModel)
        {
            if (id != ePICargoModel.IdCargo)
            {
                return BadRequest();
            }

            _context.Entry(ePICargoModel).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EPICargoModelExists(id))
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

        // POST: api/EPICargo
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize]
        [HttpPost]
        public async Task<ActionResult<EPICargoModel>> PostEPICargoModel(EPICargoModel ePICargoModel)
        {
            _context.EPICargoModels.Add(ePICargoModel);
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (EPICargoModelExists(ePICargoModel.IdCargo))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtAction("GetEPICargoModel", new { id = ePICargoModel.IdCargo }, ePICargoModel);
        }

        // DELETE: api/EPICargo/5
        [Authorize]
        [HttpDelete("{id}")]
        public async Task<ActionResult<EPICargoModel>> DeleteEPICargoModel(int id)
        {
            var ePICargoModel = await _context.EPICargoModels.FindAsync(id);
            if (ePICargoModel == null)
            {
                return NotFound();
            }

            _context.EPICargoModels.Remove(ePICargoModel);
            await _context.SaveChangesAsync();

            return ePICargoModel;
        }

        private bool EPICargoModelExists(int id)
        {
            return _context.EPICargoModels.Any(e => e.IdCargo == id);
        }
    }
}
