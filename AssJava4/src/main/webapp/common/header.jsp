
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="loader-wrapper">
    <div id="loader"></div>

    <div class="loader-section section-left"></div>
    <div class="loader-section section-right"></div>

</div>
<nav class="navbar navbar-expand-lg">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value='/index'/>">
            <i class="fa-youtube"></i>
            Online Entertainment
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
                <c:choose>
                    <c:when test="${not empty sessionScope.currentUser}">
                        <li class="nav-item">
                            <a data-toggle="modal" data-target="#changePassModal" class="nav-link nav-link-1 active" aria-current="page">Welcome, ${sessionScope.currentUser.username}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-link-1" href="favorite">My favorites</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-link-2" href="history">History</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-link-3" href="logout">Log out</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link nav-link-2" href="login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-link-3" href="register">Register</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-link-4" href="forgot">Forgot Password</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<div class="tm-hero d-flex justify-content-center align-items-center" data-parallax="scroll" data-image-src="<c:url value='/templates/user/img/hero.jpg'/>">
</div>

<!-- The Modal -->
<div class="modal" id="changePassModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Change Password</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <div class="form-group">
                    <input type="password" id="currentPass" name="current_password" class="form-control rounded-0" placeholder="CurrentPassword" required />
                </div>
                <div class="form-group">
                    <input type="password" id="newPass" name="new_password" class="form-control rounded-0" placeholder="NewPassword" required />
                </div>
                <h5 style="color: red" id="messageChangePass"></h5>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="changePassbtn">Save</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>

        </div>
    </div>
</div>
