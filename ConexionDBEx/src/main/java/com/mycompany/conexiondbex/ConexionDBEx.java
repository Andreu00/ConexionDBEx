/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.conexiondbex;

import java.sql.*;
import java.util.Scanner;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolDataSource;

/**
 *
 * @author maget
 */
public class ConexionDBEx {

    static final String DB_URL = "jdbc:mysql://localhost:3306/jcvd";//URL para acceder a la base de datos
    static final String USER = "andreu";//Usuario de la base de datos con la que acceder
    static final String PASS = "1234";//Contraseña del usuario de la base de datos
    static final String QUERY = "SELECT * FROM videojuegos where Nombre = ? ";//Consulta para mostrar datos de un videojuego a través del nombre que le pasemos
    //En el parentesis de la consulta consta de null en caso de tener id y las interrogaciones que hacen referencia a cada uno de los parametros que hay que insertar para completar un libro
    static final String QUERYREGISTRO = "insert into videojuegos values (null, ?, ?, ?, ?, ?)";//Consulta para insertar un videojuego nuevo con sus valores
    static final String QUERYDELETE = "DELETE FROM videojuegos WHERE Nombre = ? ";//Eliminar libro de la Base de Datos a traves del titulo que indiquemos
    static PoolDataSource  pds;//Crear pool de conexiones para gestionar las conexiones en la Base de Datos
    
    
    public static void main(String[] args) {
        
    //METODO PARA BUSCAR JUEGO
        
        String nombreJuego;
        Scanner teclado=new Scanner(System.in);
        int seleccion=0;
        
        try
        {   
            pds = PoolDataSourceFactory.getPoolDataSource();//Instancia apra gestionar la base de datos 
            pds.setConnectionFactoryClassName("com.mysql.cj.jdbc.Driver");//Asiganr el nombre de la clase para controlarla
            pds.setURL(DB_URL);//Establecemos la URL de la conexion
            pds.setUser(USER);//Establecemos el usuario
            pds.setPassword(PASS);//Establecemos la contraseña
            
            pds.setInitialPoolSize(5);//Establece el tamaño del pool
            
            //Creamos un menú para indicar que quiere realizar el usuario
            System.out.println("Que quieres realizar en la Base de Datos?");
            System.out.println("1. Buscar Nombre");
            System.out.println("2. Lanzar Consulta");
            System.out.println("3. Nuevo registro por parametro");
            System.out.println("4. Nuevo registro por teclado");
            System.out.println("5. Eliminar Registro");
            System.out.print("Introduce la funcion: ");
            seleccion=teclado.nextInt();
            System.out.println("");
            
            //Switch para saber que realizar dependiendo de la opción que elija el usuario
            //llamando cada opcion a su metodo indicado
            switch(seleccion){
                case 1 -> {
                    teclado.nextLine();
                    System.out.print("Introduce el nombre de juego: ");
                    nombreJuego=teclado.nextLine();
                    
                    boolean retorno=buscaNombre(nombreJuego);
                    if(retorno==true){
                        System.out.println(nombreJuego+" existe en la BDD");
                    }else{
                        System.out.println("No se ha encontrado este juego");
                    }
                }
                
                case 2 -> lanzaConsulta();
                
                case 3 -> nuevoRegistroParametro("Assasins2", "Saltos2", "2023-02-16", "Ubisoft", "50");
                
                case 4 -> nuevoRegistroTeclado();  
                
                case 5 -> {
                    boolean eliminado=eliminarRegistro("Assasins2");
                    if(eliminado==true){
                        System.out.println("El juego se ha eliminado correctamente");
                    }else{
                        System.out.println("El juego no ha sido eliminado");
                    }
                    
                }
            }                
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //Metodo para buscar un videojuego por su nombre pasandole el nombre como parametro
    public static boolean buscaNombre(String nombreV){
        
        String nombre;
        boolean encuentra=false;//Valor que devolveremos en caso de encontrar o no el videojuego
        
        try
        {
            Connection conn=pds.getConnection();//Realizamos la conexion a la bdd
            PreparedStatement stmt =conn.prepareStatement(QUERY);//Preparamos la sentencia para pasarsela a la bdd con la consulta que deseemos          
            stmt.setString(1, nombreV);//Asignamos el valor de la consulta, pasandole 1 como parametro que deseamos introducir dependiendo de las interrogaciones que tengamos y el valor que queremos asignar
            ResultSet rs =stmt.executeQuery();//Ejecuta la consulta y almacena el resultado
            
            while(rs.next()){//Recorremos la base de datos mostrando los nombres que almacenamos en el rs
                nombre=rs.getString("Nombre");  //almacenamos el nombre del titulo del cual sea del campo Nombre
                if(nombre.equals(nombreV)){//Comparamos el nombre introducido con el recorrido en la bdd para comprobar si está o no
                    encuentra=true;//Si lo encuentra será true y saldremos del bucle para finalizarlo
                    break;
                }else{
                    encuentra=false;//Si no lo encuentra será false y terminara el bucle 
                }
            }            
            stmt.close();//cerramos la sentencia                
        }catch(SQLException e){
            e.printStackTrace();
        }       
        
        return encuentra;
    }
    
    public static void lanzaConsulta(){
        try
        {  
            Connection conn=pds.getConnection();
            Statement stmt =conn.createStatement();
            ResultSet rs =stmt.executeQuery(QUERY);
            
            //Meotodo para coger los datos del videojuego a través de su campo y mostrarlo
            while(rs.next()){
                System.out.println("ID: "+rs.getInt("Id"));
                System.out.println("Nombre: "+rs.getString("Nombre"));
                System.out.println("Gemero: "+rs.getString("Genero"));
                System.out.println("Fecha de lanzamiento: "+rs.getDate("FechaLanzamiento"));
                System.out.println("Compañia: "+rs.getString("Compañia"));
                System.out.println("Precio: "+rs.getFloat("Precio"));
                System.out.println("");
                              
            }                
            stmt.close();                
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void nuevoRegistroParametro(String nombre, String genero, String fecha, String compañia, String precio){
        //Metodo para añadir un nuevo videojuego a traves de los valores que se le pasan al llamar al metodo
        try
        {  
            Connection conn=pds.getConnection();
            PreparedStatement stmt =conn.prepareStatement(QUERYREGISTRO);
                  
            //Asignamos a cada interrogacion el valor que queremos adjudicarle
            stmt.setString(1, nombre);
            stmt.setString(2, genero);
            stmt.setString(3, fecha);
            stmt.setString(4, compañia);
            stmt.setString(5, precio);
            
            stmt.executeUpdate();//Ejecutamos para actualizar la lista
            System.out.println("Videjuego añadido");
                         
            stmt.close();                
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
    public static void nuevoRegistroTeclado(){
        //Metodo para añadir un nuevo videojuego a través de los datos que introduzca el usuario por teclado
        String nombre="", genero="",fecha="", compañia="", precio="";
        Scanner teclado=new Scanner(System.in);
        try
        {  
            Connection conn=pds.getConnection();
            PreparedStatement stmt =conn.prepareStatement(QUERYREGISTRO);
            System.out.println("Introduce los datos que te voy a pedir a continuacion:");
            
            //Recogemos y almacenamos los datos introducidos en cada variable de los datos del videojuego
            //a través del teclado
            System.out.print("Nombre: ");
            nombre=teclado.nextLine();
            
            System.out.print("Genero: ");
            genero=teclado.nextLine();
            
            System.out.print("Fecha de Lanzamiento(YYYY-MM-DD): ");
            fecha=teclado.nextLine();
            
            System.out.print("Compañia: ");
            compañia=teclado.nextLine();
            
            System.out.print("Precio: ");
            precio=teclado.nextLine();
            
            //Asignamos cada interrogacion con su debido parametro
            stmt.setString(1, nombre);
            stmt.setString(2, genero);
            stmt.setString(3, fecha);
            stmt.setString(4, compañia);
            stmt.setString(5, precio);
            
            stmt.executeUpdate();
            System.out.println("Videjuego añadido");
                         
            stmt.close();                
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
    public static boolean eliminarRegistro(String nombreV){
        
        boolean eliminado=false;
        try
        {
            Connection conn=pds.getConnection();
            PreparedStatement stmt =conn.prepareStatement(QUERYDELETE);
            stmt.setString(1, nombreV);
            //ResultSet rs =stmt.executeQuery(QUERY);
            
                                                
                //Bucle para el Statement normal
                /*while(rs.next()){
                    String nombre=rs.getString("Nombre");
                    if(!nombre.equals(nombreV)){
                        eliminado=true;
                        break;                        
                    }else{
                        eliminado=false;
                    }
                }*/
                
                //Metodo para saber si se ha eliminado el libro con el PreparedStatement
                int filas=stmt.executeUpdate();
                
                //Comprobacion si ha habido algun movimiendo de lineas en la bdd
                if(filas>0){
                    eliminado=true;
                }else{
                    eliminado=false;
                }
                
            stmt.close();                
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return eliminado;
    }
}
