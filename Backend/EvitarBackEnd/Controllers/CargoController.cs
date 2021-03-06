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
    
    [Route("api/Cargo")]
    [ApiController]
    public class CargoController : ControllerBase
    {
        private readonly EVITARContext _context;

        public CargoController(EVITARContext context)
        {
            _context = context;
        }

        // GET: api/Cargo
        [Authorize]//Toda a gente pode ver os cargos
        [HttpGet]
        public async Task<ActionResult<IEnumerable<CargoModel>>> GetCargoModel()
        {
            return await _context.CargoModels.ToListAsync();
        }

        // GET: api/Cargo/5
        [Authorize]//Toda a gente pode ver os cargos
        [HttpGet("{id}")]
        public async Task<ActionResult<CargoModel>> GetCargoModel(int id)
        {
            var cargoModel = await _context.CargoModels.FindAsync(id);

            if (cargoModel == null)
            {
                return NotFound();
            }

            return cargoModel;
        }

        // PUT: api/Cargo/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize(Roles = "1, 2")]//Pode editar cargos os Admins e o RH
        [HttpPut("{id}")]
        public async Task<IActionResult> PutCargoModel(int id, CargoModel cargoModel)
        {
            if (id != cargoModel.IdCargo)
            {
                return BadRequest();
            }

            _context.Entry(cargoModel).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CargoModelExists(id))
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

        // POST: api/Cargo
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [Authorize(Roles = "1, 2")]//Pode adicionar Cargos os Admins e o RH
        [HttpPost]
        public async Task<ActionResult<CargoModel>> PostCargoModel(CargoModel cargoModel)
        {
            _context.CargoModels.Add(cargoModel);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetCargoModel), new { id = cargoModel.IdCargo }, cargoModel);
        }

        // DELETE: api/Cargo/5
        [Authorize(Roles = "1, 2")]//Pode eliminar cargos os Admins e o RH
        [HttpDelete("{id}")]
        public async Task<ActionResult<CargoModel>> DeleteCargoModel(int id)
        {
            var cargoModel = await _context.CargoModels.FindAsync(id);
            if (cargoModel == null)
            {
                return NotFound();
            }

            _context.CargoModels.Remove(cargoModel);
            await _context.SaveChangesAsync();

            return cargoModel;
        }

        private bool CargoModelExists(int id)
        {
            return _context.CargoModels.Any(e => e.IdCargo == id);
        }
    }
}
