/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.shell.shellserverapp;

/**
 *
 * @author fratiaxd
 */

/**
 * @brief ShellServerApp launches server instance
 */

public class ShellServerApp {

    public static void main(String[] args) {
        Server gtp = new Server();
        gtp.start();
    }
}
