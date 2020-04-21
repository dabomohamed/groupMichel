package test;

import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.chart.Charts;
import org.zkoss.chart.model.CategoryModel;
import org.zkoss.chart.model.DefaultCategoryModel;
import org.zkoss.chart.model.DefaultXYModel;
import org.zkoss.chart.model.XYModel;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.GroupsModel;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class GmqJoursViewModel  extends SelectorComposer<Component> {





	/*@Wire
	Charts chart;*/




	public void minAndMaxDate(){

	}

	private static Connection connection = null;

	private static DataSource ds = null;



	 private String selectedvalueespece = null;


	 private String selectedvaluelotbyespece = null;


	private String selectedvaluetypegmq = null;

	private ArrayList<Object> listespececolonne = null;
	ArrayList<Object> valueslistespececolonne = null;


	private ArrayList<ArrayList<Object>> columngmqbydays = null;
	ArrayList<Object> valuesgmqbydays = null;



	private ArrayList<Object> columnlotbyespace = null;
	ArrayList<Object> valueslotbyespace = null;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public DataSource getDs() {
		return ds;
	}



	public String getSelectedvaluetypegmq() {
		return selectedvaluetypegmq;
	}

	public void setSelectedvaluetypegmq(String selectedvaluetypegmq) {
		this.selectedvaluetypegmq = selectedvaluetypegmq;
	}


	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public ArrayList<Object> getListespececolonne() {
		return listespececolonne;
	}

	public void setListespececolonne(ArrayList<Object> listespececolonne) {
		this.listespececolonne = listespececolonne;
	}

	public String getSelectedvalueespece() {
		return selectedvalueespece;
	}

	public void setSelectedvalueespece(String selectedvalueespece) {
		System.out.println("uuuuuuuu");
		this.selectedvalueespece = selectedvalueespece;
	}

	public String getSelectedvaluelotbyespece() {

		return selectedvaluelotbyespece;
	}

	@NotifyChange({"model","columngmqbydays"})
	public void setSelectedvaluelotbyespece(String selectedvaluelotbyespece) {

		this.selectedvaluelotbyespece = selectedvaluelotbyespece;
		//drawChart();
	}

	public ArrayList<ArrayList<Object>> getColumngmqbydays() {
		if (selectedvalueespece != null && selectedvaluelotbyespece != null) {
			columngmqbydays = new ArrayList<ArrayList<Object>>();


			try {
				InitialContext ic2 = new InitialContext();
				ds = (DataSource) ic2.lookup("jdbc/Snow");
				connection = ds.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}


			/*
			"SELECT TABLE2.GMQ_MALES, TABLE2.POIDS_MALES, TABLE2.POIDS_MALES - TABLE1.POIDS_MALES , TABLE2.DATE_RELEVE " +
							"(SELECT DISTINCT BDJ.POIDS_MALES FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_DONNEES_JOURS BDJ, DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE BET WHERE BET.NUMERO_BANDE = BDJ.NUMERO_BANDE AND BDJ.GMQ_MALES <> 0 AND BDJ.NUMERO_BANDE=" + filtre2+ " " + "AND UPPER(BET.ESPECE) like " + "'" + "%" + filtre + "%" + "' ORDER BY BDJ.DATE_RELEVE ASC OFFSET 1 ROWS FETCH NEXT 10000000000 rows ONLY) TABLE1 , " +

			 */

			try {
			/*	String sql=	"SELECT DISTINCT BDJ.GMQ_MALES FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_DONNEES_JOURS BDJ, DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE BET WHERE BET.NUMERO_BANDE = BDJ.NUMERO_BANDE AND BDJ.GMQ_MALES <> 0 AND BDJ.NUMERO_BANDE=" + selectedvaluelotbyespece+ " " + "AND UPPER(BET.ESPECE) like " + "'" + "%" + selectedvalueespece + "%" + "'" ;
				//String sql2="SELECT DISTINCT BDJ.POIDS_MALES FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_DONNEES_JOURS BDJ, DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE BET WHERE BET.NUMERO_BANDE = BDJ.NUMERO_BANDE AND BDJ.GMQ_MALES <> 0 AND BDJ.NUMERO_BANDE=" + selectedvaluelotbyespece+ " " + "AND UPPER(BET.ESPECE) like " + "'" + "%" + selectedvalueespece + "%" + "' " ;
				String sql3=	"SELECT DISTINCT BDJ.DATE_RELEVE FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_DONNEES_JOURS BDJ, DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE BET WHERE BET.NUMERO_BANDE = BDJ.NUMERO_BANDE AND BDJ.GMQ_MALES <> 0 AND BDJ.NUMERO_BANDE=" + selectedvaluelotbyespece+ " " + "AND UPPER(BET.ESPECE) like " + "'" + "%" + selectedvalueespece + "%" + "' ORDER BY BDJ.DATE_RELEVE ASC " ;
*/


				String sql = "SELECT DISTINCT   BDJ.GMQ_MALES, BDJ.POIDS_MALES, BDJ.DATE_RELEVE  FROM DATASCIENCE_SHARE_DB.SCH_DATASCIENCE_VW.BANDE_DONNEES_JOURS_VIEW BDJ, DATASCIENCE_SHARE_DB.SCH_DATASCIENCE_VW.BANDE_ENTETE_VIEW BET WHERE BET.NUMERO_BANDE = BDJ.NUMERO_BANDE AND BDJ.GMQ_MALES <> 0 AND BDJ.NUMERO_BANDE=" + selectedvaluelotbyespece + " AND UPPER(BET.ESPECE) like '" + "%" + selectedvalueespece + "%'" + "  ORDER BY BDJ.DATE_RELEVE ASC ";

						/*"TABLE2(POIDS_MALES,DATE_RELEVE) AS (SELECT DISTINCT BDJ.POIDS_MALES, BDJ.DATE_RELEVE  FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_DONNEES_JOURS BDJ, DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE BET WHERE BET.NUMERO_BANDE = BDJ.NUMERO_BANDE AND BDJ.GMQ_MALES <> 0 AND BDJ.NUMERO_BANDE=" + selectedvaluelotbyespece +  " AND UPPER(BET.ESPECE) like '"+ "%" + selectedvalueespece + "%'" + " ORDER BY BDJ.DATE_RELEVE ASC OFFSET 1 ROWS FETCH NEXT 10000000000 rows ONLY)" +
				"SELECT DISTINCT TABLE1.GMQ_MALES,TABLE1.POIDS_MALES,TABLE2.POIDS_MALES - TABLE1.POIDS_MALES AS VARIATION, TABLE1.DATE_RELEVE  FROM TABLE1,TABLE2 GROUP BY TABLE1.DATE_RELEVE";
*/

				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);


				double valeurPrecedant = 0.0;
				double valeurCourante = 0.0;
				double delta = 0;
				int i=0;

				while (resultSet.next()) {

					if(i==0)
					{
						valuesgmqbydays = new ArrayList<Object>();
						valeurCourante = resultSet.getDouble(2);
						delta=0;

						valuesgmqbydays.add(resultSet.getDouble(1));
						valuesgmqbydays.add(resultSet.getDouble(2));
						valuesgmqbydays.add(delta);
						valuesgmqbydays.add(resultSet.getDate(3));
						valeurPrecedant = valeurCourante;
					}

					else {
						valuesgmqbydays = new ArrayList<Object>();
						valeurCourante = resultSet.getDouble(2);
						delta = valeurCourante - valeurPrecedant;



						valuesgmqbydays.add(resultSet.getDouble(1));
						valuesgmqbydays.add(resultSet.getDouble(2));
						valuesgmqbydays.add(delta);
						valuesgmqbydays.add(resultSet.getDate(3));
						valeurPrecedant = valeurCourante;


					}

					i++;
				columngmqbydays.add(valuesgmqbydays);

			}




				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return columngmqbydays;
		}
		return null;

	}

	public void setColumngmqbydays(ArrayList<ArrayList<Object>> columngmqbydays) {
		this.columngmqbydays = columngmqbydays;
	}

	@DependsOn("selectedvalueespece")
	public ArrayList<Object> getColumnlotbyespace() {

		columnlotbyespace = new ArrayList<Object>();

		if (selectedvalueespece != null) {
			String filtre = getSelectedvalueespece().replace("[", "").replace("]", "");

			System.out.println("" + filtre);

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
						("SELECT DISTINCT BDJ.NUMERO_BANDE, COUNT(BDJ.GMQ_MALES) AS NB FROM DATASCIENCE_SHARE_DB.SCH_DATASCIENCE_VW.BANDE_DONNEES_JOURS_VIEW BDJ, DATASCIENCE_SHARE_DB.SCH_DATASCIENCE_VW.BANDE_ENTETE_VIEW BET" +
								" WHERE BET.NUMERO_BANDE = BDJ.NUMERO_BANDE  AND UPPER(ESPECE) like " + "'" + "%" + filtre + "%" + "' AND BDJ.GMQ_MALES <> 0 GROUP BY BDJ.NUMERO_BANDE HAVING NB >= 5");
				while (resultSet.next()) {


					columnlotbyespace.add(resultSet.getInt(1));

				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}


			return columnlotbyespace;
		}
		return null;

	}

	public void setColumnlotbyespace(ArrayList<Object> columnlotbyespace) {
		this.columnlotbyespace = columnlotbyespace;
	}






	public void listOfEspece() {

		listespececolonne = new ArrayList<Object>();
		valueslistespececolonne = new ArrayList<Object>();


		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery
					("SELECT DISTINCT ESPECE , COUNT(BDJ.NUMERO_BANDE) AS NB" +
							" FROM DATASCIENCE_SHARE_DB.SCH_DATASCIENCE_VW.BANDE_DONNEES_JOURS_VIEW BDJ , DATASCIENCE_SHARE_DB.SCH_DATASCIENCE_VW.BANDE_ENTETE_VIEW BET" +
							" WHERE ESPECE NOT LIKE '%Poulettes%' AND ESPECE NOT LIKE '%Poulette%' AND ESPECE NOT LIKE '%Plettes%'" +
							" AND BET.NUMERO_BANDE = BDJ.NUMERO_BANDE AND BDJ.GMQ_MALES <> 0" +
							"GROUP BY ESPECE" +
							" HAVING NB > 0 ");

			while (resultSet.next()) {

				listespececolonne.add(resultSet.getString(1));
			}

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public CategoryModel getModel() {
		model = new DefaultCategoryModel();

		try {
			InitialContext ic2 = new InitialContext();
			ds = (DataSource) ic2.lookup("jdbc/Snow");
			connection = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (selectedvalueespece != null && selectedvaluelotbyespece != null) {
			System.out.println("apres le if ");
			// nb de lots demarrés par années et mois
			try {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("" +
						"SELECT YEAR(BDJ.DATE_RELEVE) AS ANNEE," +
						"MONTH(BDJ.DATE_RELEVE) AS MOIS," +
						"DAY(BDJ.DATE_RELEVE) AS JOURS," +
						" BDJ.GMQ_MALES," +
						"BDJ.POIDS_MALES" +
						" FROM DATASCIENCE_SHARE_DB.SCH_DATASCIENCE_VW.BANDE_DONNEES_JOURS_VIEW BDJ , DATASCIENCE_SHARE_DB.SCH_DATASCIENCE_VW.BANDE_ENTETE_VIEW BET" +
						" WHERE  ANNEE  IS NOT NULL AND MOIS IS NOT NULL AND UPPER( ESPECE) like '" + selectedvalueespece + "'" +
						" AND BET.NUMERO_BANDE = BDJ.NUMERO_BANDE AND BDJ.GMQ_MALES <> 0 AND BET.NUMERO_BANDE=" + selectedvaluelotbyespece +
						" GROUP BY ANNEE, MOIS, JOURS,BDJ.GMQ_MALES,BDJ.POIDS_MALES" +
						" ORDER BY ANNEE, MOIS,JOURS");
				double valeurPrecedant = 0.0;
				double valeurCourante = 0.0;
				double delta = 0;
				int i=0;

				while (resultSet.next()) {


					if(i==0)
					{
						valeurCourante = resultSet.getDouble(5);
						delta=0;

						valeurPrecedant = valeurCourante;
					}

					else {
						valeurCourante = resultSet.getDouble(5);
						delta = valeurCourante - valeurPrecedant;

						valeurPrecedant = valeurCourante;


					}




					//model.setValue("GMQ", resultSet.getString(3) + "/" + resultSet.getString(2), resultSet.getDouble(4));
					model.setValue("POIDS", resultSet.getString(3) + "/" + resultSet.getString(2), resultSet.getDouble(5));
					model.setValue("DELTA", resultSet.getString(3) + "/" + resultSet.getString(2),delta);

					i++;
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	public void setModel(CategoryModel model) {
		this.model = model;
	}

	private CategoryModel model;

	public void drawChart () {



	}


	@Init
	public void init() {

		try {
			InitialContext ic2 = new InitialContext();
			ds = (DataSource) ic2.lookup("jdbc/Snow");
			connection = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		listOfEspece();



		}


	

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






	/*private ArrayList<ArrayList<Object>> colonnemaxmin=null;
	ArrayList<Object> valuescolonnemaxmin = null;

	private ArrayList<ArrayList<Object>> columntopten = null;
	ArrayList<Object> valuescolumntopten = null;
	private ArrayList<ArrayList<Object>> columnmode = null;
	ArrayList<Object> valuescolumnmode = null;







	public void setColumntopten(ArrayList<ArrayList<Object>> columntopten) {
		this.columntopten = columntopten;
	}






	private String selectedvalue = null;




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



	public void setSelectedvalue(String selectedvalue) {
		this.selectedvalue = selectedvalue;
	}





	public void setColumnmode(ArrayList<ArrayList<Object>> columnmode) {
		this.columnmode = columnmode;
	}






	/*("SELECT GMQ_TOTAL AS tot , NUMERO_BANDE , CODE_ELEVEUR , ESPECE , TYPE_ANIMAL, MARGE_PA, INDICE_CONSO_TECH, DATE_MEP_REEL, DATE_DERNIER_ENLEVEMENT" +
			" FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE " +
			"WHERE (UPPER(ESPECE) LIKE '%DDE%' OR UPPER(ESPECE) LIKE '%DINDE%') AND GMQ_TOTAL IS NOT NULL  ORDER BY GMQ_TOTAL DESC LIMIT 10"); */

		//@DependsOn("selectedvalue")

	/*@DependsOn("columntopten")
	public void topten() {
		System.out.println("je suis totp");

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
								" FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE " +
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


	@DependsOn("selectedvalue")
	public ArrayList<ArrayList<Object>> getColumnmode() {

		if (selectedvalue != null) {
			columnmode = new ArrayList<ArrayList<Object>>();
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
						("SELECT GMQ_TOTAL, COUNT(GMQ_MALES) AS MODE " +
								"FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE " +
								"WHERE UPPER(ESPECE) LIKE" + "'" + "%" + filtre + "%" + "'" + " AND GMQ_TOTAL IS NOT NULL " +
								"GROUP BY GMQ_TOTAL ORDER BY MODE DESC limit 10");
				while (resultSet.next()) {
					valuescolumnmode = new ArrayList<Object>();

					valuescolumnmode.add(resultSet.getDouble(1));
					valuescolumnmode.add(resultSet.getInt(2));

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
								"FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE " +
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

*/

}
