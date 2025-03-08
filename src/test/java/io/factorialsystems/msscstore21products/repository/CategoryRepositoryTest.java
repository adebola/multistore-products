package io.factorialsystems.msscstore21products.repository;

import org.junit.jupiter.api.Test;


class CategoryRepositoryTest {

//    @ClassRule
//    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.33")
//            .withDatabaseName("test")
//            .withUsername("user")
//            .withPassword("password");

//    private MySQLContainer<?> mysqlContainer;
//    private Connection connection;
//
//    @BeforeEach
//    public void setUp() throws SQLException {
//        // Start the MySQL container
//        mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"))
//                .withDatabaseName("testdb")
//                .withUsername("testuser")
//                .withPassword("testpass");
//        mysqlContainer.start();
//
//        // Get the JDBC URL to connect to the container
//        String jdbcUrl = mysqlContainer.getJdbcUrl();
//
//        // Create a connection to the MySQL container
//        connection = DriverManager.getConnection(jdbcUrl, "testuser", "testpass");
//    }
//
//    @AfterEach
//    public void tearDown() throws SQLException {
//        // Close the database connection
//        if (connection != null) {
//            connection.close();
//        }
//
//        // Stop and remove the MySQL container
//        if (mysqlContainer != null) {
//            mysqlContainer.stop();
//        }
//    }

    @Test
    void save() {
    }
    @Test
    void update () {

    }
    @Test
    void  findById() {

    }

    @Test
    void findAll() {

    }

    @Test
    void search() {

    }

    @Test
    void findByName() {

    }

    @Test
    void suspend() {

    }

    @Test
    void unsuspend() {

    }
    @Test
    void delete () {

    }


//    @Test
//    public void testContainer() throws SQLException {
//        // Create a table
//        try (Statement statement = connection.createStatement()) {
//            statement.execute("CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(50))");
//        }
//
//        // Insert a row
//        try (Statement statement = connection.createStatement()) {
//            statement.execute("INSERT INTO users VALUES (1, 'John')");
//        }
//
//        // Query the table
//        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
//
//            // Verify the result
//            assertTrue(resultSet.next());
//            assertEquals(1, resultSet.getInt("id"));
//            assertEquals("John", resultSet.getString("name"));
//            assertFalse(resultSet.next());
//        }
//    }
}