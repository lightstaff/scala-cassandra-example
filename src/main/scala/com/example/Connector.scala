package com.example

import scala.language.reflectiveCalls

import com.outworkers.phantom.connectors.ContactPoint
import com.outworkers.phantom.dsl.{KeySpace, replication}
import com.typesafe.config.ConfigFactory
import com.outworkers.phantom.dsl._

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
