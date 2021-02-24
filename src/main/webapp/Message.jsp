<%-- 
    Document   : Message
    Created on : Feb 19, 2021, 5:41:50 PM
    Author     : santosh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Message</title>
    </head>
    <body>
    <center>
        <h2>Message!</h2>
        <h3><%=request.getAttribute("Message")%></h3>

        <a href="/HPD2">Index</a>

    </center>
</body>
</html>
