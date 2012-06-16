package ro.teamnet.hackaton.storage.sql;

import org.junit.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import ro.teamnet.hackaton.storage.Event;
import ro.teamnet.hackaton.storage.EventNode;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author padawan
 * @since 6/16/12 2:03 PM
 */
public class SqlEventNode_Test {

	static JdbcOperations jdbcTemplate;

	@BeforeClass
	public static void setupDataSource() throws Exception {
		Class.forName("org.h2.Driver");
		Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
		DataSource dataSource = new SingleConnectionDataSource(connection, true);

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	EventNode eventNode;


	@Before
	public void setupEventNode() {
		eventNode = new SqlEventNode("custom_event", jdbcTemplate);
	}


	@After
	public void tearDownEventNode() {
		eventNode = null;
	}


	@Test
	public void should_insert_into_storage() {
		// then
		eventNode.store(new Event(System.currentTimeMillis() / 1000, 1));
		// assert
		Assert.assertEquals("should have 1 event stored", 1, jdbcTemplate.queryForInt("select count(*) from t_event_custom_event"));
	}
}
