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
            <center><h2 class="tm-text-primary mb-5">Forgot Password</h2></center>
            <div class="form-group">
                <input type="email" name="email" id="email" class="form-control rounded-0" placeholder="Email" required />
            </div>
            <div class="form-group tm-text-right">
                <button type="submit" id="forgotPass" class="btn btn-success w-100 mt-30">Forgot Password</button>
            </div>
            <h5 style="color: red" id="message"></h5>
        </div>
    </div>
</div>


<%@include file="/common/footer.jsp" %>
<script>
    $('#forgotPass').click(function (){
        $('#message').text('');
        var email = $('#email').val();
        var formData = {'email' : email};
        $.ajax({
            url: 'forgot',
            type: 'POST',
            data: formData
        }).then(function (data){
            $('#message').text('Check your email');
            setTimeout(function () {
                window.location.href = 'http://localhost:8080/AssJava4_war_exploded/index';
            }, 3000);
        }).fail(function (error){
            alert('Error');
        })
    });
</script>
</body>

</html>
