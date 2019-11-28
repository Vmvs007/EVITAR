using AutoMapper;
using EvitarBackEnd.Entities;
using EvitarBackEnd.Models.Users;

namespace EvitarBackEnd.Helpers
{
    public class AutoMapperProfile : Profile
    {
        public AutoMapperProfile()
        {
            CreateMap<User, UserModel>();
            CreateMap<RegisterModel, User>();
            CreateMap<UpdateModel, User>();
        }
    }
}
