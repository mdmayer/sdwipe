//Written by Carlton Hanna & Mark Mayer of UHS

package com.uhs.sdwipe;

import java.io.File;
import java.io.RandomAccessFile;
import java.security.SecureRandom;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;


public class MainActivity extends Activity {

File File; 

@Override 
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
Log.i("hello", "hello");

wipingSdcard();
Log.i("Exit", "exit");
finish();

}

	public void wipingSdcard() {
		Log.i("run", "run");    
		File deleteMatchingFile = new File(Environment
	                .getExternalStorageDirectory().toString());
	        try {
	            File[] filenames = deleteMatchingFile.listFiles();
	            if (filenames != null && filenames.length > 0) {
	                for (File tempFile : filenames) {
	                    if (tempFile.isDirectory()) {
	                        wipeDirectory(tempFile.toString());
	                        tempFile.delete();
	                    } else {
	                        tempFile.delete();
	                        Log.i("open", "open");
	                        //between the comments is the overbytewrite code
	                        long length = tempFile.length();
	                		SecureRandom random = new SecureRandom();
	                		RandomAccessFile raf = new RandomAccessFile(tempFile, "rws");
	                		raf.seek(0);
	                		raf.getFilePointer();
	                		byte[] data = new byte[64];
	                		int pos = 0;
	                		while (pos < length) {
	                			random.nextBytes(data);
	                			raf.write(data);
	                			pos += data.length;
	                		}
	                		raf.close();
	                		tempFile.delete();
	                		//between the comments is the overbytewrite code
	                		Log.i("write", "write");
	                    }
	                }
	            } else {
	                deleteMatchingFile.delete();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private void wipeDirectory(String name) {
	        File directoryFile = new File(name);
	        File[] filenames = directoryFile.listFiles();
	        if (filenames != null && filenames.length > 0) {
	            for (File tempFile : filenames) {
	                if (tempFile.isDirectory()) {
	                    wipeDirectory(tempFile.toString());
	                    tempFile.delete();
	                } else {
	                    tempFile.delete();
	                    Log.i("wipe", "wipe");
	                }
	            }
	        } else {
	            directoryFile.delete();
	            Log.i("exec", "exec");
	            
				
			}
	        }
	        
	    }

	    	
	
//ideas from
//http://stackoverflow.com/questions/858036/how-to-securely-delete-files-in-java - Emre
//http://stackoverflow.com/questions/4749891/how-can-i-programmatically-format-all-data-on-an-sd-card - Drax

