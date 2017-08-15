package com.jiansk.parchecker.enums;

/*********************************************************
 * 文件名称：TypeEnum
 * 系统名称：交易银行系统V1.0
 * 模块名称：com.jiansk.parchecker.enums
 * 功能说明： 
 * 开发人员：jiansk20361
 * 开发时间：2017-08-15 13:42
 * 修改记录：程序版本    修改日期    修改人员    修改单号    修改说明
 *********************************************************/
public enum TypeEnum {
    STRING("TypeEnum", "String", "java.lang.String"),
    LIST("TypeEnum", "List", "java.util.List"),
    INTEGER("TypeEnum","Integer","java.lang.Integer"),
    ;

    private String dictId;
    private String code;
    private String decription;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     */
    public static TypeEnum find(String code) {
        for (TypeEnum frs : TypeEnum.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }
    /**
     * 根据传入的类型获取枚举或枚举值
     * @param object
     * @return
     */
    public static Object getObject(Object object) {
        for (TypeEnum frs : TypeEnum.values()) {
            if(object instanceof String){
                if (frs.getCode().equals(object)) {
                    return frs;
                }
            }else if(object instanceof Enum){
                if(frs.equals(object)){
                    return frs.getCode();
                }
            }
        }
        return null;
    }
    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    TypeEnum(String dictId, String code, String decription) {
        this.dictId = dictId;
        this.code = code;
        this.decription = decription;
    }
}
