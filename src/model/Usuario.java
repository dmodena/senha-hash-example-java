/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Micronos
 */
public class Usuario {
    private static int maxId = 0;
    private int id;
    private String email;
    private String hashPwd;
    private String saltPwd;
    
    public Usuario() {
        this.id = ++maxId;
    }
    
    public Usuario(String email, String hashPwd, String saltPwd) {
        this();
        this.email = email;
        this.hashPwd = hashPwd;
        this.saltPwd = saltPwd;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashPwd() {
        return hashPwd;
    }

    public void setHashPwd(String hashPwd) {
        this.hashPwd = hashPwd;
    }

    public String getSaltPwd() {
        return saltPwd;
    }

    public void setSaltPwd(String saltPwd) {
        this.saltPwd = saltPwd;
    }
    
    @Override
    public String toString() {
        return id + " - " + email + " - h: " + hashPwd + " - s: " + saltPwd;
    }
}
