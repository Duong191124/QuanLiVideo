<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Online Entertainment</title>
    <%@include file="/common/head.jsp" %>
</head>

<body>
<%@include file="/common/header.jsp" %>

<div class="container-fluid tm-mt-60">
    <div class="row tm-mb-50 ">
        <div class="col-lg-12 col-12 mb-5">
            <center><h2 class="tm-text-primary mb-5">Login</h2></center>
            <form id="login-form" action="login" method="POST" class="tm-contact-form mx-auto">
                <div class="form-group">
                    <input type="text" name="username" class="form-control rounded-0" placeholder="Username" required />
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control rounded-0" placeholder="Password" required />
                </div>
                <div class="form-group">
                    <div class="custom-control custom-checkbox mr-sm-2">
                        <input type="checkbox" class="custom-control-input" id="customControlAutosizing">
                        <label class="custom-control-label" for="customControlAutosizing">Remember me</label>
                    </div>
                </div>

                <div class="form-group tm-text-right">
                    <button type="submit" class="btn btn-success w-100 mt-30">Login</button>
                </div>
            </form>
        </div>
    </div>
</div>


<%@include file="/common/footer.jsp" %>
</body>

</html>
