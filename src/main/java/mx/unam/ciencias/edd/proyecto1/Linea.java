package mx.unam.ciencias.edd.proyecto1;

import java.text.Collator;

/**
 * Clase auxiliar que nos permite establecer la manera en que 
 * se va a ordenar las lineas de cada archivo.
 * @author Julián Rosas Scull
 */

public class Linea implements Comparable<Linea> {

    /**
     *  Renglón pasado para ser ordenado lexicográficamente.
     */

    private String linea;

    /**
     * Construye una línea por medio de un String pasado.
     * @param linea la cadena a ser procesada como línea.
     */

    public Linea(String linea){
        this.linea = linea;
    }

    /**
     * Regresa el string de la línea.
     * @return el string de la línea.
     */

    public String getLinea(){
        return this.linea;
    }
    
    /**
     * Nos dice si una linea es igual a otra.
     * @return <code>true</code> si la línea es igual al objeto recibido. 
     */
    
    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass())
            return false;
        Linea l = (Linea)o;
        return this.linea.equals(l.linea);
    }

    /**
     * Establece la comparación lexicográfica de una línea con otra por medio 
     * de la clase auxiliar {@link Collator}.
     * @param l linea a ser comparada.
     * @return <ol>
     *              <li> 0 si son iguales. </li>
     *              <li> mayor a 1 si la línea actual es mayor (lexicográficamente) que la pasada.</li>
     *              <li> menor a 1 si la línea actual es menor (lexicográficamente) que la pasada.</li>
     *         </ol>
     */

    @Override
    public int compareTo(Linea l){
        Collator comparador = Collator.getInstance();
        comparador.setStrength(Collator.PRIMARY);
        return comparador.compare(this.linea.replaceAll("\\P{L}+", ""), l.linea.replaceAll("\\P{L}+", ""));
    }

}
