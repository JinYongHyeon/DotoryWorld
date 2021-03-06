<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- test style -->
<style>
table {
  width: 100%;
  border-color:#FFBB00;
}

td {
  padding: 6px;
  text-align: center;
}
th {
padding: 5px;
  background-color: #FFBB00;
  color: white;
    text-align: center;
}

</style>

<div class="row" id="boardTableLine">
	<div class="col-sm-8 offset-2" id="toryProfileForm">
		<h2><img src="resources/img/profile/profile.png" width="500px" height="70px"></h2>
				<form id="boardTable">
		<table bgcolor="white">
		
			<tr>
				<th colspan="2">프로필</th>
			</tr>

			<tr>
				<th>프로필 사진</th>
				<td><div class="toryProfileImg">
						<c:choose>
							<c:when test="${sessionScope.mvo.profilePhoto == null}">
								<img
									src="${pageContext.request.contextPath}/resources/img/profile/profileDefualt.png">
							</c:when>
							<c:otherwise>
								<img src="${pageContext.request.contextPath}/resources/img/profile/${sessionScope.mvo.profilePhoto}">
							</c:otherwise>
						</c:choose>

					</div></td>
			</tr>
			<tr>
				<th>닉네임</th>
				<td>
					<div class="toryProfileNick">
						<span>${sessionScope.mvo.nickname}</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>소개글</th>
				<td><div class="toryProfileContent">
						<span>${sessionScope.mvo.profileContent}</span>
					</div></td>
			</tr>
		</table>

			
		<br><br>
		<input type="button" onclick="location.href='${pageContext.request.contextPath}/front?command=toryProfileUpdateForm'" value="프로필 수정">
		<input type="button" onclick="location.href='${pageContext.request.contextPath }/front?command=myDotoryList&id=${sessionScope.mvo.id}'" value="내 도토리 리스트">
		</form>
	</div>

</div>

