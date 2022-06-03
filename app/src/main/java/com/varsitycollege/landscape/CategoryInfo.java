package com.varsitycollege.landscape;

public class CategoryInfo {
    private String categoryName;
    private String categoryGoal;
    private int catCount; //amount of images in category
    private double percentage;

    public int getCatCount() {
        return catCount;
    }

    public void setCatCount(int catCount) {
        this.catCount = catCount;
    }

    public double getPercentage() {
        double val1 = (float)  catCount / Integer.parseInt(categoryGoal);
        int val2 = (int) (val1 * 100);
        return val2;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryGoal() {
        return categoryGoal;
    }

    public void setCategoryGoal(String categoryGoal) {
        this.categoryGoal = categoryGoal;
    }

    /*public int calcPercentage(int catCount, String categoryGoal){
        int val1 = catCount/ Integer.parseInt(categoryGoal);
        return val1 * 100;
    }*/
}
