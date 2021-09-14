package com.example.myfinance.data.repositories

import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.domain.repositories.RegularPaymentsRepository
import com.example.myfinance.App
import com.example.myfinance.data.DataBase
import com.example.myfinance.data.mappers.MapRegularPayments


class RegularPaymentsRepositoryImpl: RegularPaymentsRepository {
    var db: DataBase = App.getInstance().database
    override suspend fun saveRegularPayments(regularPayments: RegularPayments): Long {
        return db.regularPaymentsDao()
            .insertRegularPayments(MapRegularPayments.mapTomDb(regularPayments))
    }

    override suspend fun getRegularPayments(): RegularPayments {
        val list = db.regularPaymentsDao().getAllRegularPayments()
        if (list.isEmpty()) {
            return RegularPayments()
        }
        return MapRegularPayments.mapFromDb(list.first())
    }

    override suspend fun updateRegularPayments(regularPayments: RegularPayments) {
        db.regularPaymentsDao()
            .updateRegularPayments(MapRegularPayments.mapTomDb(regularPayments))
    }
}