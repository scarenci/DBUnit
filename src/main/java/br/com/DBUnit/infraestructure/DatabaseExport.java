package br.com.DBUnit.infraestructure;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.util.search.SearchException;

public class DatabaseExport
{
	public static void main(String[] args) throws Exception
    {
		Class.forName("org.hsqldb.jdbcDriver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:mydb", "sa", "");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        fullExport(connection);

        partialExport(connection);
        
        foreignKeyExport(connection);
    }

	private static void fullExport(IDatabaseConnection connection) throws SQLException, IOException, DataSetException,FileNotFoundException {
		IDataSet fullDataSet = connection.createDataSet();
		FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
	}

	private static void partialExport(IDatabaseConnection connection) throws AmbiguousTableNameException, IOException, DataSetException, FileNotFoundException {
		QueryDataSet partialDataSet = new QueryDataSet(connection);
		partialDataSet.addTable("FOO", "SELECT * FROM TABLE WHERE COL='VALUE'");
		partialDataSet.addTable("BAR");
		FlatXmlDataSet.write(partialDataSet, new FileOutputStream("partial.xml"));
	}

	private static void foreignKeyExport(IDatabaseConnection connection) throws SearchException, SQLException, DataSetException, IOException, FileNotFoundException {
		String[] depTableNames = TablesDependencyHelper.getAllDependentTables( connection, "X" );
        IDataSet depDataset = connection.createDataSet( depTableNames );
        FlatXmlDataSet.write(depDataset, new FileOutputStream("dependents.xml"));
	}
}