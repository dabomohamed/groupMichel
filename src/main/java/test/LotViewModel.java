package test;

import org.apache.commons.dbcp2.BasicDataSource;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class LotViewModel {

	public int getNbLotTermines() {





/*


		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.fscontext.RefFSContextFactory");
		System.setProperty(Context.PROVIDER_URL, "file:///tmp");
		try {
			InitialContext ic = null;

			ic = new InitialContext();


		// Construct BasicDataSource
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("org.apache.commons.dbcp2.TesterDriver");
		bds.setUrl("jdbc:apache:commons:testdriver");
		bds.setUsername("userName");
		bds.setPassword("password");
//bds.setDefaultCatalog();
		ic.rebind("jdbc/basic", bds);




	//	conn.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}

*/

		Connection connection = null;
		try {
			InitialContext ic2 = new InitialContext();
			//DataSource ds = (DataSource) ic2.lookup("jdbc/basic");
			System.out.println("iiiidddiiiiikdddj");
			DataSource ds = (DataSource) ic2.lookup("jdbc/Snow");

System.out.println("kjhkjhkj");
			connection =ds.getConnection();
			//Connection conn = ds.getConnection();

			//conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}



		try {
		//	connection = getConnection();

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT count(*) as tot FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE WHERE STATUT_BANDE = 'TERMINEE'");
				while(resultSet.next()) {
					nbLotTermines=	resultSet.getInt(1);
				}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbLotTermines;
	}
/*
	private static Connection getConnection()
			throws SQLException {
		try {
			Class.forName("com.snowflake.client.jdbc.SnowflakeDriver");
		} catch (ClassNotFoundException ex) {
			System.err.println("Driver not found");
		}
		// build connection properties
		Properties properties = new Properties();
		properties.put("user", "USR_DABO");     // replace "" with your username
		properties.put("password", "#Dabo@2020!"); // replace "" with your password
		//properties.put("account", "");  // replace "" with your account name
		properties.put("db", "DATASCIENCE_SHARE_DB");       // replace "" with target database name
		properties.put("schema", "SCH_ELABORATE_DATA");   // replace "" with target schema name
		//properties.put("tracing", "on");

		// create a new connection
		String connectStr = System.getenv("SF_JDBC_CONNECT_STRING");
		// use the default connection string if it is not set in environment
		if (connectStr == null) {
			connectStr = "jdbc:snowflake://uc18949.west-europe.azure.snowflakecomputing.com"; // replace accountName with your account name
		}
		return DriverManager.getConnection(connectStr, properties);
	}

*/

	public void setNbLotTermines(int nbLotTermines) {
		this.nbLotTermines = nbLotTermines;
	}

	//private int count;
private  int nbLotTermines = 0;

	@Init
	public void init() {

	}

}
