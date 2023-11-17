/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shell.shellserverapp;
        
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author fratiaxd
 */

/**
 * @brief Server class stores the information and executes supported commands
 */

public class Server extends Thread {

    /** Represents supported builtIn commands
    */
    String[] builtInCmds = new String[]{"sudo addUser","super delUser","showDir","copy","move", "login","logout"};
    
    /** Represents supported process builder commands
    */
    Set<String> processBuilderCommands = Set.of("ls","cp","mv","mkdir","rmdir","pwd","ps","which");

    private final int PORT = 2222;
    
    String statusOff = "offline";
    String statusOn = "online";
    
    /** Stores added users
    */
    private Set<UserModel> users = new HashSet<>();
    
    /**
    * Creates user with provided credentials
    * @param userName username
    * @param pwd password
    * @param confirmPwd password confirmation
    * @param userType user type
    * @return confirmation message
    */
    public String addUser(String userName, String pwd , String confirmPwd , String userType){
    
        if(!pwd.equals(confirmPwd)){
            return "Password and confirm password should be same";
        }else{
        
            UserModel userModel = new UserModel();
            userModel.setName(userName);
            userModel.setPwd(pwd);
            userModel.setUserType(userType);
            userModel.setUserStatus(statusOff);
            users.add(userModel);
            
            return "User successfully added";
        }
    }
    
    /**
    * Deletes the user with provided username
    * @param userName username
    * @return confirmation message
    */
    public String removesUser(String userName){
    
        UserModel selectedUser = null;
        
        for(UserModel userModel: users){
        
            if(userModel.getName().equals(userName)){
            
                selectedUser = userModel;
                break;
            }
        }
        
        if(selectedUser ==  null){
            return "User does not exist";
        }else{
            users.remove(selectedUser);
            return "User successfully removed";
        }
    
    }
    
    /**
    * Displays current user directory
    * @return current working directory
    */
    public String showDir(){
    
        String dir  = System.getProperty("user.dir");
        return dir;
    }
    
    /**
    * Makes a copy of the file to the preferred directory
    * @param source source directory and file name
    * @param destination destination directory and file name
    * @return confirmation message
    */
    public String copyFile(String source , String destination){
    
        Path sourceDirectory = Paths.get(source);
        Path targetDirectory = Paths.get(destination);
        try {
            Files.copy(sourceDirectory, targetDirectory,StandardCopyOption.REPLACE_EXISTING);
            
            return "File successfully copied from source to destination";
        } catch (IOException e) {
            System.out.println(e.toString());
            return "File does not exist";
        }
    }
    
    /**
    * Moves the file to the preferred directory
    * @param source source directory and file name
    * @param destination destination directory and file name
    * @return confirmation message
    */
    public String moveFile(String source, String destination){
    
        try{
        Path temp = Files.move(Paths.get(source),Paths.get(destination));
 
        if(temp != null)
        {
            return "File renamed and moved successfully";
        }
        else
        {
            return "Failed to move the file";
        }
        }catch(Exception ex){
            System.err.println("Error occurred: "+ ex.getMessage());
            return "Failed to move the file";
        }
    }
    
    /**
    * Logs user in if provided credentials are correct
    * @param name username
    * @param pwd password
    * @return confirmation message
    */
    public String loginUser(String name, String pwd){
        UserModel selectedUser = null;
        
        for(UserModel userModel: users){
        
            if(userModel.getName().equals(name) && userModel.getPwd().equals(pwd)){
            
                selectedUser = userModel;
                selectedUser.setUserStatus(statusOn);
                break;
            }
        }
        
        if(selectedUser == null){
        
            return "Invalid credentials";
        }else{
        
            return name+" logged in successfully";
        }
        
    }
        
    /**
    * Logs user out with provided username
    * @param name username
    * @return confirmation message
    */   
    public String logoutUser(String name){
        UserModel selectedUser = null;
        
        for(UserModel userModel: users){
        
            if(userModel.getName().equals(name) && userModel.getUserStatus().equals(statusOn)){
            
                selectedUser = userModel;
                selectedUser.setUserStatus(statusOff);
                break;
            }
        }
        
        if(selectedUser == null){
        
            return "User does not exist or is not logged on";
        }else{
        
            return name+" logged out successfully";
        }
        
    }

    /**
    * Process builder reads the input and executes linux supported commands
    * @param command command to execute
    * @return command result
    */ 
    public String runProcessBuilder(String command){
        StringBuilder output = null;
        try{
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
	Process process = processBuilder.start();
	output = new StringBuilder();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line2;
		while ((line2 = reader.readLine()) != null) {
			output.append(line2 + "\n");
		}

                if(command.contains("mkdir")){
                    return "directory created successfully.";
                }else if(command.contains("rmdir")){
                    return "directory removed successfully.";
                }
        }catch(Exception ex){
            return "command not found";
       }
        return output.toString();
    }
    
