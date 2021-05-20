package xzb.com.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import xzb.com.database.dao.account_detail
import xzb.com.database.dao.user_entity

class DaoFacade {
    private val db = Database.connect(DatabaseFactory.create())

    fun getMobile(idCards: MutableCollection<String>): List<Map<String, String>> = transaction(db) {
        addLogger(StdOutSqlLogger)
       return@transaction account_detail.join(user_entity,JoinType.LEFT,account_detail.account_id,user_entity.id)
           .slice(account_detail.name, account_detail.idcardNo, user_entity.mobile)
            .select {
                account_detail.idcardNo.inList(idCards)
            }.withDistinct().map { obj: ResultRow -> mapOf(obj[account_detail.idcardNo] to obj[user_entity.mobile]) }
            .toList()
    }


}