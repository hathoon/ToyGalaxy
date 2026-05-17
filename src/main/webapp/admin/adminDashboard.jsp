<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="./partials/adminHeader.jsp"%>

    <!-- Main Content -->
    <main class="flex-1 p-6">
        <header class="flex justify-between items-center mb-6">
            <h1 class="text-3xl font-bold">Welcome, <%= adminName %> !</h1>
            <button class="md:hidden bg-gray-700 p-2 rounded text-sm">☰</button>
        </header>

        <section class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div class="bg-gray-700 p-6 rounded-xl shadow hover:shadow-xl transition">
                <h3 class="text-lg font-semibold mb-2 text-gray-300">Total Users</h3>
                <p class="text-3xl font-bold">${totalUsers}</p>
                <p class="text-xs text-gray-400 mt-2">Registered user accounts</p>
            </div>
            <div class="bg-gray-700 p-6 rounded-xl shadow hover:shadow-xl transition">
                <h3 class="text-lg font-semibold mb-2 text-gray-300">Inactive Products</h3>
                <p class="text-3xl font-bold text-amber-400">${pendingProducts}</p>
                <p class="text-xs text-gray-400 mt-2">Products currently inactive</p>
            </div>
            <div class="bg-gray-700 p-6 rounded-xl shadow hover:shadow-xl transition">
                <h3 class="text-lg font-semibold mb-2 text-gray-300">Total Revenue</h3>
                <p class="text-3xl font-bold text-emerald-400">RS${monthlySales}</p>
                <p class="text-xs text-gray-400 mt-2">From all paid orders</p>
            </div>
        </section>

<%@ include file="./partials/adminFooter.jsp"%>
