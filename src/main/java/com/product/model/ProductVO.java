package com.product.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


public class ProductVO {
	
	private Integer prod_no;

	private Integer ft_id;
	
	private String prod_name;
	
	private String prod_desc;
		
	private Integer price;
	
	private Integer available_quantity;
	
	private Integer sold_quantity;
	
	private Integer rating;

	private Integer rating_count;
	
	private Integer view_count;
	
	private Timestamp listed_time;
	
	private Byte status;
	
	private ProductImgVO productimgVO;

//	一個無參數建構子
	public ProductVO() {
		super();
	}

//	一格有參數建構子
	public ProductVO(Integer prod_no, Integer ft_id, String prod_name, String prod_desc, Integer price,
			Integer available_quantity, Integer sold_quantity, Integer rating_count, Integer view_count,
			Timestamp listed_time, Byte status) {
		super();
		this.prod_no = prod_no;
		this.ft_id = ft_id;
		this.prod_name = prod_name;
		this.prod_desc = prod_desc;
		this.price = price;
		this.available_quantity = available_quantity;
		this.sold_quantity = sold_quantity;
		this.rating_count = rating_count;
		this.view_count = view_count;
		this.listed_time = listed_time;
		this.status = status;
	}

	public ProductImgVO getProductImgVO() {
		return productimgVO;
	}
	
	public void setProductImgVO(ProductImgVO productimgVO) {
		this.productimgVO = productimgVO;
	}
	
	public Integer getProd_no() {
		return prod_no;
	}

	public void setProd_no(Integer prod_no) {
		this.prod_no = prod_no;
	}

	public Integer getFt_id() {
		return ft_id;
	}

	public void setFt_id(Integer ft_id) {
		this.ft_id = ft_id;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getProd_desc() {
		return prod_desc;
	}

	public void setProd_desc(String prod_desc) {
		this.prod_desc = prod_desc;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getAvailable_quantity() {
		return available_quantity;
	}

	public void setAvailable_quantity(Integer available_quantity) {
		this.available_quantity = available_quantity;
	}

	public Integer getSold_quantity() {
		return sold_quantity;
	}

	public void setSold_quantity(Integer sold_quantity) {
		this.sold_quantity = sold_quantity;
	}

	public Integer getRating_count() {
		return rating_count;
	}

	public void setRating_count(Integer rating_count) {
		this.rating_count = rating_count;
	}

	public Integer getView_count() {
		return view_count;
	}

	public void setView_count(Integer view_count) {
		this.view_count = view_count;
	}

	public Timestamp getListed_time() {
		return listed_time;
	}

	public void setListed_time(Timestamp listed_time) {
		this.listed_time = listed_time;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ProductVO [prod_no=" + prod_no + ", prod_name=" + prod_name + ", prod_desc=" + prod_desc + ", price="
				+ price + ", available_quantity=" + available_quantity + ", listed_time=" + listed_time + ", status="
				+ status + "]";
	}
	
//	將資料庫取出的照片寫到指定資料夾中
	public void writePic(byte[] buf,Integer prod_no) throws IOException {
		File file =new File("src\\main\\webapp\\backend\\getpictest\\prod_no_"+ prod_no+".jpg");
		try {
			
			FileOutputStream fos =new FileOutputStream(file);
			BufferedOutputStream bos =new BufferedOutputStream(fos);
			
			bos.write(buf,0,buf.length);
			
			
			bos.close();
			fos.close();
			
			System.out.println("已成功取出資料庫圖片，請到getpictest資料夾查看!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
