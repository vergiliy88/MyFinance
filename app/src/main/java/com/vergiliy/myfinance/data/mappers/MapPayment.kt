package com.vergiliy.myfinance.data.mappers

import com.vergiliy.myfinance.data.models.PaymentDB
import com.vergiliy.myfinance.domain.models.Payment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapPayment {
    companion object{
        fun mapFromDbListFlow(fromDB: Flow<List<PaymentDB>>): Flow<List<Payment>> {
            return fromDB.map { item ->
                val resultList: MutableList<Payment> = mutableListOf()
                for (paymentDB in item) {
                    val domainModel = Payment()
                    domainModel.id = paymentDB.id
                    domainModel.comment = paymentDB.comment
                    domainModel.realSum = paymentDB.realSum
                    domainModel.date = paymentDB.date
                    domainModel.paymentType = paymentDB.paymentType
                    resultList.add(domainModel)
                }
                return@map resultList
            }
        }

        fun mapFromDBList(fromDB: List<PaymentDB>): List<Payment> {
            val list: MutableList<Payment> = mutableListOf()
            for (item in fromDB) {
                val domainModel = Payment()
                domainModel.id = item.id
                domainModel.comment = item.comment
                domainModel.realSum = item.realSum
                domainModel.date = item.date
                domainModel.paymentType = item.paymentType
                list.add(domainModel)
            }
            return list
        }

        fun mapFromDb(paymentDB: PaymentDB): Payment {
            val payment = Payment()
            payment.id = paymentDB.id
            payment.comment = paymentDB.comment
            payment.realSum = paymentDB.realSum
            payment.date = paymentDB.date
            payment.paymentType = paymentDB.paymentType
            return payment
        }

        fun mapToDb(domainModel: Payment): PaymentDB {
            val toDB = PaymentDB()
            toDB.id = domainModel.id
            toDB.comment = domainModel.comment
            toDB.realSum = domainModel.realSum
            toDB.date = domainModel.date
            toDB.paymentType = domainModel.paymentType
            return toDB
        }

        fun mapToDbList(domainModels: List<Payment>): List<PaymentDB> {
            val list: MutableList<PaymentDB> = mutableListOf()
            for (domainModel in domainModels) {
                val reposModel = PaymentDB()
                reposModel.id = domainModel.id
                reposModel.comment = domainModel.comment
                reposModel.realSum = domainModel.realSum
                reposModel.date = domainModel.date
                reposModel.paymentType = domainModel.paymentType
                list.add(reposModel)
            }
            return list
        }
    }
}