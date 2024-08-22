package api;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet(urlPatterns = "/file")
@MultipartConfig
public class FileUploadAPI extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        String extension = req.getParameter("extension");
        Part name = req.getPart("image");
        InputStream stream = name.getInputStream();
        boolean isExported = exportFileToSpecificPath(stream, extension, fileName);
        resp.getWriter().write("File Exported Success : "+isExported);
    }

    public boolean exportFileToSpecificPath(InputStream stream,String extension,String fileName) {
        //Document API
        //Automaticaly create a folder
        String filePath = System.getProperty("user.home")+"\\OneDrive\\Documents\\api\\"+fileName+"."+extension;

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filePath);

            int i = 0;
            while ((i = stream.read()) != -1) {
                fileOutputStream.write(i);
            }
            fileOutputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
//
        }finally {

            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
