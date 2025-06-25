package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.util.HashMap;
import java.util.List;

import javax.servlet.*;

import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import upload.Upload;


public class UploadFile extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int MEMORY_THRESHOLD   = 20 * 1024 * 1024; // 20MB
    private static final long MAX_FILE_SIZE      = 10L * 1024 * 1024; // 10MB
    private static final long MAX_REQUEST_SIZE   = 15L * 1024 * 1024; // 15MB

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HashMap<String, String> mp = new HashMap<>();

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            response.getWriter().print("Login");
            return ;
        }

        int userId = (int) session.getAttribute("user");

        if (userId == 0) {
            response.getWriter().print("Login");
            return ;
        }


        if (!ServletFileUpload.isMultipartContent(request)) {
            response.getWriter().print("File");
            return;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();

        factory.setSizeThreshold(MEMORY_THRESHOLD);

        factory.setRepository(new java.io.File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        upload.setFileSizeMax(MAX_FILE_SIZE);

        upload.setSizeMax(MAX_REQUEST_SIZE);

        upload.setHeaderEncoding("UTF-8");

        String diskDir = request.getServletContext().getRealPath("./disk/");

        java.io.File fil = new java.io.File(diskDir);

        if (!fil.exists())
            fil.mkdir();



        List<FileItem> form;

        try {
            form = upload.parseRequest(request);
        } catch (Exception ex) {
            response.getWriter().print("File");
            return ;
        }

        if (form == null)
            return ;

        int dir = 0;
        String idx = null;
        for (FileItem item : form)
            if (item.isFormField()) {
                if (item.getFieldName().equals("dir"))
                    dir = Integer.parseInt(item.getString());
                if (item.getFieldName().equals("idx"))
                    idx = item.getString();
            }

        if (userId != Upload.get(dir)) {
            return ;
        }


        diskDir = diskDir + java.io.File.separator + dir;

        fil = new java.io.File(diskDir);

        if (!fil.exists())
            fil.mkdir();

        diskDir = diskDir + java.io.File.separator + idx;

        for (FileItem item : form)
            if (!item.isFormField()) {

                InputStream in = item.getInputStream();
                FileOutputStream out = new FileOutputStream(diskDir);

                byte buffer[] = new byte[1048576];
                int len;
                while ((len = in.read(buffer)) > 0)
                    out.write(buffer, 0, len);
                in.close();
                out.close();
                Upload.addSiz(dir, item.getSize());
            }

        response.getWriter().print("Success");
        return ;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

        return ;
    }
}
