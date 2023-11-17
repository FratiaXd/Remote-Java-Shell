/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shell.shellserverapp;

/**
 *
 * @author fratiaxd
 */

/**
 * @brief UserModel class defines the structure of the user
 */

public class UserModel {
    private String name;
    private String pwd;
    private String userType;
    private String userStatus;

    /**
    * Get username
    * @return username
    */
    public String getName() {
        return name;
    }

    /**
    * Set username
    * @param name username to set
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * Get password
    * @return user password
    */
    public String getPwd() {
        return pwd;
    }

    /**
    * Set password
    * @param pwd password to set
    */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
    * Get user type
    * @return user type
    */
    public String getUserType() {
        return userType;
    }

    /**
    * Set user type
    * @param userType user type to set
    */
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    /**
    * Get user status
    * @return user status
    */
    public String getUserStatus() {
        return userStatus;
    }
    
    /**
    * Set user status
    * @param userStatus user status to set
    */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
    
}
