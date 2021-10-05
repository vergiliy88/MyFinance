package com.vergiliy.myfinance.data.repositories

import com.vergiliy.myfinance.domain.models.RegularPayments
import com.vergiliy.myfinance.domain.repositories.RegularPaymentsRepository
import com.vergiliy.myfinance.App
import com.vergiliy.myfinance.data.mappers.MapRegularPayments
import kotlinx.coroutines.flow.Flow


class RegularPaymentsRepositoryImpl: RegularPaymentsRepository {
    var db = App.getInstance().database.regularPaymentsDao()
    override suspend fun saveRegularPayments(regularPayments: RegularPayments): Long {
        return db.insertRegularPayments(MapRegularPayments.mapTomDb(regularPayments))
    }

    override suspend fun getRegularPayments(): RegularPayments {
        val list = db.getAllRegularPayments()
        if (list.isEmpty()) {
            return RegularPayments()
        }
        return MapRegularPayments.mapFromDb(list.first())
    }

    override fun getRegularPaymentsFlow(): Flow<RegularPayments> {
        return MapRegularPayments.mapFromDbListFlow(db.getAllRegularPaymentsFlow())
    }

    override suspend fun updateRegularPayments(regularPayments: RegularPayments) {
        db.updateRegularPayments(MapRegularPayments.mapTomDb(regularPayments))
    }
}