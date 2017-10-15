package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 * Created by yuezhang on 17/10/6.
 */
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器，获取当前线程的ClassLoader即可
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类（将自动初始化）
     * @param className
     * @return
     */
    public static Class<?> loadClass(String className){
        return loadClass(className,true);
    }

    /**
     * 加载类
     * @param className
     * @param isInitialized 是否初始化，是指是否执行类的静态代码块
     * @return
     */
    public static Class<?> loadClass(String className , boolean isInitialized){
        Class<?> cls;
        try {
            cls = Class.forName(className,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("load class failure",e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取指定包名下得所有类
     *
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(url != null){
                    String protocol = url.getProtocol();
                    if("file".equals(protocol)){
                        String packagePath = url.getPath().replaceAll("%20","");
                        addClass(classSet,packagePath,packageName);
                    }else if("jar".equals(protocol)){
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if(jarURLConnection != null){
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if(jarFile != null){
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()){
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if(jarEntryName.endsWith(".class")){
                                        String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                                        doAddClass(classSet,className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("get class set failure",e);
            throw new RuntimeException(e);
        }

        return classSet;
    }


    public static Set<Class<?>> getClassSetInLibs(String libName){
        Set<Class<?>> classSet = new HashSet<>();
        try {
            String libPath = getClassLoader().getResource("/").getPath().split("/classes/")[0]+"/lib/";
            File file = null;
            File [] libFiles = new File(libPath).listFiles();
            if(ArrayUtil.isEmpty(libFiles)){
                return classSet;
            }
            for(File f : libFiles){
                if(f.getName().contains(libName)){
                    file = f;
                    break;
                }
            }
            if(file == null){
                return classSet;
            }

            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()){
                JarEntry jarEntry = jarEntries.nextElement();
                String jarEntryName = jarEntry.getName();
                if(jarEntryName.endsWith(".class")){
                    String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                    System.out.println("jarEntryName: " + jarEntryName + "   className:" + className);
                    doAddClass(classSet,className);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("get class set in libs failure",e);
            throw new RuntimeException(e);
        }
        return classSet;
    }


    private static void addClass(Set<Class<?>> classSet , String packagePath , String packageName){
        File[] files = new File(packagePath).listFiles(
            new FileFilter() {
                @Override
                public boolean accept(File file) {
                    // 只接收.class文件或者文件夹
                    return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
                }
            }
        );

        if (files == null) return;

        for(File file : files){
            String fileName = file.getName();
            if(file.isFile()){
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if(StringUtil.isNotEmpty(packageName)){
                    className = packageName + "." + className;
                }
                doAddClass(classSet,className);
            }else {
                String subPackagePath = fileName;
                if(StringUtil.isNotEmpty(packagePath)){
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if(StringUtil.isNotEmpty(packageName)){
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet , String className){
        Class<?> cls = loadClass(className,false);
        classSet.add(cls);
    }


    public static void main(String [] args){
        String path = "/Users/yuezhang/work/workspaces/intellij_workspaces/build-my-web/smart-client/target/smart-client-1.0.0/WEB-INF/classes/";

        String [] array = path.split("/classes/");
        System.out.println(array[0]+"/lib/");
    }
}
