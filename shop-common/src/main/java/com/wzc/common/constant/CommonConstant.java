package com.wzc.common.constant;

public class CommonConstant {

    public static final Integer SUCCESS_CODE = 200;
    public static final Integer FAIL_CODE = 500;
    public static final String SUCCESS_MSG = "操作成功";
    public static final String FAIL_MSG = "操作失败";

    public static final Integer PRODUCT_STATUS_ON = 1;
    public static final Integer PRODUCT_STATUS_OFF = 0;
    public static final Integer PRODUCT_STATUS_DELETED = -1;

    public static final Integer USER_STATUS_NORMAL = 1;
    public static final Integer USER_STATUS_DISABLED = 0;

    public static final Integer ORDER_STATUS_PENDING = 0;
    public static final Integer ORDER_STATUS_PAID = 1;
    public static final Integer ORDER_STATUS_SHIPPED = 2;
    public static final Integer ORDER_STATUS_COMPLETED = 3;

    public static final String UPLOAD_DIR = "uploads";
    public static final int PC_WIDTH = 800;
    public static final int PC_HEIGHT = 800;
    public static final int MOBILE_WIDTH = 400;
    public static final int MOBILE_HEIGHT = 400;
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
}