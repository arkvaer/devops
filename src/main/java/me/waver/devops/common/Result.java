package me.waver.devops.common;

import cn.hutool.http.HttpStatus;
import lombok.Data;

/**
 * 项目封装结果集
 *
 * @author waver
 * @date 2019.5.27 9:19
 */
@Data
public class Result {
    /**
     * 是否成功
     */

    boolean flag;
    /**
     * 返回码
     */
    private Integer status;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;


    private Result() {
        super();
        this.flag = true;
        this.status = HttpStatus.HTTP_OK;
        this.message = "";
        this.data = null;
    }

    private Result(String message) {
        super();
        this.flag = true;
        this.status = HttpStatus.HTTP_OK;
        this.message = message;
        this.data = null;
    }

    private Result(Object data) {
        super();
        this.flag = true;
        this.status = HttpStatus.HTTP_OK;
        this.message = "success";
        this.data = data;
    }

    private Result(String message, Object data) {
        super();
        this.flag = true;
        this.status = HttpStatus.HTTP_OK;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        super();
        this.flag = flag;
        this.status = HttpStatus.HTTP_OK;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        super();
        this.flag = flag;
        this.status = HttpStatus.HTTP_OK;
        this.message = message;
    }
    public  boolean isSuccess(){
        return status == HttpStatus.HTTP_OK;
    }
    public static Result ok() {
        Result result = new Result();
        result.setMessage("success");
        return result;
    }

    public static Result ok(String message) {
        return new Result(message);
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

    public static Result ok(String message, Object data) {
        return new Result(message, data);
    }

    public static Result ok(boolean flag, Integer code, String message) {
        return new Result(flag, code, message);
    }

    public static Result ok(boolean flag, Integer code, String message, Object data) {
        return new Result(flag, code, message, data);
    }

    public static Result fail(String message) {
        return new Result(false, HttpStatus.HTTP_INTERNAL_ERROR, message);
    }

    public static Result build(boolean flag, Integer code, String message) {
        return new Result(flag, code, message);
    }

    public static Result build(boolean flag, Integer code, String message, Object data) {
        return new Result(flag, code, message, data);
    }

}
