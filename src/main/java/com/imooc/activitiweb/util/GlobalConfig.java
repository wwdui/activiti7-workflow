package com.imooc.activitiweb.util;

public class GlobalConfig {
    /**
     * 测试场景
     */
    public static final Boolean Test = false;

    //windows路径
    public static final String BPMN_PathMapping = "file:D:\\WangJianIDEA_Test\\activiti-imooc\\src\\main\\resources\\resources\\bpmn\\";

    //Liunx路径
    //public static final String BPMN_PathMapping = "file:/root/Activiti/";

    public enum ResponseCode {
        SUCCESS(0, "成功"),
        ERROR(1, "错误");

        private final int code;
        private final String desc;

        ResponseCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

}
