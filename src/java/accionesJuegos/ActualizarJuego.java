/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accionesJuegos;

import interfaces.IPersistencia;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import objetosNegocio.Videojuego;
import persistencia.PersistenciaBD;

/**
 *
 * @author user
 */
@WebServlet(name = "ActualizarJuego", urlPatterns = {"/ActualizarJuego"})
public class ActualizarJuego extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("index.jsp");
        Videojuego vj = new Videojuego();

        // Obten la tarea seleccionada del atributo tareaSel de la
        // variable session que es la que contiene a todas las variables con
        // ámbito de sesion
        HttpSession session = request.getSession();

        // Crea el objeto para acceder a la base de datos
        IPersistencia fachada = new PersistenciaBD();

        vj.setNumCatalogo((String) request.getParameter("num"));

        if (session.getAttribute("accionSel").equals("obtenActualizar")) {
            request.setAttribute("juego", fachada.obten(vj));

            session.setAttribute("accionSel", "actualizar");

            rd = request.getRequestDispatcher("actualizarJuego.jsp");

            rd.forward(request, response);
        } else {
            vj.setNumCatalogo((String) request.getParameter("num"));
            vj.setTitulo((String) request.getParameter("titulo"));
            vj.setGenero((String) request.getParameter("genero"));
            vj.setClasificacion((String) request.getParameter("clasificacion"));
            vj.setConsola((String) request.getParameter("consola"));
            vj.setFabricante((String) request.getParameter("fabricante"));
            vj.setVersion(Integer.parseInt(request.getParameter("version")));
            fachada.actualizar(vj);
            session.setAttribute("accionSel", "listarJuegos");

            rd = request.getRequestDispatcher("ObtenJuegos");

            rd.forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
