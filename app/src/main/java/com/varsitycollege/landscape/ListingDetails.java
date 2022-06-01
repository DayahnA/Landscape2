package com.varsitycollege.landscape;

public class ListingDetails {
    private String title;
    private String category;
    private String caption;
    private String description;

    public ListingDetails(){

    }

    public ListingDetails(String title, String category, String caption, String description) {
        this.title = title;
        this.category = category;
        this.caption = caption;
        this.description = description;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getCaption() { return caption; }

    public void setCaption(String caption) { this.caption = caption; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
