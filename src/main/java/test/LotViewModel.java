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
		Connection connection = null;
		DataSource  ds= null;
		try {
			InitialContext ic2 = new InitialContext();
			ds = (DataSource) ic2.lookup("jdbc/Snow");
			connection =ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT count(*) as tot FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE WHERE STATUT_BANDE = 'TERMINEE'");
				while(resultSet.next()) {
					nbLotTermines=	resultSet.getInt(1);
				}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbLotTermines;
	}

	public void setNbLotTermines(int nbLotTermines) {
		this.nbLotTermines = nbLotTermines;
	}

	private  int nbLotTermines = 0;

	@Init
	public void init() {

	}

}
