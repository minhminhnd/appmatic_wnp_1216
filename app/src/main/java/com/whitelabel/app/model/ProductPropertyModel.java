package com.whitelabel.app.model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by imaginato on 2015/7/8.
 * Modify by Aaron Hu on 2018/3/28
 *
 * JavaBean of single product
 */
public class ProductPropertyModel implements Serializable {
    private String name;
    private String id;  // Product id
    private String label;
    private String price;
    private String superAttribute;
    private String finalPrice;
    private int eligibleForCod;
    private String superLabel;
    private long stockQty;
    private long qty;
    private int level;
    private String productId; // Always null，don't care
    private long maxSaleQty;
    private int inStock;
    private String image;
    private ArrayList<String> images;
    private ArrayList<ProductPropertyModel> child;
    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    private boolean  selected=true;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getMaxSaleQty() {
        return maxSaleQty;
    }
    private String saveRm;
    private String itemsLeft;

    public String getSaveRm() {
        return saveRm;
    }

    public void setSaveRm(String saveRm) {
        this.saveRm = saveRm;
    }

    public String getItemsLeft() {
        return itemsLeft;
    }

    public void setItemsLeft(String itemsLeft) {
        this.itemsLeft = itemsLeft;
    }

    public void setMaxSaleQty(long maxSaleQty) {
        this.maxSaleQty = maxSaleQty;
    }
    public int getEligibleForCod() {
        return eligibleForCod;
    }

    public void setEligibleForCod(int eligibleForCod) {
        this.eligibleForCod = eligibleForCod;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSuperAttribute() {
        return superAttribute;
    }

    public void setSuperAttribute(String superAttribute) {
        this.superAttribute = superAttribute;
    }

    public String getSuperLabel() {
        return superLabel;
    }

    public void setSuperLabel(String superLabel) {
        this.superLabel = superLabel;
    }

    public long getStockQty() {
        return stockQty;
    }

    public void setStockQty(long stockQty) {
        this.stockQty = stockQty;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<ProductPropertyModel> getChild() {
        return child;
    }

    public void setChild(ArrayList<ProductPropertyModel> child) {
        this.child = child;
    }
}
