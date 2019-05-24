package com.yx.bean;

import org.apache.solr.client.solrj.beans.Field;

/**
 * User: LiWenC
 * Date: 17-3-24
 */
public class Person {

    @Field
    private String id;//此处类型需要和配置文件里的Field标签的fieldType字段匹配

    @Field
    private String name;

    @Field
    private String age;

    public Person() {
    }

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
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
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
