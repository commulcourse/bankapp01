<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@include file="../layout/header.jsp" %>

        <h1>계좌상세보기</h1>
        <hr />
        <div class="user-box">
            ${aDto.fullname}님 계좌<br />
            계좌번호 : ${aDto.number}<br />
            잔액 : ${aDto.balance}원
        </div>
        <div class="list-box">
            <a href="/account/${aDto.id}?gubun=all">전체</a>
            <a href="/account/${aDto.id}?gubun=deposit">입금</a>
            <a href="/account/${aDto.id}?gubun=withdraw">출금</a>
            <br />
            <table border="1">
                <thead>
                    <c:forEach items="${hDtoList}" var="history">
                        <tr>
                            <td>${history.createdAt}</td>
                            <td>${history.sender}</td>
                            <td>${history.receiver}</td>
                            <td>${history.amount}원</td>
                            <td>${history.balance}원</td>
                        </tr>
                    </c:forEach>
                </thead>
                <tbody>
                    <tr>
                        <td>2022.10.01</td>
                        <td>ATM</td>
                        <td>1111계좌</td>
                        <td>500원</td>
                        <td>1500원</td>
                    </tr>
                </tbody>
            </table>
        </div>

        </body>

        </html>