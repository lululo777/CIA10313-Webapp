package com.product.hibernate;

import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.product.model.ProductDAO_interface;
import com.product.model.ProductImgVO;
import com.product.model.ProductVO;

import util.HibernateUtil;

public class HibernateProductDAO implements HibernateProductDAO_interface{

	SessionFactory factory =HibernateUtil.getSessionFactory();

	@Override
	public void insert(HibernateProductVO productVO) {
		Session session =factory.getCurrentSession();
		session.save(productVO);
		
	}

	@Override
	public void update(HibernateProductVO productVO) {
		Session session =factory.getCurrentSession();
		session.update(productVO);
				
	}

	@Override
	public void delete(Integer prod_no) {
		Session session =factory.getCurrentSession();
//		先用session查到型別為HibernateProductVO、PK為prod的物件，並返回該物件
		HibernateProductVO productVO =session.get(HibernateProductVO.class,prod_no);
		if(productVO!=null) {
			session.delete(productVO);
		}
		
	}

	@Override
	public HibernateProductVO findByPrimaryKey(Integer prod_no) {
		Session session =factory.getCurrentSession();
		HibernateProductVO productVO =session.get(HibernateProductVO.class, prod_no);
		
		return productVO;
	}

	@Override
	public List<HibernateProductVO> getAll() {
		Session session =factory.getCurrentSession();
		List<HibernateProductVO> productlist =session.createQuery("form product",HibernateProductVO.class).list();
		return productlist;
	}

	




}
