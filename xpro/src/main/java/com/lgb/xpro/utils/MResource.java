package com.lgb.xpro.utils;

import android.content.Context;
import android.util.Log;

/** 
 * 根据资源的名字获取其ID值 
 * @author lingb 
 * 
 */  
public class MResource {  
    private static int getIdByName(Context context, String className, String name) {
        String packageName = context.getPackageName();
        Log.e("MResource", "packageName = " + packageName);
        Class<?> r = null;
        int id = 0;  
        try {  
            r = Class.forName(packageName + ".R");
  
            Class<?>[] classes = r.getClasses();
            Class<?> desireClass = null;
  
            for (int i = 0; i < classes.length; ++i) {  
                if (classes[i].getName().split("\\$")[1].equals(className)) {  
                    desireClass = classes[i];  
                    break;  
                }  
            }  
  
            if (desireClass != null)  
                id = desireClass.getField(name).getInt(desireClass);  
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {
            e.printStackTrace();  
        } catch (SecurityException e) {
            e.printStackTrace();  
        } catch (IllegalAccessException e) {
            e.printStackTrace();  
        } catch (NoSuchFieldException e) {
            e.printStackTrace();  
        }  
  
        return id;  
    }  
    
    public static int getLayout(Context context, String name) {
    	return MResource.getIdByName(context, "layout", name);
    }
    
    public static int getId(Context context, String name) {
    	return MResource.getIdByName(context, "id", name);
    }
    
    public static int getString(Context context, String name) {
    	return MResource.getIdByName(context, "string", name);
    }
    
    public static int getDrawable(Context context, String name) {
    	return MResource.getIdByName(context, "drawable", name);
    }
    
    public static int getArray(Context context, String name) {
    	return MResource.getIdByName(context, "array", name);
    }
    
    public static int getDimen(Context context, String name) {
    	return MResource.getIdByName(context, "dimen", name);
    }
}  
