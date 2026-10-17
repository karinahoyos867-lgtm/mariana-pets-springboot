 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mariana Pets | Inicio</title>

    <style>
        :root {
            --color-fondo: #fdf8f3;
            --color-secundario: #f3e5d8;
            --color-principal: #8B5E3C;
            --color-titulo: #6D4C41;
            --color-blanco: #ffffff;
            --radio: 8px;
        }

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: var(--color-fondo);
            color: #44352d;
        }

        header {
            background-color: var(--color-secundario);
            padding: 24px 20px;
            text-align: center;
        }

        h1 {
            margin: 0;
            color: var(--color-titulo);
        }

        main {
            max-width: 850px;
            margin: 45px auto;
            padding: 20px;
            text-align: center;
        }

        .bienvenida {
            background-color: var(--color-blanco);
            padding: 35px 25px;
            border: 1px solid #e5d5c7;
            border-radius: var(--radio);
        }

        h2 {
            color: var(--color-titulo);
        }

        .botones {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 15px;
            margin-top: 28px;
        }

        .boton {
            display: inline-block;
            padding: 12px 22px;
            background-color: var(--color-principal);
            color: var(--color-blanco);
            text-decoration: none;
            border-radius: var(--radio);
            font-weight: bold;
        }

        .boton:hover {
            background-color: var(--color-titulo);
        }

        footer {
            margin-top: 35px;
            padding: 20px;
            text-align: center;
            color: var(--color-titulo);
        }

        @media (max-width: 600px) {
            main {
                margin: 20px auto;
            }

            .bienvenida {
                padding: 25px 15px;
            }

            .botones {
                flex-direction: column;
            }

            .boton {
                width: 100%;
            }
        }
    </style>
</head>

<body>
    <header>
        <h1>Mariana Pets</h1>
        <p>El bienestar de tus mascotas es nuestra prioridad.</p>
    </header>

    <main>
        <section class="bienvenida">
            <h2>¡Bienvenidos a Mariana Pets!</h2>

            <p>
                Tu lugar de confianza para el cuidado de tus mascotas.
            </p>

            <p>
                Registra tus mascotas y consulta las que ya están registradas.
            </p>

            <div class="botones">
                <a class="boton"
                   href="${pageContext.request.contextPath}/registrar-mascota">
                    Registrar mascota
                </a>

                <a class="boton"
                   href="${pageContext.request.contextPath}/listar-mascotas">
                    Consultar mascotas
                </a>
            </div>
        </section>
    </main>

    <footer>
        <p>Mariana Pets - Cuidado y bienestar animal</p>
    </footer>
</body>
</html>
