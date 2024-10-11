package clases;


public class Reservacion 
{
    public int identificacion;
    public int guests;
    public int bedrooms;
    public String property_type;
    public int beds;
    public String host_language;
    public String fechaInicio;
    public String fechaFin;
    public double precio;
    
    public Reservacion (int identificacion, int guests, int bedrooms, 
                        String property_type, int beds,  String host_language, 
                        String fechaInicio, String fechaFin, double precio)
    {
        this.identificacion = identificacion;
        this.guests = guests;
        this.bedrooms = bedrooms;
        this.property_type = property_type;
        this.beds = beds;
        this.host_language = host_language;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precio = precio;
    }    
    
    public void insertarr(Conectado pCon) 
    {
        
        String sql = "INSERT INTO Reservacion(identificacion, guests, "
                                    + "bedrooms, property_type, beds, host_language, "
                                    + "fechaInicio, fechaFin, precio) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] parametros = 
                    {
                        this.identificacion,
                        this.guests,
                        this.bedrooms,
                        this.property_type,
                        this.beds,
                        this.host_language,
                        this.fechaInicio,
                        this.fechaFin,
                        this.precio
                    };
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
