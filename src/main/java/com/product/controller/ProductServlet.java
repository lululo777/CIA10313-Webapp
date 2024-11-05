package com.product.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.product.hibernate.HibernateProductService;
import com.product.hibernate.HibernateProductVO;
import com.product.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	
    public ProductServlet() {
        super();
     
    }

    
//    將get得到的請求傳送給doPost處理
    public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
    	doPost(req,res);
    }
   	
	
//    處理表單請求內容
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action =req.getParameter("action"); //取得<input name="action">的value，依照值決定處理請求的方式
		
		
//		----------------------------------<<得到查詢單一商品的請求>>--------------------------------
		if("getone_Display".equals(action)) {
			List<String>errmsg = new ArrayList<String>();
			req.setAttribute("errmsg",errmsg);//判斷錯誤之前先設定屬性，避免重複代碼
			
//			錯誤處理:使用者沒有輸入值
			String str =req.getParameter("prod_no"); //若使用者沒有填入值，回傳空字串->if判斷長度是否為0
			if(str==null || str.trim().length()==0) {
				errmsg.add("請輸入商品編號");
			}
			
			if(!errmsg.isEmpty()) {
				RequestDispatcher failureview =req.getRequestDispatcher("/backend/productPage/select_page.jsp");
				failureview.forward(req, res);
				return; //中斷程式
			}
			
//			使用者輸入非數字格式
			Integer prodno =null;
			
			try {
				prodno =Integer.valueOf(str); //若可將字串轉為數字，則代表輸入為數值，不作任何處理;無法轉換則拋出例外
			}catch(Exception e) { //抓住任何例外
				errmsg.add("商品編號格式錯誤，請輸入數字");
			}
			
			if(!errmsg.isEmpty()) {
				RequestDispatcher failureview =req.getRequestDispatcher("/backend/productPage/select_page.jsp");
//				System.out.println("errmsg:"+errmsg);
				failureview.forward(req, res);
				return; //中斷程式
			}
			
//			<<開始查詢資料>>
			ProductService service =new ProductService();
			ProductVO product = service.getOneProduct(prodno); //傳入已將String型別轉為integer型別的產品編號，進行查詢，回傳一個productVO的物件
//			showPic(product, res);//呼叫顯示圖片的方法，將傳送物件和res
			
//			HibernateProductService s =new HibernateProductService();
//			HibernateProductVO product = s.getOneProduct(prodno);
			
			
//			若查詢不到該筆資料(回傳null)，進行錯誤處理
			if(product==null) {
				errmsg.add("此商品不存在");
			}
		
			
			if(!errmsg.isEmpty()) {
				RequestDispatcher failureview =req.getRequestDispatcher("/backend/productPage/select_page.jsp");
				failureview.forward(req, res);
				return; //中斷程式
			}
			
			
//			<<完成查詢，轉交請求到查詢頁面(one_product_page.jsp)>>
			req.setAttribute("product", product); //設定屬性-查詢回來的product(單筆資料)
			RequestDispatcher successview =req.getRequestDispatcher("/backend/productPage/one_product_page.jsp");
			successview.forward(req, res);
			
			
			
			
			
				
		}//-->getone_Display結束
		
//		單獨處理查詢一個商品的照片請求，直接從<img>的src傳送請求過來
		if("getone_Pic".equals(action)) {
			String str =req.getParameter("prod_no");
			Integer prodno =Integer.valueOf(str);
			ProductService service =new ProductService();
			ProductVO product = service.getOneProduct(prodno);
			
			if(product!=null && product.getProductImgVO()!=null) {
				showPic(product,res);
			}
			
			return;
		}
		
		
