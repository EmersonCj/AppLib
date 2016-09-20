package lib.emerson.com.emersonapplib.Test;

/**
 * Created by Administrator on 2016/7/8.
 */
public class GoodsBean {

    private String imgUrl;//商品图片
    private String price;//商品单价
    private String allPrice;//加入购物车的总价
    private String buyNumber;//加入购物车的总数量
    private String supermarketPrice;//超市价格
    private String goodsName;//商品名

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }

    public String getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(String buyNumber) {
        this.buyNumber = buyNumber;
    }

    public String getSupermarketPrice() {
        return supermarketPrice;
    }

    public void setSupermarketPrice(String supermarketPrice) {
        this.supermarketPrice = supermarketPrice;
    }
}
