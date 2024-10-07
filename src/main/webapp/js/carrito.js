var carrito = null; // Array que contendrá los objetos.

function cargaCarrito() {
  var carritoJSON = localStorage.getItem("carrito");

  if (carritoJSON !== null) {
    carrito = JSON.parse(carritoJSON);
  } else {
    carrito = []; // Initialize carrito as an empty array if it doesn't exist in local storage
  }
}

function actualizarCarrito() {
    localStorage.setItem("carrito", JSON.stringify(carrito));
}

function AnadirCarrito(codigo, descripcion, titulo, precio, existencias, imagen) {

  var encontrado = false;

  for (var i = 0; i < carrito.length; i++) {
    if (carrito[i].codigo === parseInt(codigo)) {
      // Existing product found in the cart
      carrito[i].existencias = parseInt(existencias);
      encontrado = true;
      if (carrito[i].cantidad < parseInt(existencias)) {
        carrito[i].cantidad++;
      }
      break;
    }
  }

  if (!encontrado) {
    // New product to be added to the cart
    var producto = {
      codigo: parseInt(codigo),
      descripcion: descripcion,
      cantidad: 1,
      titulo: titulo,
      precio: parseFloat(precio),
      existencias: parseInt(existencias),
      imagen: imagen
    };
    carrito.push(producto);
  }

  alert("Producto añadido al carrito");
  actualizarCarrito(); // Store the updated cart items in local storage
}

 // Function to display cart items
function mostrarproductos() {
	var cartContainer = document.getElementById("cart-items");
    cartContainer.innerHTML = "";

  	for (var i = 0; i < carrito.length; i++)
  	{
     	var productHTML = `
                    <div class="cart-item">
                        <img src="${carrito[i].imagen}" alt="${carrito[i].titulo}">
                        <div class="cart-item-info">
                            <h2>${carrito[i].titulo}</h2>
                            <p>${carrito[i].descripcion}</p>
                            <p>Price: ${carrito[i].precio}</p>
                            <p>Quantity: ${carrito[i].cantidad}</p>
                            <button onclick="updateQuantity(${carrito[i].codigo})">Modify Quantity</button>
                            <button onclick="quitardeCarrito(${carrito[i].codigo})">Remove from Cart</button>
                        </div>
                    </div>
                `;
                
         cartContainer.innerHTML += productHTML;
	}
}

function preciototal()
{
	var total = 0;
	for (var i = 0; i < carrito.length; i++)
  	{
		total += carrito[i].precio * carrito[i].cantidad;	  
	}
	
	document.getElementById("total-price").innerHTML="Total Price: " + total;
}
        
     function updateQuantity(codigo) {
  cargaCarrito(); 

  var quantity = prompt("Introduce cantidad");

  if (quantity !== null) {
    quantity = parseInt(quantity);
    if (isNaN(quantity) || quantity < 1) {
      alert("introduce un numero positivo");
    } else {
      var index = carrito.findIndex(function(item) {
        return item.codigo === codigo;
      });

      if (index !== -1) {
        var existencias = carrito[index].existencias; // Obtener las existencias disponibles del producto
        if (quantity <= existencias) { // Verificar si la cantidad ingresada es menor o igual a las existencias
          carrito[index].cantidad = quantity;
          mostrarproductos(carrito);
          preciototal(carrito);
          actualizarCarrito();
          Cargar('./html/tienda2.html','cuerpo');
        } else {
          alert("No tenemos tabto stock, reduce el numero");
        }
      }
    }
  }
}

function quitardeCarrito(codigo) {
	for (var i = 0; i < carrito.length; i++)
	{
		if (carrito[i].codigo === parseInt(codigo)) {
			carrito.splice(i,1);
		}
	}

	actualizarCarrito();
	mostrarproductos();
	preciototal();
	mostrarBoton();
}

function mostrarBoton()
{
	if (carrito.length>0) {
		document.getElementById("boton_compra").innerHTML="<button id=\"comprar\" onclick=\"EnviarCarrito('RecogerCarrito','cuerpo',carrito)\"> Comprar </button>";
	}
	else {
		document.getElementById("boton_compra").innerHTML="&nbsp";
	}
}

function borrarTodo()
{
	carrito=[];
	actualizarCarrito();
}

 

      







