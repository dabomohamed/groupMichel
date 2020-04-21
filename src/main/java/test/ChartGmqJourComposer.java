package test;

import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.chart.Charts;
import org.zkoss.chart.model.CategoryModel;
import org.zkoss.chart.model.DefaultCategoryModel;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChartGmqJourComposer extends SelectorComposer<Component> {
    @Wire
    Charts chart;


    @GlobalCommand
    public void refresh (){
        System.out.println("lkdjlksdlksn"  );
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        CategoryModel model = new DefaultCategoryModel();
        Connection connection = null;
        DataSource ds= null;
        try {
            InitialContext ic2 = new InitialContext();
            ds = (DataSource) ic2.lookup("jdbc/Snow");
            connection =ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // nb de lots demarrés par années et mois
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("" +
                    "SELECT YEAR(DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE.DATE_MEP_REEL )" +
                    " AS YEAR, MONTH(DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE.DATE_MEP_REEL ) " +
                    "AS MONTH, count(*) FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE " +
                    "WHERE  YEAR  IS NOT NULL AND MONTH IS NOT NULL AND UPPER( ESPECE) " +
                    "NOT LIKE '%POULETTE%' AND UPPER( ESPECE) NOT LIKE '%PLETTE%' " +
                    "GROUP BY YEAR(DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE.DATE_MEP_REEL )," +
                    " MONTH(DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE.DATE_MEP_REEL ) " +
                    "ORDER BY YEAR, MONTH");
            while(resultSet.next()) {
                model.setValue("nb lots demarrés",resultSet.getString(2)+ "/"+ resultSet.getString(1), resultSet.getInt(3) );

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // nb lots  terminés par années et mois
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("" +
                    "SELECT YEAR(DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE.DATE_DERNIER_ENLEVEMENT ) AS YEAR, MONTH(DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE.DATE_DERNIER_ENLEVEMENT ) AS MONTH, count(STATUT_BANDE) FROM DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE WHERE STATUT_BANDE = 'TERMINEE' AND UPPER( ESPECE) NOT LIKE '%POULETTE%' AND UPPER( ESPECE) NOT LIKE '%PLETTE%' AND  YEAR  IS NOT null and MONTH IS NOT null GROUP BY YEAR(DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE.DATE_DERNIER_ENLEVEMENT ), MONTH(DB_DATASCIENCE_DABO.SCH_RESULT_REQ.BANDE_ENTETE.DATE_DERNIER_ENLEVEMENT ) ORDER BY YEAR, MONTH ");
            while(resultSet.next()) {
                model.setValue("nb lots terminés",resultSet.getString(2)+ "/"+ resultSet.getString(1), resultSet.getInt(3) );
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection.close();

        // Set model to the chart
        chart.setModel(model);
    }
}