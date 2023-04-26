<%--
 내부적으로 뭔가를 만들고 나서 보여주는 역할
 기본적으로 model, controller, service , dao 를 각각 있어야한다.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <%--테크 라이브러리를 넣을수있게--%>
                                                                <%--<dependency>
                                                                    <groupId>javax.servlet</groupId>
                                                                    <artifactId>jstl</artifactId>
                                                                    <version>1.2</version>

                                                                </dependency>
                                                                이걸 porm에 추가해준다.--%>


<html>
<head>
    <title>성적 리스트</title>
</head>
<body>
    <h1>성적 리스트</h1>
    <table border="1" width="750px" cellpadding="10px" cellspacing="0">
        <tr>
            <th>이름</th>
            <th>국어</th>
            <th>영어</th>
            <th>수학</th>
        </tr>
        <%--  <c: 하고 컨트롤 스페이스하면 사용할수있는 구문이 다나온다.--%>
        <%--for(SungJuk sj : sjs) 와 같다. sj의 타입은 안적어도 된다.--%>
        <c:forEach items="${sjs}" var="sj">
            <tr>
                <td><a href="/view?sjno=${sj.sjno}">${sj.sjno}</a> </td>
                <td>${sj.kor}</td>
                <td>${sj.eng}</td>
                <td>${sj.mat}</td>
            </tr>

        </c:forEach>

    </table>






</body>
</html>
