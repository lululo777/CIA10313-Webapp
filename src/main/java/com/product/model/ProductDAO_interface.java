package com.product.model;

import java.util.List;


public interface ProductDAO_interface {
	 public void insert(ProductVO productVO,ProductImgVO productimgVO);
     public void update(ProductVO productVO,ProductImgVO productimgVO);
     public void delete(Integer prod_no);
     public ProductVO findByPrimaryKey(Integer prod_no);
     public List<ProductVO> getAll();
     //萬用複合查詢(傳入參數型態Map)(回傳 List)
//   public List<EmpVO> getAll(Map<String, String[]> map); 
}
