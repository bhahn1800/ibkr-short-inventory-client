package ibkr.client;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * More on what data this client provides can be read about here: https://ibkr.info/article/2024
 */
public class ShortInventoryClient {

    public static void main(String[] args) throws IOException {
        ShortInventoryClient shortInventoryClient = new ShortInventoryClient();
        Map<String, ShortInventoryData> shortInventoryDataMap = shortInventoryClient.shortInventoryData(Region.USA);
        ShortInventoryData shortInventoryData = shortInventoryDataMap.get("AAPL");
        System.out.println(shortInventoryData.toString());
    }

    private static final String FTP_SITE = "ftp3.interactivebrokers.com";
    private static final String FTP_USER = "shortstock";
    private static final String LINE_START = "#";
    private static final String EOF = LINE_START + "EOF";
    private static final String FILE_EXTENSION = ".txt";
    private static final String GT = ">";
    private static final String VALUE_SEPARATOR = "\\|";

    public enum Region {
        Australia, Austria, Belgium, British, Canada, Dutch, France, Germany, HongKong, India, Italy, Japan, Mexico, Spain, Swedish, Swiss, USA
    }

    public Map<String, ShortInventoryData> shortInventoryData(Region region) throws IOException {
        Map<String, ShortInventoryData> shortableStocks = new HashMap<>();

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(FTP_SITE);
            ftpClient.login(FTP_USER, StringUtils.EMPTY);
            ftpClient.setControlKeepAliveTimeout(300);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);

            String fileName = region.name().toLowerCase() + FILE_EXTENSION;
            try (InputStream is = ftpClient.retrieveFileStream(fileName)) {
                if (is == null) throw new FileNotFoundException(fileName + " not available");

                List<String> lines = IOUtils.readLines(is, StandardCharsets.UTF_8);
                for (String line : lines) {
                    if (line.startsWith(EOF)) break;

                    if (!line.startsWith(LINE_START)) {
                        String[] values = line.split(VALUE_SEPARATOR);

                        String symbol = values[0];
                        String availableStr = values[values.length - 1];
                        Long available = Long.parseLong(StringUtils.remove(availableStr, GT));
                        shortableStocks.put(symbol, new ShortInventoryData(symbol, available, availableStr.startsWith(GT)));
                    }
                }
            }

        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.completePendingCommand();
                ftpClient.disconnect();
            }
        }

        return shortableStocks;
    }

}
