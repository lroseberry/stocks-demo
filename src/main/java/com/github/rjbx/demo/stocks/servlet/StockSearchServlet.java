package com.github.rjbx.demo.stocks.servlet;

import com.github.rjbx.demo.stocks.model.StockSearchBean;
import com.github.rjbx.demo.stocks.service.ServiceType;
import com.github.rjbx.demo.stocks.utility.StockServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class is a servlet for a stock quote search web application.
 */
public class StockSearchServlet extends HttpServlet {

    private static final String SYMBOL_PARAMETER_KEY = "symbol";
    private static final String STARTRANGE_PARAMETER_KEY = "startRange";
    private static final String ENDRANGE_PARAMETER_KEY = "endRange";
    private static final String INTERVAL_PARAMETER_KEY = "interval";
    private static final String SERVICETYPE_PARAMETER_KEY = "serviceType";

    /**
     * Requests access to form submission data and, after gaining access, uses the data to generate a response
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String symbol = request.getParameter(SYMBOL_PARAMETER_KEY);
        String startRange = request.getParameter(STARTRANGE_PARAMETER_KEY);
        String endRange = request.getParameter(ENDRANGE_PARAMETER_KEY);
        String interval = request.getParameter(INTERVAL_PARAMETER_KEY);
        String serviceType = request.getParameter(SERVICETYPE_PARAMETER_KEY);

        HttpSession session = request.getSession();

        StockSearchBean search = new StockSearchBean(symbol, startRange, endRange, interval);
        try {
            switch (serviceType) {
                case ("BASIC"):
                    search.processData(ServiceType.BASIC);
                    break;
                case ("DATABASE"):
                    search.processData(ServiceType.DATABASE);
                    break;
                case ("WEB"):
                    search.processData(ServiceType.WEB);
                    break;
                default:
                    search.processData(ServiceType.WEB);
            }
        } catch (StockServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        session.setAttribute("search", search);

        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher =
                servletContext.getRequestDispatcher("/stocksearchResults.jsp");
        dispatcher.forward(request, response);
    }
}
