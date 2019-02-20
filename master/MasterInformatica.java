package master;

import lesson.*;
import estudiante.*;
import addons.Func;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @version 2
 * @author Guillermo Leiro Arroyo
 */
public final class MasterInformatica implements Serializable{
    
    private transient ArrayList <Asignatura> planEstudios;
    private ArrayList <Pupilo> estudiantes = new ArrayList<>();
    private static MasterInformatica instance;
    
    private MasterInformatica(){
       this.cargarPlan_estudios();
    }
    
    public static MasterInformatica getInstance(){
        if(instance==null)
            instance = new MasterInformatica();
        return instance;
    }
    
    /***
     * Carga los datos de las asignaturas ofrecidas por el master.
     */
    public void cargarPlan_estudios(){
        
        String line=null;
        this.planEstudios=new ArrayList<>();
          
        try {
            BufferedReader br =  new BufferedReader(new FileReader("planEstudios.txt"));
            while((line = br.readLine()) != null){
                String[] partes = line.split("-");
                planEstudios.add(new Asignatura(partes[0], partes[1], partes[2], Integer.parseInt(partes[3])));
            }
            br.close();
        }catch(Exception ex) {
            System.out.println("\n\n\tError cargando el plan de estudios; "+ex);                
        }   
    }
    
    /***
     * Metodo que solicata la introduccion del codigo de una asignatura y la busca en las asignaturas ofertadas en el plan de estudios.
     * @return la asignatura correspondiente al codigo.
     * @post null si no la encuentra.
     */
    public Asignatura findLesson(){
        Scanner scan = new Scanner(System.in);
        Asignatura hit = null;
        String code;
        
        System.out.print("\n\tIntroduzca el codigo de la asignatura: ");
        code = scan.nextLine();
        
        for(Asignatura x: planEstudios){
            if(x.getCode().equals(code)){
                hit = x;
                break;
            }
        }
        return hit;
    }
    
    /***
     * Busca a un estudiante en el master de informatica.
     * @pre Solicita el DNI de un estudiante para buscarlo.
     * @return El estudiante en cuestion.
     */
    public Pupilo findStudent(){
        Scanner scan = new Scanner(System.in);
        String dni;
        
        System.out.print("\n\tIntroduzca su DNI: ");
        dni = scan.nextLine();
        
        return findStudent(dni);
    }
    
    /***
     * Busca a un estudiante en el master de informatica.
     * @param dni String con el dni del alumnno a buscar.
     * @return Alumno en cuestion.
     */
    public Pupilo findStudent(String dni){
        
        Pupilo hit = null;
        
        for(Pupilo x: estudiantes){
            if(x.getId().equals(dni)){
                hit = x;
                break;
            }
        }
        
        return hit;
    }
    
    /***
     * Metodo para buscar y consultar la informacion de u alumno.
     */
    public void printInfoAlumno(){
        this.findStudent().printInfo();
    }
    
    /***
     * Metodo para tramitar la matricula de una asignatura por parte de un alumno.
     */
    public void tramiteMtricula(){
        do{
            try{
                if(findStudent().matricularse())
                    System.out.println("Matriculacion exitosa.");
            }catch(NullPointerException ex){
                System.out.println(" El alumno no se encuantra matriculado en este master.");
            }
            System.out.print("Desea volver a realizar la operacion");
        }while(Func.siNo());
    }
    
    /***
     * Metodo para la evaluacion de un alumno.
     */
    public void evaluar(){
        Pupilo theOne = this.findStudent();
        if(theOne.evaluar()){
            int i, hashOne = theOne.hashCode();
            for(i=0;i<this.estudiantes.size();i++){
                if(this.estudiantes.get(i).hashCode()==hashOne){
                    this.estudiantes.add(theOne.promocion());
                    this.estudiantes.remove(i);
                }
            }
        }
    }
    
    /***
     * Metodo para el alta de un alumno.
     */
    public void addAlumno(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Introduzca su DNI: ");
        String id = scan.nextLine();
        
        if(Func.checkID(id, true)){
            if(findStudent(id)==null){
                String info[]= new String[5];
                System.out.print("\nRELLENE LOS SIGUIENTES CAMPOS\nNombre: ");
                info[0]=scan.nextLine();
                System.out.print("Apellido: ");
                info[1]=scan.nextLine();
                System.out.print("Direccion: ");
                info[2]=scan.nextLine();
                System.out.print("E-mail: ");
                info[3]=scan.nextLine();
                System.out.print("Telefono: ");
                info[4]=scan.nextLine();
                System.out.print("Estudiante a tiempo parcial (tiempo completo predefinido)");
                if(Func.siNo()){
                    this.estudiantes.add(new AlumnoT_parcial(id, info[0], info[1], info[2], info[3], info[4]));
                }else{
                    this.estudiantes.add(new AlumnoT_completo(id, info[0], info[1], info[2], info[3], info[4]));
                }
                System.out.println("\n\nEl alumno "+id+" ingreso satisfactoriamente.");
                Func.pressToContinue();
            }else{
                System.out.println("El alumno "+id+" ya se encuentra registrdo.");
                Func.pressToContinue();
            }
        }else{
            System.out.println("DNI no valido.");
            Func.pressToContinue();
        }
    
    }
}
