package estudiante;

import lesson.*;
import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @version 2
 * @author Guillermo Leiro Arroyo
 */
public abstract class Alumno implements Serializable{
    
    private String nombre, apellidos, e_mail, dni, direccion, telefono;
    protected ArrayList<AsignaturaMatriculada> expediente=new ArrayList<>();
    
    public Alumno(String id, String nombre, String apellidos, String direccion, String mail, String telf){
        this.dni=id;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.direccion=direccion;
        this.e_mail=mail;
        this.telefono=telf;
    }
    
    /***
     * Getter del DNI del alumno.
     * @return DNI.
     */
    public String getId(){
        return dni;
    }
    
    /***
     * Comprueba si una asignatura ha sido superada o esta sin evaluar.
     * @param subject asignatura a comprobar.
     * @return false si la asignatura ha sido superada o esta sin evaluar.
     */
    protected boolean greenLight(Asignatura subject){
        boolean status=true;
        for(AsignaturaMatriculada x: expediente){
            if(x.getLesson().equals(subject) && x.matriCheck()==false){
                status=false;
                break;
            }
        }
        return status;
    }
    
    /***
     * Imprime la informacion del alumno.
     */
    protected void printInfo(){
        System.out.println("DNI: "+dni);
        System.out.println("Nombre: "+nombre+" "+apellidos);
        System.out.println("E_mail: "+e_mail);
        System.out.println("Direccion: "+direccion);
        System.out.println("Telefono: "+telefono);
        if(!expediente.isEmpty()){
            System.out.println("\nAsignatura\tAño\tCalificacion");
            System.out.println("-----------------------------------------");
            for(AsignaturaMatriculada x: expediente){
                x.printAtri();
            }
        }else
            System.out.print("\nEste alumno no esta matriculado de ninguna asignatura.");
        System.out.println();
    }
    
    /**
     * Metodo para la matriculacion en una asignatura.
     * @return true si la matriculacion se realizo satisfactoriamente.
     */
    public abstract boolean matricularse();
    
    /***
     * Metodo para la evaluacion del annio academico de un alumno
     * @return true si este esta graduado tras la evaluacion.
     */
    public boolean evaluar(){
        Scanner scan = new Scanner(System.in);
        Year annio;
        boolean status;
        try{
            System.out.print("Introduzaca el curso academico a evaluar: ");
            annio=Year.of(scan.nextInt());
            for(AsignaturaMatriculada x: expediente){
                if(x.getYear().equals(annio) && x.getMarks().equals("Sin evaluar")){
                    x.evaluar();
                }
            }
        }catch(Exception ex){
            System.out.println("Año introducido no valido.");
        }
        return this.isGraduado();
    }
    
    /***
     * Metodo para comprobar si un alumno esa graduado.
     * @return true si esta graduado
     */
    private boolean isGraduado(){
        int contOP=0, contOB=0;
        for(AsignaturaMatriculada x: expediente){
            if(x.isPass()){
                if(x.getLesson().getType().equals("OB")){
                    contOB += x.getLesson().getCreditos();
                }else{
                    contOP++;
                }
            }
        }
        return (contOB>=48 && contOP>=2);
    }
    
    /***
     * Realiza la nota media de un alumno.
     * @return String con el valor de la nota media.
     */
    private String media(){
        ArrayList<String> codigos = new ArrayList<>();
        for(AsignaturaMatriculada x: expediente){
            if((!codigos.contains(x.getLesson().getCode())) && (!x.getMarks().equals("Sin evaluar")))
                codigos.add(x.getLesson().getCode());
        }
        Iterator iter_code = codigos.iterator();
        Float cont = new Float(0);
        Float acum = new Float(0);
        while(iter_code.hasNext()){
            acum += this.bestOfLesson((String) iter_code.next());
            cont++;
        }
        return Float.toString(acum/cont);
    }

    /**
     * Calcula la mejor nota de una asignatura entre todas sus matriculas.
     * @param code de la asignatura a buscar.
     * @return nota mas alta de esta.
     */
    private Float bestOfLesson(String code){
        Float best, temp;
        best=Float.valueOf(0);
        for (AsignaturaMatriculada x: this.expediente){
            if(x.getLesson().getCode().equals(code)){
                temp = x.getNumericMarks();
                if(temp!=null && best<temp){
                    best=temp;
                }
            }
        }
        return best;
    }
    
    
    /***
     * Creacion de un alumno finalizado a partir del alumno que la invoque.
     * @return objeto de alumno finalizado con atributos de la instancia invocadora.
     */
    public AlumnoFinalizado promocion(){
        return new AlumnoFinalizado(this.expediente, this.dni, this.nombre, this.apellidos, this.direccion, this.e_mail, this.telefono, this.media());
    }
}
