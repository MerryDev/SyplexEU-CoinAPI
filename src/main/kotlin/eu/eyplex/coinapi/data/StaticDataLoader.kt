package eu.eyplex.coinapi.data

import com.zaxxer.hikari.HikariDataSource
import de.chojo.sadu.databases.MariaDb
import de.chojo.sadu.datasource.DataSourceCreator
import eu.eyplex.coinapi.data.connection.ConnectionProperty
import eu.eyplex.coinapi.data.sadu.StaticQueryAdapter

object StaticDataLoader {

    fun loadDatabase(property: ConnectionProperty) {
        StaticQueryAdapter.start(createDataSource(property))
        createTable()
    }

    private fun createDataSource(property: ConnectionProperty): HikariDataSource {
        return DataSourceCreator.create(MariaDb.get())
            .configure { config ->
                config.host(property.host)
                    .port(property.port)
                    .database(property.database)
                    .user(property.username)
                    .password(property.password)
            }
            .create()
            .withMaximumPoolSize(3)
            .withMinimumIdle(1)
            .build()
    }

    private fun createTable() {
        StaticQueryAdapter.builder()
            .query("CREATE TABLE IF NOT EXISTS player_coins (uuid VARCHAR(36) NOT NULL PRIMARY KEY, coins INTEGER NOT NULL DEFAULT 0) ENGINE=InnoDB;")
            .emptyParams()
            .update()
            .sendSync()
    }

}