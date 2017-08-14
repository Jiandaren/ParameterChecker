package com.jiansk.parchecker.entity;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotNull;

import java.util.List;

/*********************************************************
 * 文件名称：AddBankReq
 * 系统名称：交易银行系统V1.0
 * 模块名称：com.hundsun.tbsp.custbiz.service.inter.req 
 * 功能说明：银行机构新增req 
 * 开发人员：jiansk20361
 * 开发时间：2017-04-26 16:26
 * 修改记录：程序版本    修改日期    修改人员    修改单号    修改说明
 *********************************************************/
public class AddOrgReq{

    /**
     * 机构号
     */
    @NotNull(errorCode = "0TB320000001", message = "机构号为空")
    @Length(errorCode = "0TB320000002", max = 10 ,message = "机构号超长")
    @MatchPattern(errorCode = "0TB320000003", message = "机构号只能为字母和数字", pattern = "^[0-9a-zA-Z]+$")
    private String orgNo;

    /**
     * 机构名称
     */
    @NotNull(errorCode = "0TB320000001", message = "机构名称为空")
    @Length(errorCode = "0TB320000002", max = 70 ,message = "机构名称超长")
    @MatchPattern(errorCode="0TB320000003",message="机构名称含有非法字符",pattern="^[^\\^'\\\"%_#&<>;]+$")
    private String orgName;

    /**
     * 联行行号
     */
    @MatchPattern(errorCode = "0TB320000003", message = "联行行号应为12位数字", pattern = "^\\d{12}$")
    @Length(max = 12 ,message = "联行行号超长")
    private String bankNo;

    /**
     * 上级机构号
     */
    @NotNull(errorCode = "0TB320000001", message = "上级机构号为空")
    @Length(errorCode = "0TB320000002", max = 32 ,message = "上级机构号超长")
    @MatchPattern(errorCode = "0TB320000003", message = "上级机构号只能为字母和数字", pattern = "^[0-9a-zA-Z]+$")
    private String uppOrgNo;

    /**
     * 机构序号
     */
    @NotNull(errorCode = "0TB320000001", message = "机构序号为空")
    private Integer sortNo;

    /**
     * 角色列表
     */
    @NotNull(errorCode = "0TB320000001", message = "角色列表为空")
    private List<String> roleList;

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getUppOrgNo() {
        return uppOrgNo;
    }

    public void setUppOrgNo(String uppOrgNo) {
        this.uppOrgNo = uppOrgNo;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }
}
