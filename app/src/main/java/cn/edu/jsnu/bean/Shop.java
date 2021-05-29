package cn.edu.jsnu.bean;

public class Shop {
    private int shop_id;
    private String shopname;
    private String address;
    private String phonenum;
    private String intro;
    private String pic;
    private String comment;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getShop_id() {
        return shop_id;
    }
    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }
    public String getShopname() {
        return shopname;
    }
    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhonenum() {
        return phonenum;
    }
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
    public String getIntro() {
        return intro;
    }
    public String getPic() {
        return pic;
    }
    public void setIntro(String intro) {
        this.intro = intro;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

}
