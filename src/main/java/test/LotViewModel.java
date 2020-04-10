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
import java.util.ArrayList;
import java.util.Properties;

public class LotViewModel {


	public ArrayList<String> getGrandeEspece() {
		return grandeEspece;
	}

	public String getSelectedEspece() {
		return selectedEspece;
	}

	public void setSelectedEspece(String selectedEspece) {
		this.selectedEspece = selectedEspece;
	}

	String selectedEspece = "";
	public void setGrandeEspece(ArrayList<String> grandeEspece) {
		this.grandeEspece = grandeEspece;
	}

	public String getNbLivraison() {
		return nbLivraison;
	}

	public void setNbLivraison(String nbLivraison) {
		this.nbLivraison = nbLivraison;
	}

	String nbLivraison ="";
	ArrayList<String> grandeEspece = new ArrayList<String>();

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
			ResultSet resultSet = statement.executeQuery("SELECT count(*) as tot FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE WHERE STATUT_BANDE = 'TERMINEE' and UPPER( ESPECE) NOT LIKE '%POULETTE%' AND UPPER( ESPECE) NOT LIKE '%PLETTE%'");
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
			ResultSet resultSet = statement.executeQuery("SELECT  DISTINCT  CASE WHEN UPPER(ESPECE) LIKE '%DINDE%'   THEN 'DINDE' WHEN UPPER(ESPECE) LIKE '%DDE%'    THEN 'DINDE' WHEN UPPER(ESPECE) LIKE '%POULET%'  THEN 'POULET'  WHEN UPPER(ESPECE) LIKE '%PLT%'  THEN 'POULET'  WHEN UPPER(ESPECE) LIKE '%PINTADE%'    THEN 'PINTADE' WHEN UPPER(ESPECE) LIKE '%CANARD%'    THEN 'CANARD' ELSE UPPER(ESPECE) END AS rr    FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE WHERE UPPER( ESPECE) NOT LIKE '%POULETTE%' AND UPPER( ESPECE) NOT LIKE '%PLETTE%';");
			while(resultSet.next()) {
				grandeEspece.add(resultSet.getString(1));
			}
			statement.close();
		//	connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

 selectedEspece = grandeEspece.get(0);


		try {
			InitialContext ic2 = new InitialContext();
			ds = (DataSource) ic2.lookup("jdbc/Snow");
			connection =ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*)  FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE  Inner JOIN   (SELECT sum(ded.QUANTITE ) AS qt, ded.NUMERO_BANDE AS ret FROM  (SELECT *  FROM DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENLEVEMENT AS ta WHERE (ta.CODE_QUALITE = 'NOR' OR ta.CODE_QUALITE like 'DC%' )GROUP BY ta.NUMERO_BANDE, ta.SOCIETE_COMMERCIALE, ta.CODE_ELEVEUR, ta.FORME_JURIDIQUE, ta.RAISON_SOCIALE, ta.NOM_ELEVEUR, ta.CODE_ABATTOIR, ta.DATE_LIVRAISON, ta.CODE_PRODUIT, ta.LIBELLE_PRODUIT, ta.ABREGE_PRODUIT, ta.CODE_FAMILLE, ta.LIBELLE_FAMILLE, ta.ABREGE_FAMILLE, ta.QUANTITE, ta.QUANTITE_LIVREE, ta.CODE_SOUS_FAMILLE, ta.LIBELLE_SOUS_FAMILLE, ta.CODE_QUALITE, ta.LIBELLE_QUALITE, ta.ABREGE_QUALITE, ta.DATE_FACTURE_ELEVEUR, ta.POIDS_COTE_ELEVEUR, ta.MONTANT_HT_COTE_ELEVEUR, ta.POIDS_COTE_ABATTOIR, ta.MONTANT_HT_COTE_ABATTOIR, ta.NUMERO_FACTURE_ELEVEUR, ta.DATE_FACTURE_ABATTOIR, ta.NUMERO_FACTURE_ABATTOIR, ta.TOTAL_MARGE, ta.CODE_SEXE, ta.LIBELLE_SEXE) ded  GROUP BY ded.NUMERO_BANDE) s	ON   TO_NUMERIC( DATASCIENCE_SHARE_DB.SCH_ELABORATE_DATA.BANDE_ENTETE.NUMERO_BANDE) = TO_NUMERIC(s.ret) where UPPER( ESPECE) NOT LIKE '%POULETTE%' AND UPPER( ESPECE) NOT LIKE '%PLETTE%';");
			while(resultSet.next()) {
				nbLivraison = resultSet.getString(1);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}




	}

}
