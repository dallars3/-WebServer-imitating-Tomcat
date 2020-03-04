package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import service.Response;

public class FileTransfer {
	public static String run(File file, Response response){
		String res = readFile(file);
		switch(file.getName().split(".")[1]){
		case "html":
			response.appendHead("content-type: text/html;\r\n");
			break;
		}
		return res;
	}
	public static String readFile(File file){
		FileInputStream fis = null;
		String reString = null;
		try {
            fis = new FileInputStream(file);
    		byte[] str = new byte[1024*1024];
            int len = 0;
    		len = fis.read(str);
            reString = new String(str, 0, len);       
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reString;
	}

}
