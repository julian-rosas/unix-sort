package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.*;


/**
 * Clase que contiene al main, maneja el uso de las banderas y la relacion de ellas
 * con las demas clases en el paquete.
 * 
 * @author Julián Rosas Scull
 */

public class Proyecto1{

    /* Metodo que establece un error de escritura*/
    public static void usoEscritura(){
        System.err.println("la opcion -o requiere un argumento ");
        System.exit(1);

    }

    /* Metodo para leer varios archivos, procesarlos como uno y dependiendo de la bandera, la accion a realizar*/
    public static void escribirVarios(ArbolRojinegro<Linea> arbol , String bandera, Lista<String> archivosLeer, String archivoAescribir){
        arbol = ReaderWriter.readFiles(archivosLeer);
        ReaderWriter.writeFile(arbol, archivoAescribir, bandera);
    }


    /* Metodo que lee un solo archivo y lo procesa dependiendo de la bandera*/
    public static void escribirUno(ArbolRojinegro<Linea> arbol, String bandera, String archivoLeer, String archivoAescribir){
        arbol = ReaderWriter.readFile(archivoLeer);
        ReaderWriter.writeFile(arbol, archivoAescribir, bandera);
    }


    /* Metodo que lee la consola e imprime lo procesado por el arbol y dependiendo de la bandera*/
    public static void imprimirLeerTerminal(ArbolRojinegro<Linea> arbol, String bandera){
        arbol = ReaderWriter.readTerminal();
        ReaderWriter.printTerminal(arbol, bandera);
    }


    /* Metodo que lee un solo archivo , lo procesa y lo imprime en la consola dependiendo de la bandera*/
    public static void leerArchivoImprimir(ArbolRojinegro<Linea> arbol, String bandera, String archivoLeer){
        arbol = ReaderWriter.readFile(archivoLeer);
        ReaderWriter.printTerminal(arbol, bandera);
    }

    /* Metodo que lee varios archivos, los procesa como uno y dependiendo de la bandera imprime en al terminal*/
    public static void leerArchivosImprimir(ArbolRojinegro<Linea> arbol, String bandera, Lista<String> archivosLeer){
        arbol = ReaderWriter.readFiles(archivosLeer);
        ReaderWriter.printTerminal(arbol, bandera);
    }


    /* Metodo qye lee la consola y dependiendo de la bandera procesa lo pasado y lo guarda en un archivo*/
    public static void leerTerminalEscribir(ArbolRojinegro<Linea> arbol, String archivoEscribir, String bandera){
        arbol = ReaderWriter.readTerminal();
        ReaderWriter.writeFile(arbol, archivoEscribir, bandera);
    }

