/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qltc.bean;

import com.qltc.pojo.Product;
import com.qltc.services.ProductService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author Vo Pham Huyen Khanh
 */
@ManagedBean
@Named(value = "productBean")
@SessionScoped
public class ProductBean implements Serializable {

    private String keyword;

    private int hallId;
    private String hallName;
    private String hallDescription;
    private BigDecimal hallPrice;
    private String hallNote;
    private boolean hallStatus;
    private Part hallImageFile;

    private static ProductService productService = new ProductService();

    /**
     * Creates a new instance of ProductBean
     */
    public ProductBean() {
        this.keyword = "";
        if (!FacesContext.getCurrentInstance().isPostback()) {
            String hall_Id = FacesContext.getCurrentInstance()
                                    .getExternalContext()
                                    .getRequestParameterMap().get("hall_id");
            if (hall_Id != null && !hall_Id.isEmpty()) {
                Product p = productService.getHallById(Integer.parseInt(hall_Id));
                this.hallId = p.getId();
                this.hallName = p.getName();
                this.hallDescription = p.getDescription();
                this.hallPrice = p.getPrice();
                this.hallNote = p.getNote();
                this.hallStatus = p.getStatus();
//                this.hallImageFile =  p.getImageUrl();

            }
        }
    }

    public List<Product> getAllHall() {
        List<Product> products = productService.getAllHall(keyword);
        return products;
    }

    public List<Product> getService() {
        List<Product> products = productService.getService();
        return products;
    }

    //create update chung mot ham
    public String createHall() {
        String hallId = FacesContext.getCurrentInstance().getExternalContext()
                                    .getRequestParameterMap().get("hall_id");
        Product p;
        if (this.hallId > 0) //            trang thai persistence: da link toi 1 product duoi db
        {
            p = productService.getHallById(this.hallId);
        } else {
            p = new Product(); //transient: trang thai moi hoan toan
        }
        p.setName(this.hallName);
        p.setDescription(this.hallDescription);
        p.setUnit("02");
        p.setType("03");
        p.setCategory("0301");
        p.setPrice(this.hallPrice);
        p.setNote(this.hallNote);
        p.setStatus(this.hallStatus);

        try {
            if (this.hallImageFile != null) {
                this.uploadFile();

                p.setImageUrl("upload" + this.hallImageFile.getSubmittedFileName());
            }
            if (productService.createOrUpdateHall(p) == true) {
                return "manage-hall?faces-redirect=true";
            }
        } catch (IOException ex) {
            Logger.getLogger(ProductBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "hall";
    }

    public String deleteHall(Product p) throws Exception {
        if (productService.deleteHall(p)) {
            return "successful";
        }
        throw new Exception("Something wrong!!!");
    }

    private void uploadFile() throws IOException {
//        String path = FacesContext.getCurrentInstance()
//                .getExternalContext().getRealPath("/resources/images/upload")
//                + "/" + this.hallImageFile.getSubmittedFileName();
//        String path = "F:\\GithubDesktop\\WeddingManagement\\WeddingManagement\\src\\main\\webapp\\resources\\images\\upload\\"
//                + this.hallImageFile.getSubmittedFileName();
        String path = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getInitParameter("com.qltc.uploadPath")
                + this.hallImageFile.getSubmittedFileName();
        try (InputStream in = this.hallImageFile.getInputStream()) {
            FileOutputStream out = new FileOutputStream(path);
            byte[] b = new byte[1024];
            int byteRead;
            while ((byteRead = in.read(b)) != -1) {
                out.write(b, 0, byteRead);
            }
        }
    }

    /**
     * @return the hallName
     */
    public String getHallName() {
        return hallName;
    }

    /**
     * @param hallName the hallName to set
     */
    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    /**
     * @return the hallDescription
     */
    public String getHallDescription() {
        return hallDescription;
    }

    /**
     * @param hallDescription the hallDescription to set
     */
    public void setHallDescription(String hallDescription) {
        this.hallDescription = hallDescription;
    }

    /**
     * @return the hallPrice
     */
    public BigDecimal getHallPrice() {
        return hallPrice;
    }

    /**
     * @param hallPrice the hallPrice to set
     */
    public void setHallPrice(BigDecimal hallPrice) {
        this.hallPrice = hallPrice;
    }

    /**
     * @return the hallNote
     */
    public String getHallNote() {
        return hallNote;
    }

    /**
     * @param hallNote the hallNote to set
     */
    public void setHallNote(String hallNote) {
        this.hallNote = hallNote;
    }

    /**
     * @return the hallStatus
     */
    public boolean isHallStatus() {
        return hallStatus;
    }

    /**
     * @param hallStatus the hallStatus to set
     */
    public void setHallStatus(boolean hallStatus) {
        this.hallStatus = hallStatus;
    }

    /**
     * @return the productService
     */
    public static ProductService getProductService() {
        return productService;
    }

    /**
     * @param aProductService the productService to set
     */
    public static void setProductService(ProductService aProductService) {
        productService = aProductService;
    }

    /**
     * @return the hallImageFile
     */
    public Part getHallImageFile() {
        return hallImageFile;
    }

    /**
     * @param hallImageFile the hallImageFile to set
     */
    public void setHallImageFile(Part hallImageFile) {
        this.hallImageFile = hallImageFile;
    }

    /**
     * @return the hallId
     */
    public int getHallId() {
        return hallId;
    }

    /**
     * @param hallId the hallId to set
     */
    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
