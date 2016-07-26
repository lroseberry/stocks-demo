<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<jsp:useBean id="search" class="com.github.rjbx.demo.stocks.model.StockSearchBean" scope="request"/>
<jsp:setProperty name="search" property="*"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Stock Quote Search</title>

</head>
<body>

<h2>
    Enter information about desired stock quote(s). <br>
</h2>
<h3>
    Enter date information in the following format: YYYY-MM-DD HH:mm:ss
</h3>

<P></P>

<form name="myform" action="servlets/StockSearchServlet/" method="post">
    <fieldset>
    Stock Symbol :<br>  <input type="text" name="symbol" %><br><br>
    Start Time :<br>    <input type="datetime-local" name="startRange"><br><br>
    End Time :<br>      <input type="datetime-local" name="endRange"><br><br>
    Interval :<br>      <input type="radio" name="interval" value="DAY" checked> Daily<br>
                        <input type="radio" name="interval" value="WEEK"> Weekly<br>
                        <input type="radio" name="interval" value="MONTH"> Monthly<br>
    Data Source :<br>   <input type="radio" name="serviceType" value="BASIC" checked> Simulation<br>
                        <input type="radio" name="serviceType" value="DATABASE"> Database<br>
                        <input type="radio" name="serviceType" value="MONTH"> Web<br>
    </fieldset>
    <input type="SUBMIT" value="OK">
    <input type="HIDDEN" name="submit" value="true">
</form>

</body>
</html>