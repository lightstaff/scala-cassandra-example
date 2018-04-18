package com.example

import scala.concurrent.Future

import com.outworkers.phantom.ResultSet
import com.outworkers.phantom.dsl._

trait MessageService extends AppDatabaseProvider {

  def store(partition: (String, String), msg: Message): Future[ResultSet] =
    db.messages.store(partition, msg).future()

  def findPartition(partition: (String, String)): Future[List[Message]] =
    db.messages.findByPartition(partition)

  def findById(partition: (String, String), id: UUID): Future[Option[Message]] =
    db.messages.findById(partition, id)

  def batchStore(partition: (String, String), messages: Message*): Future[ResultSet] =
    Batch.logged.add(messages.map(msg => db.messages.store(partition, msg))).future()

}
