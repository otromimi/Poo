package estudiante;

import lesson.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @version 2
 * @author Guillermo Leiro Arroyo
 */
public class AlumnoFinalizado extends Alumno implements Pupilo, Serializable{
    
    private String notaMedia;
    
    public AlumnoFinalizado(ArrayList<AsignaturaMatriculada> lessons, String id, String nombre, String apellidos, String direccion, String mail, String telf, String media) {
        super(id,nombre,apellidos,direccion,mail,telf);
        this.expediente=lessons;
        this.notaMedia=media;
    }
    
    /***
     * cheque si el alumno tiene media y de no ser asi la crea.
     * @return media del alumno.
     */
    public String media(){
        if(this.notaMedia==null){
            //this.notaMedia=super.media();
        }
        return this.notaMedia;
    }
    
    @Override
    public void printInfo(){
        System.out.println("Tipo alumno: Graduado.");
        super.printInfo();
        System.out.println("\t\tNota media: "+notaMedia+"\n");
    }

    @Override
    public boolean matricularse() {
        System.out.println(" Este alumno se encuentra graduado.");
        return true;
    }
    
    @Override
    public boolean evaluar() {
        System.out.println(" Este alumno se encuentra graduado.");
        return false;
    }
}
