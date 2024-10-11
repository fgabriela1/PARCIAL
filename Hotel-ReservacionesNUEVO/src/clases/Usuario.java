package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class Usuario 
{    
    public int identificacion;
    public String nombre;
    public int codigo_habitacion;
    public ArrayList<Reservacion> lReservacion;
    
    
    public Usuario(int identificacion, String nombre, int codigo_habitacion)
    {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.codigo_habitacion = codigo_habitacion;
        this.lReservacion = new ArrayList<>(); 
     
    }
    

    public void insertar(Conectado pCon)
    {
        String sql= "INSERT INTO Usuario(identificacion, nombre, codigo_habitacion) "+
                "VALUES (?, ?, ?)";
        Object[] parametros = {this.identificacion, this.nombre, this.codigo_habitacion};
       
        // Insertar el registro en la tabla Usuario
        pCon.insertar(sql, parametros);
        
        // Luego, insertar los datos de lReservacion en una tabla relacionada
        for (Reservacion reservacion : this.lReservacion) 
        {
            String sqlReservacion = "INSERT INTO Reservacion(identificacion, guests, "
                                    + "bedrooms, property_type, beds, host_language, "
                                    + "fechaInicio, fechaFin, precio) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] parametrosReservacion = 
            {
                reservacion.identificacion,
                reservacion.guests,
                reservacion.bedrooms,
                reservacion.property_type,
                reservacion.beds,
                reservacion.host_language,
                reservacion.fechaInicio,
                reservacion.fechaFin,
                reservacion.precio
            };

            // Insertar el registro de la reservación en la tabla "Reservacion"
            pCon.insertar(sqlReservacion, parametrosReservacion);
        }
    }

    
    //public void insertar(Conectado pCon)
    //{
       //for(Usuario user : lUser)
       //{
           //String sql= "INSERT INTO Usuario (identificacion, nombre, codigo_habitacion,) "
                    //+ "VALUES (?, ?, ?)";              
            //Object[] parametros = {user.identificacion, user.nombre, user.codigo_habitacion};
           //pCon.insertar(sql, parametros);
            
            //for(Reservacion reservacion : user.lReservacion)
            //{
                //String sql2 = "INSERT INTO Reservacion (identificacion, guests, bedrooms, "
                        //+ "property_type, beds, host_language, fechaInicio, fechaFin, precio) "
                        //+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                //Object[] parametros2 = {reservacion.identificacion, reservacion.guests, reservacion.bedrooms, 
                                        //reservacion.property_type, reservacion.beds, reservacion.host_language, 
                                        //reservacion.fechaInicio, reservacion.fechaFin, reservacion.precio};
                //pCon.insertar(sql2, parametros2);
            //}
        //}              
    //}
    
    
    public void insertar2(Conectado pCon)
    {
        String sql= "INSERT INTO Usuario(identificacion, nombre, codigo_habitacion) " +
                "VALUES (?, ?, ?)";
        Object[] parametros = {this.identificacion, this.nombre, this.codigo_habitacion};
        try
        {
            pCon.insertar(sql, parametros);
            pCon.getConnection().commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}




