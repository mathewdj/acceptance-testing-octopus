//package local.mathewdj.tennis
//
//import org.postgresql.ds.PGSimpleDataSource
//import org.testcontainers.containers.PostgreSQLContainer
//import org.testcontainers.utility.DockerImageName
//import javax.sql.DataSource
//
//object TestDatabase {
//    private val container = PostgreSQLContainer(DockerImageName.parse("postgres:13.4"))
//
//    fun setup(): DataSource {
//        if (!container.isRunning) {
//            container.start()
//        }
//
//        return setupDataSource(container)
//    }
//
//    private fun setupDataSource(postgreSQLContainer: PostgreSQLContainer<*>): DataSource {
//        return PGSimpleDataSource().apply {
//            setURL(postgreSQLContainer.jdbcUrl)
//            user = postgreSQLContainer.username
//            password = postgreSQLContainer.password
//        }
//    }
//}
