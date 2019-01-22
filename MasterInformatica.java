
import java.io.*;
import java.util.*;
import funcionalidad.Func;
/**
 *
 * @author Liam
 */
public class MasterInformatica implements Serializable{
    transient static ArrayList<Asignatura> plan_estudios = new ArrayList<>();
    private ArrayList<Pupilo> estudiantes = new ArrayList<>();  
    private static MasterInformatica instancia = null;
    //enum tipologiaAlumno {completo,parcial}
    
    
    
    private MasterInformatica(){
        this.cargarPlan_estudios();
    }
    
    /***
     * Metodo distintivo clase singelton.
     * @return el unico objeto instanciable de esta clase.
     */
    public static MasterInformatica getInstance(){
        if(instancia == null){
            instancia=new MasterInformatica();
        }
        return instancia;
    }
    
    /***
     * Imprime el expediente de un alumno.
     * @post has de selecionar un alumno dado de alta en el master.
     */
    public void printExpediente(){
        int num = this.searchEstudiante();
        System.out.println();
        if(num!=-1){
            estudiantes.get(num).printInfo();
            if(this.graduado(estudiantes.get(num)))
                System.out.print(" El alumno esta graduado con una nota media de ");
            else
                System.out.print(" El salumno se encuentra realizando el master con una nota media de ");
            System.out.print(estudiantes.get(num).notaMedia()+"\n");
        }else{
            System.out.println("El DNI introducido no pertenece a ningun estudiante.");
        }
        Func.pressToContinue();
        
    }
    
    /***
     * Añade la calificacion a las asignaturas de un año.
     */
    public void calificarAsignaturas(){
        int N_alumno=this.searchEstudiante();
        estudiantes.get(N_alumno).calificar();
    }    
    
    /***
     * Metodo para tramitar una nueva matricula de un alumno ya existente.
     */
    public void tramiteMatricula(){
        int N_alum, i;
        boolean added=false;
        Scanner scan = new Scanner(System.in);
        N_alum = this.searchEstudiante();
        if(N_alum>-1){
            if(!(this.graduado(estudiantes.get(N_alum)))){
                System.out.print("Introduzca el codigo de una asignatura: ");
                String idLesson = scan.nextLine();
                for(Asignatura x: plan_estudios){
                   if(x.getCodigo().equals(idLesson)){
                        System.out.print("Introduzca el año de la matriculacion: ");
                        estudiantes.get(N_alum).matricularse(x, scan.nextInt());
                        added=true;
                        break;
                    }
          
                }
               
                if(!added){
                    System.out.println("El maste no oferta la siguiente asignatura "+idLesson);
                    Func.pressToContinue();
                }
            }else{
                System.out.println("Este alumno ya se encuantra graduado.");
                Func.pressToContinue();
            }
        }else{
            System.out.println("El DNI introducido no pertenece a ningun alumno.");
            Func.pressToContinue();
        }
    }
    
    /***
     * Metodo para dar de alta a un nuevo alumno.
     */
    public void altaAlumno(){
        String dni;
        String info[] =new String[5];
        Scanner scan = new Scanner(System.in);
        System.out.print("Introduzca el DNI: ");
        dni=scan.nextLine();
        if(Func.checkID(dni, true)){
            if(this.searchEstudiante(dni)==-1){
                System.out.print("Introduzca su nombre: ");
                info[0]=scan.nextLine();
                System.out.print("Introduzca sus apellidos: ");
                info[1]=scan.nextLine();
                System.out.print("Introduzca su E-mail: ");
                info[2]=scan.nextLine();
                System.out.print("Introduzca su direccion: ");
                info[3]=scan.nextLine();
                System.out.print("Introduzca su telefono: ");
                info[4]=scan.nextLine();
                System.out.print("¿Tiempo parcial?(por defecto se pondra tiempo completo)");
                if(Func.siNo()){
                    estudiantes.add(new AlumnoTparcial(dni,info[0],info[1],info[2],info[3],info[4]));
                }else{
                    estudiantes.add(new AlumnoTcompleto(dni,info[0],info[1],info[2],info[3],info[4]));
                }
                System.out.println("\n\nEl alumno "+dni+" ha ingreasado satisfactoriamente.");
                Func.pressToContinue();
            }else{
                System.out.println("El DNI "+dni+" ye se encuantra dado de alta.");
                Func.pressToContinue();
            }
        }else{
            System.out.println("DNI no valido.");
            Func.pressToContinue();
        }
    }
    
    /***
     * Metodo para consultar una saiganatura de las ofrecidas en el master.
     */
    public void consultaAsignatura(){
        Scanner scan = new Scanner(System.in);
        String lookFor = null;
        boolean isIn;
        int i;

        do{
            System.out.print("Introduzca el codigo de la asignatura a consultar: ");
            lookFor = scan.nextLine();
            for(i=0, isIn=false; (isIn==false) && (i<plan_estudios.size());i++){
                if(plan_estudios.get(i).getCodigo().equals(lookFor)){
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    plan_estudios.get(i).printInfo();
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    isIn=true;
                }
            }

            if(false!=isIn){
                System.out.print("\n Desea consultar otra asignatura");
                isIn=Func.siNo();
            }else{
                System.out.println("No se encontro ninguna asignatura con el codigo "+lookFor);
                Func.pressToContinue();
            }
        }while(isIn==true);

    }
    
    /***
     * Carga los datos de las asignaturas ofrecidas por el master.
     */
    public void cargarPlan_estudios(){
        
        String line=null;
          
        try {
            BufferedReader br =  new BufferedReader(new FileReader("planEstudios.txt"));
            while((line = br.readLine()) != null){
                String[] partes = line.split("-");
                plan_estudios.add(new Asignatura(partes[0], partes[1], partes[2], Integer.parseInt(partes[3])));
            }
            br.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("No se pudo abrir el archivo: 'planEstudios.txt'");                
        }
        catch(IOException ex) {
            System.out.println("Error leyendo el archivo: 'planEstudios.txt'");                  
        }
    }
    
    /***
     * Busca a un estudiante dentro del array list por su DNI
     * @return la posicion si lo encuentra o -1 si no lo encuantra.
     */
    private int searchEstudiante(){
        String dni;
        Scanner scan = new Scanner(System.in);
        System.out.print("Introduzca el DNI: ");
        dni=scan.nextLine();
        return this.searchEstudiante(dni);
    }
    
    /***
     * Metodo polimorfico de SearchEstudiante()
     * @param dni
     * @return -1 si no se encuentra entre los alumnos registrados // posicion en el array si si se encuantra
     */
    private int searchEstudiante(String dni){
        if(estudiantes.isEmpty()==false){
            for(int i=0;i<estudiantes.size();i++){
                if(estudiantes.get(i).getDNI().equals(dni)){
                    return i;
                }
            }
        }
        return -1;
    }
    
    /***
     * Indica si el alumno que se le pasa esta graduado o no.
     * @param sujeto
     * @return true == esta graduado
     */
    private boolean graduado(Pupilo sujeto){
        int acum=0;
        for(Asignatura x: plan_estudios){
            if(x.getTipo().equals("OB")){
                acum+=x.getETC();
            }
        }
        return acum<=sujeto.creditosSuperados("OB") && sujeto.numeroOPsuperadas()>=2;
    }
    
    public static int getNumeroAsignaturas(){
        return plan_estudios.size();
    }
}
