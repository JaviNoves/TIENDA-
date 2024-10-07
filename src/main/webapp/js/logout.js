// Obtener una referencia al botón de "Cerrar sesión"
var logoutButton = document.getElementById('logoutButton');

// Agregar un controlador de eventos click al botón
logoutButton.addEventListener('click', function() {
  // Realizar una petición al servidor para destruir las variables de sesión
  // Esto dependerá de cómo estés manejando las sesiones en tu aplicación, por ejemplo, puedes utilizar AJAX o fetch para realizar una solicitud al servidor.
  
  // Redireccionar al usuario a la página de inicio de sesión
  window.location.href = './html/loginUsuario.jsp';
});
