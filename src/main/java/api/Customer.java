package api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.CustomerDTO;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet( urlPatterns = "/customer")
public class Customer extends HttpServlet {

    //Query Param
    //Path Variable
    //Headers
    //Body

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Query Param
        //Path Variable
        //Headers
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Query Param
        String id = req.getParameter("id");
        //Path Variable
        //Headers
        String test = req.getHeader("User-Agent");
        BufferedReader reader = req.getReader();

        //JAVA I/O

        Gson gson = new Gson();

        //Body
        //dto.Customer customer = gson.fromJson(reader, dto.Customer.class);
        ArrayList<CustomerDTO> customerDTOS = gson.fromJson(reader, new TypeToken<ArrayList<CustomerDTO>>() {});



        System.out.println(customerDTOS);

        resp.getWriter().write("From Server : ");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Query Param
        //Path Variable
        //Headers
        //Body
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Query Param
        //Path Variable
        //Headers
        //Body
        super.doDelete(req, resp);
    }
}
