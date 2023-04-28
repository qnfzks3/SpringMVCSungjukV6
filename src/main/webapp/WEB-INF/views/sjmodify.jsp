
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>성적처리 프로그램 v6</title>
</head>
<body>
<h1>성적처리 프로그램 v6 - 수정</h1>

<form name="sj"  method="post">  <%--action 을 지우면 현재 주소 그대로 간다. 그래서 지정을 안함--%>
  <div>이름 : <input type = "text" name = "name" value="${sj.name}"></div>
  <div>국어 : <input type = "text" name = "kor" value="${sj.kor}"></div>
  <div>영어 : <input type = "text" name = "eng" value="${sj.eng}"></div>
  <div>수학 : <input type = "text" name = "mat" value="${sj.mat}"></div>
  <input type="hidden" name="sjno" value="${sj.sjno}">
  <div><button type="submit">수정 완료</button></div>
</form>

</body>
</html>
