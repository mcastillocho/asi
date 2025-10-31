// URL base del backend
const API_URL = "http://localhost:3001/api/tareas";
let tareasGlobal = []; // Almacenará todas las tareas para filtrar en memoria

// Obtener y mostrar todas las tareas
async function cargarTareas() {
  const res = await fetch(API_URL);
  tareasGlobal = await res.json();
  mostrarTareas(tareasGlobal);
}

// Mostrar las tareas en la lista
function mostrarTareas(tareas) {
  const lista = document.getElementById("listaTareas");
  lista.innerHTML = "";

  if (tareas.length === 0) {
    lista.innerHTML = "<p style='text-align:center;color:#888;'>No hay tareas para mostrar</p>";
    return;
  }

  tareas.forEach(t => {
    const li = document.createElement("li");
    li.classList.add(`priority-${t.prioridad}`);

    li.innerHTML = `
      <div>
        <strong>${t.nombre}</strong><br>
        <small>Prioridad: ${t.prioridad} | Importancia: ${t.importancia}</small><br>
        <span class="fecha">Fecha límite: ${t.fecha_limite ? t.fecha_limite.substring(0, 10) : "No definida"}</span><br>
        <span class="fecha">Creado: ${t.creado_en ? t.creado_en.substring(0, 10) : "-"}</span>
      </div>
      <button class="boton-eliminar" onclick="eliminarTarea(${t.id_tarea})">🗑 Eliminar</button>
    `;
    lista.appendChild(li);
  });
}

// Agregar una nueva tarea
async function agregarTarea() {
  const nombre = document.getElementById("nombre").value.trim();
  const prioridad = document.getElementById("prioridad").value;
  const fecha_limite = document.getElementById("fecha_limite").value;
  const importancia = document.getElementById("importancia").value;

  if (!nombre || !fecha_limite) {
    alert("Por favor completa el nombre y la fecha límite.");
    return;
  }

  await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      nombre,
      prioridad,
      fecha_limite,
      estado: "Pendiente",
      importancia,
      recordatorio: null
    })
  });

  document.getElementById("nombre").value = "";
  document.getElementById("fecha_limite").value = "";
  cargarTareas();
}

// Eliminar tarea
async function eliminarTarea(id) {
  if (!confirm("¿Deseas eliminar esta tarea?")) return;
  await fetch(`${API_URL}/${id}`, { method: "DELETE" });
  cargarTareas();
}

// Filtrar tareas por nombre o prioridad
function filtrarTareas() {
  const texto = document.getElementById("buscarNombre").value.toLowerCase();
  const prioridadSeleccionada = document.getElementById("filtrarPrioridad").value;

  const filtradas = tareasGlobal.filter(t => {
    const coincideNombre = t.nombre.toLowerCase().includes(texto);
    const coincidePrioridad = prioridadSeleccionada ? t.prioridad === prioridadSeleccionada : true;
    return coincideNombre && coincidePrioridad;
  });

  mostrarTareas(filtradas);
}

// Eventos
document.getElementById("agregarBtn").addEventListener("click", agregarTarea);
document.getElementById("buscarNombre").addEventListener("input", filtrarTareas);
document.getElementById("filtrarPrioridad").addEventListener("change", filtrarTareas);

// Cargar tareas al inicio
cargarTareas();
