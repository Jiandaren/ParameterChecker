package com.jiansk.parchecker.entity;

/*********************************************************
 * 文件名称：ParField
 * 系统名称：
 * 模块名称：com.jiansk.parchecker.entity 
 * 功能说明：入参属性
 * 开发人员：jiansk
 * 开发时间：2017-08-14 16:30
 * 修改记录：程序版本    修改日期    修改人员    修改单号    修改说明
 *********************************************************/
public class ParField {

    private String name;

    private String type;

    private boolean nessesary;

    private int length;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNessesary() {
        return nessesary;
    }

    public void setNessesary(boolean nessesary) {
        this.nessesary = nessesary;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
