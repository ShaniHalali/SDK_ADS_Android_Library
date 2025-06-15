package com.example.adslib;

public class Ad {
    private String  _id;
    private String ad_link;
    private String ad_location;
    private String ad_type;
    private String beginning_date;
    private String  created_at;
    private String  description;
    private String expiration_date;
    private String ad_image_link;

    private String name;
    private String package_name;
    private String updated_at;
    private String category;

    public Ad() {
    }
    public Ad(String ad_link, String ad_location, String ad_type, String beginning_date, String created_at, String description, String expiration_date, String name, String package_name, String updated_at, String image_link, String category) {
        this.ad_link = ad_link;
        this.ad_location = ad_location;
        this.ad_type = ad_type;
        this.beginning_date = beginning_date;
        this.created_at = created_at;
        this.description = description;
        this.expiration_date = expiration_date;
        this.name = name;
        this.package_name = package_name;
        this.updated_at = updated_at;
        this.ad_image_link = image_link;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public Ad setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getAd_image_link() {
        return ad_image_link;
    }

    public Ad setAd_image_link(String ad_image_link) {
        this.ad_image_link = ad_image_link;
        return this;
    }

    public String get_id() {
        return _id;
    }

    public Ad set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getAd_link() {
        return ad_link;
    }

    public Ad setAd_link(String ad_link) {
        this.ad_link = ad_link;
        return this;
    }

    public String getAd_location() {
        return ad_location;
    }

    public Ad setAd_location(String ad_location) {
        this.ad_location = ad_location;
        return this;
    }

    public String getAd_type() {
        return ad_type;
    }

    public Ad setAd_type(String ad_type) {
        this.ad_type = ad_type;
        return this;
    }

    public String getBeginning_date() {
        return beginning_date;
    }

    public Ad setBeginning_date(String beginning_date) {
        this.beginning_date = beginning_date;
        return this;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Ad setCreated_at(String created_at) {
        this.created_at = created_at;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Ad setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public Ad setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ad setName(String name) {
        this.name = name;
        return this;
    }

    public String getPackage_name() {
        return package_name;
    }

    public Ad setPackage_name(String package_name) {
        this.package_name = package_name;
        return this;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Ad setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
        return this;
    }


}
