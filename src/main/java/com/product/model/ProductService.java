package com.product.model;

import java.sql.Timestamp;
import java.util.List;

public class ProductService {
	
	private ProductDAOJDBC dao;
	
	public ProductService() {
		this.dao =new ProductDAOJDBC();
	}
	
	public ProductVO addProduct(Integer ft_id,String prod_name,String prod_desc,Integer price,Integer available_quantity,Timestamp listed_time,Byte status,byte[] prod_pic,byte is_primary,Timestamp created_time) {
		ProductVO productVO =new ProductVO();
		ProductImgVO productimgVO =new ProductImgVO();
		
		productVO.setFt_id(ft_id);
		productVO.setProd_name(prod_name);
		productVO.setProd_desc(prod_desc);
		productVO.setPrice(price);
		productVO.setAvailable_quantity(available_quantity);
		productVO.setListed_time(listed_time);
		productVO.setStatus(status);
		
		productimgVO.setProd_pic(prod_pic);
		productimgVO.setIs_primary(is_primary);
		productimgVO.setCreated_time(created_time);
		
		dao.insert(productVO, productimgVO);
		
		return productVO;
		
	}
	
	public ProductVO updateProduct(String prod_name,String prod_desc,Integer price,Integer available_quantity,Byte status,Integer prod_no,byte[] prod_pic,byte is_primary,Timestamp updated_time) {
		ProductVO productVO =new ProductVO();
		ProductImgVO productimgVO =new ProductImgVO();
		
		productVO.setProd_name(prod_name);
		productVO.setProd_desc(prod_desc);
		productVO.setPrice(price);
		productVO.setAvailable_quantity(available_quantity);
		productVO.setStatus(status);
		productVO.setProd_no(prod_no);
		
		productimgVO.setProd_pic(prod_pic);
		productimgVO.setUpdated_time(updated_time);
		productimgVO.setProd_no(prod_no);;
		
		dao.update(productVO, productimgVO);
		
		return productVO;
		
	}
	
	public boolean deleteProduct(Integer prod_no) {
		try {
			dao.delete(prod_no);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	public ProductVO getOneProduct(Integer prod_no) {
		return dao.findByPrimaryKey(prod_no);
	}
	
	public List<ProductVO> getALL(){
		return dao.getAll();
	}
	
	
	
	
}
