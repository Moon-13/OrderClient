package cn.edu.jsnu.bean;

public class User {
    private String user_id;
    private String username;
    private String userpass;
    private String mobilenum;
    private String address;
    private String comment;

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserpass() {
        return userpass;
    }
    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }
    public String getMobilenum() {
        return mobilenum;
    }
    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    @Override
    public String toString() {
        return "User [username=" + username + ", userpass=" + userpass
                + ", mobilenum=" + mobilenum + ", address=" + address
                + ", comment=" + comment + "]";
    }

}
