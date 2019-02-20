package estudiante;


/**
 * @version 2
 * @author Guillermo Leiro Arroyo
 */
public interface Pupilo{
    
    /***
     * Coge el DNI del alumno que lo invoque.
     * @return DNI
     */
    public String getId();
    
    /***
     * Imprime la informacion del alumno.
     */
    public void printInfo();
    
    /***
     * Realiza la matriculacion del alumno en la asignatura indicada.
     * @return true si la matriculacion se realizo con exito.
     */
    public boolean matricularse();
    
    /***
     * Lleva a cabo la evaluacion de un alumno.
     * @return true si tras la evaluacion el alumno esta matriculado.
     * @post el alumno modificaco devera de pasar a alumnoFinalizado.
     */
    public boolean evaluar();

    /***
     * Pasa un alumno a graduado.
     * @return un objeto de alumnoFinalizado con los datos del alumno que lo inboque.
     */
    public Pupilo promocion();
}
