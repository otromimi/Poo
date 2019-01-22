/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Liam
 */
public interface Pupilo {
    
    public boolean matricularse(Asignatura lesson, int anno);
    
    public String getDNI();
    
    public int numeroOPsuperadas();
    
    public int creditosSuperados(String tipo);
    
    public void calificar();
    
    public void printInfo();
    
    public int notaMedia();
}
