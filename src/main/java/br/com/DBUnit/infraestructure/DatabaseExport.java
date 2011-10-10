package br.com.DBUnit.infraestructure;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class DatabaseExport
{
	public static void main(String[] args) throws Exception
    {
		DatabaseExport export = new DatabaseExport();
		export.fullExport(export.getConnection());
		export.partialExport(export.getConnection());
		export.dependentsExport(export.getConnection());
    }
	
	public IDatabaseConnection getConnection() throws Exception{
		Class.forName("org.hsqldb.jdbcDriver");
		Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:mydb", "sa", "");
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		return connection;
	}
	
	public void fullExport(IDatabaseConnection connection) throws Exception{
		IDataSet fullDataSet = connection.createDataSet();
		FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
	}

	public void partialExport(IDatabaseConnection connection) throws Exception{
		QueryDataSet partialDataSet = new QueryDataSet(connection);
		partialDataSet.addTable("Terceiro");
		partialDataSet.addTable("Terceiro", "SELECT * FROM Terceiro WHERE id='1'");
		FlatXmlDataSet.write(partialDataSet, new FileOutputStream("partial.xml"));
	}

	private void dependentsExport(IDatabaseConnection connection) throws Exception {
		String[] depTableNames = TablesDependencyHelper.getAllDependentTables( connection, "Terceiro" );
        IDataSet depDataset = connection.createDataSet( depTableNames );
        FlatXmlDataSet.write(depDataset, new FileOutputStream("dependents.xml"));
	}
}