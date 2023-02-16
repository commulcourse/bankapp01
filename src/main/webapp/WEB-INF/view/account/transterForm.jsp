<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@include file="../layout/header.jsp" %>

        <h1>ATM 이체</h1>
        <hr />
        <form action="/account/transter" method="post">
            <input type="text" name="amount" placeholder="Enter 이체금액" /><br />
            <input type="text" name="wAccountNumber" placeholder="Enter 출급 계좌번호" /><br />
            <input type="text" name="dAccountNumber" placeholder="Enter 입금 계좌번호" /><br />
            <input type="text" name="aAccountPassword" placeholder="Enter 입금 계좌 비밀번호" /><br />
            <button>입금</button>
        </form>
        </body>

        </html>