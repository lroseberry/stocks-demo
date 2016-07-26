package com.github.rjbx.demo.stocks.utility;

import com.github.rjbx.demo.stocks.model.StockQuote;
import com.github.rjbx.demo.stocks.service.ServiceFactory;
import com.github.rjbx.demo.stocks.service.ServiceType;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.apache.http.annotation.Immutable;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import static com.github.rjbx.demo.stocks.utility.DatabaseUtils.initializationFile;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit tests for the DatabaseUtils class.
 * @author Bob Basmaji
 */
@Immutable
public final class DatabaseUtilsTest {

    @Before
    public final void setUp() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(initializationFile);
    }
    /**
     * Verifies that the instance created by the factory is open
     */
    @Test
    public final void testGetSessionFactoryPositive() {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        assertTrue("getSessionFactory() return value is closed", session.isOpen());
    }

    /**
     * Verifies that the instance created by the factory is not set to "default read-only"
     */
    @Test
    public final void testGetSessionFactoryNegative() {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        assertFalse("getSessionFactory() return value is set to default read-only", session.isDefaultReadOnly());
    }

    /**
     * Verifies that the return value is a valid connection
     * @throws DatabaseConnectionException
     */
    @Test
    public final void testGetConnectionPositive() throws DatabaseConnectionException {
        assertTrue("getConnection() return value is not a valid connection", DatabaseUtils.getConnection() instanceof Connection);
    }

    /**
     * Verifies that the return value is not an invalid object
     * @throws DatabaseConnectionException
     */
    @Test
    public final void testGetConnectionNegative() throws DatabaseConnectionException {
        assertFalse("getConnection() return value is an invalid object", DatabaseUtils.getConnection() instanceof Calendar);
    }

    /**
     * Verifies that the connection can execute a statement
     * @throws DatabaseConnectionException
     * @throws DatabaseInitializationException
     * @throws SQLException
     */
    @Test
    public final void testInitializeDatabasePositive() throws DatabaseConnectionException, DatabaseInitializationException, SQLException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        assertTrue("getConnection() cannot execute a statement", DatabaseUtils.getConnection().createStatement().execute("select * from quotes"));
        DatabaseUtils.getConnection().createStatement().executeUpdate("DROP TABLE quotes;");
    }

    /**
     * Verifies that the connection cannot execute an invalid statement
     * @throws DatabaseConnectionException
     * @throws DatabaseInitializationException
     * @throws SQLException
     */
    @Test
    public final void testInitializeDatabaseNegative() throws DatabaseConnectionException, DatabaseInitializationException, SQLException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        boolean doesExecute = true;
        try {
            DatabaseUtils.getConnection().createStatement().execute("SELECT creme from oreo");
        } catch (MySQLSyntaxErrorException e) {
            doesExecute = false;
        }
        assertFalse("getConnection() executes an invalid statement", doesExecute);
        DatabaseUtils.getConnection().createStatement().executeUpdate("DROP TABLE quotes;");
    }

    /**
     * Verifies that persistXMLData() access and retrieves data from an XML file and stores it in a SQL database
     * @throws StockServiceException
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testPersistXMLDataPositive() throws StockServiceException, XMLUnmarshalException {
        DatabaseUtils.persistXMLData(XMLUtils.xmlFilePath);
        StockQuote quote = ServiceFactory.createStockService(ServiceType.DATABASE).getQuote("WIFI");
        assertTrue("persistXMLData() does not store data from the referenced XML file", quote.getPrice() instanceof BigDecimal);
    }

    /**
     * Verifies that persistXMLData() does not access and retrieve data from a nonexistent file
     * @throws StockServiceException
     * @throws XMLUnmarshalException
     */
    @Test(expected=XMLUnmarshalException.class)
    public final void testPersistXMLDataNegative() throws StockServiceException, XMLUnmarshalException {
        DatabaseUtils.persistXMLData("null.xml");
    }
}