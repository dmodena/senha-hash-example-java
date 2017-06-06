/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Usuario;

/**
 *
 * @author Micronos
 */
public class Main {
    public static void main(String[] args) {
        List<Usuario> usuarios = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        
        char option = 'i';
        do {
            listarOpcoes();
            option = sc.nextLine().charAt(0);
            
            switch (option) {
                case 'c':
                    Usuario usuario = null;
                    
                    do {
                        usuario = cadastrarUsuario();
                    } while(usuario == null);
                    
                    usuarios.add(usuario);
                    break;
                case 'l':
                    logarUsuario(usuarios);
                    break;
                default:
                    break;
            }
            
            listarUsuarios(usuarios);            
        } while(option != 's');
        
        System.out.println("\nSaindo...\n");
    }
    
    public static void listarOpcoes() {
        System.out.println("Escolha uma opção:");
        System.out.println("c - Cadastrar usuário");
        System.out.println("l - Logar usuário");
        System.out.println("s - Sair");
    }
    
    public static Usuario cadastrarUsuario() {
        Scanner sc = new Scanner(System.in);
        
        Usuario usuario = null;
        String email, senha, confirmaSenha = null;
        
        System.out.print("Email: ");
        email = sc.nextLine();
        System.out.print("Senha: ");
        senha = sc.nextLine();
        System.out.print("Confirme a senha: ");
        confirmaSenha = sc.nextLine();
        
        if (email != null 
                && senha != null 
                && confirmaSenha != null 
                && senha.equals(confirmaSenha)) {
            
            SecureRandom random = new SecureRandom();
            byte[] saltb = random.generateSeed(32);
            String salt = toHexString(saltb);
//            String salt = new String(random.generateSeed(32), StandardCharsets.UTF_8);
            
            MessageDigest digest;
            String saltAndPwd = new StringBuilder()
                    .append(salt)
                    .append(senha)
                    .toString();
            String hash = null;
            
            try {
                digest = MessageDigest.getInstance("SHA-256");
                byte[] hashb = digest.digest(saltAndPwd.getBytes(StandardCharsets.UTF_8));
                hash = toHexString(hashb);
//                hash = new String(digest.digest(saltAndPwd.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("Erro ao gerar digest!");
            }
            
            usuario = new Usuario(email, hash, salt);
        }
        
        return usuario;
    }
    
    public static void logarUsuario(List<Usuario> usuarios) {
        Scanner sc = new Scanner(System.in);
        
        String email, senha = null;
        
        
        System.out.print("Email: ");
        email = sc.nextLine();
        System.out.print("Senha: ");
        senha = sc.nextLine();
        
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                String senhaTeste = new StringBuilder()
                        .append(u.getSaltPwd())
                        .append(senha)
                        .toString();
                
                MessageDigest digest;
                String hash = null;
                try {
                    digest = MessageDigest.getInstance("SHA-256");
                    byte[] hashb = digest.digest(senhaTeste.getBytes(StandardCharsets.UTF_8));
                    hash = toHexString(hashb);
    //                hash = new String(digest.digest(saltAndPwd.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                } catch (NoSuchAlgorithmException ex) {
                    System.out.println("Erro ao gerar digest!");
                }
                
                if (u.getHashPwd().equals(hash)) {
                    System.out.println("Usuário logado!");
                } else {
                    System.out.println("Erro ao logar usuário!");
                }
                return;
            }
        }
        
        System.out.println("Erro ao logar usuário!");
    }
    
    public static void listarUsuarios(List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }
    
    public static String toHexString(byte[] byteArray) {
        StringBuilder stb = new StringBuilder();

        for (int i = 0; i < byteArray.length; i++) {
            stb.append(Integer.toHexString(0xff & byteArray[i]));
        }
        
        return stb.toString();
    }
}
