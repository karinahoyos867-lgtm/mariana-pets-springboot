<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resultado - Mariana Pets</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #fdf8f3;
            color: #6D4C41;
            margin: 0;
            padding: 30px 15px;
        }
        main {
            max-width: 500px;
            margin: 40px auto;
            padding: 30px;
            background: white;
            border-radius: 8px;
            text-align: center;
            box-shadow: 0 2px 10px #00000015;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 18px;
            background: #8B5E3C;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }
        a:hover { background: #6D4C41; }
    </style>
</head>
<body>
    <main>
        <h1>Mariana Pets</h1>
        <h2>Resultado del registro</h2>

        <p>${mensaje}</p>

       <a href="registrar-mascota">
            Registrar otra mascota
        </a>
    </main>
</body>
</html>
