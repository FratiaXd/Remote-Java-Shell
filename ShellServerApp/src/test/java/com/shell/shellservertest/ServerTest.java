/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shell.shellservertest;

import com.shell.shellserverapp.Server;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
/**
 *
 * @author fratiaxd
 */
public class ServerTest {
    static Server server = new Server();
    
    @Test
    public void test1addUser1(){
    
        String userName = "user-1";
        String pwd = "pass";
        String confirmPwd = "pass";
        String userType = "super";
        
        String expected = "User successfully added";
        String actual = server.addUser(userName, pwd, confirmPwd, userType);
        
        assertEquals(expected, actual);
    }

   @Test
    public void test2addUser2(){
    
        String userName = "user-2";
        String pwd = "pass";
        String confirmPwd = "pass";
        String userType = "super";
        
        String expected = "User successfully added";
        String actual = server.addUser(userName, pwd, confirmPwd, userType);
        
        assertEquals(expected, actual);
    }

    @Test
    public void test3passwordDoesNotMatchTest(){
    
        String userName = "user-3";
        String pwd = "pass1";
        String confirmPwd = "pass2";
        String userType = "super";
        
        String expected = "Password and confirm password should be same";
        String actual = server.addUser(userName, pwd, confirmPwd, userType);
        
        assertEquals(expected, actual);
    }

    
    @Test
    public void test4testLogin(){
    
        String userName = "user-2";
        String pwd = "pass";
        String expected = userName+" logged in successfully";
        String actual = server.loginUser(userName, pwd);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void test5testLoginInvalidCredentials(){
    
        String userName = "user-2";
        String pwd = "pass2";
        String expected = "Invalid credentials";
        String actual = server.loginUser(pwd, pwd);
        
        assertEquals(expected, actual);
    }
    

    
    @Test
    public void test6removeUserErrorCase(){
    
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String userName = "user-5";
        String expected = "User does not exist";
        String actual = server.removesUser(userName);
        
        assertEquals(expected, actual);
    }
    
    
    
    @Test
    public void test7builderMkDir(){
        String commandMkDir = "mkdir abcd";
        String expected = "directory created successfully.";
        String actual = server.runProcessBuilder(commandMkDir);
        assertEquals(expected, actual);
    }

}
