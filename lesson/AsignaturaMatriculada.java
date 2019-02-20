package lesson;


import addons.Func;
import java.io.Serializable;
import java.time.Year;
import java.util.Scanner;

/**
 * @version 2
 * @author Guillermo Leiro Arroyo
 */
public class AsignaturaMatriculada implements Serializable{
    
    private Asignatura lesson;
    private Year annioAcademico;
    private String calificacion;
    
    public AsignaturaMatriculada(Asignatura lesson, Year annio){
        this.lesson=lesson;
        this.annioAcademico=annio;
        this.calificacion="Sin evaluar";
    }
    
    /***
     * Getter para el tipo de asignatura.
     * @return lesson
     */
    public Asignatura getLesson(){
        return lesson;
    }
    
    /***
     * Getter para el año academico.
     * @return annioAcademico.
     */
    public Year getYear(){
        return annioAcademico;
    }
    
    /***
     * Getter para la calificacion en forma de String.
     * @return calificacion
     */
    public String getMarks(){
        return calificacion;
    }
    
    /***
     * Getter de la calificacion en forma numerica.
     * @return un Float con el numero o null en caso de que el string no sea convertible.
     */
    public Float getNumericMarks(){
        try{
            return Float.parseFloat(calificacion);
        }catch(Exception ex){
            return null;
        }
    }
    
    /***
     * Añade un a calificacio a la asignatura matriculada.
     */
    public void evaluar(){
        Scanner scan = new Scanner(System.in);
        System.out.print("El alumno se presento a la asignatura "+this.getLesson().getCode());
        if(!Func.siNo()){
           this.calificacion="Sin evaluar";
        }else{
            System.out.print("Calificacion "+this.getLesson().getCode()+" : ");
            try{
                Float nota=scan.nextFloat();
                if(nota<=10 && nota>=0){
                    this.calificacion = Float.toString(nota);
                }else{
                    System.out.println("La calificacion debe estar comprendida entre 0 y 10.");
                }
            }catch(Exception ex){
                System.out.println("Esta asignatura no se calificara ya que introducio una calificacion no valida.");
            }
        }
    }
    
    /***
     * Imprime un resumen de los atributos en un alinea.
     */
    public void printAtri(){
        System.out.printf("%s\t\t%s\t%s\n",this.lesson.getCode(),this.annioAcademico,this.calificacion);
    }
    
    /***
     * Metodo para comprobar si se puede realizar la matriculacion de una asignatura.
     * @return true si hay luz verde.
     */
    public boolean matriCheck(){
        Float nota;
        try{
            nota=Float.parseFloat(calificacion);
            return nota<5;
        }catch(NumberFormatException ex){
            return !(calificacion.equals("Sin evaluar"));
        }
    }
    
    /***
     * Metodo que chequea si una isntancia se asignatura matriculada ha sido superada.
     * @return true si la asignatura se supero.
     */
    public boolean isPass(){
        try{
            return (Float.parseFloat(calificacion))>=5;
        }catch(NumberFormatException ex){
            return false;
        }  
    }
}
