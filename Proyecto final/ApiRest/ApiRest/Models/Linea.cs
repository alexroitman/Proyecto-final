using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Linq;
using System.Web;

namespace ApiRest.Models
{
    public class Linea
    {
        public string id{ get; set; }
        public string Icono { get; set; }
        public string Numero { get; set; }

        public static List<Linea> TraerLinea()
        {
            OleDbConnection conn = new OleDbConnection();
            conn.ConnectionString = @"Provider=Microsoft.Jet.OLEDB.4.0; Data Source=Z:\2016- BT6IA\Proyecto final\BDFINAL.mdb";
            conn.Open();
            OleDbCommand Traer = conn.CreateCommand();
            Traer.CommandText = "SELECT * FROM Lineas";
            Traer.CommandType = CommandType.Text;
            List<Linea> ListaNot = new List<Linea>();
            OleDbDataReader dr = Traer.ExecuteReader();
            while (dr.Read())
            {
               Linea lin= new Linea();
                lin.id = dr["Id"].ToString();
                lin.Numero = dr["Numero"].ToString();
                lin.Icono= dr["Imagen"].ToString();


                ListaNot.Add(lin);
            }
            conn.Close();
            return ListaNot;


        }
    }
}