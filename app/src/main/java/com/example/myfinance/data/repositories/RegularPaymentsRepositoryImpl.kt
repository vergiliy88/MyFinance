package com.example.myfinance.data.repositories

import com.example.myfinance.domain.models.RegularPayments
import com.example.myfinance.domain.repositories.RegularPaymentsRepository
import com.example.myfinance.App
import com.example.myfinance.data.DataBase
import com.example.myfinance.domain.mappers.MapFromDBRegularPayments


class RegularPaymentsRepositoryImpl: RegularPaymentsRepository {
    var db: DataBase = App.getInstance().database
    override suspend fun saveRegularPayments(regularPayments: RegularPayments): RegularPayments {
        if (regularPayments.id != null) {
            db.regularPaymentsDao()
                .updateRegularPayments(MapFromDBRegularPayments.mapTomDb(regularPayments))
        } else {
            regularPayments.id = db.regularPaymentsDao()
                .insertRegularPayments(MapFromDBRegularPayments.mapTomDb(regularPayments))
        }

        return regularPayments
    }

    override suspend fun getRegularPayments(): RegularPayments {
        val list = db.regularPaymentsDao().getAllRegularPayments()
        if (list.isEmpty()) {
            return RegularPayments()
        }
        return MapFromDBRegularPayments.mapFromDb(db.regularPaymentsDao().getAllRegularPayments().first())
    }
}