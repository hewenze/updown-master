package com.updown.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 任务pojo
 */
@Table(name = "tb_task")
public class Task implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long task_id;  //任务id

    private Long user_id;  //发布者id

    private String task_name;  //任务名称

    private Date task_create_time;  //任务发布时间

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Date getTask_create_time() {
        return task_create_time;
    }

    public void setTask_create_time(Date task_create_time) {
        this.task_create_time = task_create_time;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id=" + task_id +
                ", user_id=" + user_id +
                ", task_name='" + task_name + '\'' +
                ", task_create_time=" + task_create_time +
                '}';
    }
}
