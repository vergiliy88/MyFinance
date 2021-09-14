package com.example.myfinance.data.mappers

import com.example.myfinance.data.entities.RegularPaymentsDB
import com.example.myfinance.domain.models.RegularPayments

class MapRegularPayments {
    companion object{
        fun mapFromDb(fromDB: RegularPaymentsDB): RegularPayments  {
            val domainModel = RegularPayments()
            domainModel.id = fromDB.id
            domainModel.salary = fromDB.salary
            domainModel.salaryDay = fromDB.salaryDay
            domainModel.prepaid = fromDB.prepaid
            domainModel.prepaidDay = fromDB.prepaidDay
            return domainModel
        }

        fun mapTomDb(domainModel: RegularPayments): RegularPaymentsDB  {
            val toDB = RegularPaymentsDB()
            toDB.id = domainModel.id
            toDB.salary = domainModel.salary
            toDB.salaryDay = domainModel.salaryDay
            toDB.prepaid = domainModel.prepaid
            toDB.prepaidDay = domainModel.prepaidDay
            return toDB
        }
    }
}