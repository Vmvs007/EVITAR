using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using EvitarBackEnd.Models;

namespace EvitarBackEnd.Controllers
{
    [Route("api/MovEpi")]
    [ApiController]
    public class MovEPIController : ControllerBase
    {
        private readonly EVITARContext _context;

        public MovEPIController(EVITARContext context)
        {
            _context = context;
        }

        // GET: api/MovEPI
        [HttpGet]
        public async Task<ActionResult<IEnumerable<MovEPIModel>>> GetMovEPIModels()
        {
            return await _context.MovEPIModels.ToListAsync();
        }

        // GET: api/MovEPI/5
        [HttpGet("{id}")]
        public async Task<ActionResult<MovEPIModel>> GetMovEPIModel(int id)
        {
            var movEPIModel = await _context.MovEPIModels.FindAsync(id);

            if (movEPIModel == null)
            {
                return NotFound();
            }

            return movEPIModel;
        }

        // PUT: api/MovEPI/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [HttpPut("{id}")]
        public async Task<IActionResult> PutMovEPIModel(int id, MovEPIModel movEPIModel)
        {
            if (id != movEPIModel.IdMovimento)
            {
                return BadRequest();
            }

            _context.Entry(movEPIModel).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!MovEPIModelExists(id))
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

        // POST: api/MovEPI
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [HttpPost]
        public async Task<ActionResult<MovEPIModel>> PostMovEPIModel(MovEPIModel movEPIModel)
        {
            _context.MovEPIModels.Add(movEPIModel);
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (MovEPIModelExists(movEPIModel.IdMovimento))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtAction("GetMovEPIModel", new { id = movEPIModel.IdMovimento }, movEPIModel);
        }

        // DELETE: api/MovEPI/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<MovEPIModel>> DeleteMovEPIModel(int id)
        {
            var movEPIModel = await _context.MovEPIModels.FindAsync(id);
            if (movEPIModel == null)
            {
                return NotFound();
            }

            _context.MovEPIModels.Remove(movEPIModel);
            await _context.SaveChangesAsync();

            return movEPIModel;
        }

        private bool MovEPIModelExists(int id)
        {
            return _context.MovEPIModels.Any(e => e.IdMovimento == id);
        }
    }
}
