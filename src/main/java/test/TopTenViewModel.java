package test;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class TopTenViewModel {


	private static Connection connection = null;

	private static DataSource ds = null;



	private ArrayList<ArrayList<Object>> colonnemaxmin=null;
	ArrayList<Object> valuescolonnemaxmin = null;


	private ArrayList<Object> colonneaxeanlyse=null;
	ArrayList<Object> valuescolonneaxeanlyse = null;

	private ArrayList<ArrayList<Object>> columntopten = null;
	ArrayList<Object> valuescolumntopten = null;
	private ArrayList<ArrayList<Object>> columnmode = null;
	ArrayList<Object> valuescolumnmode = null;




	public void setColumntopten(ArrayList<ArrayList<Object>> columntopten) {
		this.columntopten = columntopten;
	}




	private ArrayList<Object> listespececolonne = null;
	ArrayList<Object> valueslistespececolonne = null;

	private String selectedvalue = null;

	public String getSelectedvaluecolumn() {
		return selectedvaluecolumn;
	}

	public void setSelectedvaluecolumn(String selectedvaluecolumn) {
		this.selectedvaluecolumn = selectedvaluecolumn;
	}

	private String selectedvaluecolumn = null;




	public void setMin(Double min) {
		this.min = min;
	}

	private Double min=null;

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	private Double max=null;

	public String getSelectedvalue() {
		return selectedvalue;

	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public void setSelectedvalue(String selectedvalue) {
		this.selectedvalue = selectedvalue;
	}


	public ArrayList<Object> getListespececolonne() {
		return listespececolonne;
	}

	public void setListespececolonne(ArrayList<Object> listespececolonne) {
		this.listespececolonne = listespececolonne;
	}


	public void setColumnmode(ArrayList<ArrayList<Object>> columnmode) {
		this.columnmode = columnmode;
	}

	//recuperer l'ensemble des colonnes de la table bandeentete
	public ArrayList<Object> getColonneaxeanlyse() {
		return colonneaxeanlyse;
	}

	public void setColonneaxeanlyse(ArrayList<Object> colonneaxeanlyse) {
		this.colonneaxeanlyse = colonneaxeanlyse;
	}





	/*("SELECT GMQ_TOTAL AS tot , NUMERO_BANDE , CODE_ELEVEUR , ESPECE , TYPE_ANIMAL, MARGE_PA, INDICE_CONSO_TECH, DATE_MEP_REEL, DATE_DERNIER_ENLEVEMENT" +
			" FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE " +
			"WHERE (UPPER(ESPECE) LIKE '%DDE%' OR UPPER(ESPECE) LIKE '%DINDE%') AND GMQ_TOTAL IS NOT NULL  ORDER BY GMQ_TOTAL DESC LIMIT 10"); */

	//@DependsOn("selectedvalue")

	@DependsOn("columntopten")
	public void topten() {
		System.out.println("je suis totp");

	}

	@Init
	public void init() {


		System.out.println(selectedvalue);
		//columnmode=new ArrayList<ArrayList<Object>>();


		try {
			InitialContext ic2 = new InitialContext();
			ds = (DataSource) ic2.lookup("jdbc/Snow");
			connection = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		listOfEspece();
		listOfcolumn();






/*

		}*/


		//initialiser listes des especes


		System.out.println("jkjjkkkll " + selectedvalue);

		//topten();

	}


	public void listOfEspece() {

		listespececolonne = new ArrayList<Object>();

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery
					("SELECT DISTINCT ESPECE " +
							"FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE " +
							"WHERE ESPECE NOT LIKE '%Poulettes%' AND ESPECE NOT LIKE '%Poulette%' AND ESPECE NOT LIKE '%Plettes%'");
					/*("SELECT DISTINCT ESPECE " +
							"FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE limit 2");*/
			while (resultSet.next()) {

				valueslistespececolonne = new ArrayList<Object>();
				valueslistespececolonne.add(resultSet.getString(1));


				listespececolonne.add(valueslistespececolonne);


			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void listOfcolumn() {

		colonneaxeanlyse = new ArrayList<Object>();

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery
					("SELECT COLUMN_NAME " +
							"FROM DB_DATASCIENCE_DABO.INFORMATION_SCHEMA.COLUMNS " +
							"WHERE TABLE_NAME =" +  "'BANDE_ENTETE' ORDER BY COLUMN_NAME ASC " );
					/*("SELECT DISTINCT ESPECE " +
							"FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE limit 2");*/
			while (resultSet.next()) {

				valuescolonneaxeanlyse = new ArrayList<Object>();
				valuescolonneaxeanlyse.add(resultSet.getString(1));


				colonneaxeanlyse.add(valuescolonneaxeanlyse);


			}

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@DependsOn("selectedvalue")
	public ArrayList<ArrayList<Object>> getColumntopten() {

		if (selectedvalue != null) {
			columntopten = new ArrayList<ArrayList<Object>>();
			String filtre = getSelectedvalue().replace("[", "").replace("]", "");

			System.out.println("le string " + filtre);

			try {
				InitialContext ic2 = new InitialContext();
				ds = (DataSource) ic2.lookup("jdbc/Snow");
				connection = ds.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery
						("SELECT GMQ_TOTAL AS tot , NUMERO_BANDE , CODE_ELEVEUR , ESPECE , TYPE_ANIMAL, MARGE_PA, INDICE_CONSO_TECH, DATE_MEP_REEL, DATE_DERNIER_ENLEVEMENT" +
								" FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE " +
								"WHERE UPPER(ESPECE) like " + "'" + "%" + filtre + "%" + "'" + "  AND GMQ_TOTAL IS NOT NULL  ORDER BY GMQ_TOTAL DESC LIMIT 10");
				while (resultSet.next()) {
					valuescolumntopten = new ArrayList<Object>();

					valuescolumntopten.add(resultSet.getDouble(1));
					valuescolumntopten.add(resultSet.getInt(2));
					valuescolumntopten.add(resultSet.getString(3));
					valuescolumntopten.add(resultSet.getString(4));
					valuescolumntopten.add(resultSet.getInt(5));
					valuescolumntopten.add(resultSet.getDouble(6));
					valuescolumntopten.add(resultSet.getDouble(7));
					valuescolumntopten.add(resultSet.getDate(8));
					valuescolumntopten.add(resultSet.getDate(9));

					columntopten.add(valuescolumntopten);

				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return columntopten;
		}
		return null;

	}


	@DependsOn({"selectedvalue","selectedvaluecolumn"})
	public ArrayList<ArrayList<Object>> getColumnmode() {

		if (selectedvalue != null && selectedvaluecolumn != null) {
			columnmode = new ArrayList<ArrayList<Object>>();
			String filtre = getSelectedvalue().replace("[", "").replace("]", "");
			String axe = getSelectedvaluecolumn().replace("[", "").replace("]", "");


			System.out.println("le string " + filtre);
			System.out.println("le string " + axe);
			try {
				InitialContext ic2 = new InitialContext();
				ds = (DataSource) ic2.lookup("jdbc/Snow");
				connection = ds.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery
						("SELECT GMQ_TOTAL, COUNT(GMQ_MALES) AS MODE, " + axe +" AS AXE "+
								"FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE " +
								"WHERE UPPER(ESPECE) LIKE" + "'" + "%" + filtre + "%" + "'" + " AND GMQ_TOTAL IS NOT NULL " +
								"GROUP BY GMQ_TOTAL, AXE ORDER BY GMQ_TOTAL DESC limit 10");
				while (resultSet.next()) {
					valuescolumnmode = new ArrayList<Object>();

					valuescolumnmode.add(resultSet.getDouble(1));
					valuescolumnmode.add(resultSet.getInt(2));
					valuescolumnmode.add(resultSet.getString(3));


					columnmode.add(valuescolumnmode);
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return columnmode;
		}
		else return null;

	}


	@DependsOn("selectedvalue")
	public ArrayList<ArrayList<Object>>getColonnemaxmin() {
		if (selectedvalue != null) {
			colonnemaxmin = new ArrayList<ArrayList<Object>>();
			String filtre = getSelectedvalue().replace("[", "").replace("]", "");

			System.out.println("le string " + filtre);
			try {
				InitialContext ic2 = new InitialContext();
				ds = (DataSource) ic2.lookup("jdbc/Snow");
				connection = ds.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery
						("SELECT MIN(GMQ_TOTAL) AS MIN_GMQ_TOTAL, MAX(GMQ_TOTAL) AS MAX_GMQ_TOTAL " +
								"FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE " +
								"WHERE UPPER(ESPECE) LIKE" + "'" + "%" + filtre + "%" + "'" + "AND GMQ_TOTAL IS NOT NULL" );
				while (resultSet.next()) {
					valuescolonnemaxmin = new ArrayList<Object>();

					valuescolonnemaxmin.add(resultSet.getDouble(1));
					valuescolonnemaxmin.add(resultSet.getDouble(2));

					colonnemaxmin.add(valuescolonnemaxmin);
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return colonnemaxmin;
		}
		else return null;
	}

	public void setColonnemaxmin(ArrayList<ArrayList<Object>> colonnemaxmin) {
		this.colonnemaxmin = colonnemaxmin;
	}


}
