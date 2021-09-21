package com.example.myfinance.data.mappers

import com.example.myfinance.data.entities.PaymentTypeDB
import com.example.myfinance.domain.models.PaymentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapPaymentTypes {
    companion object{
        fun mapFromDBFlowList(fromDB: Flow<List<PaymentTypeDB>>): Flow<List<PaymentType>> {
            return fromDB.map { item ->
                val resultList: MutableList<PaymentType> = mutableListOf()
                for (paymentDB in item) {
                    val domainModel = PaymentType()
                    domainModel.id = paymentDB.id
                    domainModel.name = paymentDB.name
                    domainModel.sum = paymentDB.sum
                    domainModel.color = paymentDB.color
                    resultList.add(domainModel)
                }
                return@map resultList
            }
        }
        fun mapFromDBList(fromDB: List<PaymentTypeDB>): List<PaymentType> {
            val list: MutableList<PaymentType> = mutableListOf()
            for (item in fromDB) {
                val domainModel = PaymentType()
                domainModel.id = item.id
                domainModel.name = item.name
                domainModel.sum = item.sum
                domainModel.color = item.color
                list.add(domainModel)
            }
            return list
        }

        fun mapToDb(domainModel: PaymentType): PaymentTypeDB {
            val toDB = PaymentTypeDB()
            toDB.id = domainModel.id
            toDB.name = domainModel.name
            toDB.sum = domainModel.sum
            toDB.color = domainModel.color
            return toDB
        }

        fun mapFromDb(fromDB: PaymentTypeDB): PaymentType {
            val domainModel = PaymentType()
            domainModel.id = fromDB.id
            domainModel.name = fromDB.name
            domainModel.sum = fromDB.sum
            domainModel.color = fromDB.color
            return domainModel
        }
    }
}
