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
    [Route("api/EPI")]
    [ApiController]
    public class EPIController : ControllerBase
    {
        private readonly EVITARContext _context;

        public EPIController(EVITARContext context)
        {
            _context = context;
        }

        // GET: api/EPI
        [HttpGet]
        public async Task<ActionResult<IEnumerable<EPIModel>>> GetEPIModels()
        {
            return await _context.EPIModels.ToListAsync();
        }

        // GET: api/EPI/5
        [Authorize]
        [HttpGet("{id}")]
        public async Task<ActionResult<EPIModel>> GetEPIModel(int id)
        {
            var ePIModel = await _context.EPIModels.FindAsync(id);

            if (ePIModel == null)
            {
                return NotFound();
            }

            return ePIModel;
        }

        // PUT: api/EPI/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize]
        [HttpPut("{id}")]
        public async Task<IActionResult> PutEPIModel(int id, EPIModel ePIModel)
        {
            if (id != ePIModel.IdEPI)
            {
                return BadRequest();
            }

            _context.Entry(ePIModel).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EPIModelExists(id))
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

        // POST: api/EPI
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize(Roles = "1, 2, 3")]
        [HttpPost]
        public async Task<ActionResult<EPIModel>> PostEPIModel(EPIModel ePIModel)
        {
            
            _context.EPIModels.Add(ePIModel);
            _context.SaveChanges();

            return CreatedAtAction("GetEPIModel", new { id = ePIModel.IdEPI }, ePIModel);

        }

        // DELETE: api/EPI/5
        [Authorize(Roles = "1, 2, 3")]
        [HttpDelete("{id}")]
        public async Task<ActionResult<EPIModel>> DeleteEPIModel(int id)
        {
            var ePIModel = await _context.EPIModels.FindAsync(id);
            if (ePIModel == null)
            {
                return NotFound();
            }

            _context.EPIModels.Remove(ePIModel);
            await _context.SaveChangesAsync();

            return ePIModel;
        }

        private bool EPIModelExists(int id)
        {
            return _context.EPIModels.Any(e => e.IdEPI == id);
        }
    }
}