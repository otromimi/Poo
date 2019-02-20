package estudiante;



import master.*;
import lesson.*;
import java.io.Serializable;
import java.time.Year;
import java.util.Scanner;

/**
 * @version 2
 * @author Guillermo Leiro Arroyo
 */
public class AlumnoT_parcial extends Alumno implements Pupilo, Serializable{

    public AlumnoT_parcial(String id, String nombre, String apellidos, String direccion, String mail, String telf) {
        super(id,nombre,apellidos,direccion,mail,telf);
    }
    
    @Override
    public void printInfo(){
        System.out.println("Tipo alumno: Tiempo parcial.");
        super.printInfo();
    }
    
    @Override
    public boolean matricularse() {
        MasterInformatica master = MasterInformatica.getInstance();
        Scanner scan = new Scanner(System.in);
        boolean status=false, flag=true;
        
        System.out.print("Introduzca el año de matriculacion: ");
        Year annio = Year.of(scan.nextInt());
        
        if(!expediente.isEmpty()){
            if(expediente.get(expediente.size()-1).getYear().compareTo(annio)>0){
               flag=false;
               System.out.println("Las actas para el año academico introducido se encuntran cerradas.");
            }
        }
        if(flag){
            Asignatura lesson = master.findLesson();
            if(lesson!=null){
                if(creditosExcedidos(lesson,annio)){
                    if(greenLight(lesson)){
                        status = this.expediente.add(new AsignaturaMatriculada(lesson,annio));
                    }else{
                        System.out.println("La asignatura ha sido superada o tiene una combocatoria activa.");
                    }
                }else{
                    System.out.println("Maximo de creditos anuales excedido.");
                }
            }else{
                System.out.println("Asignatura no ofertada en el master.");
            }
        }
        return status;
    }
    
    /***
     * Chequea si se ha escedido el numero de creditos para este tipo de alumno en un determinado año academico.
     * @param lesson asignatura que se quiere añadir.
     * @param annio año al que se quiere añadir la asignatura.
     * @return true si es factible el añadir la asignatura sin rebasar el limite de creditos.
     */
    private boolean creditosExcedidos(Asignatura lesson, Year annio){
        int cont=lesson.getCreditos();
        for(AsignaturaMatriculada y: expediente){
            if(y.getYear().equals(annio))
                cont+=y.getLesson().getCreditos();
        }
        return 30>=cont;
    }
    
}
