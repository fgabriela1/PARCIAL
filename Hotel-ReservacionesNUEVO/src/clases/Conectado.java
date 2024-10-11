/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Conectado 
{
  
    // Para conectarse a la base de datos
    private Connection oCon;
    private Statement stm;
    
    
    public Conectado(String usuario, String clave) 
    {
        // Inicializar la lista de usuarios
        //lUser = new ArrayList<>();
        try 
        {
            Class.forName("oracle.jdbc.OracleDriver") ;
            //this.oCon = DriverManager.getConnection("jdbc:oracle:thin:@" + pIP + ":1521:" 
                                                    //+ pListener, usuario, clave);
            this.oCon = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.14:1521:FREE", 
                                                    usuario, clave);
            this.stm = this.oCon.createStatement();

            System.out.println("BRAVO ESTAS CONECTADO AL SERVER ORACLE");
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            System.out.println("Error al conectar con la base de datos");
            System.out.println(ex.getMessage());
        }
    }
    
    public Connection getConnection()
    {
       return this.oCon;
    }
    
    public ArrayList<Object[]> consultar(String query) 
    {

        ArrayList<Object[]> aResultados = new ArrayList<>();

        try 
        {
            ResultSet rs = this.stm.executeQuery(query);
            int columnCount = ((ResultSetMetaData) rs.getMetaData()).getColumnCount();

            while (rs.next()) 
            {
                Object[] fila = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) 
                {
                    fila[i-1] = rs.getObject(i);
                }
                aResultados.add(fila);
        }
    } 
    catch (Exception e) { }
    
    return aResultados;
   }
    
    public ArrayList<Object[]> consultar1(String query) 
    {
        ArrayList<Object[]> aResultados = new ArrayList<>();

        try 
        {
            ResultSet rs = this.stm.executeQuery(query);
            int columnCount = ((ResultSetMetaData) rs.getMetaData()).getColumnCount();

            while (rs.next()) 
            {
                Object[] fila = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) 
                {
                    fila[i-1] = rs.getObject(i);
                }
                aResultados.add(fila);
            }
        } 
        catch (Exception e) { }

        return aResultados;
    }
    
    
    public ArrayList<Object[]> consultar2(String query, Object[] params) 
    {
        ArrayList<Object[]> aResultados = new ArrayList<>();

        try 
        {
            PreparedStatement pStmt = this.oCon.prepareStatement(query);

            // Configura los parámetros de la consulta si se proporcionan
            if (params != null) 
            {
                for (int i = 0; i < params.length; i++) 
                {
                    pStmt.setObject(i + 1, params[i]);
                }
            }

            ResultSet rs = pStmt.executeQuery();
            int columnCount = ((ResultSetMetaData) rs.getMetaData()).getColumnCount();

            while (rs.next()) 
            {
                Object[] fila = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    fila[i - 1] = rs.getObject(i);
                }
                aResultados.add(fila);
            }
        } 
        catch (Exception e) 
        {
            // Maneja las excepciones según tus requerimientos
            e.printStackTrace();
        }

        return aResultados;
    }

    public void insertar(String query, Object[] params) 
    {
        try 
        {
            PreparedStatement pStmt = this.oCon.prepareStatement(query);
            if (params != null) 
            {
                for (int i = 0; i < params.length; i++) 
                    pStmt.setObject(i + 1, params[i]);
                pStmt.executeUpdate();
            }
        } 
        catch (Exception e) { int i=0; }
    }
}
