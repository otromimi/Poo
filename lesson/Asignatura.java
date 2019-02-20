package lesson;


import java.io.Serializable;

/**
 * @version 2
 * @author Guillermo Leiro Arroyo
 */
public class Asignatura implements Serializable{
    
    private String nombre, codigo, tipologia;
    private int etcs;
    
    public Asignatura(String codigo, String nombre, String tipo, int etcs){
        this.nombre=nombre;
        this.codigo=codigo;
        this.tipologia=tipo;
        this.etcs=etcs;
    }
    
    /***
     * Imprime la informacion de la asignatura.
     */
    public void printInfo(){
        System.out.println("Codigo: "+codigo);
        System.out.println("Nombre: "+nombre);
        System.out.println("Tipologia: "+tipologia);
        System.out.println("ETCs: "+etcs);
    }
    
    /***
     * Getter para el codigo de la asignatura.
     * @return codigo
     */
    public String getCode(){
        return codigo;
    }
    
    /***
     * Getter para el tipo de asignatura.
     * @return tipologia
     */
    public String getType(){
        return tipologia;
    }
    
    /***
     * Getter para el los creditos de la asignatura.
     * @return etcs
     */
    public int getCreditos(){
        return etcs;
    }
}