//		----------------------------------------<<處理新增的請求>>---------------------------------
	if("insert".equals(action)) {
		
//		錯誤處理在html、js中進行
		
//		<<開始新增>>
		ProductService service =new ProductService();
		ProductVO product =new ProductVO();
		ProductImgVO img =product.getProductImgVO(); //從product物件的實體變數當中取出一個img物件，存入上傳的圖片
		
//		取得輸入欄位的value
		Integer ft_id =3; //先寫固定值，登入後才能抓取使用者的id
		String prod_name =req.getParameter("prodname").trim();
		String prod_desc =req.getParameter("prodinfo").trim();
		Integer price =Integer.valueOf(req.getParameter("price").trim());
		Integer quantity =Integer.valueOf(req.getParameter("quantity").trim());
		Timestamp list_time =new java.sql.Timestamp(System.currentTimeMillis());
		
		
		Part part =req.getPart("photo");
		InputStream in =part.getInputStream();
		byte[] data = in.readAllBytes(); 
		
		in.close();
		
//		isPrimary先寫固定值，之後再設定讓使用者選擇
//		呼叫transferStatus(req)，將status上下架轉換為byte值
		service.addProduct(ft_id, prod_name, prod_desc, price, quantity, list_time,transferStatus(req), data, (byte)1, list_time);
		
//		<<新增完成，轉交請求到:all_product_list.jsp>>
		RequestDispatcher successview = req.getRequestDispatcher("/backend/productPage/all_product_list.jsp");
		successview.forward(req, res);
		
		
	}//->insert結束
	
//	---------------------------------<<刪除資料>>-----------------------------
	
	if("delete".equals(action)) {
		Integer prod_no =Integer.valueOf(req.getParameter("prodno"));
		ProductService service =new ProductService();
		
		boolean isDeleted = service.deleteProduct(prod_no);
		
//		設定回應型別為json
		res.setContentType("application/json");
		PrintWriter out =res.getWriter();
		
		//這裡為response回傳的json格式的內容，需要用key-value的形式來表示->"keyname":value
		out.print("{\"success\": " + isDeleted + "}");
		out.flush();
		
		out.close();
	}
	
//	---------------------------------<<更新資料>>-------------------------------
	if("update".equals(action)) {
//		從請求參數，取得新增的資料
	
		String prod_name =req.getParameter("prodname").trim();
		String prod_desc =req.getParameter("prodinfo").trim();
		Integer price =Integer.valueOf(req.getParameter("price").trim());
		Integer quantity =Integer.valueOf(req.getParameter("quantity").trim());
		Byte status =transferStatus(req);
		Integer prodno =Integer.valueOf(req.getParameter("prodno").trim());
		
		Part part =req.getPart("photo");
		InputStream in =part.getInputStream();
		byte[] data = in.readAllBytes(); 
		
		in.close();
		
		Timestamp updated_time =new java.sql.Timestamp(System.currentTimeMillis());
		
		
//		<<更新資料庫資料>>
		ProductService service =new ProductService();
		ProductVO product = service.updateProduct(prod_name, prod_desc, price, quantity, status, prodno, data,(byte)1, updated_time);

//		<<新增完成，轉交請求到:all_product_list.jsp>>
		RequestDispatcher successview = req.getRequestDispatcher("/backend/productPage/all_product_list.jsp");
		successview.forward(req, res);
		
		
	}
	
	if("to_update_page".equals(action)) {
//		取得該請求的產品編號
		Integer prodno =Integer.valueOf(req.getParameter("prodno").trim());
		
//		查詢單筆資料
		ProductService service =new ProductService();
		ProductVO product = service.getOneProduct(prodno); 
		req.setAttribute("product", product); //設定屬性-查詢回來的product(單筆資料)
		
//		轉交請求到新增頁面
		RequestDispatcher successview =req.getRequestDispatcher("/backend/productPage/update_page.jsp");
		successview.forward(req, res);
		
		
		
	}
	
	
	
	
			
		
	}//-->doPost結束
	
//	--------------------------------------<<以下為自訂方法庫>>----------------------------
//	從product物件中取出byte[]，轉換成資料流輸出
	public void showPic(ProductVO product,HttpServletResponse res) {
		try {
			 byte[] picdata =product.getProductImgVO().getProd_pic();
			 
			 if(picdata==null) {
				res.setContentType("text/plain;charset=Big5");
				res.getWriter().write("未上傳圖片");
				
			 }else {
						 
				  res.setContentType("image/jpeg"); 
				  res.setContentLength(picdata.length);
				  ServletOutputStream out = res.getOutputStream();
				  
				  out.write(picdata);
				  
				  out.close();
			 }
			
			 
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	
	}//->showPic method ends
		
//	將請求得到的status值，轉換成byte，回傳byte
	public Byte transferStatus(HttpServletRequest req) {
		
		Byte status;
		if("上架".equals(req.getParameter("status") ) ) {
			return status=(byte)1; 
			  
			}else if("下架".equals(req.getParameter("status") ) ) {
				return status=(byte)0; 
			}
		
		return null;
	}//-->transferStatus method ends
	

}
