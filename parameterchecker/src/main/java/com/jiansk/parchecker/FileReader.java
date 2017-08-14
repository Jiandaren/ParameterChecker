package com.jiansk.parchecker;

import com.jiansk.parchecker.entity.ParField;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotNull;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*********************************************************
 * 文件名称：FileReader
 * 系统名称：
 * 模块名称：com.jiansk.parchecker 
 * 功能说明：文件解析
 * 开发人员：jiansk
 * 开发时间：2017-08-14 16:26
 * 修改记录：程序版本    修改日期    修改人员    修改单号    修改说明
 *********************************************************/
public class FileReader {


    private static List<String> fileNames = new LinkedList<String>();

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        String path = "D:\\gitrepo\\ParameterChecker\\parameterchecker";
        List<String> fileNames = readFileNames(path);
        System.out.println(fileNames.toString());
        for(String fileName : fileNames){
            Map<String, ParField> map = javaFileReader(fileName);
            System.out.println(map.toString());
        }
    }

    public static List<String> readFileNames(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            System.out.println("文件不存在");
            return null;
        }
        if (!dir.isDirectory()) {
            System.out.println("路径错误");
            return null;
        }
        File files[] = dir.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().matches("[A-Za-z0-9]+Req\\.java")) {
                String filePath = file.getPath();
                fileNames.add(filePath.split("java\\\\")[1].replace('\\', '.'));
            }
            if (file.isDirectory()) {
                readFileNames(path + "\\" + file.getName());
            }
        }
        return fileNames;
    }

    public static Map<String, ParField> javaFileReader(String fileName) throws ClassNotFoundException,
            NoSuchFieldException {
        Map<String, ParField> map = new HashMap<String, ParField>();
        Class<?> clz = Class.forName(fileName.split("\\.java")[0]);
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            ParField parField = new ParField();
            parField.setName(field.getName());

            if("java.util.List".equals(field.getType().getName())){
                Field tempField = clz.getDeclaredField(field.getName());
                String generic = tempField.toGenericString();
                parField.setType(generic.split(" ")[1]);
            } else {
                parField.setType(field.getType().getName());
            }
            NotNull notNull = field.getAnnotation(NotNull.class);
            if(notNull != null){
                parField.setNessesary(true);
            }
            Length length = field.getAnnotation(Length.class);
            if(length != null) {
                parField.setLength(length.max());
            }
            map.put(field.getName(), parField);
        }
        return map;
    }

    public static List<ParField> excelFileReader(String path) {
        return null;
    }
}
