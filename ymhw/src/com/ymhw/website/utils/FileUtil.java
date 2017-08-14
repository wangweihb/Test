package com.ymhw.website.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	 public static boolean Move(File srcFile, String destPath){
	    // Destination directory
	    File dir = new File(destPath);
	    // Move file to new directory
	    boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));
	    return success;
	 }
	 
	 public static boolean Move(String srcFile, String destPath){
	    // File (or directory) to be moved
	    File file = new File(srcFile);
	    // Destination directory
	    File dir = new File(destPath);
	    // Move file to new directory
	    boolean success = file.renameTo(new File(dir, file.getName()));
	    return success;
	  }
	 
	/*文件copy
	 * param src,des
	 * return ture 成功。false 失败
	 */
	 public static boolean Copy(String src,String des){
		File srcFile=new File(src);
		File desFile=new File(des);
		byte[]b=new byte[1024];
		  try {
			  FileInputStream fis=new FileInputStream(srcFile);
			  FileOutputStream fos=new FileOutputStream(desFile,false);
			  while(true){
				  int i=fis.read(b);
				  if(i==-1)break;
				  fos.write(b,0,i);
			  }
			  fos.close();
			  fis.close();
			  return true;
		} catch (Exception e){ 
			e.printStackTrace();
		}
		return false;
	}	 
	 
	 /**
	  * @param oldfile
	  * @param newPath  完全路径
	  */
	 public static void Copy(File oldfile, String newPath){   
		  try {   
		      int bytesum = 0;   
		      int byteread = 0;   
		      if (oldfile.exists()) { 
		      InputStream inStream = new FileInputStream(oldfile);    
		      FileOutputStream fs = new FileOutputStream(newPath);   
		      byte[] buffer = new byte[1444];   
		      while ( (byteread = inStream.read(buffer)) != -1) {   
		          bytesum += byteread;   
		          fs.write(buffer, 0, byteread);   
		      }   
		      inStream.close();   
		      }   
		  }   
		  catch (Exception e) {   
		      e.printStackTrace();   
		  }   
	 }
	 
	/*
	 * 文件夹copy
	 * param src,des
	 *  return ture 成功。false 失败
	 */
	public static boolean folderCopy(String src,String des){
		File srcFile=new File(src);
		File desFile=new File(des);
		File []files=srcFile.listFiles();
		boolean flag = false;
		if(!desFile.exists())desFile.mkdir();
		for(int i=0;i<files.length;i++){
			String path=files[i].getAbsolutePath(); 
			if(files[i].isDirectory()){
				File newFile=new File(path.replace(src,des));
				if(!newFile.exists())newFile.mkdir();//不存在新建文件夹
				folderCopy(path,path.replace(src,des));
			}
			else
			  flag=Copy(path,path.replace(src,des));//文件复制函数
			}
		return flag;
	}   
    
    /** 
     * 删除目录（文件夹）以及目录下的文件 
     * @param   sPath 被删除目录的文件路径 
     * @return  目录删除成功返回true，否则返回false 
     */  
    public static boolean deleteDirectory(String sPath) {
    	boolean flag;
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        flag = true;  
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            //删除子文件  
            if (files[i].isFile()) {  
                //flag = deleteFile(files[i].getAbsolutePath()); 
                flag = files[i].delete();
                if (!flag) break;  
            } //删除子目录  
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;  
        //删除当前目录  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
    
    /**
     * 获取dir下的所有文件名（全路径）
     * @param dir
     * @return
     */
    public static List<String> getFileNames(String dir) {
    	List<String> names = new ArrayList<String>();
    	File file = new File(dir);
    	if(!file.exists()) {
    		return names;
    	}
    	File[] childFiles = file.listFiles();
    	for (File f: childFiles) {
    		if (f.isFile())
			{
//    			names.add(dir + File.separator + f.getName());
    			names.add(f.getName());
			}
    	}
    	return names;
    }
	 
}
