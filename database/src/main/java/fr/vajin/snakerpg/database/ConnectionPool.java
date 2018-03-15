package fr.vajin.snakerpg.database;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {

    private static ConnectionPool ourInstance = new ConnectionPool();
    private BoneCP connectionPool;
    private boolean opened;

    public static ConnectionPool getInstance() {
        return ourInstance;
    }

    public static Connection getConnection() throws SQLException {
        return ourInstance.connectionPool.getConnection();
    }

    private ConnectionPool() {
        Properties connectionProp = new Properties();
        try {
            connectionProp.loadFromXML(getClass().getResourceAsStream("/connection.xml"));
            BoneCPConfig config = new BoneCPConfig();

            config.setJdbcUrl(connectionProp.getProperty("url"));
            config.setUsername(connectionProp.getProperty("user"));
            config.setPassword(connectionProp.getProperty("password"));

            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(2);

            connectionPool = new BoneCP(config);

            opened = true;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dispose() {
        connectionPool.shutdown();
        opened = false;
    }
}
