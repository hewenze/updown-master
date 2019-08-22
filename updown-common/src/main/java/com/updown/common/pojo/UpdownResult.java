package com.updown.common.pojo;

import java.io.Serializable;

/**
 * 服务端响应客户端的状态实体类
 */
public class UpdownResult implements Serializable{   //在dubbo中进行实体类传输必须实现序列化接口


    private Integer status;  //响应业务状态

    private String msg;  //响应消息

    private Object data; //响应中的数据

    //用于构建其他状态的UpdownResult对象
    public static UpdownResult build(Integer status, String msg, Object data){
        return new UpdownResult(status,msg,data);
    }

    /**
     * 有参数的成功返回状态
     * @param data
     * @return
     */
    public static UpdownResult ok(Object data){
        return new UpdownResult(data);
    }

    /**
     * 无参数的成功返回状态
     * @return
     */
    public static UpdownResult ok(){
        return new UpdownResult(null);
    }

    /**
     * 无参构造
     */
    public UpdownResult() {
    }

    /**
     * 状态码,响应信息和响应数据构建
     * @param status
     * @param msg
     * @param data
     */
    public UpdownResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 状态码和响应信息构建
     * @param status
     * @param msg
     * @return
     */
    public static UpdownResult build(Integer status,String msg){
        return new UpdownResult(status,msg,null);
    }

    /**
     * 默认响应成功
     * @param data
     */
    public UpdownResult(Object data){
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
