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
public class AlumnoTcompleto extends Alumno implements Pupilo{
    
    public AlumnoTcompleto(String dni, String nombre, String apellidos, String mail, String direccion, String telf){
        super(dni, nombre, apellidos, mail, direccion, telf);
    }
    
    @Override
    public boolean matricularse(Asignatura lesson, int anno){
    
        boolean status=false;
        if(this.checkYear(anno)){
            if(!(this.isPass(lesson))){
                this.addAsignatura(lesson, String.valueOf(anno));
                status = true;
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
        super.printHistorial("Tiempo completo.");
    }
    
}
