package cinePlus.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del sistema CinePlus.
 * Administra películas, salas, funciones, clientes, operarios y ventas.
 * Contiene las funcionalidades requeridas del parcial.
 */
public class CinePlus {

    private String nombreComercial;
    private String nit;
    private String direccion;

    private List<Pelicula> peliculas;
    private List<Sala> salas;
    private List<Funcion> funciones;
    private List<Cliente> clientes;
    private List<Operario> operarios;
    private List<Venta> ventas;

    private int contadorBoletas = 1; // para generar códigos de boleta

    public CinePlus(String nombreComercial, String nit, String direccion) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.peliculas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.funciones = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.operarios = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    // ==================== GETTERS BÁSICOS ====================

    public String getNombreComercial() { return nombreComercial; }
    public String getNit() { return nit; }
    public String getDireccion() { return direccion; }
    public List<Pelicula> getPeliculas() { return peliculas; }
    public List<Sala> getSalas() { return salas; }
    public List<Funcion> getFunciones() { return funciones; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Operario> getOperarios() { return operarios; }
    public List<Venta> getVentas() { return ventas; }

    // ==================== 1. REGISTRAR PELÍCULAS ====================

    /**
     * Registra una película verificando que no exista otra con el mismo código.
     * @return true si se registró correctamente, false si el código ya existe.
     */
    public boolean registrarPelicula(Pelicula pelicula) {
        for (Pelicula p : peliculas) {
            if (p.getCodigoPelicula() == pelicula.getCodigoPelicula()) {
                return false; // código duplicado
            }
        }
        peliculas.add(pelicula);
        return true;
    }

    // ==================== 2. REGISTRAR CLIENTES ====================

    /**
     * Registra un cliente verificando que no exista otro con el mismo documento.
     * @return true si se registró correctamente, false si el documento ya existe.
     */
    public boolean registrarCliente(Cliente cliente) {
        for (Cliente c : clientes) {
            if (c.getDocumento() == cliente.getDocumento()) {
                return false; // documento duplicado
            }
        }
        clientes.add(cliente);
        return true;
    }

    // ==================== REGISTRAR OPERARIOS ====================

    /**
     * Registra un operario verificando que no exista otro con el mismo documento.
     */
    public boolean registrarOperario(Operario operario) {
        for (Operario o : operarios) {
            if (o.getDocumento() == operario.getDocumento()) {
                return false;
            }
        }
        operarios.add(operario);
        return true;
    }

    // ==================== REGISTRAR SALAS ====================

    public void registrarSala(Sala sala) {
        salas.add(sala);
    }

    // ==================== 3. PROGRAMAR FUNCIONES ====================

    /**
     * Programa una función verificando que no haya conflicto de horarios en la misma sala.
     * Considera la duración de la película para detectar solapamientos.
     * @return true si se programó correctamente, false si hay conflicto de horario.
     */
    public boolean programarFuncion(Funcion nuevaFuncion) {
        LocalTime inicioNueva = nuevaFuncion.getHoraInicio();
        LocalTime finNueva = nuevaFuncion.getHoraFin();
        Sala salaNueva = nuevaFuncion.getSala();
        LocalDate fechaNueva = nuevaFuncion.getFecha();

        for (Funcion f : funciones) {
            // Solo comparar funciones de la misma sala y misma fecha
            if (f.getSala().getCodigoSala() == salaNueva.getCodigoSala()
                    && f.getFecha().equals(fechaNueva)) {

                LocalTime inicioExistente = f.getHoraInicio();
                LocalTime finExistente = f.getHoraFin();

                // Hay solapamiento si:
                // inicioNueva < finExistente  &&  finNueva > inicioExistente
                if (inicioNueva.isBefore(finExistente) && finNueva.isAfter(inicioExistente)) {
                    return false; // conflicto de horario
                }
            }
        }

        funciones.add(nuevaFuncion);
        return true;
    }

    // ==================== 4. REALIZAR VENTAS DE BOLETAS ====================

    /**
     * Realiza una venta de una o varias boletas para una misma función.
     *
     * @param codigoVenta   código único de la venta
     * @param operario      operario que registra la venta
     * @param cliente       cliente que compra
     * @param funcion       función a la que asisten
     * @param asientos      lista de asientos en formato "A-5", "B-12", etc.
     * @return la Venta creada, o null si algún asiento no está disponible
     */
    public Venta realizarVenta(String codigoVenta, Operario operario, Cliente cliente,
                               Funcion funcion, List<String> asientos) {

        // 1. Verificar que todos los asientos estén disponibles
        for (String asiento : asientos) {
            String[] partes = asiento.split("-");
            char fila = partes[0].charAt(0);
            int numero = Integer.parseInt(partes[1]);

            if (!funcion.estaAsientoDisponible(fila, numero)) {
                return null; // asiento ocupado
            }
        }

        // 2. Crear la venta
        Venta venta = new Venta(codigoVenta, LocalDate.now(), operario, cliente);

        // 3. Precio base de la boleta según tipo de sala
        double precioConRecargo = funcion.calcularPrecioBoleta();

        // 4. Aplicar descuento del cliente
        double precioFinal = cliente.aplicarDescuento(precioConRecargo);

        // 5. Crear cada boleta y ocupar el asiento
        for (String asiento : asientos) {
            String[] partes = asiento.split("-");
            char fila = partes[0].charAt(0);
            int numero = Integer.parseInt(partes[1]);

            String codigoBoleta = "BOL-" + String.format("%04d", contadorBoletas++);
            Boleta boleta = new Boleta(codigoBoleta, fila, numero, precioFinal, funcion);

            // Ocupar asiento y asociar boleta a la función
            funcion.ocuparAsiento(fila, numero);
            funcion.getBoletasVendidas().add(boleta);

            venta.agregarBoleta(boleta);
        }

        ventas.add(venta);
        return venta;
    }

    // ==================== 5. CALCULAR VALOR TOTAL DE UNA VENTA ====================

    /**
     * Busca una venta por su código y retorna el valor total.
     * @return el total, o -1 si no se encuentra la venta.
     */
    public double calcularValorTotalVenta(String codigoVenta) {
        for (Venta v : ventas) {
            if (v.getCodigoVenta().equals(codigoVenta)) {
                return v.calcularValorTotal();
            }
        }
        return -1;
    }

    // ==================== 6. PORCENTAJE DE OCUPACIÓN DE UNA FUNCIÓN ====================

    /**
     * Calcula el porcentaje de ocupación de una función.
     * @return el porcentaje (0-100), o -1 si no se encuentra la función.
     */
    public double calcularPorcentajeOcupacion(String codigoFuncion) {
        for (Funcion f : funciones) {
            if (f.getCodigoFuncion().equals(codigoFuncion)) {
                return f.calcularPorcentajeOcupacion();
            }
        }
        return -1;
    }

    // ==================== MÉTODOS DE CONSULTA AUXILIARES ====================

    public Pelicula buscarPeliculaPorCodigo(int codigo) {
        for (Pelicula p : peliculas) {
            if (p.getCodigoPelicula() == codigo) return p;
        }
        return null;
    }

    public Cliente buscarClientePorDocumento(int documento) {
        for (Cliente c : clientes) {
            if (c.getDocumento() == documento) return c;
        }
        return null;
    }

    public Operario buscarOperarioPorDocumento(int documento) {
        for (Operario o : operarios) {
            if (o.getDocumento() == documento) return o;
        }
        return null;
    }

    public Funcion buscarFuncionPorCodigo(String codigo) {
        for (Funcion f : funciones) {
            if (f.getCodigoFuncion().equals(codigo)) return f;
        }
        return null;
    }

    public Sala buscarSalaPorCodigo(int codigo) {
        for (Sala s : salas) {
            if (s.getCodigoSala() == codigo) return s;
        }
        return null;
    }

    public Venta buscarVentaPorCodigo(String codigo) {
        for (Venta v : ventas) {
            if (v.getCodigoVenta().equals(codigo)) return v;
        }
        return null;
    }

    @Override
    public String toString() {
        return "CinePlus{" +
                "nombre='" + nombreComercial + '\'' +
                ", nit='" + nit + '\'' +
                ", direccion='" + direccion + '\'' +
                ", peliculas=" + peliculas.size() +
                ", salas=" + salas.size() +
                ", funciones=" + funciones.size() +
                ", clientes=" + clientes.size() +
                ", operarios=" + operarios.size() +
                ", ventas=" + ventas.size() +
                '}';
    }
}
