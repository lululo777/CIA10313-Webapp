<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>
<%@ page import="java.util.*"%>

<!-- 只要到這頁，不會經過servlet，直接查詢全部資料回來 -->
<%
    ProductService service = new ProductService();
    List<ProductVO> list = service.getALL();
    pageContext.setAttribute("list",list);
%>

    
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修卜人生 - 論壇系統</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

    <link rel="stylesheet" href="<c:url value='/backend/productPage/style.css' />"> 
</head>
<body>
    <header>
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid">
              <a class="navbar-brand" href="#">修卜人生</a>
              <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                  <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">找占卜師</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="#">購物商城</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link " aria-disabled="true">占卜論壇</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="Tarot/Tarot.html">線上占卜遊戲</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="#">客服中心</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="#">最新消息</a>
                  </li>  
                  
                  <li class="nav-item">
                    <a class="nav-link"  aria-current="page" href="#" style="color:rgb(82, 45, 45);" >註冊會員</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="#" style="color:rgb(82, 45, 45);">會員登入</a>
                  </li>
                </ul>

                <form class="d-flex" role="search">
                  <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                  <button class="btn btn-outline-success" type="submit">Search</button>
                </form>

              </div>
            </div>
          </nav>


    </header>

    <div class="container">
        <div class="row">
            <!-- 側邊欄 -->
            <aside class="col-md-3 sidebar">
                <h2>Aside</h2>
                <ul style="list-style: none;">
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>
                    <li><a href="#" style="text-decoration: none; color: rgb(99, 97, 97);">----list----</a></li>


                </ul>
            </aside>

            <!-- 主要內容區 -->
            <main class="col-md-9 main-content">

                <h5 style="font-weight: 900;">我的商品</h5>

                <div class="product-button-container" style="margin:20px auto 10px auto;display: flex;justify-content:center;" >
                    <button class="btn" style="margin: 10px;" id="add-btn">新增商品</button>
                    <button class="btn" style="margin: 10px;"><a href="<c:url value='/backend/productPage/all_product_list.jsp' />" style="color: white;text-decoration: none;">查看所有商品</a></button>
<!--                     <input type="hidden" name="action" value="get_all"> -->
                    <button class="btn" style="margin: 10px;" id="query-btn">查詢單一商品</button>

                </div>
                
                <%-- 錯誤表列 --%>
				<c:if test="${not empty errmsg}">
					  <div style="text-align: center;">
        				<font style="color: red;">商品查詢錯誤!</font><br>
        				<font style="color: red;">請修正以下錯誤，並重新輸入查詢資訊:</font>
    				</div>
					<ul>
					    <c:forEach var="message" items="${errmsg}">
							<li style="color:red; list-style:none; text-align:center;">${message}</li>
						</c:forEach>
					</ul>
					
				</c:if>
                

	<jsp:useBean id="proSvc" scope="page" class="com.product.model.ProductService" />

         <!-- 查詢表單 -->
         <div class="query-container" style="margin-top:30px;">
<%--             <form class="queryform ${errmsg!=null?'on':''}" style="border-radius: 5px ;"> --%>
               
			<!-- 輸入框查詢 -->
               <form class="queryform ${errmsg!=null?'on':''}"  style="border-radius:5px 5px 0 0;" method="post" action="<%=request.getContextPath()%>/ProductServlet">
                <div class="mb-3 form-group" style="margin-left:50px;">
                    <label for="product-name" class="form-label title" style="font-weight: 600;">請填入商品編號 :</label>
                     <input type="text" class="form-control input-value" aria-describedby="emailHelp" style="width: 150px;" name="prod_no" required >
                    <button type="submit" class="btn btn-primary query">查詢</button>
                     <input type="hidden" name="action" value="getone_Display">
                  </div>
                </form>
                          
               <!-- 編號查詢 -->
                 <form class="queryform ${errmsg!=null?'on':''}" method="post" action="<%=request.getContextPath()%>/ProductServlet">
                   <div class="mb-3 form-group" style="margin-left:50px;">
           
                   <b>選擇商品編號  :  </b> 
	                   <select size="1" name="prod_no" style="width:60px;height:30px;">
							<c:forEach var="prodVO" items="${list}">
								<option value="${prodVO.prod_no}">${prodVO.prod_no}
							</c:forEach>
						</select>
                    <button type="submit" class="btn btn-primary query">查詢</button>
                    <input type="hidden" name="action" value="getone_Display">
                  </div>
           		</form>  
                  
                  
                  <!-- 名字查詢 -->
                  <form class="queryform ${errmsg!=null?'on':''}" style="border-radius:0px 0px 5px 5px;" method="post" action="<%=request.getContextPath()%>/ProductServlet">
                   <div class="mb-3 form-group" style="margin-left:50px;">
           
                   <b>選擇商品名稱  :  </b> 
	                   <select size="1" name="prod_no" style="height:30px;">
							<c:forEach var="prodVO" items="${list}">
								<option value="${prodVO.prod_no}">${prodVO.prod_name}
							</c:forEach>
						</select>
                    <button type="submit" class="btn btn-primary query">查詢</button>
                    <input type="hidden" name="action" value="getone_Display">
                  </div>
           		</form>     
                  
            
<!--              </form> -->

		</div>
                <!-- 新增表單 -->
                <form class="addform" style="border-radius: 5px ;" method="post" action="<%=request.getContextPath()%>/ProductServlet" enctype="multipart/form-data">
                    <div class="mb-3" >
                        <h6 style="text-align: center;font-weight: 700;">新增商品</h6>
                    </div>
                    <div class="mb-3">
                      <label for="product-name" class="form-label">商品名稱</label>
                      <input type="text" class="form-control " id="product-name" aria-describedby="emailHelp" name="prodname"  required>
                      <div id="emailHelp" class="form-text"></div>
                    </div>
                  
                    <div class="mb-3">
                        <label for="product-price" class="form-label">商品價格</label>
                        <input type="text"  class="form-control" id="product-price" name="price" required> 
                      </div>

                      <div class="mb-3">
                        <label for="product-quantity" class="form-label">商品庫存</label>
                        <input type="number" min="0" class="form-control" id="product-quantity" name="quantity" required>
                      </div>

                      <div class="mb-3">
                        <label for="product-quantity" class="form-label">商品狀態</label>
                        <select class="form-control" id="choose-status" name="status">
                            <option selected>上架</option>
                            <option>下架</option>
                        </select>
                      </div>

                      <div class="mb-3">
                        <label for="product-img" class="form-label">上傳商品圖片</label>
                        <input type="file" class="form-control" id="product-img" name="photo" required>
                      </div>

                      <div class="mb-3">
                        <label for="product-info" class="form-label">商品資訊</label>
                        <textarea class="form-control" placeholder="填入商品相關的介紹(最多250個字)" id="product-info" name="prodinfo" required></textarea>
                      </div>

                      <div class="text-center">
                      	<input type="hidden" name="action" value="insert">
                        <button type="submit" class="btn btn-primary" style="margin-top: 35px;">建立商品</button>
                    </div>
                   
                  </form>
                    
               

            </main>
        </div>
    </div>

    <footer class="footer">
        <p>&copy; 2024 修卜人生.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
   <script src="<c:url value='/backend/productPage/product.js' />?t=${System.currentTimeMillis()}"></script>
   
</body>
</html>
    