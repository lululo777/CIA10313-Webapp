package com.product.hibernate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class HibernateProductService {
	
	private HibernateProductDAO dao;
	private SessionFactory sf;
	
	
//	一初始化service物件時，直接生成一個dao & factory，並存在實體變數當中
	public HibernateProductService() {
		this.dao =new HibernateProductDAO();
		this.sf=HibernateUtil.getSessionFactory();
		
	}
	
	
//	----------------------------------------<<新增>>-------------------------------------
	public HibernateProductVO addProduct(Integer ft_id,String prod_name,String prod_desc,Integer price,Integer available_quantity,Timestamp listed_time,Byte status,byte[] prod_pic,byte is_primary,Timestamp created_time) {
//		取得session，開啟一個交易
		Session session =sf.getCurrentSession();
//		封裝傳入資料到productVO物件
		HibernateProductVO hibernateproductVO =null;
		
		try {
		Transaction tx = session.beginTransaction();
		
		hibernateproductVO.setFt_id(ft_id);
		hibernateproductVO.setProd_name(prod_name);
		hibernateproductVO.setProd_desc(prod_desc);
		hibernateproductVO.setPrice(price);
		hibernateproductVO.setAvailable_quantity(available_quantity);
		hibernateproductVO.setListed_time(listed_time);
		hibernateproductVO.setStatus(status);
		
//		宣告一個hashSet存放圖片物件->配合productVO setter的型別set<HibernateProductImgVO>
		Set<HibernateProductImgVO> img =new HashSet<HibernateProductImgVO>();
		HibernateProductImgVO productimg = getImgVO(prod_pic, is_primary, created_time);//透過方法封裝圖片到圖片物件中
		
		img.add(productimg);
		
//		包好的圖片物件，存入product物件;商品物件也存入productimg物件(一個商品物件，相當於存入商品編號)->雙向關聯，兩表都相互存入，確保資料正確存入
		hibernateproductVO.setProductimgVO(img);
		productimg.setProduct(hibernateproductVO);
		
		dao.insert(hibernateproductVO);
		
//		commit &結束session
		tx.commit();
		
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			
		}
		
		return hibernateproductVO;
		
	}
	
	
//	----------------------------------------<<更新>>-------------------------------------
	public HibernateProductVO updateProduct(String prod_name,String prod_desc,Integer price,Integer available_quantity,Byte status,Integer prod_no,byte[] prod_pic,byte is_primary,Timestamp updated_time) {
//		取得session
		Session session =sf.getCurrentSession();

		HibernateProductVO hibernateproductVO =null;
		HibernateProductImgVO hibernateproductimgVO =null;
		
		try {
//		開啟一個交易
		Transaction tx = session.beginTransaction();
			
//		封裝傳入資料到productVO物件
		hibernateproductVO.setProd_name(prod_name);
		hibernateproductVO.setProd_desc(prod_desc);
		hibernateproductVO.setPrice(price);
		hibernateproductVO.setAvailable_quantity(available_quantity);
		hibernateproductVO.setStatus(status);
		hibernateproductVO.setProd_no(prod_no);
		
		Set<HibernateProductImgVO> img =new HashSet<HibernateProductImgVO>();
		HibernateProductImgVO imgVO =updateImg(prod_pic, is_primary, updated_time);
		img.add(imgVO);
		
		hibernateproductVO.setProductimgVO(img);
		imgVO.setProduct(hibernateproductVO);
		
		dao.update(hibernateproductVO);
		
		
		tx.commit();
		
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return hibernateproductVO;
		
	}
	
//	----------------------------------------<<刪除>>-------------------------------------
	public boolean deleteProduct(Integer prod_no) {
		
		Session session =sf.getCurrentSession();
		try {
			Transaction tx = session.beginTransaction();
			dao.delete(prod_no);
			tx.commit();
			
			return true;
			
		}catch(Exception e){
			
			e.printStackTrace();
			session.getTransaction().rollback();
			
			return false;
		}
		
	}
	
//	----------------------------------------<<單一查詢>>-------------------------------------
	public HibernateProductVO getOneProduct(Integer prod_no) {
		Session session =sf.getCurrentSession();
		HibernateProductVO hibernateproductVO =null; //沒有查到時會回傳null
		try {
			Transaction tx = session.beginTransaction();
			hibernateproductVO =dao.findByPrimaryKey(prod_no);
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return hibernateproductVO;
	}
	
//	----------------------------------------<<查詢全部>>-------------------------------------
	public List<HibernateProductVO> getALL(){
		Session session =sf.getCurrentSession();
		List<HibernateProductVO> productlist =new ArrayList<HibernateProductVO>();
		
		try {
			Transaction tx = session.beginTransaction();
			productlist = dao.getAll();
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		return productlist;
	}
	
	
	
	
//	----------------------------------------<<封裝圖片的方法>>-------------------------------------
	
	
	private HibernateProductImgVO updateImg(byte[] prod_pic,byte is_primary,Timestamp updated_time) {
//		將傳入資料放入productimgVO的物件裡，包起來回傳
		HibernateProductImgVO imgVO = new HibernateProductImgVO();
		
		imgVO.setProd_pic(prod_pic);
		imgVO.setIs_primary(is_primary);
		imgVO.setCreated_time(updated_time);
		
		return imgVO;
		
	}
	
	
	
//	封裝圖片相關的資料
	private HibernateProductImgVO getImgVO(byte[] prod_pic,byte is_primary,Timestamp created_time) {
		
//		將傳入資料放入productimgVO的物件裡，包起來回傳
		HibernateProductImgVO imgVO = new HibernateProductImgVO();
		
		imgVO.setProd_pic(prod_pic);
		imgVO.setIs_primary(is_primary);
		imgVO.setCreated_time(created_time);
		
		return imgVO;
	}
	
	
	
}
