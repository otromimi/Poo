package addons;


import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Guillermo Leiro Arroyo
 */
public class Func{
    
    /***
     * Bifurcador dicotomico: pregunta por si o no y debuelve el input. 
     * @return true si se introduce S o Enter y false en caso contrario.
     */
    public static boolean siNo(){
        Scanner scan =new Scanner(System.in);
        String input=null;
        boolean redo, val=true;

        do{
            System.out.print(" S/n: ");
            input=scan.nextLine();
            switch (input) {
                case "":
                case "s":
                case "S":
                    redo=false;
                    val=true;
                    break;
                case "n":
                case "N":
                    redo=false;
                    val=false;
                    break;
                default:
                    System.out.print("\n Opcion erronea intentelo de nuevo ");
                    redo=true;
                    break;
            }
        }while(redo==true);
        return val;
    }
    
    /***
     * Imprime un mensaje por pantalla y espera a un intro para proseguir la ejecucion.
     */
    public static void pressToContinue(){
        Scanner scan =new Scanner(System.in);
        System.out.print("Presione intro para continuar...");
        scan.nextLine();
    }
    
    /***
     * Chequea si el dni es correcto.
     * @param id == string con el dni.
     * @param fullcheck == if true chequea que el digito de control sea el correcto.
     * @return true si el dni es correcto o false en caso contrario
     * @pre el dni ha de ser de 9 caracteres de largo con el digito de control en mayuscula.
     */
    public static boolean checkID(String id, boolean fullcheck){
        String control = "TRWAGMYFPDXBNJZSQVHLCKE";
        boolean status=false;
        if(id.length()==9){
            try{
                    int numeros = Integer.parseInt(id.substring(0, 8));
                if(fullcheck){
                    if((control.charAt((numeros%23))==id.charAt(8)))
                        status=true;
                }else{
                    if(control.contains(String.valueOf(id.charAt(8))))
                        status=true;
                }
            }catch(Exception ex){
                status = false;
            }
            
        }
        return status;
    }
    
}
    