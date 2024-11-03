package com.product.model;

import java.io.BufferedInputStream;
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


@Entity
@Table(name="product_image")
public class ProductImgVO {
	
	@Id
	@Column(name="image_no")
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer image_no;
	
	@Column(name="prod_no")
	private Integer prod_no;
	
	@Column(name="prod_pic",columnDefinition = "longblob")
	private byte[] prod_pic;
	
	@Column(name="updated_time")
	private Timestamp updated_time;
	
	@Column(name="alt_text")
	private String alt_text;
	
	@Column(name="is_primary")
	private Byte is_primary;
	
	@Column(name="created_time")
	private Timestamp created_time;

	public ProductImgVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductImgVO(Integer image_no, Integer prod_no, byte[] prod_pic, Timestamp updated_time, String alt_text,
			Byte is_primary, Timestamp created_time) {
		super();
		this.image_no = image_no;
		this.prod_no = prod_no;
		this.prod_pic = prod_pic;
		this.updated_time = updated_time;
		this.alt_text = alt_text;
		this.is_primary = is_primary;
		this.created_time = created_time;
	}

	public Integer getImage_no() {
		return image_no;
	}

	public void setImage_no(Integer image_no) {
		this.image_no = image_no;
	}

	public Integer getProd_no() {
		return prod_no;
	}

	public void setProd_no(Integer prod_no) {
		this.prod_no = prod_no;
	}

	public byte[] getProd_pic() {
		return prod_pic;
	}

	public void setProd_pic(byte[] prod_pic) {
		this.prod_pic = prod_pic;
	}

	public Timestamp getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(Timestamp updated_time) {
		this.updated_time = updated_time;
	}

	public String getAlt_text() {
		return alt_text;
	}

	public void setAlt_text(String alt_text) {
		this.alt_text = alt_text;
	}

	public Byte getIs_primary() {
		return is_primary;
	}

	public void setIs_primary(Byte is_primary) {
		this.is_primary = is_primary;
	}

	public Timestamp getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Timestamp created_time) {
		this.created_time = created_time;
	}
	
//	將資料庫取出的照片寫到指定資料夾中
	public void writePic(byte[] buf,Integer prod_no) throws IOException {
		File file =new File("src\\main\\webapp\\backend\\getpictest\\prod_no_"+prod_no+".jpg");
		try {
			FileOutputStream fos =new FileOutputStream(file);
			BufferedOutputStream bos =new BufferedOutputStream(fos);
			
			bos.write(buf);
			
			
			bos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
//將圖片資料(路徑)傳入，轉換成byte[]並回傳->準備送進資料庫
	public byte[] readPic(String filepath) throws IOException {
		File file =new File(filepath);
		FileInputStream fis=new FileInputStream(file);
		BufferedInputStream bos =new BufferedInputStream(fis);
	
		byte[] buffer =new byte[bos.available()];
		buffer=bos.readAllBytes();
		
		bos.close();
		fis.close();
		
		return buffer;
	} 
	

	
	
}
