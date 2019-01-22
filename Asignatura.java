
import java.io.Serializable;



/**
 *
 * @author Liam
 */
public class Asignatura implements Serializable{
    
    private String nombre, codigo, tipologia;
    int etcs;
    
    public Asignatura(String codigo, String nombre, String tipo, int etcs){
        this.nombre=nombre;
        this.codigo=codigo;
        this.tipologia=tipo;
        this.etcs=etcs;
    }
    
    public void printInfo(){
        System.out.println("Codigo: "+codigo);
        System.out.println("Nombre: "+nombre);
        System.out.println("Tipologia: "+tipologia);
        System.out.println("ETCs: "+etcs);
    }
    
    public String getCodigo(){
        return this.codigo;
    }
    
    public int getETC(){
        return this.etcs;
    }
    
    public String getTipo(){
        return this.tipologia;
    }
}
