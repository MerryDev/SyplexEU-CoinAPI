package eu.eyplex.coinapi.data.connection

/**
 * Represents a data-holder object containing credentials for a successful database connection.
 *
 * @param[host] The database hostname. Most likely the IP-Address
 * @param[port] The port the database server is running at
 * @param[database] The name of the database
 * @param[username] The username used to connect to the database
 * @param[password] The users password
 */
class ConnectionProperty(val host: String, val port: String, val database: String, val username: String, val password: String)