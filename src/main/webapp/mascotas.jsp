
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mascotas registradas - Mariana Pets</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #fdf8f3;
            color: #6D4C41;
            margin: 0;
            padding: 30px 15px;
        }

        main {
            max-width: 1100px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 8px;
        }

        h1 {
            text-align: center;
            color: #8B5E3C;
        }

        .tabla-contenedor {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #e4d5c8;
            text-align: left;
        }

        th {
            background: #f3e5d8;
        }

        .enlace {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 16px;
            background: #8B5E3C;
            color: white;
            text-decoration: none;
            border-radius: 8px;
        }
    </style>
</head>
<body>
<main>
    <h1>Mascotas registradas</h1>

    <c:choose>
        <c:when test="${empty mascotas}">
            <p>No hay mascotas registradas todavía.</p>
        </c:when>
        <c:otherwise>
            <div class="tabla-contenedor">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Especie</th>
                            <th>Raza</th>
                            <th>Edad</th>
                            <th>Propietario</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="mascota" items="${mascotas}">
                            <tr>
                                <td><c:out value="${mascota.id}"/></td>
                                <td><c:out value="${mascota.nombre}"/></td>
                                <td><c:out value="${mascota.especie}"/></td>
                                <td><c:out value="${mascota.raza}"/></td>
                                <td><c:out value="${mascota.edad}"/></td>
                                <td><c:out value="${mascota.propietario}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>

    <a class="enlace" href="registrar-mascota">Registrar otra mascota</a>
</main>
</body>
</html>
