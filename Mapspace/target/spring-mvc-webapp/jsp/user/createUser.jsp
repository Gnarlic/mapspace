<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>MapSpace</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
        <style>
            #map {
                height: 80vh;
                width: 75%;
            }
        </style>
    </head>
    <body>
        <nav>

        </nav>
        <div class="container-fluid">
            <div class="row">

                <div class=" col-md-2 px-1 position-fixed text-center" id="sidebar" style="height:100vh;background-color:#C6D0E0">
                    <div id="user" style="padding-top:10px">
                        <c:if test="${pageContext.request.userPrincipal.name != null}">
                            hello, ${pageContext.request.userPrincipal.name}
                        </c:if>
                        <c:if test="${pageContext.request.userPrincipal.name == null}">
                            <div>
                                <c:if test="${param.login_error == 1}">
                                    <h3>Wrong id or password!</h3>
                                </c:if>
                                <form class="" 
                                      role="form" 
                                      method="post" 
                                      action="j_spring_security_check">
                                    <div class="form-group">
                                        <label for="j_username" 
                                               class="control-label">Username:</label>
                                        <div class="">
                                            <input type="text" 
                                                   class="form-control" 
                                                   name="j_username" 
                                                   placeholder="Username"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="j_password" 
                                               class=" control-label">Password:</label>
                                        <div class="">
                                            <input type="password" 
                                                   class="form-control" 
                                                   name="j_password" 
                                                   placeholder="Password"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-offset-4">
                                            <input type="submit" 
                                                   class="btn btn-default" 
                                                   id="search-button" 
                                                   value="Sign In"/>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </c:if>
                        <br/>
                        <a href="<c:url value="/j_spring_security_logout"/>"><input type="submit" class="btn btn-default" value="Logout"/></a>
                        <br/>

                    </div>
                    <hr/>
                    <div id="news">

                    </div>

                </div>
                <div id="userForm" class="col offset-2">
                    <div id="map-functions">
                        <div><h1 class="text-center">
                            Create Account
                            </h1></div>
                        <sf:form class="form-vertical" role="form" modelAttribute="user"
                                 method="POST" action="addUser">
                            <div class="container">
                                <sf:errors path="" cssClass="error" element="div"/>
                            </div>
                            <div class="form-group" style="text-align:center;margin: 0 auto">
                                <label for="user-name" class="col-md-2 control-label">Username: </label>
                                <div class="col-md-4">
                                    <sf:input type="text" class="form-control" id="user-name" name="userName"
                                              path="username" placeholder="username" required="required"/>
                                </div>
                                <label for="pass-word" class="col-md-2 control-label">Password: </label>
                                <div class="col-md-4">
                                    <sf:input type="text" class="form-control" id="pass-word" name="passWord"
                                              path="password" placeholder="password" required="required"/>
                                </div>
                            </div>
                                <div>
                                    <input type="submit" class="btn btn-default" id="login-button" value="create user"/>
                                </div>
                        </sf:form>
                    </div>
                </div>

                <script>
                    var contextPath = '${pageContext.request.contextPath}';

                    var user = '${pageContext.request.userPrincipal.name}';
                </script>
            </div>
            <!-- Placed at the end of the document so the pages load faster -->
            <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/maps.js"></script>
            <script async defer
                    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDb-UFbIs40VfI1cEY91POjnW_DZ56m-qA&callback=initMap">
            </script>

            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

