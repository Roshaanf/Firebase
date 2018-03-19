package roshaan.firebase.models;

/**
 * Created by Roshaann 2.7 gpa on 17/03/2018.
 */

public class UserModel {
    String email;
    String userName;
    String contact;
    String userType;
    String dateOfBirth;
    String userID;
    String imageUrl;

    public UserModel(String email, String userName, String contact, String userType, String dateOfBirth,String userID,String imageUrl) {
        this.email = email;
        this.userName = userName;
        this.contact = contact;
        this.userType = userType;
        this.dateOfBirth = dateOfBirth;
        this.userID=userID;
        this.imageUrl=imageUrl;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", contact='" + contact + '\'' +
                ", userType='" + userType + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }

    public UserModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
