package master;

import addons.Func;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;


/**
 * @version 2
 * @author Guillermo Leiro Arroyo
 */
public class Aplicacion {
    
    private static MasterInformatica run;
    
    public static void main(String args[]){
    
        try{
            run = readAll();
            run.cargarPlan_estudios();
        }catch(Exception ex){
            System.out.print("Virgin app.");
            run = MasterInformatica.getInstance();
        }
        mainMenu();
    }
    
    /***
     * Menu principal de la aplicacion.
     */
    private static void mainMenu(){
    
        int option;
        boolean alive = true;
        Scanner scan = new Scanner(System.in);
    
        do{
            System.out.print("\n\n");
            System.out.println("MASTER INFORMATICA");
            System.out.println();
            System.out.println("1.-Dar de alta un nuevo alumno.");
            System.out.println("2.-Consulta sobre asignaturas.");
            System.out.println("3.-Consulta del expediente de un alumno.");
            System.out.println("4.-Gestión de Matrícula.");
            System.out.println("5.-Calificación de la Asignatura.");
            System.out.println("6.-Guardar copia de los datos académicos.");
            System.out.println("7.-Cerrar el programa.");
            System.out.print("\n  Elija una opcion: ");
            try{
                //System.out.println("por que coño no me sale esto, hostia puta joder");
                option=scan.nextInt();
            }catch(Exception ex){
                option=0;
            }
        
        
            switch(option){
                case 1:
                    run.addAlumno();
                    break;
                case 2:
                    do{
                        try{
                            run.findLesson().printInfo();
                        }catch(NullPointerException ex){
                            System.out.println(" Este master no ofrece dicha asignatura.");
                        }
                        Func.pressToContinue();
                        System.out.print("\n\nDesea consultar otra asignatura");
                    }while(Func.siNo());
                    break;
                case 3:
                    do{
                        run.printInfoAlumno();
                        Func.pressToContinue();
                        System.out.print("\n\nDesea consultar otro alumno");
                    }while(Func.siNo());
                    break;
                case 4:
                    run.tramiteMtricula();
                    break;
                case 5:
                    run.evaluar();
                    break;
                case 6:
                    writeAll(run);
                    Func.pressToContinue();
                    break;
                case 7:
                    writeAll(run);
                    alive=false;
                    break;
                default:
                    System.out.print("Opcion no valida. ");
                    Func.pressToContinue();

            }
        }while(alive);   
    }
    
    /***
     * Guarda el objeto master con tuda su informacion menos el plan de estudios
     * @param objeto master a guardar
     */
    private static void writeAll(MasterInformatica all){
        //MasterInformatica all = MasterInformatica.getInstance();
        try{
            FileOutputStream out = new FileOutputStream("datosAcademicos.dat");
            ObjectOutputStream writeObject = new ObjectOutputStream(out);
            writeObject.writeObject(all);
            writeObject.flush();
            writeObject.close();
            out.close();
            System.out.println("\n El guardado se realizo con exito.");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    /***
     * Carga la informacion de la aplicacion. 
     * @return objeto master leido.
     */
    private static MasterInformatica readAll() throws Exception{
        FileInputStream in = new FileInputStream("datosAcademicos.dat");
        ObjectInputStream readObject = new ObjectInputStream(in);
        MasterInformatica old = (MasterInformatica) readObject.readObject();
        readObject.close();
        in.close();
        return old;
    }
}
