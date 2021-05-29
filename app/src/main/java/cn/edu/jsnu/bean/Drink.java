package cn.edu.jsnu.bean;

public class Drink {
    private int drink_id;
    private String drinkname;
    private double price;
    private String intro;
    private String pic;
    private int shop_id;
    private int type_id;
    private int recommand;

    public int getDrink_id() {
        return drink_id;
    }

    public void setDrink_id(int drink_id) {
        this.drink_id = drink_id;
    }

    public String getDrinkname() {
        return drinkname;
    }

    public void setDrinkname(String drinkname) {
        this.drinkname = drinkname;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRecommand() {
        return recommand;
    }

    public void setRecommand(int recommand) {
        this.recommand = recommand;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "drink_id=" + drink_id +
                ", drinkname='" + drinkname + '\'' +
                ", price=" + price +
                ", intro='" + intro + '\'' +
                ", pic='" + pic + '\'' +
                ", shop_id=" + shop_id +
                ", type_id=" + type_id +
                ", recommand=" + recommand +
                '}';
    }
}
