package com.product.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.JDBCUtil;

public class ProductDAOJDBC implements ProductDAO_interface{
	
//	商品表格SQL語句
	private static final String INSERT_STMT ="insert into product(ft_id,prod_name,prod_desc,price,available_quantity,listed_time,status) values(?,?,?,?,?,?,?)";
	private static final String GET_ALL = "SELECT p.prod_no, p.prod_name, p.prod_desc, p.price, p.available_quantity, p.listed_time, p.status, pi.prod_pic "
            + "FROM product p "
            + "LEFT JOIN product_image pi ON p.prod_no = pi.prod_no "
            + "ORDER BY p.prod_no";
	
	private static final String GET_ONE ="select prod_no,prod_name,prod_desc,price,available_quantity,listed_time,status from product where prod_no = ?";
	private static final String DELETE ="delete from product where prod_no =?";
	private static final String UPDATE ="update product set prod_name =?, prod_desc =? ,price =? ,available_quantity =? ,status=? where prod_no = ?";
	
//	圖片表格SQL語句
	private static final String INSERT_PIC ="insert into product_image(prod_no,prod_pic,is_primary,created_time) values(?,?,?,?)";
	private static final String GET_ONE_PIC ="select prod_pic from product_image where prod_no =?";
	private static final String DELETE_PIC ="delete from product_image where prod_no =?";
	private static final String UPDATE_PIC ="update product_image set prod_pic=?,updated_time =? where prod_no =?";
	
	
//	建立連線池，讓JVM每次執行時都先載入驅動
	static {
		try {
			Class.forName(JDBCUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void insert(ProductVO productVO,ProductImgVO productimgVO) {
		Connection con =null;
		PreparedStatement pstmt =null;
		PreparedStatement pstmtpic =null;
		ResultSet generatedKey=null;
		
		try {
			 con =DriverManager.getConnection(JDBCUtil.URL,JDBCUtil.USER,JDBCUtil.PASSWORD);
			 pstmt =con.prepareStatement(INSERT_STMT,Statement.RETURN_GENERATED_KEYS);
			 
			 pstmt.setInt(1,productVO.getFt_id());
			 pstmt.setString(2,productVO.getProd_name());
			 pstmt.setString(3,productVO.getProd_desc());
			 pstmt.setInt(4,productVO.getPrice());
			 pstmt.setInt(5,productVO.getAvailable_quantity());
			 pstmt.setTimestamp(6,productVO.getListed_time());
			 pstmt.setByte(7,productVO.getStatus());
			
			 pstmt.executeUpdate();
			 
			 pstmtpic=con.prepareStatement(INSERT_PIC,Statement.RETURN_GENERATED_KEYS);
			 
//			 查詢取得商品自增的prod_no，依照prod_no新增商品圖片
			 generatedKey =pstmt.getGeneratedKeys();
			 
			 int prod_no=0;
			 if(generatedKey.next()) {
				 prod_no =generatedKey.getInt(1);

			 }
			 
			 pstmtpic.setInt(1, prod_no);
			 pstmtpic.setBytes(2,productimgVO.getProd_pic());
			 pstmtpic.setByte(3,productimgVO.getIs_primary());
			 pstmtpic.setTimestamp(4,productimgVO.getCreated_time());
			 
			 pstmtpic.executeUpdate();
			  
			 
			 
		} catch (SQLException e) {
		e.printStackTrace();
		}finally {
			closeResources(con,pstmt,pstmtpic,generatedKey);
		}
		
	}

	@Override
	public void update(ProductVO productVO,ProductImgVO productimgVO) {
		Connection con =null;
		PreparedStatement pstmt =null;
		PreparedStatement pstmtpic=null;
		
		try {
			 con =DriverManager.getConnection(JDBCUtil.URL,JDBCUtil.USER,JDBCUtil.PASSWORD);
			 pstmt =con.prepareStatement(UPDATE);
			 
			 pstmt.setString(1,productVO.getProd_name());
			 pstmt.setString(2,productVO.getProd_desc());
			 pstmt.setInt(3,productVO.getPrice());
			 pstmt.setInt(4,productVO.getAvailable_quantity());
			 pstmt.setByte(5, productVO.getStatus());
			 pstmt.setInt(6,productVO.getProd_no());
			
			
			 pstmt.executeUpdate();
			 
			 pstmtpic =con.prepareStatement(UPDATE_PIC);
			 
			 pstmtpic.setBytes(1,productimgVO.getProd_pic());
			 pstmtpic.setTimestamp(2,productimgVO.getUpdated_time());
			 pstmtpic.setInt(3,productimgVO.getProd_no());
			 
			 pstmtpic.executeUpdate();
			 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("更新失敗");
		}finally {
			closeResources(con,pstmt,pstmtpic);
			
		}
		
	}

	@Override
	public void delete(Integer prod_no) {
		Connection con =null;
		PreparedStatement pstmt =null;
		PreparedStatement pstmtpic =null;
		
		try {
			 con =DriverManager.getConnection(JDBCUtil.URL,JDBCUtil.USER,JDBCUtil.PASSWORD);
			 pstmt =con.prepareStatement(DELETE);
			 pstmtpic =con.prepareStatement(DELETE_PIC);
			 
			 pstmt.setInt(1, prod_no);
			 pstmtpic.setInt(1,prod_no);
			
			
			 pstmt.executeUpdate();
			 pstmtpic.executeUpdate();
			 
		} catch (SQLException e) {
			System.out.println("刪除失敗");
		}finally {
			closeResources(con,pstmt,pstmtpic);
		}
		
	}

	@Override
	public ProductVO findByPrimaryKey(Integer prod_no) {
		Connection con =null;
		PreparedStatement pstmt =null;
		PreparedStatement pstmtpic =null;
		ResultSet rs =null;
		ResultSet rspic =null;
		
		ProductVO productVO =null;
		ProductImgVO productimgVO =null;
		
		try {
			 con =DriverManager.getConnection(JDBCUtil.URL,JDBCUtil.USER,JDBCUtil.PASSWORD);
			 pstmt =con.prepareStatement(GET_ONE);
			 pstmtpic =con.prepareStatement(GET_ONE_PIC);
			 
			 pstmt.setInt(1, prod_no);
			 pstmtpic.setInt(1, prod_no);
			 
			 rs=pstmt.executeQuery();
			 rspic =pstmtpic.executeQuery();
			 
			 
			
			 if(rs.next()) {
				 productVO =new ProductVO();
				 productVO.setProd_no(rs.getInt("prod_no"));
				 productVO.setProd_name(rs.getString("prod_name"));
				 productVO.setProd_desc(rs.getString("prod_desc"));
				 productVO.setPrice(rs.getInt("price"));
				 productVO.setAvailable_quantity(rs.getInt("available_quantity"));
				 productVO.setListed_time(rs.getTimestamp("listed_time"));
				 productVO.setStatus(rs.getByte("status"));
				 
				 if(rspic.next()) {
					 productimgVO =new ProductImgVO();
					 productimgVO.setProd_pic(rspic.getBytes("prod_pic"));
					 productVO.setProductImgVO(productimgVO);
				 }	
				 
			 }
			 	 
	
		} catch (SQLException e) {
			System.out.println("查詢失敗");
		}finally {
			closeResources(con,pstmt,pstmtpic,rs,rspic);
		}
		return productVO;
	}

	@Override
	public List<ProductVO> getAll() {
		
		Connection con =null;
		PreparedStatement pstmt =null;
		PreparedStatement pstmtpic =null;
		ResultSet rs =null;
		ResultSet rspic =null;
		
		List<ProductVO> list =null;
		ProductVO productVO =null;
		ProductImgVO productimgVO =null;
		
		try {
			 con =DriverManager.getConnection(JDBCUtil.URL,JDBCUtil.USER,JDBCUtil.PASSWORD);
			 pstmt =con.prepareStatement(GET_ALL);
			 list =new ArrayList();
			 
			 rs=pstmt.executeQuery();
			 
			 while(rs.next()) {
				 productVO =new ProductVO();
				 productVO.setProd_no(rs.getInt("prod_no"));
				 productVO.setProd_name(rs.getString("prod_name"));
				 productVO.setProd_desc(rs.getString("prod_desc"));
				 productVO.setPrice(rs.getInt("price"));
				 productVO.setAvailable_quantity(rs.getInt("available_quantity"));
				 productVO.setListed_time(rs.getTimestamp("listed_time"));
				 productVO.setStatus(rs.getByte("status"));
				 productVO.setProductImgVO(productimgVO);
				 
				  // 查詢對應的圖片資料
		            pstmtpic = con.prepareStatement(GET_ONE_PIC);
		            pstmtpic.setInt(1, productVO.getProd_no());
		            rspic = pstmtpic.executeQuery();
		            
		            if (rspic.next()) {
		                // 創建 ProductImgVO 物件並設定圖片資料
		                ProductImgVO productImgVO = new ProductImgVO();
		                productImgVO.setProd_no(productVO.getProd_no());
		                productImgVO.setProd_pic(rspic.getBytes("prod_pic"));
		                
		                // 將圖片物件設置到 ProductVO 中
		                productVO.setProductImgVO(productImgVO);
		            }
				 
				 
				 list.add(productVO);
			 }
			 
			 
			 
		} catch (SQLException e) {
			System.out.println("查詢失敗");
		}finally {
			closeResources(con,pstmt,pstmtpic,rs,rspic);
		}
		return list;
	
	}

//	...代表可以傳入多個參數
	private void closeResources(AutoCloseable...autoCloseables) {
		for(AutoCloseable resource :autoCloseables) {
			if(resource!=null) {
				try {
					resource.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	public static void main(String[] args) throws IOException {
		
		ProductDAOJDBC dao =new ProductDAOJDBC();
		
//		新增
//		ProductVO p =new ProductVO();
//		p.setFt_id(1);
//		p.setProd_name("幸運手鍊");
//		p.setProd_desc("帶來幸運");
//		p.setPrice(450);
//		p.setAvailable_quantity(20);
//		p.setListed_time(java.sql.Timestamp.valueOf("2024-10-12 09:25:12"));
//		p.setStatus((byte)1);
////		
//		ProductImgVO img = new ProductImgVO();
//		img.setProd_pic(img.readPic("src\\main\\webapp\\backend\\image\\1.jpg"));
//		img.setIs_primary((byte)0);
//		img.setCreated_time(java.sql.Timestamp.valueOf("2024-10-12 09:25:12"));
////		
//		dao.insert(p, img);
			
	
//		修改
//		ProductVO p =new ProductVO();
//		p.setProd_name("幸運小物");
//		p.setProd_desc("增添好運");
//		p.setPrice(250);
//		p.setAvailable_quantity(12);
//		p.setStatus((byte)1);
//		p.setProd_no(19);
//		
//		ProductImgVO img = new ProductImgVO();
//		img.setProd_no(19);
//		img.setProd_pic(img.readPic("src\\main\\webapp\\backend\\image\\2.jpg"));
//		img.setUpdated_time(java.sql.Timestamp.valueOf("2024-07-12 12:24:36"));
//	
//		dao.update(p, img);
		
//		刪除
//		dao.delete(21);
		
		
//		查詢單一資料
//		ProductVO p =dao.findByPrimaryKey(7);
//		System.out.println(p.toString());
//		if(p.getProductImgVO().getProd_pic()!=null) { 
//			p.writePic(p.getProductImgVO().getProd_pic(),p.getProd_no());
//		}else {
//			System.out.println("商品編號:"+p.getProd_no()+"沒有商品圖片");
//		}
//		
		
//		查詢全部
//		List<ProductVO> list =dao.getAll();
//		for(ProductVO product : list) {
//			System.out.println(product.toString());
//			
//			if(product.getProductImgVO().getProd_pic()!=null) {
//				product.writePic(product.getProductImgVO().getProd_pic(),product.getProd_no());	
//			}else {
//				System.out.println("商品編號:"+product.getProd_no()+"沒有商品圖片");
//				continue;
//			}
//			
//		}
//		
	
		
		
		
	}
	
	
}