    public static void main(String[] args) {

        ArbolRojinegro<Linea> arbol = new ArbolRojinegro<>();
        
        if(args.length == 0){
            arbol = ReaderWriter.readTerminal();
            ReaderWriter.printTerminal(arbol,null);
        }else if(args.length == 1){

            switch (args[0]) {
                case ("-r"):

                    // caso reversa estandar
                    imprimirLeerTerminal(arbol, "-r");
                    break;
                case ("-o"):

                    // caso escribir archivo invalido
                    usoEscritura();
                    break;
                default: 

                    // caso archvio solo
                    leerArchivoImprimir(arbol, null, args[0]);
                    break;
            }

        }else if(args.length > 1){

            Lista<String> lista = new Lista<>();

            // usamos lista para tener una estructura mas dinamica.
            for (int i = 0; i < args.length; i++) {
                lista.agrega(args[i]);
            }


            // caso 1, contiene los argumentos -r y no contiene -o
            if (lista.contiene("-r") && !lista.contiene("-o")) {
                lista.elimina("-r");

                if (lista.getLongitud() == 1) {

                    // leer terminal -r
                    leerArchivoImprimir(arbol, "-r", lista.getPrimero());
                } else if (lista.getLongitud() > 1) {
                     
                    // leer archivos -r
                    leerArchivosImprimir(arbol, "-r", lista);
                }

            // caso 2, contiene -o pero no -r los argumentos    
            } else if (!lista.contiene("-r") && lista.contiene("-o")) {


                // caso 2.1 -o es indice 0 de los argumentos
                if (lista.indiceDe("-o") == 0) {
                    lista.elimina("-o");

                    // caso 2.1.1 -o solo tiene un argumento (archivo) y no es -r
                    if (lista.getLongitud() == 1) {

                        leerTerminalEscribir(arbol, lista.getPrimero(), "-o");

                    // caso 2.1.2 -o tiene mas de un argumento y no es -r
                    } else if (lista.getLongitud() > 1) {

                        //caso 2.1.2.1 -o esta repetido y puede (o no) ser el archivo a escribir 
                        if(lista.contiene("-o")){
                            String archivo1 = lista.getPrimero();

                            // caso 2.1.2.1.1 -o es el archivo a escribir
                            if(archivo1.equals("-o")){
                                lista.eliminaPrimero();
                                escribirVarios(arbol, "-o", lista, archivo1);

                            // caso 2.1.2.1.2 -o no es el archivo a escrbir, invalido.
                            }else{
                                usoEscritura();
                            }

                            // caso 2.1.2.2 -o no esta repetido
                        }else{
                            String archivo1 = lista.getPrimero();
                            lista.eliminaPrimero();
                            escribirVarios(arbol, "-o", lista, archivo1);
                        }

                    }
                
                    // caso 2.2 -o no es indice 0 de los argumentos
                }else{
                    int indiceO = lista.indiceDe("-o");

                    String archivoGuardar = null;

                    try{
                        archivoGuardar  = lista.get(indiceO + 1);
                    }catch(ExcepcionIndiceInvalido e){
                        usoEscritura();
                    }
               
                    lista.elimina(archivoGuardar);
                    lista.elimina("-o");

                    if(archivoGuardar.equals("-o")){
                        if(lista.contiene("-o")){
                            usoEscritura();
                        }
                        escribirVarios(arbol, "-o", lista, archivoGuardar);
                        
                    }else{

                        if(lista.contiene("-o")){
                            usoEscritura();
                        }
                        
                        escribirVarios(arbol, "-o", lista, archivoGuardar);
                        
                    }

                }

            // caso 3, los argumentos no contienen ni a -r ni a -o, solo archivos
            }else if(!lista.contiene("-r") && !lista.contiene("-o")){
                leerArchivosImprimir(arbol, null, lista);
                
            // caso 4, los argumentos contienen a -r y a -o (-r puede ser el nombre de un archivo)
            }else if(lista.contiene("-r") && lista.contiene("-o")){

                //caso 4.1 -o es el indice 0 en los argumentos
                if(lista.indiceDe("-o") == 0){
                    lista.elimina("-o");

                    //caso 4.1.1 -r es el nombre del archivo
                    if(lista.getLongitud() == 1){
                        leerTerminalEscribir(arbol, lista.getPrimero(), "-o");

                    //caso 4.1.2 -r puede (o no) ser el nombre del archivo, si es, no aplica reversa, de otro modo, aplica reversa
                    }else{

                        if(lista.contiene("-o")){
                            String archivoGuardar = lista.getPrimero();
                            if(archivoGuardar.equals("-o")){
                                lista.eliminaPrimero();

                                if(lista.contiene("-o")){
                                    usoEscritura();
                                }

                                escribirVarios(arbol, "-r", lista, archivoGuardar);
                            }else if(archivoGuardar.equals("-r")){
                                lista.eliminaPrimero();

                                if(lista.contiene("-o")){
                                    usoEscritura();
                                }

                                if(lista.contiene("-r")){
                                    escribirVarios(arbol, "-r", lista, archivoGuardar);
                                }

                                escribirVarios(arbol, "-o", lista, archivoGuardar);
                            }else{
                                usoEscritura();
                            }
                        }else{

                            String archivoGuardar = lista.getPrimero();
                            lista.elimina(archivoGuardar);
                            escribirVarios(arbol, "-r", lista, archivoGuardar);
                        }
                    }

                // caso 4.2 -o no es el indice 0 de los argumentos 
                }else{

                    if(lista.getPrimero().equals("-r")){
                        if(lista.getLongitud() == 2){
                            usoEscritura();
                        }else{
                            int indiceO = lista.indiceDe("-o");
                            String archivoGuardar = null;

                           try{
                                archivoGuardar = lista.get(indiceO + 1);
                           }catch(ExcepcionIndiceInvalido e){
                                usoEscritura();
                           }

                            lista.elimina(archivoGuardar);
                            lista.elimina("-o");
                            lista.elimina("-r");

                            if(lista.contiene("-o") && archivoGuardar.equals("-o")){

                                escribirVarios(arbol, "-r", lista, archivoGuardar);

                            }else{  
                                usoEscritura();
                            }
                        }
                    }else{
                        int indiceO = lista.indiceDe("-o");
                        String archivoGuardar = null;

                        try{
                            archivoGuardar = lista.get(indiceO + 1);
                        }catch(ExcepcionIndiceInvalido e){
                            usoEscritura();
                        }

                        lista.elimina(archivoGuardar);
                        lista.elimina("-o");

                        if(archivoGuardar.equals("-r")){
                            lista.elimina("-r");

                            if (lista.contiene("-o")) {
                                usoEscritura();
                            }

                            if(lista.contiene("-r")){
                                lista.elimina("-r");

                                escribirVarios(arbol, "-r", lista, archivoGuardar);
                            }else{
                                escribirVarios(arbol, "-o", lista, archivoGuardar);
                            }
                        }else if(archivoGuardar.equals("-o")){
                            lista.elimina("-r");
                            
                            if(lista.contiene("-o")){
                                usoEscritura();
                            }

                            escribirVarios(arbol, "-r", lista, archivoGuardar);
                            
                        }else{
                            lista.elimina("-r");
                            lista.elimina("-o");

                            if(lista.contiene("-o")){
                                usoEscritura();
                            }

                            escribirVarios(arbol, "-r", lista, archivoGuardar);
                        }

                    }
                
                }

            }

        }
    }
}