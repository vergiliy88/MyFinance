package com.example.myfinance.data.mappers

import com.example.myfinance.data.models.RegularPaymentsDB
import com.example.myfinance.domain.models.RegularPayments
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapRegularPayments {
    companion object{
        fun mapFromDbListFlow(fromDB: Flow<List<RegularPaymentsDB>>): Flow<RegularPayments>{
            return fromDB.map { item ->
                val result = RegularPayments()
                for (regularPaymentsDB in item) {
                    result.id = regularPaymentsDB.id
                    result.salary = regularPaymentsDB.salary
                    result.salaryDay = regularPaymentsDB.salaryDay
                    result.prepaid = regularPaymentsDB.prepaid
                    result.prepaidDay = regularPaymentsDB.prepaidDay
                    break
                }
                return@map result
            }
        }

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