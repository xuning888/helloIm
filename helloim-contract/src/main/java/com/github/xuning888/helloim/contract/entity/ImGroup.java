package com.github.xuning888.helloim.contract.entity;

import java.io.Serializable;
import java.util.Date;

public class ImGroup implements Serializable {
    private Long groupId;

    private String groupName;

    private String groupIcon;

    private Long createUser;

    private Integer createUserType;

    private Long ownerUser;

    private Integer ownerUserType;

    private String announcement;

    private Integer atallEnable;

    private Integer muteStatus;

    private Integer groupType;

    private Long groupVersion;

    private String extra;

    private Date createdAt;

    private Date updatedAt;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Integer getCreateUserType() {
        return createUserType;
    }

    public void setCreateUserType(Integer createUserType) {
        this.createUserType = createUserType;
    }

    public Long getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(Long ownerUser) {
        this.ownerUser = ownerUser;
    }

    public Integer getOwnerUserType() {
        return ownerUserType;
    }

    public void setOwnerUserType(Integer ownerUserType) {
        this.ownerUserType = ownerUserType;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public Integer getAtallEnable() {
        return atallEnable;
    }

    public void setAtallEnable(Integer atallEnable) {
        this.atallEnable = atallEnable;
    }

    public Integer getMuteStatus() {
        return muteStatus;
    }

    public void setMuteStatus(Integer muteStatus) {
        this.muteStatus = muteStatus;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Long getGroupVersion() {
        return groupVersion;
    }

    public void setGroupVersion(Long groupVersion) {
        this.groupVersion = groupVersion;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}