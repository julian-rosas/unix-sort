package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;


/**
 * Clase encargada de la interpretación de la entrada y salida de los argumentos,
 * incluyendo la administración de banderas en cuanto a entrada y salida.
 * 
 * @author Julián Rosas Scull
 */

public class ReaderWriter {

    /**
     * Evitamos instanciación 
     */

    public ReaderWriter(){}


    /**
     *  Lectura individual de un archivo adquiriendo sus lineas para ordenarlos lexicográficamente
     *  por medio de un árbol Rojinegro.
     * @param filename el archivo que se va a leer linea por linea. 
     * @return un {@link ArbolRojinegro}, archivo ordenado linea por linea.
     */

    public static ArbolRojinegro<Linea> readFile(String filename) {

        ArbolRojinegro<Linea> arbol = new ArbolRojinegro<Linea>();

        BufferedReader buf = null;

        String linea;

        FileReader fr = null;
        
        try{
            fr = new FileReader(filename);
            buf = new BufferedReader(fr);
            linea = buf.readLine();

            while(linea != null){
                arbol.agrega(new Linea(linea));
                linea = buf.readLine();
            }
        }catch(IOException io){
            System.out.println(io);
        }finally{
            try{
                if(fr != null){
                    fr.close();
                }

                if(buf != null){
                    buf.close();
                }
            }catch(IOException io){
                System.out.println(io);
            }
        }

        return arbol;
      
    }


    /**
     * Lectura de varios archivos procesados como uno solo para ordenar lexicográficamente por medio
     * de un árbol rojinegro.
     * @param files archivos que se van a leer linea por linea.
     * @return un {@link ArbolRojinegro} con las lineas de los archivos ya ordenadas lexicográficamente.
     */

    public static ArbolRojinegro<Linea> readFiles(Lista<String> files){


        ArbolRojinegro<Linea> arbolFinal = new ArbolRojinegro<>();

        BufferedReader buf = null;

        String linea;

        FileReader fr = null;


        try{

            for(String file : files){

                fr = new FileReader(file);

                buf = new BufferedReader(fr);

                linea = buf.readLine();

                while(linea != null){
                    arbolFinal.agrega(new Linea(linea));
                    linea = buf.readLine();
                }
            }

        }catch(IOException io){
            System.out.println(io);
        }finally{
            try{
                if(fr != null){
                    fr.close();
                }

                if(buf != null){
                    buf.close();
                }
            }catch(IOException io){
                System.out.println(io);
            }
        }

        return arbolFinal;

    }


    /**
     * Escritura sobre un archivo pasado.
     * @param arbol líneas ya ordenadas lexicográficamente por medio de un {@link ArbolRojinegro}.
     * @param filename archivo dónde se va a guardar las líneas ordenadas.
     * @param bandera para conocer si se debe escribir inversamente.
     */

    public static void writeFile(ArbolRojinegro<Linea> arbol, String filename, String bandera){

        FileWriter out = null;

        try{
            out = new FileWriter(filename, true);
            if(bandera == "-o"){

                for(Linea l : arbol){
                    try{
                        out.write(l.getLinea() + "\n");
                    }catch(IOException io){
                        System.out.println(io);
                    }
                }

                
            }else if(bandera == "-r"){
                Lista<Linea> lista = new Lista<>();

                for(Linea l : arbol){
                    lista.agregaInicio(l);
                }



                for(Linea l : lista){
                    try{
                        out.write(l.getLinea() + "\n");
                    }catch(IOException io){
                        System.out.println(io);
                    }
                }
            }
        }catch(IOException io){
            System.out.println(io);
        }finally{
            try{
                if(out != null){
                    out.close();
                }
            }catch(IOException io){
                System.out.println(io);
            }
        }

    }


    /**
     * Lectura de la terminal para ser procesada y ordenada de manera lexicográfica.
     * @return  un {@link ArbolRojinegro} con los datos pasados por medio de la entrada estándar 
     */

    public static ArbolRojinegro<Linea> readTerminal() {

        ArbolRojinegro<Linea> arbol = new ArbolRojinegro<>();

        BufferedReader buf = null;

        InputStreamReader in = null;

        String linea;


        try{
            in = new InputStreamReader(System.in);
            buf = new BufferedReader(in);
               
            linea = buf.readLine();

            while(linea != null){
                arbol.agrega(new Linea(linea));
                linea = buf.readLine();
            }

        }catch(IOException io){
            System.out.println(io);
        }finally{
            try{
                if(in != null){
                    in.close();
                }

                if(buf != null){
                    buf.close();
                }

            }catch(IOException io){
                System.out.println(io);
            }
        }
        
        return arbol;

    }


    /**
     * Impresión de los datos ya ordenados lexicográficamente. 
     * @param arbol datos almacenados en un {@link ArbolRojinegro} ya ordenados lexicográficamente. 
     * @param bandera para saber si se debe imprimir inversamente.
     */

    public static void printTerminal(ArbolRojinegro<Linea> arbol, String bandera){
        if(bandera == null){
            for(Linea l : arbol){
                System.out.println(l.getLinea());
            }

        }else if(bandera == "-r"){
            Lista<Linea> l_reversa = new Lista<>();

            for(Linea l : arbol){
                l_reversa.agregaInicio(l);
            }

            for(Linea l : l_reversa){
                System.out.println(l.getLinea());
            }

        }

    }


}
