import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class Client {

    public static void main(String[] args) {
        String server = "localhost";
        int port = 21;
        String user = "user";
        String pass = "1234";

        FTPClient ftpClient = new FTPClient();
        try {

            // Establish FTP client settings
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Decide which file is to be sent
            File localFile = new File("src/test.txt");

            // Create input stream to send file
            InputStream inputStream = new FileInputStream(localFile);
            // Name the file as it will appear on server
            String file = "test.txt";

            // Send file to FTP server via inputstream
            boolean done = ftpClient.storeFile(file, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The file is uploaded successfully.");
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}