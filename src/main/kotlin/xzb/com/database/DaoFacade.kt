package xzb.com.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import xzb.com.database.dao.account_detail
import xzb.com.database.dao.user_entity

class DaoFacade {
    private val db = Database.connect(DatabaseFactory.create())

    fun getMobile(idCards: MutableCollection<String>): Map<String, String> {
        val m = mutableMapOf<String, String>()
        transaction(db)
        {
            addLogger(StdOutSqlLogger)
            val f = account_detail.join(
                user_entity,
                JoinType.LEFT,
                account_detail.account_id,
                user_entity.id
            )
                .slice(account_detail.name, account_detail.idcardNo, user_entity.mobile)
                .select {
                    account_detail.idcardNo.inList(idCards)
                }.withDistinct()
                .forEach {
                    m += it[account_detail.idcardNo] to it[user_entity.mobile]
                }
        }
        return m
    }
}