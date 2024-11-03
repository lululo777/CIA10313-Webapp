<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
    
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
                <h5 style="margin-bottom: 25px;font-weight: 900;">所有商品列表</h5>

                <button class="btn"><a href="<c:url value='/backend/productPage/select_page.jsp' />" style="color: white;text-decoration: none;">返回上一頁</a></button>
                
                <!-- 商品表格 -->
                <table class="table" style="border-radius: 5px;width:auto;">
                    <thead class="thead">
                        <th>商品編號</th>
                        <th>商品名稱</th>
                        <th>商品資訊</th>
                        <th>價格</th>
                        <th>庫存</th>
                        <th>商品狀態</th>
                        <th>新增日期</th>
                        <th>圖片</th>
                        <th>修改</th>
                        <th>刪除</th>
                    </thead>

                    <tbody>
                        <tr class="tbody">
                            <th>${requestScope.product.prod_no}</th>
                            <td>${requestScope.product.prod_name}</td>
                            <td>${requestScope.product.prod_desc}</td>
                            <td>${requestScope.product.price}</td>
                            <td>${requestScope.product.available_quantity}</td>
                            <td>${requestScope.product.status==1 ? '已上架':'已下架'} </td>
                            <td>${requestScope.product.listed_time}</td>
                            <td><img src="${pageContext.request.contextPath}/ProductServlet?action=getone_Pic&prod_no=${requestScope.product.prod_no}" alt="商品圖片" style="width:180px;height:180px"></td>
                     
		                      <form method="post" action="<%=request.getContextPath()%>/ProductServlet">
	                               <td><button type="submit" class="btn update" >修改</button></td>
	                               <input type="hidden" name="prodno" value="${product.prod_no}">
	                               <input type="hidden" name="action" value="to_update_page">
		                       </form>     
                     
                            <td><button type="button" name="action" value="delete" class="btn delete-one">刪除</button></td>
                          </tr>
                      
                    </tbody>
                </table>

            </main>
        </div>
    </div>

    <footer class="footer">
        <p>&copy; 2024 修卜人生.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="<c:url value='/backend/productPage/product.js' />"></script>
</body>
</html>
    