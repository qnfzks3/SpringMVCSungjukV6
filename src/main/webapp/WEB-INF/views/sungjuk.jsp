
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>성적처리 프로그램 v6</title>
</head>
<body>
<h1>성적처리 프로그램 v6</h1>

<form name="sj"  method="post">  <%--action 을 지우면 현재 주소 그대로 간다. 그래서 지정을 안함 method 에서 post를 하면 자동으로 vo 객체 데이터에 넣어진다.--%>
  <div>이름 : <input type = "text" name = "name"></div>
  <div>국어 : <input type = "text" name = "kor"></div>
  <div>영어 : <input type = "text" name = "eng"></div>
  <div>수학 : <input type = "text" name = "mat"></div>
  <div><button type="submit">입력완료</button></div>
</form>

</body>
</html>
