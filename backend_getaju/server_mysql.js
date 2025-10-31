// ------------------------------
// SERVIDOR BACKEND - GETAJU
// Autor: Alexander Vega
// Base de datos: MySQL (XAMPP)
// Tabla: tareas (en español)
// ------------------------------

const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const mysql = require("mysql2/promise");

const app = express();
const PORT = 3001;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// ✅ CONFIGURAR CONEXIÓN CON XAMPP (MySQL)
const pool = mysql.createPool({
  host: "localhost",     // XAMPP usa localhost
  user: "root",          // Usuario por defecto
  password: "",          // Vacío en XAMPP (sin contraseña)
  database: "getaju"     // Nombre de tu base de datos
});

// ✅ PRUEBA DE CONEXIÓN
(async () => {
  try {
    const conn = await pool.getConnection();
    console.log("✅ Conectado correctamente a la base de datos MySQL (XAMPP)");
    conn.release();
  } catch (error) {
    console.error("❌ Error al conectar con la base de datos:", error);
  }
})();

// ------------------------------
// RUTAS PRINCIPALES
// ------------------------------

// 🟢 Obtener todas las tareas
app.get("/api/tareas", async (req, res) => {
  try {
    const [rows] = await pool.query("SELECT * FROM tareas ORDER BY creado_en DESC");
    res.json(rows);
  } catch (error) {
    console.error("Error al obtener tareas:", error);
    res.status(500).json({ error: "Error al obtener las tareas" });
  }
});

// 🟢 Agregar nueva tarea
app.post("/api/tareas", async (req, res) => {
  try {
    const { nombre, prioridad, fecha_limite, estado, importancia, recordatorio } = req.body;
    const [result] = await pool.query(
      "INSERT INTO tareas (nombre, prioridad, fecha_limite, estado, importancia, recordatorio) VALUES (?, ?, ?, ?, ?, ?)",
      [nombre, prioridad, fecha_limite, estado, importancia, recordatorio]
    );
    res.json({ id_tarea: result.insertId, mensaje: "Tarea agregada correctamente" });
  } catch (error) {
    console.error("Error al agregar tarea:", error);
    res.status(500).json({ error: "Error al agregar la tarea" });
  }
});

// 🟡 Actualizar tarea existente
app.put("/api/tareas/:id_tarea", async (req, res) => {
  try {
    const { id_tarea } = req.params;
    const { nombre, prioridad, fecha_limite, estado, importancia, recordatorio } = req.body;
    await pool.query(
      "UPDATE tareas SET nombre=?, prioridad=?, fecha_limite=?, estado=?, importancia=?, recordatorio=? WHERE id_tarea=?",
      [nombre, prioridad, fecha_limite, estado, importancia, recordatorio, id_tarea]
    );
    res.json({ mensaje: "Tarea actualizada correctamente" });
  } catch (error) {
    console.error("Error al actualizar tarea:", error);
    res.status(500).json({ error: "Error al actualizar la tarea" });
  }
});

// 🔴 Eliminar tarea
app.delete("/api/tareas/:id_tarea", async (req, res) => {
  try {
    const { id_tarea } = req.params;
    await pool.query("DELETE FROM tareas WHERE id_tarea = ?", [id_tarea]);
    res.json({ mensaje: "Tarea eliminada correctamente" });
  } catch (error) {
    console.error("Error al eliminar tarea:", error);
    res.status(500).json({ error: "Error al eliminar la tarea" });
  }
});

// ------------------------------
// INICIAR SERVIDOR
// ------------------------------
app.listen(PORT, () => {
  console.log(`🚀 Servidor backend corriendo en: http://localhost:${PORT}`);
});
