package xzb.com.database.dao

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object account_detail : LongIdTable() {
  val name: Column<String> = varchar("name", 50)
  val idcardNo :Column<String> = varchar("idcardNo", 50)
  val account_id: Column<Long> = long("account_id")
}

object user_entity : LongIdTable()  {
  val mobile :Column<String> = varchar("mobile", 50)
}