    /**
    * Listens to socket and processes commands
    */ 
    public void run() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server listening port: " + PORT);
            boolean shudown = true;
            while (shudown) {
                Socket socket = server.accept();
                InputStream is = socket.getInputStream();
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String line;
                line = in.readLine();
                String auxLine = line;
                line = "";
                // looks for post data
                int postDataI = -1;
                
                while ((line = in.readLine()) != null && (line.length() != 0)) {
                    if (line.indexOf("Content-Length:") > -1) {
                        postDataI = new Integer(line
                                .substring(
                                        line.indexOf("Content-Length:") + 16,
                                        line.length())).intValue();
                        
                    }
                }
                String postData = "";
                for (int i = 0; i < postDataI; i++) {
                    int intParser = in.read();
                    postData += (char) intParser;
                }
                String[] textTokens;
                if(postData.contains("%0A")){
                    textTokens = postData.split("%0A");
                    
                    int length = textTokens.length;
                    postData = textTokens[length-1];
                }
                // replace + by " "
                int index=postData.indexOf('+');
                while(index>-1){

                    postData = postData.substring(0, index) + ' ' + postData.substring(index + 1);
                    index=postData.indexOf('+');
                }

                String[] tokens = postData.split("=");
                String prompt="";
                if(tokens.length>1){
                    postData = tokens[1];
                
                    prompt="prompt$ ";
                    prompt = prompt + postData;
                    System.out.println(prompt);
                }else if(postData.contains("%24")){
                    postData = postData.substring(10);
                    
                    prompt="prompt$ ";
                    prompt = prompt + postData;
                    System.out.println(prompt);
                }         
                String result = runCommand(postData);
                
                prompt = prompt.replaceAll("%2F", "/");   
                String textArea = prompt+"\n"+result+"\nprompt$ ";

                showHtml(out,textArea);
                //if your get parameter contains shutdown it will shutdown
                if (auxLine.indexOf("?shutdown") > -1) {
                    shudown = false;
                }
                out.close();
                socket.close();
            }
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
    * Executes built in commands
    * @param postData command to execute
    * @return command result
    */ 
    private String runCommand(String postData) {
        if(postData.isEmpty()) return "";
        if(postData.contains("super addUser")){
            String[] tokens = postData.split("\\s+");
            if(tokens.length<6)
                return "Please provide all details";
            String name = tokens[2];
            String pwd = tokens[3];
            String confirmPwd = tokens[4];
            String type = tokens[5];
            
            return addUser(name, pwd, confirmPwd, type);
            
        }else if(postData.contains("super delUser")){
            
            String[] tokens = postData.split("\\s+");
            if(tokens.length<3)
                return "Please provide all details";
            String name = tokens[2];            
            return removesUser(name);
            
        }else if(postData.contains("showDir")){
            return showDir();
        }else if(postData.contains("copy")){
            
            postData = postData.replaceAll("%2F", "/");

            String[] tokens = postData.split("\\s+");
            
            if(tokens.length<3)
                return "Please provide all details"; 
            
            String source = tokens[1];
            String destination = tokens[2];

            return copyFile(source, destination);
        }else if(postData.contains("move")){
            postData = postData.replaceAll("%2F", "/");

            String[] tokens = postData.split("\\s+");
            
            if(tokens.length<3)
                return "Please provide all details";
            
            String source = tokens[1];
            String destination = tokens[2];

            return moveFile(source, destination);
        }else if(postData.contains("login")){
            String[] tokens = postData.split("\\s+");
            
            if(tokens.length<3)
                return "Please provide all details";
            
            String name = tokens[1];
            String pwd = tokens[2];

            return loginUser(name, pwd);
        }else if(postData.contains("logout")){
            String[] tokens = postData.split("\\s+");
            
             if(tokens.length<2)
                return "Please provide all details";
                        
            String name = tokens[1];
            return logoutUser(name);
        }else{
        
        postData = postData.replaceAll("%2F", "/");
        String[] command = postData.split("\\s+");
        
        if(processBuilderCommands.contains(command[0])){
            return runProcessBuilder(postData);
        }
        else{
        
            return "Command "+command[0]+" does not exist";
        }
       }
    }

    /**
    * Sets up html page
    * @param out output stream
    * @param textArea pass result string into the text box
    */ 
    private void showHtml(PrintWriter out,String textArea) {
                     
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println("Server: MINISERVER");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        out.println("<H1>Server</H1>");
        //out.println("<p style=\"margin-left:5px\" >Result->" + result + "</p>");
        out.println("<form name=\"input\" action=\"imback\" method=\"post\">");
        out.print("<textarea rows=\"10\" cols=\"50\" name=\"user\" style=\"background-color: black;color:#fff;\">"+textArea+"</textarea>");
        out.println("<input type=\"submit\" value=\"Submit\"></form>");
                   
    }
}