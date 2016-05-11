using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web.Mvc;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web.Http;
using ApiRest.Models;
using System.Data.OleDb;

namespace ApiRest.Controllers
{
    public class LineaController : ApiController
    {
     
        public List<Linea> GetAll()
        {

            return Linea.TraerLinea() ;
        }
        public Linea Get(int id)
        {
            return new Linea { id="a", Numero = "Gaby", Icono= "Guivi" };
        }
        /* public HttpResponseMessage Announcements()
         {
             var response = new HttpResponseMessage(HttpStatusCode.OK);
             response.Content = new StringContent("Probando");
             response.Content.Headers.ContentType = new MediaTypeHeaderValue("text/plain");
             return response;
         }*/
    }
}
