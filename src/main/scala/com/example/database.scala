package com.example

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.{Database, DatabaseProvider}

class AppDatabase(override val connector: CassandraConnection)
    extends Database[AppDatabase](connector) {
  object messages extends Messages with Connector
}

trait AppDatabaseProvider extends DatabaseProvider[AppDatabase]
