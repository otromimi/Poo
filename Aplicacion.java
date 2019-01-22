

import java.io.*;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Liam
 */
public class Aplicacion {
    public static void main(String args[]){
       
       MasterInformatica master = Aplicacion.readAll();
       if(master == null){
           master = MasterInformatica.getInstance();
       }else{
            master.plan_estudios=new ArrayList<>();
            master.cargarPlan_estudios();
       }
       do{
            Aplicacion.mainMenu();
       }while(Aplicacion.runSelect(Aplicacion.getInput(),master));
    }
    
    /***
     * Registra la seleccion de una opcion de las ofertadas en el menu.
     * @return la opcion en cuestion.
     */
    private static int getInput(){
        Scanner scan = new Scanner(System.in);
        int opcion;
        do{
            System.out.print("  Elija una opcion: ");
            opcion = scan.nextInt();
            if(!(7>=opcion && 1<=opcion)){
                System.out.println("\tOpcion no valida.");
            }
        }while(!(7>=opcion && 1<=opcion));
        return opcion-1;
    }
    
    /***
     * Imprime las opciones de menu principal.
     */
    private static void mainMenu(){
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
        System.out.println();
    }
    
    /***
     * Ejecuta la opcion seleccionada.
     * @param seleccion == numero seleccionado
     * @return boolean == false si se quiere cerrar la aplicacinon
     */
    private static boolean runSelect(int seleccion, MasterInformatica master){
        //MasterInformatica master = MasterInformatica.getInstance();
        boolean alive=true;
        switch(seleccion){
            case 0:
                master.altaAlumno();
                System.out.flush();
                break;
            case 1:
                master.consultaAsignatura();
                break;
            case 2:
                master.printExpediente();
                break;
            case 3:
                master.tramiteMatricula();
                break;
            case 4:
                master.calificarAsignaturas();
                break;
            case 5:
                Aplicacion.writeAll(master);
                break;
            case 6:
                //Aplicacion.writeAll(master);
                System.out.println("---------------------");
                alive=false;
                break;
            default:
                
                
       }
        return alive;
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
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    /***
     * lee el objeto master 
     * @return objeto master
     */
    private static MasterInformatica readAll(){
        MasterInformatica all = null;
        try{
            FileInputStream in = new FileInputStream("datosAcademicos.dat");
            ObjectInputStream ois = new ObjectInputStream(in);
            all = (MasterInformatica) ois.readObject();
        }catch(Exception e){
            System.out.println(e);
        }
        return all;
    }
}
