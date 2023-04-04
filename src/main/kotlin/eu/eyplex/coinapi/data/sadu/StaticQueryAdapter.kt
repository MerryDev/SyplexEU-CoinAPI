package eu.eyplex.coinapi.data.sadu

import de.chojo.sadu.base.QueryFactory
import de.chojo.sadu.wrapper.stage.QueryStage
import eu.eyplex.coinapi.data.exception.AlreadyInitializedException
import eu.eyplex.coinapi.data.exception.NotInitializedException
import java.util.logging.Logger
import javax.sql.DataSource

object StaticQueryAdapter {
    private val LOGGER = Logger.getLogger(StaticQueryAdapter::class.java.name)
    private var factory: QueryFactory? = null

    fun builder(): QueryStage<Void> {
        assertInit()
        return factory!!.builder()
    }

    fun <T> builder(clazz: Class<T>): QueryStage<T> {
        assertInit()
        return factory!!.builder(clazz)
    }

    fun start(source: DataSource?) {
        if (factory != null) throw AlreadyInitializedException()
        factory = QueryFactory(source)
        LOGGER.info("Static SADU query adapter started.")
    }

    private fun assertInit() {
        if (factory == null) throw NotInitializedException()
    }
}
