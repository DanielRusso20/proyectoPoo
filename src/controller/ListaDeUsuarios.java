package controller;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Usuario;

public class ListaDeUsuarios {
    ArrayList <Usuario> listaUsuarios=new ArrayList<>() ;
    
    public void iniciarListaDeUsuarios(){
        listaUsuarios=new ArrayList();
    }
    
    public boolean usuarioExistente(String username){
        boolean existe=false;
        for(int cont=0;cont<listaUsuarios.size();cont++){
            if(listaUsuarios.get(cont).getUsername().equals(username)){
                existe=true;
            }
        }
        return existe;
    }
    
    public boolean crearUsuario(Usuario user, String username, String contrasena, String nombreCompleto, ArrayList<String> privilegio, String rol,String status) {
        Validador validar = new Validador();//Declaro objeto Validador
        boolean existe = usuarioExistente(username);//Declaro un booleano y le asigno una funcion para comprobar si un usuario existe en la lista
        boolean retornar=false;
        if (existe == true) {
            JOptionPane.showMessageDialog(null, "Ya existe un usuario con el nombre:" + username + ". Intente con otro nombre", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (!validar.validarConRegex(username,"^.{1,30}$","Nombre del usuario","Limite de caracteres sobrepasado, solo se puede ingresar de 5 a 30 caracteres")
                    || !validar.validarConRegex(contrasena,"^.{1,30}$", "Contraseña del usuario","Limite de caracteres sobrepasado, solo se puede ingresar de 5 a 30 caracteres")
                    || !validar.validarConRegex(nombreCompleto,"^[a-zA-Z ]{1,30}$","Nombre Verdadero del usuario","El nombre es invalido, solo se aceptan caracteres alfabeticos, con un limite de 5 a 30 caracteres")
                    || !validar.validarConRegex(rol,"^[a-zA-Z]{1,30}$","Rol del usuario","El rol es invalido, solo se aceptan caracteres alfabeticos, con un limite de 5 a 30 caracteres")
                    || !validar.validarConRegex(status,"^(true|false)$","Estado del usuario","El estado es invalido")) {
                return retornar;
            }
            else{
                boolean estado=true;
                try{
                    estado=Boolean.parseBoolean(status);
                }
                catch(Exception e){
                    estado=false;
                }
                Usuario usuario=new Usuario(username,contrasena,nombreCompleto,privilegio,rol,estado);
                listaUsuarios.add(usuario);
                retornar=true;
            }
        }
        return retornar;
    }
    
    public ArrayList<Usuario> listarUsuarios(){
        return listaUsuarios;
    }
    
    public Usuario listarUsuario(String username){
        for(Usuario encontrar:listaUsuarios){
            if(encontrar.getUsername().equals(username)){
                return encontrar;
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }
    
    public boolean modificarUsuarios(Usuario user,String oldUser,String newUser,String contrasena,String nombreCompleto,ArrayList<String> privilegio,String rol,String status){
        Validador validar = new Validador();
        Usuario usuarioExiste = listarUsuario(oldUser);
        boolean existeNombre = false;
        boolean retornar = false;
        if (usuarioExiste != null) {
            existeNombre = usuarioExistente(newUser);
            if (existeNombre == true) {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con el nombre:" + newUser + ". Intente con otro nombre", "Error", JOptionPane.ERROR_MESSAGE);
                return retornar;
            } else {
                if (!validar.validarConRegex(newUser,"^.{1,30}$","Nombre del usuario","Limite de caracteres sobrepasado, solo se puede ingresar de 5 a 30 caracteres")
                    || !validar.validarConRegex(contrasena,"^.{1,30}$", "Contraseña del usuario","Limite de caracteres sobrepasado, solo se puede ingresar de 5 a 30 caracteres")
                    || !validar.validarConRegex(nombreCompleto,"^[a-zA-Z ]{1,30}$","Nombre Verdadero del usuario","El nombre es invalido, solo se aceptan caracteres alfabeticos, con un limite de 5 a 30 caracteres")
                    || !validar.validarConRegex(rol,"^[a-zA-Z]{1,30}$","Rol del usuario","El rol es invalido, solo se aceptan caracteres alfabeticos, con un limite de 5 a 30 caracteres")
                    || !validar.validarConRegex(status,"^(true|false)$","Estado del usuario","El estado es invalido")) {
                    return retornar;
                } else {
                    boolean estado = true;
                    try {
                        estado = Boolean.parseBoolean(status);
                    } catch (Exception e) {
                        estado = false;
                    }
                    Usuario userModded = new Usuario(newUser, contrasena, nombreCompleto, privilegio, rol,estado);
                    for (int cont = 0; cont < listaUsuarios.size(); cont++) {
                        if (listaUsuarios.get(cont).getUsername().equals(oldUser)) {
                            listaUsuarios.set(cont,userModded);
                        }
                    }
                    retornar=true;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return retornar;
        }
        return retornar;
    }
    
    public boolean eliminarUsuarios(Usuario user,String nombreBorrar){
        Usuario usuarioExiste=listarUsuario(nombreBorrar);
        boolean retornar = false;
        if(usuarioExiste!=null){
            for(int cont=0;cont<listaUsuarios.size();cont++){
                if(listaUsuarios.get(cont).getUsername().equals(nombreBorrar)){
                    listaUsuarios.remove(cont);
                }
            }
            retornar=true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return retornar;
        }
        return retornar;
    }
    
    public boolean iniciarSesion(String user,String contrasena){
        boolean permitirUser=false;
        boolean permitirPass=false;
        boolean retornar=false;
        for(int cont=0;cont<listaUsuarios.size();cont++){
            if(listaUsuarios.get(cont).getUsername().equals(user)&&listaUsuarios.get(cont).getContrasena().equals(contrasena)){
                permitirUser=true;
                permitirPass=true;
            }
        }
        if(permitirUser==true && permitirPass==true){
            retornar=true;
        }
        return retornar;
    }
}
