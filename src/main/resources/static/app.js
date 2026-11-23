const filtroEstado = document.getElementById('estado');
const tabla = document.getElementById('incidencias');
const mensaje = document.getElementById('mensaje');

async function cargarIncidencias() {
    tabla.replaceChildren();
    mensaje.textContent = 'Cargando incidencias...';
    mensaje.className = '';
    filtroEstado.disabled = true;

    const estado = filtroEstado.value;
    const url = estado
        ? '/api/incidencias?status=' + encodeURIComponent(estado)
        : '/api/incidencias';

    try {
        const respuesta = await fetch(url);
        // fetch no lanza un error automaticamente ante respuestas 404 o 500.
        if (!respuesta.ok) {
            throw new Error('Error HTTP ' + respuesta.status);
        }

        const incidencias = await respuesta.json();
        for (const incidencia of incidencias) {
            const fila = document.createElement('tr');
            const valores = [
                incidencia.id,
                incidencia.title,
                incidencia.description,
                incidencia.priority,
                incidencia.status,
                incidencia.createdAt,
                incidencia.assignedUserId ?? 'Sin asignar'
            ];

            for (const valor of valores) {
                const celda = document.createElement('td');
                // Mostrar los datos como texto, sin interpretarlos como HTML.
                celda.textContent = valor ?? '';
                fila.appendChild(celda);
            }
            tabla.appendChild(fila);
        }

        mensaje.textContent = incidencias.length === 0
            ? 'No hay incidencias para este filtro.'
            : '';
    } catch (error) {
        tabla.replaceChildren();
        mensaje.textContent = 'No se pudieron cargar las incidencias. Comprueba que el servidor esté activo y vuelve a seleccionar el filtro para reintentar.';
        mensaje.className = 'error';
    } finally {
        filtroEstado.disabled = false;
    }
}

// Actualizar la tabla al cambiar el filtro, sin recargar la pagina.
filtroEstado.addEventListener('change', cargarIncidencias);
cargarIncidencias();
