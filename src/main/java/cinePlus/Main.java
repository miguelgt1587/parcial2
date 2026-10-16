package cinePlus;

import cinePlus.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz gráfica del sistema CinePlus.
 * Menú en ventana (Swing) para probar todas las funcionalidades.
 */
public class Main extends JFrame {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private final CinePlus cine;
    private int contadorFuncion = 1;
    private int contadorVenta = 1;

    private final JTextArea areaResultados;

    public Main() {
        cine = new CinePlus("CinePlus Armenia", "900123456-1", "Calle 19 #12-34, Armenia, Quindío");
        cargarDatosDeEjemplo();

        setTitle("CinePlus - Sistema de Gestión Cinematográfica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior - título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(30, 30, 60));
        panelTitulo.setBorder(new EmptyBorder(15, 15, 15, 15));
        JLabel lblTitulo = new JLabel("SISTEMA CINEPLUS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel central - botones del menú
        JPanel panelMenu = new JPanel(new GridLayout(0, 2, 12, 12));
        panelMenu.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelMenu.setBackground(new Color(245, 245, 250));

        String[] textos = {
                "1. Registrar película",
                "2. Registrar cliente",
                "3. Registrar operario",
                "4. Registrar sala",
                "5. Programar función",
                "6. Realizar venta de boletas",
                "7. Valor total de una venta",
                "8. % Ocupación de función",
                "9. Listar películas",
                "10. Listar funciones",
                "11. Listar clientes",
                "12. Listar ventas",
                "13. Listar salas",
                "14. Listar operarios"
        };

        Runnable[] acciones = {
                this::registrarPelicula,
                this::registrarCliente,
                this::registrarOperario,
                this::registrarSala,
                this::programarFuncion,
                this::realizarVenta,
                this::consultarValorTotalVenta,
                this::consultarPorcentajeOcupacion,
                this::listarPeliculas,
                this::listarFunciones,
                this::listarClientes,
                this::listarVentas,
                this::listarSalas,
                this::listarOperarios
        };

        for (int i = 0; i < textos.length; i++) {
            JButton btn = crearBoton(textos[i], acciones[i]);
            panelMenu.add(btn);
        }

        add(panelMenu, BorderLayout.CENTER);

        // Panel inferior - área de resultados
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBorder(new EmptyBorder(5, 15, 15, 15));
        JLabel lblResultados = new JLabel("Resultados / Mensajes:");
        lblResultados.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelSur.add(lblResultados, BorderLayout.NORTH);

        areaResultados = new JTextArea(8, 50);
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Consolas", Font.PLAIN, 13));
        areaResultados.setBackground(new Color(30, 30, 40));
        areaResultados.setForeground(new Color(0, 255, 128));
        areaResultados.setText("Datos de ejemplo cargados.\nSeleccione una opción del menú.\n");
        JScrollPane scroll = new JScrollPane(areaResultados);
        panelSur.add(scroll, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Runnable accion) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setBackground(new Color(40, 70, 140));
        btn.setForeground(Color.BLACK);  // texto negro
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> accion.run());
        return btn;
    }

    private void mostrar(String mensaje) {
        areaResultados.append(mensaje + "\n");
        areaResultados.setCaretPosition(areaResultados.getDocument().getLength());
    }

    private void limpiarResultados() {
        areaResultados.setText("");
    }

    // ==================== 1. REGISTRAR PELÍCULA ====================

    private void registrarPelicula() {
        JTextField txtCodigo = new JTextField();
        JTextField txtTitulo = new JTextField();
        JTextField txtDuracion = new JTextField();
        JComboBox<TipoGenero> cmbGenero = new JComboBox<>(TipoGenero.values());
        JTextField txtClasificacion = new JTextField();

        Object[] campos = {
                "Código:", txtCodigo,
                "Título:", txtTitulo,
                "Duración (minutos):", txtDuracion,
                "Género:", cmbGenero,
                "Clasificación (G, PG, PG-13, R):", txtClasificacion
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Registrar Película",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String titulo = txtTitulo.getText().trim();
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            TipoGenero genero = (TipoGenero) cmbGenero.getSelectedItem();
            String clasificacion = txtClasificacion.getText().trim();

            if (titulo.isEmpty()) {
                mostrar("El título no puede estar vacío.");
                return;
            }

            Pelicula p = new Pelicula(codigo, titulo, duracion, genero, clasificacion);
            if (cine.registrarPelicula(p)) {
                mostrar("Película registrada: [" + codigo + "] " + titulo);
            } else {
                mostrar("Ya existe una película con el código " + codigo);
            }
        } catch (NumberFormatException e) {
            mostrar("Código y duración deben ser números enteros.");
        }
    }

    // ==================== 2. REGISTRAR CLIENTE ====================

    private void registrarCliente() {
        JTextField txtDoc = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtAfiliacion = new JTextField();
        JTextField txtDescuento = new JTextField("0.10");

        Object[] campos = {
                "Documento:", txtDoc,
                "Nombre completo:", txtNombre,
                "Correo:", txtCorreo,
                "Teléfono:", txtTelefono,
                "Código de afiliación:", txtAfiliacion,
                "Descuento (ej: 0.10 = 10%):", txtDescuento
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Registrar Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            int doc = Integer.parseInt(txtDoc.getText().trim());
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            int telefono = Integer.parseInt(txtTelefono.getText().trim());
            String afiliacion = txtAfiliacion.getText().trim();
            double descuento = Double.parseDouble(txtDescuento.getText().trim());

            Cliente c = new Cliente(doc, nombre, correo, telefono, afiliacion, descuento);
            if (cine.registrarCliente(c)) {
                mostrar("Cliente registrado: " + nombre + " (descuento: " + (descuento * 100) + "%)");
            } else {
                mostrar("Ya existe un cliente con el documento " + doc);
            }
        } catch (NumberFormatException e) {
            mostrar("Documento, teléfono y descuento deben ser numéricos.");
        }
    }

    // ==================== 3. REGISTRAR OPERARIO ====================

    private void registrarOperario() {
        JTextField txtDoc = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCodigoEmp = new JTextField();
        JTextField txtFecha = new JTextField("15/03/2024");
        JTextField txtSalario = new JTextField();

        Object[] campos = {
                "Documento:", txtDoc,
                "Nombre completo:", txtNombre,
                "Correo:", txtCorreo,
                "Teléfono:", txtTelefono,
                "Código empleado:", txtCodigoEmp,
                "Fecha contratación (dd/MM/yyyy):", txtFecha,
                "Salario básico:", txtSalario
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Registrar Operario",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            int doc = Integer.parseInt(txtDoc.getText().trim());
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            int telefono = Integer.parseInt(txtTelefono.getText().trim());
            String codigoEmp = txtCodigoEmp.getText().trim();
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), FORMATO_FECHA);
            double salario = Double.parseDouble(txtSalario.getText().trim());

            Operario o = new Operario(doc, nombre, correo, telefono, codigoEmp, fecha, salario);
            if (cine.registrarOperario(o)) {
                mostrar("Operario registrado: " + nombre + " [" + codigoEmp + "]");
            } else {
                mostrar("Ya existe un operario con el documento " + doc);
            }
        } catch (Exception e) {
            mostrar("Error en los datos. Revise formato de fecha (dd/MM/yyyy) y números.");
        }
    }

    // ==================== 4. REGISTRAR SALA ====================

    private void registrarSala() {
        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCapacidad = new JTextField();
        JTextField txtFila = new JTextField("A");
        JTextField txtColumnas = new JTextField();
        String[] tipos = {"Tradicional (sin recargo)", "VIP (+30%)", "3D (+20%)"};
        JComboBox<String> cmbTipo = new JComboBox<>(tipos);

        Object[] campos = {
                "Código de sala:", txtCodigo,
                "Nombre:", txtNombre,
                "Capacidad máxima:", txtCapacidad,
                "Fila inicial (letra):", txtFila,
                "Columnas (asientos por fila):", txtColumnas,
                "Tipo de sala:", cmbTipo
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Registrar Sala",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String nombre = txtNombre.getText().trim();
            int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
            char fila = txtFila.getText().trim().toUpperCase().charAt(0);
            int columnas = Integer.parseInt(txtColumnas.getText().trim());
            int tipo = cmbTipo.getSelectedIndex();

            if (cine.buscarSalaPorCodigo(codigo) != null) {
                mostrar("Ya existe una sala con el código " + codigo);
                return;
            }

            Sala sala;
            if (tipo == 1) {
                sala = new VIP(codigo, nombre, capacidad, fila, columnas);
            } else if (tipo == 2) {
                sala = new Sala3D(codigo, nombre, capacidad, fila, columnas);
            } else {
                sala = new Tradicional(codigo, nombre, capacidad, fila, columnas);
            }

            cine.registrarSala(sala);
            mostrar("Sala registrada: " + nombre + " (" + sala.getClass().getSimpleName() + ")");
        } catch (Exception e) {
            mostrar("Error en los datos. Verifique números y fila.");
        }
    }

    // ==================== 5. PROGRAMAR FUNCIÓN ====================

    private void programarFuncion() {
        if (cine.getPeliculas().isEmpty() || cine.getSalas().isEmpty()) {
            mostrar("Debe haber películas y salas registradas.");
            return;
        }

        String[] peliculasStr = cine.getPeliculas().stream()
                .map(p -> p.getCodigoPelicula() + " - " + p.getTitulo())
                .toArray(String[]::new);
        JComboBox<String> cmbPelicula = new JComboBox<>(peliculasStr);

        String[] salasStr = cine.getSalas().stream()
                .map(s -> s.getCodigoSala() + " - " + s.getNombreSala() + " (" + s.getClass().getSimpleName() + ")")
                .toArray(String[]::new);
        JComboBox<String> cmbSala = new JComboBox<>(salasStr);

        JTextField txtFecha = new JTextField(LocalDate.now().format(FORMATO_FECHA));
        JTextField txtHora = new JTextField("18:00");
        JTextField txtPrecio = new JTextField("15000");

        Object[] campos = {
                "Película:", cmbPelicula,
                "Sala:", cmbSala,
                "Fecha (dd/MM/yyyy):", txtFecha,
                "Hora inicio (HH:mm):", txtHora,
                "Precio base:", txtPrecio
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Programar Función",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            int codPel = Integer.parseInt(peliculasStr[cmbPelicula.getSelectedIndex()].split(" - ")[0]);
            int codSala = Integer.parseInt(salasStr[cmbSala.getSelectedIndex()].split(" - ")[0]);
            Pelicula pelicula = cine.buscarPeliculaPorCodigo(codPel);
            Sala sala = cine.buscarSalaPorCodigo(codSala);
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), FORMATO_FECHA);
            LocalTime hora = LocalTime.parse(txtHora.getText().trim(), FORMATO_HORA);
            double precio = Double.parseDouble(txtPrecio.getText().trim());

            String codigoFuncion = "FUN-" + String.format("%03d", contadorFuncion++);
            Funcion funcion = new Funcion(codigoFuncion, fecha, hora, precio, pelicula, sala);

            if (cine.programarFuncion(funcion)) {
                mostrar("Función programada: " + codigoFuncion);
                mostrar("   " + pelicula.getTitulo() + " | " + sala.getNombreSala() +
                        " | " + fecha.format(FORMATO_FECHA) + " " + hora.format(FORMATO_HORA) +
                        " | Fin: " + funcion.getHoraFin().format(FORMATO_HORA) + " | $" + precio);
            } else {
                mostrar("Conflicto de horario en la sala " + sala.getNombreSala());
                contadorFuncion--;
            }
        } catch (Exception e) {
            mostrar("Error en los datos. Revise fecha (dd/MM/yyyy) y hora (HH:mm).");
        }
    }

    // ==================== 6. REALIZAR VENTA ====================

    private void realizarVenta() {
        if (cine.getClientes().isEmpty() || cine.getOperarios().isEmpty() || cine.getFunciones().isEmpty()) {
            mostrar("Se necesitan clientes, operarios y funciones registradas.");
            return;
        }

        String[] operariosStr = cine.getOperarios().stream()
                .map(o -> o.getDocumento() + " - " + o.getNombre())
                .toArray(String[]::new);
        JComboBox<String> cmbOperario = new JComboBox<>(operariosStr);

        String[] clientesStr = cine.getClientes().stream()
                .map(c -> c.getDocumento() + " - " + c.getNombre() + " (" + (c.getPorcentajeDescuento() * 100) + "% desc.)")
                .toArray(String[]::new);
        JComboBox<String> cmbCliente = new JComboBox<>(clientesStr);

        String[] funcionesStr = cine.getFunciones().stream()
                .map(f -> f.getCodigoFuncion() + " - " + f.getPelicula().getTitulo() +
                        " | " + f.getSala().getNombreSala() + " | $" + f.getPrecioBase())
                .toArray(String[]::new);
        JComboBox<String> cmbFuncion = new JComboBox<>(funcionesStr);

        JTextField txtAsientos = new JTextField();
        txtAsientos.setToolTipText("Ejemplo: A-5,A-6,B-3");

        Object[] campos = {
                "Operario:", cmbOperario,
                "Cliente:", cmbCliente,
                "Función:", cmbFuncion,
                "Asientos (separados por coma, ej: A-5,A-6):", txtAsientos
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Realizar Venta de Boletas",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            int docOp = Integer.parseInt(operariosStr[cmbOperario.getSelectedIndex()].split(" - ")[0]);
            int docCl = Integer.parseInt(clientesStr[cmbCliente.getSelectedIndex()].split(" - ")[0]);
            String codFun = funcionesStr[cmbFuncion.getSelectedIndex()].split(" - ")[0];

            Operario operario = cine.buscarOperarioPorDocumento(docOp);
            Cliente cliente = cine.buscarClientePorDocumento(docCl);
            Funcion funcion = cine.buscarFuncionPorCodigo(codFun);

            String[] partes = txtAsientos.getText().trim().toUpperCase().split(",");
            List<String> asientos = new ArrayList<>();
            for (String a : partes) {
                a = a.trim();
                if (a.matches("[A-Z]-\\d+")) {
                    asientos.add(a);
                }
            }

            if (asientos.isEmpty()) {
                mostrar("No se ingresaron asientos válidos (formato: A-5,B-3).");
                return;
            }

            String codigoVenta = "VEN-" + String.format("%03d", contadorVenta++);
            Venta venta = cine.realizarVenta(codigoVenta, operario, cliente, funcion, asientos);

            if (venta != null) {
                mostrar("Venta realizada: " + codigoVenta);
                mostrar("   Cliente: " + cliente.getNombre() + " | Operario: " + operario.getNombre());
                mostrar("   Función: " + funcion.getPelicula().getTitulo() +
                        " | Sala: " + funcion.getSala().getNombreSala() +
                        " (" + funcion.getSala().getClass().getSimpleName() + ")");
                for (Boleta b : venta.getBoletas()) {
                    mostrar("   Boleta " + b.getCodigoBoleta() + " | Asiento " +
                            b.getFila() + b.getNumeroAsiento() + " | $" + b.getValorPagado());
                }
                mostrar("   TOTAL: $" + venta.calcularValorTotal());
            } else {
                mostrar("No se pudo realizar la venta (asientos ocupados o inválidos).");
                contadorVenta--;
            }
        } catch (Exception e) {
            mostrar("Error al procesar la venta: " + e.getMessage());
        }
    }

    // ==================== 7. VALOR TOTAL VENTA ====================

    private void consultarValorTotalVenta() {
        if (cine.getVentas().isEmpty()) {
            mostrar("No hay ventas registradas.");
            return;
        }

        String[] ventasStr = cine.getVentas().stream()
                .map(v -> v.getCodigoVenta() + " - " + v.getCliente().getNombre() + " | $" + v.calcularValorTotal())
                .toArray(String[]::new);

        String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccione la venta:",
                "Valor Total de Venta", JOptionPane.QUESTION_MESSAGE, null, ventasStr, ventasStr[0]);

        if (seleccion == null) return;

        String codigo = seleccion.split(" - ")[0];
        double total = cine.calcularValorTotalVenta(codigo);
        mostrar("Valor total de " + codigo + ": $" + total);
    }

    // ==================== 8. % OCUPACIÓN ====================

    private void consultarPorcentajeOcupacion() {
        if (cine.getFunciones().isEmpty()) {
            mostrar("No hay funciones programadas.");
            return;
        }

        String[] funcionesStr = cine.getFunciones().stream()
                .map(f -> f.getCodigoFuncion() + " - " + f.getPelicula().getTitulo() +
                        " | Ocupación: " + String.format("%.1f", f.calcularPorcentajeOcupacion()) + "%")
                .toArray(String[]::new);

        String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccione la función:",
                "% de Ocupación", JOptionPane.QUESTION_MESSAGE, null, funcionesStr, funcionesStr[0]);

        if (seleccion == null) return;

        String codigo = seleccion.split(" - ")[0];
        Funcion f = cine.buscarFuncionPorCodigo(codigo);
        double pct = cine.calcularPorcentajeOcupacion(codigo);

        mostrar("Función: " + f.getPelicula().getTitulo());
        mostrar("   Sala: " + f.getSala().getNombreSala() + " (capacidad: " + f.getSala().getCapacidad() + ")");
        mostrar("   Boletas vendidas: " + f.getBoletasVendidas().size());
        mostrar("   Ocupación: " + String.format("%.1f", pct) + "%");
    }

    // ==================== LISTADOS ====================

    private void listarPeliculas() {
        limpiarResultados();
        mostrar("--- PELÍCULAS REGISTRADAS ---");
        if (cine.getPeliculas().isEmpty()) {
            mostrar("  (ninguna)");
            return;
        }
        for (Pelicula p : cine.getPeliculas()) {
            mostrar("  [" + p.getCodigoPelicula() + "] " + p.getTitulo() +
                    " | " + p.getDuracion() + " min | " + p.getTipoGenero() +
                    " | " + p.getClasificacionEdad());
        }
    }

    private void listarFunciones() {
        limpiarResultados();
        mostrar("--- FUNCIONES PROGRAMADAS ---");
        if (cine.getFunciones().isEmpty()) {
            mostrar("  (ninguna)");
            return;
        }
        for (Funcion f : cine.getFunciones()) {
            mostrar("  [" + f.getCodigoFuncion() + "] " + f.getPelicula().getTitulo() +
                    " | " + f.getFecha().format(FORMATO_FECHA) + " " + f.getHoraInicio().format(FORMATO_HORA) +
                    " | Sala: " + f.getSala().getNombreSala() + " (" + f.getSala().getClass().getSimpleName() + ")" +
                    " | $" + f.getPrecioBase() +
                    " | Ocupación: " + String.format("%.1f", f.calcularPorcentajeOcupacion()) + "%");
        }
    }

    private void listarClientes() {
        limpiarResultados();
        mostrar("--- CLIENTES REGISTRADOS ---");
        if (cine.getClientes().isEmpty()) {
            mostrar("  (ninguno)");
            return;
        }
        for (Cliente c : cine.getClientes()) {
            mostrar("  [" + c.getDocumento() + "] " + c.getNombre() +
                    " | Afiliación: " + c.getCodigoAfiliacion() +
                    " | Descuento: " + (c.getPorcentajeDescuento() * 100) + "%");
        }
    }

    private void listarOperarios() {
        limpiarResultados();
        mostrar("--- OPERARIOS REGISTRADOS ---");
        if (cine.getOperarios().isEmpty()) {
            mostrar("  (ninguno)");
            return;
        }
        for (Operario o : cine.getOperarios()) {
            mostrar("  [" + o.getDocumento() + "] " + o.getNombre() +
                    " | Empleado: " + o.getCodigoEmpleado() +
                    " | Salario: $" + o.getSalarioBasico());
        }
    }

    private void listarVentas() {
        limpiarResultados();
        mostrar("--- VENTAS REALIZADAS ---");
        if (cine.getVentas().isEmpty()) {
            mostrar("  (ninguna)");
            return;
        }
        for (Venta v : cine.getVentas()) {
            mostrar("  [" + v.getCodigoVenta() + "] " + v.getFechaVenta().format(FORMATO_FECHA) +
                    " | Cliente: " + v.getCliente().getNombre() +
                    " | Operario: " + v.getOperario().getNombre() +
                    " | Boletas: " + v.getBoletas().size() +
                    " | Total: $" + v.calcularValorTotal());
        }
    }

    private void listarSalas() {
        limpiarResultados();
        mostrar("--- SALAS REGISTRADAS ---");
        if (cine.getSalas().isEmpty()) {
            mostrar("  (ninguna)");
            return;
        }
        for (Sala s : cine.getSalas()) {
            mostrar("  [" + s.getCodigoSala() + "] " + s.getNombreSala() +
                    " | Tipo: " + s.getClass().getSimpleName() +
                    " | Capacidad: " + s.getCapacidad());
        }
    }

    // ==================== DATOS DE EJEMPLO ====================

    private void cargarDatosDeEjemplo() {
        cine.registrarSala(new Tradicional(1, "Sala Tradicional 1", 100, 'A', 10));
        cine.registrarSala(new VIP(2, "Sala VIP Premium", 40, 'A', 8));
        cine.registrarSala(new Sala3D(3, "Sala 3D Experience", 80, 'A', 10));

        cine.registrarPelicula(new Pelicula(101, "Avatar: El Camino del Agua", 192, TipoGenero.ACCION, "PG-13"));
        cine.registrarPelicula(new Pelicula(102, "Intensamente 2", 96, TipoGenero.ANIMACION, "G"));
        cine.registrarPelicula(new Pelicula(103, "El Conjuro 4", 112, TipoGenero.TERROR, "R"));
        cine.registrarPelicula(new Pelicula(104, "Deadpool & Wolverine", 128, TipoGenero.ACCION, "R"));
        cine.registrarPelicula(new Pelicula(105, "Divertida Mente 2", 96, TipoGenero.COMEDIA, "G"));

        cine.registrarCliente(new Cliente(1001, "María Gómez", "maria@email.com", 300123456, "AFI-001", 0.10));
        cine.registrarCliente(new Cliente(1002, "Carlos Pérez", "carlos@email.com", 300987654, "AFI-002", 0.05));
        cine.registrarCliente(new Cliente(1003, "Ana Rodríguez", "ana@email.com", 310555999, "AFI-003", 0.15));

        cine.registrarOperario(new Operario(2001, "Laura Martínez", "laura@cineplus.com", 310555123,
                "EMP-001", LocalDate.of(2023, 3, 15), 1800000));
        cine.registrarOperario(new Operario(2002, "Andrés López", "andres@cineplus.com", 310555456,
                "EMP-002", LocalDate.of(2024, 1, 10), 1600000));

        Pelicula avatar = cine.buscarPeliculaPorCodigo(101);
        Pelicula intensamente = cine.buscarPeliculaPorCodigo(102);
        Pelicula conjuro = cine.buscarPeliculaPorCodigo(103);
        Sala salaTrad = cine.buscarSalaPorCodigo(1);
        Sala salaVIP = cine.buscarSalaPorCodigo(2);
        Sala sala3D = cine.buscarSalaPorCodigo(3);

        cine.programarFuncion(new Funcion("FUN-001", LocalDate.now(), LocalTime.of(14, 0), 15000, avatar, sala3D));
        cine.programarFuncion(new Funcion("FUN-002", LocalDate.now(), LocalTime.of(17, 30), 12000, intensamente, salaTrad));
        cine.programarFuncion(new Funcion("FUN-003", LocalDate.now(), LocalTime.of(20, 0), 18000, conjuro, salaVIP));
        contadorFuncion = 4;
    }

    // ==================== MAIN ====================

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            Main ventana = new Main();
            ventana.setVisible(true);
        });
    }
}
