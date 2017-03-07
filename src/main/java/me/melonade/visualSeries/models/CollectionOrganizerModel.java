package me.melonade.visualSeries.models;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by damon on 8/25/2016.
 * Model of CollectionOrganizer structure.
 */
public class CollectionOrganizerModel {
    public String _id;
    public List<String> keywords;
    public String desc;
    public Boolean activeFlag;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
}
