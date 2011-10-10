package br.com.DBUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import br.com.DBUnit.domain.entity.Terceiro;
import br.com.DBUnit.domain.repository.TerceiroRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext_test.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class DBUnitTest{

	private TerceiroRepository terceiroRepository;
	
    private DataSource dataSource;
	
	private static String fileDataSet = "DatabaseInMemory/TerceiroDataSet.xml";
	
	public IDatabaseConnection getConnection() throws Exception{
        Connection con = dataSource.getConnection();
        IDatabaseConnection connection = new DatabaseConnection(con);
        return connection;
    }

    @SuppressWarnings("deprecation")
	public IDataSet getDataSet() throws Exception{
        File file = new File(fileDataSet);
        return new FlatXmlDataSet(file);
    }
    
    @Before
    public void init() throws Exception{
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
    }

    @After
    public void after() throws Exception{
        DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());
    }
	
	@Test
	public void deveTestarConsultaNoHibernate() throws Exception{
		Terceiro terceiro = terceiroRepository.get(1L);
		assertThat(terceiro, is(notNullValue()));
	}	
	
	@Test
	public void deveUtilizarDBUnit() throws Exception{
        QueryDataSet databaseSet = new QueryDataSet(getConnection());
        databaseSet.addTable("pessoas", "select * from terceiro where id = 1");
        ITable actualTable = databaseSet.getTables()[0];

        @SuppressWarnings("deprecation")
		IDataSet expectedDataSet = new FlatXmlDataSet(new File("DatabaseInMemory/expectedDataSet.xml"));
        ITable expectedTable = expectedDataSet.getTable("Terceiro");

        actualTable = DefaultColumnFilter.includedColumnsTable(actualTable, expectedTable.getTableMetaData().getColumns());
		
        assertThat(1,is(expectedTable.getRowCount()));
        assertThat(expectedTable.getRowCount(), is(actualTable.getRowCount()));
        assertThat(expectedTable.getValue(0, "nome"), is(actualTable.getValue(0, "nome")));
	}
	
	@Autowired
	public void setTerceiroRepository(TerceiroRepository terceiroRepository) {
		this.terceiroRepository = terceiroRepository;
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
