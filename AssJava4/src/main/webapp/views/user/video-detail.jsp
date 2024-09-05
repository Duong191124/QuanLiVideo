<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>${video.title}</title>
    <%@include file="/common/head.jsp" %>
</head>

<body>
<%@include file="/common/header.jsp" %>

<div class="container-fluid tm-container-content tm-mt-60">
    <div class="row mb-4">
        <h2 class="col-12 tm-text-primary">${video.title}</h2>
    </div>
    <div class="row tm-mb-90">
        <div class="col-xl-8 col-lg-7 col-md-6 col-sm-12">
            <iframe id="tm-video" src="https://www.youtube.com/embed/${video.href}" frameborder="0"></iframe>
        </div>
        <div class="col-xl-4 col-lg-5 col-md-6 col-sm-12" style="min-height: 500px!important">
            <div class="tm-bg-gray tm-video-details">
                <c:if test="${not empty sessionScope.currentUser}">
                    <div class="text-center mb-5">
                        <button id="LikeOrUlike" class="btn btn-primary tm-btn-big w-50">
                            <c:choose>
                                <c:when test="${Like == true}">
                                    Unlike
                                </c:when>
                                <c:otherwise>Like</c:otherwise>
                            </c:choose>
                        </button>
                    </div>
                    <div class="text-center mb-5">
                        <a href="#" class="btn btn-primary tm-btn-big w-50">Share</a>
                    </div>
                    <input id="VideoId" type="hidden" value="${video.href}" >
                </c:if>
            </div>
        </div>
    </div>
</div> <!-- container-fluid, tm-container-content -->

<%@include file="/common/footer.jsp" %>
<script>
    $('#LikeOrUlike').click(function (){
        var videoid = $('#VideoId').val();
        $.ajax({
            url: 'video?action=like&id=' + videoid
        }).then(function (data) {
            var text = $('#LikeOrUlike').text();
            if(text.indexOf('Like') != -1){
                $('#LikeOrUlike').text('Unlike');
            }else{
                $('#LikeOrUlike').text('Like');
            }
        }).fail(function (error) {
            alert("Lỗi");
        });
    });
</script>
</body>

</html>
