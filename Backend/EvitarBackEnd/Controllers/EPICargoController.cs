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

        // GET: api/EPICargo/5
        [Authorize]
        [Route("EPIs/{id}")]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<EPICargoNecModelView>>> GetEPICargoEPIs(int id)
        {
            var ePICargo = await _context.EPICargoNecModelViews.ToListAsync();

            var query = (from x in ePICargo where x.IdCargo == id select x).ToList();

            if (query == null)
            {
                return NotFound();
            }

            return query;
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
        //[Authorize]
        [HttpDelete("{idcargo}/{idTipo}")]
        public async Task<String> DeleteEPICargoModel(int idcargo,int idTipo)
        {
            var ePICargoModel = await _context.EPICargoModels.ToListAsync();

            var epiquery=(from x in ePICargoModel where x.IdCargo==idcargo && x.IdTipoEPI==idTipo select x).ToList();
            if (ePICargoModel == null)
            {
                return null;
            }
            for(int i=0;i<epiquery.ToArray().Length;i++){
            _context.EPICargoModels.Remove(epiquery[i]);
            await _context.SaveChangesAsync();
            }
            string ola="Foi eliminado";
            return ola;
        }

        private bool EPICargoModelExists(int id)
        {
            return _context.EPICargoModels.Any(e => e.IdCargo == id);
        }
    }
}
