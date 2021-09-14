package com.example.myfinance.data.mappers

import com.example.myfinance.data.entities.PaymentTypeDB
import com.example.myfinance.domain.models.PaymentType

class MapPaymentTypes {
    companion object{
        fun mapFromDbList(fromDB: List<PaymentTypeDB>): List<PaymentType> {
            val resultList = mutableListOf<PaymentType>()
            for (item in fromDB){
                val domainModel = PaymentType()
                domainModel.id = item.id
                domainModel.name = item.name
                domainModel.sum = item.sum
                domainModel.color = item.color
                resultList.add(domainModel)
            }

            return resultList
        }

        fun mapFromDb(fromDB: PaymentTypeDB): PaymentType {
            val domainModel = PaymentType()
            domainModel.id = fromDB.id
            domainModel.name = fromDB.name
            domainModel.sum = fromDB.sum
            domainModel.color = fromDB.color
            return domainModel
        }

        fun mapTomDb(domainModel: PaymentType): PaymentTypeDB {
            val toDB = PaymentTypeDB()
            toDB.id = domainModel.id
            toDB.name = domainModel.name
            toDB.sum = domainModel.sum
            toDB.color = domainModel.color
            return toDB
        }
    }
}