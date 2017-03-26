package com.yx.bean;

import org.apache.solr.client.solrj.beans.Field;

/**
 * User: LiWenC
 * Date: 17-3-24
 */
public class Animal {

    @Field
    private String id;//此处类型需要和配置文件里的Field标签的fieldType字段匹配
    @Field
    private String name;

    public Animal() {
    }

    public Animal(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
