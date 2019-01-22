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
import java.util.Scanner;
import funcionalidad.Func;

public class AsignaturaMatriculada implements Serializable{
    
    private int convocatoria;
    private String calificacion, annoAcademico;
    private Asignatura lesson;
    
    public AsignaturaMatriculada(Asignatura lesson, String anno, int convocatoria){
        this.lesson = lesson;
        this.convocatoria=convocatoria;
        this.annoAcademico=anno;
        this.calificacion="Sin evaluar";
    }
    
    public AsignaturaMatriculada(Asignatura lesson, String anno){
        this(lesson, anno, 0);
    }
    
    public void evaluar(String nota){
        this.calificacion=nota;
    }
    
    public Asignatura getLesson(){
        return lesson;
    }
    
    public int getConvocatoria(){
        return convocatoria;
    }
    
    public int getYear(){
        return Integer.parseInt(annoAcademico);
    }
    
    public String getMarks(){
        return calificacion;
    }
    /***
     * Dice si una asignatura matriculada ha sido superada.
     * @return true si si ha sido superada.
     */
    public boolean isPass(){
        boolean status;
        try{
            status= Integer.parseInt(calificacion)>=5;
        }catch(NumberFormatException ex){
            status= false;
        }
        return status;
    }
    /***
     * Indica si la convocatoria de esta matricula esta consumida.
     * @return true si esta consumida y false en el caso contrario.
     */
    public boolean convocatoriaConsumida(){
        boolean status;
        switch (calificacion) {
            case "Sin evaluar":
                status=false;
                break;
            case "No presentado":
                status=true;
                break;
            default:
                status=true;
                break;
        }
        return status;
    }
    
    /**
     * Imprime la informacion de la asignaturaMatriculada como fila de una tabla.
     */
    public void printAtri(){
        //System.out.println("Asignatura\tTipo\tCalificacion\tAño\tConvocatoria");
        System.out.printf("%s\t\t%s\t%s\t%s\t\t%s\n",this.lesson.getCodigo(),this.lesson.getTipo(),this.annoAcademico,this.convocatoria,this.calificacion);
    }
    
    /***
     * Añade una calificacion valida a una asignaturaMatriculada.
     */
    public void setMarks(){
        Scanner scan = new Scanner(System.in);
        int nota;
        System.out.print("El alumno se presento a esta convocatoria");
        if(!Func.siNo()){
            this.calificacion = "No presentado";
        }else{
            do{
                System.out.print("Introduzca la calificacion: ");
                nota = scan.nextInt();
                if(nota<=10 && nota>=0)
                    this.calificacion=String.valueOf(nota);
                else
                    System.out.println("Calificacion no valida.");
            }while(!(nota<=10 && nota>=0));
        }
    }
}
