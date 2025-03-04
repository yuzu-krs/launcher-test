package me.quick.tutorial;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import me.quick.tutorial.util.OSHelper;
import me.quick.tutorial.util.UnzipUtility;

public class Main {

	public static void main(String[] args) {
		launch();
	}
	
	
	public static void launch() {
	    File minecraftDirectory = new File(OSHelper.getOS().getMc());
	    File minecraftAssets = new File(minecraftDirectory.toString() + File.separator+"assets");
	    File natives = new File(System.getProperty("user.dir") + File.separator + "natives.zip");
	    File libraries = new File(System.getProperty("user.dir") + File.separator + "libraries.zip");
	    File jar = new File(System.getProperty("user.dir") + File.separator + "YuzuClient.jar");
	    
	    //System.out.println(System.getProperty("user.dir") + File.separator + "YuzuClient.jar");

	    try {
	        FileUtils.copyURLToFile(new URL("https://github.com/yuzu-krs/launcher-test/raw/main/natives.zip"), natives);
	        FileUtils.copyURLToFile(new URL("https://github.com/yuzu-krs/launcher-test/raw/main/libraries.zip"), libraries);
	        FileUtils.copyURLToFile(new URL("https://github.com/yuzu-krs/launcher-test/raw/main/YuzuClient.jar"), jar);
	      
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    UnzipUtility unzipper = new UnzipUtility();
	    try {
	        unzipper.unzip(natives.toString(), System.getProperty("user.dir") + File.separator + "natives");
	        natives.delete();
	        unzipper.unzip(libraries.toString(), System.getProperty("user.dir") + File.separator + "libraries");
	        libraries.delete();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    
	    
	    try {
	        Process process = Runtime.getRuntime().exec(
	            "java -" +
	            "Xms1024M " +
	            "-Xmx4096M " +
	            "-Djava.library.path=\"" + System.getProperty("user.dir") + File.separator + "natives"+"\" "+
	            "-cp \"" + System.getProperty("user.dir") + File.separator + "libraries"+File.separator+"*"+";"+jar.toString()+"\" "+
	            "net.minecraft.client.main.Main " +
	            "--width 854 "+
	            "--height 480 "+
	            "--username Tutorialuser " +
	            "--version YuzuClient " +
	            "--gameDir " + minecraftDirectory.toString() + " " +
	            "--assetsDir " + minecraftAssets.toString() + " " +
	            "--assetIndex YuzuClient " +
	            "--uuid N/A " +
	            "--accessToken aeef7bc935f9420eb6314dea7ad7ele5 " +
	            "--userType mojang"
	        );

	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
	        String s = null;

	        while ((s = stdInput.readLine()) != null) {
	            System.out.println(s);
	        }

	        while ((s = stdError.readLine()) != null) {
	            System.out.println(s);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	}
}
