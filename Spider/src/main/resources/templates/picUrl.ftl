
<!DOCTYPE html>
<html><head>
	<title>大爷来玩啊：${id}</title>
    <style type="text/css">
		.main
		{
			text-align: center;
		}
        img
        {
        	width: auto;  
		    height: auto;  
		    max-width: 100%;  
		    max-height: 100%; 
        }
        .avater-box
		{
			max-width : 1550px;
            height: 810px;
            display: block;
            margin: 50px 4%;
        }
        button
        {
        	width: 150px;
        	height: 50px;
        }
    </style>
</head>
<body>
<div class="main">
	当前id：${id}
	<#list manList as l>
		<div class="avater-box" id="${l.id}">
			<img src="${l.url}">
		</div>
	</#list>
	<button><a href="/next?id=${id}">下一个</a></button>
	<script src="https://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript">
	 document.onkeydown = function(event) 
	 {
         var e = event || window.event || arguments.callee.caller.arguments[0];

         if (e && e.keyCode == 68) 
         { 
        	 window.location.href="/next?id=${id}";
         }
     };
     $(".avater-box").click(function(){$.post("/disable?id="+this.id ,function(){})});
	</script>
</div>
</body>