<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar mascota - Mariana Pets</title>
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
            margin: auto;
            padding: 25px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px #00000015;
        }
        h1 { text-align: center; }
        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }
        input, select, button {
            box-sizing: border-box;
            width: 100%;
            padding: 11px;
            margin-top: 6px;
            border: 1px solid #d8c5b5;
            border-radius: 6px;
            font-size: 16px;
        }
        button {
            margin-top: 22px;
            background: #8B5E3C;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover { background: #6D4C41; }
    </style>
</head>
<body>
    <main>
        <h1>Mariana Pets</h1>
        <h2>Registrar mascota</h2>

        <form action="registrar-mascota"
              method="post">

            <label for="nombre">Nombre de la mascota</label>
            <input type="text" id="nombre" name="nombre" required>

            <label for="especie">Especie</label>
            <select id="especie" name="especie" required>
                <option value="">Selecciona una especie</option>
                <option value="Perro">Perro</option>
                <option value="Gato">Gato</option>
                <option value="Otra">Otra</option>
            </select>

            <label for="raza">Raza</label>
            <input type="text" id="raza" name="raza" required>

            <label for="edad">Edad</label>
            <input type="number" id="edad" name="edad" min="0" step="1" required>

            <label for="propietario">Nombre del propietario</label>
            <input type="text" id="propietario" name="propietario" required>

            <button type="submit">Registrar mascota</button>
        </form>
    </main>
</body>
</html>