/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import funcionalidad.Func;
/**
 *
 * @author Liam
 */
public class AlumnoTparcial extends Alumno implements Pupilo{
    
    
    public AlumnoTparcial(String dni, String nombre, String apellidos, String mail, String direccion, String telf){
        super(dni, nombre, apellidos, mail, direccion, telf);
    }
    
    @Override
    public boolean matricularse(Asignatura lesson, int anno){
        
        boolean status=false;
        
        if(this.checkYear(anno)){
            if(!(this.isPass(lesson))){
                if(30>=(this.creditosSuperados(String.valueOf(anno))+lesson.getETC())){
                    this.addAsignatura(lesson, String.valueOf(anno));
                    status = true;
                }else{
                    System.out.println("El numero de creditos anuales ha sido superado.");
                    Func.pressToContinue();
                }
            }else{
                System.out.println("La asignatura ya ha sido superada.");
                Func.pressToContinue();
            }
        }else{
            System.out.println("Las actas del año "+anno+" se encuantran cerradas.");
            Func.pressToContinue();
        }
        return status;
    }
    
    @Override
    public void printInfo(){
        super.printHistorial("Tiempo parcial.");
    }

    
}
