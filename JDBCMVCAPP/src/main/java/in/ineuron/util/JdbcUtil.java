package in.ineuron.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {
	private static Connection connection;
	
	private JdbcUtil() {
		
	}

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getJdbcConnection() throws IOException, SQLException {
		Properties p = new Properties();
		File f = new File("E:\\eclipse prgrams\\JDBCMVCAPP\\src\\main\\java\\in\\ineuron\\properties\\db.properties");
		FileInputStream fis = new FileInputStream(f);
		p.load(fis);
		
		String url = p.getProperty("url");
		String user = p.getProperty("user");
		String password = p.getProperty("password");
		connection = DriverManager.getConnection(url,user,password);
		
		return connection;
	}

}
