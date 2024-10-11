/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ordenamiento;

import clases.Usuario;
import java.util.Comparator;

public class OrdenIdASC implements Comparator<Usuario>
{

    @Override
    public int compare(Usuario p1, Usuario p2) 
    {
      return Integer.compare(p1.identificacion, p2.identificacion);       
    }     
}