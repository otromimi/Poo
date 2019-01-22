/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Liam
 */
import java.util.*;
import java.io.*;


public abstract class Alumno implements Serializable{
 
    private String nombre, apellidos, e_mail, dni, direccion, telefono;
    private ArrayList<AsignaturaMatriculada> expediente=new ArrayList<>();
    
    public Alumno(String dni, String nombre, String apellidos, String mail, String direccion, String telf){
        this.dni=dni;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.e_mail=mail;
        this.direccion=direccion;
        this.telefono=telf;
    }
    
    /***
     * 
     * @param lesson == asignatura a matricularse
     * @param anno == año de la matriculacion
     * @return true si se añade la asignatura al expediente false en caso contrario.
     */
    public abstract boolean matricularse(Asignatura lesson, int anno);
    
    /***
     * Imprime la informacion del alumno.
     */
    public abstract void printInfo();
    
    /**
     * Añade una signatura actualizando el el parametro convocatoria
     * @param lesson asignatura a añadir
     * @param anno año en ql que se formaliza la matricula
     */
    protected void addAsignatura(Asignatura lesson, String anno){
        int convocatoria=0;
        for(AsignaturaMatriculada x: expediente){
            if(x.getLesson().equals(lesson) && x.getConvocatoria()>convocatoria){
                convocatoria=x.getConvocatoria();
            }
        }
        expediente.add(new AsignaturaMatriculada(lesson, anno, convocatoria));
    }
    
    public void calificar(){
        boolean hit=false;
        Scanner scan = new Scanner(System.in);
        System.out.print("Introduzca el año a evaluar: ");
        int year = scan.nextInt();
        for(AsignaturaMatriculada x: expediente){
            if(x.getMarks().equals("Sin evaluar") && x.getYear()==year){
                x.setMarks();
                hit=true;
            }
        }
        
        if(hit==false)
            System.out.println("No se encontro ninguna asignatura pendiente de evaluacion.");
    }
    
    /***
     * Muestra el los datos del alumno seleccionado asi como su progreso en el master.
     * @param type_matricula == string que se imprimira en el campo de matricula.
     * @pre siel alumno no esta matriculado en ninguna asignatura el historial academico no se mostrara.
     */
    protected void printInfo(String type_matricula){
        System.out.println("DNI: "+dni);
        System.out.println("Nombre: "+nombre+" "+apellidos);
        System.out.println("E_mail: "+e_mail);
        System.out.println("Direccion: "+direccion);
        System.out.println("Telefono: "+telefono);
        System.out.println("Tipo alumno: "+type_matricula);
        if(!expediente.isEmpty()){
            System.out.println("\n\nAsignatura\tTipo\tAño\tConvocatoria\tCalificacion");
            System.out.println("-----------------------------------------------------------------------------------");
            for(AsignaturaMatriculada x: expediente){
                x.printAtri();
            }
        }else
            System.out.print("\nEste alumno no esta matriculado de ninguna asignatura.");
        System.out.println();
    }
    
    /***
     * Chequea que que no haya ninguna asignaturaMatriculada con un año superior al que se le pasa.
     * @param year= numero del año a chequear
     * @return true si no hay asignatura mayor al año pasado
     */
    protected boolean checkYear(int year){
            return !(this.lastYear_matricula()>year);
    }
    
    /***
     * Debuelve el ultimo año en el que se realizo la matricula de alguna asignatura.
     * @return Ultimo año en forma numerica.
     */
    private int lastYear_matricula(){
        int last=0;
        for(AsignaturaMatriculada x: expediente){
            if(last<x.getYear()){
                last=x.getYear();
            }
        }
        return last;
    }
    
    /***
     * Debuelve el objeto de la ultima matriculacion de una asignatura que se le indique.
     * @param lesson = la asignatura de la que quieres saber cual fue la ultima matriculacion.
     * @return la ultima matriculacion de esa asignatura.
     */
    protected AsignaturaMatriculada lastTry(Asignatura lesson){
        
        AsignaturaMatriculada last=null;
        int lastYear=0;
        
        for(AsignaturaMatriculada x: expediente){
            if(x.getLesson().equals(lesson) && x.getYear()>lastYear){
                lastYear=x.getYear();
                last=x;
            }
        }
        return last;
    }
    
    /***
     * Debuelve si una asignatura ha sido superada por un alumno.
     * @param lesson asignatura a comprobar
     * @return true si ha sido superada.
     */
    protected boolean isPass(Asignatura lesson){ 
        if(lastTry(lesson)==null){
            return false;
        }else{
            return lastTry(lesson).isPass();
        }
    }
    
    /***
     * Cuenta los creditos matriculados en un año.
     * @param year
     * @return creditos matriculados
     */
    protected int etcAnuales(int year){
        int acum=0;
        for(AsignaturaMatriculada x: expediente){
            if(year==x.getYear()){
                acum+=x.getLesson().getETC();
            }
        }
        return acum;
    }
 
    /***
     * Debuelve los creditos superados por el alumno de una tipologia en concreto.
     * @param tipo = tipologia a saber los creditos superados de ella.
     * @return numero de creditos superados de esa tipologia.
     */
    public int creditosSuperados(String tipo){
        int acum=0;
        for(AsignaturaMatriculada x: expediente){
            if(x.isPass() && x.getLesson().getTipo().equals(tipo)){
               acum+=x.getLesson().getETC();
            }
        }
        
        return acum;
    }
    
    /***
     * Debuelve el numero de asignaturas optativas aprovadas;
     * @return acum = numero asignaturas aprobadas.
     */
    public int numeroOPsuperadas(){
        int acum=0;
        for(AsignaturaMatriculada x: expediente){
            if(x.isPass() && x.getLesson().getTipo().equals("OP")){
                acum++;
            }
        }
        return acum;
    }
    
    /**
     * Calcula la nota media del alumno en cuestion.
     * @return int == valor de la nota media.
     */
    public int notaMedia(){
        if(!expediente.isEmpty()){
            int acum=0, matri, count=0, top;
            boolean hit;
            for(Asignatura outter: MasterInformatica.plan_estudios){
                matri=0;
                top=0;
                hit=false;
                for(AsignaturaMatriculada inner: expediente){
                    if(inner.getLesson().equals(outter) && inner.getConvocatoria()>=matri && !inner.getMarks().equals("Sin evaluar") && !inner.getMarks().equals("No presentado")){
                        top=Integer.parseInt(inner.getMarks());
                        hit=true;
                    }
                }
                if(hit){
                    acum+=top;
                    count++;
                }
            }
            if(count==0)
                return 0;
            else
                return acum/count;
        }else{
            return 0;
        }
    }
    
    public String getDNI(){
        return dni;
    }
    

}
