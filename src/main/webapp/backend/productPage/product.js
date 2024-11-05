/**
 * 
 */


// 條件判斷

$("#add-btn").on("click", function() {
    if (!$("form.addform").hasClass("on")) {

        $("form.addform").addClass("on");
        $("form.queryform").removeClass("on");
    }
});

$("#query-btn").on("click", function() {
    if (!$("form.queryform").hasClass("on")) {
        $("form.queryform").addClass("on");
        $("form.addform").removeClass("on");
    }
});



// 輸入內容篩選

$("button.btn-primary").on("click", function(e) {
	
	let price =$("#product-price").val().trim();
	

	if(isNaN(price) && price!=null){
		e.preventDefault();
		alert("請勿輸入文字，金額請輸入數字");
		$("#product-price").val("");
	}else if(isNaN(price)==false &&parseInt(price)<=0){
		e.preventDefault();
		alert("請輸入大於0的整數");
		$("#product-price").val("");
	}
	
	let name =$("#product-name").val().trim();
	//用正則表達式限制輸入:僅可輸入中文字、a-z、A-Z、0-9的字串，中文為unicode編碼號
	let regex = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/;
	if(name!="" && regex.test(name)==false){
		e.preventDefault();
		alert("請勿輸入符號，請輸入文字");
		$("#product-name").val("");
	}
	
	
});


//使用者輸入資訊框時，觸發input事件，隨即檢查字數
$("#product-info").on("input",function(){
	
	let prodinfo =$("#product-info").val().trim();
		
		if(prodinfo.length>250){
			$("#product-info").val(prodinfo.substring(0,250));
			alert("最多僅可輸入250個字，輸入已達字數上限");
		}

});


//delete-btn串接API
//需傳入的請求內容包含:action、prodno

$("button.delete").on("click",function(){
	
	let is_confirm = confirm("確定要刪除該商品嗎?");
	let prodno =$(this).closest("tr").find("th").text(); //從點擊按紐找到該項目的prodno
	
	if(is_confirm){
		//串接刪除API
		let form_data =new FormData();
		form_data.append("action","delete");
		form_data.append("prodno",prodno);
		

	    fetch("http://localhost:8081/CIA103G5_13/ProductServlet",{
	           method:"POST",
	           body: new URLSearchParams(form_data)
	       }).then(res=>res.json()).then(data=>{
	          
	               $(this).closest("tr").animate({
	                   "opacity": 0
	                 }, 2000, "swing", function(){
	                   $(this).closest("tr").remove();
	                 });
					 
					 setTimeout(function(){
	 					alert("已刪除商品!");
	 				 },2500);
	       
	       })
	}
	
});	





//單一商品的移除
$("button.delete-one").on("click",function(){
	
	let is_confirm = confirm("確定要刪除該商品嗎?");
	let prodno =$(this).closest("tr").find("th").text(); //從點擊按紐找到該項目的prodno
	
	if(is_confirm){
		//串接刪除API
		let form_data =new FormData();
		form_data.append("action","delete");
		form_data.append("prodno",prodno);
		

	    fetch("http://localhost:8081/CIA103G5_13/ProductServlet",{
	           method:"POST",
	           body: new URLSearchParams(form_data)
	       }).then(res=>res.json()).then(data=>{
	          
	               $(this).closest("table").animate({
	                   "opacity": 0
	                 }, 2000, "swing", function(){
	                   $(this).closest("table").remove();
	                 });
					 
					 setTimeout(function(){
						alert("已刪除商品!");
					 },2500);
					
					
	       
	       })
	}
	
});	





//更新資料
//->按鈕觸發後，顯示變更的輸入框&選項&上傳圖片畫面，篩選輸入內容，按下更新鍵時fetch 後端更新API，從data取出資料放到畫面上
//
//$("button.update").on("click", function () {
//    let tr = $(this).closest("tr");
//	
//	tr.find("td.none").removeClass("none");
//    tr.find(".prodname, .prodinfo, .price, .quantity, .status").addClass("none");
//    tr.find("input.update_name, input.update_info, input.update_price, input.update_quantity, input.update_pic").removeClass("none");
//    tr.find("select.update_status").removeClass("none");
//	
//});













