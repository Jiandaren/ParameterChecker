package com.jiansk.parchecker;

import com.jiansk.parchecker.entity.ParField;
import com.jiansk.parchecker.enums.TypeEnum;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotNull;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
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

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IOException {
        String excelPath = "C:\\Users\\jiansk20361\\Desktop\\new用户业务接口文档.xls";
        Map<String, List<ParField>> excelMap = excelFileReader(excelPath);
        for(String reqName : excelMap.keySet()){
            Class<?> clz = Class.forName(reqName);
            for(ParField parField : excelMap.get(reqName)){
                //根据属性名获取属性
                Field field = clz.getDeclaredField(parField.getName());
                //校验非空
                if(parField.isNullable()){
                    if(field.getAnnotation(NotNull.class) == null){
                        System.out.println(reqName + ":" +parField.getName() + "非空校验异常");
                    }
                }
                //校验长度
                if(parField.getLength() != 0){
                    Length length = field.getAnnotation(Length.class);
                    if(length == null || length.max() != parField.getLength()) {
                        System.out.println(reqName + ":" +parField.getName() + "长度校验异常");
                    }
                }
                //校验类型
                String type = null;
                if("java.util.List".equals(field.getType().getName())){
                    Field tempField = clz.getDeclaredField(field.getName());
                    String generic = tempField.toGenericString().split(" ")[1];
                    String[] types = generic.split("<|>");
                    for(int i=0;i<types.length;i++){
                        types[i] = types[i].substring(types[i].lastIndexOf(".") + 1);
                    }
                    type = types[0] + "<" + types[1] + ">";
                } else {
                    String typeName = field.getType().getName();
                    type = typeName.substring(typeName.lastIndexOf(".") + 1);
                }
                if(!type.equals(parField.getType())){
                    System.out.println(reqName + ":" +parField.getName() + "类型校验异常");
                }
            }
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

//    public static Map<String, List<ParField>> javaFileReader(List<String> fileNames) throws ClassNotFoundException,
//            NoSuchFieldException {
//        Map<String, List<ParField>> map = new HashMap<String, List<ParField>>();
//        for(String fileName : fileNames){
//            List<ParField> parFieldList = new ArrayList<ParField>();
//            String className = fileName.split("\\.java")[0];
//            Class<?> clz = Class.forName(className);
//            Field[] fields = clz.getDeclaredFields();
//            for (Field field : fields) {
//                ParField parField = new ParField();
//                parField.setName(field.getName());
//
//                if("java.util.List".equals(field.getType().getName())){
//                    Field tempField = clz.getDeclaredField(field.getName());
//                    String generic = tempField.toGenericString().split(" ")[1];
//                    String[] types = generic.split("<|>");
//                    for(int i=0;i<types.length;i++){
//                        types[i] = TypeEnum.find(types[i]).getDecription();
//                    }
//                    parField.setType(types[0] + "<" + types[1] + ">");
//                } else {
//                    String typeName = field.getType().getName();
//                    parField.setType(typeName.substring(typeName.lastIndexOf(".") + 1));
//                }
//                NotNull notNull = field.getAnnotation(NotNull.class);
//                if(notNull != null){
//                    parField.setNullable(true);
//                }
//                Length length = field.getAnnotation(Length.class);
//                if(length != null) {
//                    parField.setLength(length.max());
//                }
//                parFieldList.add(parField);
//            }
//            String reqName = className.substring(className.lastIndexOf(".") + 1);
//            map.put(reqName, parFieldList);
//        }
//        return map;
//    }

    public static Map<String, List<ParField>> excelFileReader(String path) throws IOException {
        if(path.endsWith(".xls")){
            Map<String, List<ParField>> map = new HashMap<String, List<ParField>>();
            //读取excel文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(path)));
            //前两页为版本信息和接口列表
            for (int i = 2; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
                List<ParField> parFieldList = new ArrayList<ParField>();
                HSSFSheet sheet = workbook.getSheetAt(i);
                HSSFRow row = sheet.getRow(3);
                HSSFCell reqCell  = row.getCell(0);
                String reqName = reqCell.toString();
                CellRangeAddress range = sheet.getMergedRegion(2);
                if(range.getFirstRow() == reqCell.getRow().getRowNum()){
                    for(int rowNum = range.getFirstRow();rowNum <= range.getLastRow();rowNum++){
                        //[1]英文名[3]类型[4]长度[5]必输
                        ParField parField = new ParField();
                        if("tbspRequest".equals(sheet.getRow(rowNum).getCell(1).toString())){
                            continue;
                        }
                        parField.setName(sheet.getRow(rowNum).getCell(1).toString());
                        parField.setType(sheet.getRow(rowNum).getCell(3).toString());
                        if(!"".equals(sheet.getRow(rowNum).getCell(4).toString())){
                            String length = sheet.getRow(rowNum).getCell(4).toString();
                            parField.setLength(Integer.parseInt(length.split("\\.")[0]));
                        }
                        if("Y".equals(sheet.getRow(rowNum).getCell(5).toString())){
                            parField.setNullable(true);
                        }
                        parFieldList.add(parField);
                    }
                    map.put(reqName, parFieldList);
                }
            }
            return map;
        }
        return null;
    }
}
