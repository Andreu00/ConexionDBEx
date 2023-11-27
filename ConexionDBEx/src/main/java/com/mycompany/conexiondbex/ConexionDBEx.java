/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.conexiondbex;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author maget
 */
public class ConexionDBEx {

    static final String DB_URL = "jdbc:mysql://localhost:3306/jcvd";
    static final String USER = "andreu";
    static final String PASS = "1234";
    static final String QUERY = "SELECT * FROM videojuegos where Nombre = ? ";
    static final String QUERYREGISTRO = "insert into videojuegos values (null, ?, ?, ?, ?, ?";
    
    
    public static void main(String[] args) {
        
    //METODO PARA BUSCAR JUEGO
        
        String nombreJuego;
        Scanner teclado=new Scanner(System.in);
        int seleccion=0;
        
        try
        {
            
            
            System.out.println("Que quieres realizar en la Base de Datos?");
            System.out.println("1. Buscar Nombre");
            System.out.println("2. Lanzar Consulta");
            System.out.println("3. Nuevo registro por parametro");
            System.out.println("4. Nuevo registro por teclado");
            System.out.println("5. Eliminar Registro");
            System.out.print("Introduce la funcion: ");
            seleccion=teclado.nextInt();
            System.out.println("");
            
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
                    boolean eliminado=eliminarRegistro("Assasins");
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
    
    public static boolean buscaNombre(String nombreV){
        
        String nombre;
        boolean encuentra=false;
        
        try
        {
            Connection conn=DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt =conn.prepareStatement(QUERY);            
            stmt.setString(1, nombreV);
            ResultSet rs =stmt.executeQuery();
            while(rs.next()){
                nombre=rs.getString("Nombre");  
                if(nombre.equals(nombreV)){
                    encuentra=true;
                    break;
                }else{
                    encuentra=false;
                }
            }            
            stmt.close();                
        }catch(SQLException e){
            e.printStackTrace();
        }       
        
        return encuentra;
    }
    
    public static void lanzaConsulta(){
        try
        {  
            Connection conn=DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt =conn.createStatement();
            ResultSet rs =stmt.executeQuery(QUERY);
            
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
        
        try
        {  
            Connection conn=DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt =conn.prepareStatement(QUERYREGISTRO);
                  
            
            stmt.setString(1, nombre);
            stmt.setString(2, genero);
            stmt.setString(3, fecha);
            stmt.setString(4, compañia);
            stmt.setString(5, precio);
            
            stmt.executeUpdate(QUERYREGISTRO);
            System.out.println("Videjuego añadido");
                         
            stmt.close();                
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
    public static void nuevoRegistroTeclado(){
        String nombre="", genero="",fecha="", compañia="", precio="";
        Scanner teclado=new Scanner(System.in);
        try(
            Connection conn=DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt =conn.createStatement();
            ResultSet rs =stmt.executeQuery(QUERY);)
        {  
            System.out.println("Introduce los datos que te voy a pedir a continuacion:");
            
            System.out.print("Nombre: ");
            nombre=teclado.nextLine();
            System.out.println("\n");
            
            System.out.print("Genero: ");
            genero=teclado.nextLine();
            System.out.println("\n");
            
            System.out.print("Fecha de Lanzamiento(YYYY-MM-DD): ");
            fecha=teclado.nextLine();
            System.out.println("\n");
            
            System.out.print("Compañia: ");
            compañia=teclado.nextLine();
            System.out.println("\n");
            
            System.out.print("Precio: ");
            precio=teclado.nextLine();
            System.out.println("\n");
            
            String query="INSERT INTO `videojuegos` (`id`, `Nombre`, `Genero`, `FechaLanzamiento`, `Compañia`, `Precio`) VALUES (NULL, '"+nombre+"', '"+genero+"', '"+fecha+"', '"+compañia+"', '"+precio+"')";
            stmt.executeUpdate(query);
            System.out.println("Videjuego añadido");
                         
            stmt.close();                
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
    public static boolean eliminarRegistro(String nombreV){
        
        boolean eliminado=false;
        try(
            Connection conn=DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt =conn.createStatement();
            ResultSet rs =stmt.executeQuery(QUERY);)
        {
                String query="DELETE FROM `videojuegos` WHERE `nombre` = '"+nombreV+"'";
                stmt.executeUpdate(query);
                
                while(rs.next()){
                    String nombre=rs.getString("Nombre");
                    if(!nombre.equals(nombreV)){
                        eliminado=true;
                        break;                        
                    }else{
                        eliminado=false;
                    }
                }
            stmt.close();                
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return eliminado;
    }
}
