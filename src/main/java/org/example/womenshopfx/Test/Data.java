package org.example.womenshopfx.Test;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("title")
    private String title;
    @SerializedName("id")
    private Long id;
    @SerializedName("children")
    private Boolean children;
    @SerializedName("groups")
    private List<Data> groups;

    public String getTitle() { return title; }
    public Long getId() { return id; }
    public Boolean getChildren() { return children; }
    public List<Data> getGroups() { return groups; }

    public void setTitle(String title) { this.title = title; }
    public void setId(Long id) { this.id = id; }
    public void setChildren(Boolean children) { this.children = children; }
    public void setGroups(List<Data> groups) { this.groups = groups; }

    public String toString() {
        return String.format("title:%s,id:%d,children:%s,groups:%s", title, id, children, groups);
    }
}
