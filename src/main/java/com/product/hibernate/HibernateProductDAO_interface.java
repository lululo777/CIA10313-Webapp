package com.product.hibernate;

import java.util.List;

//hibernate會自己關聯表格，所以參數只要設定傳入主表格即可連動變動
public interface HibernateProductDAO_interface {
	 public void insert(HibernateProductVO productVO);
     public void update(HibernateProductVO productVO);
     public void delete(Integer prod_no);
     public HibernateProductVO findByPrimaryKey(Integer prod_no);
     public List<HibernateProductVO> getAll();
     //萬用複合查詢(傳入參數型態Map)(回傳 List)
//   public List<EmpVO> getAll(Map<String, String[]> map); 
}
