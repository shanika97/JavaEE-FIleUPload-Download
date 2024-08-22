package api;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(urlPatterns = "/multiPartFile")
@MultipartConfig
public class MultiPartApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/fileupload.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String type = req.getParameter("type");

        // Access the uploaded file using the correct part name
        Part part = req.getPart("file");

        String fileName = extractFileName(part);
        if (fileName.isEmpty()) {
            resp.getWriter().write("Error: File name is invalid!");
            resp.getWriter().flush();
            return;
        }

        // Construct the file path
        String filePath = System.getProperty("user.home") + File.separator + "Pictures" +
                File.separator + "media" + File.separator + fileName;

        // Check if the file name already contains an extension
        if (type != null && !type.isEmpty()) {
            String fileExtension = getFileExtension(fileName);
            if (fileExtension.isEmpty()) {
                filePath += "." + type; // Append type as extension if none exists
            } else {
                // If file already has an extension, use it as is
                filePath = filePath; // No change needed
            }
        }

        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (InputStream is = part.getInputStream();
             FileOutputStream fos = new FileOutputStream(filePath)) {

            byte[] bytes = new byte[1024];
            int read;
            while ((read = is.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            resp.getWriter().write("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            resp.getWriter().write("Error uploading file!");
        } finally {
            resp.getWriter().flush();
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }

    // Helper method to get the file extension from a file name
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex + 1);
    }
}
