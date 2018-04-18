package com.example

import com.outworkers.phantom.connectors.ContactPoint
import com.outworkers.phantom.database.{Database, DatabaseProvider}
import com.outworkers.phantom.dsl.{CassandraConnection, KeySpace, replication, _}
import com.typesafe.config.ConfigFactory

// コネクタ(Singleton)
object Connector {

  private val config = ConfigFactory.load()

  config.checkValid(ConfigFactory.defaultReference(), "cassandra")

  val connection: CassandraConnection =
    ContactPoint(config.getString("cassandra.host"), config.getInt("cassandra.port"))
      .noHeartbeat()
      .noHeartbeat()
      .keySpace(
        KeySpace(config.getString("cassandra.keyspace"))
          .ifNotExists()
          .`with`(
            replication eqs SimpleStrategy.replication_factor(1)
          )
      )
}

// データベース定義
class AppDatabase(override val connector: CassandraConnection)
    extends Database[AppDatabase](connector) {
  object messages extends Messages with Connector
}

// プロバイダとして提供
trait AppDatabaseProvider extends DatabaseProvider[AppDatabase]
