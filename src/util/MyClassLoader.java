package util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import javax.enterprise.inject.New;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class MyClassLoader {
	public static ConcurrentHashMap<String, Class<?>> load(File clazzPath){
		//compiler();
		clazzPath = new File(clazzPath.getAbsolutePath() + "/bin");
		ArrayList<File> fileList = new ArrayList<File>();
		fileList = traversalFile(clazzPath, fileList);
		loadPath(clazzPath);
		Iterator<File> it = fileList.iterator();
		ConcurrentHashMap<String, Class<?>> classMap 
				= new ConcurrentHashMap<String, Class<?>>();
		while(it.hasNext()){
			loadClass(it.next(), classMap);
		}
		return classMap;
		
	}
	private static ArrayList<File> traversalFile(File parent, ArrayList<File> fileList){
		File[] fileArray = parent.listFiles();
		for(File file : fileArray){
			if( ! file.isDirectory() && file.getName().split("\\.")[1].equals("class")){
				fileList.add(file);
			}else if(file.isDirectory()){
				return traversalFile(file, fileList);
			}
		}
		return fileList;
		
	}
	private static void loadPath(File clazzPath){
		Method method = null;
		try {
			method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
        boolean accessible = method.isAccessible();
        try {
            if (!accessible) {
                method.setAccessible(true);
            }
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            method.invoke(classLoader, clazzPath.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.setAccessible(accessible);
        }
	}
	private static Class<?> loadClass(File subFile, ConcurrentHashMap<String, Class<?>> classMap){      
        String className = subFile.getAbsolutePath();
        className = className.split("\\\\bin\\\\")[1].split("\\.class")[0];
        String name = new String(className.replace('\\', '/'));
        //将/替换成. 得到全路径类名
        className = className.replaceAll("\\\\", ".");
        // 加载Class类
        Class<?> servletClass = null;
        try {
			servletClass = Class.forName(className);
			classMap.put(name, servletClass);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return servletClass;
	}
	public static void compiler(){
		String javaAbsolutePath = "e:/test/a.java";
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int status = compiler.run(null, null, null, "-encoding", "UTF-8", "-classpath", javaAbsolutePath.toString(), javaAbsolutePath);
        
		
	}

	

}
