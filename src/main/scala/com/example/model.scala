package com.example

import java.util.UUID

import scala.concurrent.Future

import com.outworkers.phantom.Table
import com.outworkers.phantom.builder.query.InsertQuery
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.keys.PartitionKey

// スキーマ
final case class Message(id: UUID, message: String, timestamp: Long)

// テーブル
abstract class Messages extends Table[Messages, Message] {

  object category extends StringColumn with PartitionKey
  object subcategory extends StringColumn with PartitionKey
  object id extends UUIDColumn with PrimaryKey
  object message extends StringColumn
  object timestamp extends LongColumn

  def store(partition: (String, String), msg: Message): InsertQuery.Default[Messages, Message] =
    insert
      .value(_.category, partition._1)
      .value(_.subcategory, partition._2)
      .value(_.id, msg.id)
      .value(_.message, msg.message)
      .value(_.timestamp, msg.timestamp)

  def findByPartition(partition: (String, String)): Future[List[Message]] =
    select.where(_.category eqs partition._1).and(_.subcategory eqs partition._2).fetch()

  def findById(partition: (String, String), id: UUID): Future[Option[Message]] =
    select
      .where(_.category eqs partition._1)
      .and(_.subcategory eqs partition._2)
      .and(_.id eqs id)
      .one()

}
