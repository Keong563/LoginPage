package com.example.crysis.loginpage;

public class Model {

    private String foodId, foodName , NewFoodImage , location , pickUpDetail , price , todayDate , expiredDate;
    private String email;



    public Model() {
    }

    public Model(String foodId, String foodName, String newFoodImage, String location, String pickUpDetail, String price, String todayDate, String expiredDate, String email) {
        this.foodId = foodId;
        this.foodName = foodName;
        NewFoodImage = newFoodImage;
        this.location = location;
        this.pickUpDetail = pickUpDetail;
        this.price = price;
        this.todayDate = todayDate;
        this.expiredDate = expiredDate;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public Model(String foodName, String newFoodImage, String location, String pickUpDetail, String price, String todayDate, String expiredDate) {
        this.foodName = foodName;
        NewFoodImage = newFoodImage;
        this.location = location;
        this.pickUpDetail = pickUpDetail;
        this.price = price;
        this.todayDate = todayDate;
        this.expiredDate = expiredDate;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getNewFoodImage() {
        return NewFoodImage;
    }

    public void setNewFoodImage(String newFoodImage) {
        NewFoodImage = newFoodImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPickUpDetail() {
        return pickUpDetail;
    }

    public void setPickUpDetail(String pickUpDetail) {
        this.pickUpDetail = pickUpDetail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
}
